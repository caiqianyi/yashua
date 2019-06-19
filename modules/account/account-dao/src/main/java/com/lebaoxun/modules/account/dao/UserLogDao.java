package com.lebaoxun.modules.account.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.account.entity.UserLogEntity;

/**
 * 
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */
@Mapper
public interface UserLogDao extends BaseMapper<UserLogEntity> {
	
	List<Map<String,Object>> queryAllLogType();
	
	List<UserLogEntity> queryAccountLogByUserId(@Param("user_id")Long userId,
			@Param("size")Integer size,
			@Param("offset")Integer offset);
	
	Long sumTradeMoneyByUser(@Param("userId") Long userId,
			@Param("time") String time,
			@Param("logType") String logType);

	long loginCount(@Param("userId")  Long userId,@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	long countByUserId(@Param("userId") Long userId, @Param("logType") String logType, 
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	long countDayu(@Param("logType")String logType,@Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("countById") Long countById);

	long count(String logType, Date startDate, Date endDate);
}
