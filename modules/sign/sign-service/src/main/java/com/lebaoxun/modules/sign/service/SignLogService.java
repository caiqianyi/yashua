package com.lebaoxun.modules.sign.service;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.sign.entity.SignLogEntity;
import com.lebaoxun.modules.sign.entity.SignUinfoEntity;

import java.util.Map;

/**
 * 签到记录表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */
public interface SignLogService extends IService<SignLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    SignUinfoEntity signIn(Long userId,String groupId);
}

