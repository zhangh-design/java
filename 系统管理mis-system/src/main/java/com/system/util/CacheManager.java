package com.system.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存管理器
 */
public class CacheManager {

	private static Map<String,Object> cache = new ConcurrentHashMap<String,Object>(); 
	
	//获取缓存值
	public static Object getValue(Object key){
		return cache.get(key);
	}
	
	//设置或更新key
	public static void addOrUpdateCache(String key, Object value){
		cache.put(key, value);
	}
	
	//根据key来删除缓存中的一条数据
	public static void evictCache(String key){
		if(cache.containsKey(key)){
			cache.remove(key);
		}
	}
	
	//清空缓存中的记录
	public static void evictCache(){
		cache.clear();
	}
}
