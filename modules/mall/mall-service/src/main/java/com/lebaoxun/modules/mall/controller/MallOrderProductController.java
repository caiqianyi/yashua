package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallOrderProductEntity;
import com.lebaoxun.modules.mall.service.MallOrderProductService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 订单明细表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
@RestController
public class MallOrderProductController {
    @Autowired
    private MallOrderProductService mallOrderProductService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallorderproduct/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallOrderProductService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallorderproduct/info/{orderProductId}")
    ResponseMessage info(@PathVariable("orderProductId") Long orderProductId){
		MallOrderProductEntity mallOrderProduct = mallOrderProductService.selectById(orderProductId);
        return ResponseMessage.ok().put("mallOrderProduct", mallOrderProduct);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallorderproduct/save")
    @RedisLock(value="mall:mallorderproduct:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallOrderProductEntity mallOrderProduct){
		mallOrderProductService.insert(mallOrderProduct);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallorderproduct/update")
    @RedisLock(value="mall:mallorderproduct:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallOrderProductEntity mallOrderProduct){
		mallOrderProductService.updateById(mallOrderProduct);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallorderproduct/delete")
    @RedisLock(value="mall:mallorderproduct:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] orderProductIds){
		mallOrderProductService.deleteBatchIds(Arrays.asList(orderProductIds));
        return ResponseMessage.ok();
    }

}
