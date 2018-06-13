package com.lebaoxun.pay.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysConfigEntity;
import com.lebaoxun.pay.service.hystrix.SysConfigServiceHystrix;

@FeignClient(value="manager-service",fallback=SysConfigServiceHystrix.class)
public interface ISysConfigService {
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/sys/config/list")
	public ResponseMessage list(@RequestParam Map<String, Object> params);
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/sys/config/info/{id}")
	public ResponseMessage info(@PathVariable("id") Long id);
	
	/**
	 * 保存配置
	 */
	@RequestMapping("/sys/config/save")
	public ResponseMessage save(@RequestBody SysConfigEntity config);
	
	/**
	 * 修改配置
	 */
	@RequestMapping("/sys/config/update")
	public ResponseMessage update(@RequestBody SysConfigEntity config);
	
	/**
	 * 删除配置
	 */
	@RequestMapping("/sys/config/delete")
	public ResponseMessage delete(@RequestBody Long[] ids);
}
