package com.user.root.daotest.java;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.system.annotation.Log;
import com.system.dao.BaseDaoImpl;
import com.system.util.BaseForm;
import com.system.util.Helper;
import com.system.util.Page;
import com.user.root.daotest.domain.User;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Controller
@RequestMapping("/user")
public class DaotestController {
	private static Logger log = LoggerFactory.getLogger(DaotestController.class);
	private DaotestService daotestService;
	@Autowired
	private HttpServletRequest httpServletRequest;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private BaseDaoImpl baseDaoImpl;
	
	@Autowired
	public void setDaotestService(DaotestService daotestService) {
		System.out.println("=============");
		this.daotestService = daotestService;
	}
	
	@RequestMapping("/register.html")
	public String register() throws Exception{
		String aString = "";
		//jdbcTemplate.execute("call sp_insert_table('红果果','红果果')");
		//baseDaoImpl.callProcedure("","红苹果","红苹果的名称");
		/*
		ClassPathResource res = new ClassPathResource("temp.jpg");
		byte[] mockImg = null;
		try {
			mockImg = FileCopyUtils.copyToByteArray(res.getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		baseDaoImpl.insertlob("",mockImg,"烦得很记得是客户机的时刻将",1112);
		*/
		//baseDaoImpl.getLobByte("",null);
		/*List<Map<String, Object>> resultList = baseDaoImpl.find("SELECT * FROM t_forum WHERE forum_id in(?,?,?)", new Object[]{2});
		log.debug("=============debug==============");
		log.info("====== info ======");*/
		//int a = 9/0;
		//log.error("========error====");
		//daotestService.doaa();
		/*
		BaseForm baseForm = new BaseForm();
		baseForm.addSql("update t_forum set forum_name = :forum_name,forum_desc = :forum_desc where forum_id = :forum_id");
		baseForm.addParam("forum_name", "黄苹果123");
		baseForm.addParam("forum_desc", "黄苹果的名称123");
		baseForm.addParam("forum_id", "16");
		
		daotestService.doBack(baseForm);
		*/
		//daotestService.greetTo("张三");
		//daotestService.readUser();
		return httpServletRequest.getContextPath()+"/user/register";
	}
	
	@RequestMapping("/mainLayout.html")
	public ModelAndView mainLayout() throws Exception{
		ModelAndView model = new ModelAndView();
		model.addObject("path", "register.jsp");
		model.setViewName("user/mainLayout");
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value="/doimage/{imageId}.html",method=RequestMethod.GET)
	public byte[] readImage(@PathVariable("imageId") String imageId){
		//List<String> list = null;
		//list.add("111111");
		
		List<Object> data = baseDaoImpl.getLobByte("SELECT post_attach FROM t_post WHERE post_id in (?,?)",new Object[]{7,8});
		return (byte[])data.get(0);
	}
	
	@ResponseBody
	@RequestMapping(value="/image1/{imageId}",method=RequestMethod.GET,produces="application/octet-stream")
	public byte[] readImage1(@PathVariable("imageId") String imageId){
		List<Object> data = baseDaoImpl.getLobByte("SELECT post_attach FROM t_post WHERE post_id in (?,?)",new Object[]{7,8});
		return (byte[])data.get(1);
	}
	
	@ResponseBody
	@RequestMapping(value="/image2/{imageId}.html",method=RequestMethod.GET)
	public void readImage2(@PathVariable("imageId") String imageId){
		//List<Object> data = baseDaoImpl.getLobByte("SELECT post_attach FROM t_post WHERE post_id in (?,?)",new Object[]{7,8});
		try {
			OutputStream out = new FileOutputStream("D:/a.jpg");
			//baseDaoImpl.getLobStream("", null, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/image3/{imageId}",method=RequestMethod.GET)
	public void readImage3(@PathVariable("imageId") String imageId,HttpServletRequest request,HttpServletResponse response){
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			//baseDaoImpl.getLobStream("SELECT post_attach FROM t_post WHERE post_id in (?)", new Object[]{7}, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/handle41.html", method = RequestMethod.POST,consumes = "application/json")
	@ResponseBody
	public void handle41(@RequestBody List<User> users) { //@RequestBody String body
		System.out.println(users.get(0).getUserName());
	}
	
	@RequestMapping(value = "/page/{no}.html")
	@ResponseBody
	public void page(@PathVariable("no") String pageNo) { //@RequestBody String body
		Page<Map<String, Object>> page = baseDaoImpl.queryPagination("SELECT * FROM t_forum", Integer.parseInt(pageNo), 3);
		page.getRows();
	}
	
	@RequestMapping(value = "/aafile/{no}")
	@ResponseBody
	public void aafile(HttpServletRequest request,HttpServletResponse response) {
		//OutputStream out = null;
		//out = response.getOutputStream();
		List<InputStream> data = new ArrayList<InputStream>();
		baseDaoImpl.getLobStream("SELECT post_attach FROM t_post WHERE post_id in (?)", new Object[]{7}, data);
		Helper.downloadFile(response, request, "壁 纸.jpg",data.get(0));
	}
	
	@RequestMapping(value = "/userList")
	@ResponseBody
	//@RequiresRoles({"admin"})
	public void userList(HttpServletRequest request,HttpServletResponse response) {
		String a = "";
		System.err.println("-----aaaaaaaa----");
	}
	
	@RequestMapping(value = "/toDeleteUser")
	@ResponseBody
	//@RequiresRoles({"admin"})
	public void toDeleteUser(HttpServletRequest request,HttpServletResponse response) {
		String a = "";
		System.err.println("-----bbbbbbbbb----");
	}
	
	
}
