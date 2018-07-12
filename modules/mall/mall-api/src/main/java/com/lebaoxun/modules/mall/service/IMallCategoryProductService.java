package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.mall.entity.MallCategoryProductEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallCategoryProductServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 商品分类关联表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@FeignClient(value="mall-service",fallback=MallCategoryProductServiceHystrix.class)
public interface IMallCategoryProductService {
	/**
     * 列表
     */
    @RequestMapping("/mall/mallcategoryproduct/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallcategoryproduct/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallcategoryproduct/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallCategoryProductEntity mallCategoryProduct);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallcategoryproduct/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallCategoryProductEntity mallCategoryProduct);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallcategoryproduct/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids);
    
}

