package com.lebaoxun.modules.yashua.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 用户口气分数表
 */
@TableName("user_data")
public class UserDataEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@TableId
	private long id;
	/**
	 * 口气分数
	 */
	private Long fenshu;
	/**
	 * 早 晚（1 早     2晚）
	 */
	private Long biaoshi;
	/**
	 * 添加时间
	 */
	private Date adddate;
	
	/**
	 * 用户的id
	 */
	private Long userid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFenshu() {
		return fenshu;
	}
	public void setFenshu(Long fenshu) {
		this.fenshu = fenshu;
	}
	public Long getBiaoshi() {
		return biaoshi;
	}
	public void setBiaoshi(Long biaoshi) {
		this.biaoshi = biaoshi;
	}
	public Date getAdddate() {
		return adddate;
	}
	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
}
