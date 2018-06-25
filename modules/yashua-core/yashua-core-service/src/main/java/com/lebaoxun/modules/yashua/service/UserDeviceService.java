package com.lebaoxun.modules.yashua.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.yashua.entity.UserDeviceEntity;

import java.util.Map;

/**
 * 用户设备表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-23 16:42:44
 */
public interface UserDeviceService extends IService<UserDeviceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

