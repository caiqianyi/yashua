package com.lebaoxun.modules.yashua.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.yashua.service.UserDataService;
import com.lebaoxun.soa.core.redis.lock.RedisLock;

/**
 * 用户口气表
 */
@RestController
public class UserDataController {
	@Autowired
	private UserDataService userDataService;
	/**
     * 列表
     */
    @RequestMapping("/yashua/userdata/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = userDataService.queryPage(params);
        return ResponseMessage.ok(page);
    }
    /**
     * 获取历史口气数据
     */
    @RequestMapping("/yashua/userdata/history/list")
   	ResponseMessage hlist(@RequestParam Map<String, Object> params){
    	 PageUtils page = userDataService.queryByConditgions(params);
         return ResponseMessage.ok(page);
    }
    /**
     * 保存口气数据
     */
    @RequestMapping("/yashua/userdata/save")
    @RedisLock(value="yashua:userdata:save:lock:#arg0")
    ResponseMessage save(@RequestParam("kouqi") Long kouqi,@RequestParam("user_id") Long user_id){
   	   userDataService.save(kouqi,user_id);
    return ResponseMessage.ok();
   }

}
