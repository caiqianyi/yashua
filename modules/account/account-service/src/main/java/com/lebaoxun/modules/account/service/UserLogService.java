package com.lebaoxun.modules.account.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.account.entity.UserLogEntity;

/**
 * 
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */
public interface UserLogService extends IService<UserLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * 查询所有日志类型
     * @return
     */
    List<Map<String,Object>> queryAllLogType();
    
    List<UserLogEntity> queryAccountLogByUserId(Long userId,
			Integer size, Integer offset);
    
    void zRange(Long userId,String logType,String time);
    
    Map<String,Object> zRank(Long userId,String logType,String time);
}

