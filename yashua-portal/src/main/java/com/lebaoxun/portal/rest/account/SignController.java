package com.lebaoxun.portal.rest.account;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.service.IUserLogService;
import com.lebaoxun.modules.sign.service.ISignLogService;
import com.lebaoxun.portal.rest.BaseController;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;

@RestController
public class SignController extends BaseController {
	@Resource
	private ISignLogService signLogService;
	
	@Resource
	private IUserLogService userLogService;
	
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
    	Long userId = oauth2SecuritySubject.getCurrentUser();
    	String time = DateFormatUtils.format(new Date(), "yyyyMM");
    	ResponseMessage rm = signLogService.signIn(userId, "score");
    	if("0".equals(rm.getErrcode())){
    		userLogService.zRange(userId, "SIGN_AWARD", time);
    	}
    	return rm;
    }
    
    @RequestMapping("/sign/findMonthInfoByUserId")
    ResponseMessage findMonthInfoByUserId(@RequestParam("time")String time){
    	Long userId = oauth2SecuritySubject.getCurrentUser();
    	Map<String,Object> ok = new HashMap<String,Object>();
    	ok.put("uInfo", signLogService.findMonthInfoByUserId(userId, time).getData());
    	ok.put("zRank", userLogService.zRank(userId, "SIGN_AWARD", time).getData());
    	return new ResponseMessage(ok);
    }
    
    /**
     * 签到
     * @param userId 签到用户ID
     * @param groupId 奖品分类
     * @return
     */
    @RequestMapping("/sign/uInfo")
    ResponseMessage uInfo(@RequestParam("time")String time){
    	Long userId = oauth2SecuritySubject.getCurrentUser();
    	return userLogService.zRank(userId, "SIGN_AWARD", time);
    }
    
    /**
     * 本周击败次数和所获积分
     */
    @RequestMapping("/sign/findWeekInfoByUserId")
    ResponseMessage findWeekInfoByUserId(){
    	Long userId = oauth2SecuritySubject.getCurrentUser();
    	return  ResponseMessage.ok(userLogService.zRankWeek(userId, "SIGN_AWARD"));
    }
}
