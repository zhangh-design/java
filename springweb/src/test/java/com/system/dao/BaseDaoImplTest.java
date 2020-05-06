package com.system.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.system.util.BaseForm;
import com.system.util.Helper;
//import com.user.root.daotest.domain.Forum;

@ContextConfiguration(locations = { "classpath*:/spring/spring-context.xml" })
public class BaseDaoImplTest extends AbstractTransactionalTestNGSpringContextTests{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Test
	public void queryTest(){
		/*
		String sql = "SELECT * FROM t_forum where forum_id in (:id) ";
		List<Forum> resultList = find7(sql, Forum.class);
		for (Forum forum : resultList) {
			System.out.println(forum);
		}
		*/
		/*
		String sql = "SELECT forum_desc FROM t_forum";
		List resultList = find2(sql);
		System.out.println(resultList);
		*/
		//find1();
		//List<Map<String, Object>> resultList = find4("SELECT * FROM t_forum where forum_id in (?,?)",new Object[]{"1","2"});
		
		//Forum forum = findForObject("SELECT * FROM t_forum where forum_id =? and forum_name like ?",new Object[]{3,"%王%"},Forum.class);
		//Forum forum = findForObject("SELECT * FROM t_forum limit 0,1",null,Forum.class);
		
		//find5("SELECT * FROM t_forum where forum_id in (:ids) and forum_name like '%?%'","1,2,3","张");
		//int a = 0;
		
		//String sql="SELECT * FROM t_forum where forum_id in (:ids) and forum_name in (:names) and forum_desc like :desc";
		//find5(sql, "1,2,3","李四,王五","1");
		
		/*
		BaseForm form = new BaseForm();
		form.addSql("SELECT * FROM t_forum where forum_id in (:id) and forum_desc like :desc and forum_name = :name");
		form.addParam("id", Arrays.asList("1","2","3"));
		form.addParam("desc", "%1%");
		form.addParam("name", "李四");
		//form.addEntity(Forum.class);
		form.addEntity("com.user.root.daotest.domain.Forum");
		find7(form);
		*/
		/*
		BaseForm form = new BaseForm();
		form.addSql("SELECT * FROM t_forum WHERE forum_desc like :name");
		form.addParam("name", "%1%");
		form.addEntity("com.user.root.daotest.domain.Forum");
		findForObject(form);
		*/
		
		/*
		BaseForm form = new BaseForm();
		form.addSql("SELECT * FROM t_forum where forum_desc like :desc");
		form.addParam("desc", "%1%");
		//form.addEntity("com.user.root.daotest.domain.Forum");
		find4(form);
		*/
		
		//update("",new Object[]{"赵六","赵六的名字3"});
		/*
		BaseForm form = new BaseForm();
		form.addSql("INSERT INTO t_forum(forum_name,forum_desc) VALUES(:forum_name,:forum_desc)");
		Forum forum = new Forum();
		forum.setForum_name("丽丽");
		forum.setForum_desc("丽丽的名称");
		form.addObject(forum);
		updateReturnId(form);
		*/
		
		/*
		BaseForm form = new BaseForm();
		form.addSql("INSERT INTO t_forum(forum_name,forum_desc) VALUES(:forum_name,:forum_desc)");
		form.addParam("forum_name", "王可可");
		form.addParam("forum_desc", "王可可的名字");
		update(form);
		*/
		//update("",null);
		//batchUpdate(null);
		/*Forum forum = new Forum();
		forum.setForum_name("丽丽");
		forum.setForum_desc("丽丽的名称");
		
		Forum forum1 = new Forum();
		forum1.setForum_name("丽丽11");
		forum1.setForum_desc("丽丽11的名称");
		
		List<Forum> datas = new ArrayList<Forum>();
		datas.add(forum);
		datas.add(forum1);*/
		/*
		String sql = "INSERT INTO t_forum(forum_name,forum_desc) VALUES(?,?)";
		Object[] a1 = new Object[]{"a","a"};
		Object[] a2 = new Object[]{"aa","aa"};
		List<Object[]> datas = new ArrayList<Object[]>();
		datas.add(a1);
		datas.add(a2);
		batchUpdate(sql,datas);
		*/
		
		//call();
		/*
		List<String> list = null;
		list.add("111111");
		*/
		/*
		String total = jdbcTemplate.queryForObject("select post_text from t_post where post_id=?",new Object[]{7}, String.class);
		System.err.println(total);
		*/
		
	}
	
