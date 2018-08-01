package com.lebaoxun.modules.pay.dao;

import com.lebaoxun.modules.pay.entity.PayOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 支付订单
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-31 14:40:10
 */
@Mapper
public interface PayOrderDao extends BaseMapper<PayOrderEntity> {
	
}
