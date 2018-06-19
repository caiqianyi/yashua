package com.lebaoxun.modules.account.dao;

import com.lebaoxun.modules.account.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 用户表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
