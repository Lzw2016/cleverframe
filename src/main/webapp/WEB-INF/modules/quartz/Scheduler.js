/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 获取调度器信息地址
    var getMetaDataUrl = globalPath.mvcPath + "/quartz/scheduler/getMetaData.json";
    // 获取调度器Context数据地址
    var getContextUrl = globalPath.mvcPath + "/quartz/scheduler/getContext.json";
    // 获取正在运行的任务地址
    var getRunningJobsUrl = globalPath.mvcPath + "/quartz/scheduler/getRunningJobs.json";
    // 设置调度器成为standby模式
    var standbyUrl = globalPath.mvcPath + "/quartz/scheduler/standby.json";
    // 启动调度器
    var startUrl = globalPath.mvcPath + "/quartz/scheduler/start.json";
    // 暂停所有任务
    var pauseAllUrl = globalPath.mvcPath + "/quartz/scheduler/pauseAll.json";
    // 取消暂停所有任务
    var resumeAllUrl = globalPath.mvcPath + "/quartz/scheduler/resumeAll.json";
    // 中断任务
    var interruptUrl = globalPath.mvcPath + "/quartz/scheduler/interrupt.json";

    // 刷新调度数据
    var schedulerReload = $("#schedulerReload");
    //调度器名称
    var schedulerName = $("#schedulerName");
    //调度器ID
    var schedulerInstanceId = $("#schedulerInstanceId");
    //调度器实现类
    var schedulerClass = $("#schedulerClass");
    //调度器版本
    var version = $("#version");
    // 调度器状态
    var schedulerStatus = $("#schedulerStatus");
    //调度器状态 - 启动
    var started = $("#started");
    //调度器状态 - 关闭
    var shutdown = $("#shutdown");
    //调度器状态 - 暂停
    var inStandbyMode = $("#inStandbyMode");
    //调度器启动时间
    var runningSince = $("#runningSince");
    //是否使用集群
    var jobStoreClustered = $("#jobStoreClustered");
    //是否远程提供服务
    var schedulerRemote = $("#schedulerRemote");
    //本次启动之后一共执行的任务数
    var numberOfJobsExecuted = $("#numberOfJobsExecuted");
    //调度数据是否支持持久化
    var jobStoreSupportsPersistence = $("#jobStoreSupportsPersistence");
    //调度数据存储实现类
    var jobStoreClass = $("#jobStoreClass");
    //调度任务线程池实现类
    var threadPoolClass = $("#threadPoolClass");
    //调度任务线程池实大小
    var threadPoolSize = $("#threadPoolSize");
    //调度器总览
    var summary = $("#summary");
    // 调度器是否处于暂停状态
    var schedulerInStandbyMode;

    // 多tab叶签
    var tabsCenter = $("#tabsCenter");

    // 调度器Context 数据表格
    var contextDataTable = $("#contextDataTable");
    // 调度器Context 数据表格 - 重新加载
    var contextDataTableButtonsReload = $("#contextDataTableButtonsReload");

    // 正在运行的任务 数据表格
    var runningJobsDataTable = $("#runningJobsDataTable");
    // 正在运行的任务 - 重新加载
    var runningJobsDataTableButtonsReload = $("#runningJobsDataTableButtonsReload");

    // 参看json数据对话框
    var jsonViewDialog = $("#jsonViewDialog");
    // 数据查看高亮控件
    var jsonViewEdit = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        tabsCenter.tabs({
            fit: true,
            border: 'false',
            pill: true,
            onSelect: function (title, index) {
                switch (index){
                    case 0:
                        _this.initSchedulerMetaData();
                        break;
                    case 1:
                        _this.initContextDataTable();
                        break;
                    case 2:
                        _this.initRunningJobsDataTable();
                        break;
                }
                console.log(index);
            }
        });

        schedulerStatus.linkbutton({
            onClick: function () {
                var url = "";
                if (schedulerInStandbyMode == true) {
                    // 当前状态：暂停
                    url = startUrl;
                } else {
                    // 当前状态：运行
                    url = standbyUrl;
                }
                $.ajax({
                    type: "POST", dataType: "JSON", url: url, async: true,
                    success: function (data) {
                        if (data.success) {
                            //_this.setSchedulerInStandbyMode(!schedulerInStandbyMode);
                            _this.initSchedulerMetaData();
                        } else {
                            $.messager.alert("提示", data.failMessage, "warn");
                        }
                    }
                });
            }
        });

        schedulerReload.linkbutton({
            iconCls: 'icon-reload',
            onClick: function () {
                _this.initSchedulerMetaData();
            }
        });
        schedulerReload.tooltip({position: 'top', content: '点击刷新调度器信息'});

        contextDataTable.datagrid({
            idField: 'configKey',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#contextDataTableButtons",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {

            }
        });

        runningJobsDataTable.datagrid({
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#runningJobsDataTableButtons",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {

            }
        });

        jsonViewDialog.dialog({
            title: "查看数据",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            minWidth: 850,
            minHeight: 450,
            modal: true,
            //buttons: "#",
            onOpen: function () {
                if (jsonViewEdit != null) {
                    return;
                }
                // 编辑器-初始化,
                jsonViewEdit = CodeMirror.fromTextArea(document.getElementById("jsonViewEdit"), {
                    mode: "application/json",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: true
                });
                jsonViewEdit.setSize("auto", "auto");
                jsonViewEdit.setOption("theme", "cobalt");
                jsonViewEdit.setValue("");
            }
        });

        _this.dataBind();
        _this.eventBind();
    };

    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        _this.initSchedulerMetaData();
        //_this.initContextDataTable();
        //_this.initRunningJobsDataTable();
    };

    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        // 调度器Context 数据表格 - 重新加载
        contextDataTableButtonsReload.click(function () {
            _this.initContextDataTable();
        });

        // 正在运行的任务 - 重新加载
        runningJobsDataTableButtonsReload.click(function () {
            _this.initRunningJobsDataTable();
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    // 初始化调度器信息
    this.initSchedulerMetaData = function () {
        $.ajax({
            type: "POST", dataType: "JSON", url: getMetaDataUrl, async: true,
            success: function (data) {
                if (data.success) {
                    _this.setSchedulerMetaData(data.result);
                } else {
                    $.messager.alert("提示", data.failMessage, "warn");
                }
            }
        });
    };

    // 设置调度器信息
    this.setSchedulerMetaData = function (metaData) {
        started.text(metaData.started);
        shutdown.text(metaData.shutdown);
        version.text(metaData.version);
        schedulerName.text(metaData.schedulerName);
        schedulerInstanceId.text(metaData.schedulerInstanceId);
        inStandbyMode.text(metaData.inStandbyMode);
        schedulerClass.text(metaData.schedulerClass);
        numberOfJobsExecuted.text(metaData.numberOfJobsExecuted);
        jobStoreSupportsPersistence.text(metaData.jobStoreSupportsPersistence);
        schedulerRemote.text(metaData.schedulerRemote);
        jobStoreClustered.text(metaData.jobStoreClustered);
        threadPoolClass.text(metaData.threadPoolClass);
        threadPoolSize.text(metaData.threadPoolSize);
        jobStoreClass.text(metaData.jobStoreClass);
        runningSince.text(_this.formatDate(new Date(metaData.runningSince)));
        summary.html(metaData.summary);
        _this.setSchedulerInStandbyMode(metaData.inStandbyMode);

    };

    // 设置 调度器状态
    this.setSchedulerInStandbyMode = function (inStandbyMode) {
        schedulerInStandbyMode = inStandbyMode;
        if (inStandbyMode == true) {
            // 当前状态：暂停
            schedulerStatus.linkbutton({iconCls: 'icon-start'});
            schedulerStatus.tooltip({position: 'top', content: '当前状态:<span style="color: red;">已暂停</span>,点击运行调度器'});
        } else {
            // 当前状态：运行
            schedulerStatus.linkbutton({iconCls: 'icon-pause'});
            schedulerStatus.tooltip({position: 'top', content: '当前状态:<span style="color: red;">正在运行</span>,点击暂停调度器'});
        }
    };

    // 初始化 调度器Context数据
    this.initContextDataTable = function () {
        contextDataTable.datagrid("loading");
        $.ajax({
            type: "POST", dataType: "JSON", url: getContextUrl, async: true,
            success: function (data) {
                if (data.success) {
                    contextDataTable.datagrid("loadData", {"total": 0, "rows": []});
                    for (var key in data.result) {
                        //noinspection JSUnfilteredForInLoop
                        var row = {"key": key, "value": data.result[key]};
                        contextDataTable.datagrid("appendRow", row);
                    }
                } else {
                    $.messager.alert("提示", data.failMessage, "warn");
                }
                contextDataTable.datagrid("loaded");
            }
        });
    };

    // 初始化 正在运行的任务
    this.initRunningJobsDataTable = function () {
        runningJobsDataTable.datagrid("loading");
        $.ajax({
            type: "POST", dataType: "JSON", url: getRunningJobsUrl, async: true,
            success: function (data) {
                if (data.success) {
                    runningJobsDataTable.datagrid("loadData", {"total": 0, "rows": []});
                    $(data.result).each(function (index, row) {
                        // row.jobData = JSON.stringify(row.jobData);
                        runningJobsDataTable.datagrid("appendRow", row);
                    });
                } else {
                    $.messager.alert("提示", data.failMessage, "warn");
                }
                runningJobsDataTable.datagrid("loaded");
            }
        });
    };

    // 时间格式化
    this.formatDate = function (dateTime) {
        var year = dateTime.getFullYear();
        var month = dateTime.getMonth() + 1;
        var date = dateTime.getDate();
        var hour = dateTime.getHours();
        var minute = dateTime.getMinutes();
        var second = dateTime.getSeconds();
        return year + "年" + month + "月" + date + "日 " + hour + ":" + minute + ":" + second;
    };

    /**
     * 打开查看对话框
     */
    this.openJsonViewDialog = function (jobData) {
        jsonViewDialog.dialog("open");
        jsonViewEdit.setValue(jobData);
    };

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.jobDataFormatter = function (value, rowData, rowIndex) {
        if (value != "" && value != null) {
            var jobData = JSON.stringify(value);
            jobData = js_beautify(jobData, 4, ' ');
            var uuid = _this.getUUID(32, 16);
            var aButton = $("<a id='" + uuid + "' href='javascript:void(0)'>查看</a>");
            $("body").on("click", "#" + uuid, function () {
                _this.openJsonViewDialog(jobData);
            });
            return $("<div></div>").append(aButton).html();
        } else {
            return value;
        }
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