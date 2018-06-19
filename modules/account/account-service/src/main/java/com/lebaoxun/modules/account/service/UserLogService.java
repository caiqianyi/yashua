package com.lebaoxun.modules.account.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.account.entity.UserLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */
public interface UserLogService extends IService<UserLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

