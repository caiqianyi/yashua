package com.lebaoxun.pay.service.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysMenuEntity;
import com.lebaoxun.pay.service.ISysMenuService;

@Component
public class SysMenuServiceHystrix implements ISysMenuService {

	@Override
	public ResponseMessage nav(Long userId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public List<SysMenuEntity> list() {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage select() {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage info(Long menuId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage save(SysMenuEntity menu) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage update(SysMenuEntity menu) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage delete(long menuId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

}
