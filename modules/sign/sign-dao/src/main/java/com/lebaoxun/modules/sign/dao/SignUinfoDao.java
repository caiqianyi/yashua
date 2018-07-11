package com.lebaoxun.modules.sign.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.sign.entity.SignUinfoEntity;

/**
 * 签到用户表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */
@Mapper
public interface SignUinfoDao extends BaseMapper<SignUinfoEntity> {
	SignUinfoEntity findSignUinfoByUserId(@Param("userId")Long userId);
	
	SignUinfoEntity queryMonthSignLog(@Param("userId")Long userId,@Param("time") String time);
}
