package com.lebaoxun.modules.mall.listener;

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
import com.lebaoxun.modules.mall.service.MallOrderService;

/**
 * 充值回调
 * @author caiqianyi
 *
 */
@Component
@RabbitListener(queues = "pay.notify.queue.shopping")
public class PayOrderListener {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private MallOrderService mallOrderService;
	
	@Bean
    public Queue queuePayShopping() {
        return new Queue("pay.notify.queue.shopping",true);
    }

    @Bean
    Binding bindingTopicExchangePayShopping(Queue queuePayShopping, TopicExchange topicExchange) {
        return BindingBuilder.bind(queuePayShopping).to(topicExchange).with("pay.notify.queue.#");
    }
	
	@RabbitHandler
    public void receive(Object data) throws MessagingException {
		Message m = (Message) data;
		String body = new String(m.getBody());
		logger.debug("body={}",body);
		String text = body.replace("\\\"", "\"");
		JSONObject message = JSONObject.parseObject(text);
		try {
			String scene = message.getString("scene");
			if("shopping".equals(scene)){
				String orderNo = message.getString("order_no");
				Long buy_time = message.getLong("buy_time");
				mallOrderService.payMallOrder(orderNo, buy_time+"");
			}
		}  catch (Exception e) {
			logger.error("error|body={}",body);
			e.printStackTrace();
		}
    }
}
