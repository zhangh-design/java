package com.system.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

//登陆用户Bean
public class User implements RowMapper<User>,Serializable{
	private static final long serialVersionUID = 1L;
	
	private int rid;
	private String no;
	private String name;
	private String address;
	private String phone;
	private int sex;
	
	public void setRid(int rid) {
		this.rid = rid;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setRid(Integer.parseInt(rs.getString("rid")));
		user.setNo(rs.getString("no"));
		user.setName(rs.getString("name"));
		user.setAddress(rs.getString("address"));
		user.setPhone(rs.getString("phone"));
		user.setSex(Integer.parseInt(rs.getString("sex")));
		return user;
	}
}
