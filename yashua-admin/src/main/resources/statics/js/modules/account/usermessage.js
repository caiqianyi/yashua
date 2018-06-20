$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'account/usermessage/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '消息标题', name: 'title', index: 'title', width: 80 }, 			
			{ label: '开始时间', name: 'startTime', index: 'start_time', width: 80 }, 			
			{ label: '结束时间', name: 'endTime', index: 'end_time', width: 80 }, 			
			{ label: '内容', name: 'content', index: 'content', width: 80 }, 			
			{ label: '消息时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '发送人', name: 'createBy', index: 'create_by', width: 80 }, 			
			{ label: '接收人', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '类型  0：个人   1：系统', name: 'type', index: 'type', width: 80 }, 			
			{ label: '是否删除  -1：已删除  0：正常', name: 'delFlag', index: 'del_flag', width: 80 }, 			
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
        	if((!vm.q.userId || vm.q.userId.length == 0)
        			&& (!vm.selected || vm.selected.length == 0) ){
        		var types = new Set($("#jqGrid").jqGrid('getCol',"type"));
        		var d = vm.types.slice(0,1);
        		for(var i=0;i<types.length;i++){
        			d[d.length] = {text: types[i], value: types[i]};
        		}
        	}
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		userMessage: {},
		q:{
			userId: null
		},
		selected: '',
		types: [
		    { text: '消息类型', value: '' },
		]
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.userMessage = {};
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
			var url = vm.userMessage.id == null ? "account/usermessage/save" : "account/usermessage/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.userMessage),
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
				    url: baseURL + "account/usermessage/delete",
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
			$.get(baseURL + "account/usermessage/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.userMessage = r.data.userMessage;
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