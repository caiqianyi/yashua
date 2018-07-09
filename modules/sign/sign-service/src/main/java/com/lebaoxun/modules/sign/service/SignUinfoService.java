package com.lebaoxun.modules.sign.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.sign.entity.SignUinfoEntity;

import java.util.Map;

/**
 * 签到用户表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */
public interface SignUinfoService extends IService<SignUinfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

