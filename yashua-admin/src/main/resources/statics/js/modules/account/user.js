$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'account/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '用户账号', name: 'account', index: 'account', width: 80 }, 			
			{ label: '注册来源', name: 'source', index: 'source', width: 80 }, 			
			{ label: '状态', name: 'status', index: 'status', width: 80  , formatter: function(value){
				return "Y" == value ? "正常" : "禁用";
			}}, 			
			/*{ label: '账户等级', name: 'level', index: 'level', width: 80 },*/	
			{ label: '账户余额', name: 'balance', index: 'balance', width: 80 }, 			
			{ label: '是否关注', name: 'subscribe', index: 'subscribe', width: 80 }, 			
			{ label: 'openid', name: 'openid', index: 'openid', width: 80 }, 			
			{ label: '昵称', name: 'nickname', index: 'nickname', width: 80 }, 			
			{ label: '性别', name: 'sex', index: 'sex', width: 80 }, 			
			{ label: '城市', name: 'city', index: 'city', width: 80 }, 			
			{ label: '国家', name: 'country', index: 'country', width: 80 }, 			
			{ label: '省份', name: 'province', index: 'province', width: 80 }, 			
			{ label: '头像', name: 'headimgurl', index: 'headimgurl', width: 80 , formatter: function(value){
				return value ? "<img src=\""+value+"\" width=\"50\" height=\"50\"/>" : "";
			}}, 			
			{ label: '关注时间', name: 'subscribeTime', index: 'subscribe_time', width: 90 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ label: '注册时间', name: 'createTime', index: 'create_time', width: 90 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ label: '最后登录时间', name: 'lastLoginTime', index: 'last_login_time', width: 90 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ label: '最后购买时间', name: 'lastBuyTime', index: 'last_buy_time', width: 90 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}
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
            page:"page", 
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
		user: {account:null},
		newPasswd: null,
		account: null,
		amount: null,
		q:{
			account: null
		}
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
		lock: function (){
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			var item = $("#jqGrid").jqGrid('getRowData',id);
			var text = "确定要禁用此用户"+item.account+'？';
			if(item.status == "禁用"){
				text = "此用户已是禁用状态，本次操作将为启用！确定要启用用户"+item.account+'吗？';
			}
			confirm(text, function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "account/user/disabled",
				    data: {userId:item.userId},
				    success: function(r){
						if(r.errcode == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							parent.layer.closeAll();
							alert(r.errmsg);
						}
					}
				});
			});
		},
		resetPassword: function(){
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			var item = $("#jqGrid").jqGrid('getRowData',id);
			vm.account = item.account;
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "重置密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "userId="+item.userId+"&newPasswd="+vm.newPasswd;
					$.ajax({
						type: "POST",
					    url: "/account/user/modifyPassword",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.errcode == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									layer.close(index);
									vm.reload();
								});
							}else{
								layer.alert(result.errmsg);
							}
						}
					});
	            }
			});
		},
		recharge: function(){
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			var item = $("#jqGrid").jqGrid('getRowData',id);
			vm.account = item.account;
			vm.amount = null;
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "手动充值",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#rechargeLayer"),
				btn: ['确定','取消'],
				btn1: function (index) {
					var data = "userId="+item.userId+"&amount="+vm.amount;
					$.ajax({
						type: "POST",
					    url: "/account/user/modifyBalance",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.errcode == 0){
								layer.close(index);
								layer.alert('操作成功', function(index){
									layer.close(index);
									vm.reload();
								});
							}else{
								layer.alert(result.errmsg);
							}
						}
					});
	            }
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.user.id == null ? "account/user/save" : "account/user/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.user),
			    success: function(r){
			    	if(r.errcode == 0){
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
							parent.layer.closeAll();
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
		handleExport(){
			window.open("/account/export/user.xlsx?token="+window.storage.get('login.access_token',false) );
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{"account": vm.q.account},
                page:page
            }).trigger("reloadGrid");
		}
	}
});