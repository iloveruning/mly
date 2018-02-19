$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/log/list',
        datatype: "json",
        colModel: [			 //常用到的属性：name 列显示的名称；index 传到服务器端用来排序用的列名称；width 列宽度；align 对齐方式；sortable 是否可以排序
			{ label: 'id', name: 'id', width: 30, key: true },
			{ label: '用户名', name: 'username', width: 50 }, 			
			{ label: '用户操作', name: 'operation', width: 70 }, 			
			{ label: '请求方法', name: 'method', width: 150 }, 			
			{ label: '请求参数', name: 'params', width: 80 },
            { label: '执行时长(毫秒)', name: 'time', width: 80 },
			{ label: 'IP地址', name: 'ip', width: 70 }, 			
			{ label: '创建时间', name: 'createTime', width: 90 }
        ],
		viewrecords: true,//定义是否要显示总记录数
        height: 385,
        rowNum: 10,  //在grid上显示记录条数，这个参数是要被传递到后台
		rowList : [10,30,50],//一个下拉选择框，用来改变显示记录数，当选择时会覆盖rowNum参数传递到后台
        rownumbers: true, //如果为ture则会在表格左边新增一列，显示行顺序号，从1开始递增。此列名为'rn'.
        rownumWidth: 25, //如果rownumbers为true，则可以设置column的宽度
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",//定义翻页用的导航栏，必须是有效的html元素。翻页工具栏可以放置在html页面任意位置
        jsonReader : {  //定义jsonReader来跟服务器端返回的数据做对应
            root: "page.list",
            page: "page.page",
            total: "page.pages",
            records: "page.size"
        },
        /*
        jsonReader : {
02.     root: "rows",   // json中代表实际模型数据的入口
03.     page: "page",   // json中代表当前页码的数据
04.     total: "total", // json中代表页码总数的数据
05.     records: "records", // json中代表数据行总数的数据
06.     repeatitems: true, // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
07.     cell: "cell",
08.     id: "id",
09.     userdata: "userdata",
10.     subgrid: {
11.         root:"rows",
12.         repeatitems: true,
13.         cell:"cell"
14.     }
15. }
         */
        prmNames : {
            page:"page", // 表示请求页码的参数名称
            rows:"size", // 表示请求行数的参数名称
            order: "order",// 表示采用的排序方式的参数名称
        },
        /*
        prmNames : {
02.     page:"page",    // 表示请求页码的参数名称
03.     rows:"rows",    // 表示请求行数的参数名称
04.     sort: "sidx", // 表示用于排序的列名的参数名称
05.     order: "sord", // 表示采用的排序方式的参数名称
06.     search:"_search", // 表示是否是搜索请求的参数名称
07.     nd:"nd", // 表示已经发送请求的次数的参数名称
08.     id:"id", // 表示当在编辑数据模块中发送数据时，使用的id的名称
09.     oper:"oper",    // operation参数名称（我暂时还没用到）
10.     editoper:"edit", // 当在edit模式中提交数据时，操作的名称
11.     addoper:"add", // 当在add模式中提交数据时，操作的名称
12.     deloper:"del", // 当在delete模式中提交数据时，操作的名称
13.     subgridid:"id", // 当点击以载入数据到子表时，传递的数据名称
14.     npage: null,
15.     totalrows:"totalrows" // 表示需从Server得到总共多少行数据的参数名称，参见jqGrid选项中的rowTotal
16. }
         */
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
			key: null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reload: function (event) {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		}
	}
});