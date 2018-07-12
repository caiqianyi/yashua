package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallProductCommentImageDao;
import com.lebaoxun.modules.mall.entity.MallProductCommentImageEntity;
import com.lebaoxun.modules.mall.service.MallProductCommentImageService;


@Service("mallProductCommentImageService")
public class MallProductCommentImageServiceImpl extends ServiceImpl<MallProductCommentImageDao, MallProductCommentImageEntity> implements MallProductCommentImageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallProductCommentImageEntity> page = this.selectPage(
                new Query<MallProductCommentImageEntity>(params).getPage(),
                new EntityWrapper<MallProductCommentImageEntity>()
        );

        return new PageUtils(page);
    }

}
