package com.lebaoxun.modules.mall.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.mall.dao.MallCartDao;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.service.MallCartService;


@Service("mallCartService")
public class MallCartServiceImpl extends ServiceImpl<MallCartDao, MallCartEntity> implements MallCartService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MallCartEntity> page = this.selectPage(
                new Query<MallCartEntity>(params).getPage(),
                new EntityWrapper<MallCartEntity>()
        );

        return new PageUtils(page);
    }

}
