$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'web/app/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', index: 'id',align: "center", width: 50, key: true },
			{ label: '应用的名称', name: 'appName', index: 'app_name', width: 80 }, 			
			{ label: '描述', name: 'description', index: 'description', width: 80 }, 			
			{ label: '部署文件夹', name: 'deploy', index: 'deploy', width: 80 }, 			
			{ label: 'AppKey', name: 'appKey', index: 'app_key', width: 80 },
			{ label: 'AppSecret', name: 'appSecret', index: 'app_secret', width: 80 },
			{ label: '创建应用的管理员', name: 'username', index: 'username', width: 80 }, 			
			{ label: '应用创建的时间', name: 'createTime', index: 'create_time', width: 100 }
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
            root: "page.list",
            page: "page.page",
            total: "page.pages",
            records: "page.size"
        },
        prmNames : {
            page:"page", 
            rows:"size",
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
        q:{
            appName: null
        },
		showList: true,
		title: null,
        serverList:{},
		app: {
			serverIdList:[]
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "注册应用";
            vm.serverList = {};
            vm.app = {serverIdList:[]};

            //获取服务器信息
            this.getServerList();
		},
        getServerList: function(){
            $.get(baseURL + "web/server/select", function(r){
                vm.serverList = r.list;
            });
        },
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id);
            //获取服务器信息
            this.getServerList();
		},
		saveOrUpdate: function (event) {
            if(vm.validator()){
                return ;
            }
			var url = vm.app.id == null ? "web/app/register" : "web/app/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.app),
			    success: function(r){
			    	if(r.code === 200){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
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
				    url: baseURL + "web/app/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code === 200){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "web/app/info/"+id, function(r){
                vm.app = r.app;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'appName': vm.q.appName},
                page:page
            }).trigger("reloadGrid");
		},
        validator: function () {
            if(isBlank(vm.app.appName)){
                alert("应用名不能为空");
                return true;
            }

        }
	}
});