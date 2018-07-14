package com.lebaoxun.modules.mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.mall.entity.MallCategoryProductEntity;
import com.lebaoxun.modules.mall.pojo.MallCategoryProductVo;

/**
 * 商品分类关联表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@Mapper
public interface MallCategoryProductDao extends BaseMapper<MallCategoryProductEntity> {
	void deleteByProduct(@Param("productId") Long productId);
	
	List<MallCategoryProductVo> findByProduct(@Param("productId") Long productId);
}
