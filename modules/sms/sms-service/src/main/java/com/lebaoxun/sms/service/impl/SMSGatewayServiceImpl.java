package com.lebaoxun.sms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lebaoxun.commons.utils.Assert;
import com.lebaoxun.sms.core.RedisKeyConstant;
import com.lebaoxun.sms.core.SMSGateway;
import com.lebaoxun.sms.service.ISMSGatewayService;
import com.lebaoxun.soa.core.redis.IRedisCache;
import com.lebaoxun.soa.core.redis.IRedisHash;

@Service
public class SMSGatewayServiceImpl implements ISMSGatewayService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private IRedisHash redisHash;
	
	@Resource
	private IRedisCache redisCache;
	
	@Override
	public void set(SMSGateway gateway) {
		// TODO Auto-generated method stub
		redisHash.hSet(RedisKeyConstant.HASH_SMS_GATEWAY_CONFIGS, gateway.getGatewayName(), JSONObject.toJSON(gateway).toString());
	}

	@Override
	public SMSGateway find(String gatewayName) {
		String config = (String)redisHash.hGet(RedisKeyConstant.HASH_SMS_GATEWAY_CONFIGS, gatewayName);
		return JSON.parseObject(config,SMSGateway.class);
	}

	@Override
	public SMSGateway getCurrentGateway() {
		String code = (String) redisCache.get(RedisKeyConstant.SMS_GATEWAY_USE_CURRENT);
		Assert.notEmpty(code, "500" , null);
		
		Map<String,Object> smsGateways = redisHash.hGetAll(RedisKeyConstant.HASH_SMS_GATEWAY_CONFIGS);
		
		Assert.notNull(smsGateways, "500");
		Assert.notEmpty(smsGateways, "500", "没有可用网关");
		
		if("RANDOM".equals(code)){
			int u = RandomUtils.nextInt(smsGateways.size());
			code = (String) smsGateways.keySet().toArray()[u];
		}
		if(!smsGateways.containsKey(code)){//当前短信通道不存在，则切换成随机模式
			redisCache.set(RedisKeyConstant.SMS_GATEWAY_USE_CURRENT,"RANDOM");
			return getCurrentGateway();
		}
		String config = (String) smsGateways.get(code);
		logger.debug("useGateway={},config={},smsGateways={}",code,config,JSONObject.toJSON(smsGateways));
		SMSGateway sgc = JSON.parseObject(config,SMSGateway.class);
		sgc.setCode(code);
		return sgc;
	}

	@Override
	public void setGatewayModel(String model) {
		// TODO Auto-generated method stub
		redisCache.set(RedisKeyConstant.SMS_GATEWAY_USE_CURRENT, model);
	}

	@Override
	public void delete(String gatewayName) {
		// TODO Auto-generated method stub
		redisHash.hDel(RedisKeyConstant.HASH_SMS_GATEWAY_CONFIGS, gatewayName);
	}

	@Override
	public List<SMSGateway> list() {
		// TODO Auto-generated method stub
		List<SMSGateway> list = new ArrayList<SMSGateway>();
		Map<String,Object> smsGateways = redisHash.hGetAll(RedisKeyConstant.HASH_SMS_GATEWAY_CONFIGS);
		for(String code : smsGateways.keySet()){
			SMSGateway sgc = JSON.parseObject((String)smsGateways.get(code),SMSGateway.class);
			list.add(sgc);
		}
		return list;
	}

}
