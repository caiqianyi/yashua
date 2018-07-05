package com.lebaoxun.modules.news.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.news.entity.PraiseLogEntity;

import java.util.Map;

/**
 * 点赞表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
public interface PraiseLogService extends IService<PraiseLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

