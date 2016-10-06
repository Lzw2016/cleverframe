<%--
  作者: LiZW
  时间: 2016-8-27 0:53
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

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/monitor/Log4jMonitor.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/monitor/Log4jMonitor.js"></script>
    <%-- 页面标题 --%>
    <title>Log4j日志管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">

<div data-options="region:'north',border:false" style="height:50px;">
    <form id="searchForm" method="post">
        <div class="row">
            <span class="columnLast">
                <label for="searchLoggerName">Logger Name</label>
                <input id="searchLoggerName" name="loggerName" style="width: 560px">
            </span>
        </div>
    </form>
</div>

<div data-options="region:'center',border:true">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:850 ,align:'left',hidden:false ,field:'name'">Logger Name</th>
            <th data-options="width:100 ,align:'left',hidden:false ,field:'level'">level</th>
            <th data-options="width:100 ,align:'left',hidden:false ,field:'effectiveLevel'">effectiveLevel</th>
            <th data-options="width:100 ,align:'left',hidden:false ,field:'additivity'">additivity</th>
            <th data-options="width:100 ,align:'left',hidden:false ,field:'traceEnabled'">traceEnabled</th>
            <th data-options="width:100 ,align:'left',hidden:false ,field:'debugEnabled'">debugEnabled</th>
            <th data-options="width:100 ,align:'left',hidden:false ,field:'infoEnabled'">infoEnabled</th>
        </tr>
        </thead>
        <div id="dataTableButtons">
            <a id="dataTableSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
            <a id="dataTableEdit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a>
        </div>
    </table>
</div>

<%-- 编辑对话框 --%>
<div id="editDialog" style="width: 750px;height: 165px;padding: 5px 10px">
    <form id="editForm" method="post">
        <div class="row">
            <span class="columnLast">
                <label for="editLoggerName">Logger Name</label>
                <input id="editLoggerName" name="loggerName"/>
            </span>
        </div>
        <div class="row">
            <span class="columnLast">
                <label for="editLevel">Level</label>
                <input id="editLevel" name="level"/>
            </span>
        </div>
    </form>
</div>
<div id="editDialogButtons">
    <a id="editDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
    <a id="editDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>
</body>
</html>
