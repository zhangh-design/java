package com.system.dao.interf;

/*
 * sql分页处理接口
 * 
 * */
public interface SQLPageHandle {
	/*
	 * 将传入的SQL做分页处理
	 * oldSql 原SQL
	 * pageNo 第几页，用来计算first 这个值由（pageNo-1）*pageSize
	 * pageSize 每页数量
	 * */
	public String handlerPagingSQL(String oldSql, int pageNo, int pageSize);
}
