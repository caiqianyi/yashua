$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mall/mallorder/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '订单编号,系统生成', name: 'orderNumber', index: 'order_number', width: 80 }, 			
			{ label: '用户ID', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '支付方式 0=积分兑换，1=在线支付', name: 'payType', index: 'pay_type', width: 80 }, 			
			{ label: '配送时间 1=不限送货时间，2=工作日送货，3=双休日、假日送货', name: 'shipmentTime', index: 'shipment_time', width: 80 }, 			
			{ label: '配送方式 0=快递配送（免运费），1=快递配送（运费）', name: 'shipmentType', index: 'shipment_type', width: 80 }, 			
			{ label: '快递费', name: 'shipmentAmount', index: 'shipment_amount', width: 80 }, 			
			{ label: '支付方式 1=不开发票，2=电子发票，3=普通发票', name: 'invoiceType', index: 'invoice_type', width: 80 }, 			
			{ label: '发票抬头', name: 'invoiceTitle', index: 'invoice_title', width: 80 }, 			
			{ label: '订单状态 -1=已删除，0=待支付，1=已支付', name: 'orderStatus', index: 'order_status', width: 80 }, 			
			{ label: '运单号', name: 'postid', index: 'postid', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			{ label: '订单金额', name: 'orderAmount', index: 'order_amount', width: 80 }, 			
			{ label: '订单积分', name: 'orderScore', index: 'order_score', width: 80 }, 			
			{ label: '支付金额 = 订单金额 + 快递费', name: 'payAmount', index: 'pay_amount', width: 80 }, 			
			{ label: '商品总数量', name: 'buyNumber', index: 'buy_number', width: 80 }, 			
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
		mallOrder: {}
	},
	methods: {
		query: function () {
			vm.reload();
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
                page:page
            }).trigger("reloadGrid");
		}
	}
});