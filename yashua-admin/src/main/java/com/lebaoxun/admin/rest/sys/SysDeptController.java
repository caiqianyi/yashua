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
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysDeptEntity;
import com.lebaoxun.pay.service.ISysDeptService;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;


/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController{
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	@Autowired
	private ISysDeptService sysDeptService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public List<SysDeptEntity> list(){
		return sysDeptService.list();
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
	public ResponseMessage select(){
		return sysDeptService.select(oauth2SecuritySubject.getCurrentUser());
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/info")
	public ResponseMessage info(){
		return sysDeptService.info(oauth2SecuritySubject.getCurrentUser());
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{deptId}")
	public ResponseMessage info(@PathVariable("deptId") Long deptId){
		return sysDeptService.infoByDeptId(deptId);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public ResponseMessage save(@RequestBody SysDeptEntity dept){
		return sysDeptService.save(dept);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public ResponseMessage update(@RequestBody SysDeptEntity dept){
		return sysDeptService.update(dept);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public ResponseMessage delete(long deptId){
		return sysDeptService.delete(deptId);
	}
	
}
