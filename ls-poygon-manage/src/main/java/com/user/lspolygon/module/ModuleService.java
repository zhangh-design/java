package com.user.lspolygon.module;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.annotation.DataSource;
import com.system.dao.BaseDaoImpl;
import com.system.entity.User;
import com.system.spring.UserDynamicDataSourceHolder;
import com.system.util.Helper;

@Service
public class ModuleService {
	@Autowired
	private BaseDaoImpl baseDaoImpl;
	
	//读取用户信息
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_MASTER)
	public Map<String,Object> readOperator(String oprt_no) {
		String sql = "SELECT * FROM department WHERE rid IN("
				+ "SELECT dept_id FROM oprt_dept WHERE oprt_id IN("
				+ "SELECT rid FROM operator WHERE NO = ?"
				+ ")"
				+ ")";
		User user=(User) baseDaoImpl.findForObject("select * from operator where no = ?",new Object[]{oprt_no}, new User());
		Map<String, Object> deptmentMap = baseDaoImpl.findForMap(sql,new Object[]{oprt_no});
		
		Map<String,Object> resultMap = Helper.ConvertObjToMap(user);
		resultMap.put("dept", deptmentMap.get("name"));
		resultMap.put("dept_no", deptmentMap.get("no"));
		return resultMap;
	}
	
	//读取用户对应的模块
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_MASTER)
	public List<Map<String, Object>> readModule(String oprt_no) {
		String sql = "SELECT * FROM module WHERE rid IN("
				+ "SELECT DISTINCT module_id FROM role_module WHERE role_id IN ("
				+ "SELECT rid FROM role WHERE rid IN ("
				+ "SELECT role_id FROM oprt_role WHERE oprt_id = (SELECT rid FROM `operator` WHERE no = ?)"
				+ ")"
				+ ")"
				+ ")";
		List<Map<String, Object>> data = baseDaoImpl.find(sql, new Object[]{oprt_no});
		return data;
	}
}
