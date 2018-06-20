package com.lebaoxun.modules.account.service.hystrix;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.IUserService;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;

/**
 * 用户表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */

@Component
public class UserServiceHystrix implements IUserService {

	@Override
	public ResponseMessage list(Map<String, Object> params) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage info(String id) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage save(UserEntity user) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage update(UserEntity user) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage delete(String[] ids) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public UserEntity findByUserId(Long userId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public UserEntity findByAccount(String account) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public UserEntity login(String username, String password) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
    
	@Override
	public UserEntity findByOpenid(String openid, String groupid) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
}

