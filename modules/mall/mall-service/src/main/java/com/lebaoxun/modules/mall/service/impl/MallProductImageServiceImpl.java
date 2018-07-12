package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallProductImageDao;
import com.lebaoxun.modules.mall.entity.MallProductImageEntity;
import com.lebaoxun.modules.mall.service.MallProductImageService;


@Service("mallProductImageService")
public class MallProductImageServiceImpl extends ServiceImpl<MallProductImageDao, MallProductImageEntity> implements MallProductImageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallProductImageEntity> page = this.selectPage(
                new Query<MallProductImageEntity>(params).getPage(),
                new EntityWrapper<MallProductImageEntity>()
        );

        return new PageUtils(page);
    }

}
