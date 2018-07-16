package com.lebaoxun.modules.mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.mall.entity.MallSpecificationEntity;

/**
 * 规格表

 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@Mapper
public interface MallSpecificationDao extends BaseMapper<MallSpecificationEntity> {
	List<MallSpecificationEntity> queryAllList();
}
