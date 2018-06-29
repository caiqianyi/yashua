package com.lebaoxun.modules.yashua.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.yashua.entity.UserDeviceEntity;
import com.lebaoxun.modules.yashua.service.UserDeviceService;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


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
    private UserDeviceService userDeviceService;

    /**
     * 列表
     */
    @RequestMapping("/yashua/userdevice/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = userDeviceService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/yashua/userdevice/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id,
    		@RequestParam(value="userId",required=false) Long userId){
    	UserDeviceEntity userDevice = null;
		if(userId == null){
			userDevice = userDeviceService.selectById(id);
		}else{
			userDevice = userDeviceService.selectOne( new EntityWrapper<UserDeviceEntity>().eq("user_id", userId).eq("id", id));
		}
        return ResponseMessage.ok().put("userDevice", userDevice);
    }

    /**
     * 保存
     */
    @RequestMapping("/yashua/userdevice/save")
    @RedisLock(value="yashua:userdevice:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody UserDeviceEntity userDevice){
		userDeviceService.insert(userDevice);
        return ResponseMessage.ok();
    }
    
    /**
     * 保存
     */
    @RequestMapping("/yashua/userdevice/addBatch")
    @RedisLock(value="yashua:userdevice:addBatch:lock:#arg0")
    ResponseMessage addBatch(@RequestParam("adminId")Long adminId,@RequestBody List<UserDeviceEntity> userDevices){
    	userDeviceService.insertBatch(userDevices);
    	return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/yashua/userdevice/update")
    @RedisLock(value="yashua:userdevice:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody UserDeviceEntity userDevice){
		userDeviceService.updateById(userDevice);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/yashua/userdevice/delete")
    @RedisLock(value="yashua:userdevice:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids){
		userDeviceService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }
    
    /**
     * 绑定设备
     * @param account 用户ID
     * @param identity 设备ID
     * @param maxBindNum 最多绑定设备数
     * @return
     */
    @RequestMapping("/yashua/userdevice/bind")
    @RedisLock(value="yashua:userdevice:bind:lock:#arg0")
    ResponseMessage bind(@RequestParam("account")String account,
    		@RequestParam("identity") String identity,
    		@RequestParam(value="maxBindNum",required=false) Integer maxBindNum){
    	userDeviceService.bind(account, identity, maxBindNum);
    	return ResponseMessage.ok();
    }
    
    /**
     * 解除绑定
     * @param account 用户ID
     * @param identity 设备ID
     * @return
     */
    @RequestMapping("/yashua/userdevice/unbind")
    @RedisLock(value="yashua:userdevice:unbind:lock:#arg0")
    ResponseMessage unbind(@RequestParam("account")String account,
    		@RequestParam("identity") String identity){
    	userDeviceService.unbind(account, identity);
    	return ResponseMessage.ok();
    }

}
