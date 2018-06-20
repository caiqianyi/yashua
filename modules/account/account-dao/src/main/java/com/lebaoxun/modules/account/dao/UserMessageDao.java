package com.lebaoxun.modules.account.dao;

import com.lebaoxun.modules.account.entity.UserMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 用户消息
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-20 15:44:50
 */
@Mapper
public interface UserMessageDao extends BaseMapper<UserMessageEntity> {
	
}
