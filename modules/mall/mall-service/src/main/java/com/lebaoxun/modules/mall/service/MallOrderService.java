package com.lebaoxun.modules.mall.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.entity.MallOrderEntity;

/**
 * 订单表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
public interface MallOrderService extends IService<MallOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    String create(Long userId,List<MallCartEntity> products);
    
    MallOrderEntity selectOrderByOrderNo(Long userId,String orderNo,
			Integer status);
}

