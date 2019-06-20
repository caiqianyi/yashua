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
import com.lebaoxun.modules.account.entity.UserMessageMidEntity;
import com.lebaoxun.modules.account.service.UserMessageMidService;
import com.lebaoxun.soa.core.redis.lock.RedisLock;



/**
 * 用户消息中间表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-20 15:44:50
 */
@RestController
public class UserMessageMidController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMessageMidService userMessageMidService;

    /**
     * 列表
     *
    @RequestMapping("/account/usermessagemid/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = userMessageMidService.queryPage(params);
        return ResponseMessage.ok(page);
    }
/

    /**
     * 信息
     */
    @RequestMapping("/account/usermessagemid/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id,
    		@RequestParam(value="userId",required=false) Long userId){
		UserMessageMidEntity userMessageMid = userMessageMidService.selectById(id);
        return ResponseMessage.ok().put("userMessage", userMessageMid);
    }

    /**
     * 保存
     */
    @RequestMapping("/account/usermessagemid/save")
    @RedisLock(value="account:usermessagemid:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId") Long adminId,
    		@RequestBody UserMessageMidEntity userMessageMid){
    	userMessageMidService.insert(userMessageMid);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/account/usermessagemid/update")
    @RedisLock(value="account:usermessagemid:save:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId") Long adminId,
    		@RequestBody UserMessageMidEntity userMessageMid){
    	userMessageMidService.updateById(userMessageMid);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/account/usermessagemid/delete")
    ResponseMessage delete(@RequestParam("adminId") Long adminId,
    		@RequestBody Integer[] ids){
    	userMessageMidService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
