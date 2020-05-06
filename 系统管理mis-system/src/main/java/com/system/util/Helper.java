package com.system.util;

import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.reflect.Field;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.bitwalker.useragentutils.UserAgent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

//静态帮助类 用于静态导入
public class Helper {
	private static Logger logger = LoggerFactory.getLogger(Helper.class);
	/*
	 * 修改mysql like ?占位符
	 * like '%?%'=>like concat('%',?,'%')
	 */
	public static String mySqlLike(String str){
		if(str.indexOf("'%?%'") == -1) return str;
		String sql = str.replace("'%?%'", "concat('%',?,'%')");
		return sql;
	}
	
	//正则匹配圆括号中的内容
	public static List<String> getTeacherList(String str){
		List<String> ls=new ArrayList<String>();
        Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
        Matcher matcher = pattern.matcher(str);
        while(matcher.find())
            ls.add(matcher.group());
        return ls;
	}
	
	//随机数
    public static String createRandom(){
        StringBuffer sb=new StringBuffer();
        //追加  当前简写年月日
        sb.append(DateUtils.getSimpleYearAndMonthAndDay());
        //追加 当前时分秒微妙
        sb.append(DateUtils.getHourAndMinAndSecAndMil());
        //生成1000~9999之间的随机数
        sb.append(Math.round(Math.random()*(9999-1000)+1000));
        return sb.toString();
    }

    //32位uuid (07d647454e274af7b9c155b2cb42c8db)
    public static String createUuid(){
        String id=java.util.UUID.randomUUID().toString();
        return id.replace("-", "");
    }
    
    //对象转Map
    public static Map<String,Object> ConvertObjToMap(Object obj){
    	Map<String,Object> reMap = new HashMap<String,Object>();
    	if (obj == null) 
    	   return null;
    	Field[] fields = obj.getClass().getDeclaredFields();
    	try {
    	   for(int i=0;i<fields.length;i++){
	    	    try {
	    	    	Field f = obj.getClass().getDeclaredField(fields[i].getName());
	    	    	f.setAccessible(true);
	    	    	Object o = f.get(obj);
	    	    	reMap.put(fields[i].getName(), o);
	    	    } catch (NoSuchFieldException e) {
	    	    	// TODO Auto-generated catch block
	    	    	e.printStackTrace();
	    	    } catch (IllegalArgumentException e) {
	    	    	// TODO Auto-generated catch block
	    	    	e.printStackTrace();
	    	    } catch (IllegalAccessException e) {
	    	    	// TODO Auto-generated catch block
	    	    	e.printStackTrace();
	    	    }
    	   }
    	 } catch (SecurityException e) {
    	   // TODO Auto-generated catch block
    	   e.printStackTrace();
    	} 
    	return reMap;
    }
    
    /*
     * 判断文件类型 适用于web下载
     * 根据后缀名 简单判断文件类型 ,如果返回null,则都指定application/octet-stream  二进制流数据（如常见的文件下载）
     * gif -> image/gif
     * png -> image/png
     * aa.doc -> null
     * aa.xls -> null
     * ...
     */
    public static String getContentType(String fileName) {
		String type = URLConnection.guessContentTypeFromName(fileName);
    	if(type==null) type="application/octet-stream";
    	return type;
	}
    
    /*
     * 获取浏览器请求类型 chrome ie firefox...
     * User Agent中文名为用户代理，简称 UA，它是一个特殊字符串头，使得服务器能够识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器
     * 渲染引擎、浏览器语言、浏览器插件等。
     * */
    public static String getBrowser(HttpServletRequest request){
    	//获得浏览器请求头中的User-Agent
    	String userAgentString = request.getHeader("User-Agent");
    	String browser = UserAgent.parseUserAgentString(userAgentString).getBrowser().getGroup().getName();
    	return browser;
    }
    
    /*
     * 下载资源
     * 下载资源不能以ajax的形式请求,需要以超链接形式单独发起get请求
     * 参数说明：
     * fileName 下载文件名 必须带有文件后缀
     * inputStream 资源的输入流
     * 示例：
     * List<InputStream> data = new ArrayList<InputStream>();
     * baseDaoImpl.getLobStream("SELECT post_attach FROM t_post WHERE post_id in (?)", new Object[]{7}, data);
     * Helper.downloadFile(response, request, "1.jpg",data.get(0));
     * */
    public static void downloadFile(HttpServletResponse response,HttpServletRequest request,String fileName,InputStream inputStream){
    	try {
	    	//设置响应内容类型
	    	response.setContentType(getContentType(fileName));
	    	//文件名编码，解决乱码问题
	    	String encodedFileName = null;
	    	String browser = getBrowser(request);
	    	if(browser.equals("Chrome") || browser.equals("Internet Exploer") || browser.equals("Safari") || browser.equals("Mozilla")) {
				encodedFileName = URLEncoder.encode(fileName,"utf-8").replaceAll("\\+", "%20");
	        } else {
	            encodedFileName = MimeUtility.encodeWord(fileName);	//FireFox
	        }
	    	//设置Content-Disposition响应头，一方面可以指定下载的文件名，另一方面可以引导浏览器弹出文件下载窗口
	    	response.setHeader("Content-Disposition", "attachment;fileName=\"" + encodedFileName + "\"");
	    	//文件下载
	    	//File file = new File("d:/泰珏电力危控区管控平台--操作手册.doc"); 
	        //InputStream in = new BufferedInputStream(inputStream);
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception e) {
			logger.error(DateUtils.formatDateTime(new Date())+" "+fileName+" 下载附件出现异常! "+e.getMessage());
			e.printStackTrace();
		}
    }
    
}
