package com.lebaoxun.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.account.entity.UserMessageEntity;

/**
 * 用户消息
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-20 15:44:50
 */
@Mapper
public interface UserMessageDao extends BaseMapper<UserMessageEntity> {
	
	List<UserMessageEntity> findInformByUserId(@Param("user_id")Long userId,
			@Param("size")Integer size,
			@Param("offset")Integer offset);
	
	UserMessageEntity findOneInformByUserId(@Param("user_id") Long userId,@Param("id") long id);
}
