package com.lebaoxun.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lebaoxun.modules.account.entity.UserAddressEntity;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.UserAddressService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


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
    private UserAddressService userAddressService;

    /**
     * 列表
     */
    @RequestMapping("/account/useraddress/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = userAddressService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/account/useraddress/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id,
    		@RequestParam(value="userId",required=false) Long userId){
		UserAddressEntity userAddress = null;
		if(userId == null){
			userAddress = userAddressService.selectById(id);
		}else{
			userAddress = userAddressService.selectOne( new EntityWrapper<UserAddressEntity>().eq("user_id", userId).eq("id", id));
		}
        return ResponseMessage.ok().put("userAddress", userAddress);
    }

    /**
     * 保存
     */
    @RequestMapping("/account/useraddress/save")
    @RedisLock(value="account:useraddress:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody UserAddressEntity userAddress){
		userAddressService.addAddress(userAddress);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/account/useraddress/update")
    @RedisLock(value="account:useraddress:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody UserAddressEntity userAddress){
		userAddressService.modifyAddress(userAddress);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/account/useraddress/delete")
    @RedisLock(value="account:useraddress:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Long[] ids){
		userAddressService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
