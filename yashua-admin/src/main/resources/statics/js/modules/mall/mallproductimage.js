$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallproductimage/list',
        datatype: "json",
        colModel: [			
            { label: 'ID', name: 'picImgId', index: 'pic_img_id', width: 80, key:true }, 			
			{ label: '商品ID', name: 'productId', index: 'product_id', width: 80 }, 			
			{ label: '展示图片', name: 'picImg', index: 'pic_img', width: 80 }, 			
			{ label: '排序', name: 'sort', index: 'sort', width: 80 }, 			
			{ label: '状态', name: 'status', index: 'status', width: 80 }, 			
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
            	vm.mallProductImage.picImg = r.data.uri;
            	$("#upload").attr("src",r.data.uri);
            }else{
                alert(r.errmsg);
            }
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		q:{
			productId: null
		},
		mallProductImage: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallProductImage = {};
		},
		update: function (event) {
			var picImgId = getSelectedRow();
			if(picImgId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(picImgId)
		},
		saveOrUpdate: function (event) {
			var url = vm.mallProductImage.picImgId == null ? "mall/mallproductimage/save" : "mall/mallproductimage/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallProductImage),
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
			var picImgId = getSelectedRow();
			if(picImgId == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "mall/mallproductimage/delete",
				    data: {picImgId:picImgId},
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
		getInfo: function(picImgId){
			$.get(baseURL + "mall/mallproductimage/info/"+picImgId, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallProductImage = r.data.mallProductImage;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{"productId": vm.q.productId},
                page:page
            }).trigger("reloadGrid");
		}
	}
});