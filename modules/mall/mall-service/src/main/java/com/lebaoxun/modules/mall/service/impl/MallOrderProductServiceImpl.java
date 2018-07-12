package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallOrderProductDao;
import com.lebaoxun.modules.mall.entity.MallOrderProductEntity;
import com.lebaoxun.modules.mall.service.MallOrderProductService;


@Service("mallOrderProductService")
public class MallOrderProductServiceImpl extends ServiceImpl<MallOrderProductDao, MallOrderProductEntity> implements MallOrderProductService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallOrderProductEntity> page = this.selectPage(
                new Query<MallOrderProductEntity>(params).getPage(),
                new EntityWrapper<MallOrderProductEntity>()
        );

        return new PageUtils(page);
    }

}
