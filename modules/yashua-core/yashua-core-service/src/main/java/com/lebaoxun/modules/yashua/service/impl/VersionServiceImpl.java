package com.lebaoxun.modules.yashua.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.yashua.dao.VersionDao;
import com.lebaoxun.modules.yashua.entity.AppVersionEntity;
import com.lebaoxun.modules.yashua.service.VersionService;

@Service("versionService")
public class VersionServiceImpl  extends ServiceImpl<VersionDao, AppVersionEntity> implements VersionService{

	@Autowired
	private VersionDao versionDao;
	  @Override
	    public PageUtils queryPage(Map<String, Object> params) {
	        Page<AppVersionEntity> page = this.selectPage(
	                new Query<AppVersionEntity>(params).getPage(),
	                new EntityWrapper<AppVersionEntity>());

	        return new PageUtils(page);
	    }

	@Override
	public AppVersionEntity newInfo(String versionType) {
		return versionDao.newInfo(versionType);
	}

	@Override
	public void addLoad(String versionNumber, String versionType) {
		versionDao.addLoad(versionNumber,versionType);
	}
}
