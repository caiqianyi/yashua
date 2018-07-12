package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallSpecificationAttributeDao;
import com.lebaoxun.modules.mall.entity.MallSpecificationAttributeEntity;
import com.lebaoxun.modules.mall.service.MallSpecificationAttributeService;


@Service("mallSpecificationAttributeService")
public class MallSpecificationAttributeServiceImpl extends ServiceImpl<MallSpecificationAttributeDao, MallSpecificationAttributeEntity> implements MallSpecificationAttributeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallSpecificationAttributeEntity> page = this.selectPage(
                new Query<MallSpecificationAttributeEntity>(params).getPage(),
                new EntityWrapper<MallSpecificationAttributeEntity>()
        );

        return new PageUtils(page);
    }

}
