$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallcategoryproduct/list',
        datatype: "json",
        colModel: [			
			{ label: '商品编号', name: 'productNumber', index: 'product_number', width: 80 }, 			
			{ label: '商品名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '显示积分', name: 'showScore', index: 'show_score', width: 80 }, 			
			{ label: '显示价格', name: 'showPrice', index: 'show_price', width: 80 }, 			
			{ label: '展示图片', name: 'showPic', index: 'show_pic', width: 80 }, 			
			{ label: '商品简介', name: 'introduce', index: 'introduce', width: 80 }, 			
			{ label: '商品描述', name: 'description', index: 'description', width: 80 }, 			
			{ label: '是否置顶', name: 'showInTop', index: 'show_in_top', width: 80 , formatter: function(value){
			    if(value == 0){
			        return '<span class="label label-primary">默认</span>';
			    }
			    if(value == 1){
			        return '<span class="label label-success">置顶</span>';
			    }
			}}, 			
			{ label: '是否热门', name: 'showInHot', index: 'show_in_hot', width: 80 , formatter: function(value){
			    if(value == 0){
			        return '<span class="label label-primary">默认</span>';
			    }
			    if(value == 1){
			        return '<span class="label label-success">热门</span>';
			    }
			}}, 			
			{ label: '是否上架', name: 'showInShelve', index: 'show_in_shelve', width: 80 , formatter: function(value){
			    if(value == 0){
			        return '<span class="label label-primary">下架</span>';
			    }
			    if(value == 1){
			        return '<span class="label label-success">上架</span>';
			    }
			}}, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 , formatter: function(value){
				return value ? new Date(value).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ label: '创建者', name: 'createBy', index: 'create_by', width: 80 }, 			
			{ label: '上架时间', name: 'shelveTime', index: 'shelve_time', width: 80 , formatter: function(value){
				return value ? new Date(value).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ label: '上架人', name: 'shelveBy', index: 'shelve_by', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 , formatter: function(value){
				return value ? new Date(value).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ label: '更新者', name: 'updateBy', index: 'update_by', width: 80 }, 			
			{ label: '搜索关键词', name: 'searchKey', index: 'search_key', width: 80 }, 			
			{ label: '分享标题', name: 'shareTitle', index: 'share_title', width: 80 }, 			
			{ label: '分享描述', name: 'shareDescription', index: 'share_description', width: 80 }, 			
			{ label: '备注', name: 'remarks', index: 'remarks', width: 80 }, 			
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
		mallCategoryProduct: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallCategoryProduct = {};
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
			var url = vm.mallCategoryProduct.id == null ? "mall/mallcategoryproduct/save" : "mall/mallcategoryproduct/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallCategoryProduct),
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
				    url: baseURL + "mall/mallcategoryproduct/delete",
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
			$.get(baseURL + "mall/mallcategoryproduct/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallCategoryProduct = r.data.mallCategoryProduct;
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