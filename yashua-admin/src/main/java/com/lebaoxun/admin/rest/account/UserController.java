package com.lebaoxun.admin.rest.account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.IUserService;



/**
 * 用户表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:14:31
 */
@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 列表
     */
    @RequestMapping("/account/user/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        return userService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/account/user/info/{id}")
    ResponseMessage info(@PathVariable("id") String id){
        return userService.info(id);
    }

    /**
     * 保存
     */
    @RequestMapping("/account/user/save")
    ResponseMessage save(@RequestBody UserEntity user){
        return userService.save(user);
    }

    /**
     * 修改
     */
    @RequestMapping("/account/user/update")
    ResponseMessage update(@RequestBody UserEntity user){
        return userService.update(user);
    }

    /**
     * 删除
     */
    @RequestMapping("/account/user/delete")
    ResponseMessage delete(@RequestBody String[] ids){
        return userService.delete(ids);
    }

}
