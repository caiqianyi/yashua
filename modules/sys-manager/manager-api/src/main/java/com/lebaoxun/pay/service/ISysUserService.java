package com.lebaoxun.pay.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysUserEntity;
import com.lebaoxun.pay.service.hystrix.SysUserServiceHystrix;

@FeignClient(value="manager-service",fallback=SysUserServiceHystrix.class)
public interface ISysUserService {
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/sys/user/list")
	public ResponseMessage list(@RequestParam Map<String, Object> params);
	
	/**
	 * 修改登录用户密码
	 */
	@RequestMapping("/sys/user/password")
	public ResponseMessage password(@RequestParam("userId") Long userId, 
			@RequestParam("password") String password, 
			@RequestParam("newPassword") String newPassword);
	
	@RequestMapping("/sys/user/findByUserId")
	SysUserEntity findByUserId(@RequestParam("userId") Long userId);
	
	@RequestMapping("/sys/user/findByUsername")
	SysUserEntity findByUsername(@RequestParam("username") String username);
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/sys/user/info/{userId}")
	public ResponseMessage info(@PathVariable("userId") Long userId);
	
	/**
	 * 保存用户
	 */
	@RequestMapping("/sys/user/save")
	public ResponseMessage save(@RequestBody SysUserEntity user);
	
	/**
	 * 修改用户
	 */
	@RequestMapping("/sys/user/update")
	public ResponseMessage update(@RequestBody SysUserEntity user);
	
	/**
	 * 删除用户
	 */
	@RequestMapping("/sys/user/delete")
	public ResponseMessage delete(@RequestParam("userIds") Long[] userIds,
			@RequestParam("userId")Long userId);
}
