package com.lebaoxun.modules.account.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.account.entity.UserAddressEntity;

import java.util.Map;

/**
 * 用户地址
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-26 10:20:24
 */
public interface UserAddressService extends IService<UserAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

