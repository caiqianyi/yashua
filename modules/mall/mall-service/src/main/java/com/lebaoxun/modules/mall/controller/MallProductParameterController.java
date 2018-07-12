package com.lebaoxun.modules.mall.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.mall.entity.MallProductParameterEntity;
import com.lebaoxun.modules.mall.service.MallProductParameterService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 商品参数表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@RestController
public class MallProductParameterController {
    @Autowired
    private MallProductParameterService mallProductParameterService;

    /**
     * 列表
     */
    @RequestMapping("/mall/mallproductparameter/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = mallProductParameterService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/mall/mallproductparameter/info/{productParameterId}")
    ResponseMessage info(@PathVariable("productParameterId") Long productParameterId){
		MallProductParameterEntity mallProductParameter = mallProductParameterService.selectById(productParameterId);
        return ResponseMessage.ok().put("mallProductParameter", mallProductParameter);
    }

    /**
     * 保存
     */
    @RequestMapping("/mall/mallproductparameter/save")
    @RedisLock(value="mall:mallproductparameter:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallProductParameterEntity mallProductParameter){
		mallProductParameterService.insert(mallProductParameter);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/mall/mallproductparameter/update")
    @RedisLock(value="mall:mallproductparameter:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallProductParameterEntity mallProductParameter){
		mallProductParameterService.updateById(mallProductParameter);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/mall/mallproductparameter/delete")
    @RedisLock(value="mall:mallproductparameter:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] productParameterIds){
		mallProductParameterService.deleteBatchIds(Arrays.asList(productParameterIds));
        return ResponseMessage.ok();
    }

}
