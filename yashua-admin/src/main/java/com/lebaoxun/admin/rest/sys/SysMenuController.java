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

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.admin.rest.BaseController;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysMenuEntity;
import com.lebaoxun.pay.service.ISysMenuService;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;

/**
 * 系统菜单
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	@Autowired
	private ISysMenuService sysMenuService;

	/**
	 * 导航菜单
	 */
	@RequestMapping("/nav")
	public ResponseMessage nav(){
		return sysMenuService.nav(oauth2SecuritySubject.getCurrentUser());
	}
	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	public List<SysMenuEntity> list(){
		return sysMenuService.list();
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	public ResponseMessage select(){
		return sysMenuService.select();
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	public ResponseMessage info(@PathVariable("menuId") Long menuId){
		return sysMenuService.info(menuId);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public ResponseMessage save(@RequestBody SysMenuEntity menu){
		return sysMenuService.save(menu);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public ResponseMessage update(@RequestBody SysMenuEntity menu){
		return sysMenuService.update(menu);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public ResponseMessage delete(@RequestParam("menuId") long menuId){
		return sysMenuService.delete(menuId);
	}
	
}
