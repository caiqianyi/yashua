package com.lebaoxun.modules.sign.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.sign.entity.SignLogEntity;
import com.lebaoxun.modules.sign.entity.SignUinfoEntity;

/**
 * 签到记录表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */
@Mapper
public interface SignLogDao extends BaseMapper<SignLogEntity> {
	int countSignByUserId(@Param("userId")Long userId,@Param("signTime") Date signTime);
	
	List<SignUinfoEntity> queryMonthSignLog(@Param("userId")Long userId,@Param("time") String time);
}
