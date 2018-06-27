var pickerSetup = null;
$(function () {
	
	pickerSetup = new PickerSetup({
    	selected: [0,0,0],
		title: "请选择地区",
		onSelected: function(selected,text){
			vm.pcikerTextShow = text.toString().replace(new RegExp(",","gm")," ");
			vm.userAddress.areaCode = selected.toString();
		},
		onChange: function(){
			
		}
	});
	
    $("#jqGrid").jqGrid({
        url: baseURL + 'account/useraddress/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '收货人', name: 'consignee', index: 'consignee', width: 80 }, 			
			{ label: '所在地区', name: 'areaCode', index: 'area_code', width: 80 , formatter: function(value){
				return pickerSetup.getCityByCode(value.split(",")).toString().replace(new RegExp(",","gm")," ");
			}}, 			
			{ label: '详细地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '手机号码', name: 'mobile', index: 'mobile', width: 80 }, 			
			{ label: '地址别名', name: 'name', index: 'name', width: 80 }, 			
			{ label: '默认首选', name: 'defaultFlag', index: 'default_flag', width: 80 , formatter: function(value){
				return value == 0? "首选" : "";
			}}, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
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
		userAddress: {},
		pcikerTextShow: "请选择所在地区"
	},
	methods: {
		showPicker: function(){
			pickerSetup.show();
		},
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.userAddress = {};
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
			var url = vm.userAddress.id == null ? "account/useraddress/save" : "account/useraddress/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.userAddress),
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
				    url: baseURL + "account/useraddress/delete",
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
			$.get(baseURL + "account/useraddress/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.userAddress = r.data.userAddress;
                var s = r.data.userAddress.areaCode.split(",");
                pickerSetup.setSelected(s);
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