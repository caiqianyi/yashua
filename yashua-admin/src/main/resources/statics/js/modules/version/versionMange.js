var um;
$(function () {
    um = UM.getEditor('myEditor');
    $("#jqGrid").jqGrid({
        url: baseURL + 'version/version/list',
        datatype: "json",
        colModel: [			
			{ label: '版本号', name: 'versionNumber', index: 'version_number', width: 80 }, 			
			{ label: '版本下载地址', name: 'url', index: 'url', width: 80 }, 			
			{ label: '版本类型', name: 'versionType', index: 'version_type', width: 80 }, 			
			{ label: '发布时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '更新说明', name: 'updateInstruction', index: 'update_instruction', width: 80 }, 			
			{ label: '下载人数', name: 'downloadUsers', index: 'download_users', width: 80 }, 			
			{ label: '是否强制更新', name: 'forcedUpdate', index: 'forced_update', width: 80 , formatter: function(value, options, row){
				return value == 0 ? 
						'<span class="label label-danger">自愿更新</span>' : 
						'<span class="label label-success">强制更新</span>';
			}}, 			
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
		version: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.version = {};
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
			var url = vm.version.id == null ? "version/version/save" : "version/version/update";
			var content = um.getContent();
			
//			var pic_items = "";
//			$("img",um.getContent()).each(function(){
//				pic_items += $(this).attr("src")+",";
//			})
//			if(pic_items.length > 0){
//				pic_items = pic_items.substring(0,pic_items.length-1);
//			}
			vm.version.updateInstruction = content;
//			vm.news.picItems = pic_items;
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.version),
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
				    url: baseURL + "version/version/delete",
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
			$.get(baseURL + "version/version/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.version = r.data.version;
                um.setContent(vm.version.updateInstruction);
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