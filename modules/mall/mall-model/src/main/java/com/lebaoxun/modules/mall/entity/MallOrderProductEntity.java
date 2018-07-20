package com.lebaoxun.modules.mall.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 订单明细表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
@TableName("mall_order_product")
public class MallOrderProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单商品ID
	 */
	@TableId
	private long orderProductId;
	/**
	 * 订单ID
	 */
	private Long orderId;
	/**
	 * 商品编号
	 */
	private Long productId;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 展示图片
	 */
	private String picImg;
	/**
	 * 商品规格编号
	 */
	private Long productSpecId;
	/**
	 * 商品规格名称
	 */
	private String productSpecName;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 积分
	 */
	private Integer score;
	/**
	 * 商品总数量
	 */
	private Integer buyNumber;
	/**
	 * 商品总积分
	 */
	private Integer productScore;
	/**
	 * 商品总金额
	 */
	private BigDecimal productAmount;
	/**
	 * 订单状态 0=待发货，2=已发货，3=待评价，4=已评价
	 */
	private Integer status;

	/**
	 * 设置：订单商品ID
	 */
	public void setOrderProductId(long orderProductId) {
		this.orderProductId = orderProductId;
	}
	/**
	 * 获取：订单商品ID
	 */
	public long getOrderProductId() {
		return orderProductId;
	}
	/**
	 * 设置：订单ID
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单ID
	 */
	public Long getOrderId() {
		return orderId;
	}
	/**
	 * 设置：商品编号
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * 获取：商品编号
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * 设置：商品名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：商品名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：展示图片
	 */
	public void setPicImg(String picImg) {
		this.picImg = picImg;
	}
	/**
	 * 获取：展示图片
	 */
	public String getPicImg() {
		return picImg;
	}
	public Long getProductSpecId() {
		return productSpecId;
	}
	public void setProductSpecId(Long productSpecId) {
		this.productSpecId = productSpecId;
	}
	/**
	 * 设置：商品规格名称
	 */
	public void setProductSpecName(String productSpecName) {
		this.productSpecName = productSpecName;
	}
	/**
	 * 获取：商品规格名称
	 */
	public String getProductSpecName() {
		return productSpecName;
	}
	/**
	 * 设置：价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：价格
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：积分
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * 获取：积分
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * 设置：商品总数量
	 */
	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}
	/**
	 * 获取：商品总数量
	 */
	public Integer getBuyNumber() {
		return buyNumber;
	}
	/**
	 * 设置：商品总积分
	 */
	public void setProductScore(Integer productScore) {
		this.productScore = productScore;
	}
	/**
	 * 获取：商品总积分
	 */
	public Integer getProductScore() {
		return productScore;
	}
	/**
	 * 设置：商品总金额
	 */
	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}
	/**
	 * 获取：商品总金额
	 */
	public BigDecimal getProductAmount() {
		return productAmount;
	}
	/**
	 * 设置：订单状态 0=待发货，2=已发货，3=待评价，4=已评价
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：订单状态 0=待发货，2=已发货，3=待评价，4=已评价
	 */
	public Integer getStatus() {
		return status;
	}
}
