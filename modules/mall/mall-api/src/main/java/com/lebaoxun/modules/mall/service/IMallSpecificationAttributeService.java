package com.lebaoxun.modules.mall.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.mall.entity.MallSpecificationAttributeEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallSpecificationAttributeServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 规格属性表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */

@FeignClient(value="mall-service",fallback=MallSpecificationAttributeServiceHystrix.class)
public interface IMallSpecificationAttributeService {
	/**
     * 列表
     */
    @RequestMapping("/mall/mallspecificationattribute/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallspecificationattribute/info/{specAttrId}")
    ResponseMessage info(@PathVariable("specAttrId") Long specAttrId);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallspecificationattribute/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallSpecificationAttributeEntity mallSpecificationAttribute);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallspecificationattribute/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallSpecificationAttributeEntity mallSpecificationAttribute);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallspecificationattribute/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] specAttrIds);
    
}

