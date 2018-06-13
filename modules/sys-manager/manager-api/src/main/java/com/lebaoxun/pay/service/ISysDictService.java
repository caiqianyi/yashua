package com.lebaoxun.pay.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.manager.sys.entity.SysDictEntity;
import com.lebaoxun.pay.service.hystrix.SysDictServiceHystrix;

@FeignClient(value="manager-service",fallback=SysDictServiceHystrix.class)
public interface ISysDictService {
	/**
     * 列表
     */
    @RequestMapping("/sys/dict/list")
    public ResponseMessage list(@RequestParam Map<String, Object> params);

    /**
     * 信息
     */
    @RequestMapping("/sys/dict/info/{id}")
    public ResponseMessage info(@PathVariable("id") Long id);

    /**
     * 保存
     */
    @RequestMapping("/sys/dict/save")
    public ResponseMessage save(@RequestBody SysDictEntity dict);

    /**
     * 修改
     */
    @RequestMapping("/sys/dict/update")
    public ResponseMessage update(@RequestBody SysDictEntity dict);

    /**
     * 删除
     */
    @RequestMapping("/sys/dict/delete")
    public ResponseMessage delete(@RequestBody Long[] ids);
}
