//全局配置
var isTokenAlert = false;
$.ajaxSetup({
	dataType: "json",
	cache: false,
	headers: { // 默认添加请求头  
        "Authorization": window.storage.get('login.access_token',false) 
    },
    success: function(r){
    	console.info(r);
    },
    complete:function(result){
    	var response = result.responseJSON;
    	if(response.hasOwnProperty("errcode") && response.errcode == 10003){
    		isTokenAlert = true;
    		var nowTime = new Date().getTime();
    	    var expire = parseInt(window.storage.get('login.expire', false),10)
    	    if (nowTime < expire-1000) {
    	        alert('您的账号在其它地方登录，如非您本人操作，请尽快修改密码！')
    	        parent.window.alert = function () { return false };
    	    }
    	    parent.window.storage.clear();
    	    parent.location.href = '/login.html';
    	}
    }
});

var login = {
		el: null,
		setup : function(oauth2) {
			for ( var ke in oauth2) {
				window.storage.set("login." + ke, oauth2[ke], false);
			}
			var expiresTime = oauth2["expires_in"];
			var now = new Date();
			var t = now.getTime() + expiresTime * 1000;
			var loginTime = new Date(t);
			window.storage.set('login.expire', loginTime.getTime(), false);
		},
		refreshCode : function(obj) {
			$(login.el).attr("src","/captcha.jpg?t=" + $.now());
		},
		action : function(username,password,captcha,fn) {
			if(username == '' || password == '' || captcha == '') {
				//均不能为空
				alert('账号、密码和验证码不能为空!');
				//改变验证码
				return false;
			}
			
			$.ajax({
				type : "GET",
				url : "/oauth2/encrypt",
				data : {
					encrypts : username + "," + encodeURIComponent(password),
					token : secret
				},
				dataType : "json",
				success : function(response) {
					if(typeof fn == 'function'){
						fn(response);
					}
					var data = {};
					if (response.errcode == 0) {
						data.username = response.data[0];
						data.password = response.data[1];
						data.captcha = captcha;
						$.ajax({
							type : "POST",
							url : "/oauth2/token",
							data : data,
							dataType : "json",
							success : function(result) {
								if (result.errcode == 0) {//登录成功
									login.setup(result.data);
									parent.location.href = '/index.html';
								} else {
									alert(result.errmsg);
									login.refreshCode();
								}
							}
						});
					} else {
						if (response.errcode == 10015) {
							setTimeout(function() {
								parent.location.reload();
							}, 1000);
							return;
						}
						login.refreshCode();
					}
				}
			});
		}
	};

