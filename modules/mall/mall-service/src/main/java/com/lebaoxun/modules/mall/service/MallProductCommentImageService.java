package com.lebaoxun.modules.mall.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.mall.entity.MallProductCommentImageEntity;

import java.util.Map;

/**
 * 评价图片表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
public interface MallProductCommentImageService extends IService<MallProductCommentImageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

