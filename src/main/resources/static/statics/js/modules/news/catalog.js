$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'news/catalog/list',
        datatype: "json",
        colModel: [
            {label: '栏目ID', name: 'id', index: 'id', width: 50, key: true},
            {label: '栏目名称', name: 'name', index: 'name', width: 80},
            {label: '栏目描述', name: 'description', index: 'description', width: 100},
            {label: '栏目图标', name: 'icon', index: 'icon', width: 80},
            {
                label: '栏目类型', name: 'type', index: 'type', width: 80, formatter: function (value, options, row) {
                    if (value === 0) {
                        return '<span class="label label-primary">版块</span>';
                    }
                    if (value === 1) {
                        return '<span class="label label-success">栏目</span>';
                    }

                }
            },
            {label: '创建者', name: 'username', index: 'username', width: 80},
            {label: '创建时间', name: 'createTime', index: 'create_time', width: 100}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.page",
            total: "page.pages",
            records: "page.size"
        },
        prmNames: {
            page: "page",
            rows: "size",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        tagList:{},
        catalog: {tagIdList:[]}

    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.catalog = {tagIdList:[]};

            //获取标签信息
            this.getTagList();
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id);
            //获取标签信息
            this.getTagList();
        },
        saveOrUpdate: function (event) {
            if(vm.validator()){
                return ;
            }

            var url = vm.catalog.id == null ? "news/catalog/save" : "news/catalog/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.catalog),
                success: function (r) {
                    if (r.code === 200) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        getTagList: function(){
            $.get(baseURL + "news/tag/select", function(r){
                vm.tagList = r.list;
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "news/catalog/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code === 200) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "news/catalog/info/" + id, function (r) {
                vm.catalog = r.catalog;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        validator: function () {
            if(isBlank(vm.catalog.name)){
                alert("栏目名不能为空");
                return true;
            }

        }
    }
});
