package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductAttrEntity;
import com.lebaoxun.modules.mall.service.MallProductAttrService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 商品属性表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallProductAttrController {
    @Autowired
    private MallProductAttrService mallProductAttrService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproductattr/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallProductAttrService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductattr/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
		MallProductAttrEntity mallProductAttr = mallProductAttrService.selectById(id);
        return ResponseMessage.ok().put("mallProductAttr", mallProductAttr);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductattr/save")
    @RedisLock(value="mall:mallproductattr:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductAttrEntity mallProductAttr){
		mallProductAttrService.insert(mallProductAttr);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductattr/update")
    @RedisLock(value="mall:mallproductattr:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductAttrEntity mallProductAttr){
		mallProductAttrService.updateById(mallProductAttr);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductattr/delete")
    @RedisLock(value="mall:mallproductattr:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids){
		mallProductAttrService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
