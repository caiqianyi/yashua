package com.lebaoxun.sms.service;

import java.util.List;
import java.util.Map;

public interface ISMSTemplateService {
	
	void add(String temapleId, String content);
	
	void remove(String templateId);
	
	List<Map<String,String>> list();
	
}