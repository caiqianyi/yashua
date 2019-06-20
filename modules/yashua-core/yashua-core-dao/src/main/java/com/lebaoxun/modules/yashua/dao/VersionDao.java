package com.lebaoxun.modules.yashua.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.yashua.entity.AppVersionEntity;

@Mapper
public interface VersionDao extends BaseMapper<AppVersionEntity>{
	/**
	 *App 获取最新版本信息
	 */

	AppVersionEntity newInfo(String versionType);

	void addLoad(@Param("versionNumber") String versionNumber, @Param("versionType") String versionType);

}
