package com.user.lspolygon.root;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.annotation.DataSource;
import com.system.dao.BaseDaoImpl;
import com.system.entity.User;
import com.system.spring.UserDynamicDataSourceHolder;
import com.system.util.DESUtils;
import com.system.util.Helper;

@Service
public class LoginService {
	@Autowired
	private BaseDaoImpl baseDaoImpl;
	
	//读取用户
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_MASTER)
	public Map<String, Object> readOperator(String username,String password){
		User user = (User) baseDaoImpl.findForObject("select * from operator where no = ? and password = ?", new Object[]{username,DESUtils.getEncryptString(password)}, new User());
		return Helper.ConvertObjToMap(user);
	}
}
