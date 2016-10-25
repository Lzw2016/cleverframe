<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/10/23
  Time: 11:20
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

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/sys/DependenceResources.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/sys/DependenceResources.js"></script>
    <%-- 页面标题 --%>
    <title>系统资源关系管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">

<%-- 页面上部 --%>
<div data-options="region:'north',border:true,split:false,collapsible:true,maxHeight:105,minHeight:105">
    <form id="searchForm" method="post">
        <%--资源类型--%>
        <input id="searchResourcesType" name="resourcesType" type="hidden" value="1">
        <div class="row">
            <span class="columnLast">
                <label for="searchTitle">资源标题</label>
                <input id="searchTitle" name="title" class="easyui-textbox" style="width: 530px">
            </span>
        </div>
        <div class="row">
            <span class="columnLast">
                <label for="searchResourcesUrl">资源URL</label>
                <input id="searchResourcesUrl" name="resourcesUrl" class="easyui-textbox" style="width: 530px">
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="searchPermission">权限标识</label>
                <input id="searchPermission" name="permission" class="easyui-textbox" style="width: 530px">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div data-options="region:'center',border:true,title:'系统页面资源'" style="height: 65%;">
    <table id="dataTable_1" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:100,align:'left',hidden:true  ,field:'id'">编号</th>
            <th data-options="width:230,align:'left',hidden:false ,field:'title'">资源标题</th>
            <th data-options="width:650,align:'left',hidden:false ,field:'resourcesUrl'">资源URL</th>
            <th data-options="width:200,align:'left',hidden:false ,field:'permission'">权限标识</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'resourcesType',formatter:pageJsObject.resourcesTypeFormatter">资源类型</th>
            <th data-options="width:200,align:'left',hidden:false ,field:'description'">资源说明</th>
        </tr>
        </thead>
        <div id="dataTableButtons_1">
            <a id="dataTableButtonsSearch_1" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
        </div>
    </table>
</div>

<%-- 页面下部 --%>
<div data-options="region:'south',border:true,title:'依赖资源',split:true,collapsible:true,minHeight:210" style="height:35%;">
    <table id="dataTable_2" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:100,align:'left',hidden:true  ,field:'id'">编号</th>
            <th data-options="width:230,align:'left',hidden:false ,field:'title'">资源标题</th>
            <th data-options="width:650,align:'left',hidden:false ,field:'resourcesUrl'">资源URL</th>
            <th data-options="width:200,align:'left',hidden:false ,field:'permission'">权限标识</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'resourcesType',formatter:pageJsObject.resourcesTypeFormatter">资源类型</th>
            <th data-options="width:200,align:'left',hidden:false ,field:'description'">资源说明</th>
        </tr>
        </thead>
        <div id="dataTableButtons_2">
            <a id="dataTableButtonsReload_2" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
            <a id="dataTableButtonsAdd_2" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增依赖</a>
            <a id="dataTableButtonsDel_2" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除依赖</a>
            <span style="margin-left: 50px;margin-top: 5px;">
                <label style="font-weight: bold;">页面资源:</label>
                <label id="selectPageResourcesText" style="color: red;"></label>
            </span>
        </div>
    </table>
</div>

<%-- 新增对话框 --%>
<div id="addDialog" style="width: 630px;height: 180px;padding: 5px 10px">
    <form id="addForm" method="post">
        <input type="hidden" id="addResourcesId" name="resourcesId"/>
        <div class="row">
            <span class="columnLast">
                <label for="addTitle">资源标题</label>
                <input id="addTitle" name="title" style="width: 500px"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="addResourcesUrl">资源URL</label>
                <input id="addResourcesUrl" name="resourcesUrl" style="width: 500px"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="addDependenceResourcesId">依赖资源</label>
                <select id="addDependenceResourcesId" name="dependenceResourcesId" style="width: 500px"></select>
            </span>
        </div>
    </form>
</div>
<div id="addDialogButtons">
    <a id="addDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="addDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

<%--选择依赖资源对话框--%>
<div id="selectDependenceResourcesDialog" style="width: 800px;height: 450px;">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'north',border:true,split:false,collapsible:true,maxHeight:105,minHeight:105">

        </div>
        <div data-options="region:'center',border:true">
            <table id="dataTable_3" data-options="border:false">
                <thead>
                <tr>
                    <th data-options="width:100,align:'left',hidden:true  ,field:'id'">编号</th>
                    <th data-options="width:230,align:'left',hidden:false ,field:'title'">资源标题</th>
                    <th data-options="width:650,align:'left',hidden:false ,field:'resourcesUrl'">资源URL</th>
                    <th data-options="width:200,align:'left',hidden:false ,field:'permission'">权限标识</th>
                    <th data-options="width:100,align:'left',hidden:false ,field:'resourcesType',formatter:pageJsObject.resourcesTypeFormatter">资源类型</th>
                    <th data-options="width:200,align:'left',hidden:false ,field:'description'">资源说明</th>
                </tr>
                </thead>
                <div id="dataTableButtons_3">
                    <a id="dataTableButtonsReload_3" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">查询</a>
                </div>
            </table>
        </div>
    </div>
</div>
<div id="selectDependenceResourcesDialogButtons">
    <a id="selectDependenceResourcesDialogButtonsOK" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a>
    <a id="selectDependenceResourcesDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
</div>

</body>
</html>
