package com.lebaoxun.pay.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysDeptEntity;
import com.lebaoxun.pay.service.hystrix.SysDeptServiceHystrix;

@FeignClient(value="manager-service",fallback=SysDeptServiceHystrix.class)
public interface ISysDeptService {
	
	/**
	 * 列表
	 */
	@RequestMapping("/sys/dept/list")
	public List<SysDeptEntity> list();

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/sys/dept/select")
	public ResponseMessage select(@RequestParam("userId")Long userId);

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/sys/dept/info")
	public ResponseMessage info(@RequestParam("userId")Long userId);
	
	/**
	 * 信息
	 */
	@RequestMapping("/sys/dept/info/{deptId}")
	public ResponseMessage infoByDeptId(@PathVariable("deptId") Long deptId);
	
	/**
	 * 保存
	 */
	@RequestMapping("/sys/dept/save")
	public ResponseMessage save(@RequestBody SysDeptEntity dept);
	
	/**
	 * 修改
	 */
	@RequestMapping("/sys/dept/update")
	public ResponseMessage update(@RequestBody SysDeptEntity dept);
	
	/**
	 * 删除
	 */
	@RequestMapping("/sys/dept/delete")
	public ResponseMessage delete(@RequestParam("deptId") long deptId);
}
