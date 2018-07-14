package com.lebaoxun.modules.mall.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品参数表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@TableName("mall_product_parameter")
public class MallProductParameterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 参数ID
	 */
	@TableId
	private long productParameterId;
	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 参数名
	 */
	private String name;
	/**
	 * 参数值
	 */
	private String value;
	/**
	 * 状态：1.显示；0.隐藏
	 */
	private Integer status;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新者
	 */
	private String updateBy;

	/**
	 * 设置：参数ID
	 */
	public void setProductParameterId(long productParameterId) {
		this.productParameterId = productParameterId;
	}
	/**
	 * 获取：参数ID
	 */
	public long getProductParameterId() {
		return productParameterId;
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
	 * 设置：参数名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：参数名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：参数值
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取：参数值
	 */
	public String getValue() {
		return value;
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
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：更新者
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：更新者
	 */
	public String getUpdateBy() {
		return updateBy;
	}
}
