package com.lebaoxun.modules.mall.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品图片表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@TableName("mall_product_image")
public class MallProductImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品图片ID
	 */
	@TableId
	private long picImgId;
	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 展示图片
	 */
	private String picImg;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 状态：1.显示；0.隐藏
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者
	 */
	private String createBy;

	/**
	 * 设置：商品图片ID
	 */
	public void setPicImgId(long picImgId) {
		this.picImgId = picImgId;
	}
	/**
	 * 获取：商品图片ID
	 */
	public long getPicImgId() {
		return picImgId;
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
	/**
	 * 设置：排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * 设置：状态：1.显示；0.隐藏
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态：1.显示；0.隐藏
	 */
	public Integer getStatus() {
		return status;
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
	/**
	 * 设置：创建者
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建者
	 */
	public String getCreateBy() {
		return createBy;
	}
}
