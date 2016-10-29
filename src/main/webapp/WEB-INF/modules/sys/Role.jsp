<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/10/29
  Time: 12:25
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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/sys/Role.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/sys/Role.js"></script>
    <%-- 页面标题 --%>
    <title>角色管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:40px;">
    <form id="searchForm" method="post">
        <div class="row">
            <span class="columnLast">
                <label for="searchName">角色名称</label>
                <input id="searchName" name="name" class="easyui-textbox" style="width: 300px;">
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
            <th data-options="width:100,align:'left',hidden:true  ,field:'companyCode'">数据所属公司的机构编码</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'orgCode'">数据直属机构的编码</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'createBy'">创建者</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'createDate'">创建时间</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'updateBy'">更新者</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'updateDate'">更新时间</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'uuid'">数据全局标识UUID</th>
            <th data-options="width:150,align:'left',hidden:false ,field:'name'">角色名称</th>
            <th data-options="width:550,align:'left',hidden:false ,field:'description'">角色说明</th>
            <th data-options="width:150,align:'left',hidden:false ,field:'remarks'">备注信息</th>
            <th data-options="width:80 ,align:'left',hidden:false ,field:'delFlag',formatter:pageJsObject.delFlagFormatter">删除标记</th>
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
<div id="addDialog" style="width: 630px;height: 300px;padding: 5px 10px">
    <form id="addForm" method="post">
        <div class="row">
            <span class="columnLast">
                <label for="addName">角色名称</label>
                <input id="addName" name="name" style="width: 500px"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="addDescription">角色说明</label>
                <input id="addDescription" name="description" style="width: 500px;height: 100px;"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="addRemarks">备注信息</label>
                <input id="addRemarks" name="remarks" style="width: 500px;height: 60px;"/>
            </span>
        </div>
    </form>
</div>
<div id="addDialogButtons">
    <a id="addDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="addDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

<%-- 编辑对话框 --%>
<div id="editDialog" style="width: 630px;height: 300px;padding: 5px 10px">
    <form id="editForm" method="post">
        <input id="editId" name="id" type="hidden"/>
        <div class="row">
            <span class="columnLast">
                <label for="editName">角色名称</label>
                <input id="editName" name="name" style="width: 500px"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="editDescription">角色说明</label>
                <input id="editDescription" name="description" style="width: 500px;height: 100px;"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="editRemarks">备注信息</label>
                <input id="editRemarks" name="remarks" style="width: 500px;height: 60px;"/>
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
