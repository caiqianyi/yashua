package com.lebaoxun.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.modules.account.entity.UserLogEntity;
import com.lebaoxun.modules.account.service.UserLogService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.exception.ResponseMessage;



/**
 * 
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */
@RestController
public class UserLogController {
    @Autowired
    private UserLogService userLogService;

    /**
     * 列表
     */
    @RequestMapping("/account/userlog/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = userLogService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/account/userlog/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
		UserLogEntity userLog = userLogService.selectById(id);
        return ResponseMessage.ok().put("userLog", userLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/account/userlog/save")
    ResponseMessage save(@RequestBody UserLogEntity userLog){
		userLogService.insert(userLog);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/account/userlog/update")
    ResponseMessage update(@RequestBody UserLogEntity userLog){
		userLogService.updateById(userLog);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/account/userlog/delete")
    ResponseMessage delete(@RequestBody Integer[] ids){
		userLogService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }

}