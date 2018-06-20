package com.lebaoxun.modules.account.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.account.entity.UserMessageEntity;
import com.lebaoxun.modules.account.service.hystrix.UserMessageServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

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
    ResponseMessage info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @RequestMapping("/account/usermessage/save")
    ResponseMessage save(@RequestBody UserMessageEntity userMessage);

    /**
     * 修改
     */
    @RequestMapping("/account/usermessage/update")
    ResponseMessage update(@RequestBody UserMessageEntity userMessage);

    /**
     * 删除
     */
    @RequestMapping("/account/usermessage/delete")
    ResponseMessage delete(@RequestBody Integer[] ids);
    
}
