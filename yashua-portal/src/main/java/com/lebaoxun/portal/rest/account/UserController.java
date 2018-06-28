package com.lebaoxun.portal.rest.account;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.GenerateCode;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.IUserService;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;
import com.lebaoxun.upload.service.IUploadLocalService;

@RestController
public class UserController {

	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	@Resource
    private IUserService userService;
    
    @Resource
    private IUploadLocalService uploadLocalService;
	
	/**
     * 信息
     */
    @RequestMapping("/account/user/info")
    ResponseMessage info(){
    	UserEntity user = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
    	user.setPassword(null);
        return ResponseMessage.ok(user);
    }

    /**
     * 保存
     */
    @RequestMapping("/account/user/save")
    ResponseMessage save(@RequestBody UserEntity user){
    	user.setBalance(0);
    	user.setLevel(0);
    	user.setSource("ADMIN");
    	user.setType("A");
    	user.setUserId(GenerateCode.gen16(9));
    	user.setMobile(user.getAccount());
        return userService.save(oauth2SecuritySubject.getCurrentUser(),user);
    }

    /**
     * 修改密码
     * @param userId 用户ID
     * @param newPasswd 新密码（非加密）
     * @param adminId 操作人
     */
    @RequestMapping("/account/user/modifyPassword")
    ResponseMessage modifyPassword(@RequestParam(value="newPasswd") String newPasswd){
    	return userService.modifyPassword(oauth2SecuritySubject.getCurrentUser(), newPasswd, oauth2SecuritySubject.getCurrentUser());
    }
    
    @RequestMapping("/account/user/modifyHeadimgurl")
    ResponseMessage modifyHeadimgurl(@RequestParam(value="imgStr") String imgStr){
    	long userId = oauth2SecuritySubject.getCurrentUser();
    	String namespace = "user/"+oauth2SecuritySubject.getCurrentUser();
    	ResponseMessage r = uploadLocalService.uploadImg("yashua", namespace, "png", false, imgStr);
    	if(!"0".equals(r.getErrcode())){
    		return r;
    	}
    	Map<String,String> data = (Map<String, String>) r.getData();
    	String headimgurl = data.get("uri");
    	r = userService.modifyHeadimgurl(userId, headimgurl);
    	if(!"0".equals(r.getErrcode())){
    		uploadLocalService.deleteFile("yashua", headimgurl);
    	}
    	r.setData(headimgurl);
    	return r;
    }
}
