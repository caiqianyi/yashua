package com.lebaoxun.modules.sign.dao;

import com.lebaoxun.modules.sign.entity.SignAwardEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 签到奖励规则表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */
@Mapper
public interface SignAwardDao extends BaseMapper<SignAwardEntity> {
	void executeSignAward(@Param("sql")String sql,@Param("userId")Long userId);
}
