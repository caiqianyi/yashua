package com.lebaoxun.modules.news.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.modules.news.entity.ReplysEntity;
import com.lebaoxun.modules.news.service.hystrix.ReplysServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 回复表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */

@FeignClient(value="news-service",fallback=ReplysServiceHystrix.class)
public interface IReplysService {
	/**
     * 列表
     */
    @RequestMapping("/news/replys/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);
    
    @RequestMapping("/news/findReplys")
    ResponseMessage findReplys(@RequestParam(value="userTbs",required=false,defaultValue="user") String userTbs,
			@RequestParam(value="type",required=false,defaultValue="news") String type, 
			@RequestParam("recordId") String recordId,
			@RequestParam("size") Integer size,
			@RequestParam("offset") Integer offset);


    /**
     * 信息
     */
    @RequestMapping("/news/replys/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @RequestMapping("/news/replys/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody ReplysEntity replys);

    /**
     * 修改
     */
    @RequestMapping("/news/replys/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody ReplysEntity replys);

    /**
     * 删除
     */
    @RequestMapping("/news/replys/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids);
    
}

