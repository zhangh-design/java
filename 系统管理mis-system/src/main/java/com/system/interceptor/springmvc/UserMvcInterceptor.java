package com.system.interceptor.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
 * spring mvc请求拦截器
 * 验证token,过滤sql注入的特殊字符
 */
public class UserMvcInterceptor implements HandlerInterceptor{

	/*
	 * 在响应已经被渲染后,执行该方法
	 * */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
		System.err.println("在末端handler处理完成......");
		
	}

	/*
	 * 在请求被HandlerAdapter执行后,执行这个后置处理方法
	 * */
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView view) throws Exception {
		System.err.println("在末端handler处理完成后执行,会在afterCompletion之前执行......");
	}
	
	/*
	 * 在请求到达Handler之前,先执行这个前置处理方法。
	 * 当该方法返回false时,请求直接返回,不会传递到链中的下一个拦截器,更不会传递到处理器链末端的Handler中。
	 * 只有返回true时，请求才向链中的下一个处理节点传递。
	 * 注意：
	 * 如果启用shiro安全拦截后,preHandle方法会在shiro之前拦截,如果返回 false 则不会进入shiro认证
	 * */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		System.err.println("请求被拦截........");
		
		return true;
	}

}
