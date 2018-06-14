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


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.Assert;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.PwdUtil;
import com.lebaoxun.commons.utils.ValidatorUtils;
import com.lebaoxun.manager.sys.entity.SysUserEntity;
import com.lebaoxun.manager.sys.service.SysUserRoleService;
import com.lebaoxun.manager.sys.service.SysUserService;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/sys/user/list")
	ResponseMessage list(@RequestParam Map<String, Object> params){
		PageUtils page = sysUserService.queryPage(params);
		return ResponseMessage.ok(page);
	}
	
	@RequestMapping("/sys/user/findByUserId")
	SysUserEntity findByUserId(@RequestParam("userId") Long userId){
		return sysUserService.selectById(userId);
	}
	
	@RequestMapping("/sys/user/findByUsername")
	SysUserEntity findByUsername(@RequestParam("username") String username){
		return sysUserService.selectOne( new EntityWrapper<SysUserEntity>().eq("username", username));
	}
	
	@RequestMapping("/sys/user/login")
	SysUserEntity login(@RequestParam("username") String username,@RequestParam("password") String password){
		return sysUserService.selectOne(new EntityWrapper<SysUserEntity>().eq("username", username).eq("password", PwdUtil.getMd5Password(username, password)));
	}
	
	/**
	 * 修改登录用户密码
	 */
	@RequestMapping("/sys/user/password")
	ResponseMessage password(@RequestParam("userId") Long userId, 
			@RequestParam("password") String password, 
			@RequestParam("newPassword") String newPassword){
		Assert.notEmpty(newPassword, "-1","新密码不为能空");
		SysUserEntity user = sysUserService.selectById(userId);
		//新密码
		password = PwdUtil.getMd5Password(user.getUsername(), password);
		newPassword = PwdUtil.getMd5Password(user.getUsername(), newPassword);
				
		//更新密码
		boolean flag = sysUserService.updatePassword(userId, password, newPassword);
		if(!flag){
			return ResponseMessage.error("-1","原密码不正确");
		}
		return ResponseMessage.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/sys/user/info/{userId}")
	ResponseMessage info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.selectById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return ResponseMessage.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@RequestMapping("/sys/user/save")
	ResponseMessage save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, SysUserEntity.class);
		
		sysUserService.save(user);
		
		return ResponseMessage.ok();
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping("/sys/user/update")
	ResponseMessage update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, SysUserEntity.class);

		sysUserService.update(user);
		
		return ResponseMessage.ok();
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping("/sys/user/delete")
	ResponseMessage delete(@RequestParam("userIds") Long[] userIds,
			@RequestParam("userId")Long userId){
		if(ArrayUtils.contains(userIds, 1L)){
			return ResponseMessage.error("-1","系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, userId)){
			return ResponseMessage.error("-1","当前用户不能删除");
		}

		sysUserService.deleteBatchIds(Arrays.asList(userIds));
		
		return ResponseMessage.ok();
	}
}
