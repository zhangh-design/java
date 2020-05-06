package com.user.root.daotest.domain;

import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;

public class Forum extends SerializableSerializer{
	private static final long serialVersionUID = 1L;
	private int forum_id; 
	private String forum_name;
	private String forum_desc;
	
	public int getForum_id() {
		return forum_id;
	}
	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}
	public String getForum_name() {
		return forum_name;
	}
	public void setForum_name(String forum_name) {
		this.forum_name = forum_name;
	}
	public String getForum_desc() {
		return forum_desc;
	}
	public void setForum_desc(String forum_desc) {
		this.forum_desc = forum_desc;
	}
	
	public String toString(){
		return "主题ID："+forum_id+" 主题名称："+forum_name+" 主题描述： "+forum_desc;
	}
}
