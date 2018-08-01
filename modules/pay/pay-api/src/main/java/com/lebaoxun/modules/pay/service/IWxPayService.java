package com.lebaoxun.modules.pay.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.pay.pojo.WxpayConfig;
import com.lebaoxun.modules.pay.service.hystrix.WxPayServiceHystrix;

@FeignClient(value="pay-service",fallback=WxPayServiceHystrix.class)
public interface IWxPayService {
	@RequestMapping(value = "/wxpay/config/list", method = RequestMethod.GET)
	List<WxpayConfig> configList();
	
	@RequestMapping(value = "/wxpay/config/info", method = RequestMethod.GET)
	ResponseMessage configInfo(@RequestParam("group")String group);
	
	@RequestMapping(value = "/wxpay/config/save", method = RequestMethod.POST)
	ResponseMessage configSave(@RequestBody WxpayConfig config);
	
	@RequestMapping(value = "/wxpay/config/delete", method = RequestMethod.POST)
	ResponseMessage configDelete(@RequestParam("group")String group);
	
	/**
	 * 微信公众号支付付款
	 * 
	 * @return JsonObject
	 * @throws Exception
	 */
	@RequestMapping(value="/wxpay/payment", method = RequestMethod.POST)
	ResponseMessage payment(@RequestParam("spbill_create_ip")String spbill_create_ip, @RequestParam("orderNo")String orderNo, 
			@RequestParam("descr")String descr, @RequestParam("totalFee")Integer totalFee, 
			@RequestParam("attach")String attach, @RequestParam("group")String group, 
			@RequestParam("openid")String openid, @RequestParam("userId") Long userId);
	
	/**
	 * 微信H5付款
	 * @param wapUrl
	 * @param wapName
	 * @param spbill_create_ip
	 * @param orderNo
	 * @param descr
	 * @param totalFee
	 * @param attach
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/wxpay/payment/h5", method = RequestMethod.POST)
	ResponseMessage h5Payment(@RequestParam("wapUrl") String wapUrl, 
			@RequestParam("wapName") String wapName, @RequestParam("spbill_create_ip") String spbill_create_ip, 
			@RequestParam("orderNo") String orderNo, @RequestParam("descr") String descr, 
			@RequestParam("totalFee") Integer totalFee, @RequestParam("attach") String attach, 
			@RequestParam("group") String group, @RequestParam("userId") Long userId);
	
	/**
	 * 原生扫码支付
	 * @param spbill_create_ip
	 * @param orderNo
	 * @param descr
	 * @param totalFee
	 * @param attach
	 * @param group
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/wxpay/payment/qrcode", method = RequestMethod.POST)
	ResponseMessage qrcodePayment(
			@RequestParam("spbill_create_ip") String spbill_create_ip, @RequestParam("orderNo") String orderNo, 
			@RequestParam("descr") String descr, @RequestParam("totalFee") Integer totalFee, 
			@RequestParam("attach") String attach, @RequestParam("group") String group, 
			@RequestParam("userId") Long userId) throws Exception;
	
	/**
	 * 微信APP支付付款
	 * 
	 * @return JsonObject
	 * @throws Exception
	 */
	@RequestMapping(value="/wxpay/payment/app", method = RequestMethod.POST)
	ResponseMessage appPayment(@RequestParam("spbill_create_ip")String spbill_create_ip, @RequestParam("orderNo")String orderNo, 
			@RequestParam("descr")String descr, @RequestParam("totalFee")Integer totalFee, 
			@RequestParam("attach")String attach, @RequestParam("group")String group,
			@RequestParam("userId") Long userId);
	
	@RequestMapping(value="/wxpay/query", method = RequestMethod.GET)
	ResponseMessage query(@RequestParam("out_trade_no")String out_trade_no, @RequestParam("account")String account,
			@RequestParam(value="send",required=false)String send);
}
