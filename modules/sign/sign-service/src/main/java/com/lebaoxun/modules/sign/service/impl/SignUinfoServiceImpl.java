package com.lebaoxun.modules.sign.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;

import com.lebaoxun.modules.sign.dao.SignUinfoDao;
import com.lebaoxun.modules.sign.entity.SignUinfoEntity;
import com.lebaoxun.modules.sign.service.SignUinfoService;


@Service("signUinfoService")
public class SignUinfoServiceImpl extends ServiceImpl<SignUinfoDao, SignUinfoEntity> implements SignUinfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SignUinfoEntity> page = this.selectPage(
                new Query<SignUinfoEntity>(params).getPage(),
                new EntityWrapper<SignUinfoEntity>()
        );

        return new PageUtils(page);
    }

}
