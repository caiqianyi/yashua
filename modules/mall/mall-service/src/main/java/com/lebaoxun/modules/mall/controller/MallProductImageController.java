package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductImageEntity;
import com.lebaoxun.modules.mall.service.MallProductImageService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 商品图片表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallProductImageController {
    @Autowired
    private MallProductImageService mallProductImageService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproductimage/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallProductImageService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductimage/info/{picImgId}")
    ResponseMessage info(@PathVariable("picImgId") Long picImgId){
		MallProductImageEntity mallProductImage = mallProductImageService.selectById(picImgId);
        return ResponseMessage.ok().put("mallProductImage", mallProductImage);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductimage/save")
    @RedisLock(value="mall:mallproductimage:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductImageEntity mallProductImage){
		mallProductImageService.insert(mallProductImage);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductimage/update")
    @RedisLock(value="mall:mallproductimage:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductImageEntity mallProductImage){
		mallProductImageService.updateById(mallProductImage);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductimage/delete")
    @RedisLock(value="mall:mallproductimage:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] picImgIds){
		mallProductImageService.deleteBatchIds(Arrays.asList(picImgIds));
        return ResponseMessage.ok();
    }

}
