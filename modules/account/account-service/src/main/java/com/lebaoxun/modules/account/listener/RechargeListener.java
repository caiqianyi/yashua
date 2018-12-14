package com.lebaoxun.modules.account.listener;

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
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.UserService;
import com.lebaoxun.soa.amqp.core.sender.IRabbitmqSender;

/**
 * 充值回调
 * @author caiqianyi
 *
 */
@Component
@RabbitListener(queues = "account.balance.queue.rechage")
public class RechargeListener {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private UserService userService;
	
	@Resource
	private IRabbitmqSender rabbitmqSender;
	
	@Bean
    public Queue queueAccountRecharge() {
        return new Queue("account.balance.queue.rechage",true);
    }

	 @Bean
    Binding bindingDirectExchangeAccountRecharge(Queue queueAccountRecharge, DirectExchange directExchange) {
        return BindingBuilder.bind(queueAccountRecharge).to(directExchange).with("account.balance.queue.rechage");
    }
	
	@RabbitHandler
    public void receive(Object data) throws MessagingException {
		Message m = (Message) data;
		String body = new String(m.getBody());
		logger.debug("body={}",body);
		String text = body.replace("\\\"", "\"");
		JSONObject message = JSONObject.parseObject(text);
		try {
			String adjunctInfo = message.getString("adjunctInfo"),
					descr = message.getString("descr"),
					logType = message.getString("logType"),
					recharge_fee = message.getString("rechargeFee");
			Long userId = message.getLong("userId");
			Long logTime = message.getLong("logTime");
			UserEntity user = userService.rechargeForSys(userId, logType, adjunctInfo, recharge_fee, logTime);
			if(user != null){
				Map<String,String> msg = new HashMap<String,String>();
				String timestamp = logTime+"";
				msg.put("userId", userId+"");
				msg.put("timestamp", timestamp);
				msg.put("logType", logType.toString());
				msg.put("tradeMoney", recharge_fee);
				msg.put("money", user.getBalance().toString());
				msg.put("descr", descr);
				msg.put("adjunctInfo", adjunctInfo);
				
				rabbitmqSender.sendContractDirect("account.log.queue",
						new Gson().toJson(msg));
			}
		}  catch (Exception e) {
			logger.error("error|body={}",body);
			e.printStackTrace();
		}
    }
}
