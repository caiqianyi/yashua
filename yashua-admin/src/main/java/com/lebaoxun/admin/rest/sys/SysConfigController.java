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

package com.lebaoxun.admin.rest.sys;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.admin.rest.BaseController;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysConfigEntity;
import com.lebaoxun.pay.service.ISysConfigService;

/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends BaseController {
	@Autowired
	private ISysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	public ResponseMessage list(@RequestParam Map<String, Object> params){
		return sysConfigService.list(params);
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	public ResponseMessage info(@PathVariable("id") Long id){
		return sysConfigService.info(id);
	}
	
	/**
	 * 保存配置
	 */
	@RequestMapping("/save")
	public ResponseMessage save(@RequestBody SysConfigEntity config){
		return sysConfigService.save(config);
	}
	
	/**
	 * 修改配置
	 */
	@RequestMapping("/update")
	public ResponseMessage update(@RequestBody SysConfigEntity config){
		return sysConfigService.update(config);
	}
	
	/**
	 * 删除配置
	 */
	@RequestMapping("/delete")
	public ResponseMessage delete(@RequestBody Long[] ids){
		return sysConfigService.delete(ids);
	}

}
