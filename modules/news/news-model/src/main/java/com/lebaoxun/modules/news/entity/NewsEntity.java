package com.lebaoxun.modules.news.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 新闻表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-05 16:39:42
 */
@TableName("news")
public class NewsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private long id;
	/**
	 * 作者ID
	 */
	private Long uId;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 分类ID
	 */
	private Integer classId;
	/**
	 * 图片
	 */
	private String picItems;
	/**
	 * 点击数
	 */
	private Integer clicks;
	/**
	 * 点赞数
	 */
	private Integer praises;
	/**
	 * 回复数
	 */
	private Integer replies;
	/**
	 * 最后回复
	 */
	private Long lastReplyId;
	/**
	 * 是否置顶  0：否   1：是
	 */
	private Integer isTop;
	/**
	 * 审核状态  0：未通过   1：已通过
	 */
	private Integer checkStatus;
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
	 * 设置：作者ID
	 */
	public void setUId(Long uId) {
		this.uId = uId;
	}
	/**
	 * 获取：作者ID
	 */
	public Long getUId() {
		return uId;
	}
	/**
	 * 设置：作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * 获取：作者
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
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
	 * 设置：分类ID
	 */
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	/**
	 * 获取：分类ID
	 */
	public Integer getClassId() {
		return classId;
	}
	/**
	 * 设置：图片
	 */
	public void setPicItems(String picItems) {
		this.picItems = picItems;
	}
	/**
	 * 获取：图片
	 */
	public String getPicItems() {
		return picItems;
	}
	/**
	 * 设置：点击数
	 */
	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}
	/**
	 * 获取：点击数
	 */
	public Integer getClicks() {
		return clicks;
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
	 * 设置：回复数
	 */
	public void setReplies(Integer replies) {
		this.replies = replies;
	}
	/**
	 * 获取：回复数
	 */
	public Integer getReplies() {
		return replies;
	}
	/**
	 * 设置：最后回复
	 */
	public void setLastReplyId(Long lastReplyId) {
		this.lastReplyId = lastReplyId;
	}
	/**
	 * 获取：最后回复
	 */
	public Long getLastReplyId() {
		return lastReplyId;
	}
	/**
	 * 设置：是否置顶  0：否   1：是
	 */
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	/**
	 * 获取：是否置顶  0：否   1：是
	 */
	public Integer getIsTop() {
		return isTop;
	}
	/**
	 * 设置：审核状态  0：未通过   1：已通过
	 */
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	/**
	 * 获取：审核状态  0：未通过   1：已通过
	 */
	public Integer getCheckStatus() {
		return checkStatus;
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
	@Override
	public String toString() {
		return "NewsEntity [id=" + id + ", uId=" + uId + ", author=" + author + ", title=" + title + ", content="
				+ content + ", classId=" + classId + ", picItems=" + picItems + ", clicks=" + clicks + ", praises="
				+ praises + ", replies=" + replies + ", lastReplyId=" + lastReplyId + ", isTop=" + isTop
				+ ", checkStatus=" + checkStatus + ", createTime=" + createTime + "]";
	}
	
	
}
