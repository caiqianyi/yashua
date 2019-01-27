package com.lebaoxun.modules.yashua.dao;

import com.lebaoxun.modules.yashua.entity.UserDeviceEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 用户设备表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-23 16:42:44
 */
@Mapper
public interface UserDeviceDao extends BaseMapper<UserDeviceEntity> {
	void unbind(@Param("account") String account,
			@Param("identity") String identity);
	
	void setName(
			@Param("name") String name,@Param("identity") String identity);

	
}
