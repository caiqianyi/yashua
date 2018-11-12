package com.lebaoxun.sms.config;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.lebaoxun.commons.utils.MD5;
import com.lebaoxun.sms.core.RedisKeyConstant;
import com.lebaoxun.sms.core.SMSGateway;
import com.lebaoxun.sms.core.SMSGatewayClient;
import com.lebaoxun.soa.core.redis.IRedisCache;
import com.lebaoxun.soa.core.redis.IRedisHash;

@Configuration
public class SMSGatewayConfig {
	
	@Resource
	private IRedisHash redisHash;
	
	@Resource
	private IRedisCache redisCache;
	
	@Bean
	public SMSGateway initGateway(){
		
		//畅卓科技
		String passwd = MD5.encodeByMD5ForUpperCase("jzpw78xq1i");
		//畅卓科技
		String gatewayName = "czkj";
		SMSGateway gateway = new SMSGateway();
		gateway.setCharset("utf-8");
		gateway.setGatewayName(gatewayName);
		gateway.setJson(false);
		gateway.setMethod(RequestMethod.POST);
		gateway.setSignature("爱闪餐");
		gateway.setSuccessText("发送成功");
		gateway.setUrl("http://api.chanzor.com/send");
		gateway.setWeight(10);
		gateway.setRequestBody("account=98abec&password="+passwd+"&mobile=%s&content=%s");
		
		/*String gatewayName = "fhwj";
		SMSGateway gateway = new SMSGateway(FhwjSMSGatewayClient.class);*/
		
		if(!redisHash.hExists(RedisKeyConstant.HASH_SMS_GATEWAY_CONFIGS, gatewayName)){
			redisHash.hSet(RedisKeyConstant.HASH_SMS_GATEWAY_CONFIGS, gatewayName, JSONObject.toJSON(gateway).toString());
		}
		if(!redisHash.hExists(RedisKeyConstant.HASH_SMS_VFCODE_TEMPLATE_IDS, "f2d5483a0a1544b08e60fed734448392")){
			redisHash.hSet(RedisKeyConstant.HASH_SMS_VFCODE_TEMPLATE_IDS,"f2d5483a0a1544b08e60fed734448392","#vfcode#（动态验证码），10分钟内有效，请尽快操作，进行验证！#signature#");
		}
		if(!redisHash.hExists(RedisKeyConstant.HASH_SMS_SECRET_CSTID, "10086")){
			redisHash.hSet(RedisKeyConstant.HASH_SMS_SECRET_CSTID,"10086","f833605b0361410896d179d84bbe3402");
		}
		String sstac_10086 = String.format(RedisKeyConstant.HASH_SMS_SEND_TIME_ASTRICT_CSTID, "10086");
		if(!redisCache.exists(sstac_10086)){
			/*redisHash.hSet(sstac_10086,(5 * 60)+"",5);
			redisHash.hSet(sstac_10086,(10 * 60)+"",8);*/
			redisHash.hSet(sstac_10086,(60 * 60)+"",10);
			redisHash.hSet(sstac_10086,"yyyyMMdd",20);
		}
		
		if(!redisCache.exists(RedisKeyConstant.SMS_GATEWAY_USE_CURRENT)){
			redisCache.set(RedisKeyConstant.SMS_GATEWAY_USE_CURRENT, "RANDOM");
		}
		return null;
	}
	
	public static void main(String[] args) {
		/*String passwd = MD5.encodeByMD5ForUpperCase("jzpw78xq1i");
		System.out.println(passwd);
		//畅卓科技
		String gatewayName = "fhwj";
		SMSGateway fhwj = new SMSGateway();
		fhwj.setCharset("utf-8");
		fhwj.setGatewayName(gatewayName);
		fhwj.setJson(false);
		fhwj.setMethod(RequestMethod.POST);
		fhwj.setSignature("畅卓科技");
		fhwj.setSuccessText("发送成功");
		fhwj.setUrl("http://api.chanzor.com/send");
		fhwj.setWeight(10);
		fhwj.setRequestBody("account=98abec&password="+passwd+"&mobile=%s&content=%s");
	    
		SMSGatewayClient client = new SMSGatewayClient();
		client.send(fhwj, "15010602819", "12331（动态验证码），10分钟内有效，请尽快操作，进行验证！【畅卓科技】");
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));*/
		
		
		String passwd = MD5.md5("ohv2ycjy");
		String seed = DateFormatUtils.format(new Date(), "yyyyMMddHHmmSS");
		String key = MD5.md5(passwd+seed);
		//烽火万家短信网关
		String gatewayName = "fhwj";
		SMSGateway fhwj = new SMSGateway();
		fhwj.setCharset("GBK");
		fhwj.setGatewayName(gatewayName);
		fhwj.setJson(false);
		fhwj.setMethod(RequestMethod.GET);
		fhwj.setSignature("魔牙科技");
		fhwj.setSuccessText("success");
		fhwj.setUrl("http://210.51.191.35:8080/eums/sms/send.do?name=xxznhy&seed="+seed+"&key="+key+"&dest=%s&content=%s");
		fhwj.setWeight(10);
	    
		SMSGatewayClient client = new SMSGatewayClient();
		client.send(fhwj, "15010602819", "【魔牙科技】12331（动态验证码），10分钟内有效，请尽快操作，进行验证！");
	}
}
