package com.lebaoxun.modules.account.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
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
    	String userId = (String)params.get("userId");
    	String account = (String)params.get("account");
    	String type = (String)params.get("type");
        Page<UserLogEntity> page = this.selectPage(
                new Query<UserLogEntity>(params).getPage(),
                new EntityWrapper<UserLogEntity>()
                .eq(StringUtils.isNotBlank(userId) && StringUtils.isNumeric(userId), "user_id", userId)
                .eq(StringUtils.isNotBlank(account), "account", account)
                .eq(StringUtils.isNotBlank(type), "log_type", type)
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map<String, Object>> queryAllLogType() {
    	// TODO Auto-generated method stub
    	return this.baseMapper.queryAllLogType();
    }
}
