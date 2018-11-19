package com.lebaoxun.modules.pay.wxpay.vo;

/**
 * 统一退单提交为微信的参数
 */
public class Unifierefund {
	
	private String appid;//微信分配的公众账号ID（企业号corpid即为此appId）,例如：wxd678efh567hg6787
	private String mch_id;//商户id
	private String nonce_str;//随机字符串:数字+大写字母的组合，32位
	private String sign;//签名
	private String out_trade_no;//商户系统内部的订单号
	private String transaction_id;//微信生成的订单号，在支付通知中有返回
	private String out_refund_no;//商户退款单号
	private String fee_type;//货币类型:符合ISO 4217标准的三位字母代码，默认人民币：CNY
	private Integer total_fee;//总金额
	private Integer refund_fee;//退款金额
	private String refund_desc;//退款原因
	private String refund_account;//退款原因
	private String notify_url;//接收微信支付异步通知回调地址
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public Integer getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(Integer refund_fee) {
		this.refund_fee = refund_fee;
	}
	public String getRefund_desc() {
		return refund_desc;
	}
	public void setRefund_desc(String refund_desc) {
		this.refund_desc = refund_desc;
	}
	public String getRefund_account() {
		return refund_account;
	}
	public void setRefund_account(String refund_account) {
		this.refund_account = refund_account;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
}
