package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.mall.entity.MallOrderProductEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallOrderProductServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 订单明细表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */

@FeignClient(value="mall-service",fallback=MallOrderProductServiceHystrix.class)
public interface IMallOrderProductService {
	/**
     * 列表
     */
    @RequestMapping("/mall/mallorderproduct/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallorderproduct/info/{orderProductId}")
    ResponseMessage info(@PathVariable("orderProductId") Long orderProductId);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallorderproduct/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallOrderProductEntity mallOrderProduct);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallorderproduct/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallOrderProductEntity mallOrderProduct);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallorderproduct/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] orderProductIds);
    
}

