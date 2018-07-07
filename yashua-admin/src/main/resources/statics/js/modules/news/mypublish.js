var um;
$(function () {
	
    um = UM.getEditor('myEditor');
    $("#jqGrid").jqGrid({
        url: baseURL + 'news/news/mylist',
        datatype: "json",
        colModel: [			
			{ label: '发布人', name: 'uId', index: 'u_id', width: 80 }, 			
			{ label: '作者', name: 'author', index: 'author', width: 80 }, 			
			{ label: '标题', name: 'title', index: 'title', width: 80 }, 			
			{ label: '分类ID', name: 'classId', index: 'class_id', width: 80 }, 			
			{ label: '图片', name: 'picItems', index: 'pic_items', width: 80 }, 			
			{ label: '点击数', name: 'clicks', index: 'clicks', width: 80 }, 			
			{ label: '点赞数', name: 'praises', index: 'praises', width: 80 }, 			
			{ label: '回复数', name: 'replies', index: 'replies', width: 80 }, 			
			{ label: '是否置顶', name: 'isTop', index: 'is_top', width: 80 , formatter: function(value, options, row){
				return value == 0 ? 
						'<span class="label label-danger">否</span>' : 
						'<span class="label label-success">是</span>';
			}}, 			
			{ label: '审核状态', name: 'checkStatus', index: 'check_status', width: 80 , formatter: function(value, options, row){
				return value == 0 ? 
						'<span class="label label-danger">未通过 </span>' : 
						'<span class="label label-success">已通过</span>';
			}}, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}
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
		news: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.news = {};
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
			var url = vm.news.id == null ? "news/news/save" : "news/news/update";
			var content = um.getContent();
			
			var pic_items = "";
			$("img",um.getContent()).each(function(){
				pic_items += $(this).attr("src")+",";
			})
			if(pic_items.length > 0){
				pic_items.substring(0,pic_items.length-1);
			}
			
			vm.news.content = content;
			vm.news.picItems = pic_items;
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.news),
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
				    url: baseURL + "news/news/delete",
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
			$.get(baseURL + "news/news/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.news = r.data.news;
                um.setContent(vm.news.content);
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