package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallProductAttrDao;
import com.lebaoxun.modules.mall.entity.MallProductAttrEntity;
import com.lebaoxun.modules.mall.service.MallProductAttrService;


@Service("mallProductAttrService")
public class MallProductAttrServiceImpl extends ServiceImpl<MallProductAttrDao, MallProductAttrEntity> implements MallProductAttrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallProductAttrEntity> page = this.selectPage(
                new Query<MallProductAttrEntity>(params).getPage(),
                new EntityWrapper<MallProductAttrEntity>()
        );

        return new PageUtils(page);
    }

}
