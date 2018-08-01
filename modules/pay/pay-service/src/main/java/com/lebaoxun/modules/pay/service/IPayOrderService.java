package com.lebaoxun.modules.pay.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.pay.entity.PayOrderEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付订单
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-31 14:40:10
 */
public interface IPayOrderService extends IService<PayOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    String notify(String out_trade_no, BigDecimal total_fee, String tradeNo, Long buyTime, String queue);
}