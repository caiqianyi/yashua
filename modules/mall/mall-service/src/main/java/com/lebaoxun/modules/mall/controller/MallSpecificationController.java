package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallSpecificationEntity;
import com.lebaoxun.modules.mall.service.MallSpecificationService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 规格表

 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallSpecificationController {
    @Autowired
    private MallSpecificationService mallSpecificationService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallspecification/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallSpecificationService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallspecification/info/{specificationId}")
    ResponseMessage info(@PathVariable("specificationId") Long specificationId){
		MallSpecificationEntity mallSpecification = mallSpecificationService.selectById(specificationId);
        return ResponseMessage.ok().put("mallSpecification", mallSpecification);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallspecification/save")
    @RedisLock(value="mall:mallspecification:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallSpecificationEntity mallSpecification){
		mallSpecificationService.insert(mallSpecification);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallspecification/update")
    @RedisLock(value="mall:mallspecification:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallSpecificationEntity mallSpecification){
		mallSpecificationService.updateById(mallSpecification);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallspecification/delete")
    @RedisLock(value="mall:mallspecification:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] specificationIds){
		mallSpecificationService.deleteBatchIds(Arrays.asList(specificationIds));
        return ResponseMessage.ok();
    }

}
