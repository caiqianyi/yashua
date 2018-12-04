package com.lebaoxun.modules.yashua.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.yashua.entity.UserDataEntity;


/**
 * 用户口气表
 */
@Mapper
public interface UserDataDao extends BaseMapper<UserDataEntity> {

}
