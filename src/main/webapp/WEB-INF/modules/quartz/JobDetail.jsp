<%--
  作者: LiZW
  时间: 2016-8-9 17:35
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%-- EasyUI --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/extend/jquery.easyui.customize.js"></script>

    <%-- CodeMirror --%>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.js"></script>
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.css">
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/theme/cobalt.css">
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/javascript/javascript.js"></script>

    <%--代码格式化JS--%>
    <script src="${applicationScope.staticPath}/CodeFormat/jsbeautify.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/quartz/JobDetail.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/quartz/JobDetail.js"></script>
    <title>定时任务(JobDetail)管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:true,height:'55%',collapsible:false,iconCls:'icon-jobs',title:'所有定时任务'">
    <table id="jobDetailDataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:200,align:'left',hidden:false,field:'schedName'">调度器名称</th>
            <th data-options="width:100,align:'left',hidden:false,field:'jobGroup'">任务分组</th>
            <th data-options="width:100,align:'left',hidden:false,field:'jobName'">任务名称</th>
            <th data-options="width:400,align:'left',hidden:false,field:'jobClassName'">任务类</th>
            <th data-options="width:80,align:'left',hidden:false,field:'isDurable'">存储</th>
            <th data-options="width:80,align:'left',hidden:false,field:'isNonconcurrent'">不支持并发</th>
            <th data-options="width:80,align:'left',hidden:false,field:'isUpdateData'">更新JobData</th>
            <th data-options="width:80,align:'left',hidden:false,field:'requestsRecovery'">支持故障恢复</th>
            <th data-options="width:100,align:'left',hidden:false,field:'jobData',formatter:pageJsObject.jobDataFormatter">JobData</th>
            <th data-options="width:500,align:'left',hidden:false,field:'description'">任务描述</th>
        </tr>
        </thead>
    </table>
    <div id="jobDetailDataTableButtons">
        <a id="jobDetailDataTableButtonsReload" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" href="javascript:void(0)">
            <%--刷新--%>
        </a>
        <a id="jobDetailDataTableButtonsPauseAll" class="easyui-linkbutton" data-options="iconCls:'icon-pause-all',plain:true" href="javascript:void(0)">
            <%--暂停所有--%>
        </a>
        <a id="jobDetailDataTableButtonsResumeAll" class="easyui-linkbutton" data-options="iconCls:'icon-resume-all',plain:true" href="javascript:void(0)">
            <%--继续所有--%>
        </a>
        <a id="jobDetailDataTableButtonsPause" class="easyui-linkbutton" style="margin-left: 20px;" data-options="iconCls:'icon-pause-job',plain:true" href="javascript:void(0)">
            <%--暂停--%>
        </a>
        <a id="jobDetailDataTableButtonsResume" class="easyui-linkbutton" data-options="iconCls:'icon-resume-job',plain:true" href="javascript:void(0)">
            <%--继续--%>
        </a>
        <a id="jobDetailDataTableButtonsExecuting" class="easyui-linkbutton" data-options="iconCls:'icon-executing-job',plain:true" href="javascript:void(0)">
            <%--立即执行--%>
        </a>
        <a id="jobDetailDataTableButtonsAdd" class="easyui-linkbutton" style="margin-left: 20px;" data-options="iconCls:'icon-trigger-add',plain:true" href="javascript:void(0)">
            <%--新增--%>
        </a>
        <a id="jobDetailDataTableButtonsDelete" class="easyui-linkbutton" data-options="iconCls:'icon-trigger-delete',plain:true" href="javascript:void(0)">
            <%--删除--%>
        </a>
    </div>
</div>

