package com.lebaoxun.modules.mall.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 评价图片表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:11
 */
@TableName("mall_product_comment_image")
public class MallProductCommentImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 评价ID
	 */
	@TableId
	private long id;
	/**
	 * 评价ID
	 */
	private Long productCommentId;
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
	 * 设置：评价ID
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 获取：评价ID
	 */
	public long getId() {
		return id;
	}
	/**
	 * 设置：评价ID
	 */
	public void setProductCommentId(Long productCommentId) {
		this.productCommentId = productCommentId;
	}
	/**
	 * 获取：评价ID
	 */
	public Long getProductCommentId() {
		return productCommentId;
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
}
