package com.lebaoxun.modules.news.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.news.entity.ReplysEntity;

import java.util.Map;

/**
 * 回复表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
public interface ReplysService extends IService<ReplysEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

