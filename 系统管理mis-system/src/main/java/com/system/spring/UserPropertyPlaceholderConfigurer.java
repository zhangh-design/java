package com.system.spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.system.util.DESUtils;

/*
 * 自定义属性加密文件
 * 适用于dbcp数据源 
 * 如果是druid 加密、解密 则参考https://github.com/alibaba/druid/wiki/使用ConfigFilter
 * 这里是用于在jdbc.properties 的数据库用户名和密码加密
 * */
public class UserPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {	
	private String[] encryptPropNames ={"userName","password"};
	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {		
		if(isEncryptProp(propertyName)){
			String decryptValue = DESUtils.getDecryptString(propertyValue);
			System.out.println(decryptValue);
			return decryptValue;
		}else{
			return propertyValue;
		}
	}
	
	/**
	 * 判断是否是加密的属性
	 * @param propertyName
	 * @return
	 */
	private boolean isEncryptProp(String propertyName){
		for(String encryptPropName:encryptPropNames){
			if(encryptPropName.equals(propertyName)){
				return true;
			}
		}
		return false;
	}
}
