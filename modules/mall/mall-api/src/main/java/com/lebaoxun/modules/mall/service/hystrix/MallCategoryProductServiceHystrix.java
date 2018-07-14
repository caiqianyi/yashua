package com.lebaoxun.modules.mall.service.hystrix;
import org.springframework.stereotype.Component;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.mall.service.IMallCategoryProductService;

/**
 * 商品分类关联表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@Component
public class MallCategoryProductServiceHystrix implements IMallCategoryProductService {
	
	@Override
	public ResponseMessage tree(Long productId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage edit(Long adminId, Long productId, Long[] categoryIds) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

}

