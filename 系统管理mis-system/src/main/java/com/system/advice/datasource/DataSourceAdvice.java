package com.system.advice.datasource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.system.annotation.DataSource;
import com.system.spring.UserDynamicDataSourceHolder;

/*
 * 控制器增强(动态数据源切换)切面类
 * 方法前置增强
 * 自定义注解@DataSource 用于方法级
 * @Order(value=0) //设置优先级为0最高，否则获取数据源的方法会优先于增强执行
 * */
@Aspect
@Order(value=0)
@Repository
public class DataSourceAdvice {
	
	@Before("@annotation(com.system.annotation.DataSource)")
	public void doBeforeChangeDataSourceHandler(JoinPoint jp){
		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
		Method method = methodSignature.getMethod();
		DataSource dataSourceNote = AnnotationUtils.findAnnotation(method, DataSource.class);
		
		String strNote = dataSourceNote.value();	//注解传入的备注 @DataSource(value="注解备注")
		if(!StringUtils.isEmpty(strNote)){
			//动态设置数据源
			UserDynamicDataSourceHolder.putDataSourceName(strNote);
		}
	}
	
	@After("@annotation(com.system.annotation.DataSource)")  
    public void after(){  
		//调用后清除数据源，系统然后就调用默认数据源了  
		UserDynamicDataSourceHolder.putDataSourceName(UserDynamicDataSourceHolder.DATA_SOURCE_MASTER);
    }
}
