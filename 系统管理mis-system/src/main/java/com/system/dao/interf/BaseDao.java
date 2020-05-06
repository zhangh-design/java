package com.system.dao.interf;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.system.util.BaseForm;
import com.system.util.GridResultData;
import com.system.util.Page;

/*
 * BaseDao接口
 * select update delete 
 * */
public interface BaseDao {
	//select
	public <T> List<T> findForListClass(String sql, Object[] params, Class<T> tClass);
	public <T> List<T> findForListClass(BaseForm baseForm);
	
	public <T> T findForObject(String sql, Object[] args, Class<T> classT);
	public <T extends RowMapper> Object findForObject(String sql, Object[] args, Object obj);
	public <T> T findForObject(BaseForm baseForm);
	
	public String findforValue(String sql,Object[] params);
	
	public Map<String, Object> findForMap(String sql,Object[] params);
	public Map<String, Object> findForMap(BaseForm baseForm);
	
	public Page<Map<String, Object>> queryPagination(String sql, int pageNo, int pageSize);
	public Page<Map<String, Object>> queryOraclePagination(String sql, int pageNo, int pageSize);	

	public List<Map<String, Object>> find(String sql,Object[] params);
	public List<Map<String, Object>> find(BaseForm baseForm);
	
	public String[] findTableColumnNames(String sql);
	
	//insert update delete
	public int update(String sql,Object[] args);
	public int update(BaseForm baseForm);
	public int updateReturnId(BaseForm baseForm);
	
	public int[] batchUpdate(String[] sql);
	public int[] batchUpdate(final String sql,final List<Object[]> datas);
	
	//procedure
	public void callDbProcedure(String sql);
	public Object callDbProcedure(String sql,final Object... params);
	
	//BLOB/CLOB
	public int insertlob(String sql,final byte[] bb,final String longText,final Object... objects);
	public List<Object> getLobByte(String sql,Object[] params);
	//public void getLobStream(String sql,Object[] params,final OutputStream out);
}
