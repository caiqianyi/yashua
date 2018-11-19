package com.lebaoxun.modules.pay.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.pay.entity.PayOrderEntity;
import com.lebaoxun.modules.pay.service.IPayOrderService;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 支付订单
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-09-03 15:50:37
 */
@RestController
public class PayOrderController {
    @Autowired
    private IPayOrderService payOrderService;

    /**
     * 列表
     */
    @RequestMapping("/pay/payorder/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = payOrderService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/pay/payorder/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
		PayOrderEntity payOrder = payOrderService.selectById(id);
        return ResponseMessage.ok().put("payOrder", payOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/pay/payorder/save")
    @RedisLock(value="pay:payorder:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody PayOrderEntity payOrder){
		payOrderService.insert(payOrder);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/pay/payorder/update")
    @RedisLock(value="pay:payorder:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody PayOrderEntity payOrder){
		payOrderService.updateById(payOrder);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/pay/payorder/delete")
    @RedisLock(value="pay:payorder:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids){
		payOrderService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
