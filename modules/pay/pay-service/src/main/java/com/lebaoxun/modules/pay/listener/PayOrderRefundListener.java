package com.lebaoxun.modules.pay.listener;

import java.math.BigDecimal;

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
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lebaoxun.modules.pay.entity.PayOrderEntity;
import com.lebaoxun.modules.pay.service.IAlipayGatewayService;
import com.lebaoxun.modules.pay.service.IPayOrderService;
import com.lebaoxun.modules.pay.service.IWxpayGatewayService;

/**
 * 用户日志 收集
 * @author caiqianyi
 *
 */
@Component
@RabbitListener(queues = "pay.order.refund.queue")
public class PayOrderRefundListener {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IPayOrderService payOrderService;
	
	@Resource
	private IWxpayGatewayService wxpayGatewayService;
	
	@Resource
	private IAlipayGatewayService alipayGatewayService;
	
	@Bean
    public Queue queuePayRefund() {
        return new Queue("pay.order.refund.queue",true);
    }

    @Bean
    Binding bindingDirectExchangePayRefund(Queue queuePayRefund, DirectExchange directExchange) {
        return BindingBuilder.bind(queuePayRefund).to(directExchange).with("pay.order.refund.queue");
    }
	
	@RabbitHandler
    public void receive(Object data) throws MessagingException {
		Message m = (Message) data;
		String body = new String(m.getBody());
		logger.debug("body={}",body);
		String text = body.replace("\\\"", "\"");
		JSONObject message = JSONObject.parseObject(text);
		
		logger.debug("rabbit|sendContractDirect|message={}",message);
		try {
			String orderNo = message.getString("orderNo"),
					refundDesc = message.getString("refundDesc");
			
			PayOrderEntity order = payOrderService.selectOne(new EntityWrapper<PayOrderEntity>().eq("order_no", orderNo));
			if(order == null){
				logger.error("订单不存在 {}",orderNo);
				return;
			}
			if(order.getStatus() == 2){
				logger.error("订单已退款 {}",orderNo);
				return;
			}
			if(order.getStatus() != 1){
				logger.error("订单尚未支付 {}",orderNo);
			}
			boolean re = false;
			if("WX".equals(order.getPayWay())){
				Integer refundFee = message.getInteger("refundFee");
				if(refundFee == null){
					refundFee = order.getTotalFee().multiply(new BigDecimal(100)).intValue();
				}
				re = wxpayGatewayService.refund(order, refundFee, refundDesc);
			}
			if("ZFB".equals(order.getPayWay())){
				re = alipayGatewayService.refund(order);
			}
			if(re){
				order.setStatus(2);
				payOrderService.updateById(order);
			}
		}  catch (Exception e) {
			logger.error("error|body={}",body);
			e.printStackTrace();
		}
    }
}
