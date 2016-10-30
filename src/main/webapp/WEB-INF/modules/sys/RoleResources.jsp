<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/10/29
  Time: 21:42
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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/sys/RoleResources.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/sys/RoleResources.js"></script>
    <%-- 页面标题 --%>
    <title>角色资源管理</title>
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
<div id="centerPanel" data-options="region:'center',border:false,title:'系统角色信息'">
    <table id="dataTable_1" data-options="border:false">
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
    <div id="dataTableButtons_1">
        <a id="dataTableButtonsSearch_1" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
    </div>
</div>

<%-- 页面下部 --%>
<div data-options="region:'south',border:true,title:'角色拥有的资源信息',split:true,collapsible:false,minHeight:210" style="height:35%;">
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
            <a id="dataTableButtonsAdd_2" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加资源</a>
            <a id="dataTableButtonsDel_2" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">移除资源</a>
            <span style="margin-left: 50px;margin-top: 5px;">
                <label style="font-weight: bold;">选择角色:</label>
                <label id="selectRoleText" style="color: red;"></label>
            </span>
        </div>
    </table>
</div>

<%--选择依赖资源对话框--%>
<div id="selectResourcesDialog" style="width: 800px;height: 450px;">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'north',border:true,split:false,collapsible:true,maxHeight:105,minHeight:105">
            <form id="selectForm" method="post">
                <%--资源类型（1:Web页面URL地址, 2:后台请求URL地址, 3:Web页面UI资源）--%>
                <%--<input id="selectResourcesType" name="resourcesType" type="hidden" value="2,3">--%>
                <div class="row">
                    <span class="columnLast">
                        <label for="selectTitle">资源标题</label>
                        <input id="selectTitle" name="title" class="easyui-textbox" style="width: 650px">
                    </span>
                </div>
                <div class="row">
                    <span class="columnLast">
                        <label for="selectResourcesUrl">资源URL</label>
                        <input id="selectResourcesUrl" name="resourcesUrl" class="easyui-textbox" style="width: 650px">
                    </span>
                </div>
                <div class="row">
                    <span class="columnLast">
                        <label for="selectPermission">权限标识</label>
                        <input id="selectPermission" name="permission" class="easyui-textbox" style="width: 650px">
                    </span>
                </div>
            </form>
        </div>
        <div data-options="region:'center',border:true">
            <table id="dataTable_3" data-options="border:false">
                <thead>
                <tr>
                    <th data-options="field:'check',checkbox:true">选择</th>
                    <th data-options="width:100,align:'left',hidden:true  ,field:'id'">编号</th>
                    <th data-options="width:150,align:'left',hidden:false ,field:'title'">资源标题</th>
                    <th data-options="width:300,align:'left',hidden:false ,field:'resourcesUrl'">资源URL</th>
                    <th data-options="width:150,align:'left',hidden:false ,field:'permission'">权限标识</th>
                    <th data-options="width:100,align:'left',hidden:false ,field:'resourcesType',formatter:pageJsObject.resourcesTypeFormatter">资源类型</th>
                    <th data-options="width:200,align:'left',hidden:false ,field:'description'">资源说明</th>
                </tr>
                </thead>
                <div id="dataTableButtons_3">
                    <a id="dataTableButtonsSearch_3" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
                </div>
            </table>
        </div>
    </div>
</div>
<div id="selectResourcesDialogButtons">
    <a id="selectResourcesDialogButtonsOK" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a>
    <a id="selectResourcesDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
</div>
</body>
</html>
