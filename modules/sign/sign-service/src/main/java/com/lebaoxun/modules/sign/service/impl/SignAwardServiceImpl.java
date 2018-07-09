package com.lebaoxun.modules.sign.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.sign.dao.SignAwardDao;
import com.lebaoxun.modules.sign.entity.SignAwardEntity;
import com.lebaoxun.modules.sign.service.SignAwardService;


@Service("signAwardService")
public class SignAwardServiceImpl extends ServiceImpl<SignAwardDao, SignAwardEntity> implements SignAwardService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SignAwardEntity> page = this.selectPage(
                new Query<SignAwardEntity>(params).getPage(),
                new EntityWrapper<SignAwardEntity>()
        );

        return new PageUtils(page);
    }

}
