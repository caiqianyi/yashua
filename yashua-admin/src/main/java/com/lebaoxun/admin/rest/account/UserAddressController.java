package com.lebaoxun.admin.rest.account;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.entity.UserAddressEntity;
import com.lebaoxun.modules.account.service.IUserAddressService;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;



/**
 * 用户地址
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-26 10:20:24
 */
@RestController
public class UserAddressController {
    @Autowired
    private IUserAddressService userAddressService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    /**
     * 列表
     */
    @RequestMapping("/account/useraddress/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return userAddressService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/account/useraddress/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
        return userAddressService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/account/useraddress/save")
    ResponseMessage save(@RequestBody UserAddressEntity userAddress){
        return userAddressService.save(oauth2SecuritySubject.getCurrentUser(),userAddress);
    }

    /**
     * 修改
     */
    @RequestMapping("/account/useraddress/update")
    ResponseMessage update(@RequestBody UserAddressEntity userAddress){
        return userAddressService.update(oauth2SecuritySubject.getCurrentUser(),userAddress);
    }

    /**
     * 删除
     */
    @RequestMapping("/account/useraddress/delete")
    ResponseMessage delete(@RequestBody Integer[] ids){
        return userAddressService.delete(oauth2SecuritySubject.getCurrentUser(),ids);
    }

}
