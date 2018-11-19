package com.lebaoxun.modules.pay.service.hystrix;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.pay.pojo.WxpayConfig;
import com.lebaoxun.modules.pay.service.IWxPayService;

@Component
public class WxPayServiceHystrix implements IWxPayService {

	@Override
	public List<WxpayConfig> configList() {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage configInfo(String group) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage configSave(WxpayConfig config) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage configDelete(String group) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage payment(String spbill_create_ip, String orderNo,
			String descr, Integer totalFee, String attach, String group,
			String openid, Long userId,BigDecimal rechargeFee,
			String scene) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage h5Payment(String wapUrl, String wapName,
			String spbill_create_ip, String orderNo, String descr,
			Integer totalFee, String attach, String group, Long userId,
			BigDecimal rechargeFee,String scene) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage qrcodePayment(String spbill_create_ip,
			String orderNo, String descr, Integer totalFee, String attach,
			String group, Long userId,BigDecimal rechargeFee,String scene){
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage appPayment(String spbill_create_ip, String orderNo,
			String descr, Integer totalFee, String attach, String group,
			Long userId,BigDecimal rechargeFee,String scene) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage query(String out_trade_no, String account,
			String send) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}

	@Override
	public ResponseMessage wxAppRefund(String outTradeNo, String refundDesc,
			Integer refundFee) {
		// TODO Auto-generated method stub
		throw new I18nMessageException("502","服务器异常，请稍后重试");
	}
}
