package com.lebaoxun.modules.account.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.account.dao.UserLogDao;
import com.lebaoxun.modules.account.entity.UserLogEntity;
import com.lebaoxun.modules.account.service.UserLogService;


@Service("userLogService")
public class UserLogServiceImpl extends ServiceImpl<UserLogDao, UserLogEntity> implements UserLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UserLogEntity> page = this.selectPage(
                new Query<UserLogEntity>(params).getPage(),
                new EntityWrapper<UserLogEntity>()
        );

        return new PageUtils(page);
    }

}
