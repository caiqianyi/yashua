$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'account/userlog/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户ID', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '用户账号', name: 'account', index: 'account', width: 80 },
			{ label: '日志时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '日志类型', name: 'logType', index: 'log_type', width: 80 }, 			
			{ label: '交易金额', name: 'tradeMoney', index: 'trade_money', width: 80 }, 			
			{ label: '账户余额', name: 'money', index: 'money', width: 80 }, 			
			{ label: '操作平台标识', name: 'platform', index: 'platform', width: 80 }, 			
			{ label: '日志发生IP', name: 'hostIp', index: 'host_ip', width: 80 }, 			
			{ label: '日志说明', name: 'descr', index: 'descr', width: 80 }, 			
			{ label: '日志参数', name: 'adjunctInfo', index: 'adjunct_info', width: 80 }, 			
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
        	
        	if((!vm.q.userId || vm.q.userId.length == 0)
        			&& (!vm.selected || vm.selected.length == 0) ){
        		var types = new Set($("#jqGrid").jqGrid('getCol',"type"));
        		var d = vm.types.slice(0,1);
        		for(var i=0;i<types.length;i++){
        			d[d.length] = {text: types[i], value: types[i]};
        		}
        	}
        	
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
		userLog: {},
		q:{
			userId: null
		},
		selected: '',
		types: [
		    { text: '日志类型', value: '' },
		]
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.userLog = {};
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
			var url = vm.userLog.id == null ? "account/userlog/save" : "account/userlog/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.userLog),
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
				    url: baseURL + "account/userlog/delete",
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
			$.get(baseURL + "account/userlog/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.userLog = r.data.userLog;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'userId': vm.q.userId, "type": vm.selected},
                page:page
            }).trigger("reloadGrid");
		}
	}
});