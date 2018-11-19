package com.lebaoxun.modules.pay.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.modules.pay.entity.PayOrderEntity;
import com.lebaoxun.modules.pay.pojo.AlipayConfig;
import com.lebaoxun.modules.pay.service.IAlipayConfigService;
import com.lebaoxun.modules.pay.service.IAlipayGatewayService;
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
	private IAlipayGatewayService alipayGatewayService;

	@Resource
	private IPayOrderService payOrderService;

	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@RequestMapping(value = "/alipay/config/list", method = RequestMethod.GET)
	List<AlipayConfig> configList() {
		return alipayConfigService.getAlipayConfig();
	}

	@RequestMapping(value = "/alipay/config/info", method = RequestMethod.GET)
	ResponseMessage configInfo(@RequestParam("account") String account) {
		return ResponseMessage.ok(alipayConfigService.getAlipayConfig(account));
	}

	@RequestMapping(value = "/alipay/config/save", method = RequestMethod.POST)
	ResponseMessage configSave(@RequestBody AlipayConfig config) {
		return ResponseMessage.ok(alipayConfigService
				.setAlipayConfigByAppid(config));
	}

	@RequestMapping(value = "/alipay/config/delete", method = RequestMethod.POST)
	ResponseMessage configDelete(@RequestParam("account") String account) {
		return ResponseMessage.ok(alipayConfigService
				.deleteAlipayConfig(account));
	}

	@RequestMapping(value = "/alipay/payment", method = RequestMethod.POST)
	ResponseMessage payment(
			@RequestParam("group") String group,
			@RequestParam("outTradeNo") String outTradeNo,
			@RequestParam("subject") String subject,
			@RequestParam("totalAmount") String totalAmount,
			@RequestParam(value = "rechargeFee", required = false) BigDecimal rechargeFee,
			@RequestParam(value = "body", required = false) String body,
			@RequestParam(value = "spbill_create_ip", required = false) String spbill_create_ip,
			@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "scene", required = false) String scene) {

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
		order.setRechargeFee(rechargeFee);
		order.setTradeType(tradeType);
		order.setScene(scene);
		payOrderService.insert(order);

		logger.debug("appid={}", appid);
		logger.debug("privateKey={}", privateKey);
		logger.debug("publicKey={}", publicKey);
		AlipayClient client = new DefaultAlipayClient(
				"https://openapi.alipay.com/gateway.do", appid, privateKey,
				"json", "UTF-8", publicKey, "RSA2");
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
		logger.debug("alipay_request={}", new Gson().toJson(alipay_request));

		// form表单生产
		String form = "";
		// 调用SDK生成表单
		try {
			form = client.pageExecute(alipay_request).getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return ResponseMessage.ok(form);

		/*
		 * logger.debug("alipay payment|aplipayReqest={}", new
		 * Gson().toJson(aplipayReqest)); return new
		 * SuccessMessage(alipaySubmit.buildRequest(aplipayReqest));
		 */
	}
	
	@RequestMapping(value = "/alipay/qr_code/payment", method = RequestMethod.POST)
	ResponseMessage qrCodePayment(
			@RequestParam("group") String group,
			@RequestParam("outTradeNo") String outTradeNo,
			@RequestParam("subject") String subject,
			@RequestParam("totalAmount") String totalAmount,
			@RequestParam(value = "rechargeFee", required = false) BigDecimal rechargeFee,
			@RequestParam(value = "body", required = false) String body,
			@RequestParam(value = "spbill_create_ip", required = false) String spbill_create_ip,
			@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "scene", required = false) String scene) {

		AlipayConfig config = alipayConfigService.getAlipayConfig(group);
		String appid = config.getAppid();
		String mch_id = config.getMercNo();
		String privateKey = config.getPrivateKey();
		String publicKey = config.getPublicKey();

		String out_trade_no = GenerateCode.getUUID();
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
		order.setRechargeFee(rechargeFee);
		order.setTradeType("QR_CODE");
		order.setScene(scene);
		payOrderService.insert(order);

		logger.debug("appid={}", appid);
		logger.debug("privateKey={}", privateKey);
		logger.debug("publicKey={}", publicKey);

		AlipayClient client = new DefaultAlipayClient(
				"https://openapi.alipay.com/gateway.do", appid, privateKey,
				"json", "UTF-8", publicKey, "RSA2");
		AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();//创建API对应的request
		Map<String,Object> maps=new HashMap<>();
        maps.put("out_trade_no",out_trade_no);
        maps.put("total_amount",totalAmount);
        maps.put("subject",subject);
        maps.put("store_id","XB001");
        maps.put("timeout_express","1m");
        //把订单信息转换为json对象的字符串
        String postdata = JSONObject.toJSONString(maps);
        alipayRequest.setBizContent(postdata);
		
		alipayRequest.setNotifyUrl(notifyUrl);
		// form表单生产
		String qr_code = "";
		// 调用SDK生成表单
		try {
			AlipayTradePrecreateResponse response = client.execute(alipayRequest);
	        String text = response.getBody();
	        JSONObject jsonObject = JSONObject.parseObject(text);
	        qr_code = jsonObject.getJSONObject("alipay_trade_precreate_response").getString("qr_code");
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		logger.debug("qr_code={}",qr_code);
		return ResponseMessage.ok(qr_code);

		/*
		 * logger.debug("alipay payment|aplipayReqest={}", new
		 * Gson().toJson(aplipayReqest)); return new
		 * SuccessMessage(alipaySubmit.buildRequest(aplipayReqest));
		 */
	}
	
	/**
	 * 
	 * @param group
	 * @param outTradeNo (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        				 需保证商户系统端不能重复，建议通过数据库sequence生成
	 * @param subject  (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
	 * @param totalAmount (必填) 订单总金额，单位为元，不能超过1亿元
	 * @param undiscountableAmount (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
	 * @param rechargeFee
	 * @param body 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
	 * @param spbill_create_ip
	 * @param userId
	 * @param scene
	 */
	/*// 测试当面付2.0生成支付二维码
    public void test_trade_precreate(
			@RequestParam("group") String group,
			@RequestParam("outTradeNo") String outTradeNo,
			@RequestParam("subject") String subject,
			@RequestParam("totalAmount") String totalAmount,
			@RequestParam(value="undiscountableAmount",required=false) String undiscountableAmount,
			@RequestParam(value = "rechargeFee", required = false) BigDecimal rechargeFee,
			@RequestParam(value = "body", required = false) String body,
			@RequestParam(value = "spbill_create_ip", required = false) String spbill_create_ip,
			@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "scene", required = false) String scene) {

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
            .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
            .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
            .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
            .setTimeoutExpress(timeoutExpress)
            //                .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
            .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                // 需要修改为运行机器上的路径
                String filePath = String.format("/Users/sudo/Desktop/qr-%s.png",
                    response.getOutTradeNo());
                log.info("filePath:" + filePath);
                //                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                break;

            case FAILED:
                log.error("支付宝预下单失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
    }*/

	@RequestMapping(value = "/alipay/notify")
	String notify(@RequestParam Map<String, String> params) {

		/*
		 * //获取支付宝POST过来反馈信息 Map<String,String> params = new
		 * HashMap<String,String>(); Map<?,
		 * 
		 *  
		 *  
		 *  
		 *  
		 *  
		 *  ?> requestParams =
		 * request.getParameterMap(); for (Iterator<?> iter =
		 * requestParams.keySet().iterator(); iter.hasNext();) { String name =
		 * (String) iter.next(); String[] values = (String[])
		 * requestParams.get(name); String valueStr = ""; for (int i = 0; i <
		 * values.length; i++) { valueStr = (i == values.length - 1) ? valueStr
		 * + values[i] : valueStr + values[i] + ","; }
		 * //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化 //valueStr = new
		 * String(valueStr.getBytes("ISO-8859-1"), "gbk"); params.put(name,
		 * valueStr); }
		 */
		try {
			// 根据合作身份者id来区分那个区服
			String appid = new String(params.get("app_id").getBytes(
					"ISO-8859-1"), "UTF-8");

			// logger.info("异步通知交易状态---"+trade_status);
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			AlipayConfig config = alipayConfigService
					.getAlipayConfigByAppid(appid);
			String publicKey = config.getPublicKey();
			logger.debug("appid={}", appid);
			logger.debug("publicKey={}", publicKey);
			logger.debug("params={}", params);

			boolean verify_result = AlipaySignature.rsaCheckV1(params,
					publicKey, "UTF-8", "RSA2");
			logger.debug("verify_result={}", verify_result);
			if (!verify_result) {// 验证失败
				throw new I18nMessageException("500", "验证失败");
			}

			// 交易状态
			String trade_status = new String(params.get("trade_status")
					.getBytes("ISO-8859-1"), "UTF-8");

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

			String out_trade_no = new String(params.get("out_trade_no")
					.getBytes("ISO-8859-1"), "UTF-8");
			BigDecimal totalFee = new BigDecimal(new String(params.get(
					"total_amount").getBytes("ISO-8859-1"), "UTF-8"));
			String tradeNo = new String(params.get("trade_no").getBytes(
					"ISO-8859-1"), "UTF-8");
			long buyTime = datetimeFormat.parse(
					new String(
							params.get("gmt_payment").getBytes("ISO-8859-1"),
							"UTF-8")).getTime();
			return payOrderService.notify(out_trade_no, totalFee, tradeNo,
					buyTime, config.getQueueName(), "alipay");
		} catch (Exception e) {
			logger.debug("notify:{}", e);
		}
		return "fail";
	}

	@RequestMapping(value="/alipay/refund")
	ResponseMessage refund(@RequestParam("orderNo")String orderNo) { // 发送请求
		PayOrderEntity order = payOrderService.selectOne(new EntityWrapper<PayOrderEntity>().eq("order_no", orderNo));
		if(order == null){
			logger.error("订单不存在 {}",orderNo);
			throw new I18nMessageException("-1","订单不存在");
		}
		if(order.getStatus() == 2){
			logger.error("订单已退款 {}",orderNo);
			throw new I18nMessageException("-1","订单已退款");
		}
		if(order.getStatus() != 1){
			logger.error("订单尚未支付 {}",orderNo);
			throw new I18nMessageException("-1","订单尚未支付");
		}
		return ResponseMessage.ok(alipayGatewayService.refund(order));
	}

}