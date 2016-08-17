/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 获取所有的任务分组
    var getJobGroupNamesUrl = globalPath.mvcPath + "/quartz/jobdetail/getJobGroupNames.json";
    // 获取所有定时任务实现类名称
    var getAllJobClassNameUrl = globalPath.mvcPath + "/quartz/jobdetail/getAllJobClassName.json";
    // 定时任务日志分页查询地址
    var findQrtzJobLogByPageUrl = globalPath.mvcPath + "/quartz/joblog/findQrtzJobLogByPage.json";

    // 查询表单
    var searchForm = $("#searchForm");
    // 调度器名称
    var searchSchedulerName = $("#searchSchedulerName");
    // 调度器ID
    var searchInstanceName = $("#searchInstanceName");
    // 任务分组
    var searchJobGroup = $("#searchJobGroup");
    // 任务名称
    var searchJobName = $("#searchJobName");
    // 任务实现类
    var searchJobClassName = $("#searchJobClassName");
    // 触发时间-起始值
    var searchStartTimeByStart = $("#searchStartTimeByStart");
    // 触发时间-结束值
    var searchStartTimeByEnd = $("#searchStartTimeByEnd");
    // 处理时间-最小值
    var searchProcessTimeByMin = $("#searchProcessTimeByMin");
    // 处理时间-最大值
    var searchProcessTimeByMax = $("#searchProcessTimeByMax");
    // 数据表格
    var jobDetailLogDataTable = $("#jobDetailLogDataTable");
    // 查询按钮
    var jobDetailLogDataTableButtonsSearch = $("#jobDetailLogDataTableButtonsSearch");

    // jobData 数据查看对话框
    var jsonViewDialog = $("#jsonViewDialog");
    // jobData对比编辑器
    var jobDataMergeView = null;

    // 任务执行异常信息查看对话框
    var exceptionInfoViewDialog = $("#exceptionInfoViewDialog");
    // 异常信息查看器
    var viewExceptionInfo = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        searchSchedulerName.textbox({});
        searchInstanceName.textbox({});
        searchJobGroup.combobox({editable: true, valueField: 'value', textField: 'text', panelHeight: 80});
        $.ajax({
            type: "POST", dataType: "JSON", url: getJobGroupNamesUrl, async: true,
            success: function (data) {
                if (data.success) {
                    var groupNames = [];
                    $(data.result).each(function (index, item) {
                        groupNames.push({"value": item, "text": item});
                    });
                    searchJobGroup.combobox("loadData", groupNames);
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
        searchJobName.textbox({});
        searchJobClassName.combobox({editable: true, valueField: 'value', textField: 'text', panelHeight: 120});
        $.ajax({
            type: "POST", dataType: "JSON", url: getAllJobClassNameUrl, async: true,
            success: function (data) {
                if (data.success) {
                    var groupNames = [];
                    $(data.result).each(function (index, item) {
                        groupNames.push({"value": item, "text": item});
                    });
                    searchJobClassName.combobox("loadData", groupNames);
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
        searchStartTimeByStart.datetimebox({});
        searchStartTimeByEnd.datetimebox({});
        searchProcessTimeByMin.numberspinner({min:0});
        searchProcessTimeByMax.numberspinner({min:0});

        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        jobDetailLogDataTable.datagrid({
            url: findQrtzJobLogByPageUrl,
            idField: 'id',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: true,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#jobDetailLogDataTableButtons",
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
                if (param.processTimeByMin && param.processTimeByMin != null && param.processTimeByMin != "") {
                    param.processTimeByMin = param.processTimeByMin * 1000;
                }
                if (param.processTimeByMax && param.processTimeByMax != null && param.processTimeByMax != "") {
                    param.processTimeByMax = param.processTimeByMax * 1000;
                }
            }
        });

        _this.initJsonViewDialog();
        _this.initExceptionInfoViewDialog();
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
        jobDetailLogDataTableButtonsSearch.click(function () {
            jobDetailLogDataTable.datagrid('load');
        });
    };

    // ---------------------------------------------------------------------------------------------------------
    this.initExceptionInfoViewDialog = function(){
        exceptionInfoViewDialog.dialog({
            title: "任务异常日志信息",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            minWidth: 850,
            minHeight: 450,
            modal: true,
            //buttons: "#",
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
                    readOnly: true
                });
                viewExceptionInfo.setSize("auto", "auto");
                viewExceptionInfo.setOption("theme", "cobalt");
                viewExceptionInfo.setValue("");
            }
        });
    };

    this.initJsonViewDialog = function(){
        var mergeViewHeight = function (mergeView) {
            function editorHeight(editor) {
                if (!editor) return 0;
                return editor.getScrollInfo().clientHeight;
            }

            return Math.max(editorHeight(mergeView.leftOriginal()),
                editorHeight(mergeView.editor()),
                editorHeight(mergeView.rightOriginal()));
        };
        var resize = function (mergeView, height) {
            if (!height || height <= 0) {
                height = mergeViewHeight(mergeView);
            }
            for (var i = 0; i < 1000; i++) {
                if (mergeView.leftOriginal())
                    mergeView.leftOriginal().setSize(null, height);
                mergeView.editor().setSize(null, height);
                if (mergeView.rightOriginal())
                    mergeView.rightOriginal().setSize(null, height);
                var newHeight = mergeViewHeight(mergeView);
                if (newHeight >= height) break;
                else height = newHeight;
            }
            mergeView.wrap.style.height = height + "px";
        };

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
                if (jobDataMergeView != null) {
                    return;
                }
                jobDataMergeView = CodeMirror.MergeView(document.getElementById("jobDataMergeView"), {
                    mode: "application/json",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: true,
                    origLeft: null,
                    value: "",
                    origRight: "",
                    revertButtons: false,
                    //connect: 'align',
                    collapseIdentical: true,
                    allowEditingOriginals: false,
                    //showDifferences: false,
                    highlightDifferences: false
                });
                resize(jobDataMergeView, jsonViewDialog.dialog("options").height - 39);
            },
            onResize: function (width, height) {
                if (jobDataMergeView == null) {
                    return;
                }
                resize(jobDataMergeView, height - 39);
            }
        });
    };

    // 打开obData数据查看对话框
    this.openJsonViewDialog = function(beforeJobData, afterJobData){
        jsonViewDialog.dialog("open");
        jobDataMergeView.editor().setValue(beforeJobData);
        jobDataMergeView.rightOriginal().setValue(afterJobData);
    };

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.jobDataFormatter = function (value, rowData, rowIndex) {
        var beforeJobData = rowData.beforeJobData;
        beforeJobData = js_beautify(beforeJobData == null ? "" : beforeJobData, 4, ' ');
        var afterJobData = rowData.afterJobData;
        afterJobData = js_beautify(afterJobData == null ? "" : afterJobData, 4, ' ');
        var uuid = _this.getUUID(32, 16);
        var aButton = $("<a id='" + uuid + "' href='javascript:void(0)'>查看</a>");
        $("body").on("click", "#" + uuid, function () {
            _this.openJsonViewDialog(beforeJobData, afterJobData);
        });
        return $("<div></div>").append(aButton).html();
    };

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.processTimeFormatter = function (value, rowData, rowIndex) {
        if (value == null || !$.isNumeric(value)) {
            return "未知";
        }
        return _this.getEasyTime(value);
    };

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.isVetoFormatter = function (value, rowData, rowIndex) {
        switch (value) {
            case "0":
                return "否";
                break;
            case "1":
                return "是";
                break;
            default:
                return "未知";
                break;
        }
    };

    // 打开任务执行异常信息查看对话框
    this.openExceptionInfoViewDialog = function(exceptionInfo){
        exceptionInfoViewDialog.dialog("open");
        viewExceptionInfo.setValue("");
        viewExceptionInfo.setValue(exceptionInfo);
    };

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.exceptionInfoFormatter = function (value, rowData, rowIndex) {
        if (!value) {
            return "正常";
        }
        var uuid = _this.getUUID(32, 16);
        var aButton = $("<a id='" + uuid + "' href='javascript:void(0)'>查看</a>");
        $("body").on("click", "#" + uuid, function () {
            _this.openExceptionInfoViewDialog(value);
        });
        return $("<div></div>").append(aButton).html();
    };

    // 毫秒转可读的时间
    this.getEasyTime = function (timeMillisecond) {
        var millisecond = timeMillisecond % 1000;
        var second = Math.floor(timeMillisecond / 1000) % 60;
        var minute = Math.floor(timeMillisecond / 1000 / 60) % 60;
        var hour = Math.floor(timeMillisecond / 1000 / 60 / 60) % 24;
        var day = Math.floor(timeMillisecond / 1000 / 60 / 60 / 24) % 30;
        var month = Math.floor(timeMillisecond / 1000 / 60 / 60 / 24 / 30) % 12;
        var year = Math.floor(timeMillisecond / 1000 / 60 / 60 / 24 / 30 / 12);
        var result = "";
        if (year >= 1) {
            result = year + "年";
        }
        if (month >= 1) {
            result = result + month + "月";
        }
        if (day >= 1) {
            result = result + day + "天";
        }
        if (hour >= 1) {
            result = result + hour + "小时";
        }
        if (minute >= 1) {
            result = result + minute + "分钟";
        }
        if (second >= 1) {
            result = result + second + "秒";
        }
        if (millisecond >= 1) {
            result = result + millisecond + "毫秒";
        }
        if(result == ""){
            result = "0毫秒";
        }
        return result;
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