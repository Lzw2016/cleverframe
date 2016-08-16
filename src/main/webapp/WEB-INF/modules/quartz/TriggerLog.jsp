<%--
  作者: LiZW
  时间: 2016-8-9 18:08
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
    <%--<link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/theme/cobalt.css">--%>
    <link rel=stylesheet href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/addon/merge/merge.css">
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/javascript/javascript.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/diff_match_patch_20121119/javascript/diff_match_patch.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/addon/merge/merge.js"></script>

    <%--代码格式化JS--%>
    <script src="${applicationScope.staticPath}/CodeFormat/jsbeautify.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>

    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/quartz/TriggerLog.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/quartz/TriggerLog.js"></script>
    <title>触发器(Trigger)日志</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:195px;">
    <form id="searchForm" method="post">
        <div class="row">
            <span class="column">
                <label for="searchSchedulerName">调度器名称</label>
                <input id="searchSchedulerName" name="schedulerName">
            </span>
            <span class="columnLast">
                <label for="searchInstanceName">调度器ID</label>
                <input id="searchInstanceName" name="instanceName">
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="searchTriggerGroup">触发器分组</label>
                <input id="searchTriggerGroup" name="triggerGroup">
            </span>
            <span class="columnLast">
                <label for="searchTriggerName">触发器名称</label>
                <input id="searchTriggerName" name="triggerName">
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="searchJobGroup">任务分组</label>
                <input id="searchJobGroup" name="jobGroup">
            </span>
            <span class="columnLast">
                <label for="searchJobName">任务名称</label>
                <input id="searchJobName" name="jobName">
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="searchJobClassName">任务实现类</label>
                <input id="searchJobClassName" name="jobClassName" style="width: 515px;">
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="searchStartTimeByStart">触发时间-起始值</label>
                <input id="searchStartTimeByStart" name="startTimeByStart">
            </span>
            <span class="columnLast">
                <label for="searchStartTimeByEnd">触发时间-结束值</label>
                <input id="searchStartTimeByEnd" name="startTimeByEnd">
            </span>
        </div>

        <div class="row" style="margin-bottom: 0px;">
            <span class="column">
                <label for="searchProcessTimeByMin">处理时间-最小值</label>
                <input id="searchProcessTimeByMin" name="processTimeByMin">
            </span>
            <span class="columnLast">
                <label for="searchProcessTimeByMax">处理时间-最大值</label>
                <input id="searchProcessTimeByMax" name="processTimeByMax">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:false">
    <table id="triggerLogDataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:100,align:'left',hidden:true ,field:'id'">编号</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'listenerName'">监听器名称</th>
            <th data-options="width:150,align:'left',hidden:false ,field:'schedName'">调度器名称</th>
            <th data-options="width:150,align:'left',hidden:false ,field:'instanceName'">调度器名称ID</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'triggerGroup'">触发器分组</th>
            <th data-options="width:130,align:'left',hidden:false ,field:'triggerName'">触发器名称</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'jobGroup'">任务分组</th>
            <th data-options="width:130,align:'left',hidden:false ,field:'jobName'">任务名称</th>
            <th data-options="width:130,align:'left',hidden:false ,field:'endTime'">触发完成时间</th>
            <th data-options="width:280,align:'left',hidden:false ,field:'jobClassName'">任务实现类</th>
            <th data-options="width:130,align:'left',hidden:false ,field:'startTime'">开始触发时间</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'processTime',formatter:pageJsObject.processTimeFormatter">用时(ms)</th>
            <th data-options="width:70 ,align:'left',hidden:false ,field:'misFired',formatter:pageJsObject.misFiredFormatter">错过了触发</th>
            <th data-options="width:130,align:'left',hidden:false ,field:'preRunTime'">上一次触发时间</th>
            <th data-options="width:130,align:'left',hidden:false ,field:'nextRunTime'">下一次触发时间</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'runCount'">触发次数</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'ipAddress'">触发节点IP</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'beforeJobData'">执行前的JobDataMap数据</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'afterJobData'">执行后的JobDataMap数据</th>
            <th data-options="width:50,align:'left',hidden:false,field:'jobData',formatter:pageJsObject.jobDataFormatter"">JobData</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'triggerInstructionCode'">触发指令码</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'instrCode'">触发指令码说明</th>
        </tr>
        </thead>
    </table>
    <div id="triggerLogDataTableButtons">
        <a id="triggerLogDataTableButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
    </div>
</div>

<%-- 查看对话框 --%>
<div id="jsonViewDialog" data-options="iconCls:'icon-job-data'" style="width: 850px;height: 330px;">
    <div id="jobDataMergeView"></div>
</div>
</body>
</html>
