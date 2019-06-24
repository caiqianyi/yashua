package com.lebaoxun.modules.account.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.account.entity.UserMessageMidEntity;

/**
 * 用户消息中间表
 * 
 * 
 */
@Mapper
public interface UserMessageMidDao extends BaseMapper<UserMessageMidEntity> {
	int updateUserMsg(@Param("user_id") Long userId,@Param("message_id") long id);
	
	void deleteByMessage(@Param("messageId") Long messageId);
	
	int queryUM(@Param("user_id") Long userId);
}
