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

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysDictEntity;
import com.lebaoxun.pay.service.ISysDictService;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.1.0 2018-01-27
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {
	
    @Autowired
    private ISysDictService sysDictService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public ResponseMessage list(@RequestParam Map<String, Object> params){
        return sysDictService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public ResponseMessage info(@PathVariable("id") Long id){
        return sysDictService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public ResponseMessage save(@RequestBody SysDictEntity dict){
        return sysDictService.save(dict);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public ResponseMessage update(@RequestBody SysDictEntity dict){
        return sysDictService.update(dict);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public ResponseMessage delete(@RequestBody Long[] ids){
        return sysDictService.delete(ids);
    }

}
