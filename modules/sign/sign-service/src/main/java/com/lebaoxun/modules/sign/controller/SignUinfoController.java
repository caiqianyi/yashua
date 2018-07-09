package com.lebaoxun.modules.sign.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.sign.entity.SignUinfoEntity;
import com.lebaoxun.modules.sign.service.SignUinfoService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.soa.core.redis.lock.RedisLock;


/**
 * 签到用户表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */
@RestController
public class SignUinfoController {
    @Autowired
    private SignUinfoService signUinfoService;

    /**
     * 列表
     */
    @RequestMapping("/sign/signuinfo/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = signUinfoService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/sign/signuinfo/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
		SignUinfoEntity signUinfo = signUinfoService.selectById(id);
        return ResponseMessage.ok().put("signUinfo", signUinfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/sign/signuinfo/save")
    @RedisLock(value="sign:signuinfo:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody SignUinfoEntity signUinfo){
		signUinfoService.insert(signUinfo);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/sign/signuinfo/update")
    @RedisLock(value="sign:signuinfo:update:lock:#arg0")
    ResponseMessage update(@RequestParam("adminId")Long adminId,@RequestBody SignUinfoEntity signUinfo){
		signUinfoService.updateById(signUinfo);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/sign/signuinfo/delete")
    @RedisLock(value="sign:signuinfo:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId")Long adminId,@RequestBody Integer[] ids){
		signUinfoService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}
