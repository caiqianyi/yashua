$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallproductattr/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '商品ID', name: 'productId', index: 'product_id', width: 80 }, 			
			{ label: '总库存', name: 'stock', index: 'stock', width: 80 }, 			
			{ label: '销售量', name: 'salesVolume', index: 'sales_volume', width: 80 }, 			
			{ label: '游览量', name: 'clicks', index: 'clicks', width: 80 }, 			
			{ label: '评论数量', name: 'replies', index: 'replies', width: 80 }, 			
			{ label: '晒单评价数量', name: 'showReplies', index: 'show_replies', width: 80 }, 			
			{ label: '平均评价', name: 'commentAverage', index: 'comment_average', width: 80 }, 			
			{ label: '收藏数', name: 'favoriteNumber', index: 'favorite_number', width: 80 }, 			
			{ label: '提问数', name: 'questionNumber', index: 'question_number', width: 80 }, 			
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
		mallProductAttr: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallProductAttr = {};
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
			var url = vm.mallProductAttr.id == null ? "mall/mallproductattr/save" : "mall/mallproductattr/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallProductAttr),
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
				    url: baseURL + "mall/mallproductattr/delete",
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
			$.get(baseURL + "mall/mallproductattr/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallProductAttr = r.data.mallProductAttr;
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