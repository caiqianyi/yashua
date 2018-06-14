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

import java.util.List;
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
import com.lebaoxun.manager.sys.entity.SysRoleEntity;
import com.lebaoxun.manager.sys.service.SysRoleDeptService;
import com.lebaoxun.manager.sys.service.SysRoleMenuService;
import com.lebaoxun.manager.sys.service.SysRoleService;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/sys/role/list")
	public ResponseMessage list(@RequestParam Map<String, Object> params){
		PageUtils page = sysRoleService.queryPage(params);

		return ResponseMessage.ok(page);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/sys/role/select")
	public ResponseMessage select(){
		List<SysRoleEntity> list = sysRoleService.selectList(null);
		
		return ResponseMessage.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/sys/role/info/{roleId}")
	public ResponseMessage info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.selectById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		//查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[]{roleId});
		role.setDeptIdList(deptIdList);
		
		return ResponseMessage.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@RequestMapping("/sys/role/save")
	public ResponseMessage save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.save(role);
		
		return ResponseMessage.ok();
	}
	
	/**
	 * 修改角色
	 */
	@RequestMapping("/sys/role/update")
	public ResponseMessage update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.update(role);
		
		return ResponseMessage.ok();
	}
	
	/**
	 * 删除角色
	 */
	@RequestMapping("/sys/role/delete")
	public ResponseMessage delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return ResponseMessage.ok();
	}
}
