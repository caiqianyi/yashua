package com.lebaoxun.modules.mall.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品规格表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@TableName("mall_product_specification")
public class MallProductSpecificationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品规格ID
	 */
	@TableId
	private long productSpecId;
	/**
	 * 商品规格编号
	 */
	private Long productSpecNumber;
	/**
	 * 商品ID
	 */
	private Long productId;
	/**
	 * 规格：规格ID
	 */
	private Long specificationId;
	@TableField(exist=false)
	private String specName;
	/**
	 * 规格属性ID
	 */
	private Long specAttrId;
	/**
	 * 规格属性名
	 */
	@TableField(exist=false)
	private String specAttrName;
	/**
	 * 库存
	 */
	private Integer stock;
	/**
	 * 销售量
	 */
	private Integer salesVolume;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 积分
	 */
	private Integer score;
	/**
	 * 是否默认状态：0,不默认；1,默认
	 */
	private Integer defaultStatus;
	/**
	 * 商品状态：0,新增；1,上架；2,下架
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
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新者
	 */
	private String updateBy;

	/**
	 * 设置：商品规格ID
	 */
	public void setProductSpecId(long productSpecId) {
		this.productSpecId = productSpecId;
	}
	/**
	 * 获取：商品规格ID
	 */
	public long getProductSpecId() {
		return productSpecId;
	}
	/**
	 * 设置：商品规格编号
	 */
	public void setProductSpecNumber(Long productSpecNumber) {
		this.productSpecNumber = productSpecNumber;
	}
	/**
	 * 获取：商品规格编号
	 */
	public Long getProductSpecNumber() {
		return productSpecNumber;
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
	 * 设置：库存
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	/**
	 * 获取：库存
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
	 * 设置：是否默认状态：0,不默认；1,默认
	 */
	public void setDefaultStatus(Integer defaultStatus) {
		this.defaultStatus = defaultStatus;
	}
	/**
	 * 获取：是否默认状态：0,不默认；1,默认
	 */
	public Integer getDefaultStatus() {
		return defaultStatus;
	}
	/**
	 * 设置：商品状态：0,新增；1,上架；2,下架
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：商品状态：0,新增；1,上架；2,下架
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
	public Long getSpecificationId() {
		return specificationId;
	}
	public void setSpecificationId(Long specificationId) {
		this.specificationId = specificationId;
	}
	public Long getSpecAttrId() {
		return specAttrId;
	}
	public void setSpecAttrId(Long specAttrId) {
		this.specAttrId = specAttrId;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public String getSpecAttrName() {
		return specAttrName;
	}
	public void setSpecAttrName(String specAttrName) {
		this.specAttrName = specAttrName;
	}
}
