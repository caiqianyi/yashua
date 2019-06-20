package com.lebaoxun.modules.yashua.service;

import java.util.Map;


import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.yashua.entity.AppVersionEntity;

public interface VersionService extends IService<AppVersionEntity>{

	PageUtils queryPage(Map<String, Object> params);
	/**
	 * app获取最新的版本接口
	 */
	AppVersionEntity newInfo(String versionType);
	/**
	 * App版本加一
	 */
	void addLoad(String versionNumber, String versionType);

}
