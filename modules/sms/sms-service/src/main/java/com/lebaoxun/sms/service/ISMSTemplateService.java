package com.lebaoxun.sms.service;

import java.util.List;
import java.util.Map;

public interface ISMSTemplateService {
	
	void saveOrUpdate(String templateId, String content);
	
	void remove(String templateId);
	
	String find(String templateId);
	
	List<Map<String,String>> list();
	
}