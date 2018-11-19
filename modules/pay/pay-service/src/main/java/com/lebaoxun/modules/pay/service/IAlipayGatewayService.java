package com.lebaoxun.modules.pay.service;

import com.lebaoxun.modules.pay.entity.PayOrderEntity;

public interface IAlipayGatewayService {
	
	boolean refund(PayOrderEntity order);
}
