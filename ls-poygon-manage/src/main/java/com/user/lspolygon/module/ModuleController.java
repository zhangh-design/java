package com.user.lspolygon.module;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.util.AjaxJSONObject;

@Controller
@RequestMapping("/read/lspolygon/module/moduleController")
public class ModuleController {
	@Autowired
	private ModuleService moduleService;
	
	//读取用户信息
	@RequestMapping(value="/readOperator/{oprt_no}",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readOperator(@PathVariable("oprt_no") String oprt_no){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		if(!StringUtils.isEmpty(oprt_no)){
			Map<String,Object> data = this.moduleService.readOperator(oprt_no);
			ajaxJSONObject.setSuccess(true);
			ajaxJSONObject.setData(data);
		}
		return ajaxJSONObject;
	}
	
	//读取用户对应的模块
	@RequestMapping(value="/readOprtModule/{oprt_no}",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readModule(@PathVariable("oprt_no") String oprt_no){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		if(!StringUtils.isEmpty(oprt_no)){
			List<Map<String, Object>> dataList = moduleService.readModule(oprt_no);
			ajaxJSONObject.setSuccess(true);
			ajaxJSONObject.setData(dataList);
		}
		return ajaxJSONObject;
	}
	
}
