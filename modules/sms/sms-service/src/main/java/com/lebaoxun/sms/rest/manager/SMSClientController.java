package com.lebaoxun.sms.rest.manager;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.sms.service.ISMSClientService;
import com.lebaoxun.sms.vo.SMSAstrictVo;
import com.lebaoxun.sms.vo.SMSClientVo;

@RestController
public class SMSClientController {
	
	@Resource
	ISMSClientService smsClientService;
	
	/**
	 * 创建账号
	 * @param appid appid
	 * @param secret secret
	 * @param status 0=禁用，1=启用
	 */
	@RequestMapping(value = "/sms/client/saveOrUpdate")
	ResponseMessage saveOrUpdate(@RequestBody SMSClientVo clientVo){
		smsClientService.saveOrUpdate(clientVo);
		return new ResponseMessage();
	}

	/**
	 * 删除账号
	 * @param appid
	 */
	@RequestMapping(value = "/sms/client/delete")
	ResponseMessage delete(@RequestParam("appid") String appid){
		smsClientService.delete(appid);
		return new ResponseMessage();
	}
	
	@RequestMapping(value = "/sms/client/find")
	ResponseMessage find(@RequestParam("appid") String appid){
		return new ResponseMessage(smsClientService.find(appid));
	}
	
	@RequestMapping(value = "/sms/client/list")
	List<SMSClientVo> findAll(){
		return smsClientService.findAll();
	}
	
	/**
	 * 配置发送限制，策略
	 * @param appid
	 * @param config
	 */
	@RequestMapping(value = "/sms/client/astrict/set")
	ResponseMessage setAstrict(@RequestParam("appid") String appid,
			@RequestParam("format") String format,
			@RequestParam("astrict") Integer astrict){
		return new ResponseMessage(smsClientService.findAll());
	}
	
	@RequestMapping(value = "/sms/client/astrict/delete")
	ResponseMessage deleteAstrict(@RequestParam("appid") String appid,
			@RequestParam("format") String format){
		smsClientService.deleteAstrict(appid, format);
		return new ResponseMessage();
	}
	
	@RequestMapping(value = "/sms/client/astrict/list")
	List<SMSAstrictVo> findAstrict(@RequestParam("appid") String appid){
		return smsClientService.findAstrict(appid);
	}
	
	@RequestMapping(value = "/sms/client/astrict/find")
	ResponseMessage findAstrict(@RequestParam("appid") String appid,
			@RequestParam("format") String format){
		return new ResponseMessage(smsClientService.findAstrict(appid, format));
	}
	
	
	@RequestMapping(value = "/sms/client/isInBlackList")
	ResponseMessage isInBlackList(@RequestParam("appid") String appid,
			@RequestParam("mobile") String mobile){
		return new ResponseMessage(smsClientService.isInBlackList(appid, mobile));
	}
	
	@RequestMapping(value = "/sms/client/isFreeze")
	ResponseMessage isFreeze(@RequestParam("appid") String appid,
			@RequestParam("mobile") String mobile){
		return new ResponseMessage(smsClientService.isFreeze(appid, mobile));
	}
	
	@RequestMapping(value = "/sms/client/addSendLog")
	ResponseMessage addSendLog(@RequestParam("appid") String appid,
			@RequestParam("mobile") String mobile){
		smsClientService.addSendLog(appid, mobile);
		return new ResponseMessage();
	}
	
	@RequestMapping(value = "/sms/client/freeze")
	ResponseMessage freeze(@RequestParam("appid") String appid,
			@RequestParam("mobile") String mobile,
			@RequestParam("reason") String reason){
		smsClientService.freeze(appid, mobile, reason);
		return new ResponseMessage();
	}
	
	@RequestMapping(value = "/sms/client/addBlackList")
	ResponseMessage addBlackList(@RequestParam("appid") String appid,
			@RequestParam("mobile") String mobile){
		smsClientService.addBlackList(appid, mobile);
		return new ResponseMessage();
	}
	
	@RequestMapping(value = "/sms/uuid")
	ResponseMessage uuid(){
		return new ResponseMessage(UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