<%-- 页面下部 --%>
<div data-options="region:'south',border:true,split:true,height:'45%',collapsible:false,iconCls:'icon-triggers',title:'选中定时任务的触发器'" <%--style="height:260px;"--%>>
    <table id="triggerDataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:200,align:'left',hidden:true,field:'schedName'">调度器名称</th>
            <th data-options="width:100,align:'left',hidden:false,field:'triggerGroup'">触发器分组</th>
            <th data-options="width:100,align:'left',hidden:false,field:'triggerName'">触发器名称</th>
            <th data-options="width:100,align:'left',hidden:true,field:'jobGroup'">任务分组</th>
            <th data-options="width:100,align:'left',hidden:true,field:'jobName'">任务名称</th>
            <th data-options="width:70,align:'left',hidden:false,field:'triggerState',formatter:pageJsObject.triggerStateFormatter">状态</th>
            <th data-options="width:280,align:'left',hidden:false,field:'triggerType'">触发器类</th>
            <th data-options="width:45,align:'left',hidden:false,field:'priority'">优先级</th>
            <th data-options="width:120,align:'left',hidden:false,field:'prevFireTime'">上次触发时间</th>
            <th data-options="width:120,align:'left',hidden:false,field:'nextFireTime'">下次触发时间</th>
            <th data-options="width:120,align:'left',hidden:false,field:'startTime'">开始触发时间</th>
            <th data-options="width:120,align:'left',hidden:false,field:'endTime'">结束触发时间</th>
            <th data-options="width:120,align:'left',hidden:false,field:'misfireInstr'">错过触发规则ID</th>
            <th data-options="width:80,align:'left',hidden:false,field:'calendarName'">使用日历</th>
            <th data-options="width:90,align:'left',hidden:false,field:'timeZoneId'">使用时区</th>
            <th data-options="width:80,align:'left',hidden:true,field:'repeatCount'">需要触发次数</th>
            <th data-options="width:80,align:'left',hidden:true,field:'repeatInterval'">重复触发时间间隔</th>
            <th data-options="width:80,align:'left',hidden:true,field:'timesTriggered'">已经触发的次数</th>
            <th data-options="width:80,align:'left',hidden:true,field:'cronEx'">cron表达式</th>
            <th data-options="width:130,align:'left',hidden:false,field:'rule',formatter:pageJsObject.ruleFormatter">触发器规则</th>
            <th data-options="width:60,align:'left',hidden:false,field:'jobData',formatter:pageJsObject.jobDataFormatter">JobData</th>
            <th data-options="width:200,align:'left',hidden:false,field:'description'">任务描述</th>
        </tr>
        </thead>
    </table>
    <div id="triggerDataTableButtons">
        <a id="triggerDataTableButtonsReload" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" href="javascript:void(0)">
            <%--刷新--%>
        </a>
        <a id="triggerDataTableButtonsPause" class="easyui-linkbutton" data-options="iconCls:'icon-pause-trigger',plain:true" href="javascript:void(0)">
            <%--暂停--%>
        </a>
        <a id="triggerDataTableButtonsResume" class="easyui-linkbutton" data-options="iconCls:'icon-resume-trigger',plain:true" href="javascript:void(0)">
            <%--继续--%>
        </a>
        <a id="triggerDataTableButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-trigger-add',plain:true" href="javascript:void(0)">
            <%--新增--%>
        </a>
        <a id="triggerDataTableButtonsDelete" class="easyui-linkbutton" data-options="iconCls:'icon-trigger-delete',plain:true" href="javascript:void(0)">
            <%--删除--%>
        </a>
        <a id="triggerDataTableButtonsDeleteAll" class="easyui-linkbutton" data-options="iconCls:'icon-trigger-delete-all',plain:true" href="javascript:void(0)">
            <%--删除所有--%>
        </a>
        <span style="margin-left: 50px;margin-top: 5px;">
            <label style="font-weight: bold;">选中定时任务:</label>
            <label id="selectJobDetailText" style="color: red;"></label>
        </span>

    </div>
</div>

<%-- 查看对话框 --%>
<div id="jsonViewDialog" data-options="iconCls:'icon-job-data'" style="width: 850px;height: 330px;padding: 5px 10px">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <textarea id="jsonViewEdit"></textarea>
</div>

<%-- SimpleTriggerImpl 触发器 处罚规则对话框 --%>
<div id="simpleTriggerViewDialog" data-options="iconCls:'icon-clock'" style="width: 430px;height: 180px;padding: 5px 10px">
    <table id="simpleTriggerViewTable">
        <tbody>
        <tr class="row">
            <td class="label">已经触发的次数:</td>
            <td class="value">
                <label id="simpleTriggerTimesTriggered"></label>
            </td>
        </tr>
        <tr class="row">
            <td class="label">需要触发次数:</td>
            <td class="value">
                <label id="simpleTriggerRepeatCount"></label>
            </td>
        </tr>
        <tr class="row">
            <td class="label">重复触发时间间隔:</td>
            <td class="value">
                <label id="simpleTriggerRepeatInterval"></label>
            </td>
        </tr>
        <tr class="row">
            <td class="label">预计下次触发时间:</td>
            <td class="value">
                <label id="simpleTriggerNextFireTime"></label>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<%-- CronTriggerImpl 触发器 处罚规则对话框 --%>
