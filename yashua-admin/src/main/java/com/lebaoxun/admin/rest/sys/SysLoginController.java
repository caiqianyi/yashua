/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.lebaoxun.admin.rest.sys;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.lebaoxun.admin.config.AccountConstant;
import com.lebaoxun.admin.rest.BaseController;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.DesUtils;
import com.lebaoxun.commons.utils.PwdUtil;
import com.lebaoxun.manager.sys.entity.SysUserEntity;
import com.lebaoxun.pay.service.ISysUserService;
import com.lebaoxun.security.oauth2.Oauth2;
import com.lebaoxun.security.oauth2.Oauth2AccessToken;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
import com.lebaoxun.soa.core.redis.IRedisCache;

/**
 * 登录相关
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
public class SysLoginController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	@Autowired
	private Producer producer;
	
	@Resource
	private IRedisCache redisCache;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/security/token.js",produces="application/javascript;charset=UTF-8")
	String securitySecret(){
		String secret = UUID.randomUUID().toString().replaceAll("-", "");
		request.getSession().setAttribute("app.secret", secret);
		return "var token = \""+secret+"\";";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/security/encrypt")
	ResponseMessage encrypt(@RequestParam("encrypts") String encrypts[],@RequestParam("token") String token) throws Exception{
		String secret = (String) request.getSession().getAttribute("app.secret");
		if(StringUtils.isBlank(secret) || !token.equals(secret)){
			throw new I18nMessageException("10015", "密钥不对");
		}
		if(encrypts != null && encrypts.length < 10){
			DesUtils desUtils = new DesUtils(token);
			StringBuffer bf = new StringBuffer();
			for(int i=0;i<encrypts.length;i++){
				String str = encrypts[i];
				bf.append(desUtils.encrypt(str));
				if(i < encrypts.length-1){
					bf.append(",");
				}
				logger.debug("str[{}]={}",i,str);
			}
			return new ResponseMessage(bf.toString().split(","));
		}
		throw new I18nMessageException("500");
	}
	
	/**
	 * 登录
	 */
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	ResponseMessage login(String username, String password, String captcha) {
		String kaptcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		String secret = (String) request.getSession().getAttribute("app.secret");
		String account = username,passwd = null;
		if(StringUtils.isBlank(secret)){
			throw new I18nMessageException("10015", "密钥不对");
		}
		try {
			logger.debug("secret={}",secret);
			DesUtils desUtils = new DesUtils(secret);
			account = desUtils.decrypt(username);
			passwd = desUtils.decrypt(password);
		} catch (Exception e) {
			throw new I18nMessageException("10015", "密钥不对");
		}
		logger.debug("username={},password={}",account,passwd);
		if(!captcha.equalsIgnoreCase(kaptcha)){
			throw new I18nMessageException("10001", "验证码不正确");
		}
		String key = AccountConstant.CACHEKEY_LOGIN_ACCOUNT_LOCK + "." + account;
		String value = (String) redisCache.get(key);
		if (StringUtils.isNotBlank(value) && "LOCK".equals(value)) {
			throw new I18nMessageException("10005",new String[]{AccountConstant.ACCOUNT_ERROR_LOCK_TIME/3600+"",redisCache.ttl(key)/60+1+""});
		}
		Integer errorCount = 0;
		if(StringUtils.isNumeric(value)){
			errorCount = Integer.parseInt(value);
		}
		
		SysUserEntity a = sysUserService.login(account, passwd);
		if(a == null){
			IncrErrorCount(account, value);
			throw new I18nMessageException("10002", new String[]{AccountConstant.ACCOUNT_ERROR_COUNT+"",AccountConstant.ACCOUNT_ERROR_LOCK_TIME/3600+"",(AccountConstant.ACCOUNT_ERROR_COUNT-errorCount-1)+""});
		}
		if(a.getStatus() == 0){
			throw new I18nMessageException("10014","账户已被禁用，请联系管理员");
		}
		Boolean isCorrectPwd = null; 
		if(passwd != null){
			isCorrectPwd = !PwdUtil.isCorrectPwd(passwd);
		}
		String openid = oauth2SecuritySubject.getOpenid(account);
		Oauth2 oauth2 = oauth2SecuritySubject.refreshToken(request,openid);
		Oauth2AccessToken.setToken(oauth2.getAssess_token());
		
		Map<String,Object> json = new TreeMap<String,Object>();
		json.put("access_token", oauth2.getAssess_token());
		json.put("openid", oauth2.getOpenid());
		json.put("expires_in", oauth2.getExpires_in());
		json.put("isCorrectPwd", isCorrectPwd);
		return new ResponseMessage(json);
	}
	
	/**
	 * 正常登录
	 * @param username
	 * @param password
	 * @param platform
	 * @param verfiyCode
	 * @param openid
	 * @return
	 */
	@RequestMapping(value = "/oauth/token", method = RequestMethod.GET)
	ResponseMessage oauthToken(String username,String password,
			String platform,
			String verfiyCode,String openid){
		Boolean isCorrectPwd = null; 
		if(StringUtils.isBlank(openid)){
			
			if(!"app".equals(platform)
					&& !"wechatOA".equals(platform)){
				String verifycode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
				logger.debug("verifycode={},verfiyCode={}",verifycode,verfiyCode);
				if(verifycode == null || !verifycode.equalsIgnoreCase(verfiyCode)){
					throw new I18nMessageException("10001", "验证码不正确");
				}
				request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
			}
			if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
				throw new I18nMessageException("10001", "验证码不正确");
			}
			String key = AccountConstant.CACHEKEY_LOGIN_ACCOUNT_LOCK + "." + username;
			String value = (String) redisCache.get(key);
			if (StringUtils.isNotBlank(value) && "LOCK".equals(value)) {
				throw new I18nMessageException("10005",new String[]{AccountConstant.ACCOUNT_ERROR_LOCK_TIME/3600+"",redisCache.ttl(key)/60+1+""});
			}
			Integer errorCount = 0;
			if(StringUtils.isNumeric(value)){
				errorCount = Integer.parseInt(value);
			}
			
			String account = username,passwd = null;
			if(!"wechatOA".equals(platform)){
				try {
					String secret = (String) request.getSession().getAttribute("app.secret");
					logger.debug("secret={}",secret);
					DesUtils desUtils = new DesUtils(secret);
					account = desUtils.decrypt(username);
					passwd = desUtils.decrypt(password);
				} catch (Exception e) {
					IncrErrorCount(username, value);
					throw new I18nMessageException("10002", new String[]{AccountConstant.ACCOUNT_ERROR_COUNT+"",AccountConstant.ACCOUNT_ERROR_LOCK_TIME/3600+"",(AccountConstant.ACCOUNT_ERROR_COUNT-errorCount-1)+""});
				}
				logger.info("username={},password={}",account,passwd);
			}
			
			
			SysUserEntity a = sysUserService.login(account, passwd);
			if(a == null){
				IncrErrorCount(username, value);
				throw new I18nMessageException("10002", new String[]{AccountConstant.ACCOUNT_ERROR_COUNT+"",AccountConstant.ACCOUNT_ERROR_LOCK_TIME/3600+"",(AccountConstant.ACCOUNT_ERROR_COUNT-errorCount-1)+""});
			}
			if(a.getStatus() == 0){
				throw new I18nMessageException("10014","账户已被禁用，请联系管理员");
			}
			
			if(passwd != null){
				isCorrectPwd = !PwdUtil.isCorrectPwd(passwd);
			}
			openid = oauth2SecuritySubject.getOpenid(account);
		}
		Oauth2 oauth2 = oauth2SecuritySubject.refreshToken(request,openid);
		Oauth2AccessToken.setToken(oauth2.getAssess_token());
		
		Map<String,Object> json = new TreeMap<String,Object>();
		json.put("access_token", oauth2.getAssess_token());
		json.put("openid", oauth2.getOpenid());
		json.put("expires_in", oauth2.getExpires_in());
		json.put("isCorrectPwd", isCorrectPwd);
		return new ResponseMessage(json);
	}
	
	private void IncrErrorCount(String account,String value) {
		// 一天中登录失败10次，账号锁定2小时
		String key = AccountConstant.CACHEKEY_LOGIN_ACCOUNT_LOCK + "." + account;
		if(StringUtils.isBlank(value)) {
			redisCache.set(key, "1", AccountConstant.ACCOUNT_ERROR_CHECK_EXPIRE);
		} else {
			int count = Integer.parseInt(value) + 1;
			if(count >= AccountConstant.ACCOUNT_ERROR_COUNT) {
				String lockKey = AccountConstant.CACHEKEY_LOGIN_ACCOUNT_LOCK+"."+account;
				redisCache.set(lockKey, "LOCK", AccountConstant.ACCOUNT_ERROR_LOCK_TIME);
				redisCache.del(key);
			} else {
				redisCache.set(key, count+"", AccountConstant.ACCOUNT_ERROR_CHECK_EXPIRE);
			}
		}
	}
	
	/**
	 * 微信授权登录
	 * @param code
	 * @param state
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/oauth/token/wechatOA", method = RequestMethod.GET)
	ResponseMessage oauthTokenForWechatOA(String code,String state) throws Exception{
		logger.info("wechatOA|code={},state={}",code,state);
		AccessToken accessToken = wechatService.getAccessToken(code, state);
		if(accessToken == null || StringUtils.isBlank(accessToken.getOpenid())){
			throw new I18nMessageException("40001","微信授权失败");
		}
		String openid = accessToken.getOpenid();
		Agent agent = accountService.findAgentInfo(openid,state);
		if(agent == null){
			ResponseMessage error = new ResponseMessage();
			error.setErrcode("40004");
			error.setErrmsg("当前微信号未绑定代理帐号，无法登录");
			error.setData(accessToken);
			return error;
		}
		if("N".equals(agent.getEnable())){
			throw new I18nMessageException("10014","账户已被禁用，请联系管理员");
		}
		ResponseMessage success = oauthToken(agent.getAccount(), agent.getPassword(), "wechatOA", null, null);
		Map<String,Object> json = (Map<String, Object>) success.getData();
		json.put("wechat", accessToken);
		return success;
	}*/
	
	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		oauth2SecuritySubject.logout();
		return "redirect:login.html";
	}
	
}
