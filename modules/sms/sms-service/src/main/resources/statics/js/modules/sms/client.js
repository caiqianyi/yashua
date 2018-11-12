var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        model: true,
        title: null,
        client: {
        	appid:null,
        	secret:''
        },
        astrict: {
        	appid:null,
        	format: null,
        	astrict: null
        }
    },
    methods: {
        add: function(){
        	$.get(baseURL + "/sms/uuid", {}, function(r){
            	if(r.errcode == 0){
	                vm.showList = false;
	                vm.model = true;
	                vm.title = "新增";
	                vm.client = {
                		appid:'',
                		secret:r.data
	                };
                }else{
                	alert(r.errmsg);
                }
            });
        },
        addAstrict: function(){
        	var appid = getTreeRId(config.id);
            if(appid == null){
                return ;
            }
            vm.showList = false;
            vm.model = false;
            vm.title = "新增";
            vm.astrict = {
            	appid: appid,
        		format:'',
        		astrict:''
            };
        },
        updateAstrict: function () {
        	var appid = getTreeRId(config.id);
        	if(appid == null){
                return ;
            }
            var format = getTreeRId(astrict.id);
            if(format == null){
                return ;
            }
            $.get(baseURL + "sms/client/astrict/find", {appid:appid,format:format}, function(r){
            	if(r.errcode == 0){
	                vm.showList = false;
	                vm.model = false;
	                vm.title = "修改";
	                vm.astrict = r.data;
                }else{
                	alert(r.errmsg);
                }
            });
        },
        delAstrict: function () {
            var format = getTreeRId(astrict.id);
            if(format == null){
                return ;
            }
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sms/client/astrict/delete",
                    data: {appid:vm.astrict.appid,format:format},
                    success: function(r){
                        if(r.errcode == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.errmsg);
                        }
                    }
                });
            });
        },
        saveAstrict: function () {
            var url = "sms/client/astrict/set";
            $.ajax({
                type: "POST",
                url:  baseURL + url,
                contentType: "application/json",
                data: {
                	appid:vm.astrict.appid,
                	format:vm.astrict.format,
                	astrict:vm.astrict.astrict
                },
                success: function(r){
                    if(r.errcode == 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.errmsg);
                    }
                }
            });
        },
        showAstrict: function(){
        	var appid = getTreeRId(config.id);
            if(appid == null){
                return ;
            }
            var colunms = astrict.initColumn();
            var table = new TreeTable(astrict.id, baseURL + "sms/client/astrict/list?appid="+appid, colunms);
            table.setExpandColumn(2);
            table.setIdField("format");
            table.setCodeField("format");
            table.setExpandAll(false);
            table.init();
            astrict.table = table;
            vm.astrict = {};
            vm.astrict.appid = appid;
        },
        update: function () {
            var appid = getTreeRId(config.id);
            if(appid == null){
                return ;
            }
            $.get(baseURL + "sms/client/find", {appid:group}, function(r){
            	if(r.errcode == 0){
            		vm.showList = false;
            		vm.model = true;
	                vm.title = "修改";
	                vm.client = r.data;
                }else{
                	alert(r.errmsg);
                }
            });
        },
        del: function () {
            var group = getTreeRId(config.id);
            if(group == null){
                return ;
            }
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sms/client/delete",
                    data: {appid:group},
                    success: function(r){
                        if(r.errcode == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.errmsg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

            var url = "sms/client/saveOrUpdate";
            $.ajax({
                type: "POST",
                url:  baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.client),
                success: function(r){
                    if(r.errcode == 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.errmsg);
                    }
                }
            });
        },
        reload: function () {
            vm.showList = true;
            vm.model = true;
            config.table.refresh();
            astrict.table.refresh();
        },
        validator: function () {
            if(isBlank(vm.client.appid)){
                alert("开发者的应用ID不能为空");
                return true;
            }
            if(isBlank(vm.client.secret)){
                alert("密钥不能为空");
                return true;
            }
        },
    }
});


var config = {
    id: "configTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
config.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '微信AppID', field: 'appid', visible: false, align: 'center', valign: 'middle', width: '180px'},
        {title: '密钥', field: 'secret', align: 'center', valign: 'middle', sortable: true, width: '360px'}
	];
    return columns;
};

var astrict = {
    id: "astrictTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
astrict.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '格式', field: 'format', visible: false, align: 'center', valign: 'middle', width: '180px'},
        {title: '最大次数', field: 'astrict', align: 'center', valign: 'middle', sortable: true, width: '360px'}
	];
    return columns;
};


function getTreeRId (id) {
    var selected = $('#'+id).bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}


$(function () {
    var colunms = config.initColumn();
    var table = new TreeTable(config.id, baseURL + "sms/client/list", colunms);
    table.setExpandColumn(2);
    table.setIdField("appid");
    table.setCodeField("appid");
    table.setExpandAll(false);
    table.init();
    config.table = table;

});
