$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallorder/list',
        datatype: "json",
        colModel: [			
			{ label: '订单号', name: 'orderNo', index: 'order_number', width: 80 }, 			
			{ label: '用户ID', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '支付方式 ', name: 'payType', index: 'pay_type', width: 80 , formatter: function(value){
	            if(value == 0){
	                return '<span class="label label-primary">积分兑换</span>';
	            }
	            if(value == 1){
	                return '<span class="label label-success">在线支付</span>';
	            }
	            return "";
	        }}, 			
			/*{ label: '配送时间', name: 'shipmentTime', index: 'shipment_time', width: 80 , formatter: function(value){
	            if(value == 1){
	                return '<span class="label label-primary">不限送货时间</span>';
	            }
	            if(value == 2){
	                return '<span class="label label-success">工作日送货</span>';
	            }
	            if(value == 3){
	            	return '<span class="label label-success">双休日、假日送货</span>';
	            }
	            return "";
	        }}, 			
			{ label: '配送方式 ', name: 'shipmentType', index: 'shipment_type', width: 80 , formatter: function(value){
	            if(value == 0){
	                return '<span class="label label-primary">快递配送（免运费）</span>';
	            }
	            if(value == 1){
	                return '<span class="label label-success">快递配送（运费）</span>';
	            }
	            return "";
	        }},*/ 			
			{ label: '快递费', name: 'shipmentAmount', index: 'shipment_amount', width: 80 }, 			
			/*{ label: '发票方式', name: 'invoiceType', index: 'invoice_type', width: 80 , formatter: function(value){
	            if(value == 1){
	                return '<span class="label label-primary">不开发票</span>';
	            }
	            if(value == 2){
	                return '<span class="label label-success">电子发票</span>';
	            }
	            if(value == 3){
	            	return '<span class="label label-success">普通发票</span>';
	            }
	            return "";
	        }},*/
			{ label: '发票类型', name: 'invoiceType', index: 'invoice_type', width: 80 , formatter: function(value){
	            if(value == 1){
	                return '<span class="label label-primary">明细</span>';
	            }
	            if(value == 2){
	                return '<span class="label label-success">日用品</span>';
	            }
	            if(value == 3){
	            	return '<span class="label label-success">家居用品</span>';
	            }
	            return "";
	        }},
			{ label: '发票抬头', name: 'invoiceTitle', index: 'invoice_title', width: 80 }, 			
			{ label: '订单状态', name: 'orderStatus', index: 'order_status', width: 80 , formatter: function(value){
	            if(value == -1){
	                return '<span class="label label-primary">已删除</span>';
	            }
	            if(value == 0){
	                return '<span class="label label-success">待支付</span>';
	            }
	            if(value == 1){
	            	return '<span class="label label-success">已支付</span>';
	            }
	            if(value == 2){
	            	return '<span class="label label-success">已发货</span>';
	            }
	            if(value == 3){
	            	return '<span class="label label-success">待评价</span>';
	            }
	            if(value == 4){
	            	return '<span class="label label-success">已评价</span>';
	            }
	            return "";
	        }}, 			
			{ label: '运单号', name: 'postid', index: 'postid', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 , formatter: function(value){
				return value ? new Date(parseInt(value,10)).format("yy-MM-dd hh:mm") : "";
			}}, 			
			{ label: '订单金额', name: 'orderAmount', index: 'order_amount', width: 80 }, 			
			{ label: '订单积分', name: 'orderScore', index: 'order_score', width: 80 }, 			
			{ label: '支付金额 ', name: 'payAmount', index: 'pay_amount', width: 80 }, 			
			{ label: '商品总数量', name: 'buyNumber', index: 'buy_number', width: 80 }, 			
			{ label: '收货地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '收获人', name: 'consignee', index: 'consignee', width: 80 }, 			
			{ label: '收获联系方式', name: 'mobile', index: 'mobile', width: 80 }, 			
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
		mallOrder: {},
		selected: '',
		q:{
			orderNo: null,
			userId: null
		},
		status: [
		    { text: '订单状态', value: '' },
		    { text: '已删除', value: '-1' },
		    { text: '待支付', value: '0' },
		    { text: '已支付', value: '1' },
		    { text: '已发货', value: '2' },
		    { text: '待评价', value: '3' },
		    { text: '已评价', value: '4' }
		]

	},
	methods: {
		query: function () {
			vm.reload();
		},
		sendOut: function(){
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			$('#myModal').modal('show');
			vm.mallOrder.id = id;
		},
		sendOutSave: function(){
			if(!vm.mallOrder.postid){
				alert("请输入运单号");
				return;
			}
			$.ajax({
				type: "POST",
			    url: "/mall/mallorder/sendOut",
			    data: {
			    	id: vm.mallOrder.id,
			    	postid: vm.mallOrder.postid
			    },
			    success: function(r){
			    	$('#myModal').modal('hide');
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
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.mallOrder = {};
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
			var url = vm.mallOrder.id == null ? "mall/mallorder/save" : "mall/mallorder/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.mallOrder),
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
				    url: baseURL + "mall/mallorder/delete",
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
			$.get(baseURL + "mall/mallorder/info/"+id, function(r){
				if(r.errcode && r.errcode != 0){
            		return;
            	}
                vm.mallOrder = r.data.mallOrder;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{"orderNo": vm.q.orderNo, "userId": vm.userId, "status": vm.selected},
                page:page
            }).trigger("reloadGrid");
		}
	}
});