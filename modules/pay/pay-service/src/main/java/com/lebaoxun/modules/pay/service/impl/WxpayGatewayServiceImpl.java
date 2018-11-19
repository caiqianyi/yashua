package com.lebaoxun.modules.pay.service.impl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.modules.pay.dao.PayOrderDao;
import com.lebaoxun.modules.pay.entity.PayOrderEntity;
import com.lebaoxun.modules.pay.pojo.WxpayConfig;
import com.lebaoxun.modules.pay.service.IWxpayConfigService;
import com.lebaoxun.modules.pay.service.IWxpayGatewayService;
import com.lebaoxun.modules.pay.wxpay.util.HttpXmlUtils;
import com.lebaoxun.modules.pay.wxpay.util.ParseXMLUtils;
import com.lebaoxun.modules.pay.wxpay.util.RandCharsUtils;
import com.lebaoxun.modules.pay.wxpay.util.WXSignUtils;
import com.lebaoxun.modules.pay.wxpay.vo.Unifierefund;

@Service("wxpayGatewayService")
public class WxpayGatewayServiceImpl implements IWxpayGatewayService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private IWxpayConfigService wxpayConfigService;
	
	@Resource
	private PayOrderDao payOrderDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean refund(PayOrderEntity order,Integer refundFee,String refundDesc) {
		// TODO Auto-generated method stub
		WxpayConfig config = wxpayConfigService.getWxpayConfig(order.getGroup());
		//String notify_url = config.getNotifyUrl();
		// 参数组
		String appid =  config.getAppid();
		String mch_id = config.getMchid();
		String secret = config.getSecret();
		
		String nonce_str = RandCharsUtils.getRandomString(16);

		String out_refund_no = GenerateCode.getUUID();
		Integer totalFee = order.getTotalFee().multiply(new BigDecimal(100)).intValue();
		// 参数：开始生成签名
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonce_str);
		parameters.put("transaction_id", order.getTradeNo());
		parameters.put("out_refund_no", out_refund_no);
		parameters.put("total_fee", totalFee);
		parameters.put("refund_fee", refundFee);
		parameters.put("refund_desc", refundDesc);
		//parameters.put("notify_url", notify_url);
		
		String sign = WXSignUtils.createSign("UTF-8", parameters, secret);

		Unifierefund unifiedorder = new Unifierefund();
		unifiedorder.setAppid(appid);
		unifiedorder.setMch_id(mch_id);
		unifiedorder.setNonce_str(nonce_str);
		unifiedorder.setTransaction_id(order.getTradeNo());
		unifiedorder.setOut_refund_no(out_refund_no);
		unifiedorder.setTotal_fee(totalFee);
		unifiedorder.setRefund_fee(refundFee);
		unifiedorder.setRefund_desc(refundDesc);
		//unifiedorder.setNotify_url(notify_url);
		unifiedorder.setSign(sign);
		return wxAppRefund(unifiedorder);
	}
	
	boolean wxAppRefund(Unifierefund unifiedorder) {
		// 构造xml参数
		String xmlInfo = HttpXmlUtils.refundXmlInfo(unifiedorder);
		String wxUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		String method = "POST";
	
		String weixinPost = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();
	
		Map<String, Object> retMap = ParseXMLUtils.jdomParseXml(weixinPost);
		String return_code=retMap.get("return_code").toString();
		
		logger.debug("retMap={}",retMap);
		logger.debug("请求申请退款接口返回结果 ==return_code={}",return_code);
		if("FAIL".equals(return_code)){
			throw new I18nMessageException("-1","支付失败,"+retMap.get("return_msg"));
		}
		//支付结果
		String result_code=retMap.get("result_code").toString();
		logger.debug("请求申请退款接口返回支付结果 ==return_code={}",result_code);
		if("FAIL".equals(result_code)){
			String err_code=retMap.get("err_code").toString();//返回错误码
			String err_code_des=retMap.get("err_code_des").toString();//错误描述信息
			if("BIZERR_NEED_RETRY".equals(err_code)){//退款业务流程错误，需要商户触发重试来解决
				return wxAppRefund(unifiedorder);
			}
			throw new I18nMessageException("-1",err_code_des);
		}
		return true;
	}
	
}