	public <T> List<T> find7(BaseForm baseForm){
		/*
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", Arrays.asList("1","2","3"));
		paramMap.put("desc", "%1%");
		String sql = "SELECT * FROM t_forum where forum_id in (:id) and forum_desc like :desc";
		List<T> resultList = null;
		Class<T> tClass = (Class<T>) Forum.class;
		resultList = namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<T>(tClass));
		*/
		//MapSqlParameterSource paramMap = new MapSqlParameterSource();
		//paramMap.addValue("id", Arrays.asList("1","2","3"));
		List<T> resultList = null;
		if(!baseForm.getMapSqlParam().getValues().isEmpty()){
			resultList = namedParameterJdbcTemplate.query(baseForm.get("sql"), baseForm.getMapSqlParam(), new BeanPropertyRowMapper<T>(baseForm.getClazz()));
		}else
			resultList = namedParameterJdbcTemplate.query(baseForm.get("sql"), new BeanPropertyRowMapper<T>(baseForm.getClazz()));
		return resultList;
	}
	
	public <T> List<T> find(BaseForm baseForm){
		List<T> resultList = null;
		if(!baseForm.getMapSqlParam().getValues().isEmpty()){
			resultList = namedParameterJdbcTemplate.query(baseForm.get("sql"), baseForm.getMapSqlParam(), new BeanPropertyRowMapper<T>(baseForm.getClazz()));
		}else
			resultList = namedParameterJdbcTemplate.query(baseForm.get("sql"), new BeanPropertyRowMapper<T>(baseForm.getClazz()));
        return resultList;
	}
	
	public void find1(){
		RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
		jdbcTemplate.query("SELECT * FROM t_forum LIMIT 1,1", countCallback);
		String[] rowCount = countCallback.getColumnNames();
		System.err.println("rowCount："+rowCount.toString());
	}
	
	public List find2(String sql){
		List resultList = jdbcTemplate.query(sql, new SingleColumnRowMapper());
		return resultList;
	}
	
