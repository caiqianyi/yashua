package com.lebaoxun.modules.pay.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.commons.utils.StringUtils;
import com.lebaoxun.modules.pay.entity.PayOrderEntity;
import com.lebaoxun.modules.pay.pojo.WxpayConfig;
import com.lebaoxun.modules.pay.service.IPayOrderService;
import com.lebaoxun.modules.pay.service.IWxpayConfigService;
import com.lebaoxun.modules.pay.service.IWxpayGatewayService;
import com.lebaoxun.modules.pay.wxpay.util.HttpXmlUtils;
import com.lebaoxun.modules.pay.wxpay.util.JdomParseXmlUtils;
import com.lebaoxun.modules.pay.wxpay.util.ParseXMLUtils;
import com.lebaoxun.modules.pay.wxpay.util.RandCharsUtils;
import com.lebaoxun.modules.pay.wxpay.util.WXSignUtils;
import com.lebaoxun.modules.pay.wxpay.vo.Unifiedorder;
import com.lebaoxun.modules.pay.wxpay.vo.Unifierefund;

/**
 * 
 * @author 蔡骞毅
 * 2017/12/18
 */
@RestController
public class WxpayController {

	private Logger logger = LoggerFactory.getLogger(WxpayController.class);
	
	@Resource
	private IWxpayConfigService wxpayConfigService;
	
	@Resource
	private IPayOrderService payOrderService;
	
	@Resource
	private IWxpayGatewayService wxpayGatewayService;
	
	@RequestMapping(value = "/wxpay/config/list", method = RequestMethod.GET)
	List<WxpayConfig> configList(){
		return wxpayConfigService.getWxpayConfig();
	}
	
	@RequestMapping(value = "/wxpay/config/info", method = RequestMethod.GET)
	ResponseMessage configInfo(@RequestParam("group")String group){
		return ResponseMessage.ok(wxpayConfigService.getWxpayConfig(group));
	}
	
	@RequestMapping(value = "/wxpay/config/save", method = RequestMethod.POST)
	ResponseMessage configSave(@RequestBody WxpayConfig config){
		return ResponseMessage.ok(wxpayConfigService.setWxpayConfigByAppid(config));
	}
	
	@RequestMapping(value = "/wxpay/config/delete", method = RequestMethod.POST)
	ResponseMessage configDelete(@RequestParam("group")String group){
		return ResponseMessage.ok(wxpayConfigService.deleteWxpayConfig(group));
	}
	
	/**
	 * 微信公众号支付付款
	 * 
	 * @return JsonObject
	 * @throws Exception
	 */
	@RequestMapping(value="/wxpay/payment", method = RequestMethod.POST)
	ResponseMessage payment(@RequestParam("spbill_create_ip")String spbill_create_ip, 
			@RequestParam("orderNo")String orderNo, 
			@RequestParam("descr")String descr, @RequestParam("totalFee")Integer totalFee, 
			@RequestParam("attach")String attach, @RequestParam("group")String group, 
			@RequestParam("openid")String openid, @RequestParam("userId") Long userId,
			@RequestParam(value="rechargeFee",required=false)BigDecimal rechargeFee,
			@RequestParam(value="scene",required=false)String scene) {
		String tradeType = "JSAPI";
		
		WxpayConfig config = wxpayConfigService.getWxpayConfig(group);
		String notify_url = config.getNotifyUrl();
		// 参数组
		String appid =  config.getAppid();
		String mch_id = config.getMchid();
		String secret = config.getSecret();
		
		String nonce_str = RandCharsUtils.getRandomString(16);
		String out_trade_no = GenerateCode.getUUID();
		
		// 参数：开始生成签名
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonce_str);
		parameters.put("body", descr);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("total_fee", totalFee);
		parameters.put("spbill_create_ip", spbill_create_ip);
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", tradeType);
		parameters.put("attach", attach);
		parameters.put("openid", openid);
		
		String sign = WXSignUtils.createSign("UTF-8", parameters, secret);

		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(appid);
		unifiedorder.setMch_id(mch_id);
		unifiedorder.setNonce_str(nonce_str);
		unifiedorder.setBody(descr);
		unifiedorder.setOut_trade_no(out_trade_no);
		unifiedorder.setTotal_fee(totalFee);
		unifiedorder.setSpbill_create_ip(spbill_create_ip);
		unifiedorder.setNotify_url(notify_url);
		unifiedorder.setTrade_type(tradeType);
		unifiedorder.setAttach(attach);
		unifiedorder.setOpenid(openid);
		unifiedorder.setSign(sign);

