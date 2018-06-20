package com.lebaoxun.modules.account.controller;

import java.util.Arrays;
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
import com.lebaoxun.commons.utils.PwdUtil;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.UserService;



/**
 * 用户表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/account/user/list")
    ResponseMessage list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);
        return ResponseMessage.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/account/user/info/{id}")
    ResponseMessage info(@PathVariable("id") String id){
		UserEntity user = userService.selectById(id);
        return ResponseMessage.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/account/user/save")
    ResponseMessage save(@RequestBody UserEntity user){
		userService.insert(user);
        return ResponseMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/account/user/update")
    ResponseMessage update(@RequestBody UserEntity user){
		userService.updateById(user);
        return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/account/user/delete")
    ResponseMessage delete(@RequestBody String[] ids){
		userService.deleteBatchIds(Arrays.asList(ids));
        return ResponseMessage.ok();
    }
    
    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    @RequestMapping("/account/user/findByUserId")
    UserEntity findByUserId(@RequestParam("userId") Long userId){
		return userService.selectOne( new EntityWrapper<UserEntity>().eq("userId", userId));
	}
	
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
	@RequestMapping("/account/user/findByAccount")
	UserEntity findByAccount(@RequestParam("account") String account){
		return userService.selectOne( new EntityWrapper<UserEntity>().eq("account", account));
	}
	
	/**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
	@RequestMapping("/account/user/findByOpenid")
	UserEntity findByOpenid(@RequestParam("openid") String openid,
			@RequestParam(value="groupid",required=false) String groupid){
		return userService.selectOne( new EntityWrapper<UserEntity>().eq("openid", openid));
	}
	
	/**
     * 根据用户名，密码验证登录
     * @param username
     * @return
     */
	@RequestMapping("/account/user/login")
	UserEntity login(@RequestParam("username") String username,@RequestParam("password") String password){
		return userService.selectOne(new EntityWrapper<UserEntity>().eq("username", username).eq("password", PwdUtil.getMd5Password(username, password)));
	}

}
