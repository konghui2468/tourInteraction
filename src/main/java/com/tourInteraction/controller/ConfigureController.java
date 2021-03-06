package com.tourInteraction.controller;

import com.tourInteraction.config.GlobalConstantKey;
import com.tourInteraction.entity.Configure;
import com.tourInteraction.entity.User;
import com.tourInteraction.service.IConfigureService;
import com.tourInteraction.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "configure")
public class ConfigureController {
	@Autowired
	@Qualifier(value = "configureServiceImpl")
	private IConfigureService configureService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/getConfigure.do")
	public @ResponseBody String getConfigure(HttpServletRequest req){
		
		logger.info("getConfigure.do被调用");
		List<Configure> list = new ArrayList<Configure>();
		list = configureService.getConfigure();
		String result = JSONUtil.list2json(list);
		return result;
	}
	
	@RequestMapping("/getConfigureById.do")
	public @ResponseBody String getConfigureById(HttpServletRequest req,
			@RequestParam("id") long id){
		logger.info("getConfigure.do被调用");
		String result="";

		Configure configure = configureService.getConfigureById(id);
	
		result = JSONUtil.object2json(configure);
		return result;

	}
	
	@RequestMapping("/addConfigure.do")
	public @ResponseBody String addConfigure(HttpServletRequest req, 
			@RequestParam("staticCodeConfigure") String staticCodeConfigure, 
			@RequestParam("staticCodeUse") String staticCodeUse,
			@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("file") String file){
		logger.info("addConfigure.do被调用");
		User user = SignInAndUpController.getSignInUser(req);
		Configure configure = new Configure();
		configure.setStaticCodeConfigure("configure_pic");
		configure.setStaticCodeUse(staticCodeUse);
		configure.setTitle(title);
		configure.setDescription(description);
		configure.setFile(file);
		configure.setCreateUser(user.getId());
		configure.setCreateTime(new Date());
		configure.setStatus(GlobalConstantKey.STATUS_OPEN);
		int num = configureService.addConfigure(configure);
		String result = "添加配置失败";
		if(num>0){
			result = "添加配置成功";
			return result;
		}
		return result;
	}
	
	@RequestMapping("/updateConfigure.do")
	public @ResponseBody String updateConfigure(HttpServletRequest req, 
			@RequestParam("id") int id, 
			@RequestParam("staticCodeConfigure") String staticCodeConfigure, 
			@RequestParam("staticCodeUse") String staticCodeUse,
			@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("file") String file) throws Throwable{
		logger.info("updateConfigure.do被调用");
		User user = SignInAndUpController.getSignInUser(req);
		Configure configure = new Configure();
		configure.setId(id);
		configure.setStaticCodeConfigure(staticCodeConfigure);
		configure.setStaticCodeUse(staticCodeUse);
		configure.setTitle(title);
		configure.setDescription(description);
		configure.setFile(file);
		configure.setUpdateUser(user.getId());
		configure.setUpdateTime(new Date());
		int num = configureService.updateConfigure(configure);		
		String result = "修改配置失败";
		if(num>0){
			result = "修改配置成功";
			return result;
		}
		return result;

	}
	
	@RequestMapping("/delConfigureById.do")
	public @ResponseBody String delConfigureById(@RequestParam("id") int id){
		
		logger.info("configure/delConfigureById.do被调用");
		
		int num = configureService.delConfigureById(id);
		String result = "删除失败";
		if(num > 0 ){
			result = "删除成功";
		}
		return result;
	}
}
