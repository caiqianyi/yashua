package com.lebaoxun.portal.rest;
/*package com.ylhy.agent.rest;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.loadtime.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.CommonUtil;
import com.lebaoxun.commons.utils.DateUtil;
import com.lebaoxun.commons.utils.DesUtils;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.commons.utils.JsonUtil;
import com.lebaoxun.commons.utils.MD5;
import com.lebaoxun.commons.utils.PwdUtil;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
import com.lebaoxun.sms.service.ISMSGatewayService;
import com.lebaoxun.soa.core.redis.IRedisCache;

@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

	@Resource
	private IAccountService accountService;

	@Resource
	private ITransferCardService transferCardService;

	@Resource
	private IServerCityService serverCityService;
	
	@Resource
	private IWechatService wechatService;
	
	@Resource
	private IUploadService uploadService;
	
	@Resource
	private DesUtils desUtils;
	
	@Resource
	private ISMSGatewayService smsGatewayService;
	
	@Resource
	private IRedisCache redisCache;
	
	@Value("${sms.cst_id}")
	private String smsCstid;
	
	@Value("${sms.secret}")
	private String smsSecret;
	
	@Value("${sms.template.register}")
	private String smsTemplateRegister;

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseMessage logout(){
		oauth2SecuritySubject.logout();
		return new ResponseMessage();
	}
	
	@RequestMapping(value = "/myInfo", method = RequestMethod.GET)
	public ResponseMessage myInfo() throws Exception {
		Agent agent = (Agent) oauth2SecuritySubject.getCurrentUser();
		return new ResponseMessage(agent);
	}

	@RequestMapping(value = "/transferCard/{userId}/{sellNum}", method = RequestMethod.GET)
	public ResponseMessage transferCard(
			@PathVariable(value = "userId") Integer userId,
			@PathVariable(value = "sellNum") Integer sellNum) throws Exception {
		Agent agent = (Agent) oauth2SecuritySubject.getCurrentUser();
		Integer agentId = agent.getAgentId();
		agent = accountService.findAgentByAgentid(agentId);
		logger.debug("myInfo-agentId={}", agentId);

		ParamTranscard paramTranscard = new ParamTranscard();
		paramTranscard.setServerCode(agent.getServerCode());
		paramTranscard.setCardNum(sellNum);
		paramTranscard.setUserid(userId);
		if("20001".equals(agent.getGameId())){
			paramTranscard.setCardType(EmCardType.FOUR.getKey());
		}else{
			paramTranscard.setCardType(EmCardType.EIGHT.getKey());
		}
		paramTranscard.setOperType(EmChargeType.BUY.getKey());
		paramTranscard.setHostIP(request.getHeader("user-agent") + " "
				+ CommonUtil.getClientIpAddr(request));

		ResponseMessage result = transferCardService.execute(paramTranscard,
				agentId);
		return result;
	}
	@RequestMapping(value = "/transferCard/toSubAgent/{subAgentId}/{sellNum}", method = RequestMethod.GET)
	public ResponseMessage toSubAgentTransferCard(
			@PathVariable(value = "subAgentId") Integer subAgentId,
			@PathVariable(value = "sellNum") Integer sellNum) throws Exception {
		Agent agent = (Agent) oauth2SecuritySubject.getCurrentUser();
		return transferCardService.toRechargeSubAgent(agent.getAgentId(), subAgentId, sellNum);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findMyRechargeRecordByTime")
	DataTables findMyRechargeRecordByTime(String start, String end,
			DataTables dataTables) {

		if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
			logger.debug(
					"start={},end={},iDisplayStart={},pageDisplayLength={}",
					start, end, dataTables.getiDisplayStart(),
					dataTables.getPageDisplayLength());
			Date s = DateUtil.parse(start), e = DateUtil.parse(end);
			String sm = DateUtil.dateFormatMonth(s), em = DateUtil
					.dateFormatMonth(e);
			logger.debug("sm={},em={}", sm, em);

			logger.debug("dataTables={}", JsonUtil.bean2Json(dataTables));
			// 判断是否跨越分查询的
			if (!sm.equals(em)) {
				throw new I18nMessageException("10201", "不支持跨月查询");
			}
		}
		Agent agent = (Agent) oauth2SecuritySubject.getCurrentUser();
		Integer agentId = agent.getAgentId();
		return accountService.findMyRechargeRecordByTime(agentId, start, end,
				dataTables);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findMyRewardRecordByTime")
	DataTables findMyRewardRecordByTime(String start, String end,
			DataTables dataTables) {

		if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
			logger.debug(
					"start={},end={},iDisplayStart={},pageDisplayLength={}",
					start, end, dataTables.getiDisplayStart(),
					dataTables.getPageDisplayLength());
			Date s = DateUtil.parse(start), e = DateUtil.parse(end);
			String sm = DateUtil.dateFormatMonth(s), em = DateUtil
					.dateFormatMonth(e);
			logger.debug("sm={},em={}", sm, em);
			// 判断是否跨越分查询的
			if (!sm.equals(em)) {
				throw new I18nMessageException("10201", "不支持跨越查询");
			}
		}
		logger.debug("dataTables={}", JsonUtil.bean2Json(dataTables));
		Agent agent = (Agent) oauth2SecuritySubject.getCurrentUser();
		Integer agentId = agent.getAgentId();
		return accountService.findMyRewardRecordByTime(agentId, start, end,
				dataTables);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findMySubAgent")
	DataTables findMySubAgent(DataTables dataTables) {
		Agent agent = (Agent) oauth2SecuritySubject.getCurrentUser();
		Integer agentId = agent.getAgentId();
		logger.debug("findMySubAgent|agentId={}", agentId);
		return accountService.findMySubAgent(agentId, dataTables);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editMyInfo")
	ResponseMessage editMyInfo(String nickName) {
		Agent agent = (Agent) oauth2SecuritySubject.getCurrentUser();
		Integer agentId = agent.getAgentId();
		Agent update = new Agent();
		update.setAgentId(agentId);
		update.setNickName(nickName);
		update.setGameId(agent.getGameId());
		ResponseMessage sm = accountService.updateMyInfo(agentId,update);
		return sm;
	}

	@RequestMapping(value = "/editPassword", method = RequestMethod.POST)
	public ResponseMessage editPassword(String prepsw, String newpsw) {
		String newpasswd, passwd;
		logger.info("prepsw={},newpsw={}", prepsw, newpsw);
		try {
			passwd = desUtils.decrypt(prepsw);
			newpasswd = desUtils.decrypt(newpsw);
		} catch (Exception e) {
			e.printStackTrace();
			throw new I18nMessageException("500", "非法请求", e);
		}
		logger.info("newpasswd={},passwd={}", newpasswd, passwd);
		Agent currentAgent = (Agent) oauth2SecuritySubject.getCurrentUser();

		String pw = PwdUtil.getMd5Password(currentAgent.getAccount(), passwd);
		String npw = PwdUtil.getMd5Password(currentAgent.getAccount(),
				newpasswd);
		logger.info("npw={},pw={}", npw, pw);

		if (!pw.equals(currentAgent.getPassword())) {
			throw new I18nMessageException("10004", "原密码不对");
		}

		Agent agent = new Agent();
		agent.setAgentId(currentAgent.getAgentId());
		agent.setPassword(npw);
		agent.setGameId(currentAgent.getGameId());
		agent.setIsUpdate("11");
		ResponseMessage sm = accountService.updateMyInfo(currentAgent.getAgentId(),agent);
		return sm;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findSellCardRecord")
	DataTables findSellCardRecord(@RequestParam(value = "start") String start,
			@RequestParam(value = "end") String end, DataTables dataTables) {
		logger.debug("start={},end={},iDisplayStart={},pageDisplayLength={}",
				start, end, dataTables.getiDisplayStart(),
				dataTables.getPageDisplayLength());
		Agent currentAgent = (Agent) oauth2SecuritySubject.getCurrentUser();
		Integer agentId = currentAgent.getAgentId();
		return accountService.findSellCardRecordByAgentId(agentId, start, end,
				dataTables);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findSellCardRecord/{agentId}")
	DataTables findSellCardRecord(@PathVariable(value="agentId")Integer agentId,@RequestParam(value = "start") String start,
			@RequestParam(value = "end") String end, DataTables dataTables) {
		logger.debug("start={},end={},iDisplayStart={},pageDisplayLength={}",
				start, end, dataTables.getiDisplayStart(),
				dataTables.getPageDisplayLength());
		Agent currentAgent = (Agent) oauth2SecuritySubject.getCurrentUser();
		if(!"GA".equals(currentAgent.getType())){
			throw new I18nMessageException("20010");
		}
		Agent subAgent = accountService.findAgentByAgentid(agentId);
		if(subAgent == null || !subAgent.getServerCode().equals(currentAgent.getServerCode())
				|| !subAgent.getLevel1().equals(currentAgent.getAgentId())){
			throw new I18nMessageException("500");
		}
		return accountService.findSellCardRecordByAgentId(agentId, start, end,
				dataTables);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findAllSellCardRecordByAgentId/")
	List<PlayerCharge> findAllSellCardRecordByAgentId(){
		Agent currentAgent = (Agent) oauth2SecuritySubject.getCurrentUser();
		return accountService.findAllSellCardRecordByAgentId(currentAgent.getAgentId(),currentAgent.getServerCode());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findMyServer")
	List<ServerCity> findMyServer() {
		Agent currentAgent = (Agent) oauth2SecuritySubject.getCurrentUser();
		return serverCityService.findServerProvinceByServerCode(currentAgent
				.getServerCode());
	}

	@RequestMapping(value = "/addSubAgent", method = RequestMethod.POST)
	public ResponseMessage addSubAgent(Agent agent, String checkcode)
			throws Exception {

		String verifycode = (String) request.getSession().getAttribute(
				WebConstants.SYS_VERIFYCODE);
		if (verifycode == null || !verifycode.equals(checkcode)) {
			throw new I18nMessageException("10001", "验证码不正确");
		}
		request.getSession().removeAttribute(WebConstants.SYS_VERIFYCODE);

		String password = GenerateCode.gen(6) + "";
		String hostIp = request.getHeader("user-agent") + " "
				+ CommonUtil.getClientIpAddr(request);
		agent.setHost_ip(hostIp);
		agent.setPassword(PwdUtil.getMd5Password(agent.getTel(), password));
		Agent user = (Agent) oauth2SecuritySubject.getCurrentUser();
		Agent newAgent = accountService.register(agent, user.getAgentId());

		if (newAgent == null) {
			throw new I18nMessageException("500");
		}
		return new ResponseMessage("ok");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cashback")
	DataTables findRecordByAgentId(@RequestParam(value = "start") String start,
			@RequestParam(value = "end") String end, DataTables dataTables) {
		Agent currentAgent = (Agent) oauth2SecuritySubject.getCurrentUser();
		Integer agentId = currentAgent.getAgentId();
		return accountService.findRecordByAgentId(agentId, 1, start, end,
				dataTables);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/sendRegisterSMS/{mobile}")
	ResponseMessage sendSMS(@PathVariable(value="mobile")String mobile,
			String verfiyCode,String emid){
		
		if(!ValidateUtil.checkTel(mobile)){
			throw new I18nMessageException("10011", "手机号格式不正确！");
		}
		
		String key = "agent:sms:oneminute:"+mobile;
		if(redisCache.exists(key)){
			String ttl = ""+redisCache.ttl(key);
			ResponseMessage sm = new ResponseMessage();
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
		ResponseMessage sm = smsGatewayService.send(mobile, smsTemplateRegister, smsCstid, sign, null);
		if("0".equals(sm.getErrcode())){
			redisCache.set(key, "success", 60l);
		}
		return sm;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/registerByGAgent/{smsVfcode}")
	ResponseMessage registerByGAgent(Agent agent,@PathVariable(value="smsVfcode")String smsVfcode){
		Agent currentAgent = (Agent) oauth2SecuritySubject.getCurrentUser();
		if(!"GA".equals(currentAgent.getType())){
			throw new I18nMessageException("20010");
		}
		logger.debug("agent={}",new Gson().toJson(agent));
		return accountService.registerByGAgent(agent, currentAgent.getAgentId(), smsVfcode);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findAnalysAgentCard/{agentId}")
	List<AnalyseAgentCard> findAnalysAgentCard(@PathVariable(value="agentId")Integer agentId){
		Agent currentAgent = (Agent) oauth2SecuritySubject.getCurrentUser();
		if(!"GA".equals(currentAgent.getType())){
			throw new I18nMessageException("20010");
		}
		Agent subAgent = accountService.findAgentByAgentid(agentId);
		if(subAgent == null || !subAgent.getServerCode().equals(currentAgent.getServerCode())
				|| !subAgent.getLevel1().equals(currentAgent.getAgentId())){
			throw new I18nMessageException("500");
		}
		return accountService.findAnalysAgentCard(agentId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/bindWechat")
	ResponseMessage bindWechat(String openid,String state){
		if(StringUtils.isBlank(openid)){
			throw new I18nMessageException("500","参数错误");
		}
		Agent agent = (Agent) oauth2SecuritySubject.getCurrentUser();
		String userId = agent.getUserid();
		if(StringUtils.isNotBlank(userId)){
			throw new I18nMessageException("40002","代理帐号已绑定微信");
		}
		if(accountService.findAgentInfo(openid,state) != null){
			throw new I18nMessageException("40003","该微信已绑定代理帐号");
		}
		Agent update = new Agent();
		update.setAgentId(agent.getAgentId());
		update.setUserid(openid);
		update.setGameId(agent.getGameId());
		return accountService.updateMyInfo(agent.getAgentId(),update);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/unBindWechat")
	ResponseMessage unBindWechat(@RequestParam("smsVfcode") String smsVfcode){
		Agent agent = (Agent) oauth2SecuritySubject.getCurrentUser();
		String userId = agent.getUserid();
		if(StringUtils.isBlank(userId)){
			throw new I18nMessageException("40005","未绑定微信帐号");
		}
		ResponseMessage sgsm = smsGatewayService.checkVfCode(agent.getTel(), smsVfcode);
		if(sgsm == null || !"0".equals(sgsm.getErrcode())){
			return sgsm;
		}
		return accountService.unbindWechat(agent.getAgentId());
	}
}
*/