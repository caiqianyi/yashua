package com.lebaoxun.modules.mall.service.impl;

import com.lebaoxun.commons.utils.StringUtils;
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
    	String productId = (String)params.get("productId");
        Page<MallProductImageEntity> page = this.selectPage(
                new Query<MallProductImageEntity>(params).getPage(),
                new EntityWrapper<MallProductImageEntity>()
                .eq(StringUtils.isNotBlank(productId) && StringUtils.isNumeric(productId), "product_id", productId)
                .orderBy(StringUtils.isNotBlank(productId) && StringUtils.isNumeric(productId), "sort", true)
                .orderBy("create_time", false)
        );

        return new PageUtils(page);
    }

}
