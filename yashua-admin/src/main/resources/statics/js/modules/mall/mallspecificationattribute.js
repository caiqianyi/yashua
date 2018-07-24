$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallspecificationattribute/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'specAttrId', index: 'spec_attr_id', width: 50, key: true },
			{ label: '规格名稱', name: 'specificationName', index: 'specification_name', width: 80 }, 			
			{ label: '规格属性名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}, 			
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
var ztree;
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "specificationId",
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
		mallSpecificationAttribute: {
			specificationName: "",
			specificationId: 0,
			name: ""
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallSpecificationAttribute = {
				specificationName: "",
				specificationId: 0,
				name: ""
			};
			vm.getSpecification();
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
                vm.getSpecification();
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
		selectSpecification: function(){
			layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择分类",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#specificationLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.mallSpecificationAttribute.specificationId = node[0].specificationId;
                    vm.mallSpecificationAttribute.specificationName = node[0].name;
                    layer.close(index);
                }
            });
		},
		getSpecification: function(){
            //加载菜单树
            $.get(baseURL + "mall/mallspecification/select", function(r){
            	if(r.errcode && r.errcode != 0){
            		return;
            	}
            	var mallspecifications = r.data;
            	ztree = $.fn.zTree.init($("#specificationTree"), setting, mallspecifications);
                var node = ztree.getNodeByParam("specificationId", vm.mallSpecificationAttribute.specificationId);
                if(node)
                	vm.mallSpecificationAttribute.specificationName = node.name;
            })
        }
	}
});