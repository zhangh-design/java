package com.system.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/*
 * AuthorizationFilter抽象类事项了javax.servlet.Filter接口，它是个过滤器。
 * 默认的roles规则需要用户(subject)同时满足user和admin才可以进入请求
 * 修改默认拦截器：  
 * /userList.html=roles["user,admin"]
 * 当用户为user或者admin其中一个角色时return true;进入请求
 * */
public class UserRolesAuthorizationFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = getSubject(request, response);
		String[] rolesArray = (String[]) mappedValue;
		
		if (rolesArray == null || rolesArray.length == 0) { //没有角色限制，有权限访问    
            return true;    
        }    
        for (int i = 0; i < rolesArray.length; i++) {
            if (subject.hasRole(rolesArray[i])) { //若当前用户是rolesArray中的任何一个，则有权限访问    
                return true;
            }    
        }
        return false;
	}

}
