package com.lebaoxun.sms.rest.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.sms.core.SMSGateway;
import com.lebaoxun.sms.service.ISMSGatewayService;

@RestController
public class SMSGatewayController {

	@Resource
	private ISMSGatewayService smsGatewayService;
	
	@RequestMapping(value = "/sms/gateway/set")
	ResponseMessage set(@RequestBody SMSGateway gateway){
		smsGatewayService.set(gateway);
		return ResponseMessage.ok();
	}
	
	@RequestMapping(value = "/sms/gateway/find")
	ResponseMessage find(@RequestParam("gatewayName") String gatewayName){
		return ResponseMessage.ok(smsGatewayService.find(gatewayName));
	}
	
	@RequestMapping(value = "/sms/gateway/current")
	ResponseMessage getCurrentGateway(){
		return ResponseMessage.ok(smsGatewayService.getCurrentGateway());
	}
	
	@RequestMapping(value = "/sms/gateway/setModel")
	ResponseMessage setGatewayModel(@RequestParam("model") String model){
		smsGatewayService.setGatewayModel(model);
		return ResponseMessage.ok();
	}
	
	@RequestMapping(value = "/sms/gateway/delete")
	ResponseMessage delete(@RequestParam("gatewayName") String gatewayName){
		smsGatewayService.delete(gatewayName);
		return ResponseMessage.ok();
	}
	
	@RequestMapping(value = "/sms/gateway/list")
	List<SMSGateway> list(){
		return smsGatewayService.list();
	}
}
