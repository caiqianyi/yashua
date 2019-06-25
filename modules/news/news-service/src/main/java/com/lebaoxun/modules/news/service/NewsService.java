package com.lebaoxun.modules.news.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.news.entity.NewsEntity;

/**
 * 新闻表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
public interface NewsService extends IService<NewsEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<NewsEntity> queryReleaseNews(Integer size,
			Integer offset,Integer class_id);
    
    NewsEntity queryReleaseNewsInfo(Long id);
    
    void modifyClicks(Long id,boolean flag);

    
}

