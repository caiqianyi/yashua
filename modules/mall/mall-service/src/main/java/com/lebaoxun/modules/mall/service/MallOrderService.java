package com.lebaoxun.modules.mall.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.entity.MallOrderEntity;
import com.lebaoxun.modules.mall.entity.MallOrderProductEntity;

/**
 * 订单表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
public interface MallOrderService extends IService<MallOrderEntity> {

	PageUtils queryPage(Map<String, Object> params);

	String create(Long userId, Integer maxOrderNum, List<MallCartEntity> products);
	
	void scoreExchange(Long userId, String orderNo, Integer invoiceType,
			String invoiceTitle, String address, String consignee, String mobile);

	MallOrderEntity selectOrderByOrderNo(Long userId, String orderNo,
			Integer status);

	List<MallOrderEntity> mylist(Long userId, Integer status, 
			Integer size, Integer offset);

	void confirmOrder(Long userId, String orderNo, Integer invoiceType,
			String invoiceTitle, String address, String consignee, String mobile);
	
	void delete(Long userId, String orderNo);
	
	MallOrderProductEntity selectOrderProductByOrderProductId(Long userId,
			Long orderProductId);
}
