package com.lebaoxun.modules.pay.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lebaoxun.modules.pay.entity.PayOrderEntity;
import com.lebaoxun.modules.pay.service.hystrix.PayOrderServiceHystrix;
import com.lebaoxun.commons.exception.ResponseMessage;

import java.util.Map;

/**
 * 支付订单
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-31 14:40:10
 */

@FeignClient(value="pay-service",fallback=PayOrderServiceHystrix.class)
public interface IPayOrderService {
	/**
     * 列表
     */
    @RequestMapping("/pay/payorder/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/pay/payorder/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id);

    /**
     * 保存
     */
    @RequestMapping("/pay/payorder/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody PayOrderEntity payOrder);

    /**
     * 修改
     */
    @RequestMapping("/pay/payorder/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody PayOrderEntity payOrder);

    /**
     * 删除
     */
    @RequestMapping("/pay/payorder/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids);
    
}

