package com.user.root.daotest.java;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.system.annotation.DataSource;
import com.system.annotation.Log;
import com.system.dao.BaseDaoImpl;
import com.system.entity.User;
import com.system.spring.UserDynamicDataSourceHolder;
import com.system.util.BaseForm;

@Service
public class DaotestService {
	private static Logger log = LoggerFactory.getLogger(DaotestService.class);
	@Autowired
	private BaseDaoImpl baseDaoImpl;
	
	public void doaa(){
		List<Map<String, Object>> resultList = baseDaoImpl.find("SELECT * FROM t_forum WHERE forum_id in(?,?,?)", new Object[]{2});
		log.debug("=============debug==============");
		log.info("====== info ======");
	}
	
	public void doBack(BaseForm baseForm){
		baseDaoImpl.update(baseForm);
		baseDaoImpl.update("update t_post set post_text='苹果测试' where forum_id=?", new Object[]{16});
	}
	
	@Log(value="读取分页数据")
	public void greetTo(String clientName){
		System.out.println("NaiveWaiter: greet to"+clientName);
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_SLAVER)
	public void readUser(){
		//User user=(User) baseDaoImpl.findForObject("select * from t_forum where forum_id = 6",null, new User());
		List<Map<String, Object>> data = baseDaoImpl.find("select * from student",null);
		String aString = "";
		System.out.println(data.toString());
	}
	
	@DataSource(value=UserDynamicDataSourceHolder.DATA_SOURCE_MASTER)
	public void readUser2(){
		User user=(User) baseDaoImpl.findForObject("select * from t_forum where forum_id = 6",null, new User());
		String aString = "";
	}
}
