package com.system.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class Deptment implements RowMapper<User>,Serializable{

	@Override
	public User mapRow(ResultSet arg0, int arg1) throws SQLException {
		
		
		return null;
	}
}
