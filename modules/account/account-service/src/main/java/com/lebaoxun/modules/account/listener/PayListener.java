package com.lebaoxun.modules.account.listener;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
@RabbitListener(queues = "pay.notify.queue.recharge")
public class PayListener {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private UserService userService;
	
	@Resource
	private IRabbitmqSender rabbitmqSender;
	
	@Bean
    public Queue queuePayRecharge() {
        return new Queue("pay.notify.queue.recharge",true);
    }

    @Bean
    Binding bindingTopicExchangePayRecharge(Queue queuePayRecharge, TopicExchange topicExchange) {
        return BindingBuilder.bind(queuePayRecharge).to(topicExchange).with("pay.notify.queue.#");
    }
	
	@RabbitHandler
    public void receive(Object data) throws MessagingException {
		Message m = (Message) data;
		String body = new String(m.getBody());
		logger.debug("body={}",body);
		String text = body.replace("\\\"", "\"");
		JSONObject message = JSONObject.parseObject(text);
		try {
			String out_trade_no = message.getString("out_trade_no"),
					trade_no = message.getString("trade_no"),
					mercno = message.getString("merc_no"),
					platform = message.getString("platform"),
					trade_type = message.getString("trade_type"),
					total_fee = message.getString("total_fee"),
					recharge_fee = message.getString("recharge_fee"),
					group = message.getString("group"),
					scene = message.getString("scene");
			
			Long user_id = message.getLong("user_id");
			Long buy_time = message.getLong("buy_time");
			logger.debug(
					"out_trade_no={},buy_time={},platform={},total_fee={},recharge_fee={},trade_no={},mercno={},group={},scene={}",
					out_trade_no, buy_time, platform, trade_type, total_fee,
					recharge_fee, trade_no, mercno, group,scene);
			if("recharge".equals(scene)){
				UserEntity user = userService.recharge(user_id, out_trade_no, buy_time, recharge_fee);
				if(user != null){
					String logType = "RECHARGE_SUCCESS";
					Map<String,String> msg = new HashMap<String,String>();
					String timestamp = buy_time+"";
					msg.put("userId", user_id+"");
					msg.put("timestamp", timestamp);
					msg.put("logType", logType.toString());
					msg.put("tradeMoney", recharge_fee);
					msg.put("money", user.getBalance().toString());
					msg.put("descr", "充值成功");
					msg.put("adjunctInfo", out_trade_no);
					
					rabbitmqSender.sendContractDirect("account.log.queue",
							new Gson().toJson(msg));
				}
			}
		}  catch (Exception e) {
			logger.error("error|body={}",body);
			e.printStackTrace();
		}
    }
}
