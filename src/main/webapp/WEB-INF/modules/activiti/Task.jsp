<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/12/4
  Time: 22:49
  To change this template use File | Settings | File Templates.
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
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/clike/clike.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/xml/xml.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/htmlembedded/htmlembedded.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/css/css.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/sql/sql.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/javascript/javascript.js"></script>

    <%--代码格式化JS--%>
    <script src="${applicationScope.staticPath}/CodeFormat/jsbeautify.js"></script>

    <%--FancyBox--%>
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/lib/jquery.mousewheel-3.0.6.pack.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/jquery.fancybox.pack.js?v=2.1.5"></script>
    <link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/jquery.fancybox.css?v=2.1.5" media="screen"/>

    <%--Moment--%>
    <script src="${applicationScope.staticPath}/Moment/moment-2.17.1/moment.min.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/activiti/Task.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/activiti/Task.js"></script>
    <%-- 页面标题 --%>
    <title>流程任务</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:75px;">
    <form id="searchForm">
        <%--id（默认），processDefinitionId，tenantId 或 processDefinitionKey--%>
        <input type="hidden" name="sort" value="id">
        <input type="hidden" name="order" value="asc">
        <%--表示结果中包含流程变量--%>
        <input type="hidden" name="includeProcessVariables" value="true">
        <div class="row">
            <span class="column">
                <label for="searchProcessDefinitionKey">流程定义Key</label>
                <input id="searchProcessDefinitionKey" name="processDefinitionKey">
            </span>
            <span class="column">
                <label for="searchProcessDefinitionId">流程定义ID</label>
                <input id="searchProcessDefinitionId" name="processDefinitionId">
            </span>
            <span class="columnLast">
                <label for="searchBusinessKey">业务主键</label>
                <input id="searchBusinessKey" name="businessKey">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:true">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:60 ,align:'left',hidden:false,field:'id'">任务ID</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'url',formatter:pageJsObject.urlFormatter">任务url</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'diagram',formatter:pageJsObject.diagramFormatter">流程实例图</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'variables',formatter:pageJsObject.variablesFormatter">任务变量</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'formKey'">任务表单Key</th>
            <th data-options="width:150,align:'left',hidden:false,field:'name'">任务名称</th>
            <th data-options="width:150,align:'left',hidden:false,field:'taskDefinitionKey'">任务Key</th>
            <th data-options="width:150,align:'left',hidden:false,field:'description'">任务说明</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'owner'">委托人</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'assignee'">处理人</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'delegationState'">委托状态</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'tenantId'">租户ID</th>
            <th data-options="width:130,align:'left',hidden:false,field:'createTime',formatter:pageJsObject.timeFormatter">创建时间</th>
            <th data-options="width:130,align:'left',hidden:false,field:'dueDate',formatter:pageJsObject.timeFormatter">到期时间</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'priority'">优先级</th>
            <th data-options="width:50 ,align:'left',hidden:false,field:'suspended'">挂起</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'category'">类别</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'parentTaskId'">父任务ID</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'parentTaskUrl',formatter:pageJsObject.urlFormatter">父任务Url</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'executionId'">分支ID</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'executionUrl',formatter:pageJsObject.urlFormatter">分支Url</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'processInstanceId'">流程实例ID</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'processInstanceUrl',formatter:pageJsObject.urlFormatter">流程实例Url</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'processDefinitionId'">流程定义ID</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'processDefinitionUrl',formatter:pageJsObject.urlFormatter">流程定义Url</th>
        </tr>
        </thead>
    </table>
    <div id="dataTableButtons">
        <a id="dataTableButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
    </div>
</div>


<%-- 查看流程数据对话框(文本) --%>
<div id="viewCodeTemplateDialog" style="width: 700px;height: 450px;">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <textarea id="viewCodeTemplateEdit"></textarea>
</div>
</body>
</html>
