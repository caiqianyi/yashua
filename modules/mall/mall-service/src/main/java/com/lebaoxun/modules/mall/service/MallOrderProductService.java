package com.lebaoxun.modules.mall.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallOrderProductEntity;

import java.util.Map;

/**
 * 订单明细表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
public interface MallOrderProductService extends IService<MallOrderProductEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

