package com.lebaoxun.portal.rest;

import javax.annotation.Resource;

import com.lebaoxun.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.DesUtils;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.IUserService;
import com.lebaoxun.portal.config.AccountConstant;
import com.lebaoxun.soa.core.redis.IRedisCache;


@RestController
public class RegisterController extends BaseController{
	
	@Resource
	private IRedisCache redisCache;
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/register/add")
	ResponseMessage register(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("vfcode") String vfcode){
		String account = username,passwd = null;
		String secret = (String) request.getSession().getAttribute("app.secret");
		if(StringUtils.isBlank(secret)){
			throw new I18nMessageException("10015", "您在当前页面停留时间过长，密钥已过期。稍后将刷新页面获取新的密钥，请重新操作！");
		}
		try {
			logger.debug("secret={}",secret);
			DesUtils desUtils = new DesUtils(secret);
			account = desUtils.decrypt(username);
			passwd = desUtils.decrypt(password);
		} catch (Exception e) {
			throw new I18nMessageException("10015", "密钥不对");
		}
		logger.info("username={},password={}",account,passwd);
		String verifycode = (String) redisCache.get(String.format(AccountConstant.SMS_VFCODE, account));
		logger.debug("verifycode={},vfcode={}",verifycode,vfcode);
		if(verifycode == null || !verifycode.equalsIgnoreCase(vfcode)){
			throw new I18nMessageException("10001", "验证码不正确");
		}
		redisCache.del(String.format(AccountConstant.SMS_VFCODE, account));;
		UserEntity user = new UserEntity();
		Long userId = GenerateCode.gen16(9);
		user.setBalance(0);
    	user.setLevel(0);
    	user.setSource("SELF");
    	user.setType("A");
    	user.setUserId(userId);
    	user.setMobile(account);
    	user.setPassword(passwd);
    	user.setAccount(account);
		return userService.save(userId, user);
	}
}
