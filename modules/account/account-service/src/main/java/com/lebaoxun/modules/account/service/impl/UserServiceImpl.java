package com.lebaoxun.modules.account.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.PwdUtil;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.commons.utils.StringUtils;
import com.lebaoxun.commons.utils.ValidatorUtils;
import com.lebaoxun.modules.account.dao.UserDao;
import com.lebaoxun.modules.account.dao.UserLogDao;
import com.lebaoxun.modules.account.em.UserLogAction;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.entity.UserLogEntity;
import com.lebaoxun.modules.account.service.UserService;
import com.lebaoxun.soa.amqp.core.sender.IRabbitmqSender;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private UserLogDao userLogDao;
	
	@Value("${security.md5.password}")
	private String passwdSecret;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String account = (String) params.get("account");
        Page<UserEntity> page = this.selectPage(
                new Query<UserEntity>(params).getPage(),
                new EntityWrapper<UserEntity>().like(StringUtils.isNotBlank(account), "account", account)
        );

        return new PageUtils(page);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void save(Long adminId,UserEntity user) {
    	// TODO Auto-generated method stub
    	
    	UserEntity u = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", user.getUserId()));
		if(u != null){
			throw new I18nMessageException("-1","用户ID已存在");
		}
		
		if(StringUtils.isBlank(user.getMobile()) || !ValidatorUtils.checkTel(user.getMobile())){
			throw new I18nMessageException("-1","手机号格式不正确！");
		}
		
		u = this.selectOne( new EntityWrapper<UserEntity>().eq("account", user.getAccount()).or().eq("mobile", user.getMobile()));
		if(u != null){
			if(u.getAccount().equals(user.getAccount())){
				throw new I18nMessageException("-1","账号已存在！");
			}
			
			if(u.getMobile().equals(user.getMobile())){
				throw new I18nMessageException("-1","手机号已存在！");
			}
		}
		logger.debug("passwdSecret={},account={},password={}",passwdSecret,user.getAccount(), user.getPassword());
		String passwd = PwdUtil.getMd5Password(passwdSecret,user.getAccount(), user.getPassword());
		
    	user.setCreateTime(new Date());
    	user.setBalance(0);
    	user.setLevel(0);
    	user.setPassword(passwd);
    	this.baseMapper.insert(user);
    }
    
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void lock(Long userId, Long adminId) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		String status = "N",adjunctInfo = adminId+"";
		UserLogAction logType = UserLogAction.A_LOCK;
		if("N".equals(user.getStatus())){
			logType = UserLogAction.A_UNLOCK;
			status = "Y";
		}
		
		insertLog(user, logType, 0, logType.getDescr(), adjunctInfo);
		
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setStatus(status);
		this.updateById(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void modifyPassword(Long userId, String newPasswd,
			Long adminId) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		String adjunctInfo = null;
		UserLogAction logType = UserLogAction.U_MODIFY_PASSWD;
		if(adminId != null){
			adjunctInfo = adminId+"";
			logType = UserLogAction.A_MODIFY_PASSWD;
		}
		insertLog(user, logType, 0, logType.getDescr(), adjunctInfo);
		
		String passwd = PwdUtil.getMd5Password(passwdSecret,user.getAccount(), newPasswd);
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setPassword(passwd);
		this.updateById(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void modifyBalance(Long userId,Integer amount, String descr, Long adminId) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		UserLogAction logType;
		String adjunctInfo = null;
		if(amount > 0){
			logType = UserLogAction.U_BALANCE_ADD;
			if(adminId != null){
				adjunctInfo = adminId+"";
				logType = UserLogAction.A_BALANCE_ADD;
			}
		}else{
			logType = UserLogAction.U_BALANCE_REDUCE;
			if(adminId != null){
				adjunctInfo = adminId+"";
				logType = UserLogAction.A_BALANCE_REDUCE;
			}
		}
		if(StringUtils.isBlank(descr)){
			descr = logType.getDescr();
		}
		insertLog(user, logType, amount, descr, adjunctInfo);
		
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setBalance(user.getBalance()+amount);
		this.updateById(entity);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void modifyHeadimgurl(Long userId, String headimgurl) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		UserLogAction logType = UserLogAction.U_MODIFY_INFO;
		insertLog(user, logType, 0, logType.getDescr(), headimgurl);
		
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setHeadimgurl(headimgurl);
		this.updateById(entity);
	}
	
	@Resource
	private IRabbitmqSender rabbitmqSender;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void modifyInfo(Long userId, UserEntity q,
			Long adminId, String descr) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		UserLogAction logType = UserLogAction.U_MODIFY_INFO;
		String adjunctInfo = null;
		if(adminId != null){
			adjunctInfo = adminId+"";
			logType = UserLogAction.A_MODIFY_INFO;
		}
		insertLog(user, logType, null, logType.getDescr(), adjunctInfo);
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setCity(q.getCity());
		entity.setCountry(q.getCountry());
		entity.setGroupid(q.getGroupid());
		entity.setHeadimgurl(user.getHeadimgurl());
		entity.setLanguage(q.getLanguage());
		entity.setNickname(q.getNickname());
		entity.setProvince(q.getProvince());
		entity.setRemark(q.getRemark());
		entity.setSex(q.getSex());
		entity.setMobile(q.getMobile());
		entity.setRealname(q.getRealname());
		entity.setIdentity(q.getIdentity());
		entity.setBirthday(q.getBirthday());
		entity.setAreaCode(q.getAreaCode());
		
		//个人信息 第一次编辑完善成功之后加100积分 ，积分明细列表显示完善资料+
		Map<String,String> msg = new HashMap<String,String>();
		String timestamp = System.currentTimeMillis()+"";
		msg.put("userId", user.getId()+"");
		msg.put("logTime", timestamp);
		msg.put("logType", "ACCOUNT_MODIFY_AWARD");
		msg.put("rechargeFee", 100+"");
		msg.put("descr", "完善资料+");
		msg.put("adjunctInfo", user.getId()+"");
		
		rabbitmqSender.sendContractDirect("account.balance.queue.rechage",
				new Gson().toJson(msg));
		this.updateById(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void bindMobile(Long userId,String mobile, String password) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(mobile) || !ValidatorUtils.checkTel(mobile)){
			throw new I18nMessageException("-1","手机号格式不正确！");
		}
		
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		UserLogAction logType = UserLogAction.U_BIND_MOBILE;
		insertLog(user, logType, logType.getDescr());
		
		String passwd = PwdUtil.getMd5Password(passwdSecret,mobile, password);
		
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setAccount(mobile);
		entity.setMobile(mobile);
		entity.setPassword(passwd);
		this.updateById(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void bindOpenid(Long userId, String openid, String unionid) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		
		UserLogAction logType = UserLogAction.U_BIND_OPENID;
		insertLog(user, logType, logType.getDescr());
		
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		if(StringUtils.isNotBlank(openid)) {
			entity.setOpenid(openid);
		}
		if(StringUtils.isNotBlank(unionid)) {
			entity.setUnionid(unionid);
		}
		this.updateById(entity);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void wechatOARegister(Long userId, UserEntity q) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user != null){
			throw new I18nMessageException("500");
		}
		UserEntity entity = new UserEntity();
		entity.setCity(q.getCity());
		entity.setCountry(q.getCountry());
		entity.setGroupid(q.getGroupid());
		entity.setHeadimgurl(q.getHeadimgurl());
		entity.setLanguage(q.getLanguage());
		entity.setNickname(q.getNickname());
		entity.setProvince(q.getProvince());
		entity.setRemark(q.getRemark());
		entity.setSex(q.getSex());
		entity.setBalance(0);
		entity.setCreateTime(new Date());
		entity.setOpenid(q.getOpenid());
		entity.setUnionid(q.getUnionid());
		entity.setType(q.getType());
		entity.setSource("WECHATOA");
		entity.setUserId(userId);
		entity.setStatus("Y");
		
		UserLogAction logType = UserLogAction.U_WECHATOA_REGISTER;
		insertLog(entity, logType, 0, null, new Gson().toJson(q));
		
		this.insert(entity);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void loginLog(Long userId, UserLogAction logType, String adjunctInfo,
			String descr) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		insertLog(user, logType, 0, descr, adjunctInfo);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public UserEntity recharge(Long userId, String orderNo, Long buyTime,
			String total_fee) {
		// TODO Auto-generated method stub
		BigDecimal fee = new BigDecimal(total_fee);
		
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
//		int count = userLogDao.selectCount(new EntityWrapper<UserLogEntity>().eq("user_id", userId).eq("adjunct_info", orderNo));
//		if(count > 0){//已完成充值
//			return null;
//		}
		Date time = new Date(buyTime);
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setBalance(user.getBalance() + fee.intValue());
		entity.setLastBuyTime(time);
		this.updateById(entity);
		return user;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public UserEntity rechargeForSys(Long userId, String logType, String adjunctInfo, String total_fee, Long logTime) {
		// TODO Auto-generated method stub
		BigDecimal fee = new BigDecimal(total_fee);
		
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		int count = userLogDao.selectCount(new EntityWrapper<UserLogEntity>().eq("user_id", userId).eq("log_type", logType).eq("adjunct_info", adjunctInfo));
		if(count > 0){//已完成充值
			return null;
		}
		Date time = new Date(logTime);
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setBalance(user.getBalance() + fee.intValue());
		entity.setLastBuyTime(time);
		this.updateById(entity);
		return user;
	}
	
	void insertLog(UserEntity user,UserLogAction logType,Integer tradeMoney,String descr,String adjunctInfo){
		UserLogEntity log = new UserLogEntity();
		if(adjunctInfo != null){
			log.setAdjunctInfo(adjunctInfo);
		}
		if(StringUtils.isBlank(descr)){
			descr = logType.getDescr();
		}
		log.setAccount(user.getAccount());
		log.setCreateTime(new Date());
		log.setDescr(descr);
		log.setLogType(logType.toString());
		log.setMoney(user.getBalance());
		log.setTradeMoney(tradeMoney);
		log.setUserId(user.getUserId());
		
		userLogDao.insert(log);
	}
	
	void insertLog(UserEntity user,UserLogAction logType,String descr){
		insertLog(user, logType, 0, descr, null);
	}

	@Override
	public long loginCount(Long userId) {
		long count=0;
		 try {
		 Calendar calendar = Calendar.getInstance();
		 calendar = Calendar.getInstance();  
		 calendar.add(Calendar.DATE, -1*7);
		 calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		 Date startDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar.getTime()));
		 calendar.add(Calendar.DAY_OF_MONTH,6);
		 Date endDate= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendar.getTime()));
		 count= userLogDao.loginCount(userId,startDate,endDate);
		 System.out.println(startDate+"wdwdwd"+endDate+"wewewe"+userId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		return count;
	}

}