	public void find3(String sql,Object[] params){
		Map<String, Object> reMap = null;
		try {
			if(params != null && params.length>0){
				reMap = jdbcTemplate.queryForMap(sql,params);
			}else
				reMap = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void find3(BaseForm baseForm){
		
		Map<String, Object> reMap = null;
		//reMap = namedParameterJdbcTemplate.queryForMap(baseForm.get("sql"), baseForm.getMapSqlParam());
		
		List<Map<String, Object>> resultList = namedParameterJdbcTemplate.query(baseForm.get("sql"), new ColumnMapRowMapper());
		
		String aString = "";
	}
	
	public List<Map<String, Object>> find4(String sql,Object[] params){
		//sql = sql.replace("'%?%'", "concat('%',?,'%')");
		List<Map<String, Object>> resultList = jdbcTemplate.query(sql,params, new ColumnMapRowMapper());
		return resultList;
	}
	
	public List<Map<String, Object>> find4(BaseForm baseForm){
		//sql = sql.replace("'%?%'", "concat('%',?,'%')");
		List<Map<String, Object>> resultList = null;
		//resultList = namedParameterJdbcTemplate.queryForList(baseForm.get("sql"), baseForm.getMapSqlParam());
		resultList = namedParameterJdbcTemplate.query(baseForm.get("sql"), new ColumnMapRowMapper());
		return resultList;
	}
	
	public List<Map<String, Object>> find5(String sql,String... args){
		/*Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("ids", Arrays.asList(args[0].split(",")));
		List<Map<String, Object>> list =  namedParameterJdbcTemplate.queryForList("SELECT * FROM t_forum where forum_id in (:ids)", paramMap);
		String a = "";*/
		List<String> sqlpList = Helper.getTeacherList(sql);
		/*if(sqlpList.size() != args.length){
			return null;
		}*/
		Map<String,Object> paramMap = new HashMap<String,Object>();
		for (int i = 0; i < sqlpList.size(); i++) {
			paramMap.put(sqlpList.get(i).replace(":", ""), Arrays.asList(args[i].split(",")));
		}
		//sql = sql.replace("'%?%'", "concat('%',?,'%')");
		List<Map<String, Object>> list =  namedParameterJdbcTemplate.queryForList(sql, paramMap);
		return null;
	}
	
	public void find6(){
		
	}
	
	public <T> T findForObject(String sql, Object[] args, Class<T> classT){
		if (sql == null || sql.length() <= 0) {
            return null;
        }
		//sql = Helper.mySqlLike(sql);
        try {
            if (args == null || args.length <= 0) {
                return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>());
            }
            return jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<T>(classT));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
	}
	
	public <T> T findForObject(BaseForm baseForm){
		T Object = null;
		try {
			if(baseForm.getMapSqlParam() !=null){
				Object = namedParameterJdbcTemplate.queryForObject(baseForm.get("sql"), baseForm.getMapSqlParam(), new BeanPropertyRowMapper<T>(baseForm.getClazz()));
			}else{
				List<T> listData = namedParameterJdbcTemplate.query(baseForm.get("sql"), new BeanPropertyRowMapper<T>(baseForm.getClazz()));
				if(listData.size()>0){
					Object = listData.get(0);
				}
			}
		} catch (Exception e) {
            e.printStackTrace();
        }
		return Object;
	}
	
	public int update(String sql,Object[] args){
		/*sql = "INSERT INTO t_forum(forum_name,forum_desc) VALUES(?,?)";
		try {
			return jdbcTemplate.update(sql, args);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}*/
		sql = "INSERT INTO t_forum(forum_name,forum_desc) VALUES('aaa','bbb')";
		int result = jdbcTemplate.update(sql);
		System.err.println(result);
		return result;
	}
	
	public int update(BaseForm baseForm){
		String sql = "INSERT INTO t_forum(forum_name,forum_desc) VALUES(:forum_name,:forum_desc)";
		SqlParameterSource sps = new BeanPropertySqlParameterSource(baseForm.getObject());
		int result = namedParameterJdbcTemplate.update(sql, sps);
		System.err.println(result);
		return 0;
		
		/*
		int result = namedParameterJdbcTemplate.update(baseForm.get("sql"), baseForm.getMapSqlParam());
		System.err.println(result);
		return 0;
		*/
	}
	
	public void updateReturnId(BaseForm baseForm){
		String sql = "INSERT INTO t_forum(forum_name,forum_desc) VALUES(:forum_name,:forum_desc)";
		SqlParameterSource sps = new BeanPropertySqlParameterSource(baseForm.getObject());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, sps, keyHolder);
		System.err.println(keyHolder.getKey().intValue());
	}
	
	public int[] batchUpdate(final String sql,final List<Object[]> datas) {
		/*String[] sqlStrings = {"INSERT INTO t_forum(forum_name,forum_desc) VALUES('a','a')",
								"INSERT INTO t_forum(forum_name,forum_desc) values('aa','aa')"};
		jdbcTemplate.batchUpdate(sqlStrings);*/
		
		//final String sql = "INSERT INTO t_forum(forum_name,forum_desc) VALUES(?,?)";
		int[] result = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Object[] params = datas.get(i);
				for (int n = 0; n < params.length; n++){
					ps.setObject(n + 1, params[n]);
				}
			}
			@Override
			public int getBatchSize() {
				return datas.size();
			}
		});
		return result;
	}
	
	public void call(){
		String sql = "call sp_insert_table('小明','小明的名字')";
		jdbcTemplate.execute(sql);
	}
}
