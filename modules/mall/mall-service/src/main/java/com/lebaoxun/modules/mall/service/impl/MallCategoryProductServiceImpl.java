package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallCategoryProductDao;
import com.lebaoxun.modules.mall.entity.MallCategoryProductEntity;
import com.lebaoxun.modules.mall.service.MallCategoryProductService;


@Service("mallCategoryProductService")
public class MallCategoryProductServiceImpl extends ServiceImpl<MallCategoryProductDao, MallCategoryProductEntity> implements MallCategoryProductService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallCategoryProductEntity> page = this.selectPage(
                new Query<MallCategoryProductEntity>(params).getPage(),
                new EntityWrapper<MallCategoryProductEntity>()
        );

        return new PageUtils(page);
    }

}
