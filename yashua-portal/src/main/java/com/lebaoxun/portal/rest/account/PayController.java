package com.lebaoxun.portal.rest.account;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.CommonUtil;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.IUserService;
import com.lebaoxun.modules.pay.service.IWxPayService;
import com.lebaoxun.portal.rest.BaseController;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;

@RestController
public class PayController extends BaseController{	
	
	@Resource
	private IWxPayService wxPayService;
	
	@Resource
    private IUserService userService;
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	/**
	 * 微信小程序充值
	 * 
	 * @return JsonObject
	 * @throws Exception
	 */
	@RequestMapping(value="/recharge/wxpay/wechatapp/payment")
	ResponseMessage payment(@RequestParam("totalFee")BigDecimal totalFee,
			@RequestParam(name="wxopenid",required=false) String wxopenid){
		Long userId = oauth2SecuritySubject.getCurrentUser();
		UserEntity user = userService.findByUserId(userId);
		if(StringUtils.isBlank(wxopenid)){
			wxopenid = user.getOpenid();
		}
		
		if(StringUtils.isBlank(wxopenid)){
			throw new I18nMessageException("-1","请在微信公众号菜单进入支付");
		}
		
		String group = "yashua",
				attach = "",
				descr = "充值",
				orderNo = GenerateCode.getUUID(),
				spbill_create_ip = CommonUtil.getIp2(request),
				scene = "recharge";
		BigDecimal rechargeFee = totalFee.setScale(2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal("10"));
		Integer fee = rechargeFee.multiply(new BigDecimal("100")).intValue();
		return wxPayService.payment(spbill_create_ip, orderNo, descr, fee, attach, group, wxopenid, userId, rechargeFee, scene);
	}
}