package com.user.lspolygon.uicomponent;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.system.annotation.DataSource;
import com.system.dao.BaseDaoImpl;
import com.system.spring.UserDynamicDataSourceHolder;
import com.system.util.AjaxJSONObject;
import com.system.util.BaseForm;
import com.user.lspolygon.uicomponent.domain.PoygonType;

@Service
public class PoygonService {
	@Autowired
	private BaseDaoImpl baseDaoImpl;
	
	//读取危控区 面
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Object> readPoygon(String poygonType,String zindex,String viewPoint){
		try {
			poygonType = URLDecoder.decode(poygonType, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Object> resultList = new ArrayList<Object>();
		BaseForm formPoint = new BaseForm();
		formPoint.addSql("SELECT rid,obj_name,TYPE,zindex,remark FROM sys_map_object WHERE obj_name = 'poygon'");
		Map<String, Object> map = baseDaoImpl.findForMap(formPoint);
		Object o = map.get("zindex");
		int ll = Integer.parseInt(o.toString());
		String sql = "SELECT '危控区'  AS kind,a.rid,a.name,a.path,a.type,a.x1,a.x2,a.y1,a.y2,"
				+ "a.class,(SELECT NAME FROM v_poygon_class WHERE rid = a.class) AS class_name,a.create_time,"
				+ "(SELECT NAME FROM mis_sys.department WHERE rid = a.dept_id) AS dept_name,"
				+ "b.fill,b.fillOpacity,b.icon,b.name AS type_name,b.en_name,b.stroke,b.strokeOpacity,b.strokeWidth "
				+","+ ll +"  AS best_level "
				+ "FROM poygon AS a LEFT JOIN poygon_type AS b "
				+ "ON a.type = b.rid "
				+ "WHERE a.type = ? ";
		if(viewPoint.isEmpty()){
			sql += poygonType+ " ORDER BY TYPE ";
		}else {
			sql += " AND (f_lineintoline(a.path,"+ viewPoint +") >0 "+ poygonType +" ) " +" ORDER BY TYPE ";
		}
		if(Integer.parseInt(zindex) >= ll){
			BaseForm form = new BaseForm();
			form.addSql("SELECT * FROM poygon_type WHERE icon IS NULL ORDER BY `order`"); //前端拼接sql在这插入
			form.addEntity(PoygonType.class);
			List<PoygonType> typeList = baseDaoImpl.findForListClass(form);
			
			for (PoygonType item : typeList) {
				List<Map<String, Object>> poygons = baseDaoImpl.find(sql, new Object[]{item.getRid()});
				if(poygons.size()>0)
					resultList.add(poygons);
			}
		}
		return resultList;
	}
	
	//读取危控区 点
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Object> readPoint(String poygonType,String zindex,String viewPoint){
		try {
			poygonType = URLDecoder.decode(poygonType, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Object> resultList = new ArrayList<Object>();
		BaseForm formPoint = new BaseForm();
		formPoint.addSql("SELECT rid,obj_name,TYPE,zindex,remark FROM sys_map_object WHERE obj_name = 'poygon'");
		Map<String, Object> map = baseDaoImpl.findForMap(formPoint);
		Object o = map.get("zindex");
		int ll = Integer.parseInt(o.toString());
		String sql = "SELECT '危控区'  AS kind,a.rid,a.name,a.path,a.type,a.x1,a.x2,a.y1,a.y2,"
				+ "a.class,(SELECT NAME FROM v_poygon_class WHERE rid = a.class) AS class_name,a.create_time,"
				+ "(SELECT NAME FROM mis_sys.department WHERE rid = a.dept_id) AS dept_name,"
				+ "b.fill,b.fillOpacity,b.icon,b.name AS type_name,b.en_name,b.stroke,b.strokeOpacity,b.strokeWidth "
				+","+ ll +"  AS best_level "
				+ "FROM poygon AS a LEFT JOIN poygon_type AS b "
				+ "ON a.type = b.rid "
				+ "WHERE a.type = ? ";
		if(viewPoint.isEmpty()){
			sql += poygonType+ " ORDER BY TYPE ";
		}else {
			sql += " AND (f_lineintoline(a.path,"+ viewPoint +") >0 "+ poygonType +" ) " +" ORDER BY TYPE ";
		}
		if(Integer.parseInt(zindex) >= ll){
			BaseForm form = new BaseForm();
			form.addSql("SELECT * FROM poygon_type WHERE icon IS NOT NULL ORDER BY `order`");
			form.addEntity(PoygonType.class);
			List<PoygonType> typeList = baseDaoImpl.findForListClass(form);
			
			for (PoygonType item : typeList) {
				List<Map<String, Object>> poygons = baseDaoImpl.find(sql, new Object[]{item.getRid()});
				if(poygons.size()>0)
					resultList.add(poygons);
			}
		}
		return resultList;
	}
	
	/* 线路查询 */
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Object> readLine(String line,String zindex,String viewPoint){
		try {
			line = URLDecoder.decode(line, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<Object> resultList = new ArrayList<Object>();
		List<Map<String,Object>> linesAll = new ArrayList<Map<String,Object>>();
		BaseForm formOver = new BaseForm();
		//架空路线 先有
		formOver.addSql("SELECT rid,obj_name,TYPE,zindex,remark FROM sys_map_object WHERE obj_name = 'line' AND TYPE=2");
		Map<String, Object> mapOver = baseDaoImpl.findForMap(formOver);
		Object oOver = mapOver.get("zindex");
		int llOver = Integer.parseInt(oOver.toString());
		if(Integer.parseInt(zindex) >= llOver){
			String sql = "SELECT '线路'  AS kind,a.rid,a.type,a.name,a.path,a.voltage,(SELECT NAME FROM mis_sys.department WHERE rid = a.dept_name) AS dept_name,a.style,a.substation_rid "
					+","+ llOver +"  AS best_level "
					+" ,a.x1,a.x2,a.y1,a.y2 "
					+ "FROM line AS a where 1=1 and a.type=2 ";
			if(viewPoint.isEmpty()){
				sql += line;
			}else {
				sql += " AND (f_lineintoline(a.path,"+ viewPoint +") >0  "+ line +" ) ";
			}
			linesAll = baseDaoImpl.find(sql, new Object[]{});
		}
		//低压路线
		BaseForm form = new BaseForm();
		form.addSql("SELECT rid,obj_name,TYPE,zindex,remark FROM sys_map_object WHERE obj_name = 'line' AND TYPE=1");
		Map<String, Object> map = baseDaoImpl.findForMap(form);
		Object o = map.get("zindex");
		int ll = Integer.parseInt(o.toString());
		if(Integer.parseInt(zindex) >= ll){
			String sql = "SELECT '线路'  AS kind,a.rid,a.type,a.name,a.path,a.voltage,(SELECT NAME FROM mis_sys.department WHERE rid = a.dept_name) AS dept_name,a.style,a.substation_rid "
					+","+ ll +"  AS best_level "
					+" ,a.x1,a.x2,a.y1,a.y2 "
					+ "FROM line AS a where 1=1 and a.type=1 ";
			if(viewPoint.isEmpty()){
				sql += line;
			}else {
				sql += " AND (f_lineintoline(a.path,"+ viewPoint +") >0 "+ line +" ) ";
			}
			List<Map<String,Object>> lines = baseDaoImpl.find(sql, new Object[]{});
			for(int i = 0 ; i < lines.size() ; i++) {
				linesAll.add(lines.get(i));
			}
		}
		resultList.add(linesAll);
		return resultList;
	}
	/* 单个线路查询 */
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Object> readSingleLine(String line){
		try {
			line = URLDecoder.decode(line, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<Object> resultList = new ArrayList<Object>();
		String sql = "SELECT '线路'  AS kind,a.rid,a.type,a.name,a.path,a.voltage,(SELECT NAME FROM mis_sys.department WHERE rid = a.dept_name) AS dept_name,a.style,a.substation_rid" 
				  +",b.zindex AS best_level ,a.x1,a.x2,a.y1,a.y2 FROM line AS a ,sys_map_object AS b WHERE 1=1 AND a.type=b.type "+line;
		List<Map<String,Object>> lineSingle = baseDaoImpl.find(sql, new Object[]{});
		resultList.add(lineSingle);
		return resultList;
	}
	
	/* 变压器信息查询 */
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Object> readTransfromer(String transformer,String zindex){
		try {
			transformer = URLDecoder.decode(transformer, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<Object> resultList = new ArrayList<Object>();
		BaseForm form = new BaseForm();
		form.addSql("SELECT rid,obj_name,TYPE,zindex,remark FROM sys_map_object WHERE obj_name = 'transformer'");
		Map<String, Object> map = baseDaoImpl.findForMap(form);
		Object o = map.get("zindex");
		int ll = Integer.parseInt(o.toString());
		if(Integer.parseInt(zindex) >= ll){
			String sql = "SELECT '变压器'  AS kind,a.rid,a.name,a.x,a.y,(SELECT NAME FROM mis_sys.department WHERE rid = a.dept_id) AS dept_name,a.voltage,a.x1,a.x2,a.y1,a.y2"
					+","+ ll +"  AS best_level "
					+" FROM transformer AS a WHERE 1=1 "+transformer;
			List<Map<String,Object>> lines = baseDaoImpl.find(sql, new Object[]{});
			resultList.add(lines);
		}
		return resultList;
	}
	/* 最优层级 */
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Object> readBestLevel(){
		List<Object> resultList = new ArrayList<Object>();
		String sql = "SELECT rid,obj_name,TYPE,zindex,remark FROM `sys_map_object`";
		List<Map<String,Object>> level = baseDaoImpl.find(sql, new Object[]{});
		resultList.add(level);
		return resultList;
	}
	/* 框选信息查询 */
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public List<Object> readRegionData(String poygon,String point,String line,String transform,String zindex){
		List<Object> resultList = new ArrayList<Object>();
		
		List<Object> poygonType = this.readPoygon(poygon,zindex,line);
		for(int i = 0 ; i < poygonType.size() ; i++) {
			List<Map<String,Object>> objList = (List<Map<String,Object>>) poygonType.get(i);
			for(int j = 0 ; j < objList.size(); j++){
				resultList.add(objList.get(j));
			}
		}
		List<Object> poygonPoint = this.readPoint(point,zindex,line);
		for(int i = 0 ; i < poygonPoint.size() ; i++) {
			List<Map<String,Object>> objList = (List<Map<String,Object>>) poygonPoint.get(i);
			for(int j = 0 ; j < objList.size(); j++){
				resultList.add(objList.get(j));
			}
		}
		List<Object> linePoint = this.readLine(point,zindex,line);
		for(int i = 0 ; i < linePoint.size() ; i++) {
			List<Map<String,Object>> objList = (List<Map<String,Object>>) linePoint.get(i);
			for(int j = 0 ; j < objList.size(); j++){
				resultList.add(objList.get(j));
			}
		}
		List<Object> transformPoint = this.readTransfromer(transform,zindex);
		for(int i = 0 ; i < transformPoint.size() ; i++) {
			List<Map<String,Object>> objList = (List<Map<String,Object>>) transformPoint.get(i);
			for(int j = 0 ; j < objList.size(); j++){
				resultList.add(objList.get(j));
			}
		}
		/*resultList.add(poygonType);
		resultList.add(poygonPoint);
		resultList.add(linePoint);
		resultList.add(transformPoint);*/
		return resultList;
	}
}
