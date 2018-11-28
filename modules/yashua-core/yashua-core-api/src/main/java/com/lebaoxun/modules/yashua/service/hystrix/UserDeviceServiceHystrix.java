package com.lebaoxun.modules.yashua.service.hystrix;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.modules.yashua.entity.UserDeviceEntity;
import com.lebaoxun.modules.yashua.service.IUserDeviceService;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;

/**
 * 用户设备表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-23 16:42:44
 */

@Component
public class UserDeviceServiceHystrix implements IUserDeviceService {

	@Override
	public ResponseMessage list(Map<String, Object> params) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage info(Integer id, Long userId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage save(Long adminId,UserDeviceEntity userDevice) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
	
	@Override
	public ResponseMessage addBatch(Long adminId,
			List<UserDeviceEntity> userDevices) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage update(Long adminId,UserDeviceEntity userDevice) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage delete(Long adminId,Integer[] ids) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage bind(String account, String identity,
			Integer maxBindNum) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage unbind(String account, String identity) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage connect(String account, String identity) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
}

