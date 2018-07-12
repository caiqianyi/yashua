package com.lebaoxun.modules.mall.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallSpecificationEntity;

import java.util.Map;

/**
 * 规格表

 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
public interface MallSpecificationService extends IService<MallSpecificationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

