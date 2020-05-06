package com.user.root.daotest.java;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.system.util.Resource;
import com.user.root.daotest.domain.User;

@Controller
@RequestMapping("/requesttest")
/**
 * controller控制器访问参考类
 * ApplicationContextAware接口可不实现
 * 通过请求URL进行映射 支持Ant风格(?、*和**字符和带{xxx}占位符的URL)
 * */
public class RequestTest implements ApplicationContextAware{
	//Logger 日志记录类 可不实现
	private static Logger log = LoggerFactory.getLogger(RequestTest.class);
	//应用上下文 用于获取xml配置文件定义的Bean
	private static ApplicationContext applicationContext = null;
	//在controller中用于直接获取request
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	//访问登陆页
	@RequestMapping(value="/user.html",method=RequestMethod.GET)
	public ModelAndView user(){
		/*
		 * 获取登陆页js
		 * */
		OutputStream outputStream = Resource.getClassPathResource("com/user/root/daotest/js/Login.js");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("jsvalue", outputStream.toString());
		modelAndView.setViewName("user/login");
		return modelAndView;
	}
	
	//执行登陆
	@RequestMapping(value="/login.html",method=RequestMethod.POST)
	@ResponseBody
	public String checkLogin(String username,String password) {
		JSONObject jsonObject = new JSONObject();
		/**
		 * 获取当前Subject
		 * 通过SecurityUtils.getSubject()我们就可以获得当前正在执行的用户,用户通常是指正在与软件交互的。
		 * 可以将Subject理解为Shiro的用户。
		 * */
		Subject currentUser = SecurityUtils.getSubject();
		//传递token给shiro的realm
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		try {
   	 		currentUser.login(token);
   	 		jsonObject.put("success", true);
   	 	} catch (AuthenticationException e) {
   	 		log.error("登陆用户名和密码错误");
   	 		jsonObject.put("success", false);
   	 	}
		return jsonObject.toJSONString();
	}
	
	/**
	 * 退出登陆
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
 	   log.error("用户访问无权限的链接时跳转此页面");
 	   return "user/unauthorizedUrl";
    }
    
	@RequestMapping(value="/handle/{no}.html",method=RequestMethod.GET)
    public void handle(@PathVariable("no") String no){
    	System.err.println("no："+no);
    }
    
    @RequestMapping(value="/handle1.html",method=RequestMethod.GET)
    public void handle1(@RequestParam("userName") String userName,
			@RequestParam(value="password",required=false) String password,@RequestParam(value="realName",required=false) String realName){
		//请求必须将参数都传递进来,除非配置required=false
		System.out.println(userName);
		System.out.println(password);
		System.out.println(realName);
	}
    
    @RequestMapping(value="/handle2.html",method=RequestMethod.GET)
    public void handle1(HttpServletRequest request,HttpServletResponse response,@RequestParam("userName") String userName){
    	
    }
    
    @ResponseBody
	@RequestMapping(value="/handler3/{imageId}.html",method=RequestMethod.GET)
    public byte[] handler3(@PathVariable("imageId") String imageId){
    	//读取一张图片 byte形式
    	/*
		List<Object> data = baseDaoImpl.getLobByte("SELECT post_attach FROM t_post WHERE post_id in (?,?)",new Object[]{7,8});
    	return (byte[])data.get(0);
    	*/
    	return null;
    }
    
    @RequestMapping(value = "/handler4/{no}")
	@ResponseBody
	public void handler4(@PathVariable("no") String no,HttpServletRequest request,HttpServletResponse response){
    	//读取一张图片 I/O流形式
    	/*
    	List<InputStream> data = new ArrayList<InputStream>();
		baseDaoImpl.getLobStream("SELECT post_attach FROM t_post WHERE post_id in (?)", new Object[]{7}, data);
		Helper.downloadFile(response, request, "壁 纸.jpg",data.get(0));
		*/
    }
    
    //映射集合类
    @RequestMapping(value = "/handle5.html", method = RequestMethod.POST,consumes = "application/json")
	@ResponseBody
	public void handle5(@RequestBody List<User> users) { //@RequestBody String body
    	/* var saveDataAry=[];  
	    var data1={"userName":"test",password:'a123',realName:'张三'};
	    var data2={"userName":"ququ",password:'abcd',realName:'李四'};
	    saveDataAry.push(data1);
	    saveDataAry.push(data2);
	    $.ajax({
            type:"POST",
            url:"/user/handle41.html",
            dataType:"json",
            contentType:"application/json;charset=utf-8",
            data:JSON.stringify(saveDataAry),
            success:function(data){ 
                 console.info(data);                   
            } 
	     }); */
		System.out.println(users.get(0).getUserName());
	}
    
    //映射实体类
    @RequestMapping(value = "/handle6")
	public String handle6(User user) {
    	System.out.println(user.getUserName());
		return "success";
	}
    
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		RequestTest.applicationContext = applicationContext;
	}
}
