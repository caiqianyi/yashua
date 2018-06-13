/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.lebaoxun.manager.sys.rest;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.ValidatorUtils;
import com.lebaoxun.manager.sys.entity.SysConfigEntity;
import com.lebaoxun.manager.sys.service.SysConfigService;

/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/sys/config/list")
	public ResponseMessage list(@RequestParam Map<String, Object> params){
		PageUtils page = sysConfigService.queryPage(params);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("page", page);
		return ResponseMessage.ok();
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/sys/config/info/{id}")
	public ResponseMessage info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.selectById(id);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("config", config);
		return ResponseMessage.ok(data);
	}
	
	/**
	 * 保存配置
	 */
	@RequestMapping("/sys/config/save")
	public ResponseMessage save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.save(config);
		
		return ResponseMessage.ok();
	}
	
	/**
	 * 修改配置
	 */
	@RequestMapping("/sys/config/update")
	public ResponseMessage update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		
		sysConfigService.update(config);
		
		return ResponseMessage.ok();
	}
	
	/**
	 * 删除配置
	 */
	@RequestMapping("/sys/config/delete")
	public ResponseMessage delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		
		return ResponseMessage.ok();
	}

}
