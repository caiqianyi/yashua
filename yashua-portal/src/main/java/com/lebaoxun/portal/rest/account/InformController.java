package com.lebaoxun.portal.rest.account;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.service.IUserMessageService;
import com.lebaoxun.portal.rest.BaseController;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;

@RestController
public class InformController extends BaseController{
	
	@Resource
	private IUserMessageService userMessageService;
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	/**
     * 列表
     */
    @RequestMapping("/account/inform/list")
    ResponseMessage list(@RequestParam(value="size",required=false) Integer size, 
    		@RequestParam(value="offset",required=false) Integer offset){
    	return userMessageService.findInformByUserId(oauth2SecuritySubject.getCurrentUser(), size, offset);
    }


    /**
     * 信息
     */
    @RequestMapping("/account/inform/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id){
    	return userMessageService.findOneInformByUserId(oauth2SecuritySubject.getCurrentUser(), id);
    }
    
}
