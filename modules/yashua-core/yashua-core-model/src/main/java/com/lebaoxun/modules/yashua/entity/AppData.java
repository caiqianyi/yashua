package com.lebaoxun.modules.yashua.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * App显示数据
 */
public class AppData {
	
	private List<Long> alist = new LinkedList<>();//早上的口气数据集合
	private List<Long> plist= new LinkedList<>();//晚上口气的数据集合
	private Integer zp;//早上口气的平均分
	private Integer wp;//晚上口气的平均分
	public List<Long> getAlist() {
		return alist;
	}
	public void setAlist(List<Long> alist) {
		this.alist = alist;
	}
	public List<Long> getPlist() {
		return plist;
	}
	public void setPlist(List<Long> plist) {
		this.plist = plist;
	}
	public Integer getZp() {
		return zp;
	}
	public void setZp(Integer zp) {
		this.zp = zp;
	}
	public Integer getWp() {
		return wp;
	}
	public void setWp(Integer wp) {
		this.wp = wp;
	}
}
