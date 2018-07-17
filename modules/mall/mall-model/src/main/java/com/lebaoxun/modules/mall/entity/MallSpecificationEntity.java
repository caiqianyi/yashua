package com.lebaoxun.modules.mall.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 规格表

 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@TableName("mall_specification")
public class MallSpecificationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 规格ID
	 */
	@TableId
	private long specificationId;
	/**
	 * 分类ID
	 */
	private Long categoryId;
	/**
	 * 分类名称
	 */
	@TableField(exist=false)
	private String categoryName;
	/**
	 * 规格名称
	 */
	private String name;
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
	
	@TableField(exist=false)
	private List<MallSpecificationAttributeEntity> attrs;

	/**
	 * 设置：规格ID
	 */
	public void setSpecificationId(long specificationId) {
		this.specificationId = specificationId;
	}
	/**
	 * 获取：规格ID
	 */
	public long getSpecificationId() {
		return specificationId;
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
	 * 设置：规格名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：规格名称
	 */
	public String getName() {
		return name;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<MallSpecificationAttributeEntity> getAttrs() {
		return attrs;
	}
	public void setAttrs(List<MallSpecificationAttributeEntity> attrs) {
		this.attrs = attrs;
	}
}
