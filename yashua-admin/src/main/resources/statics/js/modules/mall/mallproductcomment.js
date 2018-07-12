$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallproductcomment/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '商品ID', name: 'productId', index: 'product_id', width: 80 }, 			
			{ label: '用户ID', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '昵称', name: 'nickname', index: 'nickname', width: 80 }, 			
			{ label: '用户头像', name: 'headimgurl', index: 'headimgurl', width: 80 }, 			
			{ label: '订单ID', name: 'orderId', index: 'order_id', width: 80 }, 			
			{ label: '评论星级：1,2,3,4,5', name: 'star', index: 'star', width: 80 }, 			
			{ label: '评论内容', name: 'content', index: 'content', width: 80 }, 			
			{ label: '好评数', name: 'praises', index: 'praises', width: 80 }, 			
			{ label: '状态：1.显示；0.隐藏', name: 'status', index: 'status', width: 80 }, 			
			{ label: '评论类型：1,优质；0,普通', name: 'type', index: 'type', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '创建者', name: 'createBy', index: 'create_by', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			{ label: '更新者', name: 'updateBy', index: 'update_by', width: 80 }, 			
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
		mallProductComment: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallProductComment = {};
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
			var url = vm.mallProductComment.id == null ? "mall/mallproductcomment/save" : "mall/mallproductcomment/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallProductComment),
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
				    url: baseURL + "mall/mallproductcomment/delete",
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
			$.get(baseURL + "mall/mallproductcomment/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallProductComment = r.data.mallProductComment;
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