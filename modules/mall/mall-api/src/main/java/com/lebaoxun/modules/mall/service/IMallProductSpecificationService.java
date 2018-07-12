package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.mall.entity.MallProductSpecificationEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallProductSpecificationServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 商品规格表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@FeignClient(value="mall-service",fallback=MallProductSpecificationServiceHystrix.class)
public interface IMallProductSpecificationService {
	/**
     * 列表
     */
    @RequestMapping("/mall/mallproductspecification/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductspecification/info/{productSpecId}")
    ResponseMessage info(@PathVariable("productSpecId") Long productSpecId);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductspecification/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductSpecificationEntity mallProductSpecification);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductspecification/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductSpecificationEntity mallProductSpecification);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductspecification/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] productSpecIds);
    
}

