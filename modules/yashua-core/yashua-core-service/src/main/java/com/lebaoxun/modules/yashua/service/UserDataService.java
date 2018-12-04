package com.lebaoxun.modules.yashua.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.yashua.entity.UserDataEntity;

/**
 * 用户口气表
 */
public interface UserDataService extends IService<UserDataEntity>{
	  PageUtils queryPage(Map<String, Object> params);
}
