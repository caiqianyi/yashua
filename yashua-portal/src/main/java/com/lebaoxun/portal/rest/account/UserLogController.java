package com.lebaoxun.portal.rest.account;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.service.IUserLogService;
import com.lebaoxun.portal.rest.BaseController;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;

@RestController
public class UserLogController extends BaseController{
	@Resource
	private IUserLogService userLogService;
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	/**
     * 查询账户交易记录
     * @param userId
     * @param size
     * @param offset
     * @return
     */
    @RequestMapping("/account/log/mylist")
    ResponseMessage mylist(@RequestParam(value="size",required=false) Integer size, 
    		@RequestParam(value="offset",required=false) Integer offset){
    	return userLogService.findAccountLogByUserId(oauth2SecuritySubject.getCurrentUser(), size, offset);
    }
}
