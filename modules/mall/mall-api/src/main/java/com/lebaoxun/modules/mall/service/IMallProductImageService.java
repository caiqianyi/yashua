package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.mall.entity.MallProductImageEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallProductImageServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 商品图片表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@FeignClient(value="mall-service",fallback=MallProductImageServiceHystrix.class)
public interface IMallProductImageService {
	/**
     * 列表
     */
    @RequestMapping("/mall/mallproductimage/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductimage/info/{picImgId}")
    ResponseMessage info(@PathVariable("picImgId") Long picImgId);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductimage/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductImageEntity mallProductImage);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductimage/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductImageEntity mallProductImage);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductimage/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] picImgIds);
    
}

