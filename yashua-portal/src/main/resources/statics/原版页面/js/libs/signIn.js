var SignIn = function(param) {
	this._init(param);
};

SignIn.prototype = {
	param : {
		iYear : new Date().getFullYear(),
		iMonth : new Date().getMonth(),
		signTimes : []
	},
	_init : function(param) {
		this.param = $.extend(this.param,param);
	}
};

SignIn.prototype.calendar = function() {
	var nlstr = new Date(this.param.iYear, this.param.iMonth, 1); // 当月第一天
	var firstday = nlstr.getDay(); // 第一天星期几
	
	function is_leap(year) {
		return (year % 100 == 0 ? res = (year % 400 == 0 ? 1 : 0)
				: res = (year % 4 == 0 ? 1 : 0));
	}
	var m_days = new Array(31, 28 + is_leap(this.param.iYear), 31, 30, 31, 30, 31, 31, 30,
			31, 30, 31); // 每个月的天数
	var tr_str = Math.ceil((m_days[this.param.iMonth] + firstday) / 7);
	var days = [];
	var idx, date_str;
	for (var i = 0; i < tr_str; i++) { // 表格的行
		days[i] = new Array();
		for (var k = 0; k < 7; k++) { // 表格每行的单元格
			idx = i * 7 + k; // 单元格自然序列号
			
			date_str = idx - firstday + 1; // 计算日期
			(date_str <= 0 || date_str > m_days[this.param.iMonth]) ? date_str = null
					: date_str = idx - firstday + 1; // 过滤无效日期（小于等于零的、大于月总天数的）
			var isSign = date_str != null && this.isSign(this.param.iMonth,date_str);
			days[i][k] = {date:date_str,isSign:isSign};
		}
	}
	return days;
};

SignIn.prototype.load = function(fn){
	var calendar = this.calendar();
	fn({
		calendar: calendar,
		show: {
			iMonth: this.param.iMonth+1,
			iYear: this.param.iYear
		}
	});
};

SignIn.prototype.prev = function(fn){
	this.param.iMonth -= 1;
	if(this.param.iMonth==0){
		this.param.iMonth = 12;
		this.param.iYear -= 1;
	}
	var calendar = this.calendar();
	fn({
		calendar: calendar,
		show: {
			iMonth: this.param.iMonth+1,
			iYear: this.param.iYear
		}
	});
};

SignIn.prototype.next = function(fn){
	this.param.iMonth += 1;
	if(this.param.iMonth==13){
		this.param.iMonth = 1;
		this.param.iYear += 1;
	}
	var calendar = this.calendar();
	fn({
		calendar: calendar,
		show: {
			iMonth: this.param.iMonth+1,
			iYear: this.param.iYear
		}
	});
};

SignIn.prototype.isSign = function(iMonth,iDay){
	for(var u=0;u<this.param.signTimes.length;u++){
		var time = this.param.signTimes[u];
		if(time.getMonth() == iMonth 
				&& time.getDate() == iDay){
			return true;
		}
	}
	return false;
}
