package com.lebaoxun.portal.rest;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.MD5;
import com.lebaoxun.commons.utils.StringUtils;
import com.lebaoxun.commons.utils.ValidatorUtils;
import com.lebaoxun.modules.pay.service.ISMSGatewayService;
import com.lebaoxun.soa.core.redis.IRedisCache;

@RestController
public class SMSController extends BaseController{
	
	@Resource
	private IRedisCache redisCache;
	
	@Resource
	private ISMSGatewayService smsGatewayService;
	
	@Value("${sms.cst_id}")
	private String smsCstid;
	
	@Value("${sms.secret}")
	private String smsSecret;
	
	@Value("${sms.template.register}")
	private String smsTemplateForRegister;

	@RequestMapping(value = "/sms/send")
	ResponseMessage sendSMS(@RequestParam("mobile") String mobile,
			@RequestParam("token") String token){
		String secret = (String) request.getSession().getAttribute("app.secret");
		if(StringUtils.isBlank(secret) || !token.equals(secret)){
			throw new I18nMessageException("10015", "密钥不对");
		}
		
		if(!ValidatorUtils.checkTel(mobile)){
			throw new I18nMessageException("10011", "手机号格式不正确！");
		}
		
		String key = "sms:oneminute:"+mobile;
		if(redisCache.exists(key)){
			String ttl = ""+redisCache.ttl(key);
			ResponseMessage sm = new ResponseMessage();
			sm.setErrcode("10012");
			sm.setErrmsg("发送次数过于频繁，请"+ttl+"秒后再试！");
			sm.setData(ttl);
			return sm;
		}
		String sign = MD5.md5(mobile + smsCstid + smsSecret);//生成签名数据
		ResponseMessage sm = smsGatewayService.send(mobile, smsTemplateForRegister, smsCstid, sign, null);
		if("0".equals(sm.getErrcode())){
			redisCache.set(key, "success", 60l);
		}
		return sm;
	}
}
