package com.lebaoxun.modules.account.listener;

import java.math.BigDecimal;
import java.util.Date;

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
import com.lebaoxun.commons.utils.StringUtils;
import com.lebaoxun.modules.account.entity.UserLogEntity;
import com.lebaoxun.modules.account.service.UserLogService;
import com.lebaoxun.soa.amqp.core.sender.IRabbitmqSender;

/**
 * 用户日志 收集
 * @author caiqianyi
 *
 */
@Component
@RabbitListener(queues = "account.log.queue")
public class UserLogListener {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private UserLogService userLogService;
	
	@Resource
	private IRabbitmqSender rabbitmqSender;
	
	@Bean
    public Queue queueAccountLog() {
        return new Queue("account.log.queue",true);
    }

    @Bean
    Binding bindingDirectExchangeAccountLog(Queue queueAccountLog, DirectExchange directExchange) {
        return BindingBuilder.bind(queueAccountLog).to(directExchange).with("account.log.queue");
    }
	
	@RabbitHandler
    public void receive(Object data) throws MessagingException {
		Message m = (Message) data;
		String body = new String(m.getBody());
		logger.debug("body={}",body);
		String text = body.replace("\\\"", "\"");
		
		try {
			JSONObject message = JSONObject.parseObject(text);
			logger.debug("rabbit|sendContractDirect|message={}",message);
			Long userId = message.getLong("userId"),
					timestamp = message.getLong("timestamp");
			
			String logType = message.getString("logType"),
					tradeMoney = message.getString("tradeMoney"),
					money = message.getString("money"),
					descr = message.getString("descr"),
					adjunctInfo = message.getString("adjunctInfo");
			
			UserLogEntity log = new UserLogEntity();
			log.setUserId(userId);
			log.setCreateTime(new Date(timestamp));
			log.setLogType(logType);
			if(StringUtils.isNotBlank(tradeMoney))
				log.setTradeMoney(new BigDecimal(tradeMoney).intValue());
			if(StringUtils.isNotBlank(money))
				log.setMoney(new BigDecimal(money).intValue());
			log.setDescr(descr);
			log.setAdjunctInfo(adjunctInfo);
			
			logger.info("add log={}",new Gson().toJson(log));
			userLogService.insert(log);
		}  catch (Exception e) {
			logger.error("error|body={}",body);
			e.printStackTrace();
		}
    }
}
