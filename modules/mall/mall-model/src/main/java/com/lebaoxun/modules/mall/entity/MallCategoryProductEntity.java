package com.lebaoxun.modules.mall.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品分类关联表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@TableName("mall_category_product")
public class MallCategoryProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类ID
	 */
	@TableId
	private long id;
	/**
	 * 分类ID
	 */
	private Long categoryId;
	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者
	 */
	private String createBy;

	/**
	 * 设置：分类ID
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 获取：分类ID
	 */
	public long getId() {
		return id;
	}
	/**
	 * 设置：分类ID
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取：分类ID
	 */
	public Long getCategoryId() {
		return categoryId;
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
