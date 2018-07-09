package com.lebaoxun.modules.sign.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.sign.entity.SignAwardEntity;
import com.lebaoxun.modules.sign.service.SignAwardService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 签到奖励规则表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */
@RestController
public class SignAwardController {
    @Autowired
    private SignAwardService signAwardService;

    /**
     * 列表
     */
    @RequestMapping("/sign/signaward/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = signAwardService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/sign/signaward/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
		SignAwardEntity signAward = signAwardService.selectById(id);
        return ResponseMessage.ok().put("signAward", signAward);
    }

    /**
     * 保存
     */
    @RequestMapping("/sign/signaward/save")
    @RedisLock(value="sign:signaward:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody SignAwardEntity signAward){
		signAwardService.insert(signAward);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/sign/signaward/update")
    @RedisLock(value="sign:signaward:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody SignAwardEntity signAward){
		signAwardService.updateById(signAward);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/sign/signaward/delete")
    @RedisLock(value="sign:signaward:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids){
		signAwardService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
