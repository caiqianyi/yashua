package com.lebaoxun.modules.news.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.news.entity.ReplysEntity;

/**
 * 回复表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
public interface ReplysService extends IService<ReplysEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<ReplysEntity> queryReplys(String userTbs,
			String type, String recordId,
			Integer size, Integer offset);
}

