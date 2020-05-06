package com.user.lspolygon.uicomponent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.util.AjaxJSONObject;

@Controller
@RequestMapping("/read/lspolygon/uicomponent/poygonController")
public class PoygonController {
	@Autowired
	private PoygonService poygonService;
	
	//读取危控区 面
	@RequestMapping(value="/readPoygon",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readPoygon(String poygonType,String zindex,String viewPoint){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		List<Object> resultList = this.poygonService.readPoygon(poygonType,zindex,viewPoint);
		
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(resultList);
		return ajaxJSONObject;
	}
	
	//读取危控区 点
	@RequestMapping(value="/readPoint",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readPoint(String poygonType,String zindex,String viewPoint){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		List<Object> resultList = this.poygonService.readPoint(poygonType,zindex,viewPoint);
		
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(resultList);
		return ajaxJSONObject;
	}
	
	/*读取危控区 线*/
	@RequestMapping(value="/readLine",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readLine(String line,String zindex,String viewPoint){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		List<Object> resultList = this.poygonService.readLine(line,zindex,viewPoint);
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(resultList);
		ajaxJSONObject.setMessage("获取数据成功");
		return ajaxJSONObject;
	}
	/*读取危控区 线==单个数据 */
	@RequestMapping(value="/readSingleLine",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readSingleLine(String line){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		List<Object> resultList = this.poygonService.readSingleLine(line);
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(resultList);
		ajaxJSONObject.setMessage("获取数据成功");
		return ajaxJSONObject;
	}
	/*读取危控区 变压器*/
	@RequestMapping(value="/readTransfromer",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readTransfromer(String transformer,String zindex){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		List<Object> resultList = this.poygonService.readTransfromer(transformer,zindex);
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(resultList);
		ajaxJSONObject.setMessage("获取数据成功");
		return ajaxJSONObject;
	}
	/*读取危控区 最优层级*/
	@RequestMapping(value="/readBestLevel",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readBestLevel(){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		List<Object> resultList = this.poygonService.readBestLevel();
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(resultList);
		ajaxJSONObject.setMessage("获取数据成功");
		return ajaxJSONObject;
	}
	/* 读取控件里面所有的数据  */
	@RequestMapping(value="/readRegionData",method=RequestMethod.GET)
	@ResponseBody
	public AjaxJSONObject readRegionData(String poygon,String point,String line,String transform,String zindex){
		AjaxJSONObject ajaxJSONObject = new AjaxJSONObject();
		List<Object> resultList = this.poygonService.readRegionData(poygon,point,line,transform,zindex);
		ajaxJSONObject.setSuccess(true);
		ajaxJSONObject.setData(resultList);
		ajaxJSONObject.setMessage("获取数据成功");
		return ajaxJSONObject;
	}
}
