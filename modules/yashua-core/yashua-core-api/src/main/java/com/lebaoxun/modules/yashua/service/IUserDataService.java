package com.lebaoxun.modules.yashua.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.yashua.service.hystrix.UserDataServiceHystrix;

/**
 * 用户口气表
 */
@FeignClient(value="yashua-core-service",fallback=UserDataServiceHystrix.class)
public interface IUserDataService {
	/**
     * 列表
     */
    @RequestMapping("/yashua/userdata/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);
    /**
     * 获取历史口气数据
     */
    @RequestMapping("/yashua/userdata/history/list")
	ResponseMessage hlist(@RequestParam Map<String, Object> params);
    /*
     * 保存口气数据
     */
    @RequestMapping("/yashua/userdata/save")
	ResponseMessage save(@RequestParam("kouqi") Long kouqi,@RequestParam("user_id") Long user_id);

    /**
     * 获取历史口气数据App端使用
     */
    @RequestMapping("/yashua/userdata/history/listforApp")
    ResponseMessage hlistApp(@RequestParam Map<String, Object> params);
}
