package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.modules.mall.entity.MallProductEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallProductServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 商品表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@FeignClient(value="mall-service",fallback=MallProductServiceHystrix.class)
public interface IMallProductService {
	
	@RequestMapping("/mall/product/show/list")
    ResponseMessage findShowProdcutByCategory(@RequestParam("categoryId")Long categoryId, 
    		@RequestParam("size")Integer size, 
    		@RequestParam("offset")Integer offset);
	
	@RequestMapping("/mall/product/score/list")
    ResponseMessage score(@RequestParam("size")Integer size, 
    		@RequestParam("offset")Integer offset);
	
	@RequestMapping("/mall/product/show/info")
    MallProductEntity findShowProdcutInfo(@RequestParam("id")Long id);
    
	/**
     * 列表
     */
    @RequestMapping("/mall/mallproduct/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproduct/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproduct/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductEntity mallProduct);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproduct/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductEntity mallProduct);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproduct/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestParam("id") Long id);
    
}

