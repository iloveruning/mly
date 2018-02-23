var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		catalog: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.catalog = {};
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
			var url = vm.catalog.id == null ? "news/catalog/save" : "news/catalog/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.catalog),
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
				    url: baseURL + "news/catalog/delete",
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
			$.get(baseURL + "news/catalog/info/"+id, function(r){
                vm.catalog = r.catalog;
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


var Catalog = {
    id: "catalogTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Catalog.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '栏目ID', field: 'id', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '栏目名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '1580px'},
        {title: '上级栏目', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '150px'},
        {title: '图标', field: 'icon', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
                return item.icon == null ? '' : '<i class="'+item.icon+' fa-lg"></i>';
            }},
        {title: '栏目描述', field: 'description', align: 'center', valign: 'middle', sortable: false, width: '200px'},
        {title: '类型', field: 'type', align: 'center', valign: 'middle', sortable: false, width: '120px', formatter: function (item, index) {
				return item.type === 0 ?
					'<span class="label label-primary">版块</span>' :
                    '<span class="label label-success">栏目</span>';
            }},
        {title: '创建者', field: 'username', align: 'center', valign: 'middle', sortable: false,width: '150px'},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: false}
	]
    return columns;
};


function getCatalogId () {
    var selected = $('#catalogTable').bootstrapTreeTable('getSelections');
    if (selected.length === 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}


$(function () {
    var colunms = Catalog.initColumn();
    console.log(colunms);
    var table = new TreeTable(Catalog.id, baseURL + "news/catalog/list", colunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(true);
    table.init();
    Catalog.table = table;
});