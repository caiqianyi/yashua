package com.lebaoxun.modules.mall.service.hystrix;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.service.IMallCartService;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;

/**
 * 购物车表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@Component
public class MallCartServiceHystrix implements IMallCartService {

	@Override
	public ResponseMessage list(Map<String, Object> params) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage info(Long cartId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage save(Long adminId,MallCartEntity mallCart) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage update(Long adminId,MallCartEntity mallCart) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage delete(Long adminId,Long[] cartIds) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage sync(Long userId, List<MallCartEntity> list) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage findByUser(Long userId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage queryByProductSpecId(Long[] ids) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
    
}