<div id="cronTriggerViewDialog" data-options="iconCls:'icon-clock'" style="width: 410px;height: 320px;padding: 5px 10px">
    <table id="cronTriggerViewTable">
        <tbody>
        <tr class="row">
            <td class="label">Cron表达式:</td>
            <td class="value">
                <label id="cronTriggerCronEx"></label>
            </td>
        </tr>
        <tr class="row">
            <td class="label">预计触发时间:</td>
            <td class="value">
                <label id="cronTriggerNextTime"></label>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<%--新增定时任务对话框--%>
<div id="addJobDetailDialog" data-options="iconCls:'icon-jobs'" style="width: 580px;height: 350px;">
    <div id="addJobDetailTabs" class="easyui-tabs" data-options="fit:true,border:false">
        <div title="定时任务属性">
            <form id="addJobDetailForm" method="post" style="margin-top: 12px;">
                <div class="row">
                    <label for="addJobDetailJobGroup">任务分组</label>
                    <input id="addJobDetailJobGroup" name="jobGroup" style="width: 220px"/>
                </div>
                <div class="row">
                    <label for="addJobDetailJobName">任务名称</label>
                    <input id="addJobDetailJobName" name="jobName" style="width: 220px"/>
                </div>
                <div class="row">
                    <label for="addJobDetailRequestsRecovery">支持故障恢复</label>
                    <input id="addJobDetailRequestsRecovery" name="requestsRecovery" style="width: 100px;"/>
                </div>
                <div class="row">
                    <label for="addJobDetailJobClassName">任务实现类</label>
                    <input id="addJobDetailJobClassName" name="jobClassName" style="width: 450px;"/>
                </div>
                <div class="row">
                    <label for="addJobDetailDescription">任务描述</label>
                    <input id="addJobDetailDescription" name="description" style="width: 450px; height: 100px;"/>
                </div>
            </form>
        </div>
        <div title="定时任务数据">
            <table id="addJobDetailJobDataTable" data-options="border:false">
                <thead>
                <tr>
                    <th data-options="width:150,align:'left',hidden:false,field:'key',editor:{type:'textbox',options:{required: true}}">属性名称</th>
                    <th data-options="width:350,align:'left',hidden:false,field:'value',editor:{type:'textbox',options:{required: true}}">属性值</th>
                </tr>
                </thead>
            </table>
            <div id="addJobDetailJobDataTableButtons">
                <a id="addJobDetailJobDataTableButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" href="javascript:void(0)"></a>
                <a id="addJobDetailJobDataTableButtonsRemove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" href="javascript:void(0)"></a>
                <a id="addJobDetailJobDataTableButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" href="javascript:void(0)"></a>
            </div>
        </div>
    </div>
</div>
<div id="addJobDetailDialogButtons">
    <a id="addJobDetailDialogSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)">新增</a>
    <a id="addJobDetailDialogCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)">取消</a>
</div>

