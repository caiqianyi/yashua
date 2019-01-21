package com.lebaoxun.modules.mall.service.hystrix;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.entity.MallOrderEntity;
import com.lebaoxun.modules.mall.service.IMallOrderService;

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
	public ResponseMessage sendOut(Long adminId, Long orderId, String postid) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
	
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
	public ResponseMessage create(Long userId, 
			Integer maxOrderNum, List<MallCartEntity> products) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public MallOrderEntity selectOrderByOrderNo(Long userId, String orderNo,
			Integer status) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage confirmOrder(Long userId, String orderNo,
			Integer invoiceType, String invoiceTitle, String invoiceNo,
			String address, String consignee,String mobile, 
			String wxopenid, String spbill_create_ip, Long fuid) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
	
	@Override
	public ResponseMessage mylist(Long userId, Integer status,
			Integer size, Integer offset) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
	
	@Override
	public ResponseMessage scoreExchange(Long userId, String orderNo,
			Integer invoiceType, String invoiceTitle, String invoiceNo, String address,
			String consignee, String mobile) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
	
	@Override
	public ResponseMessage kuaid100Query(String postid) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
	
	@Override
	public ResponseMessage confirmReceive(Long userId, String orderNo) {
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
}

