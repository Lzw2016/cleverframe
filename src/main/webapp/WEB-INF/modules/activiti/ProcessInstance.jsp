<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/12/4
  Time: 18:51
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

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/activiti/ProcessInstance.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/activiti/ProcessInstance.js"></script>
    <%-- 页面标题 --%>
    <title>流程实例</title>
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
        <div class="row">
            <span class="column">
                <label for="searchInvolvedUser">参与流程用户</label>
                <input id="searchInvolvedUser" name="involvedUser">
            </span>
            <span class="column">
                <label for="searchTenantIdLike">流程变量</label>
                <input id="searchTenantIdLike" name="tenantIdLike">
            </span>
            <span class="columnLast">
                <label for="searchSuspended">是否挂起</label>
                <input id="searchSuspended" name="suspended">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:true">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:150,align:'left',hidden:false,field:'id'">流程ID</th>
            <th data-options="width:150,align:'left',hidden:false,field:'name'">名称</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'url',formatter:pageJsObject.urlFormatter">流程实例Url</th>
            <th data-options="width:150,align:'left',hidden:false,field:'activityId'">Activity ID</th>
            <th data-options="width:150,align:'left',hidden:false,field:'businessKey'">业务主键</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'variables',formatter:pageJsObject.variablesFormatter">流程变量</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'suspended'">是否暂停</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'completed'">是否完成</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'ended'">流程是否结束</th>
            <th data-options="width:150,align:'left',hidden:false,field:'processDefinitionId'">流程定义ID</th>
            <th data-options="width:150,align:'left',hidden:false,field:'processDefinitionKey'">流程定义Key</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'processDefinitionUrl',formatter:pageJsObject.processDefinitionUrlFormatter">流程定义Url</th>
            <th data-options="width:150,align:'left',hidden:false,field:'tenantId'">租户ID</th>
        </tr>
        </thead>
    </table>
    <div id="dataTableButtons">
        <a id="dataTableButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
        <a id="dataTableButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
    </div>
</div>

<%-- 查看流程数据对话框(文本) --%>
<div id="viewCodeTemplateDialog" style="width: 700px;height: 450px;">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <textarea id="viewCodeTemplateEdit"></textarea>
</div>

</body>
</html>
