<%--
  作者: LiZW
  时间: 2016-8-9 18:07
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
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/clike/clike.js"></script>

    <%--代码格式化JS--%>
    <script src="${applicationScope.staticPath}/CodeFormat/jsbeautify.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>

    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/quartz/SchedulerLog.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/quartz/SchedulerLog.js"></script>
    <title>调度器(Scheduler)日志</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:105px;">
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
                <label for="searchLogTimeStart">时间-起始值</label>
                <input id="searchLogTimeStart" name="logTimeStart">
            </span>
            <span class="columnLast">
                <label for="searchLogTimeEnd">时间-结束值</label>
                <input id="searchLogTimeEnd" name="logTimeEnd">
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="searchMethodName">日志方法</label>
                <input id="searchMethodName" name="methodName">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:false">
    <table id="schedulerLogDataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:100,align:'left',hidden:true ,field:'id'">编号</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'listenerName'">监听器名称</th>
            <th data-options="width:160,align:'left',hidden:false ,field:'schedName'">调度器名称</th>
            <th data-options="width:160,align:'left',hidden:false ,field:'instanceName'">调度器名称ID</th>
            <th data-options="width:130,align:'left',hidden:false ,field:'logTime'">日志时间</th>
            <th data-options="width:260,align:'left',hidden:false ,field:'methodName'">日志方法</th>
            <th data-options="width:60,align:'left',hidden:false ,field:'logData',formatter:pageJsObject.logDataFormatter">日志数据</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'ipAddress'">节点IP</th>
        </tr>
        </thead>
    </table>
    <div id="schedulerLogDataTableButtons">
        <a id="schedulerLogDataTableButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
    </div>
</div>

<%-- 查看对话框 --%>
<div id="jsonViewDialog" data-options="iconCls:'icon-job-data'" style="width: 850px;height: 450px;">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <textarea id="logDataView"></textarea>
</div>
</body>
</html>
