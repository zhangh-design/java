package com.system.advice.exception;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/*
 * 控制器增强(全局类),为所有的控制器添加处理异常
 * logback结合spring记录堆栈异常
 * 这里是将堆栈异常记录到计算机硬盘文件
 * 控制台显示的日志查看是通过log4j配置console来显示的
 * */
@ControllerAdvice
public class GlobalExceptionAdvice {
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);
	
	@ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        logger.error(ExceptionUtils.getFullStackTrace(e));  //记录错误信息
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = "服务器出错";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", msg);
        return jsonObject;
    }
}
