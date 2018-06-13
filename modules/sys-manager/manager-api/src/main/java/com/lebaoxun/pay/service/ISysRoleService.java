package com.lebaoxun.pay.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysRoleEntity;
import com.lebaoxun.pay.service.hystrix.SysRoleServiceHystrix;

@FeignClient(value="manager-service",fallback=SysRoleServiceHystrix.class)
public interface ISysRoleService {
	/**
	 * 角色列表
	 */
	@RequestMapping("/sys/role/list")
	public ResponseMessage list(@RequestParam Map<String, Object> params);
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/sys/role/select")
	public ResponseMessage select();
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/sys/role/info/{roleId}")
	public ResponseMessage info(@PathVariable("roleId") Long roleId);
	
	/**
	 * 保存角色
	 */
	@RequestMapping("/sys/role/save")
	public ResponseMessage save(@RequestBody SysRoleEntity role);
	
	/**
	 * 修改角色
	 */
	@RequestMapping("/sys/role/update")
	public ResponseMessage update(@RequestBody SysRoleEntity role);
	
	/**
	 * 删除角色
	 */
	@RequestMapping("/sys/role/delete")
	public ResponseMessage delete(@RequestBody Long[] roleIds);
}
