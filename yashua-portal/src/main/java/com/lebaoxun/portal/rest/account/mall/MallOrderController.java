package com.lebaoxun.portal.rest.account.mall;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.service.IMallOrderService;
import com.lebaoxun.portal.rest.BaseController;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;

@RestController
public class MallOrderController extends BaseController {
	@Resource
	private IMallOrderService mallOrderService;

	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

	@RequestMapping("/mall/order/create")
	ResponseMessage create(@RequestBody List<MallCartEntity> products) {
		return mallOrderService.create(oauth2SecuritySubject.getCurrentUser(),
				products);
	}
	
	@RequestMapping("/mall/order/delete")
    ResponseMessage delete(@RequestParam("orderNo")String orderNo){
		return mallOrderService.deleteByUser(oauth2SecuritySubject.getCurrentUser(), orderNo);
	}

	@RequestMapping("/mall/order/nopay")
	ResponseMessage nopay(@RequestParam("orderNo") String orderNo) {
		return ResponseMessage.ok(mallOrderService.selectOrderByOrderNo(
				oauth2SecuritySubject.getCurrentUser(), orderNo, 0));
	}

	@RequestMapping("/mall/order/confirmOrder")
	ResponseMessage confirmOrder(@RequestParam("orderNo") String orderNo,
			@RequestParam("invoiceType") Integer invoiceType,
			@RequestParam("invoiceTitle") String invoiceTitle,
			@RequestParam("address") String address,
			@RequestParam("consignee") String consignee,
			@RequestParam("mobile") String mobile) {
		return mallOrderService.confirmOrder(
				oauth2SecuritySubject.getCurrentUser(), orderNo, invoiceType,
				invoiceTitle, address, consignee, mobile);
	}
	
	@RequestMapping("/mall/order/mylist")
    ResponseMessage mylist(@RequestParam(value="status",required=false) Integer status, 
    		@RequestParam("size") Integer size, 
    		@RequestParam("offset") Integer offset){
		return mallOrderService.mylist(oauth2SecuritySubject.getCurrentUser(), status, 1, size, offset);
	}
}
