var carts = {
	localKey: "shopping.cart",
	localTempKey: "shopping.cart.temp",
	userId: '',
	products: {
		items: [],
		put: function(productSpecId,buyNumber,checkStatus,flag){
			var idx = this.indexOf(productSpecId), inr = buyNumber, len = this.items.length;
			if(idx > -1){
				inr = this.items[idx].buyNumber;
				if(typeof flag == 'undefined' || flag == 0){
					inr += buyNumber;
				}else if(flag == 1){
					inr -= buyNumber;
				}
				len = idx;
			}
			this.items[len] = {productSpecId:productSpecId,buyNumber:inr,checkStatus:checkStatus,time: new Date().getTime()};
		},
		get: function(productSpecId){
			var i = this.indexOf(productSpecId);
			if(i > -1){
				return this.items[i];
			}
			return null;
		},
		remove: function(productSpecId){
			var i = this.indexOf(productSpecId);
			if(i > -1){
				this.items.splice(i,1);
				return true;
			}
			return;
		},
		indexOf: function(productSpecId){
			for(var i=0;i<this.items.length;i++){
				if(this.items[i].productSpecId == productSpecId){
					return i;
				}
			}
			return -1;
		}
	},
	put: function(productSpecId,buyNumber,checkStatus,flag){
		buyNumber = parseInt(buyNumber,10);
		var amount = buyNumber;
		var products = this.get();
		if(!products){
			products = this.products;
		}
		if(!checkStatus){
			checkStatus = 0;
		}
		products.put(productSpecId,buyNumber,checkStatus,flag);
		var key = this.userId && this.userId.length > 0 ? this.localKey+"."+userId : this.localKey;
		window.localStorage.setItem(key,JSON.stringify(products.items));
	},
	remove: function(productSpecId){
		var products = this.get(productSpecId);
		if(products){
			products.remove(productSpecId);
			var result = products.indexOf(productSpecId) == -1;
			if(result){
				window.localStorage.setItem(this.localKey,JSON.stringify(products.items));
			}
			return result;
		}
		return false;
	},
	clear: function(){
		var key = this.userId && this.userId.length > 0 ? this.localKey+"."+userId : this.localKey;
		window.localStorage.removeItem(key);
		return !(!window.localStorage.getItem(key));
	},
	clearTemp: function(){
		window.localStorage.removeItem(this.localTempKey);
		return !(!window.localStorage.getItem(this.localTempKey));
	},
	get: function(){
		var key = this.userId && this.userId.length > 0 ? this.localKey+"."+userId : this.localKey;
		var items = window.localStorage.getItem(key);
		if(items){
			var p = $.extend({},this.products);
			p.items = JSON.parse(items);
			return p;
		}
		return null;
	},
	getTemp: function(){
		var items = window.localStorage.getItem(this.localTempKey);
		if(items){
			var p = $.extend({},this.products);
			p.items = JSON.parse(items);
			return p;
		}
		return null;
	},
	syncServer: function(succuess){
		var _this = this;
		if(typeof login != 'undefined'){
			var products = _this.get();
			if(products){
				if(login.isLogin()){
					$.ajax({
						type: "POST",
					    url: "/mall/cart/sync",
					    contentType: "application/json",
					    data: JSON.stringify(products.items),
					    success: function(r){
					    	if(r.errcode == 0){
					    		if(succuess && typeof succuess == 'function'){
					    			succuess();
					    		}
								console.info("购物车数据同步成功");
							}else{
								window.localStorage.setItem(this.localTempKey,JSON.stringify(products.items));
								console.error(r.errmsg);
							}
						}
					});
					return;
				}
				window.localStorage.setItem(this.localTempKey,JSON.stringify(products.items));
				console.error("尚未登录");
			}
		}
	},
	pull: function(){
		
	}
};