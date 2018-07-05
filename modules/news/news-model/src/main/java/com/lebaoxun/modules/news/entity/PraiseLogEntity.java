package com.lebaoxun.modules.news.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 点赞表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
@TableName("praise_log")
public class PraiseLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
@TableId
	private Integer id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 赞类型
	 */
	private String type;
	/**
	 * host
	 */
	private String host;
	/**
	 * 点赞记录ID
	 */
	private String recordId;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Integer getId() {
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
	 * 设置：赞类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：赞类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：host
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * 获取：host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * 设置：点赞记录ID
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	/**
	 * 获取：点赞记录ID
	 */
	public String getRecordId() {
		return recordId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
