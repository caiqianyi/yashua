package com.lebaoxun.pay.service.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysDeptEntity;
import com.lebaoxun.pay.service.ISysDeptService;

@Component
public class SysDeptServiceHystrix implements ISysDeptService {

	@Override
	public List<SysDeptEntity> list() {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage select(Long userId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage info(Long userId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage infoByDeptId(Long deptId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage save(SysDeptEntity dept) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage update(SysDeptEntity dept) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage delete(long deptId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}


}
