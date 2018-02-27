$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'web/server/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: 'id', align: "center", width: 50, key: true},
            {label: '服务器名称', name: 'name', index: 'name', width: 80},
            {label: '服务器IP', name: 'ip', index: 'ip', width: 80},
            {label: '服务器密码', name: 'password', index: 'password', width: 80},
            {
                label: '数据库类型',
                name: 'sqlType',
                index: 'sql_type',
                width: 80,
                formatter: function (value, options, row) {

                    if (value === 1) {
                        return '<span class="label label-primary">mysql</span>';
                    }
                    if (value === 2) {
                        return '<span class="label label-info">SQL server</span>';
                    }
                    if (value === 3) {
                        return '<span class="label label-success">oracle</span>';
                    }
                    if (value === 4) {
                        return '<span class="label label-danger">其它</span>';
                    }
                }
            },
            {label: '数据库用户名', name: 'sqlUsername', index: 'sql_username', width: 80},
            {label: '数据库密码', name: 'sqlPassword', index: 'sql_password', width: 80},
            {label: '管理员', name: 'username', index: 'username', width: 80},
            {label: '添加时间', name: 'createTime', index: 'create_time', width: 100}
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
        server: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.server = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.server.id == null ? "web/server/save" : "web/server/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.server),
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
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "web/server/delete",
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
            $.get(baseURL + "web/server/info/" + id, function (r) {
                vm.server = r.server;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});