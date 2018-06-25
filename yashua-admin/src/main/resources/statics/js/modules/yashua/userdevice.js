$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'yashua/userdevice/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户账号', name: 'account', index: 'account', width: 80 }, 			
			{ label: '日志时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '设备号', name: 'identity', index: 'identity', width: 80 }, 			
			{ label: '图片', name: 'icon', index: 'icon', width: 80 }, 			
			{ label: '名称', name: 'name', index: 'name', width: 80 }, 			
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
var wb;//读取完成的数据
var rABS = false; //是否将文件读取为二进制字符串
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		userDevice: {},
		addType: 1 
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.userDevice = {};
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
			var url = vm.userDevice.id == null ? "yashua/userdevice/save" : "yashua/userdevice/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.userDevice),
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
				    url: baseURL + "yashua/userdevice/delete",
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
			$.get(baseURL + "yashua/userdevice/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.userDevice = r.data.userDevice;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
		importf: function(event){
			var obj = event.target;
			if(!obj.files || obj.files.length == 0) {
                return;
            }
            var f = obj.files[0];
            var type = f.name.substring(f.name.indexOf(".")+1,f.name.length).toLowerCase();
            console.info(type);
            if (!(type && /^(xlsx)$/.test(type))){
                alert('只支持xlsx！');
                return false;
            }
            var reader = new FileReader();
            reader.onload = function(e) {
                var data = e.target.result;
                if(rABS) {
                    wb = XLSX.read(btoa(vm.fixdata(data)), {//手动转化
                        type: 'base64'
                    });
                } else {
                    wb = XLSX.read(data, {
                        type: 'binary'
                    });
                }
                //wb.SheetNames[0]是获取Sheets中第一个Sheet的名字
                //wb.Sheets[Sheet名]获取第一个Sheet的数据
                var json = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);
                confirm('确定要添加xlsx中'+json.length+'条记录吗？', function(){
    				$.ajax({
    					type: "POST",
    				    url: baseURL + "yashua/userdevice/addBatch",
                        contentType: "application/json",
    				    data: JSON.stringify(json),
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
            };
            if(rABS) {
                reader.readAsArrayBuffer(f);
            } else {
                reader.readAsBinaryString(f);
            }
		},
		fixdata: function(data) { //文件流转BinaryString
            var o = "",
                l = 0,
                w = 10240;
            for(; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
            o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
            return o;
        }

	}
});