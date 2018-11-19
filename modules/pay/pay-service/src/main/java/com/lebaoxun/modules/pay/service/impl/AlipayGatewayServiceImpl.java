package com.lebaoxun.modules.pay.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.modules.pay.entity.PayOrderEntity;
import com.lebaoxun.modules.pay.pojo.AlipayConfig;
import com.lebaoxun.modules.pay.service.IAlipayConfigService;
import com.lebaoxun.modules.pay.service.IAlipayGatewayService;

@Service("alipayGatewayService")
public class AlipayGatewayServiceImpl implements IAlipayGatewayService{

	@Resource
	private IAlipayConfigService alipayConfigService;
	
	@Override
	public boolean refund(PayOrderEntity order) {
		// TODO Auto-generated method stub
		AlipayConfig config = alipayConfigService.getAlipayConfig(order.getGroup());
		AlipayClient alipayClient = new DefaultAlipayClient(
				"https://openapi.alipay.com/gateway.do", config.getAppid(),
				config.getPrivateKey(), "json", "UTF-8", config.getPublicKey(), "RSA2");
		AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
		String out_request_no = GenerateCode.getUUID();
		request.setBizContent("{" + "\"out_trade_no\":\""+order.getOutOrderNo()+"\","
				+ "\"out_request_no\":\""+out_request_no+"\"}");
		AlipayTradeFastpayRefundQueryResponse response;
		try {
			response = alipayClient
					.execute(request);
			if (response.isSuccess()) {
				return true;
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
