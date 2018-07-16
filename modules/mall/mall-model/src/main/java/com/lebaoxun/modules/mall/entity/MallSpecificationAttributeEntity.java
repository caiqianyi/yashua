package com.lebaoxun.modules.mall.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 规格属性表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@TableName("mall_specification_attribute")
public class MallSpecificationAttributeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 规格属性ID
	 */
	@TableId
	private long specAttrId;
	/**
	 * 规格ID
	 */
	private Long specificationId;
	/**
	 * 规格名称
	 */
	@TableField(exist=false)
	private String specificationName;
	/**
	 * 规格属性名称
	 */
	private String name;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者
	 */
	private String createBy;

	/**
	 * 设置：规格属性ID
	 */
	public void setSpecAttrId(long specAttrId) {
		this.specAttrId = specAttrId;
	}
	/**
	 * 获取：规格属性ID
	 */
	public long getSpecAttrId() {
		return specAttrId;
	}
	/**
	 * 设置：规格ID
	 */
	public void setSpecificationId(Long specificationId) {
		this.specificationId = specificationId;
	}
	/**
	 * 获取：规格ID
	 */
	public Long getSpecificationId() {
		return specificationId;
	}
	/**
	 * 设置：规格属性名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：规格属性名称
	 */
	public String getName() {
		return name;
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
	public String getSpecificationName() {
		return specificationName;
	}
	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}
}
