package com.system.shiro;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.system.annotation.DataSource;
import com.system.dao.BaseDaoImpl;
import com.system.spring.UserDynamicDataSourceHolder;

/*
 * Realm：域，Shiro从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager要验证用户身份，
 * 那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；
 * 也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；
 * 可以把Realm看成DataSource，即安全数据源。
 * */
public class UserShiroRealm extends AuthorizingRealm {
	@Autowired
	private BaseDaoImpl baseDaoImpl;
	
	public UserShiroRealm() {}
	public UserShiroRealm(CacheManager cacheManager) {
		super(cacheManager);
	}
	public UserShiroRealm(CredentialsMatcher matcher) {
		super(matcher);
	}
	public UserShiroRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
		super(cacheManager, matcher);
	}
	
	/*
	 * 角色
	 * */
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_MASTER)
	private void addRole(String username, SimpleAuthorizationInfo info) {
		List<Map<String, Object>> list = baseDaoImpl.find("SELECT * FROM role "
				+ "WHERE rid IN(SELECT role_id FROM oprt_role WHERE oprt_id = (SELECT rid FROM operator WHERE no = ?))", new Object[]{username});
		if(list!=null&&list.size()>0){
			for (Map<String, Object> map : list) {
				info.addRole(map.get("role_name").toString());
			}
		}
	}
	
	/*
	 * 权限
	 * */
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_MASTER)
	private void addPermission(String username,SimpleAuthorizationInfo info) {
		List<Map<String, Object>> list = baseDaoImpl.find("SELECT * FROM permission WHERE rid IN ("
				+ "SELECT permission_rid FROM role_permission WHERE role_id IN ("
				+ "SELECT rid FROM role WHERE rid IN(SELECT role_id FROM oprt_role WHERE oprt_id = (SELECT rid FROM operator WHERE no = ?))"
				+ ")"
				+ ")", new Object[]{username});
		if(list!=null&&list.size()>0){
			for (Map<String, Object> map : list) {
				info.addStringPermission(map.get("permission_name").toString());
			}
		}
	}
	
	/*
	 * 获取授权信息
	 * 当其他请求进入shiro时，shiro会调用doGetAuthorizationInfo(PrincipalCollection principals)去获取授权信息，若是没
	 * 有权限或角色，会跳转到未授权页面，若有权限或角色，shiro会放行，ok，此时进入真正的请求方法……
	 * 到此shiro的认证及授权便完成了。
	 * */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//用户名 
        String username = (String) principals.fromRealm(getName()).iterator().next();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(!StringUtils.isEmpty(username)){
        	addRole(username,info);
        	addPermission(username, info);
        	return info;
        }
        return null;
	}

	/* 
    * 登录验证  
    * login请求调用currentUser.login之后，shiro会将token传递给自定义realm,
    * 此时realm会先调用doGetAuthenticationInfo(AuthenticationToken authcToken )登录验证的方法,
    * 验证通过后会接着调用 doGetAuthorizationInfo(PrincipalCollection principals)获取角色和权限的方法（授权），最后返回视图。
    */
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_MASTER)
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//令牌——基于用户名和密码的令牌
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        //令牌中可以取出用户名
		String accountName = token.getUsername();
		if(!StringUtils.isEmpty(accountName)){
			//此处可以连接数据库查询 用户
			Map<String, Object> data = baseDaoImpl.findForMap("SELECT * FROM operator where no = ?",new Object[]{accountName});
			if(!data.isEmpty())
				return new SimpleAuthenticationInfo(data.get("no").toString(), data.get("password").toString(), getName());
		}
		return null;
	}

}
