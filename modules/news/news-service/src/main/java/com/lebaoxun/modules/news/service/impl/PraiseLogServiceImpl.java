package com.lebaoxun.modules.news.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.news.dao.PraiseLogDao;
import com.lebaoxun.modules.news.entity.PraiseLogEntity;
import com.lebaoxun.modules.news.service.PraiseLogService;


@Service("praiseLogService")
public class PraiseLogServiceImpl extends ServiceImpl<PraiseLogDao, PraiseLogEntity> implements PraiseLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PraiseLogEntity> page = this.selectPage(
                new Query<PraiseLogEntity>(params).getPage(),
                new EntityWrapper<PraiseLogEntity>()
        );

        return new PageUtils(page);
    }

}
