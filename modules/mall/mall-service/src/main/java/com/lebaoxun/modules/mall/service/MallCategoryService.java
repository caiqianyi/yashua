package com.lebaoxun.modules.mall.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallCategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 分类表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
public interface MallCategoryService extends IService<MallCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<MallCategoryEntity> queryAllList();
}

