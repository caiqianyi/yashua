package com.lebaoxun.modules.pay.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.google.gson.Gson;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.modules.pay.entity.PayOrderEntity;
import com.lebaoxun.modules.pay.pojo.AlipayConfig;
import com.lebaoxun.modules.pay.service.IAlipayConfigService;
import com.lebaoxun.modules.pay.service.IPayOrderService;
import com.lebaoxun.soa.amqp.core.sender.IRabbitmqSender;

/**
 * 
 * @author 蔡骞毅 2017/12/18
 */
@RestController
public class AlipayController {

	private Logger logger = LoggerFactory.getLogger(AlipayController.class);

	@Resource
	private IRabbitmqSender rabbitmqSender;
	
	@Resource
	private IAlipayConfigService alipayConfigService;
	
	@Resource
	private IPayOrderService payOrderService;

	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping(value = "/alipay/config/list", method = RequestMethod.GET)
	List<AlipayConfig> configList(){
		return alipayConfigService.getAlipayConfig();
	}
	
	@RequestMapping(value = "/alipay/config/info", method = RequestMethod.GET)
	ResponseMessage configInfo(@RequestParam("account")String account){
		return ResponseMessage.ok(alipayConfigService.getAlipayConfig(account));
	}
	
	@RequestMapping(value = "/alipay/config/save", method = RequestMethod.POST)
	ResponseMessage configSave(@RequestBody AlipayConfig config){
		return ResponseMessage.ok(alipayConfigService.setAlipayConfigByAppid(config));
	}
	
	@RequestMapping(value = "/alipay/config/delete", method = RequestMethod.POST)
	ResponseMessage configDelete(@RequestParam("account")String account){
		return ResponseMessage.ok(alipayConfigService.deleteAlipayConfig(account));
	}
	
	@RequestMapping(value = "/alipay/payment", method = RequestMethod.POST)
	ResponseMessage payment(@RequestParam("group")String group,
			@RequestParam("outTradeNo")String outTradeNo,
			@RequestParam("subject")String subject,
			@RequestParam("totalAmount")String totalAmount,
			@RequestParam(value="body",required=false)String body,
			@RequestParam(value="spbill_create_ip",required=false)String spbill_create_ip,
			@RequestParam(value="userId") Long userId) {
		
		AlipayConfig config = alipayConfigService.getAlipayConfig(group);
		String appid = config.getAppid();
		String mch_id = config.getMercNo();
		String privateKey = config.getPrivateKey();
		String publicKey = config.getPublicKey();
		String tradeType = "QUICK_WAP_WAY";
		
		String out_trade_no = GenerateCode.getUUID();
		String returnUrl = config.getReturnUrl();
		String notifyUrl = config.getNotifyUrl();
		
		PayOrderEntity order = new PayOrderEntity();
		order.setAppid(appid);
		order.setAttach(subject);
		order.setBody(body);
		order.setCreateTime(new Date());
		order.setCreateUser(userId);
		order.setGroup(group);
		order.setMchId(mch_id);
		order.setNotifyUrl(notifyUrl);
		order.setOrderNo(outTradeNo);
		order.setOutOrderNo(out_trade_no);
		order.setPayWay("ZFB");
		order.setSpbillCreateIp(spbill_create_ip);
		order.setStatus(0);
		order.setTotalFee(new BigDecimal(totalAmount));
		order.setTradeType(tradeType);
		payOrderService.insert(order);
		
		logger.debug("appid={}",appid);
		logger.debug("privateKey={}",privateKey);
		logger.debug("publicKey={}",publicKey);
		AlipayClient client = new DefaultAlipayClient(
				"https://openapi.alipay.com/gateway.do",
				appid,
				privateKey,
						"json",
						 "UTF-8",
						 publicKey,
				"RSA2");
		AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

		// 封装请求支付信息
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(outTradeNo);
		model.setSubject(subject);
		model.setTotalAmount(totalAmount);
		model.setBody(body);
		model.setTimeoutExpress("2m");// 超时时间 可空
		model.setProductCode(tradeType);// 销售产品码 必填
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(notifyUrl);
		// 设置同步地址
		alipay_request.setReturnUrl(returnUrl);
		logger.debug("alipay_request={}",new Gson().toJson(alipay_request));

		// form表单生产
		String form = "";
		// 调用SDK生成表单
		try {
			form = client.pageExecute(alipay_request).getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return ResponseMessage.ok(form);

		/*logger.debug("alipay payment|aplipayReqest={}",
				new Gson().toJson(aplipayReqest));
		return new SuccessMessage(alipaySubmit.buildRequest(aplipayReqest));*/
	}

	@RequestMapping(value = "/alipay/notify")
	String notify(@RequestParam Map<String, String> params) {

		/*//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<?, ?> requestParams = request.getParameterMap();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}*/
		try {
			// 根据合作身份者id来区分那个区服
			String appid = new String(params.get("app_id")
					.getBytes("ISO-8859-1"), "UTF-8");
			
			// logger.info("异步通知交易状态---"+trade_status);
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			
			AlipayConfig config = alipayConfigService.getAlipayConfigByAppid(appid);
			String publicKey = config.getPublicKey();
			logger.debug("appid={}",appid);
			logger.debug("publicKey={}",publicKey);
			logger.debug("params={}",params);
			
			boolean verify_result = AlipaySignature.rsaCheckV1(params, publicKey, "UTF-8", "RSA2");
			logger.debug("verify_result={}",verify_result);
			if (!verify_result) {// 验证失败
				throw new I18nMessageException("500", "验证失败");
			}

			// 交易状态
			String trade_status = new String(params.get(
					"trade_status").getBytes("ISO-8859-1"), "UTF-8");

			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码
			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

			if ("TRADE_FINISHED".equals(trade_status)) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				// 如果有做过处理，不执行商户的业务程序
				// 注意：
				// 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				throw new I18nMessageException("500", "验证失败");
			}

			if (!"TRADE_SUCCESS".equals(trade_status)) {
				throw new I18nMessageException("500", "验证失败");
			}

			// 判断该笔订单是否在商户网站中已经做过处理
			// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
			// 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
			// 如果有做过处理，不执行商户的业务程序

			String out_trade_no = new String(params.get(
					"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			BigDecimal totalFee = new BigDecimal(new  String(params.get("total_amount")
					.getBytes("ISO-8859-1"), "UTF-8"));
			String tradeNo = new String(params.get("trade_no")
					.getBytes("ISO-8859-1"), "UTF-8");
			long buyTime = datetimeFormat.parse(
					new String(params.get("gmt_payment").getBytes(
							"ISO-8859-1"), "UTF-8")).getTime();
			return payOrderService.notify(out_trade_no, totalFee, tradeNo, buyTime, config.getQueueName());
		} catch (Exception e) {
			logger.debug("notify:{}", e);
		}
		return "fail";
	}

}