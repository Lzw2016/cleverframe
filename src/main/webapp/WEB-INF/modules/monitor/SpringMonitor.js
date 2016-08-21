/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    // 查询地址
    var getSpringBeansUrl = globalPath.mvcPath + "/monitor/spring/getSpringBeans.json";

    // 查询表单
    var searchForm = $("#searchForm");
    // Bean名称
    var searchBeanName = $("#searchBeanName");
    // 数据表格
    var dataTable = $("#dataTable");
    // 查询
    var dataTableSearch = $("#dataTableSearch");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        //noinspection JSUnusedLocalSymbols
        searchBeanName.textbox({
            icons: [{
                iconCls: 'icon-search',
                handler: function (e) {
                    // 查询数据
                    if (!searchForm.form("validate")) {
                        return;
                    }
                    dataTable.datagrid("load");
                }
            }]
        });

        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
            url: getSpringBeansUrl,
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#dataTableButtons",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {
            },
            onBeforeLoad: function (param) {
                // 增加查询参数
                var paramArray = searchForm.serializeArray();
                $(paramArray).each(function () {
                    if (param[this.name]) {
                        if ($.isArray(param[this.name])) {
                            param[this.name].push(this.value);
                        } else {
                            param[this.name] = [param[this.name], this.value];
                        }
                    } else {
                        param[this.name] = this.value;
                    }
                });
            }
        });

        _this.dataBind();
        _this.eventBind();
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        // 查询
        dataTableSearch.click(function () {
            if (!searchForm.form("validate")) {
                return;
            }
            dataTable.datagrid("load");
        });
    };

    // ---------------------------------------------------------------------------------------------------------

};

/**
 * 页面Js对象
 */
var pageJsObject = null;
/**
 * 页面Js执行入口
 */
$(document).ready(function () {
    if (typeof(globalPath) == "undefined") {
        alert("系统全局路径对象未定义(globalPath)");
    } else {
        pageJsObject = new pageJs(globalPath);
        pageJsObject.init();
    }
});