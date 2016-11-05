<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/11/2
  Time: 23:44
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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/sys/Menu.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/sys/Menu.js"></script>
    <%-- 页面标题 --%>
    <title>菜单管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">

<%--菜单类型--%>
<div data-options="region:'west',title:'菜单类别',split:true,collapsible:false,minWidth:150,maxWidth:500,border:true,tools:'#menuTypeTools'" style="width:300px;">
    <ul id="menuTypeList" data-options="fit:true,border:false"></ul>
</div>
<div id="menuTypeTools">
    <a id="menuTypeToolsAdd" class="icon-add" style="margin-right: 10px;"></a>
    <a id="menuTypeToolsReload" class="icon-reload"></a>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:false">
    <table id="treeGrid" data-options="border:true">
        <thead>
        <tr>
            <th data-options="width:100,align:'left',hidden:true ,field:'id'">编号</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'companyCode'">数据所属公司的机构编码</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'orgCode'">数据直属机构的编码</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'createBy'">创建者</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'createDate'">创建时间</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'updateBy'">更新者</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'updateDate'">更新时间</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'uuid'">数据全局标识UUID</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'parentId'">父级编号,根节点的父级编号是：-1</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'fullPath'">树结构的全路径用“-”隔开,包含自己的ID</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'menuType'">菜单类型</th>
            <th data-options="width:310,align:'left',hidden:false ,field:'name'">菜单名称</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'resourcesId'">资源ID,关联表:core_resources</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'icon'">图标</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'openMode'">菜单打开方式</th>
            <th data-options="width:210,align:'left',hidden:false ,field:'remarks'">备注信息</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'delFlag'">删除标记</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'sort'">排序</th>
        </tr>
        </thead>
    </table>
    <div id="treeGridButtons">
        <a id="treeGridButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
        <a id="treeGridButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
        <a id="treeGridButtonsEdit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
        <a id="treeGridButtonsDel" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
        <span style="margin-left: 50px;margin-top: 5px;">
                <label style="font-weight: bold;">选择菜单类别:</label>
                <label id="selectMenuTypeText" style="color: red;"></label>
            </span>
    </div>
</div>

<%-- 新增对话框 - 新增类型 --%>
<div id="addTypeDialog" style="width: 830px;height: 330px;padding: 5px 10px">
    <form id="addTypeForm" method="post">
        <div class="row">
            <span class="column">
                <label for="addTypeParentId">上级菜单</label>
                <input id="addTypeParentId" name="parentId"/>
            </span>
            <span class="column">
                <label for="addTypeMenuType">菜单类别</label>
                <input id="addTypeMenuType" name="menuType"/>
            </span>
            <span class="columnLast">
                <label for="addTypeName">菜单名称</label>
                <input id="addTypeName" name="name"/>
            </span>
        </div>
        <div class="row">
            <span class="column">
                <label for="addTypeIcon">菜单图标</label>
                <input id="addTypeIcon" name="icon"/>
            </span>
            <span class="column">
                <label for="addTypeOpenMode">打开方式</label>
                <input id="addTypeOpenMode" name="openMode"/>
            </span>
            <span class="columnLast">
                <label for="addTypeSort">排序</label>
                <input id="addTypeSort" name="sort"/>
            </span>
        </div>
        <div class="row">
            <span class="columnLast">
                <label for="addTypeResourcesId">菜单地址</label>
                <input id="addTypeResourcesId" name="resourcesId" style="width: 680px;"/>
            </span>
        </div>
        <div class="row">
            <span class="columnLast">
                <label for="addTypeRemarks">备注</label>
                <input id="addTypeRemarks" name="remarks" style="width: 680px;height: 140px;"/>
            </span>
        </div>
    </form>
</div>
<div id="addTypeDialogButtons">
    <a id="addTypeDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="addTypeDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

<%-- 新增对话框 --%>
<div id="addDialog" style="width: 830px;height: 330px;padding: 5px 10px">
    <form id="addForm" method="post">
        <div class="row">
            <span class="column">
               <label for="addMenuType">菜单类别</label>
                <input id="addMenuType" name="menuType"/>
            </span>
            <span class="column">
                 <label for="addName">菜单名称</label>
                <input id="addName" name="name"/>
            </span>
            <span class="columnLast">
                <label for="addParentId">上级菜单</label>
                <input id="addParentId" name="parentId"/>
            </span>
        </div>
        <div class="row">
            <span class="column">
                <label for="addIcon">菜单图标</label>
                <input id="addIcon" name="icon"/>
            </span>
            <span class="column">
                <label for="addOpenMode">打开方式</label>
                <input id="addOpenMode" name="openMode"/>
            </span>
            <span class="columnLast">
                <label for="addSort">排序</label>
                <input id="addSort" name="sort"/>
            </span>
        </div>
        <div class="row">
            <span class="columnLast">
                <label for="addResourcesId">菜单地址</label>
                <input id="addResourcesId" name="resourcesId" style="width: 680px;"/>
            </span>
        </div>
        <div class="row">
            <span class="columnLast">
                <label for="addRemarks">备注</label>
                <input id="addRemarks" name="remarks" style="width: 680px;height: 140px;"/>
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
        <input id="editId" type="hidden" name="id" />
        <div class="row">
            <span class="column">
               <label for="editMenuType">菜单类别</label>
                <input id="editMenuType" name="menuType"/>
            </span>
            <span class="column">
                 <label for="editName">菜单名称</label>
                <input id="editName" name="name"/>
            </span>
            <span class="columnLast">
                <label for="editParentId">上级菜单</label>
                <input id="editParentId" name="parentId"/>
            </span>
        </div>
        <div class="row">
            <span class="column">
                <label for="editIcon">菜单图标</label>
                <input id="editIcon" name="icon"/>
            </span>
            <span class="column">
                <label for="editOpenMode">打开方式</label>
                <input id="editOpenMode" name="openMode"/>
            </span>
            <span class="columnLast">
                <label for="editSort">排序</label>
                <input id="editSort" name="sort"/>
            </span>
        </div>
        <div class="row">
            <span class="columnLast">
                <label for="editResourcesId">菜单地址</label>
                <input id="editResourcesId" name="resourcesId" style="width: 680px;"/>
            </span>
        </div>
        <div class="row">
            <span class="columnLast">
                <label for="editRemarks">备注</label>
                <input id="editRemarks" name="remarks" style="width: 680px;height: 140px;"/>
            </span>
        </div>
    </form>
</div>
<div id="editDialogButtons">
    <a id="editDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="editDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

</body>
</html>
