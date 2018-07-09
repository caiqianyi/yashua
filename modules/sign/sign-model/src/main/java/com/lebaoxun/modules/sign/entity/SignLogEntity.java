package com.lebaoxun.modules.sign.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到记录表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */
@TableName("sign_log")
public class SignLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 签到时间
	 */
	private Date createTime;

	/**
	 * 设置：主键
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
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
	 * 设置：签到时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：签到时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
