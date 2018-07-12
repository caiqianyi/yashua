package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.service.MallCartService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 购物车表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallCartController {
    @Autowired
    private MallCartService mallCartService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallcart/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallCartService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallcart/info/{cartId}")
    ResponseMessage info(@PathVariable("cartId") Long cartId){
		MallCartEntity mallCart = mallCartService.selectById(cartId);
        return ResponseMessage.ok().put("mallCart", mallCart);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallcart/save")
    @RedisLock(value="mall:mallcart:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallCartEntity mallCart){
		mallCartService.insert(mallCart);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallcart/update")
    @RedisLock(value="mall:mallcart:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallCartEntity mallCart){
		mallCartService.updateById(mallCart);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallcart/delete")
    @RedisLock(value="mall:mallcart:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] cartIds){
		mallCartService.deleteBatchIds(Arrays.asList(cartIds));
        return ResponseMessage.ok();
    }

}
