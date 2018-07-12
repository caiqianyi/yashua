package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallProductSpecificationDao;
import com.lebaoxun.modules.mall.entity.MallProductSpecificationEntity;
import com.lebaoxun.modules.mall.service.MallProductSpecificationService;


@Service("mallProductSpecificationService")
public class MallProductSpecificationServiceImpl extends ServiceImpl<MallProductSpecificationDao, MallProductSpecificationEntity> implements MallProductSpecificationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallProductSpecificationEntity> page = this.selectPage(
                new Query<MallProductSpecificationEntity>(params).getPage(),
                new EntityWrapper<MallProductSpecificationEntity>()
        );

        return new PageUtils(page);
    }

}
