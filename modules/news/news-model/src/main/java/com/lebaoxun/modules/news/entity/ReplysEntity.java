package com.lebaoxun.modules.news.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 回复表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
@TableName("replys")
public class ReplysEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private long id;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 回复人
	 */
	private Long userId;
	/**
	 * 评论分类
	 */
	private String type;
	/**
	 * 回帖ID
	 */
	private String recordId;
	/**
	 * 给谁回复
	 */
	private Integer toReplyId;
	/**
	 * 点赞数
	 */
	private Integer praises;
	/**
	 * 创建时间
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
	 * 设置：回复人
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：回复人
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：评论分类
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：评论分类
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：回帖ID
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	/**
	 * 获取：回帖ID
	 */
	public String getRecordId() {
		return recordId;
	}
	/**
	 * 设置：给谁回复
	 */
	public void setToReplyId(Integer toReplyId) {
		this.toReplyId = toReplyId;
	}
	/**
	 * 获取：给谁回复
	 */
	public Integer getToReplyId() {
		return toReplyId;
	}
	/**
	 * 设置：点赞数
	 */
	public void setPraises(Integer praises) {
		this.praises = praises;
	}
	/**
	 * 获取：点赞数
	 */
	public Integer getPraises() {
		return praises;
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
