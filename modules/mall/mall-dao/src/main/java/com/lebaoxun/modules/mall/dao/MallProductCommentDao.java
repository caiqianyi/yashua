package com.lebaoxun.modules.mall.dao;

import java.util.List;

import com.lebaoxun.modules.mall.entity.MallProductCommentEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 评价表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
@Mapper
public interface MallProductCommentDao extends BaseMapper<MallProductCommentEntity> {
	void save(MallProductCommentEntity comment);
	
	List<MallProductCommentEntity> selectByProduct(@Param("productId") Long productId);
	
	MallProductCommentEntity selectLastByProduct(@Param("productId") Long productId);
}
