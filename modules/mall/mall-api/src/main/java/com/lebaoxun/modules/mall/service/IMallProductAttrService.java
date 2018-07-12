package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.mall.entity.MallProductAttrEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallProductAttrServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 商品属性表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@FeignClient(value="mall-service",fallback=MallProductAttrServiceHystrix.class)
public interface IMallProductAttrService {
	/**
     * 列表
     */
    @RequestMapping("/mall/mallproductattr/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductattr/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductattr/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductAttrEntity mallProductAttr);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductattr/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductAttrEntity mallProductAttr);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductattr/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids);
    
}

