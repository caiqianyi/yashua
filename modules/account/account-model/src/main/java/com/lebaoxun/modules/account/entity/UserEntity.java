package com.lebaoxun.modules.account.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-19 20:01:34
 */
@TableName("user")
public class UserEntity implements Serializable {
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
	 * 用户账号
	 */
	private String account;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 注册来源
	 */
	private String source;
	/**
	 * 账号类型
	 */
	private String type;
	/**
	 * Y 正常 N 禁用  F 异常冻结
	 */
	private String status;
	/**
	 * 登录信息
	 */
	private String host;
	/**
	 * 账户等级
	 */
	private Integer level;
	/**
	 * 账户金额
	 */
	private Integer balance;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 生日
	 */
	private String birthday;
	/**
	 * 身份证号
	 */
	private String identity;
	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
	 */
	private String subscribe;
	/**
	 * 微信openid
	 */
	private String openid;
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	 */
	private String unionid;
	/**
	 * 用户的昵称
	 */
	private String nickname;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	private Integer sex;
	/**
	 * 用户所在城市
	 */
	private String city;
	/**
	 * 用户所在国家
	 */
	private String country;
	/**
	 * 用户所在省份
	 */
	private String province;
	/**
	 * 用户的语言，简体中文为zh_CN
	 */
	private String language;
	/**
	 * 户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 */
	private String headimgurl;
	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 */
	private String subscribeTime;
	/**
	 * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	 */
	private String remark;
	/**
	 * 用户所在的分组ID（兼容旧的用户分组接口）
	 */
	private String groupid;
	/**
	 * 注册时间
	 */
	private Date createTime;
	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;
	/**
	 * 最后购买时间
	 */
	private Date lastBuyTime;
	/**
	 * 最后修改时间
	 */
	private Date lastUpdateTime;

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
	 * 设置：用户账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：用户账号
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：注册来源
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * 获取：注册来源
	 */
	public String getSource() {
		return source;
	}
	/**
	 * 设置：账号类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：账号类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：Y 正常 N 禁用  F 异常冻结
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：Y 正常 N 禁用  F 异常冻结
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：登录信息
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * 获取：登录信息
	 */
	public String getHost() {
		return host;
	}
	/**
	 * 设置：账户等级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：账户等级
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：账户金额
	 */
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	/**
	 * 获取：账户金额
	 */
	public Integer getBalance() {
		return balance;
	}
	/**
	 * 设置：用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
	 */
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	/**
	 * 获取：用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
	 */
	public String getSubscribe() {
		return subscribe;
	}
	/**
	 * 设置：微信openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：微信openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * 设置：只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	/**
	 * 获取：只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	 */
	public String getUnionid() {
		return unionid;
	}
	/**
	 * 设置：用户的昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：用户的昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置：用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 获取：用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 设置：用户所在城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：用户所在城市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：用户所在国家
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * 获取：用户所在国家
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * 设置：用户所在省份
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：用户所在省份
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：用户的语言，简体中文为zh_CN
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * 获取：用户的语言，简体中文为zh_CN
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * 设置：户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 */
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	/**
	 * 获取：户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}
	/**
	 * 设置：用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 */
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	/**
	 * 获取：用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 */
	public String getSubscribeTime() {
		return subscribeTime;
	}
	/**
	 * 设置：公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：用户所在的分组ID（兼容旧的用户分组接口）
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	/**
	 * 获取：用户所在的分组ID（兼容旧的用户分组接口）
	 */
	public String getGroupid() {
		return groupid;
	}
	/**
	 * 设置：注册时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：注册时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：最后登录时间
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	/**
	 * 获取：最后登录时间
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	/**
	 * 设置：最后购买时间
	 */
	public void setLastBuyTime(Date lastBuyTime) {
		this.lastBuyTime = lastBuyTime;
	}
	/**
	 * 获取：最后购买时间
	 */
	public Date getLastBuyTime() {
		return lastBuyTime;
	}
	/**
	 * 设置：最后修改时间
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * 获取：最后修改时间
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
}
