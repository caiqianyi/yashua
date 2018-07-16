$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallspecification/list',
        datatype: "json",
        colModel: [			
			{ label: '规格ID', name: 'specificationId', index: 'specification_id', width: 50, key: true },
			{ label: '分类', name: 'categoryName', index: 'category_name', width: 80 }, 			
			{ label: '规格名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '状态', name: 'status', index: 'status', width: 80 , formatter: function(value){
	            if(value == 0){
	                return '<span class="label label-primary">隐藏</span>';
	            }
	            if(value == 1){
	                return '<span class="label label-success">显示</span>';
	            }
	        }}, 			
			{ label: '排序', name: 'sort', index: 'sort', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ label: '创建者', name: 'createBy', index: 'create_by', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}, 			
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
//分类树
var category_ztree;
var category_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
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
			vm.mallSpecification = {
				categoryName: "",
				categoryId: 0,
				name: "",
				status: 0,
				sort: 0
			};
			vm.getCategory();
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
                vm.getCategory();
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
		selectCategory: function(){
			layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择分类",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#categoryLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = category_ztree.getSelectedNodes();
                    //选择上级部门
                    vm.mallSpecification.categoryId = node[0].id;
                    vm.mallSpecification.categoryName = node[0].name;
                    layer.close(index);
                }
            });
		},
		getCategory: function(){
            //加载菜单树
            $.get(baseURL + "mall/mallcategory/select", function(r){
            	if(r.errcode && r.errcode != 0){
            		return;
            	}
            	var categorys = r.data;
            	category_ztree = $.fn.zTree.init($("#categoryTree"), category_setting, categorys);
                var node = category_ztree.getNodeByParam("id", vm.mallSpecification.categoryId);
                category_ztree.selectNode(node);
                console.info(node);
                vm.mallSpecification.categoryName = node.name;
            })
        }
	}
});