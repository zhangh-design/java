package com.system.util;

import java.util.HashMap;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

//自定义Map 用于组合jdbc操作
public class BaseForm extends HashMap<String, Object>{
	private MapSqlParameterSource paramMap;
	//用于映射对象的实体类
	private Class clazz;
	//以Bean对象的形式 用于执行update delete insert
	private Object object;
	
	private static final String TABLE = "sql";
	private static final String ORDER = "order";
	
	public BaseForm(){
		super();
	}
	
	//添加sql语句
	public void addSql(String sql){
		this.put(TABLE, sql);
	}
	
	//添加实体类
	//addEntity(Forum.class);
	public <T> void addEntity(Class<T> clazz){
		this.put("clazz", clazz);
	}
	//添加类路径通过反射查找到类
	//addEntity("com.user.root.daotest.domain.Forum");
	public void addEntity(String classPath){
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = null;
		try {
			clazz = loader.loadClass(classPath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.put("clazz", clazz);
	}
	
	//添加Bean对象
	public void addObject(Object object){
		this.put("object", object);
	}
	
	//添加查询条件
	public void addParam(String key,Object value){
		if(paramMap == null){
			paramMap = new MapSqlParameterSource();
			this.put("paramMap", paramMap);
		}
		((MapSqlParameterSource) super.get("paramMap")).addValue(key, value);
	}
	
	//添加排序条件
	public void addOrder(String key,String direction){
		this.put(ORDER, direction);
	}
	
	//获取设置的sql信息
	public String get(String key){
		if(!super.containsKey(key)) return null;
		return (String) super.get(key);
	}
	
	//获取sql命名参数
	public MapSqlParameterSource getMapSqlParam(){
		if(!super.containsKey("paramMap")) return null;
		return (MapSqlParameterSource) super.get("paramMap");
	}
	
	//获取实体类
	public Class getClazz() {
		if(!super.containsKey("clazz")) return null;
		return (Class)super.get("clazz");
	}
	
	//获取Bean对象
	public Object getObject(){
		if(!super.containsKey("object")) return null;
		return super.get("object");
	}
}
