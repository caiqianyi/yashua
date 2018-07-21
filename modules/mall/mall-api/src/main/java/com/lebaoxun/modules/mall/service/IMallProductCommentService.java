package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.modules.mall.entity.MallProductCommentEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallProductCommentServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 评价表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */

@FeignClient(value="mall-service",fallback=MallProductCommentServiceHystrix.class)
public interface IMallProductCommentService {
	
	@RequestMapping("/mall/mallproductcomment/publish")
	ResponseMessage publish(@RequestParam("userId") Long userId,
			@RequestParam("orderProductId") Long orderProductId,
			@RequestBody MallProductCommentEntity comment);

	@RequestMapping("/mall/mallproductcomment/selectByProduct")
	ResponseMessage selectByProduct(
			@RequestParam("productId") Long productId);
	
	@RequestMapping("/mall/mallproductcomment/selectLastByProduct")
	MallProductCommentEntity selectLastByProduct(@RequestParam("productId") Long productId);
	
	/**
     * 列表
     */
    @RequestMapping("/mall/mallproductcomment/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductcomment/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductcomment/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductCommentEntity mallProductComment);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductcomment/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductCommentEntity mallProductComment);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductcomment/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids);
    
}

