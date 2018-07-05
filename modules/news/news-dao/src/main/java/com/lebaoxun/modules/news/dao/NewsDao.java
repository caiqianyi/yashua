package com.lebaoxun.modules.news.dao;

import com.lebaoxun.modules.news.entity.NewsEntity;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 新闻表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
@Mapper
public interface NewsDao extends BaseMapper<NewsEntity> {
	
}
