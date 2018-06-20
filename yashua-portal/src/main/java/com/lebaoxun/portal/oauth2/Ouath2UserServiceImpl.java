package com.lebaoxun.portal.oauth2;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.IUserService;
import com.lebaoxun.security.oauth2.IOuath2UserService;
import com.lebaoxun.security.oauth2.entity.Oauth2UserLog;
import com.lebaoxun.security.oauth2.entity.Oauth2VisitPath;

@Service
public class Ouath2UserServiceImpl implements IOuath2UserService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IUserService userService;

	@Override
	public Long findByUsername(String account) {
		// TODO Auto-generated method stub
		UserEntity user = userService.findByAccount(account);
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

}
