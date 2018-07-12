package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallCategoryDao;
import com.lebaoxun.modules.mall.entity.MallCategoryEntity;
import com.lebaoxun.modules.mall.service.MallCategoryService;


@Service("mallCategoryService")
public class MallCategoryServiceImpl extends ServiceImpl<MallCategoryDao, MallCategoryEntity> implements MallCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallCategoryEntity> page = this.selectPage(
                new Query<MallCategoryEntity>(params).getPage(),
                new EntityWrapper<MallCategoryEntity>()
        );

        return new PageUtils(page);
    }

}
