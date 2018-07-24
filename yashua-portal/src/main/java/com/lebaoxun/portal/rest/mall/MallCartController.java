package com.lebaoxun.portal.rest.mall;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.service.IMallCartService;
import com.lebaoxun.portal.rest.BaseController;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;

@RestController
public class MallCartController extends BaseController{
	
	@Resource
	private IMallCartService mallCartService;
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	@RequestMapping("/mall/cart/sync")
    ResponseMessage sync(@RequestBody List<MallCartEntity> list){
		return mallCartService.sync(oauth2SecuritySubject.getCurrentUser(), list);
	}
	
	@RequestMapping("/mall/cart/mylist")
    ResponseMessage mylist(){
		return mallCartService.findByUser(oauth2SecuritySubject.getCurrentUser());
	}
	
	@RequestMapping("/mall/cart/product/list")
	ResponseMessage list(@RequestBody Long[] ids){
		return mallCartService.queryByProductSpecId(ids);
	}
	
	
}
