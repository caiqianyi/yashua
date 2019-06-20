package com.lebaoxun.modules.account.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 用户消息中间表
 * 
 * 
 */
@TableName("user_message_mid")
public class UserMessageMidEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@TableId
	private long id;
	/**
	 * 用户Id
	 */
	private Long userId;
	/**
	 * 消息id
	 */
	private Long messageId;
	/**
	 * 读取状态0：未读1：已读
	 */
	private Integer status;
	/**
	 * 添加时间
	 */
	private Date addTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

}
