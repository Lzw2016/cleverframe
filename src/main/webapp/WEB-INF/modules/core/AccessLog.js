/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 分页查询地址
    var findByPageUrl = globalPath.mvcPath + "/core/accesslog/findAccessLogByPage.json";
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";

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

    // 查看对话框
    var viewDialog = $("#viewDialog");
    // 查看对话框-关闭
    var viewDialogButtonsCancel = $("#viewDialogButtonsCancel");
    // 请求参数
    var viewParams = $("#viewParams");
    // 异常信息查看器
    var viewExceptionInfo = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        searchMethod.combobox({
            required: false,
            url: findDictTypeUrl + encodeURIComponent("HTTP请求方法") + "&hasSelectAll=true",
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 100
        });
        searchRequestStartTime.datetimebox({
            value: _this.getTodayStrat()
        });
        searchRequestEndTime.datetimebox({
            value: _this.getTodayEnd()
        });
        searchProcessMinTime.numberspinner({
            value:0
        });
        searchProcessMaxTime.numberspinner({
            value:10000
        });
        searchHasException.combobox({
            required: false,
            url: findDictTypeUrl + encodeURIComponent("是否") + "&hasSelectAll=true",
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 70
        });

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
                _this.openViewDialog();
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

        // 页面数据初始化
        _this.dataBind();
        // 事件绑定初始化
        _this.eventBind();
        // 查看对话框初始化
        _this.viewDialogInit();
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
        // 数据显示表格 查询
        dataTableButtonsSearch.click(function () {
            dataTable.datagrid('load');
        });

        // 查看对话框-关闭
        viewDialogButtonsCancel.click(function () {
            viewDialog.dialog('close');
        });
    };

    // ---------------------------------------------------------------------------------------------------------
    // 初始化查看对话框
    this.viewDialogInit =  function () {
        viewDialog.dialog({
            title: "请求日志信息",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            minWidth: 850,
            minHeight: 450,
            modal: true,
            buttons: "#viewDialogButtons",
            onOpen: function() {
                if(viewExceptionInfo != null) {
                    return;
                }
                // Java编辑器-初始化,
                viewExceptionInfo = CodeMirror.fromTextArea(document.getElementById("viewExceptionInfo"), {
                    mode: "text/x-java",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: false
                });
                viewExceptionInfo.setSize("auto", "auto");
                //viewExceptionInfo.setSize("100%", "800");
                viewExceptionInfo.setOption("theme", "cobalt");
                viewExceptionInfo.setValue("");
            }
        });

        viewParams.textbox({
            multiline: true
        });
    };

    /**
     * 打开查看对话框
     */
    this.openViewDialog = function () {
        var row = dataTable.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提示", "请选择要查看的数据！", "info");
            return;
        }
        if (row) {
            viewDialog.dialog('open');
            viewParams.textbox("setText", row.params);
            if(!row.exceptionInfo || row.exceptionInfo == null) {
                viewExceptionInfo.setValue("");
            } else {
                viewExceptionInfo.setValue(row.exceptionInfo);
            }
        }
    };

    // 2016-07-13 00:00:00
    this.getTodayStrat = function () {
        var date = new Date();
        var year = date.getFullYear();// 从 Date 对象以四位数字返回年份
        var month = date.getMonth(); // 从 Date 对象返回月份 (0 ~ 11)
        var day = date.getDate(); // 从 Date 对象返回一个月中的某一天 (1 ~ 31)
        return year + "-" + (month + 1) + "-" + day + " 00:00:00"
    };

    // 2016-07-13 23:59:59
    this.getTodayEnd = function () {
        var date = new Date();
        var year = date.getFullYear();// 从 Date 对象以四位数字返回年份
        var month = date.getMonth(); // 从 Date 对象返回月份 (0 ~ 11)
        var day = date.getDate(); // 从 Date 对象返回一个月中的某一天 (1 ~ 31)
        return year + "-" + (month + 1) + "-" + day + " 23:59:59"
    };

    // 格式化
    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.hasExceptionFormatter = function(value, rowData, rowIndex) {
        var result = "";
        $(searchHasException.combobox('getData')).each(function(index, data) {
            if(data.value == value) {
                result = data.text;
                return false;
            }
        });
        return result;
    };

    // 格式化
    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.processTimeFormatter = function(value, rowData, rowIndex) {
        return value + "(ms)";
    }
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