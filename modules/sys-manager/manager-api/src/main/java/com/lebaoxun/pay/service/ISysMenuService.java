package com.lebaoxun.pay.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysMenuEntity;
import com.lebaoxun.pay.service.hystrix.SysMenuServiceHystrix;

@FeignClient(value="manager-service",fallback=SysMenuServiceHystrix.class)
public interface ISysMenuService {
	/**
	 * 导航菜单
	 */
	@RequestMapping("/sys/menu/nav")
	public ResponseMessage nav(@RequestParam("userId")Long userId);
	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/sys/menu/list")
	public List<SysMenuEntity> list();
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/sys/menu/select")
	public ResponseMessage select();
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/sys/menu/info/{menuId}")
	public ResponseMessage info(@PathVariable("menuId") Long menuId);
	
	/**
	 * 保存
	 */
	@RequestMapping("/sys/menu/save")
	public ResponseMessage save(@RequestBody SysMenuEntity menu);
	
	/**
	 * 修改
	 */
	@RequestMapping("/sys/menu/update")
	public ResponseMessage update(@RequestBody SysMenuEntity menu);
	
	/**
	 * 删除
	 */
	@RequestMapping("/sys/menu/delete")
	public ResponseMessage delete(@RequestParam("menuId")long menuId);
}
