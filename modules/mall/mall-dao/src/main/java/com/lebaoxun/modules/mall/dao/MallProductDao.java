package com.lebaoxun.modules.mall.dao;

import java.util.List;

import com.lebaoxun.modules.mall.entity.MallProductEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 商品表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@Mapper
public interface MallProductDao extends BaseMapper<MallProductEntity> {
	List<MallProductEntity> findProductByCategory(@Param("categoryId")Long categoryId,
			@Param("productNumber") Long productNumber,
			@Param("showInShelve")Integer showInShelve,@Param("size") Integer size
			,@Param("offset") Integer offset);
	
	int countProductByCategory(@Param("categoryId")Long categoryId,
			@Param("productNumber") Long productNumber,
			@Param("showInShelve")Integer showInShelve);
	
	long addProduct(MallProductEntity mpe);
}
