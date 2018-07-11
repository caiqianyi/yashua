package com.lebaoxun.modules.sign.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 签到用户表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-07-09 17:06:41
 */
@TableName("sign_uinfo")
public class SignUinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 连续签到数
	 */
	private Integer keepUpCount;
	/**
	 * 月签到数
	 */
	private Integer mKeepUpCount;
	/**
	 * 历史最大连签数
	 */
	private Integer maxKeepUpCount;
	/**
	 * 当月最大连签天数
	 */
	private Integer mMaxKeepUpCount;
	/**
	 * 总签到数
	 */
	private Integer totalSignNum;
	/**
	 * 总未签到数
	 */
	private Integer totalResignNum;
	/**
	 * 最后签到时间
	 */
	private Date signTime;
	
	@TableField(exist=false)
	private List<SignLogEntity> logs;

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
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：连续签到数
	 */
	public void setKeepUpCount(Integer keepUpCount) {
		this.keepUpCount = keepUpCount;
	}
	/**
	 * 获取：连续签到数
	 */
	public Integer getKeepUpCount() {
		return keepUpCount;
	}
	/**
	 * 设置：月签到数
	 */
	public void setMKeepUpCount(Integer mKeepUpCount) {
		this.mKeepUpCount = mKeepUpCount;
	}
	/**
	 * 获取：月签到数
	 */
	public Integer getMKeepUpCount() {
		return mKeepUpCount;
	}
	/**
	 * 设置：总签到数
	 */
	public void setTotalSignNum(Integer totalSignNum) {
		this.totalSignNum = totalSignNum;
	}
	/**
	 * 获取：总签到数
	 */
	public Integer getTotalSignNum() {
		return totalSignNum;
	}
	/**
	 * 设置：总未签到数
	 */
	public void setTotalResignNum(Integer totalResignNum) {
		this.totalResignNum = totalResignNum;
	}
	/**
	 * 获取：总未签到数
	 */
	public Integer getTotalResignNum() {
		return totalResignNum;
	}
	/**
	 * 设置：最后签到时间
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	/**
	 * 获取：最后签到时间
	 */
	public Date getSignTime() {
		return signTime;
	}
	public Integer getMaxKeepUpCount() {
		return maxKeepUpCount;
	}
	public void setMaxKeepUpCount(Integer maxKeepUpCount) {
		this.maxKeepUpCount = maxKeepUpCount;
	}
	public Integer getmMaxKeepUpCount() {
		return mMaxKeepUpCount;
	}
	public void setmMaxKeepUpCount(Integer mMaxKeepUpCount) {
		this.mMaxKeepUpCount = mMaxKeepUpCount;
	}
	public List<SignLogEntity> getLogs() {
		return logs;
	}
	public void setLogs(List<SignLogEntity> logs) {
		this.logs = logs;
	}
}
