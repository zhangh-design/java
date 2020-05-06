package com.system.util;

import com.alibaba.fastjson.JSONObject;

//ajax访问统一返回类
public class AjaxJSONObject extends JSONObject{
	private boolean success;
	private Object data;
	private String message;
	private int total;
	
	public void setSuccess(boolean success) {
		this.success = success;
		this.put("success", this.success);
	}
	public void setData(Object data) {
		this.data = data;
		this.put("data", data);
	}
	public void setMessage(String message) {
		this.message = message;
		this.put("message", message);
	}
	public void setTotal(int total) {
		this.total = total;
		this.put("total", total);
	}
}
