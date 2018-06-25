package com.lebaoxun.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.commons.utils.PwdUtil;
import com.lebaoxun.modules.account.em.UserLogAction;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.UserService;
import com.lebaoxun.soa.core.redis.lock.RedisLock;



/**
 * 用户表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */
@RestController
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public HttpServletRequest request;
	
    @Autowired
    private UserService userService;

    @Value("${security.md5.password}")
	private String passwdSecret;
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
    @RedisLock(value="account:user:save:lock:#arg0")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody UserEntity user){
		userService.save(adminId,user);
        return ResponseMessage.ok();
    }

    /**
     * 设置用户账户状态
     * @param adminId 管理员
     * @param userId 用户ID
     * @param scope 获取管理员host使用
     * @return
     */
    @RequestMapping("/account/user/disabled")
    @RedisLock(value="account:user:disabled:lock:#arg0:#arg1")
    ResponseMessage disabled(@RequestParam(value="adminId")Long adminId,
    		@RequestParam(value="userId")Long userId){
    	 userService.lock(userId, adminId);
    	 return ResponseMessage.ok();
    }
    
    /**
     * 修改密码
     * @param userId 用户ID
     * @param newPasswd 新密码（非加密）
     * @param adminId 操作人
     */
    @RequestMapping("/account/user/modifyPassword")
    @RedisLock(value="account:user:modifyPassword:lock:#arg0")
    ResponseMessage modifyPassword(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="newPasswd") String newPasswd,
    		@RequestParam(value="adminId",required=false) Long adminId){
    	userService.modifyPassword(userId, newPasswd, adminId);
    	return ResponseMessage.ok();
    }
    
    /**
     * 修改账户金额
     * @param userId 用户ID
     * @param amount 变更数量
     * @param adminId 操作人
     * @param logType 带字母U开头，为用户本人操作产生的日志
     * @param descr 操作说明
     */
    @RequestMapping("/account/user/modifyBalance")
    @RedisLock(value="account:user:modifyBalance:lock:#arg0")
    ResponseMessage modifyBalance(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="amount") Integer amount,
    		@RequestParam(value="adminId",required=false) Long adminId,
    		@RequestParam(value="descr",required=false) String descr){
    	userService.modifyBalance(userId, amount, descr, adminId);
    	return ResponseMessage.ok();
    }
    
    /**
     * 修改用户信息
     * @param userId
     * @param user
     * @param logType
     * @param adminId
     * @param descr
     */
    @RequestMapping("/account/user/modifyInfo")
    @RedisLock(value="account:user:modifyInfo:lock:#arg0")
    ResponseMessage modifyInfo(@RequestParam(value="userId") Long userId,
    		@RequestBody UserEntity user, 
    		@RequestParam(value="adminId",required=false) Long adminId,
    		@RequestParam(value="descr",required=false) String descr){
    	userService.modifyInfo(userId, user, adminId, descr);
    	return ResponseMessage.ok();
    }
    
    /**
     * 绑定账号
     * @param userId
     * @param account
     */
    @RequestMapping("/account/user/bindMobile")
    @RedisLock(value="account:user:bindMobile:lock:#arg0")
    ResponseMessage bindMobile(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="mobile") String mobile, 
    		@RequestParam(value="password") String password){
    	userService.bindMobile(userId, mobile, password);
    	return ResponseMessage.ok();
    }
    
    /**
     * 绑定微信公众号openid
     * @param userId 用户ID
     * @param openid 微信openid
     */
    @RequestMapping("/account/user/bindOpenid")
    @RedisLock(value="account:user:bindOpenid:lock:#arg0")
    ResponseMessage bindOpenid(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="openid") String openid){
    	userService.bindOpenid(userId, openid);
    	return ResponseMessage.ok();
    }
    
    /**
     * 微信公众号注册
     * @param userId
     * @param user
     * @param scope
     */
    @RequestMapping("/account/user/wechatOARegister")
    @RedisLock(value="account:user:wechatOARegister:lock:#arg0")
    ResponseMessage wechatOARegister(@RequestParam(value="userId") Long userId, 
    		@RequestBody UserEntity user){
    	userService.wechatOARegister(userId, user);
    	return ResponseMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/account/user/delete")
    @RedisLock(value="account:user:delete:lock:#arg0")
    ResponseMessage delete(@RequestParam("adminId") Long adminId,@RequestBody String[] ids){
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
		return userService.selectOne(new EntityWrapper<UserEntity>().eq("username", username).eq("password", PwdUtil.getMd5Password(passwdSecret, username, password)));
	}

	/**
     * 记录登录日志
     * @param userId
     * @param scope
     * @param logType
     * @param adjunctInfo
     * @param descr
     */
	@RequestMapping("/account/user/loginLog")
    @RedisLock(value="account:user:loginLog:lock:#arg0")
	ResponseMessage loginLog(@RequestParam("userId") Long userId,
			@RequestParam(value="logType") UserLogAction logType,
			@RequestParam(value="adjunctInfo") String adjunctInfo,
			@RequestParam(value="descr") String descr){
		userService.loginLog(userId, logType, adjunctInfo, descr);
		return ResponseMessage.ok();
    }
}
