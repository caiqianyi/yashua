package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.mall.service.hystrix.MallCategoryProductServiceHystrix;

/**
 * 商品分类关联表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@FeignClient(value="mall-service",fallback=MallCategoryProductServiceHystrix.class)
public interface IMallCategoryProductService {

    @RequestMapping("/mall/mallcategoryproduct/tree/{productId}")
    ResponseMessage tree(@PathVariable("productId") Long productId);

    @RequestMapping("/mall/mallcategoryproduct/edit")
    ResponseMessage edit(@RequestParam("adminId")Long adminId,
    		@RequestParam("productId")Long productId,
    		@RequestParam("categoryIds")Long[] categoryIds);
}

