/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    // 查询地址
    var getApplicationAttributeUrl = globalPath.mvcPath + "/monitor/server/getApplicationAttribute.json";
    // 查询地址
    var getSessionAttributeUrl = globalPath.mvcPath + "/monitor/server/getSessionAttribute.json";

    var searchFormApplication = $("#searchFormApplication");
    var searchFormApplicationName = $("#searchFormApplicationName");
    var dataTableApplication = $("#dataTableApplication");
    var dataTableApplicationSearch = $("#dataTableApplicationSearch");

    var searchFormSession = $("#searchFormSession");
    var searchFormSessionName = $("#searchFormSessionName");
    var dataTableSession = $("#dataTableSession");
    var dataTableSessionSearch = $("#dataTableSessionSearch");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        searchFormApplicationName.textbox();
        searchFormSessionName.textbox();

        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTableApplication.datagrid({
            url: getApplicationAttributeUrl,
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#dataTableApplicationButtons",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {
            },
            onBeforeLoad: function (param) {
                // 增加查询参数
                var paramArray = searchFormApplication.serializeArray();
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

        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTableSession.datagrid({
            url: getSessionAttributeUrl,
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#dataTableSessionButtons",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {
            },
            onBeforeLoad: function (param) {
                // 增加查询参数
                var paramArray = searchFormSession.serializeArray();
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
        dataTableApplicationSearch.click(function () {
            dataTableApplication.datagrid("load");
        });

        // 查询
        dataTableSessionSearch.click(function () {
            dataTableSession.datagrid("load");
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