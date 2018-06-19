package com.lebaoxun.modules.account.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.hystrix.UserServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 用户表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */

@FeignClient(value="account-service",fallback=UserServiceHystrix.class)
public interface IUserService {
	/**
     * 列表
     */
    @RequestMapping("/account/user/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/account/user/info/{id}")
    ResponseMessage info(@PathVariable("id") String id);

    /**
     * 保存
     */
    @RequestMapping("/account/user/save")
    ResponseMessage save(@RequestBody UserEntity user);

    /**
     * 修改
     */
    @RequestMapping("/account/user/update")
    ResponseMessage update(@RequestBody UserEntity user);

    /**
     * 删除
     */
    @RequestMapping("/account/user/delete")
    ResponseMessage delete(@RequestBody String[] ids);
    
}

