package com.lebaoxun.modules.account.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.PwdUtil;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.commons.utils.ValidatorUtils;
import com.lebaoxun.modules.account.dao.UserDao;
import com.lebaoxun.modules.account.dao.UserLogDao;
import com.lebaoxun.modules.account.em.UserLogAction;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.entity.UserLogEntity;
import com.lebaoxun.modules.account.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	@Resource
	private UserLogDao userLogDao;
	
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
		String passwd = PwdUtil.getMd5Password(user.getAccount(), user.getPassword());
		
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
		
		String passwd = PwdUtil.getMd5Password(user.getAccount(), newPasswd);
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
		entity.setHeadimgurl(q.getHeadimgurl());
		entity.setLanguage(q.getLanguage());
		entity.setNickname(q.getNickname());
		entity.setProvince(q.getProvince());
		entity.setRemark(q.getRemark());
		entity.setSex(q.getSex());
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
		
		String passwd = PwdUtil.getMd5Password(mobile, password);
		
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setAccount(mobile);
		entity.setMobile(mobile);
		entity.setPassword(passwd);
		this.updateById(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void bindOpenid(Long userId, String openid) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		
		UserLogAction logType = UserLogAction.U_BIND_OPENID;
		insertLog(user, logType, logType.getDescr());
		
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setOpenid(openid);
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
		insertLog(entity, logType, new Gson().toJson(q));
		
		this.insert(entity);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void loginLog(Long userId, UserLogAction logType, String adjunctInfo,
			String descr) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user != null){
			throw new I18nMessageException("500");
		}
		insertLog(user, logType, 0, descr, adjunctInfo);
	}
	
	void insertLog(UserEntity user,UserLogAction logType,Integer tradeMoney,String descr,String adjunctInfo){
		UserLogEntity log = new UserLogEntity();
		if(adjunctInfo != null){
			log.setAdjunctInfo(adjunctInfo);
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

}
