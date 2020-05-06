package com.system.advice.Log;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.system.annotation.Log;
import com.system.dao.BaseDaoImpl;
import com.system.util.DateUtils;

/*
 * 控制器增强(日志记录)切面类
 * 方法后置增强
 * 自定义注解@Log 用于方法级
 * @Aspect 通过该注解将LogHandlerAdvice标识为一个切面
 * */
@Aspect
@Component
public class LogHandlerAdvice {
	@Autowired
	private BaseDaoImpl baseDaoImpl;
	
	/*
	 * 任何使用自定义@Log注解的方法
	 * 在方法调用执行完后执行增强,建议在service类中使用 
	 * 后置增强
	 * @After("@annotation(com.system.annotation.Log)") 定义切点和增强类型
	 * */
	@After("@annotation(com.system.annotation.Log)")
	public void doAfterLogHandler(JoinPoint jp) {	//增强的横切逻辑
		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
		Method method = methodSignature.getMethod();
		Log logNote = AnnotationUtils.findAnnotation(method, Log.class);
		
		String clazz = jp.getTarget().getClass().getName();	//目标类,并不是代理类
		String strMethod = method.getName();				//执行的方法
		String strNote = logNote.value();					//注解传入的备注 @Log(value="注解备注")
		String doTimeString = DateUtils.formatDateTime(new Date());	//方法执行的时间
		//将Log日志保存入表
		//System.out.println("class："+clazz+" method: "+strMethod+" dotime："+DateUtils.formatDateTime(new Date())+"  "+strNote);
	}
}
