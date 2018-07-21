package com.lebaoxun.modules.mall.service.hystrix;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.entity.MallOrderEntity;
import com.lebaoxun.modules.mall.service.IMallOrderService;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;

/**
 * 订单表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */

@Component
public class MallOrderServiceHystrix implements IMallOrderService {

	@Override
	public ResponseMessage list(Map<String, Object> params) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage deleteByUser(Long userId, String orderNo) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
	
	@Override
	public ResponseMessage selectOrderProductByOrderProductId(Long userId,
			Long orderProductId) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
	
	@Override
	public ResponseMessage info(Long id) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage save(Long adminId,MallOrderEntity mallOrder) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage update(Long adminId,MallOrderEntity mallOrder) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage delete(Long adminId,Long[] ids) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage create(Long userId, List<MallCartEntity> products) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public MallOrderEntity selectOrderByOrderNo(Long userId, String orderNo,
			Integer status) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage confirmOrder(Long userId, String orderNo,
			Integer invoiceType, String invoiceTitle, String address,
			String consignee,String mobile) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
	
	@Override
	public ResponseMessage mylist(Long userId, Integer status, Integer payType,
			Integer size, Integer offset) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
}
