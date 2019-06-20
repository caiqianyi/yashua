package com.lebaoxun.modules.account.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.modules.account.entity.UserLogEntity;
import com.lebaoxun.modules.account.service.hystrix.UserLogServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */

@FeignClient(value="account-service",fallback=UserLogServiceHystrix.class)
public interface IUserLogService {
	/**
     * 列表
     */
    @RequestMapping("/account/userlog/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/account/userlog/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id);

    
    @RequestMapping("/account/userlog/allLogType")
    ResponseMessage allLogType();
    
    /**
     * 查询账户交易记录
     * @param userId
     * @param size
     * @param offset
     * @return
     */
    @RequestMapping("/account/userlog/findAccountLogByUserId")
    ResponseMessage findAccountLogByUserId(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="size",required=false) Integer size, 
    		@RequestParam(value="offset",required=false) Integer offset);
    
    @RequestMapping("/account/userlog/zRange")
    ResponseMessage zRange(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="logType") String logType,
    		@RequestParam(value="time") String time);
    
    @RequestMapping("/account/userlog/zRank")
    ResponseMessage zRank(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="logType") String logType,
    		@RequestParam(value="time") String time);

    /**
     * 本周击败次数和所获积分
     */
    @RequestMapping("/account/findWeekInfoByUserId")
	Map<String,Long> zRankWeek(@RequestParam(value="userId") Long userId, @RequestParam(value="logType") String logType);
}

