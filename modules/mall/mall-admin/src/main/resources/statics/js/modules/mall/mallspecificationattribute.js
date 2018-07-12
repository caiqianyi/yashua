$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallspecificationattribute/list',
        datatype: "json",
        colModel: [			
			{ label: 'specAttrId', name: 'specAttrId', index: 'spec_attr_id', width: 50, key: true },
			{ label: '规格ID', name: 'specificationId', index: 'specification_id', width: 80 }, 			
			{ label: '规格属性名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '创建者', name: 'createBy', index: 'create_by', width: 80 }, 			
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
		mallSpecificationAttribute: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallSpecificationAttribute = {};
		},
		update: function (event) {
			var specAttrId = getSelectedRow();
			if(specAttrId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(specAttrId)
		},
		saveOrUpdate: function (event) {
			var url = vm.mallSpecificationAttribute.specAttrId == null ? "mall/mallspecificationattribute/save" : "mall/mallspecificationattribute/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallSpecificationAttribute),
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
			var specAttrIds = getSelectedRows();
			if(specAttrIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "mall/mallspecificationattribute/delete",
                    contentType: "application/json",
				    data: JSON.stringify(specAttrIds),
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
		getInfo: function(specAttrId){
			$.get(baseURL + "mall/mallspecificationattribute/info/"+specAttrId, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallSpecificationAttribute = r.data.mallSpecificationAttribute;
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