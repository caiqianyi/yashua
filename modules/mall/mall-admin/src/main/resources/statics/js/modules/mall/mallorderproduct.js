$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallorderproduct/list',
        datatype: "json",
        colModel: [			
			{ label: 'orderProductId', name: 'orderProductId', index: 'order_product_id', width: 50, key: true },
			{ label: '订单ID', name: 'orderId', index: 'order_id', width: 80 }, 			
			{ label: '商品编号', name: 'productNumber', index: 'product_number', width: 80 }, 			
			{ label: '商品名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '展示图片', name: 'picImg', index: 'pic_img', width: 80 }, 			
			{ label: '商品规格编号', name: 'productSpecNumber', index: 'product_spec_number', width: 80 }, 			
			{ label: '商品规格名称', name: 'productSpecName', index: 'product_spec_name', width: 80 }, 			
			{ label: '价格', name: 'price', index: 'price', width: 80 }, 			
			{ label: '积分', name: 'score', index: 'score', width: 80 }, 			
			{ label: '商品总数量', name: 'buyNumber', index: 'buy_number', width: 80 }, 			
			{ label: '商品总积分', name: 'productScore', index: 'product_score', width: 80 }, 			
			{ label: '商品总金额', name: 'productAmount', index: 'product_amount', width: 80 }, 			
			{ label: '订单状态 0=待发货，2=已发货，3=待评价，4=已评价', name: 'status', index: 'status', width: 80 }, 			
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
		mallOrderProduct: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallOrderProduct = {};
		},
		update: function (event) {
			var orderProductId = getSelectedRow();
			if(orderProductId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(orderProductId)
		},
		saveOrUpdate: function (event) {
			var url = vm.mallOrderProduct.orderProductId == null ? "mall/mallorderproduct/save" : "mall/mallorderproduct/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallOrderProduct),
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
			var orderProductIds = getSelectedRows();
			if(orderProductIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "mall/mallorderproduct/delete",
                    contentType: "application/json",
				    data: JSON.stringify(orderProductIds),
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
		getInfo: function(orderProductId){
			$.get(baseURL + "mall/mallorderproduct/info/"+orderProductId, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallOrderProduct = r.data.mallOrderProduct;
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