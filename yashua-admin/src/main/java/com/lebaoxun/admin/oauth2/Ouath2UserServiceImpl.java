package com.lebaoxun.admin.oauth2;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.lebaoxun.admin.config.AccountConstant;
import com.lebaoxun.manager.sys.entity.SysUserEntity;
import com.lebaoxun.pay.service.ISysUserService;
import com.lebaoxun.security.oauth2.IOuath2UserService;
import com.lebaoxun.security.oauth2.entity.Oauth2UserLog;
import com.lebaoxun.security.oauth2.entity.Oauth2VisitPath;

@Service("ouath2UserService")
public class Ouath2UserServiceImpl implements IOuath2UserService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private ISysUserService sysUserService;

	@Override
	public Long findByUsername(String username) {
		// TODO Auto-generated method stub
		SysUserEntity user = sysUserService.findByUsername(username);
		return user.getUserId();
	}

	@Override
	public void saveLoginLog(Oauth2UserLog log) {
		// TODO Auto-generated method stub
		logger.debug("login log={}",new Gson().toJson(log));
	}

	@Override
	public List<Oauth2VisitPath> findWhiteAccess() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getScope() {
		// TODO Auto-generated method stub
		return AccountConstant.SCOPE;
	}

}
