package com.user.lspolygon.module.show;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.annotation.DataSource;
import com.system.dao.BaseDaoImpl;
import com.system.spring.UserDynamicDataSourceHolder;
import com.system.util.AjaxJSONObject;
import com.system.util.Helper;

@Controller
@RequestMapping("/read/lspolygon/module/popwindowController")
public class PopWindowController {
	@Autowired
	private PopWindowService popwindowService;
	@Autowired
	private BaseDaoImpl baseDaoImpl;
	//读取infowindow title
	@RequestMapping(value="/readTopTitle",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readTopTitle(int rid){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		if(!StringUtils.isEmpty(rid)){
		List<Map<String, Object>> dataList = popwindowService.readTopTitle(rid);
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(dataList);
		}
		return ajaxJSONObject;
	}
	//读取infowindow info
		@RequestMapping(value="/readCenterInfo",method=RequestMethod.GET)
		@ResponseBody
		public AjaxJSONObject readPoygonType(int rid){
			AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
			if(!StringUtils.isEmpty(rid)){
			List<Map<String, Object>> dataList = popwindowService.readCenterInfo(rid);
			ajaxJSONObject.setSuccess(true);
			ajaxJSONObject.setData(dataList);
			}
			return ajaxJSONObject;
		}
		//读取infowindow download
		@RequestMapping(value="/readBottomDown",method=RequestMethod.GET)
		@ResponseBody
		public AjaxJSONObject readBottomDown(int rid){
			AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
			if(!StringUtils.isEmpty(rid)){
			List<Map<String, Object>> dataList = popwindowService.readBottomDown(rid);
			ajaxJSONObject.setSuccess(true);
			ajaxJSONObject.setData(dataList);
			}
			return ajaxJSONObject;
		}
		//读取infowindow pic
		@RequestMapping(value="/readCenterPic",method=RequestMethod.GET)
		@ResponseBody
		public AjaxJSONObject readCenterPic(int rid){
			AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
			if(!StringUtils.isEmpty(rid)){
			List<Map<String, Object>> dataList = popwindowService.readCenterPic(rid);
			ajaxJSONObject.setSuccess(true);
			ajaxJSONObject.setData(dataList);
			}
			return ajaxJSONObject;
		}
		//读取infowindow pic
		@RequestMapping(value="/readTopName",method=RequestMethod.GET)
		@ResponseBody
		public AjaxJSONObject readTopName(int rid){
			AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
			if(!StringUtils.isEmpty(rid)){
			List<Map<String, Object>> dataList = popwindowService.readTopName(rid);
			ajaxJSONObject.setSuccess(true);
			ajaxJSONObject.setData(dataList);
			}
			return ajaxJSONObject;
		}
		//读取infowindow pic
		@RequestMapping(value="/showPicture",method=RequestMethod.GET,produces="application/octet-stream")
		@ResponseBody
		public void showPicture(String src,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			 FileInputStream inputStream = new FileInputStream(src);  
		        int i = inputStream.available();  
		        //byte数组用于存放图片字节数据  
		        byte[] buff = new byte[i];  
		        inputStream.read(buff);  
		        //记得关闭输入流  
		        inputStream.close();  
		        //设置发送到客户端的响应内容类型  
		        response.setContentType("image/*");  
		        OutputStream out = response.getOutputStream();  
		        out.write(buff);  
		        //关闭响应输出流  
		        out.close();
		}	
		//读取infowindow pic
				@RequestMapping(value="/downLoad",method=RequestMethod.GET,produces="application/octet-stream")
				@ResponseBody
				public void downLoad(String src,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
					 FileInputStream inputStream = new FileInputStream(src);  
				        int i = inputStream.available();  
				        //byte数组用于存放图片字节数据  
				        byte[] buff = new byte[i];  
				        inputStream.read(buff);  
				        //记得关闭输入流  
				        inputStream.close();  
				        //设置发送到客户端的响应内容类型  
				        response.setContentType("a/*");  
				        OutputStream out = response.getOutputStream();  
				        out.write(buff);  
				        //关闭响应输出流  
				        out.close();
				}	
				//读取infowindow all
		@RequestMapping(value="/readAll",method=RequestMethod.GET)
		@ResponseBody
		public AjaxJSONObject readAll(int rid){
			AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
			if(!StringUtils.isEmpty(rid)){
			List<Map<String, Object>> dataList = popwindowService.readAll(rid);
			ajaxJSONObject.setSuccess(true);
			ajaxJSONObject.setData(dataList);
			}
			return ajaxJSONObject;
		}
}
