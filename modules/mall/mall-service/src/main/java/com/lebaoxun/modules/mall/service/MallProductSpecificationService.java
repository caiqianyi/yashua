package com.lebaoxun.modules.mall.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallProductSpecificationEntity;

/**
 * 商品规格表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
public interface MallProductSpecificationService extends IService<MallProductSpecificationEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<MallProductSpecificationEntity> queryByProductId(Long productId);
    
    void save(MallProductSpecificationEntity specification);
    
    void update(MallProductSpecificationEntity specification);
}

