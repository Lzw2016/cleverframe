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
<div data-options="region:'west',title:'菜单类别',split:true,collapsible:false,minWidth:150,maxWidth:500,border:true" style="width:300px;">
    <ul id="menuTypeList" data-options="fit:true,border:false"></ul>
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
            <th data-options="width:100,align:'left',hidden:false ,field:'name'">菜单名称</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'resourcesId'">资源ID,关联表:core_resources</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'icon'">图标</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'openMode'">菜单打开方式</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'sort'">排序</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'remarks'">备注信息</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'delFlag'">删除标记</th>
        </tr>
        </thead>
    </table>
    <div id="treeGridButtons">
        <a id="treeGridButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">查询</a>
        <a id="treeGridButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
        <a id="treeGridButtonsEdit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
        <a id="treeGridButtonsDel" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </div>
</div>



</body>
</html>
