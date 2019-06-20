package com.lebaoxun.modules.account.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.entity.UserMessageEntity;
import com.lebaoxun.modules.account.service.hystrix.UserMessageServiceHystrix;

/**
 * 用户消息
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-20 15:44:50
 */

@FeignClient(value="account-service",fallback=UserMessageServiceHystrix.class)
public interface IUserMessageService {
	/**
     * 列表
     */
    @RequestMapping("/account/usermessage/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/account/usermessage/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id,
    		@RequestParam(value="userId",required=false) Long userId);
    
    @RequestMapping("/account/usermessage/findInformByUserId")
    ResponseMessage findInformByUserId(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="size",required=false) Integer size, 
    		@RequestParam(value="offset",required=false) Integer offset);

    @RequestMapping("/account/usermessage/findOneInformByUserId")
    ResponseMessage findOneInformByUserId(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="id") long id);

    /**
     * 保存
     */
    @RequestMapping("/account/usermessage/save")
    ResponseMessage save(@RequestParam("adminId") Long adminId,
    		@RequestBody UserMessageEntity userMessage);

    /**
     * 修改
     */
    @RequestMapping("/account/usermessage/update")
    ResponseMessage update(@RequestParam("adminId") Long adminId,
    		@RequestBody UserMessageEntity userMessage);

    /**
     * 删除
     */
    @RequestMapping("/account/usermessage/delete")
    ResponseMessage delete(@RequestParam("adminId") Long adminId,
    		@RequestBody Long[] ids);
    
}

