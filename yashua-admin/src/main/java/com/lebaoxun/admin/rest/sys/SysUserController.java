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

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lebaoxun.admin.rest.BaseController;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysUserEntity;
import com.lebaoxun.pay.service.ISysUserService;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	public ResponseMessage list(@RequestParam Map<String, Object> params){
		return sysUserService.list(params);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public ResponseMessage info(){
		return sysUserService.info(oauth2SecuritySubject.getCurrentUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@RequestMapping("/password")
	public ResponseMessage password(String password, String newPassword){
		return sysUserService.password(oauth2SecuritySubject.getCurrentUser(), password, newPassword);
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	public ResponseMessage info(@PathVariable("userId") Long userId){
		return sysUserService.info(userId);
	}
	
	/**
	 * 保存用户
	 */
	@RequestMapping("/save")
	public ResponseMessage save(@RequestBody SysUserEntity user){
		logger.debug("user={}",new Gson().toJson(user));
		return sysUserService.save(user);
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping("/update")
	public ResponseMessage update(@RequestBody SysUserEntity user){
		return sysUserService.update(user);
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping("/delete")
	public ResponseMessage delete(@RequestBody Long[] userIds){
		return sysUserService.delete(userIds, oauth2SecuritySubject.getCurrentUser());
	}

}
