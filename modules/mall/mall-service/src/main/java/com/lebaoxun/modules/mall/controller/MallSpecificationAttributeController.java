package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallSpecificationAttributeEntity;
import com.lebaoxun.modules.mall.service.MallSpecificationAttributeService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 规格属性表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallSpecificationAttributeController {
    @Autowired
    private MallSpecificationAttributeService mallSpecificationAttributeService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallspecificationattribute/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallSpecificationAttributeService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallspecificationattribute/info/{specAttrId}")
    ResponseMessage info(@PathVariable("specAttrId") Long specAttrId){
		MallSpecificationAttributeEntity mallSpecificationAttribute = mallSpecificationAttributeService.selectById(specAttrId);
        return ResponseMessage.ok().put("mallSpecificationAttribute", mallSpecificationAttribute);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallspecificationattribute/save")
    @RedisLock(value="mall:mallspecificationattribute:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallSpecificationAttributeEntity mallSpecificationAttribute){
		mallSpecificationAttributeService.insert(mallSpecificationAttribute);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallspecificationattribute/update")
    @RedisLock(value="mall:mallspecificationattribute:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallSpecificationAttributeEntity mallSpecificationAttribute){
		mallSpecificationAttributeService.updateById(mallSpecificationAttribute);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallspecificationattribute/delete")
    @RedisLock(value="mall:mallspecificationattribute:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] specAttrIds){
		mallSpecificationAttributeService.deleteBatchIds(Arrays.asList(specAttrIds));
        return ResponseMessage.ok();
    }

}
