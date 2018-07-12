package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.mall.entity.MallProductCommentImageEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallProductCommentImageServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 评价图片表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */

@FeignClient(value="mall-service",fallback=MallProductCommentImageServiceHystrix.class)
public interface IMallProductCommentImageService {
	/**
     * 列表
     */
    @RequestMapping("/mall/mallproductcommentimage/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductcommentimage/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductcommentimage/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductCommentImageEntity mallProductCommentImage);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductcommentimage/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductCommentImageEntity mallProductCommentImage);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductcommentimage/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids);
    
}

