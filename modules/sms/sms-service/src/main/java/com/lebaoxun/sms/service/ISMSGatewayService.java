package com.lebaoxun.sms.service;

import java.util.Map;

import com.lebaoxun.sms.core.SMSGateway;

public interface ISMSGatewayService {
	
	void set(String name,SMSGateway gateway);
	
	void delete(String name);
	
	Map<String,SMSGateway> list();
	
}