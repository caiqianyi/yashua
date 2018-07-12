$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallspecification/list',
        datatype: "json",
        colModel: [			
			{ label: 'specificationId', name: 'specificationId', index: 'specification_id', width: 50, key: true },
			{ label: '分类ID', name: 'categoryId', index: 'category_id', width: 80 }, 			
			{ label: '规格名称', name: 'name', index: 'name', width: 80 }, 			
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
		mallSpecification: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallSpecification = {};
		},
		update: function (event) {
			var specificationId = getSelectedRow();
			if(specificationId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(specificationId)
		},
		saveOrUpdate: function (event) {
			var url = vm.mallSpecification.specificationId == null ? "mall/mallspecification/save" : "mall/mallspecification/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallSpecification),
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
			var specificationIds = getSelectedRows();
			if(specificationIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "mall/mallspecification/delete",
                    contentType: "application/json",
				    data: JSON.stringify(specificationIds),
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
		getInfo: function(specificationId){
			$.get(baseURL + "mall/mallspecification/info/"+specificationId, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallSpecification = r.data.mallSpecification;
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