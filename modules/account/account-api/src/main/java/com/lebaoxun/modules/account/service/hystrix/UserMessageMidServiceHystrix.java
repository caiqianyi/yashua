package com.lebaoxun.modules.account.service.hystrix;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.entity.UserMessageMidEntity;
import com.lebaoxun.modules.account.service.IUserMessageMidService;

/**
 * 用户消息关联
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-20 15:44:50
 */
@Component
public class UserMessageMidServiceHystrix implements IUserMessageMidService{

	@Override
	public ResponseMessage list(Map<String, Object> params) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage info(Integer id, Long userId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage save(Long adminId, UserMessageMidEntity userMessageMid) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage update(Long adminId, UserMessageMidEntity userMessageMid) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage delete(Long adminId, Integer[] ids) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

}