<%--新增触发器对话框--%>
<div id="addTriggerDialog" data-options="iconCls:'icon-triggers'" style="width: 650px;height: 440px;">
    <div id="addTriggerTabs" class="easyui-tabs" data-options="fit:true,border:false">
        <div title="触发器属性">
            <form id="addTriggerForm" method="post" style="margin-top: 12px;">
                <div class="row">
                    <label for="addTriggerJobGroup">任务分组</label>
                    <input id="addTriggerJobGroup" name="jobGroup" style="width: 200px"/>

                    <label for="addTriggerJobName">任务名称</label>
                    <input id="addTriggerJobName" name="jobName" style="width: 200px"/>
                </div>
                <div class="row">
                    <label for="addTriggerTriggerGroup">触发器分组</label>
                    <input id="addTriggerTriggerGroup" name="triggerGroup" style="width: 200px"/>

                    <label for="addTriggerTriggerName">触发器名称</label>
                    <input id="addTriggerTriggerName" name="triggerName" style="width: 200px"/>
                </div>
                <div class="row">
                    <label for="addTriggerStartTime">开始触发时间</label>
                    <input id="addTriggerStartTime" name="startTime" style="width: 200px"/>

                    <label for="addTriggerEndTime">结束触发时间</label>
                    <input id="addTriggerEndTime" name="endTime" style="width: 200px"/>
                </div>
                <div class="row">
                    <label for="addTriggerPriority">优先级</label>
                    <input id="addTriggerPriority" name="priority" style="width: 200px"/>

                    <label for="addTriggerMisfireInstruction">错过触发规则ID</label>
                    <input id="addTriggerMisfireInstruction" name="misfireInstruction" style="width: 200px"/>
                </div>
                <div class="row">
                    <label for="addTriggerTriggerType">触发器类型</label>
                    <input id="addTriggerTriggerType" name="triggerType" style="width: 200px"/>
                </div>
                <%-- SimpleTrigger触发器规则 --%>
                <div id="simpleTriggerRule" style="display: none">
                    <div class="row">
                        <label for="addTriggerRepeatCount">触发的次数</label>
                        <input id="addTriggerRepeatCount" name="repeatCount" style="width: 220px"/>
                    </div>
                    <%--<input id="addTriggerInterval" name="interval" type="hidden"/>--%>
                    <div class="row">
                        <label>触发的时间间隔</label>
                        <%--suppress HtmlFormInputWithoutLabel --%>
                        <input id="addTriggerIntervalYear" name="addTriggerIntervalYear" style="width: 50px;"/>年
                        <%--suppress HtmlFormInputWithoutLabel --%>
                        <input id="addTriggerIntervalMonth" name="addTriggerIntervalMonth" style="width: 50px;"/>月
                        <%--suppress HtmlFormInputWithoutLabel --%>
                        <input id="addTriggerIntervalDay" name="addTriggerIntervalDay" style="width: 50px;"/>天
                        <%--suppress HtmlFormInputWithoutLabel --%>
                        <input id="addTriggerIntervalHour" name="addTriggerIntervalHour" style="width: 50px;"/>小时
                        <%--suppress HtmlFormInputWithoutLabel --%>
                        <input id="addTriggerIntervalMinute" name="addTriggerIntervalMinute" style="width: 50px;"/>分钟
                        <%--suppress HtmlFormInputWithoutLabel --%>
                        <input id="addTriggerIntervalSecond" name="addTriggerIntervalSecond" style="width: 50px;"/>秒
                        <a id="simpleTriggerRuleView" href="javascript:void(0)">详情</a>
                    </div>
                </div>
                <%-- CronTrigger触发器规则 --%>
                <div id="cronTriggerRule" style="display: none">
                    <div class="row">
                        <label for="addTriggerCron">Cron表达式</label>
                        <input id="addTriggerCron" name="cron" style="width: 400px"/>
                        <a id="cronTriggerRuleView" href="javascript:void(0)">详情</a>
                    </div>
                </div>
                <div class="row">
                    <label for="addTriggerDescription">触发器描述</label>
                    <input id="addTriggerDescription" name="description" style="width: 510px; height: 100px;"/>
                </div>
            </form>
        </div>
        <div title="触发器数据">
            <table id="addTriggerDataTable" data-options="border:false">
                <thead>
                <tr>
                    <th data-options="width:150,align:'left',hidden:false,field:'key',editor:{type:'textbox',options:{required: true}}">属性名称</th>
                    <th data-options="width:350,align:'left',hidden:false,field:'value',editor:{type:'textbox',options:{required: true}}">属性值</th>
                </tr>
                </thead>
            </table>
            <div id="addTriggerDataTableButtons">
                <a id="addTriggerDataTableButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" href="javascript:void(0)"></a>
                <a id="addTriggerDataTableButtonsRemove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" href="javascript:void(0)"></a>
                <a id="addTriggerDataTableButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" href="javascript:void(0)"></a>
            </div>
        </div>
    </div>
</div>
<div id="addTriggerDialogButtons">
    <a id="addTriggerDialogSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)">新增</a>
    <a id="addTriggerDialogCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)">取消</a>
</div>
</body>
</html>
