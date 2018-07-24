$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallproductspecification/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'productSpecId', index: 'product_spec_id', width: 50, key: true },
			{ label: '商品ID', name: 'productId', index: 'product_id', width: 80 }, 			
			{ label: '规格ID', name: 'specificationId', index: 'spec', width: 80 }, 			
			{ label: '规格属性ID', name: 'specAttrId', index: 'spec', width: 80 }, 			
			{ label: '库存', name: 'stock', index: 'stock', width: 80 }, 			
			{ label: '销售量', name: 'salesVolume', index: 'sales_volume', width: 80 }, 			
			{ label: '价格', name: 'price', index: 'price', width: 80 }, 			
			{ label: '积分', name: 'score', index: 'score', width: 80 }, 			
			{ label: '是否默认状态', name: 'defaultStatus', index: 'default_status', width: 80 , formatter: function(value){
	            if(value == 0){
	                return '<span class="label label-primary">不默认</span>';
	            }
	            if(value == 1){
	                return '<span class="label label-success">默认</span>';
	            }
	        }}, 			
			{ label: '商品状态', name: 'status', index: 'status', width: 80 , formatter: function(value){
	            if(value == 0){
	                return '<span class="label label-primary">新增</span>';
	            }
	            if(value == 1){
	                return '<span class="label label-success">上架</span>';
	            }
	            if(value == 2){
	            	return '<span class="label label-primary">下架</span>';
	            }
	        }}, 			
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
var ztree;
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
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		mallProductSpecification: {
			productSpecNumber:0,
			productId: 0,
			spec: 0,
			specName: "",
			stock: 0,
			salesVolume: 0,
			price: 0,
			score: 0,
			defaultStatus: 0,
			status: 0
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.getSpecification();
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
			if(!vm.mallProductSpecification.specAttrId){
				alert("请选择商品规格!");
				return;
			}
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
                vm.getSpecification(r.data.mallProductSpecification);
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
                    if(!node[0].specAttrId){
                    	alert("请选择二级规格！");
                    	return;
                    }
                    vm.mallProductSpecification.specificationId = node[0].specificationId;
                    vm.mallProductSpecification.specAttrId = node[0].specAttrId;
                    vm.mallProductSpecification.specName = node[0].name;
                    layer.close(index);
                }
            });
		},
		getSpecification: function(specInfo){
            //加载菜单树
            $.get(baseURL + "mall/mallspecification/select", function(r){
            	if(r.errcode && r.errcode != 0){
            		return;
            	}
            	var mallspecifications = [];
            	var datas = r.data;
            	for(var i=0;i<datas.length;i++){
            		var item = datas[i];
            		var parentId = mallspecifications.length;
            		mallspecifications[parentId] = {
            			id: parentId,
        				specificationId: item.specificationId,
        				specAttrId: null,
        				parentName: null,
            			name: item.name,
            			sort: item.sort
            		};
            		var attrs = item.attrs;
            		for(var k=0;k<attrs.length;k++){
            			var attr = attrs[k];
            			mallspecifications[mallspecifications.length] = {
        					id: mallspecifications.length,
            				specificationId: item.specificationId,
            				specAttrId: attr.specAttrId,
            				parentId: parentId,
            				parentName: item.name,
            				name: attr.name
                		};
            		}
            	}
            	ztree = $.fn.zTree.init($("#specificationTree"), setting, mallspecifications);
            	
            	if(!specInfo){
            		specInfo = {
        				productSpecNumber:0,
        				productId: 0,
        				spec: 0,
        				specName: "",
        				stock: 0,
        				salesVolume: 0,
        				price: 0,
        				score: 0,
        				defaultStatus: 0,
        				status: 0
        			};
            	}
                if(vm.mallProductSpecification.specAttrId){
                	var node = ztree.getNodeByParam("specAttrId", vm.mallProductSpecification.specAttrId);
                	ztree.selectNode(node);
                	specInfo.specificationId = node.specificationId;
                	specInfo.specName = node.name;
                }
                
                vm.mallProductSpecification = specInfo;
            })
        }
	}
});