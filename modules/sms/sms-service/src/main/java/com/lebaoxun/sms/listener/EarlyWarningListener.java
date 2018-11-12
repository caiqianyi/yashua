package com.lebaoxun.sms.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lebaoxun.sms.core.RedisKeyConstant;
import com.lebaoxun.sms.core.SMSGatewayClient;
import com.lebaoxun.soa.amqp.core.sender.IRabbitmqSender;
import com.lebaoxun.soa.core.redis.IRedisHash;

/**
 * 短信预警
 * @author caiqianyi
 *
 */
@Component
@RabbitListener(queues = "sms.early.warn.queue")
public class EarlyWarningListener {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="smsGatewayClient")
	private SMSGatewayClient smsGatewayClient;
	
	@Resource
	private IRedisHash redisHash;
	
	private long period = 5 * 60 * 1000l;
	
	@Bean
    public Queue queueEarlyWarning() {
        return new Queue("sms.early.warn.queue",true);
    }

    @Bean
    Binding bindingDirectExchangeEarlyWarning(Queue queueEarlyWarning, DirectExchange directExchange) {
        return BindingBuilder.bind(queueEarlyWarning).to(directExchange).with("sms.early.warn.queue");
    }
	
	@RabbitHandler
    public void receive(Object data) throws MessagingException {
		Message m = (Message) data;
		String body = new String(m.getBody());
		logger.debug("body={}",body);
		String text = body.replace("\\\"", "\"");
		JSONObject message = JSONObject.parseObject(text);
		try {
			String mobile = message.getString("mobile"),
					content = message.getString("content"),
					  m_id = message.getString("m_id"),
						m_type = message.getString("m_type");
			String k = String.format(RedisKeyConstant.HASH_SMS_EARLY_WARNING, m_type);
			boolean isSend = false;
			Date now = new Date();
			JSONObject pu = null;
			if(!redisHash.hExists(k, m_id)){ 
				pu = new JSONObject();
				pu.put("mobile", mobile);
				pu.put("count", 0);
				pu.put("lastsend", now.getTime());
				pu.put("timestamp", now.getTime());
				isSend = true;
				redisHash.hSet(k, m_id, pu.toJSONString());
			}else{
				String mbody = (String) redisHash.hGet(k, m_id);
				logger.debug("mbody={}",mbody);
				pu = JSONObject.parseObject(mbody);
				int count = pu.getInteger("count");
				Long timestamp = pu.getLongValue("timestamp"),
						lastsend = pu.getLongValue("lastsend");
				long y = (now.getTime() - timestamp)/ period;
				logger.debug("y={}",y);
				if(y > 6){//重置
					pu = new JSONObject();
					pu.put("mobile", mobile);
					pu.put("count", 0);
					pu.put("lastsend", now.getTime());
					pu.put("timestamp", now.getTime());
					isSend = true;
				}else{//连续发送累计
					long decayTime = period;
					for(int i=0;i<count;i++){
						decayTime *= 2; 
					}
					long xc = (now.getTime() - lastsend)/1000;
					logger.debug("decayTime={},xc={}",decayTime/1000,xc);
					if(xc >= decayTime/1000){
						pu.put("lastsend", now.getTime());
						++count;
						isSend = true;
					}
					pu.put("mobile", mobile);
					pu.put("count", count);
					pu.put("timestamp", now.getTime());
				}
			}
			redisHash.hSet(k, m_id, pu.toJSONString());
			logger.debug("isSend={}",isSend);
			if(isSend){
				smsGatewayClient.doSend("f833605b0361410896d179d84bbe3402", mobile, content+"#signature#");
			}
		}  catch (Exception e) {
			logger.error("error|body={}",body);
			e.printStackTrace();
		}
    }
	
	@Resource
	private IRabbitmqSender rabbitmqSender;
	
	//@Scheduled(cron = "0 */5 * * * ?")//每隔5分钟执行一次
	//@Scheduled(cron = "*/5 * * * * ?")//每隔5秒执行一次
	public void testWarning() {
		Map<String,String> message = new HashMap<String,String>();
		message.put("mobile", "15010602819");
		message.put("content", "系统预警，编号24机器出现故障，请立即查看！");
		message.put("m_id", "24");
		message.put("m_type", "MAC_ERROR");
		
		rabbitmqSender.sendContractDirect("sms.early.warn.queue",
				new Gson().toJson(message));
	}
}
