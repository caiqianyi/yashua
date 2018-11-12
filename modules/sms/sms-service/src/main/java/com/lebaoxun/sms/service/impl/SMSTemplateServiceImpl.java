package com.lebaoxun.sms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lebaoxun.sms.core.RedisKeyConstant;
import com.lebaoxun.sms.service.ISMSTemplateService;
import com.lebaoxun.soa.core.redis.IRedisHash;

@Service
public class SMSTemplateServiceImpl implements ISMSTemplateService {

	@Resource
	private IRedisHash redisHash;
	
	@Override
	public void saveOrUpdate(String templateId, String content) {
		// TODO Auto-generated method stub
		redisHash.hSet(RedisKeyConstant.HASH_SMS_VFCODE_TEMPLATE_IDS,templateId,content);
	}

	@Override
	public void remove(String templateId) {
		// TODO Auto-generated method stub
		redisHash.hDel(RedisKeyConstant.HASH_SMS_VFCODE_TEMPLATE_IDS,templateId);
	}

	@Override
	public List<Map<String, String>> list() {
		// TODO Auto-generated method stub
		Map<String,Object> map = redisHash.hGetAll(RedisKeyConstant.HASH_SMS_VFCODE_TEMPLATE_IDS);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for(String key : map.keySet()){
			Map<String, String> e = new HashMap<String,String>();
			e.put("templateId", key);
			e.put("content", (String)map.get(key));
			list.add(e);
		}
		return list;
	}
	
	@Override
	public String find(String templateId) {
		// TODO Auto-generated method stub
		String content = (String) redisHash.hGet(RedisKeyConstant.HASH_SMS_VFCODE_TEMPLATE_IDS, templateId);
		return content;
	}

}
