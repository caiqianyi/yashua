package com.lebaoxun.modules.account.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.lebaoxun.commons.utils.PageUtils;
import com.lebaoxun.modules.account.em.UserLogAction;
import com.lebaoxun.modules.account.entity.UserEntity;

/**
 * 用户表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    void save(Long adminId,UserEntity user);
    
    /**
     * 设置用户账户状态
     * @param userId
     * @param status
     */
    void lock(Long userId, Long adminId);
    
    /**
     * 修改密码
     * @param userId 用户ID
     * @param newPasswd 新密码（非加密）
     * @param adminId 操作人
     */
    void modifyPassword(Long userId, String newPasswd, Long adminId);
    
    /**
     * 修改账户金额
     * @param userId 用户ID
     * @param amount 变更数量
     * @param adminId 操作人
     * @param logType 带字母U开头，为用户本人操作产生的日志
     * @param descr 操作说明
     */
    void modifyBalance(Long userId,Integer amount, String descr, Long adminId);
    
    /**
     * 修改用户头像
     * @param userId
     * @param headimgurl
     */
    void modifyHeadimgurl(Long userId,String headimgurl);
    
    /**
     * 修改用户信息
     * @param userId
     * @param user
     * @param logType
     * @param adminId
     * @param descr
     */
    void modifyInfo(Long userId,UserEntity user, Long adminId,String descr);
    
    /**
     * 绑定账号
     * @param userId
     * @param account
     */
    void bindMobile(Long userId,String mobile, String password);
    
    /**
     * 绑定微信公众号openid
     * @param userId 用户ID
     * @param openid 微信openid
     */
    void bindOpenid(Long userId,String openid);
    
    /**
     * 微信公众号注册
     * @param userId
     * @param user
     * @param scope
     */
    void wechatOARegister(Long userId, UserEntity user);
    
    /**
     * 记录登录日志
     * @param userId
     * @param scope
     * @param logType
     * @param adjunctInfo
     * @param descr
     */
    void loginLog(Long userId,UserLogAction logType,String adjunctInfo,String descr);
    
    UserEntity recharge(Long userId,String adjunct_info,Long buyTime,String total_fee);
    
    UserEntity rechargeForSys(Long userId, String logType, String adjunctInfo, String total_fee, Long logTime);
}

