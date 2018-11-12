var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "group",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        config:{
        	group:null,
        	appid:'',
        	mchid:'',
        	secret:'',
        	notifyUrl:'',
        	queueName:''
        },
        showTest: true,
        order: {
        	type: 1,
        	group: null,
        	outTradeNo: "lv2018072301080",
        	subject: "购买道具",
        	totalAmount: 1,
        	userId: 1,
        	body: "",
        	openid: "odeZn073ReSxbUL9x8-nvJ0rZAxw",
        	spbill_create_ip: "121.69.133.202"
        }
    },
    methods: {
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.config = {
            		group:null,
                	appid:'',
                	mchid:'',
                	secret:'',
                	notifyUrl:'',
                	queueName:''
                };
        },
        update: function () {
            var group = getConfigGroup();
            if(group == null){
                return ;
            }

            $.get(baseURL + "wxpay/config/info/", {group:group}, function(r){
            	if(r.errcode == 0){
            		vm.showList = false;
	                vm.title = "修改";
	                vm.config = r.data;
                }else{
                	alert(r.errmsg);
                }
            });
        },
        del: function () {
            var group = getConfigGroup();
            if(group == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "wxpay/config/delete",
                    data: {group:group},
                    success: function(r){
                        if(r.errcode == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.errmsg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

            var url = "wxpay/config/save";
            $.ajax({
                type: "POST",
                url:  baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.config),
                success: function(r){
                    if(r.errcode == 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.errmsg);
                    }
                }
            });
        },
        reload: function () {
            vm.showList = true;
            vm.showTest = true;
            config.table.refresh();
        },
        validator: function () {
            if(isBlank(vm.config.group)){
                alert("名称不能为空");
                return true;
            }
            if(isBlank(vm.config.appid)){
                alert("开发者的应用ID不能为空");
                return true;
            }
            if(isBlank(vm.config.mchid)){
                alert("商户号不能为空");
                return true;
            }
            if(isBlank(vm.config.secret)){
                alert("密钥不能为空");
                return true;
            }
            if(isBlank(vm.config.notifyUrl)){
                alert("支付结果异步通知URL不能为空");
                return true;
            }
        },
        toTestPay: function(){
        	var group = getConfigGroup();
            if(group == null){
                return ;
            }
            vm.order.group = group;
            vm.showTest = false;
        	vm.showList = true;
        },
        testPay: function(){
        	if(isBlank(vm.order.outTradeNo) ||
        			isBlank(vm.order.subject) ||
        			isBlank(vm.order.totalAmount) ||
        			isBlank(vm.order.userId)){
        		alert("请填写必填项！");
        		return;
        	}
        	var order = vm.order;
        	
        	if(order.type == 1){
        		$.ajax({
        			type: "POST",
        			url: baseURL + "/wxpay/payment",
        			data: {
        				group: order.group,
        				orderNo: order.outTradeNo,
        				descr: order.subject,
        				totalFee: order.totalAmount,
        				userId: order.userId,
        				attach: order.body,
        				openid: order.openid,
        				spbill_create_ip: order.spbill_create_ip
        			},
        			success: function(r){
        				if(r.errcode == 0){
        					var pay_info = r.data;
                            function onBridgeReady(){
                                WeixinJSBridge.invoke(
                                    'getBrandWCPayRequest', {
                                        "appId": pay_info.appId, //公众号名称，由商户传入
                                        "timeStamp": pay_info.timeStamp, //戳，自1970 年以来的秒数
                                        "nonceStr": pay_info.nonceStr, //随机串
                                        "package": pay_info.package,
                                        "signType": pay_info.signType, //微信签名方式:
                                        "paySign": pay_info.paySign  //微信签名,
                                    }, function (res) {
                                        if (res.err_msg == "get_brand_wcpay_request:ok") {
                                            // 此处可以使用此方式判断前端返回,微信团队郑重提示：res.err_msg 将在用户支付成功后返回ok，但并不保证它绝对可靠。
                                            location.href = "/pay/success.html";
                                        }
                                    }
                                )  
                            }

                            if (typeof WeixinJSBridge == "undefined"){
                                if( document.addEventListener ){
                                    document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                                }else if (document.attachEvent){
                                    document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
                                    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                                }
                             }else{
                                onBridgeReady();
                             }
        				}else{
        					alert(r.errmsg);
        				}
        			}
        		});
        	}
        	
        	if(order.type == 2){
        		$.ajax({
        			type: "POST",
        			url: baseURL + "/wxpay/payment/h5",
        			data: {
        				group: order.group,
        				orderNo: order.outTradeNo,
        				descr: order.subject,
        				totalFee: order.totalAmount,
        				userId: order.userId,
        				attach: order.body,
        				wapUrl: order.wapUrl,
        				wapName: order.wapName,
        				spbill_create_ip: order.spbill_create_ip
        			},
        			success: function(r){
        				if(r.errcode == 0){
        					var pay_info = r.data;
        					console.info(pay_info);
        					window.location.href=pay_info.mweb_url+"&redirect_url="+encodeURI("http://www.wechatpay.com.cn");
        				}else{
        					alert(r.errmsg);
        				}
        			}
        		});
        	}
        	
        	if(order.type == 3){
        		$.ajax({
        			type: "POST",
        			url: baseURL + "/wxpay/payment/qrcode",
        			data: {
        				group: order.group,
        				orderNo: order.outTradeNo,
        				descr: order.subject,
        				totalFee: order.totalAmount,
        				userId: order.userId,
        				attach: order.body,
        				spbill_create_ip: order.spbill_create_ip
        			},
        			success: function(r){
        				if(r.errcode == 0){
        					var pay_info = r.data;
        					console.info(pay_info);
        					var openurl = "/wxpay/qrcode/show?code_url="+pay_info.code_url;
        					
        					window.open(openurl);
        				}else{
        					alert(r.errmsg);
        				}
        			}
        		});
        	}
        	
        	if(order.type == 4){
        		$.ajax({
        			type: "POST",
        			url: baseURL + "/wxpay/payment/app/",
        			data: {
        				group: order.group,
        				orderNo: order.outTradeNo,
        				descr: order.subject,
        				totalFee: order.totalAmount,
        				userId: order.userId,
        				attach: order.body,
        				spbill_create_ip: order.spbill_create_ip
        			},
        			success: function(r){
        				if(r.errcode == 0){
        					var pay_info = r.data;
        					console.info(pay_info);
        				}else{
        					alert(r.errmsg);
        				}
        			}
        		});
        	}
        }
    }
});


var config = {
    id: "configTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
config.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '分组', field: 'group', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '微信AppID', field: 'appid', visible: false, align: 'center', valign: 'middle', width: '180px'},
        {title: '密钥', field: 'secret', align: 'center', valign: 'middle', sortable: true, width: '360px'},
        {title: '商户号', field: 'mchid', align: 'center', valign: 'middle', sortable: true, width: '160px'},
        {title: '异步回调URL', field: 'notifyUrl', align: 'center', valign: 'middle', sortable: true, width: '280px'},
		{title: '队列名', field: 'queueName', align: 'center', valign: 'middle', sortable: true, width: '160px'},
		{title: '创建时间', field: 'createTime',align: 'center', valign: 'middle', sortable: true}
	];
    return columns;
};


function getConfigGroup () {
    var selected = $('#'+config.id).bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}


$(function () {
    var colunms = config.initColumn();
    var table = new TreeTable(config.id, baseURL + "wxpay/config/list", colunms);
    table.setExpandColumn(2);
    table.setIdField("group");
    table.setCodeField("group");
    table.setExpandAll(false);
    table.init();
    config.table = table;
});
