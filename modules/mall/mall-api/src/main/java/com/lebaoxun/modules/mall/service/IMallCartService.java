package com.lebaoxun.modules.mall.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallCartServiceHystrix;

/**
 * 购物车表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@FeignClient(value="mall-service",fallback=MallCartServiceHystrix.class)
public interface IMallCartService {
	/**
     * 列表
     */
    @RequestMapping("/mall/mallcart/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallcart/info/{cartId}")
    ResponseMessage info(@PathVariable("cartId") Long cartId);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallcart/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallCartEntity mallCart);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallcart/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallCartEntity mallCart);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallcart/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] cartIds);
    
    /**
     * 同步购物车
     * @param userId
     * @param list
     * @return
     */
    @RequestMapping("/mall/mallcart/sync")
    ResponseMessage sync(@RequestParam("userId")Long userId,@RequestBody List<MallCartEntity> list);
    
    @RequestMapping("/mall/mallcart/findByUser")
    ResponseMessage findByUser(@RequestParam("userId") Long userId);
    
    @RequestMapping("/mall/mallcart/queryList")
    ResponseMessage queryByProductSpecId(@RequestBody Long[] ids);
}

