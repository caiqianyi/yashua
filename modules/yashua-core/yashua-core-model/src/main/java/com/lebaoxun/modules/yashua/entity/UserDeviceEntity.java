package com.lebaoxun.modules.yashua.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户设备表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-23 16:42:44
 */
@TableName("user_device")
public class UserDeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private long id;
	/**
	 * 用户账号
	 */
	private String account;
	/**
	 * 日志时间
	 */
	private Date createTime;
	/**
	 * 设备号
	 */
	private String identity;
	/**
	 * 物理地址
	 */
	private String mac;
	/**
	 * 图片
	 */
	private String icon;
	/**
	 * 名称
	 */
	private String name;

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
	 * 设置：用户账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：用户账号
	 */
	public String getAccount() {
		return account;
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
	 * 设置：设备号
	 */
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	/**
	 * 获取：设备号
	 */
	public String getIdentity() {
		return identity;
	}
	/**
	 * 设置：图片
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取：图片
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
}
