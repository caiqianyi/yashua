package com.lebaoxun.modules.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.mall.service.MallCategoryProductService;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 商品分类关联表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallCategoryProductController {
    @Autowired
    private MallCategoryProductService mallCategoryProductService;

    @RequestMapping("/mall/mallcategoryproduct/tree/{productId}")
    ResponseMessage tree(@PathVariable("productId") Long productId){
    	return ResponseMessage.ok(mallCategoryProductService.findByProduct(productId));
    }

    @RequestMapping("/mall/mallcategoryproduct/edit")
    @RedisLock(value="mall:mallcategoryproduct:edit:lock:#arg0")
    ResponseMessage edit(@RequestParam("adminId")Long adminId,
    		@RequestParam("productId")Long productId,
    		@RequestParam("categoryIds")Long[] categoryIds){
    	mallCategoryProductService.edit(adminId, productId, categoryIds);
    	return ResponseMessage.ok();
    }
}
