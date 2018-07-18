package com.lebaoxun.modules.mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.pojo.MallProductCartVo;

/**
 * 购物车表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@Mapper
public interface MallCartDao extends BaseMapper<MallCartEntity> {
	List<MallProductCartVo> queryByUser(@Param("userId")Long userId);
	
	List<MallProductCartVo> queryByProductSpecId(@Param("ids")Long[] ids);
}
