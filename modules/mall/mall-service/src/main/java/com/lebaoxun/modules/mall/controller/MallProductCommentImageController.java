package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductCommentImageEntity;
import com.lebaoxun.modules.mall.service.MallProductCommentImageService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 评价图片表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
@RestController
public class MallProductCommentImageController {
    @Autowired
    private MallProductCommentImageService mallProductCommentImageService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproductcommentimage/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallProductCommentImageService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductcommentimage/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
		MallProductCommentImageEntity mallProductCommentImage = mallProductCommentImageService.selectById(id);
        return ResponseMessage.ok().put("mallProductCommentImage", mallProductCommentImage);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductcommentimage/save")
    @RedisLock(value="mall:mallproductcommentimage:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductCommentImageEntity mallProductCommentImage){
		mallProductCommentImageService.insert(mallProductCommentImage);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductcommentimage/update")
    @RedisLock(value="mall:mallproductcommentimage:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductCommentImageEntity mallProductCommentImage){
		mallProductCommentImageService.updateById(mallProductCommentImage);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductcommentimage/delete")
    @RedisLock(value="mall:mallproductcommentimage:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids){
		mallProductCommentImageService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
