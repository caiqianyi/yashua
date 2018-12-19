package com.lebaoxun.modules.yashua.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.yashua.entity.UserDataEntity;


/**
 * 用户口气表
 */
@Mapper
public interface UserDataDao extends BaseMapper<UserDataEntity> {
	List<UserDataEntity> lookList(@Param("adddate") Date adddate,@Param("userid") Long userid,@Param("biaoshi")Long biaoshi);
}
