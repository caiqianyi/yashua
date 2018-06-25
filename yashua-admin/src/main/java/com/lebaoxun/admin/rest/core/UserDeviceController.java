package com.lebaoxun.admin.rest.core;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.yashua.entity.UserDeviceEntity;
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

    /**
     * 列表
     */
    @RequestMapping("/yashua/userdevice/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return userDeviceService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/yashua/userdevice/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
        return userDeviceService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/yashua/userdevice/save")
    ResponseMessage save(@RequestBody UserDeviceEntity userDevice){
        return userDeviceService.save(oauth2SecuritySubject.getCurrentUser(),userDevice);
    }

    @RequestMapping("/yashua/userdevice/addBatch")
    ResponseMessage addBatch(@RequestBody List<UserDeviceEntity> userDevices){
        return userDeviceService.addBatch(oauth2SecuritySubject.getCurrentUser(), userDevices);
    }
    /**
     * 修改
     */
    @RequestMapping("/yashua/userdevice/update")
    ResponseMessage update(@RequestBody UserDeviceEntity userDevice){
        return userDeviceService.update(oauth2SecuritySubject.getCurrentUser(),userDevice);
    }

    /**
     * 删除
     */
    @RequestMapping("/yashua/userdevice/delete")
    ResponseMessage delete(@RequestBody Integer[] ids){
        return userDeviceService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