		// 构造xml参数
		String xmlInfo = HttpXmlUtils.xmlInfo(unifiedorder);
		String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String method = "POST";

		String weixinPost = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();

		Map<String, Object> retMap = ParseXMLUtils.jdomParseXml(weixinPost);
		String return_code=retMap.get("return_code").toString();
		
		logger.debug("retMap={}",retMap);
		logger.info("请求统一下单接口返回结果 ==return_code={}",return_code);
		if("FAIL".equals(return_code)){
			logger.error("retMap={}",retMap);
			throw new I18nMessageException("-1","支付失败,"+retMap.get("return_msg"));
		}
		//支付结果
		String result_code=retMap.get("result_code").toString();
		logger.debug("请求统一下单接口返回支付结果 ==return_code={}",result_code);
		if("FAIL".equals(result_code)){
			String err_code=retMap.get("err_code").toString();//返回错误码
			String err_code_des=retMap.get("err_code_des").toString();//错误描述信息
			/**
			 * NOTENOUGH 余额不足
			 * ORDERPAID 商户订单已支付
			 * ORDERCLOSED 订单已关闭
			 * OUT_TRADE_NO_USED 商户订单号重复
			 */
			if("NOTENOUGH".equals(err_code)||
					"ORDERCLOSED".equals(err_code)||
					"ORDERPAID".equals(err_code)||
					"OUT_TRADE_NO_USED".equals(err_code)){
				throw new I18nMessageException("-1",err_code_des);
			}
			throw new I18nMessageException("-1","支付失败");
		}
		String prepay_id=retMap.get("prepay_id").toString();
		SortedMap<Object, Object> parameters1 = new TreeMap<Object, Object>();
		parameters1.put("appId", appid);
		long timetoken = System.currentTimeMillis() / 1000;
		parameters1.put("timeStamp", timetoken + "");
		parameters1.put("nonceStr", nonce_str);
		parameters1.put("package", "prepay_id="+prepay_id);
		parameters1.put("signType", "MD5");
		
		PayOrderEntity order = new PayOrderEntity();
		order.setAppid(appid);
		order.setAttach(attach);
		order.setBody(descr);
		order.setCreateTime(new Date());
		order.setCreateUser(userId);
		order.setGroup(group);
		order.setMchId(mch_id);
		order.setNotifyUrl(notify_url);
		order.setOrderNo(orderNo);
		order.setOutOrderNo(out_trade_no);
		order.setPayWay("WX");
		order.setSpbillCreateIp(spbill_create_ip);
		order.setStatus(0);
		order.setTotalFee(new BigDecimal(totalFee).divide(new BigDecimal(100)));
		order.setRechargeFee(rechargeFee);
		order.setTradeType(tradeType);
		order.setScene(scene);
		payOrderService.insert(order);
		
