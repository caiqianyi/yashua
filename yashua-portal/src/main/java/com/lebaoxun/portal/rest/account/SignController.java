package com.lebaoxun.portal.rest.account;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.sign.service.ISignLogService;
import com.lebaoxun.portal.rest.BaseController;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;

@RestController
public class SignController extends BaseController {
	@Resource
	private ISignLogService signLogService;
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	/**
     * 签到
     * @param userId 签到用户ID
     * @param groupId 奖品分类
     * @return
     */
    @RequestMapping("/sign/in")
    ResponseMessage signIn(){
    	return signLogService.signIn(oauth2SecuritySubject.getCurrentUser(), "score");
    }
}
