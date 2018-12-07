package com.lebaoxun.portal.rest;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import com.lebaoxun.commons.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.CommonUtil;
import com.lebaoxun.commons.utils.DesUtils;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.commons.utils.PwdUtil;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.IUserService;
import com.lebaoxun.modules.sms.service.ISMSGatewayService;
import com.lebaoxun.portal.config.AccountConstant;
import com.lebaoxun.security.oauth2.Oauth2AccessToken;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
import com.lebaoxun.security.oauth2.entity.Oauth2;
import com.lebaoxun.soa.core.redis.IRedisCache;
import com.lebaoxun.wechat.service.IWechatService;
import com.lebaoxun.wechat.vo.AccessToken;

/**
 * 登录相关
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018年6月20日 10:54:15
 */
@RestController
public class LoginController extends BaseController{
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	@Resource
	private IRedisCache redisCache;
	
	@Autowired
	private IUserService userService;
	
	@Resource
	private IWechatService wechatService;
	
	@Resource
	private ISMSGatewayService smsGatewayService;
	
	@Value("${sms.cst_id}")
	private String smsCstid;
	
//	@RequestMapping("captcha.jpg")
//	public void captcha(String emid)throws IOException {
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		
//		//验证码等级
//		String code = CommonUtil.getVerifyCode(output, 1);
//		//当前页面刷验证码的次数
//		
//		if(StringUtils.isNotBlank(emid)){
//			redisCache.set("agent:vfcode:"+emid, code, 20*60L);
//		}else{
//			logger.debug("code={}",code);
//			request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
//		}
//		try {
//			ServletOutputStream out = response.getOutputStream();
//			output.writeTo(out);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/oauth2/secret.js",produces="application/javascript;charset=UTF-8")
	String securitySecret(){
		String secret = UUID.randomUUID().toString().replaceAll("-", "");
		request.getSession().setAttribute("app.secret", secret);
		return "var secret = \""+secret+"\";";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/oauth2/system.js",produces="application/javascript;charset=UTF-8")
	String system(){
		return "$.system = {loadTime: new Date("+new Date().getTime()+")};";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/oauth2/now")
	ResponseMessage now(){
		return new ResponseMessage().put("now",new Date().getTime());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/oauth2/encrypt")
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
	 * 正常登录
	 * @param username
	 * @param password
	 * @param platform
	 * @param kaptcha
	 * @param openid
	 * @return
	 */
	@RequestMapping(value = "/oauth2/token", method = RequestMethod.POST)
	ResponseMessage oauthToken(String username,String password,
			String platform,
			//String captcha,
			String openid,
			String wxopenid){
		Boolean isCorrectPwd = null; 
		UserEntity a = null;
		if(StringUtils.isBlank(openid)){
//			if(!"app".equals(platform)
//					&& !"wechatOA".equals(platform)){
//				String verifycode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//				logger.debug("verifycode={},captcha={}",verifycode,captcha);
//				if(verifycode == null || !verifycode.equalsIgnoreCase(captcha)){
//					throw new I18nMessageException("10001", "验证码不正确");
//				}
//				request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
//			}
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
			if("wechatOA".equals(platform)){
				a = userService.findByAccount(account);
			}else{
				String secret = (String) request.getSession().getAttribute("app.secret");
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
				logger.info("username={},password={}",account,passwd);
				a = userService.login(account, passwd);
			}
			if(a == null){
				IncrErrorCount(username, value);
				throw new I18nMessageException("10002", new String[]{AccountConstant.ACCOUNT_ERROR_COUNT+"",AccountConstant.ACCOUNT_ERROR_LOCK_TIME/3600+"",(AccountConstant.ACCOUNT_ERROR_COUNT-errorCount-1)+""});
			}
			
			if("N".equals(a.getStatus())){
				throw new I18nMessageException("10014","账户已被禁用，请联系管理员");
			}
			
			if(StringUtils.isNotBlank(wxopenid)){
				ResponseMessage sm = userService.bindOpenid(a.getUserId(), wxopenid);
				if(!"0".equals(sm.getErrcode())){
					return sm;
				}
			}
			if(passwd != null){
				isCorrectPwd = !PwdUtil.isCorrectPwd(passwd);
			}
			openid = oauth2SecuritySubject.getOpenid(account);
		}
		Oauth2 oauth2 = oauth2SecuritySubject.refreshToken(request,openid);
		Oauth2AccessToken.setToken(oauth2.getAssess_token());
		
		Map<String,Object> json = new TreeMap<String,Object>();
		json.put("uid", a.getUserId());
		json.put("access_token", oauth2.getAssess_token());
		json.put("openid", oauth2.getOpenid());
		json.put("expires_in", oauth2.getExpires_in());
		json.put("isCorrectPwd", isCorrectPwd);
		return new ResponseMessage(json);
	}
	
	/**
	 * 微信授权登录
	 * @param code
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/oauth2/token/wechatOA", method = RequestMethod.GET)
	ResponseMessage oauthTokenForWechatOA(String code,
			String openid) throws Exception{
		logger.info("wechatOA|code={}",code);
		if(StringUtils.isNotBlank(code)){
			AccessToken accessToken = wechatService.getAccessToken(code, "yashua");
			if(accessToken == null || StringUtils.isBlank(accessToken.getOpenid())){
				throw new I18nMessageException("40001","微信授权失败");
			}
			openid = accessToken.getOpenid();
		}
		UserEntity user = userService.findByOpenid(openid,null);
		if(user == null){
			ResponseMessage error = new ResponseMessage();
			error.setErrcode("40004");
			error.setErrmsg("当前微信号未绑定代理帐号，无法登录");
			error.setData(openid);
			return error;
		}
		if("N".equals(user.getStatus())){
			throw new I18nMessageException("10014","账户已被禁用，请联系管理员");
		}
		ResponseMessage success = oauthToken(user.getAccount(), user.getPassword(), "wechatOA", null,null);
		Map<String,Object> json = (Map<String, Object>) success.getData();
		json.put("wxappid", openid);
		return success;
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
     * 修改密码
     * @param userId 用户ID
     * @param newPasswd 新密码（非加密）
     * @param adminId 操作人
     */
    @RequestMapping("/oauth2/modifyPassword")
    ResponseMessage modifyPassword(@RequestParam("username") String username,
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
		ResponseMessage sm = smsGatewayService.checkVfCode(smsCstid, account, vfcode);
		if(!"0".equals(sm.getErrcode())){
			return sm;
		}
		UserEntity user = userService.findByAccount(account);
		if(user == null){
			throw new I18nMessageException("-1", "帐号不存在！");
		}
    	return userService.modifyPassword(user.getUserId(), passwd, user.getUserId());
    }
    
    @RequestMapping(value = "/register/add")
	ResponseMessage register(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("vfcode") String vfcode,
			@RequestParam(name="wxopenid",required = false) String wxopenid){
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
		ResponseMessage sm = smsGatewayService.checkVfCode(smsCstid, account, vfcode);
		if(!"0".equals(sm.getErrcode())){
			return sm;
		}
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
    	user.setOpenid(wxopenid);
    	ResponseMessage success = userService.save(userId, user);
    	if(!"0".equals(success.getErrcode())){
    		return success;
    	}
		return oauthToken(user.getAccount(), user.getPassword(), "wechatOA", null, null);
	}
	
}
