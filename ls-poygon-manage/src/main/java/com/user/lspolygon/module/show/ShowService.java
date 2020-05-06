package com.user.lspolygon.module.show;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.system.annotation.DataSource;
import com.system.dao.BaseDaoImpl;
import com.system.spring.UserDynamicDataSourceHolder;
import com.system.util.Page;

@Service
public class ShowService {
	@Autowired
	private BaseDaoImpl baseDaoImpl;
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readPoygonType(){
		String tableName = httpServletRequest.getParameter("tableName");
		
		String sqlString = "SELECT * FROM "+tableName;
		List<Map<String, Object>> data = baseDaoImpl.find(sqlString, null);
		return data;
	}
	
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readPoygonTree(){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> data = baseDaoImpl.find("SELECT CONCAT('1-',rid) AS text_id,rid,name as text FROM v_poygon_type", null);
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", data.get(i).get("text_id"));
			row.put("text", data.get(i).get("text"));
			
			List<Map<String, Object>> children = baseDaoImpl.find("SELECT rid as id,name as text,(x1+x2)/2 as center_x,(y1+y2)/2 as center_y,(SELECT zindex FROM sys_map_object WHERE obj_name = 'poygon' ) AS best_level FROM poygon WHERE TYPE = ?", new Object[]{data.get(i).get("rid")});
			if(children.size()>0){
				row.put("children", children);
				result.add(row);
			}
		}
		return result;
	}

	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readLineTree(){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> data = baseDaoImpl.find("SELECT CONCAT('1-',rid) AS text_id,rid,name as text FROM line", null);
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", data.get(i).get("text_id"));
			row.put("text", data.get(i).get("text"));
			List<Map<String, Object>> children = baseDaoImpl.find("SELECT b.rid as id,b.name as text,(x1+x2)/2 as center_x,(y1+y2)/2 as center_y,(SELECT zindex FROM sys_map_object WHERE obj_name = 'poygon' ) AS best_level FROM line_poygon a,poygon b WHERE a.poygon_id = b.rid and a.line_id =  ?", new Object[]{data.get(i).get("rid")});
			if(children.size()>0){
				row.put("children", children);
				result.add(row);
			}
		}
		return result;
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readTranTree(){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> data = baseDaoImpl.find("SELECT CONCAT('1-',rid) AS text_id,rid,name as text FROM transformer", null);
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", data.get(i).get("text_id"));
			row.put("text", data.get(i).get("text"));
			List<Map<String, Object>> children = baseDaoImpl.find("SELECT b.rid as id,b.name as text,(x1+x2)/2 as center_x,(y1+y2)/2 as center_y,(SELECT zindex FROM sys_map_object WHERE obj_name = 'poygon' ) AS best_level FROM transformer_poygon a,poygon b WHERE a.poygon_id = b.rid and a.transformer_id =  ?", new Object[]{data.get(i).get("rid")});
			if(children.size()>0){
				row.put("children", children);
				result.add(row);
			}
		}
		return result;
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readPoygonTreebill(int rid){
		String dept = "空";
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		String sqlString = "SELECT * FROM poygon where rid ="+rid;
		List<Map<String, Object>> data = baseDaoImpl.find(sqlString, null);
		Map<String, Object> row = new HashMap<String, Object>();
		String typesqlString = "SELECT * FROM poygon_type where rid ="+data.get(0).get("type");
		List<Map<String, Object>> typedata = baseDaoImpl.find(typesqlString, null);
		if(data.get(0).get("dept_id")!=null){
			 dept = data.get(0).get("dept_id").toString();
		}
		row.put("name", data.get(0).get("name"));
		row.put("type", typedata.get(0).get("name"));
		row.put("level", data.get(0).get("class"));
		row.put("dept", dept);
		result.add(row);
		/*
		List<Map<String, Object>> data = baseDaoImpl.find("SELECT CONCAT('1-',rid) AS text_id,rid,name as text FROM v_poygon_type", null);
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", data.get(i).get("text_id"));
			row.put("text", data.get(i).get("text"));
			
			List<Map<String, Object>> children = baseDaoImpl.find("SELECT rid as id,name as text FROM poygon WHERE TYPE = ?", new Object[]{data.get(i).get("rid")});
			if(children.size()>0){
				row.put("children", children);
				result.add(row);
			}
		}*/
		return result;
	}

	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public Page<Map<String, Object>> readLineGrid(int page,int rows){
		String name = httpServletRequest.getParameter("name");
		
		String sqlString = "SELECT * FROM line";
		if(!StringUtils.isEmpty(name)){
			sqlString = "SELECT * FROM line WHERE name like '%"+name+"%'";
		}
		return baseDaoImpl.queryPagination(sqlString, page, rows);
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public Page<Map<String, Object>> readRiskGrid(int page,int rows){
		String name = httpServletRequest.getParameter("name");
		
		String sqlString = "SELECT * FROM poygon";
		if(!StringUtils.isEmpty(name)){
			sqlString = "SELECT * FROM poygon WHERE name like '%"+name+"%'";
		}
		return baseDaoImpl.queryPagination(sqlString, page, rows);
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public Page<Map<String, Object>> readTranGrid(int page,int rows){
		String name = httpServletRequest.getParameter("name");
		
		String sqlString = "SELECT * FROM transformer";
		if(!StringUtils.isEmpty(name)){
			sqlString = "SELECT * FROM transformer WHERE name like '%"+name+"%'";
		}
		return baseDaoImpl.queryPagination(sqlString, page, rows);
	}

	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Map<String, Object>> readLineType(){
		String tableName = httpServletRequest.getParameter("tableName");
		
		String sqlString = "SELECT * FROM "+tableName;
		List<Map<String, Object>> data = baseDaoImpl.find(sqlString, null);
		return data;
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_MASTER)
	public List<Map<String, Object>> readDepartmentTree(){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		int prid = Integer.parseInt(httpServletRequest.getParameter("parentid"));
		int prdd = prid+1;
		List<Map<String, Object>> data = baseDaoImpl.find("SELECT CONCAT('"+prdd+"-',rid) AS text_id,rid,NAME AS TEXT FROM mis_sys.department WHERE prid = ?", new Object[]{prid});
		
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", data.get(i).get("text_id"));
			row.put("text", data.get(i).get("text"));
			int prddd = prdd+1;
			List<Map<String, Object>> children = baseDaoImpl.find("SELECT CONCAT('"+prddd+"-',rid) AS text_id,rid,NAME AS TEXT FROM mis_sys.department WHERE prid = ?", new Object[]{data.get(i).get("rid")});
			if(children.size()>0){
				//row.put("children", children);
				result.add(row);
			}else {
				row.put("state","open");
				result.add(row);
			}
		}
		return result;
	}

}
