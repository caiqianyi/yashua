package com.lebaoxun.modules.pay.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.pay.pojo.AlipayConfig;
import com.lebaoxun.modules.pay.service.hystrix.AlipayServiceHystrix;

@FeignClient(value="pay-service",fallback=AlipayServiceHystrix.class)
public interface IAlipayService {

	@RequestMapping(value = "/alipay/config/list", method = RequestMethod.GET)
	List<AlipayConfig> configList();
	
	@RequestMapping(value = "/alipay/config/info", method = RequestMethod.GET)
	ResponseMessage configInfo(@RequestParam("account")String account);
	
	@RequestMapping(value = "/alipay/config/save", method = RequestMethod.POST)
	ResponseMessage configSave(@RequestBody AlipayConfig config);
	
	@RequestMapping(value = "/alipay/config/delete", method = RequestMethod.POST)
	ResponseMessage configDelete(@RequestParam("account")String account);
	
	@RequestMapping(value = "/alipay/payment", method = RequestMethod.POST)
	ResponseMessage payment(@RequestParam("group")String group,
			@RequestParam("outTradeNo")String outTradeNo,
			@RequestParam("subject")String subject,
			@RequestParam("totalAmount")String totalAmount,
			@RequestParam(value="body",required=false)String body,
			@RequestParam(value="spbill_create_ip",required=false)String spbill_create_ip,
			@RequestParam(value="userId") Long userId,
			@RequestParam(value="rechargeFee",required=false)BigDecimal rechargeFee);
	
	@RequestMapping(value = "/alipay/qr_code/payment", method = RequestMethod.POST)
	ResponseMessage qrCodePayment(
			@RequestParam("group") String group,
			@RequestParam("outTradeNo") String outTradeNo,
			@RequestParam("subject") String subject,
			@RequestParam("totalAmount") String totalAmount,
			@RequestParam(value = "rechargeFee", required = false) BigDecimal rechargeFee,
			@RequestParam(value = "body", required = false) String body,
			@RequestParam(value = "spbill_create_ip", required = false) String spbill_create_ip,
			@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "scene", required = false) String scene);

	@RequestMapping(value = "/alipay/notify")
	String notify(@RequestParam Map<String, String> params);
	
	@RequestMapping(value="/alipay/refund")
	ResponseMessage refund(@RequestParam("orderNo")String orderNo);
}
