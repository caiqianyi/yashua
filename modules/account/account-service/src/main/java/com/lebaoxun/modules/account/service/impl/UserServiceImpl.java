package com.lebaoxun.modules.account.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.lebaoxun.commons.utils.ValidatorUtils;
import com.lebaoxun.modules.account.dao.UserDao;
import com.lebaoxun.modules.account.dao.UserLogDao;
import com.lebaoxun.modules.account.em.UserLogAction;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.entity.UserLogEntity;
import com.lebaoxun.modules.account.service.UserService;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
import com.lebaoxun.security.oauth2.entity.Oauth2UserLog;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	@Resource
	private UserLogDao userLogDao;
	
	@Resource
	private Oauth2SecuritySubject oauth2Security;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String account = (String) params.get("account");
        Page<UserEntity> page = this.selectPage(
                new Query<UserEntity>(params).getPage(),
                new EntityWrapper<UserEntity>().eq(StringUtils.isNotBlank(account), "account", account)
        );

        return new PageUtils(page);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void lock(Long userId, String scope, Long adminId) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		String status = "N",adjunctInfo = null;
		UserLogAction logType = UserLogAction.A_LOCK;
		if("N".equals(user.getStatus())){
			logType = UserLogAction.A_UNLOCK;
			status = "Y";
			adjunctInfo = adminId+"";
		}
		
		insertLog(user, logType, scope, logType.getDescr(), adjunctInfo);
		
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setStatus(status);
		this.updateById(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void modifyPassword(Long userId, String newPasswd,
			String scope, Long adminId) {
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
		insertLog(user, logType, scope, logType.getDescr(), adjunctInfo);
		
		String passwd = PwdUtil.getMd5Password(user.getAccount(), newPasswd);
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setPassword(passwd);
		this.updateById(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void modifyBalance(Long userId, Integer amount,String scope, 
			Long adminId, String descr) {
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
		insertLog(user, logType, scope, descr, adjunctInfo);
		
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setBalance(user.getBalance()+amount);
		this.updateById(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void modifyInfo(Long userId, UserEntity q,
			String scope, Long adminId, String descr) {
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
		insertLog(user, logType, scope, logType.getDescr(), adjunctInfo);
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
	public void bindMobile(Long userId,String scope, String mobile, String password) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(mobile) || ValidatorUtils.checkTel(mobile)){
			throw new I18nMessageException("-1","手机号格式不正确！");
		}
		
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		UserLogAction logType = UserLogAction.U_BIND_MOBILE;
		insertLog(user, logType, scope, logType.getDescr());
		
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
	public void bindOpenid(Long userId, String openid,String scope) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user == null){
			throw new I18nMessageException("500");
		}
		
		UserLogAction logType = UserLogAction.U_BIND_OPENID;
		insertLog(user, logType, scope, logType.getDescr());
		
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setOpenid(openid);
		this.updateById(entity);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void wechatOARegister(Long userId, UserEntity q, String scope) {
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
		insertLog(entity, logType, scope, new Gson().toJson(q));
		
		this.insert(entity);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void loginLog(Long userId, String scope, UserLogAction logType, String adjunctInfo,
			String descr) {
		// TODO Auto-generated method stub
		UserEntity user = this.selectOne( new EntityWrapper<UserEntity>().eq("user_id", userId));
		if(user != null){
			throw new I18nMessageException("500");
		}
		insertLog(user, logType, scope, descr, adjunctInfo);
	}
	
	void insertLog(UserEntity user,UserLogAction logType,String scope,String descr,String adjunctInfo){
		UserLogEntity log = new UserLogEntity();
		if(adjunctInfo != null){
			log.setAdjunctInfo(adjunctInfo);
		}
		log.setAccount(user.getAccount());
		log.setCreateTime(new Date());
		log.setDescr(descr);
		log.setLogType(logType.toString());
		log.setMoney(user.getBalance());
		log.setTradeMoney(0);
		log.setUserId(user.getUserId());
		
		Oauth2UserLog userLog = oauth2Security.getSessionUser(user.getAccount(), scope);
		if(userLog != null){
			log.setHostIp(userLog.getHost());
			log.setPlatform(userLog.getPlatformSource());
		}
		userLogDao.insert(log);
	}
	
	void insertLog(UserEntity user,UserLogAction logType,String scope,String descr){
		insertLog(user, logType, scope, descr, null);
	}

}
