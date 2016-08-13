/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    // 获取所有的JobDetail
    var getAllJobDetailUrl = globalPath.mvcPath + "/quartz/jobdetail/getAllJobDetail.json";
    // 暂停所有任务
    var pauseAllUrl = globalPath.mvcPath + "/quartz/scheduler/pauseAll.json";
    // 取消暂停所有任务
    var resumeAllUrl = globalPath.mvcPath + "/quartz/scheduler/resumeAll.json";
    // 暂停任务
    var pauseJobUrl = globalPath.mvcPath + "/quartz/jobdetail/pauseJob.json";
    // 取消暂停任务
    var resumeJobUrl = globalPath.mvcPath + "/quartz/jobdetail/resumeJob.json";
    // 立即运行JobDetail
    var triggerJobUrl = globalPath.mvcPath + "/quartz/jobdetail/triggerJob.json";
    // 增加定时任务
    var saveJobDetailUrl = globalPath.mvcPath + "/quartz/jobdetail/saveJobDetail.json";
    // 删除定时任务
    var deleteJobDetailUrl = globalPath.mvcPath + "/quartz/jobdetail/deleteJobDetail.json";

    // 获取JobDetail的所有Trigger
    var getTriggerByJobUrl = globalPath.mvcPath + "/quartz/trigger/getTriggerByJob.json";
    // 暂停触发器
    var pauseTriggerUrl = globalPath.mvcPath + "/quartz/trigger/pauseTrigger.json";
    // 取消暂停触发器
    var resumeTriggerUrl = globalPath.mvcPath + "/quartz/trigger/resumeTrigger.json";
    // 验证Cron表达式
    var validatorCronUrl = globalPath.mvcPath + "/quartz/trigger/validatorCron.json";
    // 新增简单的触发器
    var addSimpleTriggerForJobUrl = globalPath.mvcPath + "/quartz/trigger/addSimpleTriggerForJob.json";
    // 新增Cron表达式触发器
    var addCronTriggerForJobUrl = globalPath.mvcPath + "/quartz/trigger/addCronTriggerForJob.json";
    // 删除触发器
    var deleteTriggerUrl = globalPath.mvcPath + "/quartz/trigger/deleteTrigger.json";
    // 删除一个定时任务的所有触发器
    var deleteTriggerByJobUrl = globalPath.mvcPath + "/quartz/trigger/deleteTriggerByJob.json";

    // 所有定时任务
    var jobDetailDataTable = $("#jobDetailDataTable");
    // 所有定时任务 - 刷新
    var jobDetailDataTableButtonsReload = $("#jobDetailDataTableButtonsReload");
    // 所有定时任务 - 暂停所有
    var jobDetailDataTableButtonsPauseAll = $("#jobDetailDataTableButtonsPauseAll");
    // 所有定时任务 - 继续所有
    var jobDetailDataTableButtonsResumeAll = $("#jobDetailDataTableButtonsResumeAll");
    // 所有定时任务 - 暂停
    var jobDetailDataTableButtonsPause = $("#jobDetailDataTableButtonsPause");
    // 所有定时任务 - 继续
    var jobDetailDataTableButtonsResume = $("#jobDetailDataTableButtonsResume");
    // 所有定时任务 - 立即执行
    var jobDetailDataTableButtonsExecuting = $("#jobDetailDataTableButtonsExecuting");
    // 所有定时任务 - 新增定时任务
    var jobDetailDataTableButtonsAdd = $("#jobDetailDataTableButtonsAdd");
    // 所有定时任务 - 删除定时任务
    var jobDetailDataTableButtonsDelete = $("#jobDetailDataTableButtonsDelete");
    // 所有定时任务 - 删除所有定时任务
    var triggerDataTableButtonsDeleteAll = $("#triggerDataTableButtonsDeleteAll");
    // 选中的定时任务
    var selectJobDetail = null;

    // 选中定时任务的触发器
    var triggerDataTable = $("#triggerDataTable");
    // 选中定时任务的触发器 - 刷新
    var triggerDataTableButtonsReload = $("#triggerDataTableButtonsReload");
    // 选中定时任务的触发器 - 暂停
    var triggerDataTableButtonsPause = $("#triggerDataTableButtonsPause");
    // 选中定时任务的触发器 - 继续
    var triggerDataTableButtonsResume = $("#triggerDataTableButtonsResume");
    // 选中定时任务的触发器 - 新增
    var triggerDataTableButtonsAdd = $("#triggerDataTableButtonsAdd");
    // 选中定时任务的触发器 - 删除
    var triggerDataTableButtonsDelete = $("#triggerDataTableButtonsDelete");
    // 显示当前选择的定时任务
    var selectJobDetailText = $("#selectJobDetailText");

    // 参看json数据对话框
    var jsonViewDialog = $("#jsonViewDialog");
    // 数据查看高亮控件
    var jsonViewEdit = null;

    // SimpleTriggerImpl 触发器 处罚规则对话框
    var simpleTriggerViewDialog = $("#simpleTriggerViewDialog");
    // 已经触发的次数
    var simpleTriggerTimesTriggered = $("#simpleTriggerTimesTriggered");
    // 需要触发次数
    var simpleTriggerRepeatCount = $("#simpleTriggerRepeatCount");
    // 重复触发时间间隔
    var simpleTriggerRepeatInterval = $("#simpleTriggerRepeatInterval");
    // 预计下次触发时间
    var simpleTriggerNextFireTime = $("#simpleTriggerNextFireTime");

    // CronTriggerImpl 触发器 处罚规则对话框
    var cronTriggerViewDialog = $("#cronTriggerViewDialog");
    // Cron表达式
    var cronTriggerCronEx = $("#cronTriggerCronEx");
    // 预计触发时间
    var cronTriggerNextTime = $("#cronTriggerNextTime");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        jobDetailDataTable.datagrid({
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#jobDetailDataTableButtons",
            pagination: false,
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {

            },
            onSelect: function (rowIndex, rowData) {
                var jobDetail = rowData.schedName + "." + rowData.jobGroup + "." + rowData.jobName;
                if (selectJobDetail == jobDetail) {
                    return;
                }
                selectJobDetail = jobDetail;
                _this.initTriggerDataTable({"jobName": rowData.jobName, "jobGroup": rowData.jobGroup, "description": rowData.description});
            }
        });
        jobDetailDataTableButtonsPauseAll.tooltip({position: 'bottom', content: '<span style="color: red;">暂停</span>所有定时任务'});
        jobDetailDataTableButtonsResumeAll.tooltip({position: 'bottom', content: '<span style="color: red;">取消暂停</span>所有定时任务'});
        jobDetailDataTableButtonsPause.tooltip({position: 'bottom', content: '<span style="color: red;">暂停</span>选中的定时任务'});
        jobDetailDataTableButtonsResume.tooltip({position: 'bottom', content: '<span style="color: red;">取消暂停</span>选中的定时任务'});
        jobDetailDataTableButtonsExecuting.tooltip({position: 'bottom', content: '<span style="color: red;">立即执行</span>选中的定时任务'});
        jobDetailDataTableButtonsAdd.tooltip({position: 'bottom', content: '<span style="color: red;">新增</span>定时任务'});
        jobDetailDataTableButtonsDelete.tooltip({position: 'bottom', content: '<span style="color: red;">删除</span>定时任务'});

        triggerDataTableButtonsPause.tooltip({position: 'bottom', content: '<span style="color: red;">暂停</span>选中触发器'});
        triggerDataTableButtonsResume.tooltip({position: 'bottom', content: '<span style="color: red;">取消暂停</span>选中触发器'});
        triggerDataTableButtonsAdd.tooltip({position: 'bottom', content: '<span style="color: red;">新增</span>选中触发器'});
        triggerDataTableButtonsDelete.tooltip({position: 'bottom', content: '<span style="color: red;">删除</span>选中触发器'});
        triggerDataTableButtonsDeleteAll.tooltip({position: 'bottom', content: '<span style="color: red;">删除</span>定时任务的所有触发器'});

        triggerDataTable.datagrid({
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#triggerDataTableButtons",
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

        simpleTriggerViewDialog.dialog({
            title: "查看触发器规则",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 370,
            minHeight: 180,
            modal: true
        });

        cronTriggerViewDialog.dialog({
            title: "查看触发器规则",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 410,
            minHeight: 260,
            modal: true
        });

        _this.dataBind();
        _this.eventBind();
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        _this.initJobDetailDataTable();
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        // 所有定时任务 - 刷新
        jobDetailDataTableButtonsReload.click(function () {
            _this.initJobDetailDataTable();
        });

        // 正在运行的任务 - 暂停所有
        jobDetailDataTableButtonsPauseAll.click(function () {
            _this.pauseAll();
        });

        // 正在运行的任务 - 继续所有
        jobDetailDataTableButtonsResumeAll.click(function () {
            _this.resumeAll();
        });

        // 所有定时任务 - 立即执行
        jobDetailDataTableButtonsExecuting.click(function () {
            _this.triggerJob();
        });

        // 所有定时任务 - 新增定时任务
        jobDetailDataTableButtonsAdd.click(function () {
            //_this.triggerJob();
        });

        // 所有定时任务 - 删除定时任务
        jobDetailDataTableButtonsDelete.click(function () {
            _this.deleteJobDetail();
        });

        // 所有定时任务 - 暂停
        jobDetailDataTableButtonsPause.click(function () {
            _this.pauseJob();
        });

        // 所有定时任务 - 继续
        jobDetailDataTableButtonsResume.click(function () {
            _this.resumeJob();
        });

        // 选中定时任务的触发器 - 刷新
        triggerDataTableButtonsReload.click(function () {
            _this.initTriggerDataTable();
        });

        // 选中定时任务的触发器 - 暂停
        triggerDataTableButtonsPause.click(function () {
            _this.pauseTrigger();
        });

        // 选中定时任务的触发器 - 继续
        triggerDataTableButtonsResume.click(function () {
            _this.resumeTrigger();
        });

        // 选中定时任务的触发器 - 新增
        triggerDataTableButtonsAdd.click(function () {
            //_this.triggerJob();
        });

        // 选中定时任务的触发器 - 删除
        triggerDataTableButtonsDelete.click(function () {
            _this.deleteTrigger();
        });

        // 选中定时任务的触发器 - 删除所有
        triggerDataTableButtonsDeleteAll.click(function () {
            _this.deleteTriggerByJob();
        });
    };

    // ---------------------------------------------------------------------------------------------------------
    // 初始化定时任务
    this.initJobDetailDataTable = function () {
        jobDetailDataTable.datagrid("loading");
        $.ajax({
            type: "POST", dataType: "JSON", url: getAllJobDetailUrl, async: true,
            success: function (data) {
                if (data.success) {
                    jobDetailDataTable.datagrid("loadData", {"total": data.result.length, "rows": data.result});
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
                jobDetailDataTable.datagrid("loaded");
            }
        });
    };

    // 初始化触发器数据
    this.initTriggerDataTable = function (param) {
        if(!param){
            var row = jobDetailDataTable.datagrid("getSelected");
            if(row == null){
                triggerDataTable.datagrid("loadData", {"total": 0, "rows": []});
                selectJobDetailText.text("");
                return;
            }
            param = {"jobName": row.jobName, "jobGroup": row.jobGroup, "description": row.description};
        }
        selectJobDetailText.text("[" + param.jobGroup + "." + param.jobName + "] - " + param.description);
        triggerDataTable.datagrid("loading");
        $.ajax({
            type: "POST", dataType: "JSON", url: getTriggerByJobUrl, data: param, async: true,
            success: function (data) {
                if (data.success) {
                    triggerDataTable.datagrid("loadData", {"total": data.result.length, "rows": data.result});
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
                triggerDataTable.datagrid("loaded");
            }
        });
    };

    // 暂停所有定时任务
    this.pauseAll = function () {
        $.messager.confirm('确认', '您确定要暂停所有定时任务？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST", dataType: "JSON", url: pauseAllUrl, async: true,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                        _this.initTriggerDataTable();
                    }
                });
            }
        });
    };

    // 取消暂停所有定时任务
    this.resumeAll = function () {
        $.messager.confirm('确认', '您确定要取消暂停所有定时任务？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST", dataType: "JSON", url: resumeAllUrl, async: true,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                        _this.initTriggerDataTable();
                    }
                });
            }
        });
    };

    // 暂停定时任务
    this.pauseJob = function () {
        var row = jobDetailDataTable.datagrid("getSelected");
        if(row == null){
            $.messager.alert("提示", "请先选择定时任务！", "info");
            return;
        }
        var param = {"jobName": row.jobName, "jobGroup": row.jobGroup};
        $.messager.confirm('确认', '您确定要暂停定时任务:<br/>' + row.jobGroup + '.' + row.jobName + ' ？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST", dataType: "JSON", url: pauseJobUrl, data: param, async: true,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                        _this.initTriggerDataTable();
                    }
                });
            }
        });
    };

    // 取消暂停定时任务
    this.resumeJob = function () {
        var row = jobDetailDataTable.datagrid("getSelected");
        if(row == null){
            $.messager.alert("提示", "请先选择定时任务！", "info");
            return;
        }
        var param = {"jobName": row.jobName, "jobGroup": row.jobGroup};
        $.messager.confirm('确认', '您确定要取消暂停定时任务:<br/>[' + row.jobGroup + '.' + row.jobName + '] ？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST", dataType: "JSON", url: resumeJobUrl, data: param, async: true,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                        _this.initTriggerDataTable();
                    }
                });
            }
        });
    };

    // 立即运行定时任务
    this.triggerJob = function () {
        var row = jobDetailDataTable.datagrid("getSelected");
        if(row == null){
            $.messager.alert("提示", "请先选择定时任务！", "info");
            return;
        }
        var param = {"jobName": row.jobName, "jobGroup": row.jobGroup};
        $.messager.confirm('确认', '您确定要立即运行定时任务:<br/>[' + row.jobGroup + '.' + row.jobName + '] ？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST", dataType: "JSON", url: triggerJobUrl, data: param, async: true,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                        _this.initTriggerDataTable();
                    }
                });
            }
        });
    };

    // 删除定时任务
    this.deleteJobDetail = function() {
        var row = jobDetailDataTable.datagrid("getSelected");
        if(row == null){
            $.messager.alert("提示", "请先选择定时任务！", "info");
            return;
        }
        var param = {"jobName": row.jobName, "jobGroup": row.jobGroup};
        $.messager.confirm('确认', '您确定要删除定时任务:<br/>[' + row.jobGroup + '.' + row.jobName + '] ？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST", dataType: "JSON", url: deleteJobDetailUrl, data: param, async: true,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                        _this.initJobDetailDataTable();
                        _this.initTriggerDataTable();
                    }
                });
            }
        });
    };

    // 暂停触发器
    this.pauseTrigger = function(){
        var row = triggerDataTable.datagrid("getSelected");
        if(row == null){
            $.messager.alert("提示", "请先选择触发器！", "info");
            return;
        }
        var param = {"triggerGroup": row.triggerGroup, "triggerName": row.triggerName};
        $.messager.confirm('确认', '您确定要暂停触发器:<br/>[' + row.triggerGroup + '.' + row.triggerName + '] ？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST", dataType: "JSON", url: pauseTriggerUrl, data: param, async: true,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                        _this.initTriggerDataTable();
                    }
                });
            }
        });
    };

    // 取消暂停触发器
    this.resumeTrigger = function(){
        var row = triggerDataTable.datagrid("getSelected");
        if(row == null){
            $.messager.alert("提示", "请先选择触发器！", "info");
            return;
        }
        var param = {"triggerGroup": row.triggerGroup, "triggerName": row.triggerName};
        $.messager.confirm('确认', '您确定要取消暂停触发器:<br/>[' + row.triggerGroup + '.' + row.triggerName + '] ？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST", dataType: "JSON", url: resumeTriggerUrl, data: param, async: true,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                        _this.initTriggerDataTable();
                    }
                });
            }
        });
    };

    // 删除触发器
    this.deleteTrigger = function () {
        var row = triggerDataTable.datagrid("getSelected");
        if (row == null) {
            $.messager.alert("提示", "请先选择触发器！", "info");
            return;
        }
        var param = {"triggerGroup": row.triggerGroup, "triggerName": row.triggerName};
        $.messager.confirm('确认', '您确定要删除触发器:<br/>[' + row.triggerGroup + '.' + row.triggerName + '] ？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST", dataType: "JSON", url: deleteTriggerUrl, data: param, async: true,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                        _this.initTriggerDataTable();
                    }
                });
            }
        });
    };

    // 删除定时任务的所有触发器
    this.deleteTriggerByJob = function(){
        var row = jobDetailDataTable.datagrid("getSelected");
        if(row == null){
            $.messager.alert("提示", "请先选择定时任务！", "info");
            return;
        }
        var param = {"jobName": row.jobName, "jobGroup": row.jobGroup};
        $.messager.confirm('确认', '您确定要删除定时任务的所有触发器<br/>定时任务:[' + row.jobGroup + '.' + row.jobName + '] ？', function (r) {
            if (r) {
                $.ajax({
                    type: "POST", dataType: "JSON", url: deleteTriggerByJobUrl, data: param, async: true,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                        _this.initTriggerDataTable();
                    }
                });
            }
        });
    };

    // 打开查看对话框
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

    // 打开 SimpleTriggerImpl 触发器 处罚规则对话框
    this.openSimpleTriggerViewDialog = function (timesTriggered, repeatCount, repeatInterval) {
        simpleTriggerTimesTriggered.text(timesTriggered + "次");
        simpleTriggerRepeatCount.text(repeatCount + "次");
        simpleTriggerRepeatInterval.text((repeatInterval / 1000) + "秒");
        var dateLong = new Date().getTime() + repeatInterval;
        simpleTriggerNextFireTime.text(_this.formatDate(new Date(dateLong)));
        simpleTriggerViewDialog.dialog("open");
    };

    // CronTriggerImpl 触发器 处罚规则对话框
    this.openCronTriggerViewDialog = function (cronEx) {
        $.ajax({
            type: "POST", dataType: "JSON", url: validatorCronUrl, data: {"cron": cronEx}, async: true,
            success: function (data) {
                if (data.success) {
                    cronTriggerCronEx.text(cronEx);
                    var div = $("<div></div>");
                    $(data.result).each(function (index, item) {
                        var itemDiv = $("<div style='margin-top: 5px;margin-bottom: 5px;'></div>");
                        itemDiv.append("<label style='display:inline-block; width: 70px;text-align: center;'>第" + (index + 1) + "次: </label>");
                        itemDiv.append("<label>" + item + "</label><br/>");
                        div.append(itemDiv);
                    });
                    cronTriggerNextTime.html(div.html());
                    cronTriggerViewDialog.dialog("open");
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
    };

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.ruleFormatter = function (value, rowData, rowIndex) {
        switch (rowData.triggerType) {
            case "org.quartz.impl.triggers.SimpleTriggerImpl":
                var aButton_1 = "<a href='javascript:void(0)' onclick='pageJsObject.openSimpleTriggerViewDialog(" +
                    rowData.timesTriggered + "," +
                    rowData.repeatCount + "," +
                    rowData.repeatInterval + ")'>详情</a>";
                return aButton_1 + " " + rowData.timesTriggered + "/" + rowData.repeatCount + " (" + (rowData.repeatInterval / 1000) + "秒) ";
                break;
            case "org.quartz.impl.triggers.CronTriggerImpl":
                var aButton_2 = "<a href='javascript:void(0)' onclick='pageJsObject.openCronTriggerViewDialog(\"" + rowData.cronEx + "\")'>详情</a>";
                return aButton_2 + " (" + rowData.cronEx + ") ";
                break;
            default:
                return "未知";
        }
    };

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.triggerStateFormatter = function (value, rowData, rowIndex) {
        switch (value) {
            case "PAUSED_BLOCKED":
                return "停止_阻塞";
            case "PAUSED":
                return "停止";
            case "WAITING":
                return "等待执行";
            case "ACQUIRED":
                return "已获得";
            case "EXECUTING":
                return "执行中";
            case "COMPLETE":
                return "完成";
            case "BLOCKED":
                return "阻塞";
            case "ERROR":
                return "错误";
            case "DELETED":
                return "已删除";
            case "NORMAL":
                return "正常";
            default:
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