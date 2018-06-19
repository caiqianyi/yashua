$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'account/user/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户ID', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '用户账号', name: 'account', index: 'account', width: 80 }, 			
			{ label: '手机号', name: 'mobile', index: 'mobile', width: 80 }, 			
			{ label: '密码', name: 'password', index: 'password', width: 80 }, 			
			{ label: '注册来源', name: 'source', index: 'source', width: 80 }, 			
			{ label: '账号类型', name: 'type', index: 'type', width: 80 }, 			
			{ label: 'Y 正常 N 禁用  F 异常冻结', name: 'status', index: 'status', width: 80 }, 			
			{ label: '登录信息', name: 'host', index: 'host', width: 80 }, 			
			{ label: '账户等级', name: 'level', index: 'level', width: 80 }, 			
			{ label: '账户金额', name: 'balance', index: 'balance', width: 80 }, 			
			{ label: '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息', name: 'subscribe', index: 'subscribe', width: 80 }, 			
			{ label: '微信openid', name: 'openid', index: 'openid', width: 80 }, 			
			{ label: '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段', name: 'unionid', index: 'unionid', width: 80 }, 			
			{ label: '用户的昵称', name: 'nickname', index: 'nickname', width: 80 }, 			
			{ label: '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知', name: 'sex', index: 'sex', width: 80 }, 			
			{ label: '用户所在城市', name: 'city', index: 'city', width: 80 }, 			
			{ label: '用户所在国家', name: 'country', index: 'country', width: 80 }, 			
			{ label: '用户所在省份', name: 'province', index: 'province', width: 80 }, 			
			{ label: '用户的语言，简体中文为zh_CN', name: 'language', index: 'language', width: 80 }, 			
			{ label: '户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。', name: 'headimgurl', index: 'headimgurl', width: 80 }, 			
			{ label: '用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间', name: 'subscribeTime', index: 'subscribe_time', width: 80 }, 			
			{ label: '公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '用户所在的分组ID（兼容旧的用户分组接口）', name: 'groupid', index: 'groupid', width: 80 }, 			
			{ label: '注册时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '最后登录时间', name: 'lastLoginTime', index: 'last_login_time', width: 80 }, 			
			{ label: '最后购买时间', name: 'lastBuyTime', index: 'last_buy_time', width: 80 }, 			
			{ label: '最后修改时间', name: 'lastUpdateTime', index: 'last_update_time', width: 80 }, 			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
        	root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames : {
            page:"data", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		user: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.user = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.user.id == null ? "account/user/save" : "account/user/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.user),
			    success: function(r){
			    	if(r.errcode === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.errmsg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "account/user/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.errcode == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.errmsg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "account/user/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.user = r.data.user;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});