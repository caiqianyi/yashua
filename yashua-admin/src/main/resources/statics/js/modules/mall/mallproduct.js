$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallproduct/list',
        datatype: "json",
        colModel: [			
            { label: '商品ID', name: 'id', index: 'id', width: 80 }, 			
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
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ label: '创建者', name: 'createBy', index: 'create_by', width: 80 }, 			
			{ label: '上架时间', name: 'shelveTime', index: 'shelve_time', width: 80 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ label: '上架人', name: 'shelveBy', index: 'shelve_by', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
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
    
    vm.getCategory();
    
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
            	vm.mallProduct.showPic = r.data.uri;
            	$("#upload").attr("src",r.data.uri);
            }else{
                alert(r.errmsg);
            }
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
    },
    check:{
        enable:true,
        nocheckInherit:true
    }
};

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		attrShowList: true,//编辑属性
		title: null,
		attrTitle: null,
		selected: '',
		q:{
			productNumber: null
		},
		types: [
		    { text: '商品分类', value: '' },
		],
		mallProduct: {
			productNumber: null,
			name: null,
			showScore: 0,
			showPrice: 0,
			showInTop: 0,
			showInHot: 0,
			showInShelve: 0,
			showPic: "",
			introduce: "",
			description: "",
			searchKey: "",
			shareTitle: "",
			shareDescription: "",
			remarks: ""
		},
		mallProductAttr: {
			productId: 0,
			stock: 0,
			salesVolume: 0,
			clicks: 0,
			replies: 0,
			showReplies: 0,
			commentAverage: 0,
			favoriteNumber: 0,
			questionNumber: 0
		},
		mallCategoryProducts: []
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.attrShowList = true;
			vm.title = "新增";
			vm.mallProduct = {
    			productNumber: null,
    			name: null,
    			showScore: 0,
    			showPrice: 0,
    			showInTop: 0,
    			showInHot: 0,
    			showInShelve: 0,
    			showPic: "",
    			introduce: "",
    			description: "",
    			searchKey: "",
    			shareTitle: "",
    			shareDescription: "",
    			remarks: ""
    		};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
			vm.attrShowList = true;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		updateAttr: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.attrShowList = false;
            vm.title = "编辑属性";
            
            vm.getAttrInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.mallProduct.id == null ? "mall/mallproduct/save" : "mall/mallproduct/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallProduct),
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
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "mall/mallproduct/delete",
				    data: {id: id},
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
			$.get(baseURL + "mall/mallproduct/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallProduct = r.data.mallProduct;
            });
		},
		getAttrInfo: function(id){
			$.get(baseURL + "mall/mallproductattr/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallProductAttr = r.data.mallProductAttr;
            });
		},
		attrSaveOrUpdate: function (event) {
			var url = "mall/mallproductattr/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallProductAttr),
			    success: function(r){
			    	if(r.errcode == 0){
						alert('操作成功', function(index){
							vm.getAttrInfo(vm.mallProductAttr.productId);
						});
					}else{
						alert(r.errmsg);
					}
				}
			});
		},
		attrReload: function(){
			vm.showList = true;
			vm.attrShowList = true;
		},
		reload: function (event) {
			vm.showList = true;
			vm.attrShowList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{"productNumber": vm.q.productNumber, "categoryId": vm.selected},
                page:page
            }).trigger("reloadGrid");
		},
		selectCategory: function(){
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
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
                    var nodes = category_ztree.getCheckedNodes(true);
                    if(nodes.length == 0){
                    	alert("请选择一个分类");
                    	return;
                    }
                    console.info(nodes);
                    //选择上级部门
                    /*vm.role.deptId = node[0].deptId;
                    vm.role.deptName = node[0].name;*/
                    var categoryIds = [];
                    for(var i=0;i<nodes.length;i++){
                    	categoryIds[i] = nodes[i].id;
                    }
                    var url = "/mall/mallcategoryproduct/edit";
        			$.ajax({
        				type: "POST",
        			    url: baseURL + url,
        			    data: {
        			    	productId: id,
        			    	categoryIds:categoryIds.toString()
        			    },
        			    success: function(r){
        			    	if(r.errcode == 0){
        						alert('操作成功', function(i){
        							layer.close(index);
        						});
        					}else{
        						alert(r.errmsg);
        					}
        				}
        			});
                    
                    
                }
            });
			
			$.get(baseURL + "mall/mallcategoryproduct/tree/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallCategoryProducts = r.data;
                vm.getCategory();
            });
		},
		getCategory: function(){
            //加载菜单树
            $.get(baseURL + "mall/mallcategory/select", function(r){
            	if(r.errcode && r.errcode != 0){
            		return;
            	}
            	var categorys = r.data;
            	vm.types = [ { text: '商品分类', value: '' }];
            	var d = vm.types.slice(0,1);
        		for(var i=0;i<categorys.length;i++){
        			d[d.length] = {text: categorys[i].name, value: categorys[i].id};
        		}
        		vm.types = d;
            	category_ztree = $.fn.zTree.init($("#categoryTree"), category_setting, categorys);
            	//展开所有节点
            	category_ztree.expandAll(true);
                var mcps = vm.mallCategoryProducts;
                for(var i=0; i<mcps.length; i++) {
					var node = category_ztree.getNodeByParam("id", mcps[i].categoryId);
					category_ztree.checkNode(node, true, false);
				}
            })
        },
	}
});