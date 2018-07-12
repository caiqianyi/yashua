package com.lebaoxun.modules.mall.service.hystrix;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lebaoxun.modules.mall.entity.MallProductParameterEntity;
import com.lebaoxun.modules.mall.service.IMallProductParameterService;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;

/**
 * 商品参数表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@Component
public class MallProductParameterServiceHystrix implements IMallProductParameterService {

	@Override
	public ResponseMessage list(Map<String, Object> params) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage info(Long productParameterId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage save(Long adminId,MallProductParameterEntity mallProductParameter) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage update(Long adminId,MallProductParameterEntity mallProductParameter) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage delete(Long adminId,Long[] productParameterIds) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
    
}

