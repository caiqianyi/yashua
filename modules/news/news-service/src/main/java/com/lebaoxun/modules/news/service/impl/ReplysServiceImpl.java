package com.lebaoxun.modules.news.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.news.dao.ReplysDao;
import com.lebaoxun.modules.news.entity.ReplysEntity;
import com.lebaoxun.modules.news.service.ReplysService;


@Service("replysService")
public class ReplysServiceImpl extends ServiceImpl<ReplysDao, ReplysEntity> implements ReplysService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ReplysEntity> page = this.selectPage(
                new Query<ReplysEntity>(params).getPage(),
                new EntityWrapper<ReplysEntity>()
        );

        return new PageUtils(page);
    }

}
