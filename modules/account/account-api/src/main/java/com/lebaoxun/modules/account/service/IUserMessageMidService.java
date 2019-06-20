package com.lebaoxun.modules.account.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.entity.UserMessageMidEntity;
import com.lebaoxun.modules.account.service.hystrix.UserMessageMidServiceHystrix;

/**
 * 用户消息中间表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-20 15:44:50
 */
@FeignClient(value="account-service",fallback=UserMessageMidServiceHystrix.class)
public interface IUserMessageMidService {
	/**
     * 列表
     */
    @RequestMapping("/account/usermessagemid/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/account/usermessagemid/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id,
    		@RequestParam(value="userId",required=false) Long userId);
  
    /**
     * 保存
     */
    @RequestMapping("/account/usermessagemid/save")
    ResponseMessage save(@RequestParam("adminId") Long adminId,
    		@RequestBody UserMessageMidEntity userMessageMid);

    /**
     * 修改
     */
    @RequestMapping("/account/usermessagemid/update")
    ResponseMessage update(@RequestParam("adminId") Long adminId,
    		@RequestBody UserMessageMidEntity userMessageMid);

    /**
     * 删除
     */
    @RequestMapping("/account/usermessagemid/delete")
    ResponseMessage delete(@RequestParam("adminId") Long adminId,
    		@RequestBody Integer[] ids);
}
