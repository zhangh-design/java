package com.user.lspolygon.module.show;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.annotation.DataSource;
import com.system.dao.BaseDaoImpl;
import com.system.spring.UserDynamicDataSourceHolder;

@Service
public class PopWindowService {
	@Autowired
	private BaseDaoImpl baseDaoImpl;
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readTopTitle(int rid){
		String sqlString = "SELECT * FROM document_manage where poygon_id ="+rid;
		List<Map<String, Object>> data = baseDaoImpl.find(sqlString, null);
		if(data.size()==0){
			return null;
		}
		String safesqlString = "SELECT * FROM d_safe_condition where p_id ="+data.get(0).get("rid");
		List<Map<String, Object>> safedata = baseDaoImpl.find(safesqlString, null);
		if(safedata.size()==0){
			return null;
		}
		return safedata;
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readCenterInfo(int rid){
		String sqlString = "SELECT * FROM document_manage where poygon_id ="+rid;
		List<Map<String, Object>> data = baseDaoImpl.find(sqlString, null);
		if(data.size()==0){
			return null;
		}
		String infosqlString = "SELECT * FROM d_contact_info where p_id ="+data.get(0).get("rid");
		List<Map<String, Object>> infodata = baseDaoImpl.find(infosqlString, null);
		if(infodata.size()==0){
			return null;
		}
		return infodata;
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readBottomDown(int rid){
		String sqlString = "SELECT * FROM document_manage where poygon_id ="+rid;
		List<Map<String, Object>> data = baseDaoImpl.find(sqlString, null);
		if(data.size()==0){
			return null;
		}
		String downsqlString = "SELECT * FROM d_planning where p_id ="+data.get(0).get("rid");
		List<Map<String, Object>> downdata = baseDaoImpl.find(downsqlString, null);
		if(downdata.size()==0){
			return null;
		}
		return downdata;
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readCenterPic(int rid){
		String sqlString = "SELECT * FROM document_manage where poygon_id ="+rid;
		List<Map<String, Object>> data = baseDaoImpl.find(sqlString, null);
		if(data.size()==0){
			return null;
		}
		String picsqlString = "SELECT * FROM d_local_realimg where p_id ="+data.get(0).get("rid");
		List<Map<String, Object>> picdata = baseDaoImpl.find(picsqlString, null);
		if(picdata.size()==0){
			return null;
		}
		return picdata;
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readTopName(int rid){
		String sqlString = "SELECT * FROM document_manage where poygon_id ="+rid;
		List<Map<String, Object>> data = baseDaoImpl.find(sqlString, null);
		return data;
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readAll(int rid){
		String sqlString = "SELECT * FROM document_manage where poygon_id ="+rid;
		List<Map<String, Object>> data = baseDaoImpl.find(sqlString, null);
		return data;
	}
	
}
