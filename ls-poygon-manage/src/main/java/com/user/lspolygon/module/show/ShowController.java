package com.user.lspolygon.module.show;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.util.AjaxJSONObject;
import com.system.util.Page;

@Controller
@RequestMapping("/read/lspolygon/module/showController")
public class ShowController {
	@Autowired
	private ShowService showService;
	
	//读取危控区类型
	@RequestMapping(value="/readPoygonType",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readPoygonType(){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		
		List<Map<String, Object>> dataList = showService.readPoygonType();
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(dataList);
		return ajaxJSONObject;
	}
	
	//读取危控区树
	@RequestMapping(value="/readPoygonTree",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readPoygonTree(){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		
		List<Map<String, Object>> dataList = showService.readPoygonTree();
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(dataList);
		return ajaxJSONObject;
	}
	
	//读取线路grid
	@RequestMapping(value="/readLineGrid",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readLineGrid(int page,int rows){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		
		Page<Map<String, Object>> dataList = showService.readLineGrid(page,rows);
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(dataList.getRows());
		ajaxJSONObject.setTotal(dataList.getTotal());
		return ajaxJSONObject;
	}
	
	//读取线路grid
	@RequestMapping(value="/readRiskGrid",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readRiskGrid(int page,int rows){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		
		Page<Map<String, Object>> dataList = showService.readRiskGrid(page,rows);
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(dataList.getRows());
		ajaxJSONObject.setTotal(dataList.getTotal());
		return ajaxJSONObject;
	}
	//读取线路grid
	@RequestMapping(value="/readTranGrid",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readTranGrid(int page,int rows){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		
		Page<Map<String, Object>> dataList = showService.readTranGrid(page,rows);
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(dataList.getRows());
		ajaxJSONObject.setTotal(dataList.getTotal());
		return ajaxJSONObject;
	}		
		
	
	//读取危控区树台账
	@RequestMapping(value="/readPoygonTreebill",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readPoygonTreebill(int rid){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		
		List<Map<String, Object>> dataList = showService.readPoygonTreebill(rid);
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(dataList);
		return ajaxJSONObject;
	}
	
	//读取线路危控区树
	@RequestMapping(value="/readLineTree",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readLineTree(){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		
		List<Map<String, Object>> dataList = showService.readLineTree();
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(dataList);
		return ajaxJSONObject;
	}
	
	//读取线路类型
	@RequestMapping(value="/readLineType",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readLineType(){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		
		List<Map<String, Object>> dataList = showService.readLineType();
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(dataList);
		return ajaxJSONObject;
	}
	
	//读取部门树
	@RequestMapping(value="/readDepartmentTree",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readDepartmentTree(){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		
		List<Map<String, Object>> dataList = showService.readDepartmentTree();
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(dataList);
		return ajaxJSONObject;
	}
	
	//读取变压器危控区树
		@RequestMapping(value="/readTranTree",method=RequestMethod.GET)
		@ResponseBody
		public AjaxJSONObject readTranTree(){
			AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
			
			List<Map<String, Object>> dataList = showService.readTranTree();
			ajaxJSONObject.setSuccess(true);
			ajaxJSONObject.setData(dataList);
			return ajaxJSONObject;
		}
	
}
