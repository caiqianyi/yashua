package com.lebaoxun.modules.mall.dao;

import com.lebaoxun.modules.mall.entity.MallOrderEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

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
	
	MallOrderEntity selectOrderByOrderNo(@Param("userId")Long userId,@Param("orderNo")String orderNo,
			@Param("status")Integer status);
}
