package com.lebaoxun.portal.rest.account;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.entity.UserAddressEntity;
import com.lebaoxun.modules.account.service.IUserAddressService;
import com.lebaoxun.portal.rest.BaseController;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;

@RestController
public class AddressController extends BaseController{
	
	@Resource
	private IUserAddressService userAddressService;
	
	@Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;
	
	/**
     * 列表
     */
    @RequestMapping("/account/address/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
    	params.put("userId", oauth2SecuritySubject.getCurrentUser());
    	return userAddressService.list(params);
    }
    
    @RequestMapping("/account/address/defaultUse")
    ResponseMessage defaultUse(){
    	return userAddressService.defaultUse(oauth2SecuritySubject.getCurrentUser());
    }


    /**
     * 信息
     */
    @RequestMapping("/account/address/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
    	return userAddressService.info(id, oauth2SecuritySubject.getCurrentUser());
    }

    /**
     * 保存
     */
    @RequestMapping("/account/address/save")
    ResponseMessage save(@RequestBody UserAddressEntity userAddress){
    	userAddress.setId(0);
    	userAddress.setUserId(oauth2SecuritySubject.getCurrentUser());
    	return userAddressService.save(oauth2SecuritySubject.getCurrentUser(), userAddress);
    }

    /**
     * 修改
     */
    @RequestMapping("/account/address/update")
    ResponseMessage update(@RequestBody UserAddressEntity userAddress){
    	userAddress.setUserId(oauth2SecuritySubject.getCurrentUser());
    	return userAddressService.update(oauth2SecuritySubject.getCurrentUser(), userAddress);
    }

    /**
     * 删除
     */
    @RequestMapping("/account/address/delete")
    ResponseMessage delete(@RequestParam("id")Long id){
    	return userAddressService.delete(oauth2SecuritySubject.getCurrentUser(), new Long[]{id});
    }
}
