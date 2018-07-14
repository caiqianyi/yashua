var setting = {
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

function getCategoryId () {
    var selected = $('#categoryTree').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		mallCategory: {
            parentName:null,
            parentId:0,
            name: null,
            type:1,
            sort:0,
            status: 1
        }
	},
	methods: {
		getCategory: function(menuId){
            //加载菜单树
            $.get(baseURL + "mall/mallcategory/select", function(r){
            	if(r.errcode && r.errcode != 0){
            		return;
            	}
                ztree = $.fn.zTree.init($("#categoryTree"), setting, r.data);
                var node = ztree.getNodeByParam("id", vm.mallCategory.parentId);
                ztree.selectNode(node);
                if(node){
                	vm.mallCategory.parentName = node.name;
                }
            })
        },
        categoryTree: function(){
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
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    vm.mallCategory.parentId = node[0].id;
                    vm.mallCategory.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallCategory = {parentName:null,
		            parentId:0,
		            name: null,
		            type:1,
		            sort:0,
		            status: 1};
			vm.getCategory();
		},
		update: function (event) {
			var id = getCategoryId();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			if(vm.validator()){
                return ;
            }
			var url = vm.mallCategory.id == null ? "mall/mallcategory/save" : "mall/mallcategory/update";
			$.ajax({
				type: "POST",
				url: baseURL + url,
				contentType: "application/json",
				data: JSON.stringify(vm.mallCategory),
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
			var id = getCategoryId();
			if(id == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "mall/mallcategory/delete",
                    contentType: "application/json",
				    data: JSON.stringify([id]),
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
			});
		},
		getInfo: function(id){
			$.get(baseURL + "mall/mallcategory/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallCategory = r.data.mallCategory;
                vm.getCategory();
            });
		},
		reload: function (event) {
			vm.showList = true;
			Category.table.refresh();
		},
        validator: function () {
            if(isBlank(vm.mallCategory.name)){
                alert("名称不能为空");
                return true;
            }
            if(isBlank(vm.mallCategory.icon)){
                alert("图标");
                return true;
            }
        }
	}
});


var Category = {
	    id: "categoryTable",
	    table: null,
	    layerIndex: -1
	};

	/**
	 * 初始化表格的列
	 */
	Category.initColumn = function () {
	    var columns = [
	        {field: 'selectItem', radio: true},
	        { title: '分类ID', field: 'id', visible: false, align: 'center', valign: 'middle', width: 50, key: true },
            { title: '分类名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: 80 }, 			
			{ title: '上级分类', field: 'parentId', visible: false, align: 'center', valign: 'middle',width: 80 }, 			
			{ title: '分类图标', field: 'icon', visible: false, align: 'center', valign: 'middle',width: 160 }, 			
			{ title: '排序', field: 'sort', visible: false, align: 'center', valign: 'middle', width: 80 }, 			
			{ title: '分类类型', field: 'type', visible: false, align: 'center', valign: 'middle', width: 80 , formatter: function(item, index){
	            if(item.type == 0){
	                return '<span class="label label-primary">总目录</span>';
	            }
	            if(item.type == 1){
	                return '<span class="label label-success">一级分类</span>';
	            }
	            if(item.type == 2){
	                return '<span class="label label-warning">二级分类</span>';
	            }
	        }}, 			
			{ title: '状态', field: 'status', visible: false, align: 'center', valign: 'middle', width: 80 , formatter: function(item, index){
	            if(item.status == 0){
	                return '<span class="label label-primary">隐藏</span>';
	            }
	            if(item.status == 1){
	                return '<span class="label label-success">显示</span>';
	            }
	        }}, 			
			{ title: '创建时间', field: 'createTime', visible: false, align: 'center', valign: 'middle', width: 80 , formatter: function(item){
				return item.createTime ? new Date(item.createTime).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ title: '创建者', field: 'createBy', visible: false, align: 'center', valign: 'middle', width: 80 }, 			
			{ title: '更新时间', field: 'updateTime', index: 'update_time', width: 80 , formatter: function(item){
				return item.updateTime ? new Date(item.updateTime).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ title: '更新者', field: 'updateBy', visible: false, align: 'center', valign: 'middle', width: 80 }, 			
			{ title: '备注信息', field: 'remarks', index: 'remarks', width: 80 }
		];
	    return columns;
	};

	function getCategoryId () {
	    var selected = $('#categoryTable').bootstrapTreeTable('getSelections');
	    if (selected.length == 0) {
	        alert("请选择一条记录");
	        return null;
	    } else {
	        return selected[0].id;
	    }
	}

$(function () {
    var colunms = Category.initColumn();
    var table = new TreeTable(Category.id, baseURL + "mall/mallcategory/list", colunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    Category.table = table;
    
    new AjaxUpload('#upload', {
        action: baseURL + "/upload/imageUp?token="+window.storage.get('login.access_token',false),
        name: 'upfile',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.errcode == 0){
            	vm.mallCategory.icon = r.data.uri;
            	$("#upload").attr("src",r.data.uri);
            }else{
                alert(r.errmsg);
            }
        }
    });
});