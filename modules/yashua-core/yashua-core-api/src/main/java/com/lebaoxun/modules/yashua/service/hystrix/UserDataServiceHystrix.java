package com.lebaoxun.modules.yashua.service.hystrix;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.yashua.service.IUserDataService;

/**
 * 用户口气表
 */
@Component
public class UserDataServiceHystrix implements IUserDataService{

	@Override
	public ResponseMessage list(Map<String, Object> params) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage hlist(Map<String, Object> params) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

}
