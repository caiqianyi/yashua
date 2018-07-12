package com.lebaoxun.modules.mall.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallProductAttrEntity;

import java.util.Map;

/**
 * 商品属性表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
public interface MallProductAttrService extends IService<MallProductAttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

