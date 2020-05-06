package com.user.root.daotest.java;

import java.io.OutputStream;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.system.annotation.DataSource;
import com.system.spring.UserDynamicDataSourceHolder;
import com.system.util.DESUtils;
import com.system.util.Resource;

@Controller
public class UserController implements ApplicationContextAware {
	@Autowired
	private HttpServletRequest httpServletRequest;
	@Autowired
	private DaotestService daotestService;
	private static ApplicationContext applicationContext = null;
	
	@RequestMapping(value="/index.html",method=RequestMethod.GET)
	public String index(){
		return "user/login";
	}
	
	@RequestMapping(value="/user.html",method=RequestMethod.GET)
	public ModelAndView user(){
		OutputStream outputStream = Resource.getClassPathResource("com/user/root/daotest/js/Login.js");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("jsvalue", outputStream.toString());
		modelAndView.setViewName("user/login");
		
		//UserDynamicDataSourceHolder.putDataSourceName(UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER);
		System.out.println("user.html");
		//daotestService.readUser();
		return modelAndView;
	}
	
   /* 
    * 验证用户名和密码 
    */  
   @RequestMapping(value="/login.html",method=RequestMethod.POST)
   @ResponseBody
   public String checkLogin(String username,String password) {
	    //UserDynamicDataSourceHolder.putDataSourceName(UserDynamicDataSourceHolder.DATA_SOURCE_MASTER);
	   	//daotestService.readUser2();
	   	//return "";
   	 	
	   	JSONObject jsonObject = new JSONObject();
	   	password = DESUtils.getEncryptString(password);
	   	/*
	   	  * 当前Subject
	   	  * 通过SecurityUtils.getSubject()我们就可以获得当前正在执行的用户,用户通常是指正在与软件交互的。
	   	  * 可以将Subject理解为Shiro的用户。
	   	  */
   	 	Subject currentUser = SecurityUtils.getSubject();
   	 	//传递token给shiro的realm
   	 	UsernamePasswordToken token = new UsernamePasswordToken(username,password);
   	 	try {
   	 		currentUser.login(token);
   	 		jsonObject.put("success", true);
   	 	} catch (AuthenticationException e) {
   	 		System.out.println("用户名和密码错误");
   	 		jsonObject.put("success", false);
   	 	}
   	 	return jsonObject.toString();
   }
   
   /** 
    * 退出登录
    */
   @RequestMapping(value="/logout.html")
   public String logout(HttpServletRequest request) {
       SecurityUtils.getSubject().logout();
	   return "user/login";
   }
   
   /** 
    * 用户访问无权限的链接时跳转此页面
    */
   @RequestMapping(value="/unauthorizedUrl.html")
   public String unauthorizedUrl(HttpServletRequest request) {
	   System.out.println("用户访问无权限的链接时跳转此页面");
	   return "user/unauthorizedUrl";
   }

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		UserController.applicationContext = applicationContext;
	}
}
