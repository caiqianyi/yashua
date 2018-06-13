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
import com.lebaoxun.manager.sys.entity.SysRoleEntity;
import com.lebaoxun.pay.service.ISysRoleService;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
	
	@Autowired
	private ISysRoleService sysRoleService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	public ResponseMessage list(@RequestParam Map<String, Object> params){
		return sysRoleService.list(params);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	public ResponseMessage select(){
		return sysRoleService.select();
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	public ResponseMessage info(@PathVariable("roleId") Long roleId){
		return sysRoleService.info(roleId);
	}
	
	/**
	 * 保存角色
	 */
	@RequestMapping("/save")
	public ResponseMessage save(@RequestBody SysRoleEntity role){
		return sysRoleService.save(role);
	}
	
	/**
	 * 修改角色
	 */
	@RequestMapping("/update")
	public ResponseMessage update(@RequestBody SysRoleEntity role){
		return sysRoleService.update(role);
	}
	
	/**
	 * 删除角色
	 */
	@RequestMapping("/delete")
	public ResponseMessage delete(@RequestBody Long[] roleIds){
		return sysRoleService.delete(roleIds);
	}
}
