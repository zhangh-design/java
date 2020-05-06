package com.system.dao;

import org.springframework.stereotype.Repository;

import com.system.dao.interf.SQLPageHandle;

/**
 * mysql数据库的分页实现
 * 
 * */
@Repository
public class MysqlSQLPageHandleImpl implements SQLPageHandle{

	@Override
	public String handlerPagingSQL(String oldSql, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer(oldSql);
		if(pageSize > 0){
			int pageList = (pageNo-1)*pageSize;
			if(pageList <= 0){
				sql.append(" limit ").append(pageSize);
			}else{
				sql.append(" limit ").append(pageList).append(",").append(pageSize);
			}
		}
		return sql.toString();
	}
}
