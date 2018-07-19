package com.lebaoxun.portal.rest.account.mall;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	ResponseMessage create(@RequestBody List<MallCartEntity> products){
		return mallOrderService.create(oauth2SecuritySubject.getCurrentUser(), products);
	}
}
