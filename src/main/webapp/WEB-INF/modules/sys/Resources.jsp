<%--
  作者: LiZW
  时间: 2016-8-17 13:54
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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/sys/Resources.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/sys/Resources.js"></script>
    <%-- 页面标题 --%>
    <title>系统资源管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:80px;">
    <form id="searchForm" method="post">
        <div class="row">
            <span class="column">
                <label for="searchTitle">资源标题</label>
                <input id="searchTitle" name="title" class="easyui-textbox">
            </span>
            <span class="columnLast">
                <label for="searchResourcesUrl">资源URL</label>
                <input id="searchResourcesUrl" name="resourcesUrl" class="easyui-textbox" style="width: 435px">
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="searchPermission">权限标识</label>
                <input id="searchPermission" name="permission" class="easyui-textbox">
            </span>
            <span class="column">
                <label for="searchResourcesType">资源类型</label>
                <input id="searchResourcesType" name="resourcesType" class="easyui-combobox">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:false">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:100,align:'left',hidden:true  ,field:'id'">编号</th>
            <th data-options="width:230,align:'left',hidden:false ,field:'title'">资源标题</th>
            <th data-options="width:230,align:'left',hidden:false ,field:'needAuthorization'">需要授权</th>
            <th data-options="width:650,align:'left',hidden:false ,field:'resourcesUrl'">资源URL</th>
            <th data-options="width:200,align:'left',hidden:false ,field:'permission'">权限标识</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'resourcesType',formatter:pageJsObject.resourcesTypeFormatter">资源类型</th>
            <th data-options="width:200,align:'left',hidden:false ,field:'description'">资源说明</th>
        </tr>
        </thead>
    </table>
    <div id="dataTableButtons">
        <a id="dataTableButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
        <a id="dataTableButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
        <a id="dataTableButtonsEdit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
        <a id="dataTableButtonsDel" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </div>
</div>

<%-- 新增对话框 --%>
<div id="addDialog" style="width: 830px;height: 330px;padding: 5px 10px">
    <form id="addForm" method="post">
        <div class="row">

            <span class="column">
                <label for="addTitle">资源标题</label>
                <input id="addTitle" name="title" style="width: 435px"/>
            </span>
            <span class="columnLast">
                <label for="addTitle">需要授权</label>
                <input id="addNeedAuthorization" name="needAuthorization"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="addResourcesUrl">资源URL</label>
                <input id="addResourcesUrl" name="resourcesUrl" style="width: 700px"/>
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="addPermission">权限标识</label>
                <input id="addPermission" name="permission" style="width: 435px;"/>
            </span>
            <span class="columnLast">
                <label for="addResourcesType">资源类型</label>
                <input id="addResourcesType" name="resourcesType"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="addDescription">资源说明</label>
                <input id="addDescription" name="description" style="width: 700px;height: 140px;"/>
            </span>
        </div>
    </form>
</div>
<div id="addDialogButtons">
    <a id="addDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="addDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

<%-- 编辑对话框 --%>
<div id="editDialog" style="width: 830px;height: 330px;padding: 5px 10px">
    <form id="editForm" method="post">
        <input id="editId" name="id" type="hidden"/>
        <div class="row">
            <span class="columnLast">
                <label for="editTitle">资源标题</label>
                <input id="editTitle" name="title" style="width: 435px"/>
            </span>
            <span class="columnLast">
                <label for="addTitle">需要授权</label>
                <input id="editNeedAuthorization" name="needAuthorization"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="editResourcesUrl">资源URL</label>
                <input id="editResourcesUrl" name="resourcesUrl" style="width: 700px"/>
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="editPermission">权限标识</label>
                <input id="editPermission" name="permission" style="width: 435px;"/>
            </span>
            <span class="columnLast">
                <label for="editResourcesType">资源类型</label>
                <input id="editResourcesType" name="resourcesType"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="editDescription">资源说明</label>
                <input id="editDescription" name="description" style="width: 700px;height: 140px;"/>
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
