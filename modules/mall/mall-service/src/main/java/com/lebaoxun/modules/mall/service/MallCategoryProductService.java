package com.lebaoxun.modules.mall.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallCategoryProductEntity;
import com.lebaoxun.modules.mall.pojo.MallCategoryProductVo;

/**
 * 商品分类关联表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
public interface MallCategoryProductService extends IService<MallCategoryProductEntity> {

    List<MallCategoryProductVo> findByProduct(Long productId);
    
    void edit(Long adminId,Long productId,Long[] categoryIds);
}

