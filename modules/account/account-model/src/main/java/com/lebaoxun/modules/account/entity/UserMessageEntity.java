package com.lebaoxun.modules.account.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户消息
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-20 15:44:50
 */
@TableName("user_message")
public class UserMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
@TableId
	private Integer id;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 消息时间
	 */
	private Date createTime;
	/**
	 * 发送人
	 */
	private Long createBy;
	/**
	 * 接收人
	 */
	private Long userId;
	/**
	 * 类型  0：个人   1：系统
	 */
	private Integer type;
	/**
	 * 是否删除  -1：已删除  0：正常
	 */
	private Integer delFlag;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：消息标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：消息标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：消息时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：消息时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：发送人
	 */
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：发送人
	 */
	public Long getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：接收人
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：接收人
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：类型  0：个人   1：系统
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型  0：个人   1：系统
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：是否删除  -1：已删除  0：正常
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：是否删除  -1：已删除  0：正常
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
}
