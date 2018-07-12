package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallSpecificationDao;
import com.lebaoxun.modules.mall.entity.MallSpecificationEntity;
import com.lebaoxun.modules.mall.service.MallSpecificationService;


@Service("mallSpecificationService")
public class MallSpecificationServiceImpl extends ServiceImpl<MallSpecificationDao, MallSpecificationEntity> implements MallSpecificationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallSpecificationEntity> page = this.selectPage(
                new Query<MallSpecificationEntity>(params).getPage(),
                new EntityWrapper<MallSpecificationEntity>()
        );

        return new PageUtils(page);
    }

}
