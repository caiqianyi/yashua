package com.lebaoxun.portal.rest.yashua;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.IUserService;
import com.lebaoxun.modules.yashua.service.IUserDataService;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
/**
 * 用户口气表
 */
@RestController
public class UserDataController {
	@Autowired
	private IUserDataService userDataService;
	@Resource
	private IUserService userService;
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	/**
     * 列表
     */
    @RequestMapping("/yashua/userdata/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
    	UserEntity ue = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
    	params.put("user_id", ue.getUserId());
        return userDataService.list(params);
    }
    
    /**
     * 获取历史口气数据
     */
    	@RequestMapping("/yashua/userdata/history/list/{id}")
    	ResponseMessage hlist(@RequestParam Map<String, Object> params,@PathVariable("id") Integer id){
    	UserEntity ue = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
    	params.put("user_id", ue.getUserId());
    	params.put("id", id);
    	params.put("limit",300);
        return userDataService.hlist(params);
    }
}
