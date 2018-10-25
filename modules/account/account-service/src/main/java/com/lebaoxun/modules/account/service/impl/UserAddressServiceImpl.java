package com.lebaoxun.modules.account.service.impl;

import com.lebaoxun.commons.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.account.dao.UserAddressDao;
import com.lebaoxun.modules.account.entity.UserAddressEntity;
import com.lebaoxun.modules.account.service.UserAddressService;


@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressDao, UserAddressEntity> implements UserAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String userId = (String)params.get("userId");
        Page<UserAddressEntity> page = this.selectPage(
                new Query<UserAddressEntity>(params).getPage(),
                new EntityWrapper<UserAddressEntity>()
                .eq(StringUtils.isNotBlank(userId) && StringUtils.isNumeric(userId), "user_id", userId)
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addAddress(UserAddressEntity userAddress) {
    	// TODO Auto-generated method stub
    	if("0".equals(userAddress.getDefaultFlag())){
    		UserAddressEntity uae = new UserAddressEntity();
    		uae.setDefaultFlag("1");
    		this.baseMapper.update(uae, new EntityWrapper<UserAddressEntity>()
    				.eq(true, "user_id", userAddress.getUserId()));
    	}
    	this.baseMapper.insert(userAddress);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void modifyAddress(UserAddressEntity userAddress) {
    	// TODO Auto-generated method stub
    	if("0".equals(userAddress.getDefaultFlag())){
    		UserAddressEntity uae = new UserAddressEntity();
    		uae.setDefaultFlag("1");
    		this.baseMapper.update(uae, new EntityWrapper<UserAddressEntity>()
    				.eq(true, "user_id", userAddress.getUserId()).eq(false, "id", userAddress.getId()));
    	}
    	this.baseMapper.updateById(userAddress);
    }
}
