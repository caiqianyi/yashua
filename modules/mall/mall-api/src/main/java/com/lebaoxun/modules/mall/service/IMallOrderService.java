package com.lebaoxun.modules.mall.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.entity.MallOrderEntity;
import com.lebaoxun.modules.mall.service.hystrix.MallOrderServiceHystrix;

/**
 * 订单表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */

@FeignClient(value="mall-service",fallback=MallOrderServiceHystrix.class)
public interface IMallOrderService {
	
	@RequestMapping("/mall/mallorder/sendOut")
	ResponseMessage sendOut(@RequestParam("adminId") Long adminId,
			@RequestParam("orderId") Long orderId,
			@RequestParam("postid") String postid);
	
	@RequestMapping("/mall/mallorder/create")
    ResponseMessage create(@RequestParam("userId")Long userId,
    		@RequestParam("maxOrderNum") Integer maxOrderNum,
    		@RequestBody List<MallCartEntity> products);
	
	@RequestMapping("/mall/mallorder/deleteByUser")
    ResponseMessage deleteByUser(@RequestParam("userId")Long userId,
    		@RequestParam("orderNo")String orderNo);
	
	@RequestMapping("/mall/mallorder/selectOrderByOrderNo")
    MallOrderEntity selectOrderByOrderNo(@RequestParam("userId")Long userId,
    		@RequestParam("orderNo")String orderNo,
    		@RequestParam("status")Integer status);
	
	@RequestMapping("/mall/mallorder/selectOrderProductByOrderProductId")
    ResponseMessage selectOrderProductByOrderProductId(@RequestParam("userId")Long userId,
    		@RequestParam("orderProductId")Long orderProductId);
	
	@RequestMapping("/mall/mallorder/confirmOrder")
    ResponseMessage confirmOrder(@RequestParam("userId") Long userId,
    		@RequestParam("orderNo") String orderNo, 
    		@RequestParam("invoiceType") Integer invoiceType,
    		@RequestParam("invoiceTitle") String invoiceTitle, 
    		@RequestParam("address") String address,
    		@RequestParam("consignee") String consignee,
    		@RequestParam("mobile") String mobile,
    		@RequestParam("wxopenid") String wxopenid,
			@RequestParam("spbill_create_ip") String spbill_create_ip,
			@RequestParam(name="fuid",required=false) Long fuid);
	
	@RequestMapping("/mall/mallorder/scoreExchange")
	ResponseMessage scoreExchange(@RequestParam("userId") Long userId,
			@RequestParam("orderNo") String orderNo,
			@RequestParam("invoiceType") Integer invoiceType,
			@RequestParam("invoiceTitle") String invoiceTitle,
			@RequestParam("address") String address,
			@RequestParam("consignee") String consignee,
			@RequestParam("mobile") String mobile);
	
	@RequestMapping("/mall/mallorder/mylist")
    ResponseMessage mylist(@RequestParam("userId") Long userId, 
    		@RequestParam(value="status",required=false) Integer status, 
    		@RequestParam("size") Integer size, 
    		@RequestParam("offset") Integer offset);

	/**
     * 列表
     */
    @RequestMapping("/mall/mallorder/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);


    /**
     * 信息
     */
    @RequestMapping("/mall/mallorder/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id);

    /**
     * 保存
     */
    @RequestMapping("/mall/mallorder/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody MallOrderEntity mallOrder);

    /**
     * 修改
     */
    @RequestMapping("/mall/mallorder/update")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody MallOrderEntity mallOrder);

    /**
     * 删除
     */
    @RequestMapping("/mall/mallorder/delete")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids);
    
    @RequestMapping("/mall/mallorder/kuaid100Query")
	ResponseMessage kuaid100Query(@RequestParam("postid")String postid);
    
    @RequestMapping("/mall/mallorder/confirmReceive")
	ResponseMessage confirmReceive(@RequestParam("userId") Long userId,
			@RequestParam("orderNo") String orderNo);
}

