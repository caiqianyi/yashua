package com.lebaoxun.modules.account.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.hystrix.UserServiceHystrix;

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
    
    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    @RequestMapping("/account/user/findByUserId")
    UserEntity findByUserId(@RequestParam("userId") Long userId);
	
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
	@RequestMapping("/account/user/findByAccount")
	UserEntity findByAccount(@RequestParam("account") String account);
	
	/**
     * 根据用户名，密码验证登录
     * @param username
     * @return
     */
	@RequestMapping("/account/user/login")
	UserEntity login(@RequestParam("username") String username,@RequestParam("password") String password);
	
	/**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
	@RequestMapping("/account/user/findByOpenid")
	UserEntity findByOpenid(@RequestParam("openid") String openid,
			@RequestParam(value="groupid",required=false) String groupid);
}

