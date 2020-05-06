package com.system.spring;

/*
 * Spring 动态数据源 持有者
 * **/
public class UserDynamicDataSourceHolder {
	public static final String DATA_SOURCE_MASTER = "Master"; //主数据源
	public static final String DATA_SOURCE_SLAVER = "Slaver"; //从数据源
	
	private static ThreadLocal<String> holder = new ThreadLocal<String>();

	public static void putDataSourceName(String name){
        holder.set(name);
    }
	
    public static String getDataSourceName(){
        return holder.get();
    }
    
}
