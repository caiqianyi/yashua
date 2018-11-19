package com.lebaoxun.modules.pay.service;

import com.lebaoxun.modules.pay.entity.PayOrderEntity;

public interface IWxpayGatewayService {
	
	boolean refund(PayOrderEntity order,Integer refundFee,String refundDesc);
}
