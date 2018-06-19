package com.lebaoxun.admin.rest.account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.entity.UserLogEntity;
import com.lebaoxun.modules.account.service.IUserLogService;



/**
 * 
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:14:31
 */
@RestController
public class UserLogController {
    @Autowired
    private IUserLogService userLogService;

    /**
     * 列表
     */
    @RequestMapping("/account/userlog/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return userLogService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/account/userlog/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
        return userLogService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/account/userlog/save")
    ResponseMessage save(@RequestBody UserLogEntity userLog){
        return userLogService.save(userLog);
    }

    /**
     * 修改
     */
    @RequestMapping("/account/userlog/update")
    ResponseMessage update(@RequestBody UserLogEntity userLog){
        return userLogService.update(userLog);
    }

    /**
     * 删除
     */
    @RequestMapping("/account/userlog/delete")
    ResponseMessage delete(@RequestBody Integer[] ids){
        return userLogService.delete(ids);
    }

}
