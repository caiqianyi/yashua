package com.lebaoxun.modules.yashua.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.yashua.dao.UserDeviceDao;
import com.lebaoxun.modules.yashua.entity.UserDeviceEntity;
import com.lebaoxun.modules.yashua.service.UserDeviceService;


@Service("userDeviceService")
public class UserDeviceServiceImpl extends ServiceImpl<UserDeviceDao, UserDeviceEntity> implements UserDeviceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String account = (String) params.get("account");
        Page<UserDeviceEntity> page = this.selectPage(
                new Query<UserDeviceEntity>(params).getPage(),
                new EntityWrapper<UserDeviceEntity>().eq(StringUtils.isNotBlank(account), "account", account)
        );

        return new PageUtils(page);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void bind(String account,String identity, Integer maxBindNum) {
		// TODO Auto-generated method stub
		if(maxBindNum != null){
			int total = this.baseMapper.selectCount(new EntityWrapper<UserDeviceEntity>().eq("account", account));
			if(maxBindNum >= total){
				throw new I18nMessageException("-1","设备已达上限值："+maxBindNum);
			}
		}
		UserDeviceEntity device = this.selectOne(new EntityWrapper<UserDeviceEntity>().eq("identity", identity));
		if(device == null){
			throw new I18nMessageException("-1","设备ID‘"+identity+"’不存在！");
		}
		if(device.getAccount() != null && StringUtils.isNotBlank(device.getAccount())){
			throw new I18nMessageException("-1","此设备已被绑定！");
		}
		device.setAccount(account);
		baseMapper.update(device, new EntityWrapper<UserDeviceEntity>().eq("identity", identity));
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void unbind(String account,String identity) {
		// TODO Auto-generated method stub
		Wrapper<UserDeviceEntity> ew = new EntityWrapper<UserDeviceEntity>().eq("identity", identity).eq("account", account);
		UserDeviceEntity device = this.selectOne(ew);
		if(device == null){
			throw new I18nMessageException("-1","设备ID‘"+identity+"’不存在！");
		}
		device.setAccount(null);
		baseMapper.update(device, ew);
	}

}
