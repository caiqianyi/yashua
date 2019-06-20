package com.lebaoxun.modules.sign.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.sign.entity.SignLogEntity;
import com.lebaoxun.modules.sign.service.hystrix.SignLogServiceHystrix;

/**
 * 签到记录表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */

@FeignClient(value="sign-service",fallback=SignLogServiceHystrix.class)
public interface ISignLogService {
	/**
     * 列表
     */
    @RequestMapping("/sign/signlog/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/sign/signlog/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @RequestMapping("/sign/signlog/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody SignLogEntity signLog);

    /**
     * 修改
     */
    @RequestMapping("/sign/signlog/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody SignLogEntity signLog);

    /**
     * 删除
     */
    @RequestMapping("/sign/signlog/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids);
    
    /**
     * 签到
     * @param userId 签到用户ID
     * @param groupId 奖品分类
     * @return
     */
    @RequestMapping("/sign/in")
    ResponseMessage signIn(@RequestParam("userId")Long userId,
    		@RequestParam("groupId")String groupId);
    
    @RequestMapping("/sign/findMonthInfoByUserId")
    ResponseMessage findMonthInfoByUserId(@RequestParam("userId")Long userId,
    		@RequestParam("time")String time);

  
}

