package com.lebaoxun.modules.sign.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到奖励规则表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */
@TableName("sign_award")
public class SignAwardEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private long id;
	/**
	 * 自定义条件值
	 */
	private String condition;
	/**
	 * 自定义奖励SQL
	 */
	private String awardSql;
	/**
	 * 奖励名称
	 */
	private String name;
	/**
	 * 分组名
	 */
	private String groupId;
	/**
	 * 是否启用  -1：未启用  0：启用
	 */
	private Integer flag;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：主键
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public long getId() {
		return id;
	}
	/**
	 * 设置：自定义条件值
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	/**
	 * 获取：自定义条件值
	 */
	public String getCondition() {
		return condition;
	}
	/**
	 * 设置：自定义奖励SQL
	 */
	public void setAwardSql(String awardSql) {
		this.awardSql = awardSql;
	}
	/**
	 * 获取：自定义奖励SQL
	 */
	public String getAwardSql() {
		return awardSql;
	}
	/**
	 * 设置：奖励名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：奖励名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：分组名
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * 获取：分组名
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * 设置：是否启用  -1：未启用  0：启用
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	/**
	 * 获取：是否启用  -1：未启用  0：启用
	 */
	public Integer getFlag() {
		return flag;
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
}
