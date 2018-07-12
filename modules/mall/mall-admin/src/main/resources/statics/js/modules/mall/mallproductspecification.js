$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallproductspecification/list',
        datatype: "json",
        colModel: [			
			{ label: 'productSpecId', name: 'productSpecId', index: 'product_spec_id', width: 50, key: true },
			{ label: '商品规格编号', name: 'productSpecNumber', index: 'product_spec_number', width: 80 }, 			
			{ label: '商品ID', name: 'productId', index: 'product_id', width: 80 }, 			
			{ label: '规格：规格ID，以“,”相隔', name: 'spec', index: 'spec', width: 80 }, 			
			{ label: '库存', name: 'stock', index: 'stock', width: 80 }, 			
			{ label: '销售量', name: 'salesVolume', index: 'sales_volume', width: 80 }, 			
			{ label: '价格', name: 'price', index: 'price', width: 80 }, 			
			{ label: '积分', name: 'score', index: 'score', width: 80 }, 			
			{ label: '是否默认状态：0,不默认；1,默认', name: 'defaultStatus', index: 'default_status', width: 80 }, 			
			{ label: '商品状态：0,新增；1,上架；2,下架', name: 'status', index: 'status', width: 80 }, 			
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
		mallProductSpecification: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallProductSpecification = {};
		},
		update: function (event) {
			var productSpecId = getSelectedRow();
			if(productSpecId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(productSpecId)
		},
		saveOrUpdate: function (event) {
			var url = vm.mallProductSpecification.productSpecId == null ? "mall/mallproductspecification/save" : "mall/mallproductspecification/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallProductSpecification),
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
			var productSpecIds = getSelectedRows();
			if(productSpecIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "mall/mallproductspecification/delete",
                    contentType: "application/json",
				    data: JSON.stringify(productSpecIds),
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
		getInfo: function(productSpecId){
			$.get(baseURL + "mall/mallproductspecification/info/"+productSpecId, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallProductSpecification = r.data.mallProductSpecification;
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