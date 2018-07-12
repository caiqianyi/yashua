$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallproductparameter/list',
        datatype: "json",
        colModel: [			
			{ label: 'productParameterId', name: 'productParameterId', index: 'product_parameter_id', width: 50, key: true },
			{ label: '商品ID', name: 'productId', index: 'product_id', width: 80 }, 			
			{ label: '参数名', name: 'name', index: 'name', width: 80 }, 			
			{ label: '参数值', name: 'value', index: 'value', width: 80 }, 			
			{ label: '状态：1.显示；0.隐藏', name: 'status', index: 'status', width: 80 }, 			
			{ label: '排序', name: 'sort', index: 'sort', width: 80 }, 			
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
		mallProductParameter: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallProductParameter = {};
		},
		update: function (event) {
			var productParameterId = getSelectedRow();
			if(productParameterId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(productParameterId)
		},
		saveOrUpdate: function (event) {
			var url = vm.mallProductParameter.productParameterId == null ? "mall/mallproductparameter/save" : "mall/mallproductparameter/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallProductParameter),
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
			var productParameterIds = getSelectedRows();
			if(productParameterIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "mall/mallproductparameter/delete",
                    contentType: "application/json",
				    data: JSON.stringify(productParameterIds),
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
		getInfo: function(productParameterId){
			$.get(baseURL + "mall/mallproductparameter/info/"+productParameterId, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallProductParameter = r.data.mallProductParameter;
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