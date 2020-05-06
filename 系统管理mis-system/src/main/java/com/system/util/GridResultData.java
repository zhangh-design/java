package com.system.util;

//数据查询多行时统一使用
public class GridResultData {
	public Object value;
	public long total;
	
	public Object getValue() {
		return value;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public void setValue(Object value){
		this.value = value;
	}
}
