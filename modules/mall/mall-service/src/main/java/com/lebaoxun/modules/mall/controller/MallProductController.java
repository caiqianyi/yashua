package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductEntity;
import com.lebaoxun.modules.mall.service.MallProductService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 商品表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallProductController {
    @Autowired
    private MallProductService mallProductService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproduct/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallProductService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproduct/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
		MallProductEntity mallProduct = mallProductService.selectById(id);
        return ResponseMessage.ok().put("mallProduct", mallProduct);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproduct/save")
    @RedisLock(value="mall:mallproduct:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductEntity mallProduct){
		mallProductService.insert(mallProduct);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproduct/update")
    @RedisLock(value="mall:mallproduct:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductEntity mallProduct){
		mallProductService.updateById(mallProduct);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproduct/delete")
    @RedisLock(value="mall:mallproduct:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids){
		mallProductService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
