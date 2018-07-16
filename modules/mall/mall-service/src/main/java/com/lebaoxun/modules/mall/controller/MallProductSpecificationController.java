package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductSpecificationEntity;
import com.lebaoxun.modules.mall.service.MallProductSpecificationService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 商品规格表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallProductSpecificationController {
    @Autowired
    private MallProductSpecificationService mallProductSpecificationService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproductspecification/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallProductSpecificationService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductspecification/info/{productSpecId}")
    ResponseMessage info(@PathVariable("productSpecId") Long productSpecId){
		MallProductSpecificationEntity mallProductSpecification = mallProductSpecificationService.selectById(productSpecId);
        return ResponseMessage.ok().put("mallProductSpecification", mallProductSpecification);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductspecification/save")
    @RedisLock(value="mall:mallproductspecification:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductSpecificationEntity mallProductSpecification){
		mallProductSpecificationService.save(mallProductSpecification);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductspecification/update")
    @RedisLock(value="mall:mallproductspecification:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductSpecificationEntity mallProductSpecification){
		mallProductSpecificationService.update(mallProductSpecification);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductspecification/delete")
    @RedisLock(value="mall:mallproductspecification:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] productSpecIds){
		mallProductSpecificationService.deleteBatchIds(Arrays.asList(productSpecIds));
        return ResponseMessage.ok();
    }

}
