package com.lebaoxun.modules.mall.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品属性表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@TableName("mall_product_attr")
public class MallProductAttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性ID
	 */
@TableId
	private Long id;
	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 总库存
	 */
	private Integer stock;
	/**
	 * 销售量
	 */
	private Integer salesVolume;
	/**
	 * 游览量
	 */
	private Integer clicks;
	/**
	 * 评论数量
	 */
	private Integer replies;
	/**
	 * 晒单评价数量
	 */
	private Integer showReplies;
	/**
	 * 平均评价
	 */
	private BigDecimal commentAverage;
	/**
	 * 收藏数
	 */
	private Integer favoriteNumber;
	/**
	 * 提问数
	 */
	private Integer questionNumber;

	/**
	 * 设置：属性ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：属性ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：商品ID
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * 获取：商品ID
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * 设置：总库存
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	/**
	 * 获取：总库存
	 */
	public Integer getStock() {
		return stock;
	}
	/**
	 * 设置：销售量
	 */
	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}
	/**
	 * 获取：销售量
	 */
	public Integer getSalesVolume() {
		return salesVolume;
	}
	/**
	 * 设置：游览量
	 */
	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}
	/**
	 * 获取：游览量
	 */
	public Integer getClicks() {
		return clicks;
	}
	/**
	 * 设置：评论数量
	 */
	public void setReplies(Integer replies) {
		this.replies = replies;
	}
	/**
	 * 获取：评论数量
	 */
	public Integer getReplies() {
		return replies;
	}
	/**
	 * 设置：晒单评价数量
	 */
	public void setShowReplies(Integer showReplies) {
		this.showReplies = showReplies;
	}
	/**
	 * 获取：晒单评价数量
	 */
	public Integer getShowReplies() {
		return showReplies;
	}
	/**
	 * 设置：平均评价
	 */
	public void setCommentAverage(BigDecimal commentAverage) {
		this.commentAverage = commentAverage;
	}
	/**
	 * 获取：平均评价
	 */
	public BigDecimal getCommentAverage() {
		return commentAverage;
	}
	/**
	 * 设置：收藏数
	 */
	public void setFavoriteNumber(Integer favoriteNumber) {
		this.favoriteNumber = favoriteNumber;
	}
	/**
	 * 获取：收藏数
	 */
	public Integer getFavoriteNumber() {
		return favoriteNumber;
	}
	/**
	 * 设置：提问数
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}
	/**
	 * 获取：提问数
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}
}
