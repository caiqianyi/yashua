package com.lebaoxun.modules.mall.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallProductEntity;

/**
 * 商品表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
public interface MallProductService extends IService<MallProductEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<MallProductEntity> findShowProdcutByCategory(Long categoryId, 
    		Integer size, Integer offset);
    
    List<MallProductEntity> findShowProdcutByHaveScore(Integer size,
			Integer offset);
    
    MallProductEntity findShowProdcutInfo(Long id);
    
    void create(MallProductEntity mallProduct);
    
    void update(MallProductEntity mallProduct);
    
    void delete(Long id);
}

