package com.lebaoxun.sms.config;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lebaoxun.commons.utils.Assert;
import com.lebaoxun.commons.utils.MD5;
import com.lebaoxun.sms.core.SMSGateway;
import com.lebaoxun.sms.core.SMSGatewayClient;
import com.lebaoxun.sms.service.ISMSClientService;
import com.lebaoxun.sms.service.ISMSTemplateService;
/**
 * 容联 云通信 短信发送
 * @author caiqianyi 2017.9.25
 *
 */
@Component("FhwjSMSGatewayClient")
public class FhwjSMSGatewayClient extends SMSGatewayClient {
	
	private Logger logger = LoggerFactory.getLogger(FhwjSMSGatewayClient.class);
	
	@Resource
	private ISMSClientService smsClientService;
	
	@Resource
	private ISMSTemplateService smsTemplateService;
	
	@Override
	public boolean doSend(String mobile, String template_id,
			String cst_id,String ...datas) {
		
		String passwd = MD5.md5("Steven260809");
		String seed = DateFormatUtils.format(new Date(), "yyyyMMddHHmmSS");
		String key = MD5.md5(passwd+seed);
		//烽火万家短信网关
		String gatewayName = "fhwj";
		SMSGateway config = new SMSGateway();
		config.setCharset("GBK");
		config.setGatewayName(gatewayName);
		config.setJson(false);
		config.setMethod(RequestMethod.GET);
		config.setSignature("魔牙科技");
		config.setSuccessText("success");
		config.setUrl("http://210.51.191.35:8080/eums/sms/send.do?name=xxznhy&seed="+seed+"&key="+key+"&dest=%s&content=%s");
		
		String content = smsTemplateService.find(template_id);
		Assert.notEmpty(content, "10401" , "为没有找到短信模板");
		
		content = content.replace("#signature#", "【"+config.getSignature()+"】");
		if(content.indexOf("#vfcode#") > -1){
			String vfCode = smsClientService.refreshVfCode(cst_id, mobile);
			content =  content.replace("#vfcode#", vfCode);
		}
		if(datas != null){
			content = String.format(content, datas);
		}
		
		logger.debug("sms send content={}",content);
		return send(config, mobile, content);
	}
	
	@Override
	public boolean doSend(String appid, String mobile, String content) {
		return false;
	}

}
