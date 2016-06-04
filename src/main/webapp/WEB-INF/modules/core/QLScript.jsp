<%--suppress HtmlUnknownTarget --%>
<%--
  作者: LiZW
  时间: 2016-6-4 23:17
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
    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalSysPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/core/QLScript.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/core/QLScript.js"></script>
    <%-- 页面标题 --%>
    <title>数据库脚本管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true" style="height:70px;">
    <form id="fm-search" method="post" style="margin-top: 5px;margin-left: 20px">
        <div class="row">
				<span class="column">
					<label for="type-search">脚本类型</label>
					<input id="type-search" name="type-search" class="easyui-combobox">
				</span>
				<span class="columnLast">
					<label for="name-search">脚本名称</label>
					<input id="name-search" name="name-search" class="easyui-textbox" style="width: 420px">
				</span>
        </div>
        <div class="row">
				<span class="column">
					<label for="id-search">数据ID</label>
					<input id="id-search" name="id-search" class="easyui-numberbox">
				</span>
				<span class="columnLast">
					<label for="uuid-search">数据UUID</label>
					<input id="uuid-search" name="uuid-search" class="easyui-textbox" style="width: 420px">
				</span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:false">
    <table id="qlScriptData" data-options="border:false">
        <thead>
        <tr>
            <th data-options="field:'name',width:550,align:'left'">脚本名称</th>
            <th data-options="field:'scriptType',width:70,align:'left'">脚本类型</th>
            <th data-options="field:'createDate',width:130,align:'left',hidden:false">创建时间</th>
            <th data-options="field:'description',width:450,align:'left'">脚本说明</th>
            <th data-options="field:'script',width:100,align:'left'">脚本</th>

            <th data-options="field:'id',width:30,align:'left',hidden:true">编号</th>
            <th data-options="field:'companyId',width:80,align:'left',hidden:true">所属公司的ID</th>
            <th data-options="field:'orgId',width:80,align:'left',hidden:true">所属机构的ID</th>
            <th data-options="field:'createBy',width:50,align:'left',hidden:true">创建者</th>
            <th data-options="field:'updateBy',width:50,align:'left',hidden:true">更新者</th>
            <th data-options="field:'updateDate',width:100,align:'left',hidden:true">更新时间</th>
            <th data-options="field:'remarks',width:100,align:'left'">备注信息</th>
            <th data-options="field:'delFlag',width:50,align:'left',hidden:true">删除标记</th>
            <th data-options="field:'uuid',width:80,align:'left',hidden:true">数据UUID</th>
        </tr>
        </thead>
    </table>
    <div id="tb">
        <a onclick="searchQLScript();" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
        <a onclick="newQLScript();" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
        <a onclick="editQLScript();" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
        <a onclick="delQLScript();" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </div>
</div>
</body>
</html>
