package com.lebaoxun.modules.mall.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallOrderEntity;

import java.util.Map;

/**
 * 订单表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
public interface MallOrderService extends IService<MallOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

