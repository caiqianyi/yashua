package com.lebaoxun.modules.pay.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.Query;
import com.lebaoxun.modules.pay.dao.PayOrderDao;
import com.lebaoxun.modules.pay.entity.PayOrderEntity;
import com.lebaoxun.modules.pay.service.IPayOrderService;
import com.lebaoxun.soa.amqp.core.sender.IRabbitmqSender;


@Service("payOrderService")
public class PayOrderServiceImpl extends ServiceImpl<PayOrderDao, PayOrderEntity> implements IPayOrderService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IRabbitmqSender rabbitmqSender;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PayOrderEntity> page = this.selectPage(
                new Query<PayOrderEntity>(params).getPage(),
                new EntityWrapper<PayOrderEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String notify(String out_trade_no, BigDecimal total_fee, String tradeNo, Long buyTime, String queue){
		PayOrderEntity order = this.selectOne(new EntityWrapper<PayOrderEntity>().eq("out_order_no", out_trade_no));
		if(order == null){
			throw new I18nMessageException("-1","交易失败，订单号不存在-out_trade_no="+out_trade_no);
		}
		
		if (order.getTotalFee().equals(total_fee)) {
			throw new I18nMessageException("-1","订单金额不一致！-out_trade_no="+out_trade_no);
		}
		
		if(order.getStatus() == 1){//订单已完成
			return "sucess";
		}
		
		if(StringUtils.isBlank(queue)){
			queue = "pay.success.queue";
		}
		
		order.setStatus(1);
		order.setTradeNo(tradeNo);
		order.setPayTime(buyTime);
		order.setQueueKey(queue);
		this.baseMapper.updateById(order);
		sendNotify(queue, out_trade_no, total_fee.toString(), tradeNo, buyTime+"", order.getTradeType(), order.getMchId());
		return "sucess";
	}
	
	private void sendNotify(String queueKey,String out_trade_no,String total_fee,String rransaction_id,
			String buyTime,String trade_type,String merc_no){
		Map<String,String> message = new HashMap<String,String>();
		message.put("out_trade_no", out_trade_no);
		message.put("total_fee", total_fee);
		message.put("trade_no", rransaction_id);
		message.put("buyTime", ""+buyTime);
		message.put("platform", "wxpay");
		message.put("trade_type", trade_type);
		message.put("merc_no", merc_no);
		logger.info("rabbit|sendContractDirect|message={}",message);
		rabbitmqSender.sendContractDirect(queueKey,
				new Gson().toJson(message));
	}

}
