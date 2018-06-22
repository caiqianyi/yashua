package com.lebaoxun.modules.account.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */
@TableName("user_log")
public class UserLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户名
	 */
	private String account;
	/**
	 * 日志时间
	 */
	private Date createTime;
	/**
	 * 日志类型
	 */
	private String logType;
	/**
	 * 交易金额
	 */
	private Integer tradeMoney;
	/**
	 * 账户余额
	 */
	private Integer money;
	/**
	 * 日志说明
	 */
	private String descr;
	/**
	 * 日志参数
	 */
	private String adjunctInfo;

	/**
	 * 设置：
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public long getId() {
		return id;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：日志时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：日志时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：日志类型
	 */
	public void setLogType(String logType) {
		this.logType = logType;
	}
	/**
	 * 获取：日志类型
	 */
	public String getLogType() {
		return logType;
	}
	/**
	 * 设置：交易金额
	 */
	public void setTradeMoney(Integer tradeMoney) {
		this.tradeMoney = tradeMoney;
	}
	/**
	 * 获取：交易金额
	 */
	public Integer getTradeMoney() {
		return tradeMoney;
	}
	/**
	 * 设置：账户余额
	 */
	public void setMoney(Integer money) {
		this.money = money;
	}
	/**
	 * 获取：账户余额
	 */
	public Integer getMoney() {
		return money;
	}
	/**
	 * 设置：日志说明
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}
	/**
	 * 获取：日志说明
	 */
	public String getDescr() {
		return descr;
	}
	/**
	 * 设置：日志参数
	 */
	public void setAdjunctInfo(String adjunctInfo) {
		this.adjunctInfo = adjunctInfo;
	}
	/**
	 * 获取：日志参数
	 */
	public String getAdjunctInfo() {
		return adjunctInfo;
	}
	/**
	 * 获取：用户名
	 */
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
}
