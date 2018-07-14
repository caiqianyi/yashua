package com.lebaoxun.modules.mall.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-12 19:57:12
 */
@TableName("mall_product")
public class MallProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品ID
	 */
	@TableId
	private long id;
	/**
	 * 商品编号
	 */
	private Long productNumber;
	/**
	 * 标签ID
	 */
	private Integer labelId;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 显示积分
	 */
	private Integer showScore;
	/**
	 * 显示价格
	 */
	private BigDecimal showPrice;
	/**
	 * 展示图片
	 */
	private String showPic;
	/**
	 * 商品简介
	 */
	private String introduce;
	/**
	 * 商品描述
	 */
	private String description;
	/**
	 * 是否置顶 1=置顶/0=默认
	 */
	private Integer showInTop;
	/**
	 * 是否热门 1=热门/0=默认
	 */
	private Integer showInHot;
	/**
	 * 是否上架：1=上架/0=下架
	 */
	private Integer showInShelve;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 上架时间
	 */
	private Date shelveTime;
	/**
	 * 上架人
	 */
	private String shelveBy;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新者
	 */
	private String updateBy;
	/**
	 * 搜索关键词
	 */
	private String searchKey;
	/**
	 * 页面标题
	 */
	private String shareTitle;
	/**
	 * 页面描述
	 */
	private String shareDescription;
	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 设置：商品ID
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 获取：商品ID
	 */
	public long getId() {
		return id;
	}
	/**
	 * 设置：商品编号
	 */
	public void setProductNumber(Long productNumber) {
		this.productNumber = productNumber;
	}
	/**
	 * 获取：商品编号
	 */
	public Long getProductNumber() {
		return productNumber;
	}
	/**
	 * 设置：标签ID
	 */
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	/**
	 * 获取：标签ID
	 */
	public Integer getLabelId() {
		return labelId;
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
	 * 设置：显示积分
	 */
	public void setShowScore(Integer showScore) {
		this.showScore = showScore;
	}
	/**
	 * 获取：显示积分
	 */
	public Integer getShowScore() {
		return showScore;
	}
	/**
	 * 设置：显示价格
	 */
	public void setShowPrice(BigDecimal showPrice) {
		this.showPrice = showPrice;
	}
	/**
	 * 获取：显示价格
	 */
	public BigDecimal getShowPrice() {
		return showPrice;
	}
	/**
	 * 设置：展示图片
	 */
	public void setShowPic(String showPic) {
		this.showPic = showPic;
	}
	/**
	 * 获取：展示图片
	 */
	public String getShowPic() {
		return showPic;
	}
	/**
	 * 设置：商品简介
	 */
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	/**
	 * 获取：商品简介
	 */
	public String getIntroduce() {
		return introduce;
	}
	/**
	 * 设置：商品描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：商品描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：是否置顶 1=置顶/0=默认
	 */
	public void setShowInTop(Integer showInTop) {
		this.showInTop = showInTop;
	}
	/**
	 * 获取：是否置顶 1=置顶/0=默认
	 */
	public Integer getShowInTop() {
		return showInTop;
	}
	/**
	 * 设置：是否热门 1=热门/0=默认
	 */
	public void setShowInHot(Integer showInHot) {
		this.showInHot = showInHot;
	}
	/**
	 * 获取：是否热门 1=热门/0=默认
	 */
	public Integer getShowInHot() {
		return showInHot;
	}
	/**
	 * 设置：是否上架：1=上架/0=下架
	 */
	public void setShowInShelve(Integer showInShelve) {
		this.showInShelve = showInShelve;
	}
	/**
	 * 获取：是否上架：1=上架/0=下架
	 */
	public Integer getShowInShelve() {
		return showInShelve;
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
	 * 设置：上架时间
	 */
	public void setShelveTime(Date shelveTime) {
		this.shelveTime = shelveTime;
	}
	/**
	 * 获取：上架时间
	 */
	public Date getShelveTime() {
		return shelveTime;
	}
	/**
	 * 设置：上架人
	 */
	public void setShelveBy(String shelveBy) {
		this.shelveBy = shelveBy;
	}
	/**
	 * 获取：上架人
	 */
	public String getShelveBy() {
		return shelveBy;
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
	/**
	 * 设置：搜索关键词
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	/**
	 * 获取：搜索关键词
	 */
	public String getSearchKey() {
		return searchKey;
	}
	/**
	 * 设置：页面标题
	 */
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	/**
	 * 获取：页面标题
	 */
	public String getShareTitle() {
		return shareTitle;
	}
	/**
	 * 设置：页面描述
	 */
	public void setShareDescription(String shareDescription) {
		this.shareDescription = shareDescription;
	}
	/**
	 * 获取：页面描述
	 */
	public String getShareDescription() {
		return shareDescription;
	}
	/**
	 * 设置：备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：备注
	 */
	public String getRemarks() {
		return remarks;
	}
}
