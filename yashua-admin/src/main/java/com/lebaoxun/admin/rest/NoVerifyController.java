package com.lebaoxun.admin.rest;
/*package com.ylhy.agent.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lebaoxun.agent.core.entity.Agent;
import com.lebaoxun.agent.core.service.IAccountService;
import com.lebaoxun.agent.core.service.IAgentVisitService;
import com.lebaoxun.agent.core.service.IGameTypeService;
import com.lebaoxun.agent.core.service.IMallCardService;
import com.lebaoxun.agent.core.service.IServerCityService;
import com.lebaoxun.agent.gonghui.service.ILaborUnionService;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.SuccessMessage;
import com.lebaoxun.commons.utils.CommonUtil;
import com.lebaoxun.commons.utils.DesUtils;
import com.lebaoxun.commons.utils.MD5;
import com.lebaoxun.commons.utils.PwdUtil;
import com.lebaoxun.commons.utils.ValidateUtil;
import com.lebaoxun.gonghui.vo.LaborUnion;
import com.lebaoxun.sms.service.ISMSGatewayService;
import com.lebaoxun.soa.core.redis.IRedisCache;
import com.lebaoxun.wechat.service.IWechatService;
import com.lebaoxun.wechat.vo.AccessToken;
import com.lebaoxun.word.service.IUploadService;
import com.ylhy.agent.constants.CacheKeyConstant;
import com.ylhy.agent.constants.WebConstants;
import com.ylhy.agent.security.GlobalToken;
import com.ylhy.agent.security.Oauth2;
import com.ylhy.agent.security.Oauth2SecuritySubject;

@RequestMapping("/noverify")
@RestController
public class NoVerifyController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(NoVerifyController.class);
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	@Resource
	private IAccountService accountService;
	
	@Resource
	private IServerCityService serverCityService;
	
	@Resource
	private ISMSGatewayService smsGatewayService;
	
	@Resource
	private RedisTemplate<String,?> redisTemplate;
	
	@Resource
	private IRedisCache redisCache;
	
	@Resource
	private DesUtils desUtils;
	
	@Resource
	private IAgentVisitService agentVisitService;
	
	@Resource
	private IWechatService wechatService;
	
	@Resource
	private IMallCardService mallCardService;
	
	@Resource
	private IGameTypeService gameTypeService;
	
	@Resource
	private ILaborUnionService laborUnionService;
	
	@Resource
	private IUploadService uploadService;
	
	@Value("${sms.cst_id}")
	private String smsCstid;
	
	@Value("${sms.secret}")
	private String smsSecret;
	
	@Value("${sms.template.findPassword}")
	private String smsTemplateFindPassword;
	
	@RequestMapping(method = RequestMethod.GET, value = "/time/now/")
	String now(){
		return new Date().getTime()+"";
	}

	*//**
	 * 正常登录
	 * @param username
	 * @param password
	 * @param platform
	 * @param verfiyCode
	 * @param openid
	 * @return
	 *//*
	@RequestMapping(value = "/oauth/token", method = RequestMethod.GET)
	SuccessMessage oauthToken(String username,String password,
			String platform,
			String verfiyCode,String openid){
		Boolean isCorrectPwd = null; 
		if(StringUtils.isBlank(openid)){
			
			if(!"app".equals(platform)
					&& !"wechatOA".equals(platform)){
				String verifycode = (String) request.getSession().getAttribute(WebConstants.SYS_VERIFYCODE);
				logger.debug("verifycode={},verfiyCode={}",verifycode,verfiyCode);
				if(verifycode == null || !verifycode.equalsIgnoreCase(verfiyCode)){
					throw new I18nMessageException("10001", "验证码不正确");
				}
				request.getSession().removeAttribute(WebConstants.SYS_VERIFYCODE);
			}
			if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
				throw new I18nMessageException("10001", "验证码不正确");
			}
			String key = CacheKeyConstant.CACHEKEY_LOGIN_ACCOUNT_LOCK + "." + username;
			String value = (String) redisCache.get(key);
			if (StringUtils.isNotBlank(value) && "LOCK".equals(value)) {
				throw new I18nMessageException("10005",new String[]{CacheKeyConstant.ACCOUNT_ERROR_LOCK_TIME/3600+"",redisCache.ttl(key)/60+1+""});
			}
			Integer errorCount = 0;
			if(StringUtils.isNumeric(value)){
				errorCount = Integer.parseInt(value);
			}
			
			String account = username,pw = password,passwd = null;
			if(!"wechatOA".equals(platform)){
				try {
					account = desUtils.decrypt(username);
					passwd = desUtils.decrypt(password);
				} catch (Exception e) {
					IncrErrorCount(username, value);
					throw new I18nMessageException("10002", new String[]{CacheKeyConstant.ACCOUNT_ERROR_COUNT+"",CacheKeyConstant.ACCOUNT_ERROR_LOCK_TIME/3600+"",(CacheKeyConstant.ACCOUNT_ERROR_COUNT-errorCount-1)+""});
				}
				pw = PwdUtil.getMd5Password(account, passwd);
				logger.info("username={},password={},pw={}",account,passwd,pw);
			}
			
			Agent a = accountService.login(account, pw);
			if(a == null){
				IncrErrorCount(username, value);
				throw new I18nMessageException("10002", new String[]{CacheKeyConstant.ACCOUNT_ERROR_COUNT+"",CacheKeyConstant.ACCOUNT_ERROR_LOCK_TIME/3600+"",(CacheKeyConstant.ACCOUNT_ERROR_COUNT-errorCount-1)+""});
			}
			if("N".equals(a.getEnable())){
				throw new I18nMessageException("10014","账户已被禁用，请联系管理员");
			}
			
			if(passwd != null){
				isCorrectPwd = !PwdUtil.isCorrectPwd(passwd);
			}
			openid = oauth2SecuritySubject.getOpenid(account);
		}
		Oauth2 oauth2 = oauth2SecuritySubject.refreshToken(request,openid);
		GlobalToken.setToken(oauth2.getAssess_token());
		
		Agent agent = oauth2SecuritySubject.getCurrentUser();
		Map<String,Object> json = new TreeMap<String,Object>();
		json.put("access_token", oauth2.getAssess_token());
		json.put("openid", oauth2.getOpenid());
		json.put("expires_in", oauth2.getExpires_in());
		if(isCorrectPwd != null && isCorrectPwd) {
			json.put("isUpdate", "N");
	    } else {
	    	json.put("isUpdate", agent.getIsUpdate());
	    }
		return new SuccessMessage(json);
	}
	
	*//**
	 * 微信授权登录
	 * @param code
	 * @param state
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/oauth/token/wechatOA", method = RequestMethod.GET)
	SuccessMessage oauthTokenForWechatOA(String code,String state) throws Exception{
		logger.info("wechatOA|code={},state={}",code,state);
		AccessToken accessToken = wechatService.getAccessToken(code, state);
		if(accessToken == null || StringUtils.isBlank(accessToken.getOpenid())){
			throw new I18nMessageException("40001","微信授权失败");
		}
		String openid = accessToken.getOpenid();
		Agent agent = accountService.findAgentInfo(openid,state);
		if(agent == null){
			SuccessMessage error = new SuccessMessage();
			error.setErrcode("40004");
			error.setErrmsg("当前微信号未绑定代理帐号，无法登录");
			error.setData(accessToken);
			return error;
		}
		if("N".equals(agent.getEnable())){
			throw new I18nMessageException("10014","账户已被禁用，请联系管理员");
		}
		SuccessMessage success = oauthToken(agent.getAccount(), agent.getPassword(), "wechatOA", null, null);
		Map<String,Object> json = (Map<String, Object>) success.getData();
		json.put("wechat", accessToken);
		return success;
	}
	
	private void IncrErrorCount(String account,String value) {
		// 一天中登录失败10次，账号锁定2小时
		String key = CacheKeyConstant.CACHEKEY_LOGIN_ACCOUNT_LOCK + "." + account;
		if(StringUtils.isBlank(value)) {
			redisCache.set(key, "1", CacheKeyConstant.ACCOUNT_ERROR_CHECK_EXPIRE);
		} else {
			int count = Integer.parseInt(value) + 1;
			if(count >= CacheKeyConstant.ACCOUNT_ERROR_COUNT) {
				String lockKey = CacheKeyConstant.CACHEKEY_LOGIN_ACCOUNT_LOCK+"."+account;
				redisCache.set(lockKey, "LOCK", CacheKeyConstant.ACCOUNT_ERROR_LOCK_TIME);
				redisCache.del(key);
			} else {
				redisCache.set(key, count+"", CacheKeyConstant.ACCOUNT_ERROR_CHECK_EXPIRE);
			}
		}
	}
	
	@RequestMapping(value = "/encrypt", method = RequestMethod.GET)
	public SuccessMessage encrypt(String encrypts[]) throws Exception{
		if(encrypts != null && encrypts.length < 10){
			StringBuffer bf = new StringBuffer();
			for(int i=0;i<encrypts.length;i++){
				String str = encrypts[i];
				bf.append(desUtils.encrypt(str));
				if(i < encrypts.length-1){
					bf.append(",");
				}
				logger.debug("str[{}]={}",i,str);
			}
			return new SuccessMessage(bf.toString().split(","));
		}
		throw new I18nMessageException("500");
	}
	
	*//**
	 * 生成验证码
	 * 
	 * @throws IOException
	 *//*
	@RequestMapping(value="/getCaptcher")
	public void getCaptcher(String emid) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		//验证码等级
		String code = CommonUtil.getVerifyCode(output, 1);
		//当前页面刷验证码的次数
		
		if(StringUtils.isNotBlank(emid)){
			redisCache.set("agent:vfcode:"+emid, code, 20*60L);
		}else{
			logger.debug("code={}",code);
			request.getSession().setAttribute(WebConstants.SYS_VERIFYCODE, code);
		}
		try {
			ServletOutputStream out = response.getOutputStream();
			output.writeTo(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 获得省下拉
	 * @return
	 *//*
	@RequestMapping(method = RequestMethod.GET, value = "/provinces")
	SuccessMessage provinces(){
		return new SuccessMessage(serverCityService.provinces());
	}
	
	*//**
	 * 根据省code，获得城市下拉
	 * @param provincecode
	 * @return
	 *//*
	@RequestMapping(method = RequestMethod.GET, value = "/citys/{provincecode}")
	SuccessMessage citys(@PathVariable(value="provincecode")String provincecode){
		return new SuccessMessage(serverCityService.citys(provincecode));
	}
	
	*//**
	 * 根据省市获得区县
	 * @param province
	 * @param citycode
	 * @return
	 *//*
	@RequestMapping(method = RequestMethod.GET, value = "/districts/{citycode}")
	SuccessMessage districts(@PathVariable(value="citycode")String citycode){
		return new SuccessMessage(serverCityService.districts(citycode));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/sendSMS/{mobile}")
	SuccessMessage sendSMS(@PathVariable(value="mobile")String mobile,
			String verfiyCode,String emid){
		
		if(!ValidateUtil.checkTel(mobile)){
			throw new I18nMessageException("10011", "手机号格式不正确！");
		}
		
		String key = "agent:sms:oneminute:"+mobile;
		if(redisCache.exists(key)){
			String ttl = ""+redisCache.ttl(key);
			SuccessMessage sm = new SuccessMessage();
			sm.setErrcode("10012");
			sm.setErrmsg("发送次数过于频繁，请"+ttl+"秒后再试！");
			sm.setData(ttl);
			return sm;
		}
		
		if(StringUtils.isBlank(emid)){
			String verifycode = (String) request.getSession().getAttribute(WebConstants.SYS_VERIFYCODE);
			if(verifycode == null || !verifycode.equalsIgnoreCase(verfiyCode)){
				throw new I18nMessageException("10001", "验证码不正确");
			}
			request.getSession().removeAttribute(WebConstants.SYS_VERIFYCODE);
		}else{
			String verifycode = (String) redisCache.get("agent:vfcode:"+emid);
			if(verifycode == null || !verifycode.equals(verfiyCode)){
				throw new I18nMessageException("10001", "验证码不正确");
			}
			redisCache.del("agent:vfcode:"+emid);
		}
		String sign = MD5.md5(mobile + smsCstid + smsSecret);//生成签名数据
		SuccessMessage sm = smsGatewayService.send(mobile, smsTemplateFindPassword, smsCstid, sign, null);
		if("0".equals(sm.getErrcode())){
			redisCache.set(key, "success", 60l);
		}
		return sm;
	}
	
	@RequestMapping(value="/checkVfCode",method=RequestMethod.GET)
	public SuccessMessage checkVfCode(String mobile,String smsVfcode) throws Exception{
		return smsGatewayService.checkVfCode(mobile, smsVfcode);
	}
	
	
	@RequestMapping(value="/resetPassword",method=RequestMethod.POST)
	public SuccessMessage resetPassword(String mobile,String password,String confirm,String smsVfcode) throws Exception{
		
		SuccessMessage sgsm = smsGatewayService.checkVfCode(mobile, smsVfcode);
		if(sgsm == null || !"0".equals(sgsm.getErrcode())){
			return sgsm;
		}
		
		password = desUtils.decrypt(password);
		confirm = desUtils.decrypt(confirm);
		logger.debug("resetPassword|mobile={},password={},confirm={},smsVfcode={}", mobile, password, confirm,smsVfcode);
		
		if (!PwdUtil.isCorrectPwd(password)) {
			throw new I18nMessageException("10007","帐号密码不符合规则");
		}

		Agent agent = accountService.findByMobile(mobile);
		logger.debug("resetPassword|agent={}", new Gson().toJson(agent));
		
		if(agent==null){
			throw new I18nMessageException("10006","找不到代理帐号!");
		}
		
		agent = accountService.findAgentByAgentid(agent.getAgentId());
		
		if(!password.equals(confirm)){
			throw new I18nMessageException("10004","修改失败，前后密码不一致！");
		}
		String pw = PwdUtil.getMd5Password(agent.getAccount(), password);
		Agent a = new Agent();
		a.setPassword(pw);
		a.setAgentId(agent.getAgentId());
		a.setGameId(agent.getGameId());
		return accountService.updateMyInfo(agent.getAgentId(),a);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/agentVisitCacheEvict/")
	SuccessMessage agentVisitCacheEvict(){
		return agentVisitService.cacheEvict();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/version/",produces="application/javascript;charset=UTF-8")
	String version(){
		String version = DateFormatUtils.format(new Date(), "yyyyMMDDHHmmss"),key = "agent:statics:version";
		if(redisCache.exists(key)){
			version = (String) redisCache.get(key);
		}else{
			redisCache.set(key, version);
		}
		return "var static_version = \""+version+"\";";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/version/refresh/")
	SuccessMessage refreshVersion(){
		String version = DateFormatUtils.format(new Date(), "yyyyMMDDHHmmss"),key = "agent:statics:version";
		redisCache.set(key, version);
		return new SuccessMessage(redisCache.get(key));
	}
	
	@RequestMapping(value = "/deleteCache/gameConfig/{gameId}/{scope}/", method = RequestMethod.GET)
	SuccessMessage deleteCacheForGameConfig(@PathVariable("gameId") String gameId,
			@PathVariable("scope")String scope){
		return gameTypeService.deleteCache(gameId, scope);
	}
	

	*//**
	 * 查询工会信息
	 * 
	 * @param agentId 代理ID
	 * @param unionId 工会ID
	 * @return
	 *//*
	@RequestMapping(value = "/findUnion/{agentId}/{unionId}/", method = RequestMethod.GET)
	SuccessMessage findLaborUnion(@PathVariable("agentId") Integer agentId,
			@PathVariable("unionId") Integer unionId) {
		LaborUnion lu = laborUnionService.findLaborUnion(agentId, unionId);
		return new SuccessMessage(lu);
	}
	
	*//**
	 * 玩家申请加入工会
	 * 
	 * @param userId 玩家ID
	 * @param unionId 工会ID
	 * @return
	 *//*
	@RequestMapping(value = "/applyUnion/{agentId}/{userId}/{unionId}/", method = RequestMethod.GET)
	SuccessMessage applyLaborUnion(
			@PathVariable("agentId") Integer agentId,
			@PathVariable("userId") Integer userId,
			@PathVariable("unionId") Integer unionId) {
		return laborUnionService.userApplyJoinUnion(agentId, unionId, userId);
	}
	
	
	@RequestMapping(value = "/upload/encode/", method = RequestMethod.GET)
	SuccessMessage uploadEncode(
			@RequestParam(value="clubImgPath",required=false) String clubImgPath,
			@RequestParam(value="agentId",required=true) String agentId,
			@RequestParam(value="clubId",required=true) String clubId,
			@RequestParam(value="gameId",required=true) String gameId,
			@RequestParam(value="ckey",required=false) String ckey
			){
		return uploadService.uploadEncode(clubImgPath, agentId, clubId, gameId, ckey);
	}
	
	
}
*/