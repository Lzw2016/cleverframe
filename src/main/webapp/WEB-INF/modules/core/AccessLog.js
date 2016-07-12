/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 分页查询地址
    var findByPageUrl = globalPath.mvcPath + "/core/accesslog/findAccessLogByPage.json";

    // 数据查询表单
    var searchForm = $("#searchForm");
    // 登录名-查询
    var searchLoginName = $("#searchLoginName");
    // 请求时间-查询
    var searchRequestStartTime = $("#searchRequestStartTime");
    // 请求时间-查询
    var searchRequestEndTime = $("#searchRequestEndTime");
    // 操作方式-查询
    var searchMethod = $("#searchMethod");
    // 请求URI-查询
    var searchRequestUri = $("#searchRequestUri");
    // 请求IP-查询
    var searchRemoteAddr = $("#searchRemoteAddr");
    // 处理时间-查询
    var searchProcessMinTime = $("#searchProcessMinTime");
    // 处理时间-查询
    var searchProcessMaxTime = $("#searchProcessMaxTime");
    // 用户代理-查询
    var searchUserAgent = $("#searchUserAgent");
    // 是否有异常-查询
    var searchHasException = $("#searchHasException");

    // 数据显示表格
    var dataTable = $("#dataTable");
    // 数据显示表格 - 按钮栏
    var dataTableButtons = $("#dataTableButtons");
    // 数据显示表格 - 查询
    var dataTableButtonsSearch = $("#dataTableButtonsSearch");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
            url: findByPageUrl,
            idField: 'configKey',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: true,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#dataTableButtons",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {
                //_this.openEditDialog();
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