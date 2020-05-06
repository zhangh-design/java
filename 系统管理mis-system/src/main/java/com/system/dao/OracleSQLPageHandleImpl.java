package com.system.dao;

import org.springframework.stereotype.Repository;

import com.taijue.system.dao.interf.SQLPageHandle;

/**
 * oracle数据库的分页实现
 */
@Repository
public class OracleSQLPageHandleImpl implements SQLPageHandle {

	@Override
	public String handlerPagingSQL(String oldSql, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer();
		if(pageSize > 0){
			if(pageNo <= 0){
				pageNo = 1;
			}
			int pageList = (pageNo-1)*pageSize;
			sql.append("SELECT * FROM (SELECT ROWNUM AS rowno, t.* FROM ("+oldSql+") t WHERE ROWNUM <= "+(pageList+pageSize)+") table_alias WHERE table_alias.rowno > "+pageList);
		}
		return sql.toString();
	}

}
