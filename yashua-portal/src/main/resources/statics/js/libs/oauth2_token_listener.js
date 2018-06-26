var tokenTimer = null;
function tokenListener(){
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
tokenListener();
tokenTimer = setInterval('tokenListener()',1400);