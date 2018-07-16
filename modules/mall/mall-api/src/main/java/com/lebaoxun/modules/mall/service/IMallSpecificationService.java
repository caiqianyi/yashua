package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.mall.entity.MallSpecificationEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallSpecificationServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 规格表

 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@FeignClient(value="mall-service",fallback=MallSpecificationServiceHystrix.class)
public interface IMallSpecificationService {
	/**
     * 列表
     */
    @RequestMapping("/mall/mallspecification/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);
    
    @RequestMapping("/mall/mallspecification/select")
    ResponseMessage select();

    /**
     * 信息
     */
    @RequestMapping("/mall/mallspecification/info/{specificationId}")
    ResponseMessage info(@PathVariable("specificationId") Long specificationId);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallspecification/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallSpecificationEntity mallSpecification);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallspecification/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallSpecificationEntity mallSpecification);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallspecification/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] specificationIds);
    
}

