package com.lebaoxun.portal.rest;

import javax.annotation.Resource;

import com.lebaoxun.commons.utils.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.commons.utils.ValidatorUtils;
import com.lebaoxun.portal.config.AccountConstant;
import com.lebaoxun.soa.core.redis.IRedisCache;

@RestController
public class SMSController extends BaseController{
	
	@Resource
	private IRedisCache redisCache;

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
		String vfcode = GenerateCode.gen(5)+"";
		logger.debug("vfcode={}",vfcode);
		redisCache.set(String.format(AccountConstant.SMS_VFCODE, mobile), vfcode, 10 * 60l);
		redisCache.set(key, "success", 60l);
		return ResponseMessage.ok();
	}
}
