package com.lebaoxun.modules.mall.dao;

import com.lebaoxun.modules.mall.entity.MallProductAttrEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 商品属性表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@Mapper
public interface MallProductAttrDao extends BaseMapper<MallProductAttrEntity> {
	void deleteByProduct(@Param("productId") Long productId);
}
