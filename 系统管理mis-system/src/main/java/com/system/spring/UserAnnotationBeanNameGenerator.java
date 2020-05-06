package com.system.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/*
 * 防止项目中类同名不同包,导致注解命名冲突
 * 注解默认的名是类名,此处自定义修改为 包名+类名
 * @Service('MyClass')=>@Service('user.mypackage.MyClass')
 * */
public class UserAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator{
	
	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String className = definition.getBeanClassName();
		return className;
	}
}
