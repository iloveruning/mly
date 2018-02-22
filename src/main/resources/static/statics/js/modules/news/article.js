$(function () {

    var auditFlag=$('#auditFlag');

    $("#jqGrid").jqGrid({
        url: baseURL + 'news/article/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: 'id', width: 50, key: true},
            {label: '标题', name: 'title', index: 'title', width: 80, search: true},
            {label: '权重(1-100)', name: 'weight', index: 'weight', width: 80},
            {label: '阅读量', name: 'read', index: 'read', width: 80},
            {label: '发布者', name: 'username', index: 'username', width: 80},
            {
                label: '状态', name: 'state', index: 'state', width: 80, formatter: function (value, options, row) {
                    if (value === 0) {
                        return '<span class="label label-default">待审核</span>';
                    }
                    if (value === 1) {
                        return '<span class="label label-danger">未通过</span>';
                    }
                    if (value === 2) {
                        return '<span class="label label-info">通过</span>';
                    }
                    if (value === 3) {
                        return '<span class="label label-success">精华</span>';
                    }
                    if (value === 4) {
                        return '<span class="label label-primary">置顶</span>';
                    }
                }
            },
            {label: '提交时间', name: 'createTime', index: 'create_time', width: 80, sortable: true},

            {label: '操作', name: 'operate', index: 'detail', sortable: false, align: "center", width: "100px" }


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
            var ids = jQuery("#jqGrid").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var id = ids[i];
                var auditBtn="&nbsp;&nbsp;<a href='#' onclick='audit("+id+")' ><span class='label label-warning'>审核</span></a>";
                var detailBtn = "<a href='#' onclick='info("+id+")' ><span class='label label-success'>详情</span></a>";
                if(auditFlag.length>0){
                    detailBtn=detailBtn+auditBtn;
                }

                jQuery("#jqGrid").jqGrid('setRowData', id, { operate: detailBtn});
            }
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});


function info(id) {
    console.log(id);
    $.ajax({
        type: "GET",
        url: baseURL + "news/article/info/" + id,
        contentType: "application/json",
        success: function (r) {
            if (r.code === 200) {
                var art=r.article;
               $("#art_title").text(art.title);
               $("#art_content").html(art.content);
                $('#articleDetail').modal('show');
            } else {
                alert(r.msg);
            }
        }
    });
}


function audit(id) {
    $.ajax({
        type: "GET",
        url: baseURL + "news/article/audit/" + id,
        contentType: "application/json",
        success: function (r) {
            if (r.code === 200) {
                var art=r.article;
                $("#art_title").text(art.title);
                $("#art_content").html(art.content);
                $('#articleDetail').modal('show');
            } else {
                alert(r.msg);
            }
        }
    });
}


var vm = new Vue({
    el: '#rrapp',
    data: {
        q:{
            title: null
        },
        showList: true,
        title: null,
        article: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.article = {};
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
            var url = vm.article.id == null ? "news/article/save" : "news/article/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.article),
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
                    url: baseURL + "news/article/delete",
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
            $.get(baseURL + "news/article/info/" + id, function (r) {
                vm.article = r.article;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData:{'title': vm.q.title},
                page: page
            }).trigger("reloadGrid");
        }
    }
});