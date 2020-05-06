package com.user.lspolygon.root;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.util.AjaxJSONObject;
import com.system.util.DESUtils;

@Controller
public class LoginController {
	@Autowired
	private HttpServletRequest httpServletRequest;
	@Autowired
	private LoginService loginService;
	
	/* 
	 * 验证用户名和密码 
	 */  
	@RequestMapping(value="/login.html",method=RequestMethod.POST)
	@ResponseBody
	public AjaxJSONObject checkLogin(String username,String password) {
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		/*
	   	 * 当前Subject
	   	 * 通过SecurityUtils.getSubject()我们就可以获得当前正在执行的用户,用户通常是指正在与软件交互的。
	   	 * 可以将Subject理解为Shiro的用户。
	   	 */
   	 	Subject currentUser = SecurityUtils.getSubject();
   	 	
   	 	//传递token给shiro的realm
   	 	UsernamePasswordToken token = new UsernamePasswordToken(username,DESUtils.getEncryptString(password));
   	 	try {
   	 		currentUser.login(token);
   	 		Map<String, Object> data = loginService.readOperator(username, password);
   	 		ajaxJSONObject.setSuccess(true);
   	 		ajaxJSONObject.setData(data);
   	 	} catch (AuthenticationException e) {
   	 		ajaxJSONObject.setSuccess(false);
   	 		ajaxJSONObject.setMessage("用户名和密码错误");
   	 	}
   	 	return ajaxJSONObject;
   }
   
   /** 
    * 用户访问无权限的链接时跳转此页面
    */
   @RequestMapping(value="/unauthorizedUrl.html")
   public String unauthorizedUrl(HttpServletRequest request) {
	   System.out.println("用户访问无权限的链接时跳转此页面");
	   return "user/unauthorizedUrl";
   }
}
