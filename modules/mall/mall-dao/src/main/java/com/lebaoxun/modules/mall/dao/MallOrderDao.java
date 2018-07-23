package com.lebaoxun.modules.mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.mall.entity.MallOrderEntity;
import com.lebaoxun.modules.mall.entity.MallOrderProductEntity;

/**
 * 订单表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
@Mapper
public interface MallOrderDao extends BaseMapper<MallOrderEntity> {
	int save(MallOrderEntity order);

	MallOrderEntity selectOrderByOrderNo(@Param("userId") Long userId,
			@Param("orderNo") String orderNo, @Param("status") Integer status);

	List<MallOrderEntity> mylist(@Param("userId") Long userId,
			@Param("status") Integer status,
			@Param("size") Integer size, @Param("offset") Integer offset);

	MallOrderProductEntity selectOrderProductByOrderProductId(
			@Param("userId") Long userId,
			@Param("orderProductId") Long orderProductId);

	List<MallOrderProductEntity> selectOrderProductByOrderId(
			@Param("orderId") Long orderId);

	void balancePay(@Param("userId") Long userId, @Param("tradeAmount") Integer tradeAmount,
			@Param("logType") String logType, @Param("descr") String descr, 
			@Param("adjunctInfo") String adjunctInfo);
}
