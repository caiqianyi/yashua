package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallProductCommentDao;
import com.lebaoxun.modules.mall.entity.MallProductCommentEntity;
import com.lebaoxun.modules.mall.service.MallProductCommentService;


@Service("mallProductCommentService")
public class MallProductCommentServiceImpl extends ServiceImpl<MallProductCommentDao, MallProductCommentEntity> implements MallProductCommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallProductCommentEntity> page = this.selectPage(
                new Query<MallProductCommentEntity>(params).getPage(),
                new EntityWrapper<MallProductCommentEntity>()
        );

        return new PageUtils(page);
    }

}
