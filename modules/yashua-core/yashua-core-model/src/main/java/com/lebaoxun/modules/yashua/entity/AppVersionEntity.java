package com.lebaoxun.modules.yashua.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("version")
public class AppVersionEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@TableId
	private long id;
	/**
	 * App版本号
	 */
	private String versionNumber;
	/**
	 * 是否强制更新
	 */
	private Integer forcedUpdate;
	/**
	 * 版本下载地址
	 */
	private String url;
	
	/**
	 * 版本更新说明
	 */
	private String updateInstruction;
	
	/**
	 * 版本的类型
	 */
	
	private String versionType;
	
	/**
	 * 版本的创建时间 
	 */
	
	private Date createTime;
	
	/**
	 * 版本下载人数
	 */
	
	private Long downloadUsers;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Integer getForcedUpdate() {
		return forcedUpdate;
	}

	public void setForcedUpdate(Integer forcedUpdate) {
		this.forcedUpdate = forcedUpdate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUpdateInstruction() {
		return updateInstruction;
	}

	public void setUpdateInstruction(String updateInstruction) {
		this.updateInstruction = updateInstruction;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getDownloadUsers() {
		return downloadUsers;
	}

	public void setDownloadUsers(Long downloadUsers) {
		this.downloadUsers = downloadUsers;
	}
	
}
