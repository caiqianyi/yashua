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
import com.lebaoxun.modules.yashua.service.IUserDeviceService;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;


/**
 * 用户设备表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-23 16:42:44
 */
@RestController
public class UserDeviceController {
    @Autowired
    private IUserDeviceService userDeviceService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    @Resource
    private IUserService userService;
    /**
     * 列表
     */
    @RequestMapping("/yashua/device/mylist")
    ResponseMessage list(@RequestParam Map<String, Object> params){
    	UserEntity ue = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
    	params.put("account", ue.getAccount());
        return userDeviceService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/yashua/device/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
        return userDeviceService.info(id, oauth2SecuritySubject.getCurrentUser());
    }

    /**
     * 绑定
     */
    @RequestMapping("/yashua/device/bind")
    ResponseMessage bind(@RequestParam("identity") String identity){
    	UserEntity ue = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
        return userDeviceService.bind(ue.getAccount(), identity, 1);
    }

    /**
     * 解绑
     */
    @RequestMapping("/yashua/device/unbind")
    ResponseMessage unbind(@RequestParam("identity") String identity){
    	UserEntity ue = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
        return userDeviceService.unbind(ue.getAccount(), identity);
    }

}
