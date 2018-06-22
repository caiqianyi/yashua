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

function login(oauth2){
	for(var ke in oauth2){
		window.storage.set("login."+ke,oauth2[ke],false);
	}
	var expiresTime = oauth2["expires_in"];
	var now = new Date();
	var t = now.getTime()+expiresTime*1000;
	var loginTime = new Date(t);
	window.storage.set('login.expire',loginTime.getTime(),false);
}

var tokenTimer = null;
function tokenSurvey(){
	if(isTokenAlert == true){
		return;
	}
    var token = parent.window.storage.get('login.access_token',false)
    var isLogout = false;
    if(!token){
    	isLogout = true;
    }else{
    	var nowTime = new Date().getTime();
    	var expire = parseInt(parent.window.storage.get('login.expire', false),10)
    	if (nowTime > expire) {
    		isLogout = true;
    		/*alert('您的账号在其它地方登录，如非您本人操作，请尽快修改密码！')
    		window.alert = function () { return false };*/
    	}
    }
    if(isLogout == true){
    	isTokenAlert = true;
    	window.storage.clear();
    	parent.location.href = '/login.html';
    }
}
tokenSurvey();
tokenTimer = setInterval('tokenSurvey()',1400);

