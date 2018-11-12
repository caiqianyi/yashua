package com.lebaoxun.sms.rest.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.sms.service.ISMSTemplateService;

@RestController
public class SMSTemplateController {
	@Resource
	private ISMSTemplateService smsTemplateService;
	
	@RequestMapping(value = "/sms/template/saveOrUpdate")
	ResponseMessage saveOrUpdate(@RequestParam("templateId") String templateId, 
			@RequestParam("content") String content){
		smsTemplateService.saveOrUpdate(templateId, content);
		return ResponseMessage.ok();
	}
	
	@RequestMapping(value = "/sms/template/remove")
	ResponseMessage remove(@RequestParam("templateId") String templateId){
		smsTemplateService.remove(templateId);
		return ResponseMessage.ok();
	}
	
	@RequestMapping(value = "/sms/template/find")
	ResponseMessage find(@RequestParam("templateId") String templateId){
		return ResponseMessage.ok(smsTemplateService.find(templateId));
	}
	
	@RequestMapping(value = "/sms/template/list")
	List<Map<String, String>> list(){
		return smsTemplateService.list();
	}
}
