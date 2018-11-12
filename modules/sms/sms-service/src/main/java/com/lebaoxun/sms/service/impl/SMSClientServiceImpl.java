package com.lebaoxun.sms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lebaoxun.commons.utils.DateUtil;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.commons.utils.StringUtils;
import com.lebaoxun.sms.core.RedisKeyConstant;
import com.lebaoxun.sms.service.ISMSClientService;
import com.lebaoxun.sms.utils.MapKeyComparator;
import com.lebaoxun.sms.vo.SMSAstrictVo;
import com.lebaoxun.sms.vo.SMSClientVo;
import com.lebaoxun.soa.core.redis.IRedisHash;

@Service("smsClientService")
public class SMSClientServiceImpl implements ISMSClientService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private IRedisHash redisHash;
	
	@Override
	public void saveOrUpdate(SMSClientVo clientVo) {
		// TODO Auto-generated method stub
		redisHash.hSet(RedisKeyConstant.HASH_SMS_SECRET_CSTID,clientVo.getAppid(),clientVo.getSecret());
	}

	@Override
	public void delete(String appid) {
		// TODO Auto-generated method stub
		redisHash.hDel(RedisKeyConstant.HASH_SMS_SECRET_CSTID, appid);
	}

	@Override
	public SMSClientVo find(String appid) {
		// TODO Auto-generated method stub
		return new SMSClientVo(appid,redisHash.hGet(RedisKeyConstant.HASH_SMS_SECRET_CSTID,appid).toString());
	}

	@Override
	public List<SMSClientVo> findAll() {
		// TODO Auto-generated method stub
		List<SMSClientVo> list = new ArrayList<SMSClientVo>();
		Map<String,Object> map = redisHash.hGetAll(RedisKeyConstant.HASH_SMS_SECRET_CSTID);
		for(String appid: map.keySet()){
			list.add(new SMSClientVo(appid,map.get(appid).toString()));
		}
		return list;
	}

	@Override
	public void setAstrict(String appid, String format, Integer astrict) {
		// TODO Auto-generated method stub
		String appidk = String.format(RedisKeyConstant.HASH_SMS_SEND_TIME_ASTRICT_CSTID, appid);
		redisHash.hSet(appidk, format, astrict);
	}

	@Override
	public void deleteAstrict(String appid, String format) {
		// TODO Auto-generated method stub
		String key = String.format(RedisKeyConstant.HASH_SMS_SEND_TIME_ASTRICT_CSTID, appid);
		redisHash.hDel(key, format);
	}

	@Override
	public List<SMSAstrictVo> findAstrict(String appid) {
		// TODO Auto-generated method stub
		String key = String.format(RedisKeyConstant.HASH_SMS_SEND_TIME_ASTRICT_CSTID, appid);
		Map<String,Object> map = redisHash.hGetAll(key);
		List<SMSAstrictVo> list = new ArrayList<SMSAstrictVo>();
		for(String format: map.keySet()){
			list.add(new SMSAstrictVo(appid, format,(Integer)map.get(format)));
		}
		return list;
	}
	
	@Override
	public SMSAstrictVo findAstrict(String appid, String format) {
		// TODO Auto-generated method stub
		String key = String.format(RedisKeyConstant.HASH_SMS_SEND_TIME_ASTRICT_CSTID, appid);
		return new SMSAstrictVo(appid, format,(Integer)redisHash.hGet(key, format));
	}

	@Override
	public boolean isInBlackList(String appid, String mobile) {
		// TODO Auto-generated method stub
		return redisHash.hExists(RedisKeyConstant.HASH_SMS_BLACKLIST_MOBILES+":"+appid, mobile);
	}

	@Override
	public boolean isFreeze(String appid, String mobile) {
		// TODO Auto-generated method stub
		String key = String.format(RedisKeyConstant.SMS_FREEZE_LIST_MOBILES, appid);
		if(redisHash.hExists(key,mobile)){
			JSONObject json = JSONObject.parseObject((String)redisHash.hGet(key,mobile));
			boolean isExpire = new Date(json.getLong("expire")).before(new Date());
			if(isExpire){//判断是否过期
				redisHash.hDel(key,mobile);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 记录发送次数，发送场景，比对限制，拉入黑名单
	 * @param mobile
	 * @param cst_id
	 */
	@Override
	public void addSendLog(String appid, String mobile){
		Map<String,Object>  smsAstrict = redisHash.hGetAll(String.format(RedisKeyConstant.HASH_SMS_SEND_TIME_ASTRICT_CSTID, appid));//短信限制配置
  		if(smsAstrict != null){
  			for(String key : smsAstrict.keySet()){
  				Integer max = (Integer) smsAstrict.get(key),count = -1;
  				String reason = null;
  				if(StringUtils.isNumeric(key)){
  	  				Long time = Long.parseLong(key);
  	  				String astrictCache = String.format(RedisKeyConstant.SMS_SEND_COUNT_SECOND_MOBILE, appid, time);
  	  				JSONObject json = null;
  	  				boolean isExpire = true;
  	  				if(redisHash.hExists(astrictCache,mobile)){
  	  					json = JSONObject.parseObject((String)redisHash.hGet(astrictCache, mobile));
  	  					json.put("time", json.getInteger("time")+1);
	  	  				if(!new Date(json.getLong("expire")).before(new Date())){//判断是否过期
		  					isExpire = false;
		  				}
  	  				}
  	  				if(isExpire){
  	  					json = new JSONObject();
  	  					json.put("expire", new Date().getTime() + time * 1000);
  	  					json.put("time", 1);
	  				}
  	  				count = json.getInteger("time");
  	  				redisHash.hSet(astrictCache, mobile, json.toJSONString());
  	  				reason = astrictCache;
  				}else{
  					String t = null;
  					Integer expire = null;
  	  				if("yyyyMMdd".equalsIgnoreCase(key) ||
  	  						"yyyyMM".equalsIgnoreCase(key) ||
  	  						"yyyy".equalsIgnoreCase(key)){
  	  					t = DateUtil.formatDatetime(new Date(),key);
  	  					if("yyyyMMdd".equalsIgnoreCase(key)){
  	  						expire = 24 * 60 * 60;
  	  					}
	  	  				if("yyyyMM".equalsIgnoreCase(key)){
	  						expire = 30 * 24 * 60 * 60;
	  					}
		  	  			if("yyyy".equalsIgnoreCase(key)){
							expire = 365 *24 * 60 * 60;
						}
  	  				}
  	  				if(t == null){
  	  					continue;
  	  				}
  	  				String countDate = String.format(RedisKeyConstant.HASH_SMS_SEND_COUNT_DATE, appid, t);
  	  				JSONObject json = null;
  	  				boolean isExpire = true;
  	  				if(redisHash.hExists(countDate, mobile)){
  	  					json = JSONObject.parseObject((String)redisHash.hGet(countDate, mobile));
	  					json.put("time", json.getInteger("time")+1);
		  				if(!new Date(json.getLong("expire")).before(new Date())){//判断是否过期
		  					isExpire = false;
		  				}
  	  				}
  	  				if(isExpire){
  	  					json = new JSONObject();
	  	  				json.put("expire", new Date().getTime() + expire * 1000);
	  					json.put("time", 1);
  	  				}
  	  				count = json.getInteger("time");
  	  				reason = countDate;
  	  				redisHash.hSet(countDate, mobile, json.toJSONString());
  				}
  				logger.debug("key={},max={},count={},reason={}",key,max,count,reason);
  				if(count >= max){//超过最大值立即拉黑
  					freeze(appid,mobile, reason);
  				}
  			}
  		}
	}
	
	@Override
	public void freeze(String appid,String mobile,String reason){
		String freezeReason = "";
		String freezeMobile = String.format(RedisKeyConstant.HASH_SMS_FREEZE_RECORDS_MOBILES, appid, mobile);
		Map<String,Object> freezeRecord = redisHash.hGetAll(freezeMobile);
		Long time = new Date().getTime();
		String key = String.format(RedisKeyConstant.SMS_FREEZE_LIST_MOBILES,appid);
		if(freezeRecord == null || freezeRecord.isEmpty()){//第一次冻结
			Long freezeTime = 60 * 60l;
			freezeReason = reason+"|freezeTime:"+freezeTime;
			
			JSONObject json = null;
			boolean isExpire = true;
			if(redisHash.hExists(key, mobile)){
				json = JSONObject.parseObject((String)redisHash.hGet(key, mobile));
				if(!new Date(json.getLong("expire")).before(new Date())){//判断是否过期
					isExpire = false;
				}
			}
			if(isExpire){
				json = new JSONObject();
  				json.put("expire", new Date().getTime() + freezeTime * 1000);
			}
			json.put("reason", reason);
			redisHash.hSet(key, mobile, json.toJSONString());//冻结一小时
		} else {
			Map<String, Object> sortMap = new TreeMap<String, Object>(
	                new MapKeyComparator(true));
	        sortMap.putAll(freezeRecord);
	        
	        String fzr = sortMap.values().iterator().next().toString();
	        Long freezeTime = 60 * 60l * 5;
	        try{
	        	freezeTime = Long.parseLong(fzr.split("freezeTime:")[1]) * 5;
	        }catch(Exception e){
	        	logger.error("e:{}",e);
	        }
	        logger.debug("mobile={},freezeTime={},fzr={}",mobile,freezeTime,fzr);
	        freezeReason = reason+"|freezeTime:"+freezeTime;
	        
	        JSONObject json = null;
			boolean isExpire = true;
			if(redisHash.hExists(key, mobile)){
				json = JSONObject.parseObject((String)redisHash.hGet(key, mobile));
				if(!new Date(json.getLong("expire")).before(new Date())){//判断是否过期
					isExpire = false;
				}
			}
			if(isExpire){
				json = new JSONObject();
  				json.put("expire", new Date().getTime() + freezeTime * 1000);
			}
			json.put("reason", reason);
			redisHash.hSet(key, mobile, json.toJSONString());//冻结上一次时间*5
		}
		
		redisHash.hSet(freezeMobile, time+"", freezeReason);
		
		if(freezeRecord != null && freezeRecord.size() > 4){//冻结次数超过5次则永久拉黑
			this.addBlackList(appid, freezeMobile);
		}
	}

	@Override
	public void addBlackList(String appid, String mobile) {
		// TODO Auto-generated method stub
		redisHash.hSet(String.format(RedisKeyConstant.HASH_SMS_BLACKLIST_MOBILES, appid), mobile, "time="+new Date().getTime());
	}
	
	@Override
	public boolean checkVfCode(String appid, String mobile, String vfCode) {
		// TODO Auto-generated method stub
		String key = String.format(RedisKeyConstant.SMS_SEND_VFCODE_MOBILE, appid);
		if(redisHash.hExists(key, mobile)){
			JSONObject json = JSONObject.parseObject((String)redisHash.hGet(key, mobile));
			if(!new Date(json.getLong("expire")).before(new Date())){//判断是否过期
				String vfcode = json.getString("vfcode");
				return vfcode == null || !vfcode.equalsIgnoreCase(vfCode) ? false : true;
			}
		}
		return false;
	}
	
	@Override
	public String refreshVfCode(String appid, String mobile) {
		String vfcode = null;
		String key = String.format(RedisKeyConstant.SMS_SEND_VFCODE_MOBILE, appid);
		long expire = 10 * 60l;
		JSONObject json = null;
		boolean isExpire = true;
		if(redisHash.hExists(key, mobile)){
			json = JSONObject.parseObject((String)redisHash.hGet(key, mobile));
			vfcode = json.getString("vfcode");
			if(!new Date(json.getLong("expire")).before(new Date())){//判断是否过期
				isExpire = false;
			}
		}
		if(isExpire){
			vfcode = GenerateCode.gen(5)+"";
			json = new JSONObject();
			json.put("expire", new Date().getTime() + expire * 1000);
			json.put("vfcode", vfcode);
		}
		logger.info("refreshVfCode vfcode={}",vfcode);
		redisHash.hSet(key, mobile, json.toJSONString());
		return vfcode;
	}
}
