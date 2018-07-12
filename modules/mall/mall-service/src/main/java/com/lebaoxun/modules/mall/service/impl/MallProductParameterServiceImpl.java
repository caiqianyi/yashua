package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallProductParameterDao;
import com.lebaoxun.modules.mall.entity.MallProductParameterEntity;
import com.lebaoxun.modules.mall.service.MallProductParameterService;


@Service("mallProductParameterService")
public class MallProductParameterServiceImpl extends ServiceImpl<MallProductParameterDao, MallProductParameterEntity> implements MallProductParameterService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallProductParameterEntity> page = this.selectPage(
                new Query<MallProductParameterEntity>(params).getPage(),
                new EntityWrapper<MallProductParameterEntity>()
        );

        return new PageUtils(page);
    }

}
