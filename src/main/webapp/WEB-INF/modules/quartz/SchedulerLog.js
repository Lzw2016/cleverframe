/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 调度器日志分页查询地址
    var findSchedulerLogByPageUrl = globalPath.mvcPath + "/quartz/schedulerlog/findSchedulerLogByPage.json";

    // 查询表单
    var searchForm = $("#searchForm");
    // 查询表单 - 调度器名称
    var searchSchedulerName = $("#searchSchedulerName");
    // 查询表单 - 调度器ID
    var searchInstanceName = $("#searchInstanceName");
    // 查询表单 - 时间-起始值
    var searchLogTimeStart = $("#searchLogTimeStart");
    // 查询表单 - 时间-结束值
    var searchLogTimeEnd = $("#searchLogTimeEnd");
    // 查询表单 - 日志方法
    var searchMethodName = $("#searchMethodName");

    // 调度器日志数据表格
    var schedulerLogDataTable = $("#schedulerLogDataTable");
    // 调度器日志数据表格 - 查询按钮
    var schedulerLogDataTableButtonsSearch = $("#schedulerLogDataTableButtonsSearch");

    //
    var jsonViewDialog = $("#jsonViewDialog");
    //
    var logDataView = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        searchSchedulerName.textbox({});
        searchInstanceName.textbox({});
        searchLogTimeStart.datetimebox({});
        searchLogTimeEnd.datetimebox({});
        searchMethodName.combobox({
            editable: true,
            valueField: 'value',
            textField: 'text',
            panelHeight: 150,
            data:[{text: 'jobScheduled', value: 'jobScheduled'},
                {text: 'jobUnscheduled', value: 'jobUnscheduled'},
                {text: 'triggerFinalized', value: 'triggerFinalized'},
                {text: 'triggerPaused', value: 'triggerPaused'},
                {text: 'triggersPaused', value: 'triggersPaused'},
                {text: 'triggerResumed', value: 'triggerResumed'},
                {text: 'triggersResumed', value: 'triggersResumed'},
                {text: 'jobAdded', value: 'jobAdded'},
                {text: 'jobDeleted', value: 'jobDeleted'},
                {text: 'jobPaused', value: 'jobPaused'},
                {text: 'jobsPaused', value: 'jobsPaused'},
                {text: 'jobResumed', value: 'jobResumed'},
                {text: 'jobsResumed', value: 'jobsResumed'},
                {text: 'schedulerError', value: 'schedulerError'},
                {text: 'schedulerInStandbyMode', value: 'schedulerInStandbyMode'},
                {text: 'schedulerStarted', value: 'schedulerStarted'},
                {text: 'schedulerStarting', value: 'schedulerStarting'},
                {text: 'schedulerShutdown', value: 'schedulerShutdown'},
                {text: 'schedulerShuttingdown', value: 'schedulerShuttingdown'},
                {text: 'schedulingDataCleared', value: 'schedulingDataCleared'}]
        });

        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        schedulerLogDataTable.datagrid({
            url: findSchedulerLogByPageUrl,
            idField: 'id',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: true,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#schedulerLogDataTableButtons",
            pageSize: 20,
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

        _this.initJsonViewDialog();
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
        //查询按钮
        schedulerLogDataTableButtonsSearch.click(function () {
            schedulerLogDataTable.datagrid('load');
        });
    };

    // ---------------------------------------------------------------------------------------------------------
    this.initJsonViewDialog = function () {
        jsonViewDialog.dialog({
            title: "日志数据",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            minWidth: 500,
            minHeight: 400,
            modal: true,
            //buttons: "#",
            onOpen: function() {
                if(logDataView != null) {
                    return;
                }
                // Java编辑器-初始化,
                logDataView = CodeMirror.fromTextArea(document.getElementById("logDataView"), {
                    //mode: "text/x-java",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: true
                });
                logDataView.setSize("auto", "auto");
                logDataView.setOption("theme", "cobalt");
                logDataView.setValue("");
            }
        });
    };

    // 打开任务执行异常信息查看对话框
    this.openJsonViewDialog = function(logData, mode){
        jsonViewDialog.dialog("open");
        if(mode) {
            logDataView.setOption("mode", mode);
        }
        logDataView.setValue("");
        logDataView.setValue(logData);
    };

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.logDataFormatter = function (value, rowData, rowIndex) {
        if (!value) {
            return "NULL";
        }
        value = JSON.parse(value);
        var mode;
        switch (value.dataType) {
            case "string":
                return value.data;
                break;
            case "null":
                return "NULL";
                break;
            case "json":
                mode = "application/json";
                value = js_beautify(JSON.stringify(value.data), 4, ' ');
                break;
            case "java":
                mode = "text/x-java";
                value = value.data;
                break;
            default:
                mode = "application/json";
                value = js_beautify(JSON.stringify(value), 4, ' ');
                break;
        }
        var uuid = _this.getUUID(32, 16);
        var aButton = $("<a id='" + uuid + "' href='javascript:void(0)'>查看</a>");
        $("body").on("click", "#" + uuid, function () {
            _this.openJsonViewDialog(value, mode);
        });
        return $("<div></div>").append(aButton).html();
    };

    // 获取一个UUID
    this.getUUID = function (len, radix) {
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var uuid = [], i;
        radix = radix || chars.length;
        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
        } else {
            // rfc4122, version 4 form
            var r;
            // rfc4122 requires these characters
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';
            // Fill in random data.  At i==19 set the high bits of clock sequence as
            // per rfc4122, sec. 4.1.5
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }
        return uuid.join('');
    };
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