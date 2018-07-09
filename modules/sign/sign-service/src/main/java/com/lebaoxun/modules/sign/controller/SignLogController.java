package com.lebaoxun.modules.sign.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.sign.entity.SignLogEntity;
import com.lebaoxun.modules.sign.entity.SignUinfoEntity;
import com.lebaoxun.modules.sign.service.SignLogService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 签到记录表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */
@RestController
public class SignLogController {
    @Autowired
    private SignLogService signLogService;

    /**
     * 列表
     */
    @RequestMapping("/sign/signlog/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = signLogService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/sign/signlog/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
		SignLogEntity signLog = signLogService.selectById(id);
        return ResponseMessage.ok().put("signLog", signLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/sign/signlog/save")
    @RedisLock(value="sign:signlog:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody SignLogEntity signLog){
		signLogService.insert(signLog);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/sign/signlog/update")
    @RedisLock(value="sign:signlog:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody SignLogEntity signLog){
		signLogService.updateById(signLog);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/sign/signlog/delete")
    @RedisLock(value="sign:signlog:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids){
		signLogService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }
    
    /**
     * 签到
     * @param userId 签到用户ID
     * @param groupId 奖品分类
     * @return
     */
    @RequestMapping("/sign/in")
    @RedisLock(value="sign:signlog:signIn:lock:#arg0")
    ResponseMessage signIn(@RequestParam("userId")Long userId,
    		@RequestParam("groupId")String groupId){
    	return ResponseMessage.ok(signLogService.signIn(userId, groupId));
    }

}
