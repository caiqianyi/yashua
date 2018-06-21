package com.lebaoxun.modules.account.em;

public enum UserLogAction {
	/**
	 * 带字母U开头，为用户本人操作产生的日志
	 */
	U_REGISTER("注册"),U_WECHATOA_REGISTER("公众号注册"),U_LOGIN("登录"), U_LOGOUT("登出"),U_MODIFY_INFO("修改个人资料"),U_BACK_PASSWD("找回密码"),U_MODIFY_PASSWD("修改密码"),
	U_BIND_OPENID("绑定微信公众号openid"),U_UNBIND_WECHATOA("解除绑定微信公众号"),U_BIND_MOBILE("绑定手机号"),U_PAY("支付"),
	U_BALANCE_ADD("账户交易（增加）"),U_BALANCE_REDUCE("账户交易（扣除）"),
	
	
	/**
	 * 带字母A开头，为管理员操作用户账号操作产生的日志
	 */
	A_REGISTER("管理员开通账号"),A_MODIFY_INFO("管理员编辑资料"),A_UNLOCK("管理员解除禁用"),A_LOCK("管理员禁用账号"),A_MODIFY_PASSWD("管理员重置密码"),A_DELETE("管理员删除账号"),
	A_BALANCE_ADD("管理员手动充值"),A_BALANCE_REDUCE("管理员手动扣除");
	
	private String descr;
	
	private UserLogAction(String descr) {
		// TODO Auto-generated constructor stub
		this.descr = descr;
	}

	public String getDescr() {
		return descr;
	}
}
