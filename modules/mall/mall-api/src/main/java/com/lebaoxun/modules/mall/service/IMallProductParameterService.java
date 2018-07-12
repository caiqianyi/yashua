package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.mall.entity.MallProductParameterEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallProductParameterServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 商品参数表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@FeignClient(value="mall-service",fallback=MallProductParameterServiceHystrix.class)
public interface IMallProductParameterService {
	/**
     * 列表
     */
    @RequestMapping("/mall/mallproductparameter/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductparameter/info/{productParameterId}")
    ResponseMessage info(@PathVariable("productParameterId") Long productParameterId);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductparameter/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductParameterEntity mallProductParameter);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductparameter/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductParameterEntity mallProductParameter);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductparameter/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] productParameterIds);
    
}

