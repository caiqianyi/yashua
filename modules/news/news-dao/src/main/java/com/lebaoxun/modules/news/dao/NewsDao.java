package com.lebaoxun.modules.news.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lebaoxun.modules.news.entity.NewsEntity;

/**
 * 新闻表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
@Mapper
public interface NewsDao extends BaseMapper<NewsEntity> {
	List<NewsEntity> queryReleaseNews(@Param("size")Integer size,
			@Param("offset")Integer offset, @Param("class_id")Integer class_id);
	
	NewsEntity queryReleaseNewsInfo(@Param("id") Long id);
	
	void modifyClicks(@Param("id") Long id,@Param("flag")boolean flag);
}
