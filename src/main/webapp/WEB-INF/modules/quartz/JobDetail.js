/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";
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

    // 获取所有的任务分组
    var getJobGroupNamesUrl = globalPath.mvcPath + "/quartz/jobdetail/getJobGroupNames.json";
    // 获取所有定时任务实现类名称
    var getAllJobClassNameUrl = globalPath.mvcPath + "/quartz/jobdetail/getAllJobClassName.json";
    // 获取所有的触发器分组
    var getTriggerGroupNamesUrl = globalPath.mvcPath + "/quartz/trigger/getTriggerGroupNames.json";

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

    // 新增定时任务对话框
    var addJobDetailDialog = $("#addJobDetailDialog");
    // 新增定时任务对话框 - 多页签
    var addJobDetailTabs = $("#addJobDetailTabs");
    // 新增定时任务对话框 - 新增
    var addJobDetailDialogSave = $("#addJobDetailDialogSave");
    // 新增定时任务对话框 - 取消
    var addJobDetailDialogCancel = $("#addJobDetailDialogCancel");
    // 新增定时任务对话框 - 表单
    var addJobDetailForm = $("#addJobDetailForm");
    // 新增定时任务表单 - 任务名称
    var addJobDetailJobName = $("#addJobDetailJobName");
    // 新增定时任务表单 - 任务分组
    var addJobDetailJobGroup = $("#addJobDetailJobGroup");
    // 新增定时任务表单 - 任务描述
    var addJobDetailDescription = $("#addJobDetailDescription");
    // 新增定时任务表单 - 任务实现类
    var addJobDetailJobClassName = $("#addJobDetailJobClassName");
    // 新增定时任务表单 - 支持故障恢复
    var addJobDetailRequestsRecovery = $("#addJobDetailRequestsRecovery");
    // 新增定时任务表单 - 任务数据表格
    var addJobDetailJobDataTable = $("#addJobDetailJobDataTable");
    // 新增定时任务表单 - 任务数据表格 - 编辑行
    var addJobDetailEditIndex = undefined;
    // 新增定时任务表单 - 任务数据表格 - 增加
    var addJobDetailJobDataTableButtonsAdd = $("#addJobDetailJobDataTableButtonsAdd");
    // 新增定时任务表单 - 任务数据表格 - 移除
    var addJobDetailJobDataTableButtonsRemove = $("#addJobDetailJobDataTableButtonsRemove");
    // 新增定时任务表单 - 任务数据表格 - 保存
    var addJobDetailJobDataTableButtonsSave = $("#addJobDetailJobDataTableButtonsSave");

    // 新增触发器对话框
    var addTriggerDialog = $("#addTriggerDialog");
    // 新增触发器对话框 - 多页签
    var addTriggerTabs = $("#addTriggerTabs");
    // 新增触发器对话框 - 新增
    var addTriggerDialogSave = $("#addTriggerDialogSave");
    // 新增触发器对话框 - 取消
    var addTriggerDialogCancel = $("#addTriggerDialogCancel");
    // 新增触发器对话框 - 表单
    var addTriggerForm = $("#addTriggerForm");
    // 新增触发器对话框 - 表单 - 任务分组
    var addTriggerJobGroup = $("#addTriggerJobGroup");
    // 新增触发器对话框 - 表单 - 任务名称
    var addTriggerJobName = $("#addTriggerJobName");
    // 新增触发器对话框 - 表单 - 触发器分组
    var addTriggerTriggerGroup = $("#addTriggerTriggerGroup");
    // 新增触发器对话框 - 表单 - 触发器名称
    var addTriggerTriggerName = $("#addTriggerTriggerName");
    // 新增触发器对话框 - 表单 - 开始触发时间
    var addTriggerStartTime = $("#addTriggerStartTime");
    // 新增触发器对话框 - 表单 - 结束触发时间
    var addTriggerEndTime = $("#addTriggerEndTime");
    // 新增触发器对话框 - 表单 - 优先级
    var addTriggerPriority = $("#addTriggerPriority");
    // 新增触发器对话框 - 表单 - 错过触发规则ID
    var addTriggerMisfireInstruction = $("#addTriggerMisfireInstruction");
    // 触发器类型
    var addTriggerTriggerType = $("#addTriggerTriggerType");
    // CronTrigger触发器规则
    var cronTriggerRule = $("#cronTriggerRule");
    // SimpleTrigger触发器规则
    var simpleTriggerRule = $("#simpleTriggerRule");
    // 新增触发器对话框 - 表单 - 触发的时间间隔
    var addTriggerInterval = $("#addTriggerInterval");
    // 新增触发器对话框 - 表单 - 触发的时间间隔 - 年
    var addTriggerIntervalYear = $("#addTriggerIntervalYear");
    // 新增触发器对话框 - 表单 - 触发的时间间隔 - 月
    var addTriggerIntervalMonth = $("#addTriggerIntervalMonth");
    // 新增触发器对话框 - 表单 - 触发的时间间隔 - 天
    var addTriggerIntervalDay = $("#addTriggerIntervalDay");
    // 新增触发器对话框 - 表单 - 触发的时间间隔 - 小时
    var addTriggerIntervalHour = $("#addTriggerIntervalHour");
    // 新增触发器对话框 - 表单 - 触发的时间间隔 - 分钟
    var addTriggerIntervalMinute = $("#addTriggerIntervalMinute");
    // 新增触发器对话框 - 表单 - 触发的时间间隔 - 秒
    var addTriggerIntervalSecond = $("#addTriggerIntervalSecond");
    // 新增触发器对话框 - 表单 - 触发的次数
    var addTriggerRepeatCount = $("#addTriggerRepeatCount");
    // 新增触发器对话框 - 表单 - cron表达式
    var addTriggerCron = $("#addTriggerCron");
    // 新增触发器对话框 - 表单 - 触发器描述
    var addTriggerDescription = $("#addTriggerDescription");
    // 新增触发器对话框 - 触发器数据表格
    var addTriggerDataTable = $("#addTriggerDataTable");
    // 新增定时任务表单 - 任务数据表格 - 编辑行
    var addTriggerEditIndex = undefined;
    // 新增触发器对话框 - 触发器数据表格 - 增加
    var addTriggerDataTableButtonsAdd = $("#addTriggerDataTableButtonsAdd");
    // 新增触发器对话框 - 触发器数据表格 - 移除
    var addTriggerDataTableButtonsRemove = $("#addTriggerDataTableButtonsRemove");
    // 新增触发器对话框 - 触发器数据表格 - 保存
    var addTriggerDataTableButtonsSave = $("#addTriggerDataTableButtonsSave");

    // SimpleTrigger触发器规则 - 查看
    var simpleTriggerRuleView = $("#simpleTriggerRuleView");
    // CronTrigger触发器规则 - 查看
    var cronTriggerRuleView = $("#cronTriggerRuleView");

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
            minWidth: 410,
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

        _this.initAddJobDetailDialog();
        _this.initAddTriggerDialog();
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
            addJobDetailDialog.dialog("open");
            addJobDetailTabs.tabs("select", 0);
            addJobDetailForm.form('reset');
            addJobDetailJobDataTable.datagrid("loadData", {total: 0, rows: []});
        });

        // 所有定时任务 - 删除定时任务
        jobDetailDataTableButtonsDelete.click(function () {
            _this.deleteJobDetail();
        });

        // 保存定时任务
        addJobDetailDialogSave.click(function () {
            if (addJobDetailForm.form("validate") == true) {
                addJobDetailForm.form("submit");
            }
        });

        // 新增定时任务对话框 - 取消
        addJobDetailDialogCancel.click(function () {
            addJobDetailDialog.dialog("close");
        });

        // -----------------------------------------------

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
            var row = jobDetailDataTable.datagrid("getSelected");
            if(row == null){
                $.messager.alert("提示", "请先选择定时任务！", "info");
                return;
            }
            addTriggerDialog.dialog("open");
            addTriggerTabs.tabs("select", 0);
            addTriggerForm.form('reset');
            addTriggerTriggerType.combobox("setValue", 'SimpleTrigger');
            addTriggerDataTable.datagrid("loadData", {total: 0, rows: []});
            addTriggerStartTime.datetimebox("setValue", _this.formatDate(new Date()));
            addTriggerJobGroup.textbox("setValue", row.jobGroup);
            addTriggerJobName.textbox("setValue", row.jobName);
        });

        // 选中定时任务的触发器 - 删除
        triggerDataTableButtonsDelete.click(function () {
            _this.deleteTrigger();
        });

        // 选中定时任务的触发器 - 删除所有
        triggerDataTableButtonsDeleteAll.click(function () {
            _this.deleteTriggerByJob();
        });

        // 新增触发器对话框 - 新增
        addTriggerDialogSave.click(function () {
            if (addTriggerForm.form("validate") == true) {
                var triggerType = addTriggerTriggerType.combobox("getValue");
                switch (triggerType) {
                    case 'SimpleTrigger':
                        addTriggerForm.form({url: addSimpleTriggerForJobUrl});
                        break;
                    case 'CronTrigger':
                        addTriggerForm.form({url: addCronTriggerForJobUrl});
                        break;
                    default:
                        $.messager.alert("提示", "未知的触发器类型[" + triggerType + "]", "warning");
                        break;
                }
                addTriggerForm.form("submit");
            }
        });

        // 新增触发器对话框 - 取消
        addTriggerDialogCancel.click(function () {
            addTriggerDialog.dialog("close");
        });
    };

    // ---------------------------------------------------------------------------------------------------------
    // 初始化 新增定时任务 对话框
    this.initAddJobDetailDialog = function(){
        addJobDetailDialog.dialog({
            title: "新增定时任务",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 410,
            minHeight: 260,
            modal: true,
            buttons: "#addJobDetailDialogButtons"
        });

        addJobDetailTabs.tabs({
            fit: true,
            border: false
        });

        addJobDetailForm.form({
            novalidate: false,
            url: saveJobDetailUrl,
            onSubmit: function (param) {
                var jobData = {};
                addJobDetailJobDataTable.datagrid('acceptChanges');
                var rows = addJobDetailJobDataTable.datagrid('getRows');
                if (rows == null || rows.length <= 0) {
                    return;
                }
                $(rows).each(function (index, item) {
                    jobData[item.key] = item.value;
                });
                param.jobData = JSON.stringify(jobData);
            },
            success: function (data) {
                data = JSON.parse(data);
                if (data.success) {
                    addJobDetailDialog.dialog("close");
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                    _this.initJobDetailDataTable();
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });

        addJobDetailJobGroup.combobox({
            required: true,
            validType: 'length[1,200]',
            editable: true,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        $.ajax({
            type: "POST", dataType: "JSON", url: getJobGroupNamesUrl, async: true,
            success: function (data) {
                if (data.success) {
                    var groupNames = [];
                    $(data.result).each(function (index, item) {
                        groupNames.push({"value": item, "text": item});
                    });
                    addJobDetailJobGroup.combobox("loadData", groupNames);
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });

        addJobDetailJobName.textbox({required: true, validType: 'length[1,200]'});

        addJobDetailJobClassName.combobox({
            required: true,
            validType: 'length[1,200]',
            editable: true,
            valueField: 'value',
            textField: 'text',
            panelHeight: 120
        });
        $.ajax({
            type: "POST", dataType: "JSON", url: getAllJobClassNameUrl, async: true,
            success: function (data) {
                if (data.success) {
                    var groupNames = [];
                    $(data.result).each(function (index, item) {
                        groupNames.push({"value": item, "text": item});
                    });
                    addJobDetailJobClassName.combobox("loadData", groupNames);
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });

        addJobDetailRequestsRecovery.combobox({
            required: true,
            validType: 'length[1,250]',
            editable: false,
            url: findDictTypeUrl + encodeURIComponent("是否"),
            valueField: 'value',
            textField: 'text',
            panelHeight: 50
        });

        addJobDetailDescription.textbox({
            required: true,
            validType: 'length[1,250]',
            multiline: true
        });

        var endEditing = function () {
            if (addJobDetailEditIndex == undefined) {
                return true
            }
            if (addJobDetailJobDataTable.datagrid('validateRow', addJobDetailEditIndex)) {
                addJobDetailJobDataTable.datagrid('endEdit', addJobDetailEditIndex);
                addJobDetailEditIndex = undefined;
                return true;
            } else {
                return false;
            }
        };
        addJobDetailJobDataTable.datagrid({
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            toolbar: "#addJobDetailJobDataTableButtons",
            pagination: false,
            onEndEdit: function (index, row) {

            },
            onClickCell: function (index, field) {
                if (addJobDetailEditIndex != index) {
                    if (endEditing()) {
                        addJobDetailJobDataTable.datagrid('selectRow', index).datagrid('beginEdit', index);
                        var ed = addJobDetailJobDataTable.datagrid('getEditor', {index: index, field: field});
                        if (ed) {
                            ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                        }
                        addJobDetailEditIndex = index;
                    } else {
                        setTimeout(function () {
                            addJobDetailJobDataTable.datagrid('selectRow', addJobDetailEditIndex);
                        }, 0);
                    }
                }
            }
        });
        // 新增定时任务表单 - 任务数据表格 - 增加
        addJobDetailJobDataTableButtonsAdd.click(function () {
            if (endEditing()) {
                addJobDetailJobDataTable.datagrid('appendRow', {});
                addJobDetailEditIndex = addJobDetailJobDataTable.datagrid('getRows').length - 1;
                addJobDetailJobDataTable.datagrid('selectRow', addJobDetailEditIndex).datagrid('beginEdit', addJobDetailEditIndex);
            }
        });
        // 新增定时任务表单 - 任务数据表格 - 移除
        addJobDetailJobDataTableButtonsRemove.click(function () {
            if (addJobDetailEditIndex == undefined) {
                return
            }
            addJobDetailJobDataTable.datagrid('cancelEdit', addJobDetailEditIndex).datagrid('deleteRow', addJobDetailEditIndex);
            addJobDetailEditIndex = undefined;
        });
        // 新增定时任务表单 - 任务数据表格 - 保存
        addJobDetailJobDataTableButtonsSave.click(function () {
            if (endEditing()){
                addJobDetailJobDataTable.datagrid('acceptChanges');
            }
        });
    };

    // 新增触发器对话框
    this.initAddTriggerDialog = function () {
        addTriggerTabs.tabs({fit: true, border: false});
        addTriggerJobGroup.textbox({required: true, readonly: true, validType: 'length[1,200]', value: ' '});
        addTriggerJobName.textbox({required: true, readonly: true, validType: 'length[1,200]', value: ' '});

        var getInterval = function(){
            var year = addTriggerIntervalYear.numberspinner("getValue");
            if(year == null || year == ""){
                addTriggerIntervalYear.numberspinner("isValid");
                return false;
            }
            var month = addTriggerIntervalMonth.numberspinner("getValue");
            if(month == null || month == ""){
                return false;
            }
            var day = addTriggerIntervalDay.numberspinner("getValue");
            if(day == null || day == ""){
                return false;
            }
            var hour = addTriggerIntervalHour.numberspinner("getValue");
            if(hour == null || hour == ""){
                return false;
            }
            var minute = addTriggerIntervalMinute.numberspinner("getValue");
            if(minute == null || minute == ""){
                return false;
            }
            var second = addTriggerIntervalSecond.numberspinner("getValue");
            if(second == null || second == ""){
                return false;
            }
            return year * 31104000000 + month * 2592000000 + day * 86400000 + hour * 3600000 + minute * 60000 + second * 1000;
        };
        simpleTriggerRuleView.tooltip({position:'top', content:'触发的时间间隔信 <span style="color: red;">息填写不完整</span>', showEvent: ''});
        simpleTriggerRuleView.click(function () {
            var interval = getInterval();
            if(interval === false) {
                simpleTriggerRuleView.tooltip('show');
            } else {
                var repeatCount = addTriggerRepeatCount.numberspinner("getValue");
                repeatCount = (repeatCount == "" ? 0 : repeatCount);
                _this.openSimpleTriggerViewDialog(0, repeatCount, interval);
            }
        });
        cronTriggerRuleView.tooltip({position:'top', content:'Cron表达式 <span style="color: red;">息填写不完整</span>', showEvent: ''});
        cronTriggerRuleView.click(function () {
            var cron = addTriggerCron.textbox('getValue');
            if($.trim(cron) == "") {
                cronTriggerRuleView.tooltip('show');
            } else {
                _this.openCronTriggerViewDialog(cron);
            }
        });
        addTriggerForm.form({
            novalidate: false,
            //url: saveJobDetailUrl,
            onSubmit: function (param) {
                var jobData = {};
                addTriggerDataTable.datagrid('acceptChanges');
                var rows = addTriggerDataTable.datagrid('getRows');
                if (rows == null || rows.length <= 0) {
                    return;
                }
                $(rows).each(function (index, item) {
                    jobData[item.key] = item.value;
                });
                param.jobData = JSON.stringify(jobData);
                param.interval = getInterval();
            },
            success: function (data) {
                data = JSON.parse(data);
                if (data.success) {
                    addTriggerDialog.dialog("close");
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                    _this.initTriggerDataTable();
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });

        addTriggerTriggerGroup.combobox({
            required: true,
            validType: 'length[1,200]',
            editable: true,
            valueField: 'value',
            textField: 'text',
            panelHeight: 120
        });
        $.ajax({
            type: "POST", dataType: "JSON", url: getTriggerGroupNamesUrl, async: true,
            success: function (data) {
                if (data.success) {
                    var groupNames = [];
                    $(data.result).each(function (index, item) {
                        groupNames.push({"value": item, "text": item});
                    });
                    addTriggerTriggerGroup.combobox("loadData", groupNames);
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });

        addTriggerTriggerName.textbox({required: true, validType: 'length[1,200]'});
        addTriggerStartTime.datetimebox({required: true, value: _this.formatDate(new Date(), true)});
        addTriggerEndTime.datetimebox({required: false});
        addTriggerPriority.numberspinner({required: true, min: 1, max: 10, value: 5});
        addTriggerMisfireInstruction.numberspinner({required: true, min: 0, max: 10, value: 0});
        addTriggerRepeatCount.numberspinner({required: true, min: 1, value: 1});
        addTriggerCron.textbox({required: true, validType: 'length[1,200]'});
        addTriggerDescription.textbox({required: true, validType: 'length[1,250]', multiline: true});

        addTriggerIntervalYear.numberspinner({required: true, min: 0, max: 50, value: 0});
        addTriggerIntervalMonth.numberspinner({required: true, min: 0, max: 11, value: 0});
        addTriggerIntervalDay.numberspinner({required: true, min: 0, max: 29, value: 0});
        addTriggerIntervalHour.numberspinner({required: true, min: 0, max: 23, value: 0});
        addTriggerIntervalMinute.numberspinner({required: true, min: 0, max: 59, value: 0});
        addTriggerIntervalSecond.numberspinner({required: true, min: 0, max: 59, value: 0});

        var selectSimpleTrigger = function () {
            addTriggerIntervalYear.numberspinner({required: true});
            addTriggerIntervalMonth.numberspinner({required: true});
            addTriggerIntervalDay.numberspinner({required: true});
            addTriggerIntervalHour.numberspinner({required: true});
            addTriggerIntervalMinute.numberspinner({required: true});
            addTriggerIntervalSecond.numberspinner({required: true});
            simpleTriggerRule.show();
            addTriggerCron.textbox({required: false});
            cronTriggerRule.hide();
        };
        var selectCronTrigger = function () {
            addTriggerIntervalYear.numberspinner({required: false});
            addTriggerIntervalMonth.numberspinner({required: false});
            addTriggerIntervalDay.numberspinner({required: false});
            addTriggerIntervalHour.numberspinner({required: false});
            addTriggerIntervalMinute.numberspinner({required: false});
            addTriggerIntervalSecond.numberspinner({required: false});
            simpleTriggerRule.hide();
            addTriggerCron.textbox({required: true});
            cronTriggerRule.show();
        };
        addTriggerTriggerType.combobox({
            required: true,
            editable: false,
            valueField: 'value',
            textField: 'text',
            data: [{text: 'SimpleTrigger - 简单触发器', value: 'SimpleTrigger'}, {text: 'CronTrigger - Cron表达式触发器', value: 'CronTrigger'}],
            panelHeight: 50,
            value: 'SimpleTrigger',
            onChange: function (newValue, oldValue) {
                switch (newValue) {
                    case 'SimpleTrigger':
                        selectSimpleTrigger();
                        break;
                    case 'CronTrigger':
                        selectCronTrigger();
                        break;
                }
            }
        });
        selectSimpleTrigger();

        addTriggerDialog.dialog({
            title: "新增触发器",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 410,
            minHeight: 260,
            modal: true,
            buttons: "#addTriggerDialogButtons",
            onOpen: function() {
                selectSimpleTrigger();
            }
        });

        var endEditing = function () {
            if (addTriggerEditIndex == undefined) {
                return true
            }
            if (addTriggerDataTable.datagrid('validateRow', addTriggerEditIndex)) {
                addTriggerDataTable.datagrid('endEdit', addTriggerEditIndex);
                addTriggerEditIndex = undefined;
                return true;
            } else {
                return false;
            }
        };
        addTriggerDataTable.datagrid({
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            toolbar: "#addTriggerDataTableButtons",
            pagination: false,
            onEndEdit: function (index, row) {

            },
            onClickCell: function (index, field) {
                if (addTriggerEditIndex != index) {
                    if (endEditing()) {
                        addTriggerDataTable.datagrid('selectRow', index).datagrid('beginEdit', index);
                        var ed = addTriggerDataTable.datagrid('getEditor', {index: index, field: field});
                        if (ed) {
                            ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                        }
                        addTriggerEditIndex = index;
                    } else {
                        setTimeout(function () {
                            addTriggerDataTable.datagrid('selectRow', addTriggerEditIndex);
                        }, 0);
                    }
                }
            }
        });
        // 新增触发器对话框 - 触发器数据表格 - 增加
        addTriggerDataTableButtonsAdd.click(function () {
            if (endEditing()) {
                addTriggerDataTable.datagrid('appendRow', {});
                addTriggerEditIndex = addTriggerDataTable.datagrid('getRows').length - 1;
                addTriggerDataTable.datagrid('selectRow', addTriggerEditIndex).datagrid('beginEdit', addTriggerEditIndex);
            }
        });
        // 新增触发器对话框 - 触发器数据表格 - 移除
        addTriggerDataTableButtonsRemove.click(function () {
            if (addTriggerEditIndex == undefined) {
                return
            }
            addTriggerDataTable.datagrid('cancelEdit', addTriggerEditIndex).datagrid('deleteRow', addTriggerEditIndex);
            addTriggerEditIndex = undefined;
        });
        // 新增触发器对话框 - 触发器数据表格 - 保存
        addTriggerDataTableButtonsSave.click(function () {
            if (endEditing()){
                addTriggerDataTable.datagrid('acceptChanges');
            }
        });
    };

    // 初始化定时任务
    this.initJobDetailDataTable = function () {
        jobDetailDataTable.datagrid("loading");
        $.ajax({
            type: "POST", dataType: "JSON", url: getAllJobDetailUrl, async: true,
            success: function (data) {
                if (data.success) {
                    jobDetailDataTable.datagrid("loadData", {total: data.result.length, rows: data.result});
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
                triggerDataTable.datagrid("loadData", {total: 0, rows: []});
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
                    triggerDataTable.datagrid("loadData", {total: data.result.length, rows: data.result});
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
        simpleTriggerRepeatInterval.text(_this.getEasyTime(repeatInterval));
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
                return aButton_1 + " " + rowData.timesTriggered + "/" + rowData.repeatCount + "(" + _this.getEasyTime(rowData.repeatInterval) + ")";
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
        return year + '-'
            + (month < 10 ? ('0' + month) : month) + '-'
            + (date < 10 ? ('0' + date) : date) + ' '
            + (hour < 10 ? ('0' + hour) : hour) + ':'
            + (minute < 10 ? ('0' + minute) : minute) + ":"
            + (second < 10 ? ('0' + second) : second);
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