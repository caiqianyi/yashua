package com.lebaoxun.modules.account.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.modules.account.em.UserLogAction;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.hystrix.UserServiceHystrix;

/**
 * 用户表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */

@FeignClient(value="account-service",fallback=UserServiceHystrix.class)
public interface IUserService {
	/**
     * 列表
     */
    @RequestMapping("/account/user/list")
    ResponseMessage list(@RequestParam Map<String, Object> params);
    
    @RequestMapping("/account/user/all")
    List<UserEntity> all();


    /**
     * 信息
     */
    @RequestMapping("/account/user/info/{id}")
    ResponseMessage info(@PathVariable("id") Long id);

    /**
     * 保存
     */
    @RequestMapping("/account/user/save")
    ResponseMessage save(@RequestParam("adminId")Long adminId,@RequestBody UserEntity user);

    /**
     * 设置用户账户状态
     * @param adminId 管理员
     * @param userId 用户ID
     * @param scope 获取管理员host使用
     * @return
     */
    @RequestMapping("/account/user/disabled")
    ResponseMessage disabled(@RequestParam(value="adminId")Long adminId,
    		@RequestParam(value="userId")Long userId);
    
    /**
     * 修改密码
     * @param userId 用户ID
     * @param newPasswd 新密码（非加密）
     * @param adminId 操作人
     */
    @RequestMapping("/account/user/modifyPassword")
    ResponseMessage modifyPassword(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="newPasswd") String newPasswd,
    		@RequestParam(value="adminId",required=false) Long adminId);
    
    /**
     * 修改账户金额
     * @param userId 用户ID
     * @param amount 变更数量
     * @param adminId 操作人
     * @param logType 带字母U开头，为用户本人操作产生的日志
     * @param descr 操作说明
     */
    @RequestMapping("/account/user/modifyBalance")
    ResponseMessage modifyBalance(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="amount") Integer amount,
    		@RequestParam(value="adminId",required=false) Long adminId,
    		@RequestParam(value="descr",required=false) String descr);
    
    /**
     * 修改用户信息
     * @param userId
     * @param user
     * @param logType
     * @param adminId
     * @param descr
     */
    @RequestMapping("/account/user/modifyInfo")
    ResponseMessage modifyInfo(@RequestParam(value="userId") Long userId,
    		@RequestBody UserEntity user, 
    		@RequestParam(value="adminId",required=false) Long adminId,
    		@RequestParam(value="descr",required=false) String descr);
    
    /**
     * 修改头像
     * @param userId 用户ID
     * @param headimgurl 用户头像地址
     * @return
     */
    @RequestMapping("/account/user/modifyHeadimgurl")
    ResponseMessage modifyHeadimgurl(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="headimgurl") String headimgurl);
    
    /**
     * 绑定账号
     * @param userId
     * @param account
     */
    @RequestMapping("/account/user/bindMobile")
    ResponseMessage bindMobile(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="mobile") String mobile, 
    		@RequestParam(value="password") String password);
    
    /**
     * 绑定微信公众号openid
     * @param userId 用户ID
     * @param openid 微信openid
     */
    @RequestMapping("/account/user/bindOpenid")
    ResponseMessage bindOpenid(@RequestParam(value="userId") Long userId,
    		@RequestParam(value="openid") String openid);
    
    /**
     * 微信公众号注册
     * @param userId
     * @param user
     * @param scope
     */
    @RequestMapping("/account/user/wechatOARegister")
    ResponseMessage wechatOARegister(@RequestParam(value="userId") Long userId, 
    		@RequestBody UserEntity user);

    /**
     * 删除
     */
    @RequestMapping("/account/user/delete")
    ResponseMessage delete(@RequestParam("adminId") Long adminId,@RequestBody String[] ids);
    
    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    @RequestMapping("/account/user/findByUserId")
    UserEntity findByUserId(@RequestParam("userId") Long userId);
	
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
	@RequestMapping("/account/user/findByAccount")
	UserEntity findByAccount(@RequestParam("account") String account);
	
	/**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
	@RequestMapping("/account/user/findByOpenid")
	UserEntity findByOpenid(@RequestParam("openid") String openid,
			@RequestParam(value="groupid",required=false) String groupid);
	
	/**
     * 根据用户名，密码验证登录
     * @param username
     * @return
     */
	@RequestMapping("/account/user/login")
	UserEntity login(@RequestParam("username") String username,@RequestParam("password") String password);

	/**
     * 记录登录日志
     * @param userId
     * @param scope
     * @param logType
     * @param adjunctInfo
     * @param descr
     */
	@RequestMapping("/account/user/loginLog")
	ResponseMessage loginLog(@RequestParam("userId") Long userId,
			@RequestParam(value="logType") UserLogAction logType,
			@RequestParam(value="adjunctInfo",required=false) String adjunctInfo,
			@RequestParam(value="descr",required=false) String descr);
}

