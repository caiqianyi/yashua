package com.lebaoxun.modules.account.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.account.entity.UserMessageEntity;

/**
 * 用户消息
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-20 15:44:50
 */
public interface UserMessageService extends IService<UserMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<UserMessageEntity> findInformByUserId(Long userId,
									Integer size, Integer offset);
	
	UserMessageEntity findOneInformByUserId(Long userId,long id);
}

