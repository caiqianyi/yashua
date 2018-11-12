package com.lebaoxun.sms.service;

import java.util.List;

import com.lebaoxun.sms.core.SMSGateway;

public interface ISMSGatewayService {
	
	void set(SMSGateway gateway);
	
	SMSGateway find(String gatewayName);
	
	SMSGateway getCurrentGateway();
	
	void setGatewayModel(String model);
	
	void delete(String gatewayName);
	
	List<SMSGateway> list();
	
}