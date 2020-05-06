package com.system.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.support.ServletContextResource;

/*
 * Spring 访问资源
 * classPath filePath jar web-inf根目录
 * */
public class Resource {
	private static Logger logger = LoggerFactory.getLogger(Resource.class);
	
	/*
	 * 使用类路径方式加载资源
	 * 示例：
	 * Resource.getClassPathResource("com/user/root/daotest/js/Login.js");
	 */
	public static OutputStream getClassPathResource(String path){
		ClassPathResource resource = null;
		InputStream input = null;
		ByteArrayOutputStream baos = null;
		//ServletContextResource();
		try {
			resource = new ClassPathResource(path);
			if(resource.exists() && !resource.isOpen()){
				input = resource.getInputStream();
				baos = new ByteArrayOutputStream();
				FileCopyUtils.copy(input, baos);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(path+" 资源读取出现异常 "+e.getMessage());
		}
		//baos.toString();
		return baos;
	}
	
	/*
	 * 使用文件系统路径加载资源
	 * 示例：
	 * Resource.getFilePathResource("d:\\sql.txt");
	 */
	public static OutputStream getFilePathResource(String path){
		WritableResource resource = null;
		InputStream input = null;
		ByteArrayOutputStream baos = null;
		try {
			resource = new PathResource(path);
			if(resource.exists() && !resource.isOpen()){
				input = resource.getInputStream();
				baos = new ByteArrayOutputStream();
				FileCopyUtils.copy(input, baos);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(path+" 资源读取出现异常 "+e.getMessage());
		}
		return baos;
	}
	
	/*
	 * 使用URL路径加载资源
	 * 示例：
	 * Resource.getURLPathResource("http://127.0.0.1:7001/JSLoad.jsp");
	 */
	public static OutputStream getURLPathResource(String path){
		UrlResource resource = null;
		InputStream input = null;
		ByteArrayOutputStream baos = null;
		try {
			resource = new UrlResource(path);
			if(resource.exists() && !resource.isOpen()){
				baos = new ByteArrayOutputStream();
				input = resource.getInputStream();
				FileCopyUtils.copy(input, baos);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(path+" 资源读取出现异常 "+e.getMessage());
		}
		return baos;
	}
	
	/*
	 * 在web应用中,用户还可以通过ServletContextResource以相对于web应用根目录的方式访问文件资源
	 * 示例：
	 * @Autowired
	 * private HttpServletRequest httpServletRequest;
	 * Resource.getServletContextResource(httpServletRequest.getServletContext(), "/WEB-INF/classes/log4j.properties");
	 */
	public static OutputStream getServletContextResource(ServletContext servletContext, String path){
		org.springframework.core.io.Resource resource = null;
		InputStream input = null;
		ByteArrayOutputStream baos = null;
		try {
			resource = new ServletContextResource(servletContext,path);
			if(resource.exists() && !resource.isOpen()){
				baos = new ByteArrayOutputStream();
				input = resource.getInputStream();
				FileCopyUtils.copy(input, baos);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(path+" 资源读取出现异常 "+e.getMessage());
		}
		return baos;
	}
}
