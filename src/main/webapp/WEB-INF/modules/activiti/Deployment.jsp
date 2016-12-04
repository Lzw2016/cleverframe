<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/12/4
  Time: 14:19
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

    <%--Moment--%>
    <script src="${applicationScope.staticPath}/Moment/moment-2.17.1/moment.min.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/activiti/Deployment.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/activiti/Deployment.js"></script>
    <%-- 页面标题 --%>
    <title>部署流程管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:45px;">
    <form id="searchForm">
        <%--sort	否	'id'（默认），'name'，'deploytime'或'tenantId'	排序属性，与'order'一起使用。--%>
        <input type="hidden" name="sort" value="name">
        <input type="hidden" name="order" value="asc">
        <div class="row">
            <span class="column">
                <label for="searchNameLike">流程名称</label>
                <input id="searchNameLike" name="nameLike">
            </span>

            <span class="column">
                <label for="searchCategory">流程类别</label>
                <input id="searchCategory" name="category">
            </span>

            <span class="columnLast">
                <label for="searchTenantIdLike">流程租户ID</label>
                <input id="searchTenantIdLike" name="tenantIdLike">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:true" style="height: 65%;">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:80 ,align:'left',hidden:false,field:'id'">部署流程ID</th>
            <th data-options="width:200 ,align:'left',hidden:false,field:'name'">流程名称</th>
            <th data-options="width:180,align:'left',hidden:false,field:'category'">流程类别</th>
            <th data-options="width:130,align:'left',hidden:false,field:'deploymentTime',formatter:pageJsObject.timeFormatter">流程部署时间</th>
            <th data-options="width:180,align:'left',hidden:false,field:'tenantId'">流程租户ID</th>
            <th data-options="width:450,align:'left',hidden:false,field:'url'">流程地址</th>
        </tr>
        </thead>
    </table>
    <div id="dataTableButtons">
        <a id="dataTableButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
        <a id="dataTableButtonsDelete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </div>
</div>

<%-- 页面下部 --%>
<div data-options="region:'south',border:true,title:'',split:true,collapsible:false,minHeight:210" style="height:35%;">
    <table id="dataTable_2" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:810,align:'left',hidden:false,field:'id'">流程部署资源ID</th>
            <th data-options="width:180,align:'left',hidden:false,field:'type'">资源类型</th>
            <th data-options="width:150,align:'left',hidden:false,field:'mediaType'">资源MediaType</th>
            <th data-options="width:350,align:'left',hidden:true ,field:'url'">资源Url</th>
            <th data-options="width:450,align:'left',hidden:true ,field:'contentUrl'">资源ContentUrl</th>
            <th data-options="width:450,align:'left',hidden:false,field:'operate',formatter:pageJsObject.operateFormatter">操作</th>
        </tr>
        </thead>
    </table>
    <div id="dataTableButtons_2">
        <a id="dataTableButtonsSearch_2" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
        <span style="margin-left: 50px;margin-top: 5px;">
                <label style="font-weight: bold;">选择的流程:</label>
                <label id="selectDeployment" style="color: red;"></label>
        </span>
    </div>
</div>

<%-- 查看流程数据对话框(文本) --%>
<div id="viewCodeTemplateDialog" style="width: 700px;height: 450px;">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <textarea id="viewCodeTemplateEdit"></textarea>
</div>
</body>
</html>
