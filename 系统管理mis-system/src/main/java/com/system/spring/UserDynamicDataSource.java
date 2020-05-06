package com.system.spring;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/*
 * Spring 动态数据源切换
 * 如果在一个拥有事务的service方法中同时使用到多个数据源则会失败,
 * 因为这个时候spring已经打开了一个事务,他会阻止你切换,所以你应在这之前切换,
 * 然后进入service方法,这样spring又给你新切换的数据源加上事务了。
 * **/
public class UserDynamicDataSource extends AbstractRoutingDataSource{
	
	@Override
	protected Object determineCurrentLookupKey() {
		return UserDynamicDataSourceHolder.getDataSourceName();
	}
}
