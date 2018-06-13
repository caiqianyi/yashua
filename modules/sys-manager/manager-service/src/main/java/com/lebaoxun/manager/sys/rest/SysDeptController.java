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
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysDeptEntity;
import com.lebaoxun.manager.sys.service.SysDeptService;


/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@RestController
public class SysDeptController extends AbstractController {
	@Autowired
	private SysDeptService sysDeptService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/sys/dept/list")
	public List<SysDeptEntity> list(){
		List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

		return deptList;
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/sys/dept/select")
	public ResponseMessage select(@RequestParam("userId")Long userId){
		List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

		//添加一级部门
		if(userId == 1){
			SysDeptEntity root = new SysDeptEntity();
			root.setDeptId(0L);
			root.setName("一级部门");
			root.setParentId(-1L);
			root.setOpen(true);
			deptList.add(root);
		}
		Map<String,Object> dataModel = new HashMap<String,Object>();
		dataModel.put("deptList", deptList);
		return ResponseMessage.ok(dataModel);
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/sys/dept/info")
	public ResponseMessage info(@RequestParam("userId")Long userId){
		long deptId = 0;
		if(userId != 1){
			List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
			Long parentId = null;
			for(SysDeptEntity sysDeptEntity : deptList){
				if(parentId == null){
					parentId = sysDeptEntity.getParentId();
					continue;
				}

				if(parentId > sysDeptEntity.getParentId().longValue()){
					parentId = sysDeptEntity.getParentId();
				}
			}
			deptId = parentId;
		}
		Map<String,Object> dataModel = new HashMap<String,Object>();
		dataModel.put("deptId", deptId);
		return ResponseMessage.ok(dataModel);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/sys/dept/info/{deptId}")
	public ResponseMessage infoByDeptId(@PathVariable("deptId") Long deptId){
		SysDeptEntity dept = sysDeptService.selectById(deptId);
		Map<String,Object> dataModel = new HashMap<String,Object>();
		dataModel.put("dept", dept);
		return ResponseMessage.ok(dataModel);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/sys/dept/save")
	public ResponseMessage save(@RequestBody SysDeptEntity dept){
		sysDeptService.insert(dept);
		
		return ResponseMessage.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/sys/dept/update")
	public ResponseMessage update(@RequestBody SysDeptEntity dept){
		sysDeptService.updateById(dept);
		
		return ResponseMessage.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/sys/dept/delete")
	public ResponseMessage delete(@RequestParam("deptId") long deptId){
		//判断是否有子部门
		List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
		if(deptList.size() > 0){
			return ResponseMessage.error("-1","请先删除子部门");
		}

		sysDeptService.deleteById(deptId);
		
		return ResponseMessage.ok();
	}
	
}