		String resign = WXSignUtils.createSign("UTF-8", parameters1,secret);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("appId", appid);
		ret.put("timeStamp", timetoken + "");
		ret.put("nonceStr", nonce_str);
		ret.put("package", "prepay_id="+prepay_id);
		ret.put("signType", "MD5");
		ret.put("paySign", resign);
		return new ResponseMessage(ret);
	}
	
	/**
	 * 微信H5付款
	 * @param wapUrl WAP网站URL地址
	 * @param wapName WAP 网站名
	 * @param spbill_create_ip
	 * @param orderNo
	 * @param descr
	 * @param totalFee
	 * @param attach
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/wxpay/payment/h5", method = RequestMethod.POST)
	ResponseMessage h5Payment(@RequestParam("wapUrl") String wapUrl, 
			@RequestParam("wapName") String wapName, @RequestParam("spbill_create_ip") String spbill_create_ip, 
			@RequestParam("orderNo") String orderNo, @RequestParam("descr") String descr, 
			@RequestParam("totalFee") Integer totalFee, @RequestParam("attach") String attach, 
			@RequestParam(value="rechargeFee",required=false)BigDecimal rechargeFee,
			@RequestParam("group") String group, @RequestParam("userId") Long userId,
			@RequestParam(value="scene",required=false)String scene){
		
		String tradeType = "MWEB";
		
		WxpayConfig config = wxpayConfigService.getWxpayConfig(group);
		String notify_url = config.getNotifyUrl();
		// 参数组
		String appid =  config.getAppid();
		String mch_id = config.getMchid();
		String secret = config.getSecret();
		
		String nonce_str = RandCharsUtils.getRandomString(16);

		String out_trade_no = GenerateCode.getUUID();
		
		// 参数：开始生成签名
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonce_str);
		parameters.put("body", descr);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("total_fee", totalFee);
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", tradeType);
		parameters.put("spbill_create_ip", spbill_create_ip);
		parameters.put("attach", attach);
		
		Map<String,String> scene_info = new TreeMap<String, String>();
		scene_info.put("type", "Wap");
		scene_info.put("wap_url", wapUrl);
		scene_info.put("wap_name", wapName);
		parameters.put("scene_info", scene_info);
		
		String sign = WXSignUtils.createSign("UTF-8", parameters, secret);

		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(appid);
		unifiedorder.setMch_id(mch_id);
		unifiedorder.setNonce_str(nonce_str);
		unifiedorder.setSign(sign);
		unifiedorder.setBody(descr);
		unifiedorder.setOut_trade_no(out_trade_no);
		unifiedorder.setTotal_fee(totalFee);
		unifiedorder.setSpbill_create_ip(spbill_create_ip);
		unifiedorder.setNotify_url(notify_url);
		unifiedorder.setTrade_type(tradeType);
		unifiedorder.setAttach(attach);

		// 构造xml参数
		String xmlInfo = HttpXmlUtils.xmlInfo(unifiedorder);
		String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String method = "POST";

		String weixinPost = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();

		Map<String, Object> retMap = ParseXMLUtils.jdomParseXml(weixinPost);
		String return_code = retMap.remove("return_code").toString();
		
		logger.debug("retMap={}",retMap);
		logger.error("请求统一下单接口返回结果 =="+return_code);
		if("FAIL".equals(return_code)){
			throw new I18nMessageException("-1","支付失败,"+retMap.get("return_msg"));
		}
		//支付结果
		String result_code = retMap.remove("result_code").toString();
		logger.error("请求统一下单接口返回支付结果 =="+result_code);
		if("FAIL".equals(result_code)){
			String err_code=retMap.remove("err_code").toString();//返回错误码
			String err_code_des=retMap.remove("err_code_des").toString();//错误描述信息
			logger.error("请求统一下单接口返回结果 =="+err_code+"错误信息为=="+err_code_des);
			/**
			 * NOTENOUGH 余额不足
			 * ORDERPAID 商户订单已支付
			 * ORDERCLOSED 订单已关闭
			 * OUT_TRADE_NO_USED 商户订单号重复
			 */
			if("NOTENOUGH".equals(err_code)||
					"ORDERCLOSED".equals(err_code)||
					"ORDERPAID".equals(err_code)||
					"OUT_TRADE_NO_USED".equals(err_code)){
				throw new I18nMessageException("-1",err_code_des);
			}
			throw new I18nMessageException("-1","支付失败");
		}
		PayOrderEntity order = new PayOrderEntity();
		order.setAppid(appid);
		order.setAttach(attach);
		order.setBody(descr);
		order.setCreateTime(new Date());
		order.setCreateUser(userId);
		order.setGroup(group);
		order.setMchId(mch_id);
		order.setNotifyUrl(notify_url);
		order.setOrderNo(orderNo);
		order.setOutOrderNo(out_trade_no);
		order.setPayWay("WX");
		order.setSpbillCreateIp(spbill_create_ip);
		order.setStatus(0);
		order.setTotalFee(new BigDecimal(totalFee).divide(new BigDecimal(100)));
		order.setRechargeFee(rechargeFee);
		order.setTradeType(tradeType);
		order.setScene(scene);
		payOrderService.insert(order);
		return new ResponseMessage(retMap);
	}
	
	@RequestMapping(value="/wxpay/payment/qrcode", method = RequestMethod.POST)
	ResponseMessage qrcodePayment(
			@RequestParam("spbill_create_ip") String spbill_create_ip, @RequestParam("orderNo") String orderNo, 
			@RequestParam("descr") String descr, @RequestParam("totalFee") Integer totalFee, 
			@RequestParam("attach") String attach, @RequestParam("group") String group, 
			@RequestParam("userId") Long userId,
			@RequestParam(value="rechargeFee",required=false)BigDecimal rechargeFee,
			@RequestParam(value="scene",required=false)String scene) throws Exception {
		String tradeType = "NATIVE";
		WxpayConfig config = wxpayConfigService.getWxpayConfig(group);
		String notify_url = config.getNotifyUrl();
		// 参数组
		String appid =  config.getAppid();
		String mch_id = config.getMchid();
		String secret = config.getSecret();
		
		String nonce_str = RandCharsUtils.getRandomString(16);

		String out_trade_no = GenerateCode.getUUID();
		
		// 参数：开始生成签名
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonce_str);
		parameters.put("body", descr);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("total_fee", totalFee);
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", tradeType);
		parameters.put("spbill_create_ip", spbill_create_ip);
		parameters.put("attach", attach);
		
		String sign = WXSignUtils.createSign("UTF-8", parameters, secret);

		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(appid);
		unifiedorder.setMch_id(mch_id);
		unifiedorder.setNonce_str(nonce_str);
		unifiedorder.setSign(sign);
		unifiedorder.setBody(descr);
		unifiedorder.setOut_trade_no(out_trade_no);
		unifiedorder.setTotal_fee(totalFee);
		unifiedorder.setSpbill_create_ip(spbill_create_ip);
		unifiedorder.setNotify_url(notify_url);
		unifiedorder.setTrade_type(tradeType);
		unifiedorder.setAttach(attach);

		// 构造xml参数
		String xmlInfo = HttpXmlUtils.xmlInfo(unifiedorder);
		String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String method = "POST";

		String weixinPost = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();

		Map<String, Object> retMap = ParseXMLUtils.jdomParseXml(weixinPost);
		String return_code=retMap.get("return_code").toString();
		logger.debug("retMap={}",retMap);
		logger.error("请求统一下单接口返回结果 =="+return_code);
		if("FAIL".equals(return_code)){
			throw new I18nMessageException("-1","支付失败,"+retMap.get("return_msg"));
		}
		//支付结果
		String result_code=retMap.get("result_code").toString();
		logger.error("请求统一下单接口返回支付结果 =="+result_code);
		if("FAIL".equals(result_code)){
			String err_code=retMap.get("err_code").toString();//返回错误码
			String err_code_des=retMap.get("err_code_des").toString();//错误描述信息
			logger.error("请求统一下单接口返回结果 =="+err_code+"错误信息为=="+err_code_des);
			/**
			 * NOTENOUGH 余额不足
			 * ORDERPAID 商户订单已支付
			 * ORDERCLOSED 订单已关闭
			 * OUT_TRADE_NO_USED 商户订单号重复
			 */
			if("NOTENOUGH".equals(err_code)||
					"ORDERCLOSED".equals(err_code)||
					"ORDERPAID".equals(err_code)||
					"OUT_TRADE_NO_USED".equals(err_code)){
				throw new I18nMessageException("-1",err_code_des);
			}
			throw new I18nMessageException("-1","支付失败");
		}
		
		PayOrderEntity order = new PayOrderEntity();
		order.setAppid(appid);
		order.setAttach(attach);
		order.setBody(descr);
		order.setCreateTime(new Date());
		order.setCreateUser(userId);
		order.setGroup(group);
		order.setMchId(mch_id);
		order.setNotifyUrl(notify_url);
		order.setOrderNo(orderNo);
		order.setOutOrderNo(out_trade_no);
		order.setPayWay("WX");
		order.setSpbillCreateIp(spbill_create_ip);
		order.setStatus(0);
		order.setTotalFee(new BigDecimal(totalFee).divide(new BigDecimal(100)));
		order.setRechargeFee(rechargeFee);
		order.setTradeType(tradeType);
		order.setScene(scene);
		payOrderService.insert(order);
		
		String prepay_id=retMap.get("prepay_id").toString();
		String code_url=retMap.get("code_url").toString();
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("prepay_id", prepay_id);
		ret.put("code_url", code_url);
		ret.put("order_no", orderNo);
		return new ResponseMessage(ret);
	}
	
	 /**
     * 生成二维码图片并直接以流的形式输出到页面
     * @param code_url
     * @param response
     */
    @RequestMapping("/wxpay/qrcode/show")
    public void getQRCode(@RequestParam("code_url")String code_url,
    		HttpServletResponse response){
    	encodeQrcode(code_url, response);
    }

	/**
	 * 生成二维码图片 不存储 直接以流的形式输出到页面
	 * @param content
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void encodeQrcode(String content,HttpServletResponse response){
	    if(content==null || "".equals(content))
	        return;
	   MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	   Map hints = new HashMap();
	   hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); //设置字符集编码类型
	   BitMatrix bitMatrix = null;
	   try {
	       bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300,hints);
	       BufferedImage image = toBufferedImage(bitMatrix);
	       //输出二维码图片流
	       try {
	           ImageIO.write(image, "png", response.getOutputStream());
	       } catch (IOException e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();
	       }
	   } catch (WriterException e1) {
	       // TODO Auto-generated catch block
	       e1.printStackTrace();
	   }         
	}
	
	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;

	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
	
	/**
	 * 微信APP支付付款
	 * 
	 * @return JsonObject
	 * @throws Exception
	 */
	@RequestMapping(value="/wxpay/payment/app", method = RequestMethod.POST)
	ResponseMessage appPayment(@RequestParam("spbill_create_ip")String spbill_create_ip, @RequestParam("orderNo")String orderNo, 
			@RequestParam("descr")String descr, @RequestParam("totalFee")Integer totalFee, 
			@RequestParam("attach")String attach, @RequestParam("group")String group,
			@RequestParam("userId") Long userId,
			@RequestParam(value="rechargeFee",required=false)BigDecimal rechargeFee,
			@RequestParam(value="scene",required=false)String scene) {
		String tradeType = "APP";
		WxpayConfig config = wxpayConfigService.getWxpayConfig(group);
		String notify_url = config.getNotifyUrl();
		// 参数组
		String appid =  config.getAppid();
		String mch_id = config.getMchid();
		String secret = config.getSecret();
		
		String nonce_str = RandCharsUtils.getRandomString(16);

		String out_trade_no = GenerateCode.getUUID();
		
		// 参数：开始生成签名
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", appid);
		parameters.put("attach", attach);
		parameters.put("body", descr);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonce_str);
		parameters.put("notify_url", notify_url);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("spbill_create_ip", spbill_create_ip);
		parameters.put("total_fee", totalFee);
		parameters.put("trade_type", tradeType);
		
		String sign = WXSignUtils.createSign("UTF-8", parameters, secret);

		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(appid);
		unifiedorder.setAttach(attach);
		unifiedorder.setBody(descr);
		unifiedorder.setMch_id(mch_id);
		unifiedorder.setNonce_str(nonce_str);
		unifiedorder.setNotify_url(notify_url);
		unifiedorder.setOut_trade_no(out_trade_no);
		unifiedorder.setSpbill_create_ip(spbill_create_ip);
		unifiedorder.setTotal_fee(totalFee);
		unifiedorder.setTrade_type(tradeType);
		unifiedorder.setSign(sign);

		// 构造xml参数
		String xmlInfo = HttpXmlUtils.xmlInfo(unifiedorder);
		String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String method = "POST";

		String weixinPost = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();

		Map<String, Object> retMap = ParseXMLUtils.jdomParseXml(weixinPost);
		String return_code=retMap.get("return_code").toString();
		
		logger.debug("retMap={}",retMap);
		logger.debug("请求统一下单接口返回结果 ==return_code={}",return_code);
		if("FAIL".equals(return_code)){
			throw new I18nMessageException("-1","支付失败,"+retMap.get("return_msg"));
		}
		//支付结果
		String result_code=retMap.get("result_code").toString();
		logger.debug("请求统一下单接口返回支付结果 ==return_code={}",result_code);
		if("FAIL".equals(result_code)){
			String err_code=retMap.get("err_code").toString();//返回错误码
			String err_code_des=retMap.get("err_code_des").toString();//错误描述信息
			/**
			 * NOTENOUGH 余额不足
			 * ORDERPAID 商户订单已支付
			 * ORDERCLOSED 订单已关闭
			 * OUT_TRADE_NO_USED 商户订单号重复
			 */
			if("NOTENOUGH".equals(err_code)||
					"ORDERCLOSED".equals(err_code)||
					"ORDERPAID".equals(err_code)||
					"OUT_TRADE_NO_USED".equals(err_code)){
				throw new I18nMessageException("-1",err_code_des);
			}
			throw new I18nMessageException("-1","支付失败");
		}
		
		PayOrderEntity order = new PayOrderEntity();
		order.setAppid(appid);
		order.setAttach(attach);
		order.setBody(descr);
		order.setCreateTime(new Date());
		order.setCreateUser(userId);
		order.setGroup(group);
		order.setMchId(mch_id);
		order.setNotifyUrl(notify_url);
		order.setOrderNo(orderNo);
		order.setOutOrderNo(out_trade_no);
		order.setPayWay("WX");
		order.setSpbillCreateIp(spbill_create_ip);
		order.setStatus(0);
		order.setTotalFee(new BigDecimal(totalFee).divide(new BigDecimal(100)));
		order.setRechargeFee(rechargeFee);
		order.setTradeType(tradeType);
		order.setScene(scene);
		payOrderService.insert(order);
		
		nonce_str = RandCharsUtils.getRandomString(16);
		String prepay_id=retMap.get("prepay_id").toString();
		SortedMap<Object, Object> parameters1 = new TreeMap<Object, Object>();
		parameters1.put("appid", appid);
		parameters1.put("partnerid", mch_id);
		parameters1.put("prepayid", prepay_id);
		parameters1.put("package", "Sign=WXPay");
		parameters1.put("noncestr", nonce_str);
		long timetoken = System.currentTimeMillis() / 1000;
		parameters1.put("timestamp", timetoken + "");
		parameters1.put("package", "prepay_id="+prepay_id);
		parameters1.put("signType", "MD5");
		
		String ios_resign = WXSignUtils.createSign("UTF-8", parameters1,secret);
		parameters1.put("appid", appid);
		String and_resign = WXSignUtils.createSign("UTF-8", parameters1,secret);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("appid", appid);
		ret.put("partnerid", mch_id);
		ret.put("prepayid", prepay_id);
		ret.put("noncestr", nonce_str);
		ret.put("timestamp", timetoken + "");
		ret.put("iossign", ios_resign);
		ret.put("andsign", and_resign);
		return new ResponseMessage(ret);
	}
	
	/**
	 * 小程序支付退款
	 * 
	 * @return JsonObject
	 * @throws Exception
	 */
	@RequestMapping(value="/wxpay/pay/wxAppRefund", method = RequestMethod.POST)
	ResponseMessage wxAppRefund(@RequestParam("orderNo")String orderNo,
			@RequestParam("refundDesc")String refundDesc,
			@RequestParam(value="refundFee",required=false)Integer refundFee) {
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
		return ResponseMessage.ok(wxpayGatewayService.refund(order, refundFee, refundDesc));
	}
	
	ResponseMessage wxAppRefund(Unifierefund unifiedorder) {
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
		
		return new ResponseMessage();
	}
	
	@RequestMapping(value="/wxpay/notify")
	String notify(@RequestBody String body) {
		PayOrderEntity order = null;
		try{
			// 微信异步通知 信息为空
			if (StringUtils.isEmpty(body)) {
				throw new I18nMessageException("-1", "未获取到微信返回的结果");
			}
			SortedMap<Object, Object> wxPayResult = JdomParseXmlUtils
					.getWXPayResultForMap(body);
			if (!"SUCCESS".equalsIgnoreCase((String)wxPayResult.get("return_code"))) {
				throw new I18nMessageException("-1", "交易失败");
			}
			String mch_id = (String) wxPayResult.get("mch_id");
			logger.debug("mch_id={}",mch_id);
			WxpayConfig config = wxpayConfigService.getWxpayConfigByMchid(mch_id);
			// 参数组
			String account = config.getGroup();
			String secret = config.getSecret();
			
			String out_trade_no = (String)wxPayResult.get("out_trade_no");
			logger.debug("account={},secret={}",account,secret);
			
			String sign = WXSignUtils.createSign("UTF-8", wxPayResult, secret);
			
			// 签名成功
			if (sign.equals(wxPayResult.get("sign"))) {
				Long buyTime = DateUtils.parseDate((String)wxPayResult.get("time_end"), new String[]{"yyyyMMddHHmmss"}).getTime();
				BigDecimal total_fee = new BigDecimal((String)wxPayResult.get("total_fee")).divide(new BigDecimal("100"));
				String tradeNo = (String)wxPayResult.get("transaction_id");
				
				order = payOrderService.selectOne(new EntityWrapper<PayOrderEntity>().eq("out_order_no", out_trade_no));
				
				String queue = null;
				if(StringUtils.isNotBlank(config.getQueueName())){
					queue = config.getQueueName();
				}
				return payOrderService.notify(out_trade_no, total_fee, tradeNo, buyTime, queue, "wxpay");
			}
			logger.error("[wxpay] notify.error {}","sign failure");
			query(out_trade_no, account, "1");
			return "success";
		}catch(I18nMessageException e){
			e.printStackTrace();
			if(order == null){
				logger.error("[wxpay] notify.error {}",e.getInfo());
			}else{
				logger.error("[wxpay] notify.error {} order={}",e.getInfo(),new Gson().toJson(order));
			}
		}catch(Exception e){
			e.printStackTrace();
			if(order == null){
				logger.error("[wxpay] notify.error {}",e.getMessage());
			}else{
				logger.error("[wxpay] notify.error {} order={}", e.getMessage(), new Gson().toJson(order));
			}
		}
		return "fail";
	}
	
	@RequestMapping(value="/wxpay/query", method = RequestMethod.GET)
	ResponseMessage query(@RequestParam("out_trade_no")String out_trade_no, @RequestParam("account")String account,
			@RequestParam(value="send",required=false)String send) {
		// 参数组
		
		WxpayConfig config = wxpayConfigService.getWxpayConfig(account);
		String appid =  config.getAppid();
		String mch_id = config.getMchid();
		String secret = config.getSecret();
		
		String nonce_str = RandCharsUtils.getRandomString(16);

		// 参数：开始生成签名
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonce_str);
		parameters.put("out_trade_no", out_trade_no);
		
		String sign = WXSignUtils.createSign("UTF-8", parameters, secret);

		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(appid);
		unifiedorder.setMch_id(mch_id);
		unifiedorder.setNonce_str(nonce_str);
		unifiedorder.setOut_trade_no(out_trade_no);
		unifiedorder.setSign(sign);

		// 构造xml参数
		String xmlInfo = HttpXmlUtils.queryXmlInfo(unifiedorder);
		String wxUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
		String method = "POST";

		logger.debug("xmlInfo={}",xmlInfo);
		String weixinPost = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();

		Map<String, Object> retMap = ParseXMLUtils.jdomParseXml(weixinPost);
		String return_code=retMap.get("return_code").toString();
		
		logger.debug("retMap={}",retMap);
		logger.debug("请求统一下单接口返回结果 ==return_code={}",return_code);
		if("FAIL".equals(return_code)){
			throw new I18nMessageException("-1","支付失败,"+retMap.get("return_msg"));
		}
		//支付结果
		String result_code=retMap.get("result_code").toString();
		logger.debug("请求统一下单接口返回支付结果 ==return_code={}",result_code);
		if("FAIL".equals(result_code)){
			String err_code = retMap.get("err_code").toString();//返回错误码
			String err_code_des = retMap.get("err_code_des").toString();//错误描述信息
			/**
			 * NOTENOUGH 余额不足
			 * ORDERPAID 商户订单已支付
			 * ORDERCLOSED 订单已关闭
			 * OUT_TRADE_NO_USED 商户订单号重复
			 */
			if("NOTENOUGH".equals(err_code)||
					"ORDERCLOSED".equals(err_code)||
					"ORDERPAID".equals(err_code)||
					"OUT_TRADE_NO_USED".equals(err_code)){
				throw new I18nMessageException("-1",err_code_des);
			}
			throw new I18nMessageException("-1","支付失败");
		}
		if(StringUtils.isNotBlank(send)){
			String total_fee = retMap.get("total_fee").toString();
			String tradeNo = retMap.get("transaction_id").toString(),
					time_end = retMap.get("time_end").toString();
			try {
				BigDecimal totalFee = new BigDecimal(total_fee).divide(new BigDecimal("100"));
				Long buyTime = DateUtils.parseDate(time_end, new String[]{"yyyyMMddHHmmss"}).getTime();
				
				payOrderService.notify(out_trade_no, totalFee, tradeNo, buyTime, config.getQueueName(),"wxpay");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ResponseMessage(retMap);
	}
	
}
