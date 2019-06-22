package com.lebaoxun.modules.account.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.account.entity.UserAddressEntity;
import com.lebaoxun.modules.account.entity.UserMessageEntity;
import com.lebaoxun.modules.account.service.UserMessageService;
import com.lebaoxun.soa.core.redis.lock.RedisLock;



/**
 * 用户消息
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-20 15:44:50
 */
@RestController
public class UserMessageController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMessageService userMessageService;

    /**
     * 列表
     */
    @RequestMapping("/account/usermessage/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = userMessageService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/account/usermessage/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id,
    		@RequestParam(value="userId",required=false) Long userId){
		UserMessageEntity userMessage = userMessageService.selectById(id);
		
		if(userId == null){
			userMessage = userMessageService.selectById(id);
		}else{
			userMessage = userMessageService.selectOne( new EntityWrapper<UserMessageEntity>().eq("user_id", userId).eq("id", id));
		}
        return ResponseMessage.ok().put("userMessage", userMessage);
    }
    
    @RequestMapping("/account/usermessage/findInformByUserId")
    ResponseMessage findInformByUserId(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="size",required=false) Integer size, 
    		@RequestParam(value="offset",required=false) Integer offset){
    	return ResponseMessage.ok(userMessageService.findInformByUserId(userId, size, offset));
    }

    @RequestMapping("/account/usermessage/findOneInformByUserId")
    ResponseMessage findOneInformByUserId(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="id") long id){
    	return ResponseMessage.ok(userMessageService.findOneInformByUserId(userId, id));
	}

    /**
     * 保存
     */
    @RequestMapping("/account/usermessage/save")
    @RedisLock(value="account:usermessage:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId") Long adminId,
    		@RequestBody UserMessageEntity userMessage){
		//userMessageService.insert(userMessage);
    	userMessageService.create(userMessage);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/account/usermessage/update")
    @RedisLock(value="account:usermessage:save:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId") Long adminId,
    		@RequestBody UserMessageEntity userMessage){
		userMessageService.updateById(userMessage);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/account/usermessage/delete")
    ResponseMessage delete(@RequestParam("adminId") Long adminId,
    		@RequestBody Long[] ids){
    	userMessageService.delete(ids);
		userMessageService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

    @RequestMapping("/account/usermessage/messS")
    int messS(@RequestParam(value="userId") Long userId){
		return userMessageService.messageS(userId);
    	
    }
}
