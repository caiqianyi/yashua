package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallCategoryProductEntity;
import com.lebaoxun.modules.mall.service.MallCategoryProductService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 商品分类关联表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallCategoryProductController {
    @Autowired
    private MallCategoryProductService mallCategoryProductService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallcategoryproduct/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallCategoryProductService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallcategoryproduct/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
		MallCategoryProductEntity mallCategoryProduct = mallCategoryProductService.selectById(id);
        return ResponseMessage.ok().put("mallCategoryProduct", mallCategoryProduct);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallcategoryproduct/save")
    @RedisLock(value="mall:mallcategoryproduct:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallCategoryProductEntity mallCategoryProduct){
		mallCategoryProductService.insert(mallCategoryProduct);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallcategoryproduct/update")
    @RedisLock(value="mall:mallcategoryproduct:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallCategoryProductEntity mallCategoryProduct){
		mallCategoryProductService.updateById(mallCategoryProduct);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallcategoryproduct/delete")
    @RedisLock(value="mall:mallcategoryproduct:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids){
		mallCategoryProductService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
