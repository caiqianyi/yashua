package com.lebaoxun.modules.news.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.news.entity.ReplysEntity;

/**
 * 回复表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
@Mapper
public interface ReplysDao extends BaseMapper<ReplysEntity> {
	
	void addReplys(@Param("tbs") String tbs, @Param("recordId") String recordId);

	void deductReplys(@Param("tbs") String tbs,
			@Param("recordId") String recordId);

	List<ReplysEntity> queryReplys(@Param("userTbs") String userTbs,
			@Param("type") String type, @Param("recordId") String recordId,
			@Param("size")Integer size,
			@Param("offset")Integer offset);
}
