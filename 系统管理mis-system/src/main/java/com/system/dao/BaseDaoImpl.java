package com.system.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.LobRetrievalFailureException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.stereotype.Repository;

import com.system.dao.interf.BaseDao;
import com.system.util.BaseForm;
import com.system.util.Page;

/*
 * BaseDao实现基类
 * 多参数情况推荐使用命名参数绑定
 * 单行查询或参数易区分情况下推荐使用 ?占位符
 * */
@Repository
public class BaseDaoImpl implements BaseDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private MysqlSQLPageHandleImpl mysqlSQLPageHandleImpl;
	@Autowired
	private OracleSQLPageHandleImpl oracleSQLPageHandleImpl;
	//默认连接 mysql sqlserver
	@Autowired
	private LobHandler lobHandler;
	//Oracle连接
	@Autowired
	private OracleLobHandler oracleLobHandler;
	
	/*
	 * List结果集查询,List中的每个元素是一个实体类
	 * 优点：在结果集为多行记录时,该接口更容易使用
	 * 缺点：一次性返回数据并组合到List中,不适合大量数据查询,不适合大量in条件语句
	 * 示例：①
	 * String sql = "SELECT * FROM t_forum";
	 * List<Forum> resultList = find(sql, null, Forum.class);
	 * 示例：②
	 * String sql = "SELECT * FROM t_forum where forum_id = ?";
	 * List<Forum> resultList = find(sql, new Object[]{2}, Forum.class);
	 * 示例：③
	 * String sql = "SELECT * FROM t_forum where forum_id in (?,?)";	//in的条件推荐使用命名参数绑定的形式
	 * List<Forum> resultList = find(sql, new Object[]{2,3}, Forum.class);
	 * 示例：④
	 * String sql = "SELECT * FROM t_forum where forum_name like ?";
	 * List<Forum> resultList = find(sql, new Object[]{'%名称%'}, Forum.class);
	 * 返回List [Forum1,Forum2...]
	 * */
	public <T> List<T> findForListClass(String sql,Object[] params, Class<T> tClass){
		List<T> resultList = null;
        if (params != null && params.length > 0){
            resultList = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<T>(tClass));
        }else{
            /*
             * BeanPropertyRowMapper是自动映射实体类的
             * RowMapper需要手动映射实体类
             */
            resultList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(tClass));
        }
        return resultList;
	}
	
	/*
	 * 命名参数绑定
	 * find(String sql,Object[] params, Class<T> tClass) 方法的重构
	 * 示例：
	 * BaseForm form = new BaseForm();
	 * form.addSql("SELECT * FROM t_forum where forum_id in (:id) and forum_desc like :desc and forum_name = :name");
	 * form.addParam("id", Arrays.asList("1","2","3"));	//in需要以List的形式
	 * form.addParam("desc", "%1%");	//此处不带单引号''
	 * form.addParam("name","李四");		//此处不带单引号''
	 * form.addEntity(Forum.class);		//设置实体类 也可以通过重构的form.addEntity("com.user.root.daotest.domain.Forum");
	 * find(form);
	 * 返回List [Forum1,Forum2...]
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findForListClass(BaseForm baseForm){
		List<T> resultList = null;
		if(baseForm.getMapSqlParam() !=null){
			resultList = namedParameterJdbcTemplate.query(baseForm.get("sql"), baseForm.getMapSqlParam(), new BeanPropertyRowMapper<T>(baseForm.getClazz()));
		}else
			resultList = namedParameterJdbcTemplate.query(baseForm.get("sql"), new BeanPropertyRowMapper<T>(baseForm.getClazz()));
		return resultList;
	}
	
	/*
	 * 获取单行记录
	 * 示例：User user=(User) baseDaoImpl.findForObject("select * from t_forum where forum_id = 6", new User());
	 * 返回Class实体类 可根据传入的对象自定义赋值过程
	 * */
	@SuppressWarnings("unchecked")
	public <T extends RowMapper> Object findForObject(String sql, Object[] args, Object obj){
		if (args == null || args.length <= 0) {
			return jdbcTemplate.queryForObject(sql, (T)obj);
		}
		return jdbcTemplate.queryForObject(sql, args, (T)obj);
	}
	
	/*
	 * 获取单行记录
	 * 示例：find("SELECT * FROM t_forum WHERE forum_id=? and forum_name like ?",new Object[]{2,"%王%"},Forum.class)
	 * 返回Class实体类
	 * */
	public <T> T findForObject(String sql, Object[] args, Class<T> classT) {
		if (args == null || args.length <= 0) {
        	//适用于一张表只有一行记录
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(classT));
        }
        return jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<T>(classT));
	}
	
	/*
	 * 命名参数绑定
	 * 获取单行记录
	 * 示例：
	 * BaseForm form = new BaseForm();
	 * form.addSql("SELECT * FROM t_forum WHERE forum_desc like :name");
	 * form.addParam("name", "%1%");	//此处不带单引号''
	 * form.addEntity(Forum.class);
	 * findForObject(form);
	 * 返回Class实体类
	 * */
	@SuppressWarnings("unchecked")
	public <T> T findForObject(BaseForm baseForm){
		T clazz = null;
		if(baseForm.getMapSqlParam() !=null){
			clazz = namedParameterJdbcTemplate.queryForObject(baseForm.get("sql"), baseForm.getMapSqlParam(), new BeanPropertyRowMapper<T>(baseForm.getClazz()));
		}else{
			//适用于一张表只查询一行记录 limit 0,1
			List<T> listData = namedParameterJdbcTemplate.query(baseForm.get("sql"), new BeanPropertyRowMapper<T>(baseForm.getClazz()));
			if(listData.size()>0){
				clazz = listData.get(0);
			}
		}
		return clazz;
	}
	
	/*
	 * 获取单个值
	 * 示例：①
	 * String sql = "select post_text from t_post where post_id=10";
	 * findforValue(sql,null);
	 * 示例：②
	 * String sql = "select post_text from t_post where post_id=?";
	 * findforValue(sql,new Object[]{7});
	 * 返回字符字段
	 * */
	public String findforValue(String sql,Object[] params){
		if (params == null || params.length <= 0) {
			return jdbcTemplate.queryForObject(sql, String.class);
		}else
			return jdbcTemplate.queryForObject(sql, params, String.class);
	}
	
	/*
	 * 获取单行记录
	 * 示例：find("SELECT * FROM t_forum WHERE forum_id=?",new Object[]{2})
	 * 返回Map {forum_id=2, forum_name=李四, forum_desc=李四的名称}
	 * */
	public Map<String, Object> findForMap(String sql,Object[] params){
		Map<String, Object> reMap = null;
		if(params != null && params.length>0){
			reMap = jdbcTemplate.queryForMap(sql,params);
		}else
			reMap = jdbcTemplate.queryForMap(sql);
		return reMap;
	}
	
	/*
	 * 命名参数绑定
	 * 获取单行记录
	 * 示例：
	 * BaseForm form = new BaseForm();
	 * form.addSql("SELECT * FROM t_forum WHERE forum_desc like :name");
	 * form.addParam("name", "%12%");	//此处不带单引号''
	 * findForMap(form);
	 * 返回Map {forum_id=2, forum_name=李四, forum_desc=李四的名称}
	 * */
	public Map<String, Object> findForMap(BaseForm baseForm){
		Map<String, Object> reMap = null;
		if(baseForm.getMapSqlParam() !=null){
			reMap = namedParameterJdbcTemplate.queryForMap(baseForm.get("sql"), baseForm.getMapSqlParam());
		}else{
			//适用于一张表只查询一行记录 limit 0,1
			List<Map<String, Object>> resultList = namedParameterJdbcTemplate.query(baseForm.get("sql"), new ColumnMapRowMapper());
			if(resultList.size()>0){
				reMap = resultList.get(0);
			}
		}
		return reMap;
	}
	
	/*
	 * 返回集合,将结果集中的每一行映射为一个Map
	 * 示例：find("SELECT * FROM t_forum WHERE forum_id=?",new Object[]{2})
	 * 返回List<Map<String, Object>> [{forum_id=1, forum_name=张三, forum_desc=张三的名称}]
	 * */
	public List<Map<String, Object>> find(String sql,Object[] params){
		List<Map<String, Object>> resultList = null;
		if(params != null && params.length>0){
			resultList = jdbcTemplate.query(sql,params, new ColumnMapRowMapper());
		}else
			resultList = jdbcTemplate.query(sql, new ColumnMapRowMapper());
		return resultList;
	}
	
	/*
	 * 命名参数绑定
	 * 返回集合,将结果集中的每一行映射为一个Map
	 * 示例：
	 * BaseForm form = new BaseForm();
	 * form.addSql("SELECT * FROM t_forum where forum_desc like :desc");
	 * form.addParam("desc", "%1%");
	 * find(form);
	 * 返回List<Map<String, Object>> [{forum_id=1, forum_name=张三, forum_desc=张三的名称}]
	 * */
	public List<Map<String, Object>> find(BaseForm baseForm){
		List<Map<String, Object>> resultList = null;
		if(baseForm.getMapSqlParam() !=null){
			resultList = namedParameterJdbcTemplate.queryForList(baseForm.get("sql"), baseForm.getMapSqlParam());
		}else
			resultList = namedParameterJdbcTemplate.query(baseForm.get("sql"), new ColumnMapRowMapper());
		return resultList;
	}
	
	/*
	 * 分页
	 * 返回page分页类对象
	 * 示例：
	 * Page<Map<String, Object>> page = baseDaoImpl.queryPagination("SELECT * FROM t_forum", pageNo, 3);
	 * page.getRows(); 获取分页list结果集
	 * 返回page<Map<String, Object>> [{forum_id=1, forum_name=张三, forum_desc=张三的名称}...]
	 * */
	public Page<Map<String, Object>> queryPagination(String sql, int pageNo, int pageSize){
		//将sql语句进行分页查询
		String newSql = mysqlSQLPageHandleImpl.handlerPagingSQL(sql, pageNo, pageSize);
		String tableSql = "select count(1) from ("+sql+") as a";	//此处只适用于mysql
		//获取sql查询的总数
		int total = Integer.parseInt(this.findforValue(tableSql,null));
		List<Map<String, Object>> list = find(newSql, null);
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageNo, pageSize, total, list);
		return page;
	}
	
	/*
	 * 分页（Oracle）
	 * 返回page分页类对象
	 * 示例：
	 * Page<Map<String, Object>> page = baseDaoImpl.queryOraclePagination("t_forum", 1, 3);
	 * page.getRows(); 获取分页list结果集
	 * 返回page<Map<String, Object>> [{forum_id=1, forum_name=张三, forum_desc=张三的名称}...]
	 * */
	public Page<Map<String, Object>> queryOraclePagination(String sql, int pageNo, int pageSize){
		//将sql语句进行分页查询
		String newSql = oracleSQLPageHandleImpl.handlerPagingSQL(sql, pageNo, pageSize);
		String tableSql = "select count(1) from ("+sql+")";
		//获取sql查询的总数
		int total = Integer.parseInt(this.findforValue(tableSql,null));
		List<Map<String, Object>> list = find(newSql, null);
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageNo, pageSize, total, list);
		return page;
	}
	
	/*
	 * 获取表字段
	 * 示例：findTableColumnNames("SELECT * FROM t_forum")
	 * 返回String[] [forum_id, forum_name, forum_desc]
	 * */
	public String[] findTableColumnNames(String sql){
		RowCountCallbackHandler countCallback = null;
		countCallback = new RowCountCallbackHandler();
		jdbcTemplate.query(sql+" LIMIT 1,1", countCallback);
		return countCallback.getColumnNames();
	}
	
	/*
	 * 添加一行记录
	 * 示例：
	 * update("INSERT INTO t_forum(forum_name,forum_desc) VALUES(?,?)",new Object[]{"赵六","赵六的名字3"});
	 * update("INSERT INTO t_forum(forum_name,forum_desc) VALUES('aa','bb')");
	 * 返回 影响的行数
	 * */
	public int update(String sql,Object[] args){
		if (args==null || args.length==0) {
			jdbcTemplate.update(sql);
			return -1;
		}
		return jdbcTemplate.update(sql, args);
	}
	
	/*
	 * 命名参数绑定
	 * 添加一行记录
	 * 示例：①
	 * BaseForm form = new BaseForm();
	 * form.addSql("INSERT INTO t_forum(forum_name,forum_desc) VALUES(:forum_name,:forum_desc)");
	 * form.addParam("forum_name", "xxx");
	 * form.addParam("forum_desc", "xxx");
	 * update(form);
	 * 示例：②
	 * BaseForm form = new BaseForm();
	 * form.addSql("INSERT INTO t_forum(forum_name,forum_desc) VALUES(:forum_name,:forum_desc)");
	 * Forum forum = new Forum();
	 * forum.setForum_name("丽丽");
	 * forum.setForum_desc("丽丽的名称");
	 * form.addObject(forum);
	 * update(form);
	 * 返回 影响的行数
	 * */
	public int update(BaseForm baseForm){
		if(baseForm.getObject() != null){
			//以Bean对象的形式添加
			SqlParameterSource sps = new BeanPropertySqlParameterSource(baseForm.getObject());
			return namedParameterJdbcTemplate.update(baseForm.get("sql"), sps);
		}
		if(baseForm.getMapSqlParam() != null){
			//以Map形式添加
			return namedParameterJdbcTemplate.update(baseForm.get("sql"), baseForm.getMapSqlParam());
		}
		return -1;
	}
	
	/*
	 * 命名参数绑定
	 * 添加一行记录,并返回数据表中增加的id主键值
	 * 注意: 这里只能用Bean对象的形式
	 * 示例：
	 * BaseForm form = new BaseForm();
	 * form.addSql("INSERT INTO t_forum(forum_name,forum_desc) VALUES(:forum_name,:forum_desc)");
	 * Forum forum = new Forum();
	 * forum.setForum_name("丽丽");
	 * forum.setForum_desc("丽丽的名称");
	 * form.addObject(forum);
	 * updateReturnId(form);
	 * 返回 增加的id主键值
	 * */
	public int updateReturnId(BaseForm baseForm){
		if(baseForm.getObject() != null) return -1;
		SqlParameterSource sps = new BeanPropertySqlParameterSource(baseForm.getObject());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();	//创建一个主键持有者
		namedParameterJdbcTemplate.update(baseForm.get("sql"), sps, keyHolder);
		return keyHolder.getKey().intValue();			//从主键持有者中获取主键
	}
	
	/*
	 * 批量更改数据
	 * 多条SQL语句组成一个数组(这些SQL语句不带参数)
	 * 示例：
	 * String[] sqlStrings = {"INSERT INTO t_forum(forum_name,forum_desc) VALUES('a','a')",
	 * 							"INSERT INTO t_forum(forum_name,forum_desc) values('aa','aa')"};
	 * batchUpdate(sqlStrings);
	 * 返回 [1,1]
	 * */
	public int[] batchUpdate(String[] sql) {
		if(sql.length==0) return null;
		return jdbcTemplate.batchUpdate(sql);
	}
	
	/*
	 * 批量更改数据
	 * 如果List非常大,希望分多次批量提交,则可以分段读取这个大的List并暂存到一个小的List中(适用于excel大数据量数据导入)
	 * 示例：
	 * String sql = "INSERT INTO t_forum(forum_name,forum_desc) VALUES(?,?)";
	 * Object[] a1 = new Object[]{"a","a"};
	 * Object[] a2 = new Object[]{"aa","aa"};
	 * List<Object[]> datas = new ArrayList<Object[]>();
	 * datas.add(a1);
	 * datas.add(a2);
	 * batchUpdate(sql,datas);
	 * 返回 [1,1]
	 * */
	public int[] batchUpdate(final String sql,final List<Object[]> datas) {
		if(datas == null || datas.size() == 0) return null;
		int[] result = null;
		result = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			//绑定插入的参数
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Object[] params = datas.get(i);
				for (int n = 0; n < params.length; n++){
					ps.setObject(n + 1, params[n]);
				}
			}
			@Override
			//指定本批次的大小
			public int getBatchSize() {
				return datas.size();
			}
		});
		return result;
	}
	
	/*
	 * 测试中用的mysql 存储过程
	 * DROP PROCEDURE IF EXISTS sp_insert_table; 
	 * CREATE PROCEDURE `sp_insert_table`(IN aaa VARCHAR(50),IN bbb VARCHAR(150),OUT ccc INT) 
	 * BEGIN
	 * 	#SELECT * FROM t_forum where forum_name = aaa and forum_desc = bbb;
	 * 	#SELECT * FROM t_forum;
	 * 	INSERT INTO t_forum (forum_name,forum_desc) VALUES (aaa,bbb);
	 * 	SELECT COUNT(*) INTO ccc FROM t_forum WHERE forum_name = aaa AND forum_desc = bbb;
	 * END 
	 *	//
	 * DELIMITER ;
	*/
	
	/*
	 * 调用mysql存储过程
	 * 没有返回值
	 * 示例：
	 * callProcedure("call sp_insert_table('红果果','红果果')");
	 * */
	public void callDbProcedure(String sql) {
		if(sql==null || sql.length()==0) return;
		jdbcTemplate.execute(sql);
	}
	
	/*
	 * 调用mysql存储过程
	 * 返回int
	 * 示例：
	 * baseDaoImpl.callProcedure("call sp_insert_table(?,?,?)","红苹果","红苹果的名称");
	 * 注意：最后一个? out的输出参数不需要指定
	 * 返回 10
	 * */
	public Object callDbProcedure(String sql,final Object... params) {
		//sql = "call sp_insert_table(?,?,?)";
		Object result = jdbcTemplate.execute(sql,new CallableStatementCallback<Object>() {
			public Object doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				for (int n = 0; n < params.length; n++){
					cs.setObject(n+1, params[n]);
				}
				int outIndex = params.length+1;
				cs.registerOutParameter(outIndex,Types.INTEGER);
				cs.execute();
				return cs.getObject(outIndex);
			}
		});
		return result;
	}
	
	/*
	 * 插入mysql LOB类型的数据 
	 * 返回int 影响行数
	 * 示例：
	 * String sql = " INSERT INTO t_post(user_id,post_text,post_attach)"
	 *		       + " VALUES(?,?,?)";
	 * ClassPathResource res = new ClassPathResource("temp.jpg");
	 * byte[] mockImg = null;
	 * try {
	 *	mockImg = FileCopyUtils.copyToByteArray(res.getFile());
	 * } catch (IOException e) {
	 *	e.printStackTrace();
	 * }	
	 * baseDaoImpl.insertlob(sql,mockImg,"clob长文字",101112);
	 * 注意：clob和blob字段需要是表的最后两个字段
	 * 返回 1
	 * */
	public int insertlob(String sql,final byte[] bb,final String longText,final Object... objects){
		/*sql = " INSERT INTO t_post(user_id,post_text,post_attach)"
			       + " VALUES(?,?,?)";*/
		int result = jdbcTemplate.execute(sql,new AbstractLobCreatingPreparedStatementCallback(this.lobHandler) {
		    protected void setValues(PreparedStatement ps,LobCreator lobCreator)throws SQLException {
		    	/*
		    	 * Spring 通过LobCreator接口取消了不同厂商操作LOB数据的差异
		    	 * 并提供了创建LobCreator的LobHandler接口,你只要根据底层数据库类型选择合适的LobHandler进行配置即可
		    	 * */
		    	int blobIndex = objects.length+1,longIndex=objects.length+1;
		    	for (int n = 0; n < objects.length; n++){
		    		ps.setObject(n+1, objects[n]);
				}
		    	if(longText !=null){
		    		lobCreator.setClobAsString(ps, longIndex, longText);
		    		blobIndex = objects.length+2;
		    	}
				if(bb !=null){
					lobCreator.setBlobAsBytes(ps, blobIndex, bb);
				}
			}
		});
		return result;
	}
	
	/*
	 * 读取mysql LOB类型的数据 
	 * 示例：
	 * @ResponseBody
	 * @RequestMapping(value="/image/{imageId}.html",method=RequestMethod.GET)
	 * public byte[] readImage(@PathVariable("imageId") String imageId){
	 * 	  String sql = "SELECT post_attach FROM t_post WHERE post_id in (?,?)";
	 * 	  List<Object> data = baseDaoImpl.getLobByte(sql,new Object[]{7,8});
	 *    return (byte[])data.get(0);
	 * }
	 * 注意：返回值只包含byte类型,以byte的形式直接按块读取需要数据不能过大(大于100M),否则内存消耗太多
	 * [[byte[]],[byte[]]...]
	 * */
	public List<Object> getLobByte(String sql,Object[] params){
		//sql = "SELECT post_attach FROM t_post WHERE post_id in (?,?)";
		List<Object> resultList = jdbcTemplate.query(sql, params, new RowMapper<Object>(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				//以二进制数组方式获取blob数据
				byte[] attach = lobHandler.getBlobAsBytes(rs, 1);
				return attach;
			}
		});
		return resultList;
	}
	
	/*
	 * 读取mysql LOB类型的数据 
	 * 示例：
	 * try {
	 *   String sql = "SELECT post_attach FROM t_post WHERE post_id in (?)";
	 *   OutputStream out = new FileOutputStream("D:/a.jpg");
	 *   baseDaoImpl.getLobStream(sql, new Object[]{7}, out);
	 * } catch (FileNotFoundException e) {
	 * 	 e.printStackTrace();
	 * }
	 * 注意：以流数据方式读取 LOB 数据,适用于存储大的文件
	 * 返回值: void
	 * */
	public void getLobStream(String sql,Object[] params,final List<InputStream> data){
		//sql = "SELECT post_attach FROM t_post WHERE post_id in (?)";
		jdbcTemplate.query(sql, params, new AbstractLobStreamingResultSetExtractor<Object>(){
			//处理未找到数据行的情况
			protected void handleNoRowFound() throws LobRetrievalFailureException {
		       System.out.println("Not Found result!");
		    }
			protected void streamData(ResultSet rs) throws SQLException,
					IOException, DataAccessException {
				//以流的方式处理 LOB 字段 输出流请在自身的业务逻辑中关闭 或者使用FileCopyUtils类会自动关闭输出/输入流
				InputStream in = lobHandler.getBlobAsBinaryStream(rs, 1);
				data.add(in);
				/*if (in != null) {
					FileCopyUtils.copy(in, out);	//输入流转输出流
				}*/
			}
		});
	}

}
