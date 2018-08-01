package com.lebaoxun.modules.pay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.modules.pay.pojo.WxpayConfig;
import com.lebaoxun.modules.pay.service.IWxpayConfigService;
import com.lebaoxun.soa.core.redis.IRedisCache;

@Service
public class WxpayConfigServiceImpl implements IWxpayConfigService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private IRedisCache redisCache;
	
	private static final String CACHEKEY = "wxpay:config:";
	
	@Override
	public List<WxpayConfig> getWxpayConfig() {
		// TODO Auto-generated method stub
		List<WxpayConfig> list = new ArrayList<WxpayConfig>();
		String key = CACHEKEY + "*";
		Set<String> sets = redisCache.searchKey(key);
		if(sets != null && !sets.isEmpty()){
			for(String account : sets){
				list.add((WxpayConfig) redisCache.get(account));
			}
		}
		return list;
	}

	@Override
	public WxpayConfig getWxpayConfig(String group) {
		// TODO Auto-generated method stub
		String key = CACHEKEY + group;
		if(!redisCache.exists(key)){
			logger.error("wxpay group '{}' config no exist!",group);
			throw new I18nMessageException("80001","支付账号不存在");
		}
		return (WxpayConfig) redisCache.get(key);
	}

	@Override
	public WxpayConfig getWxpayConfigByMchid(String mchid) {
		// TODO Auto-generated method stub
		String key = CACHEKEY + "*";
		Set<String> sets = redisCache.searchKey(key);
		if(sets != null && !sets.isEmpty()){
			for(String group : sets){
				WxpayConfig config = (WxpayConfig) redisCache.get(group);
				if(config.getMchid().equals(mchid)){
					return config;
				}
			}
		}
		logger.error("wxpay mchid '{}' config no exist!",mchid);
		throw new I18nMessageException("80001","支付账号不存在");
	}

	@Override
	public WxpayConfig setWxpayConfigByAppid(WxpayConfig config) {
		// TODO Auto-generated method stub
		String key = CACHEKEY + config.getGroup();
		config.setCreateTime(new Date());
		redisCache.set(key, config);
		return config;
	}

	@Override
	public boolean deleteWxpayConfig(String group) {
		// TODO Auto-generated method stub
		String key = CACHEKEY + group;
		redisCache.del(key);
		return !redisCache.exists(key);
	}

}
