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
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/extend/jquery.easyui.customize.js"></script>
    <%--<script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/ajxGlobalEventByEasyUI.js"--%>

    <%-- CodeMirror --%>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.js"></script>
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.css">
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/theme/cobalt.css">
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/sql/sql.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/core/QLScript.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/core/QLScript.js"></script>
    <%-- 页面标题 --%>
    <title>数据库脚本管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:110px;">
    <form id="searchForm" method="post">
        <div class="row">
            <span class="columnLast">
                <label for="searchName">脚本名称</label>
                <input id="searchName" name="name" class="easyui-textbox" style="width: 645px">
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="searchType">脚本类型</label>
                <input id="searchType" name="scriptType" class="easyui-combobox">
            </span>
            <span class="column">
                <label for="searchDelFlag">删除标记</label>
                <input id="searchDelFlag" name="delFlag" class="easyui-combobox">
            </span>
            <span class="columnLast">
                <label for="searchId">数据ID</label>
                <input id="searchId" name="id" class="easyui-numberbox">
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="searchUuid">数据UUID</label>
                <input id="searchUuid" name="uuid" class="easyui-textbox" style="width: 400px">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:false">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:650,align:'left',hidden:false,field:'name'">脚本名称</th>
            <th data-options="width:70 ,align:'left',hidden:false,field:'scriptType',formatter:pageJsObject.scriptTypeFormatter">脚本类型</th>
            <th data-options="width:450,align:'left',hidden:false,field:'description'">脚本说明</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'script'">脚本</th>

            <th data-options="width:30 ,align:'left',hidden:true ,field:'id'">编号</th>
            <th data-options="width:80 ,align:'left',hidden:true ,field:'companyCode'">公司编码</th>
            <th data-options="width:80 ,align:'left',hidden:true ,field:'orgCode'">机构编码</th>
            <th data-options="width:50 ,align:'left',hidden:true ,field:'createBy'">创建者</th>
            <th data-options="width:130,align:'left',hidden:false,field:'createDate'">创建时间</th>
            <th data-options="width:50 ,align:'left',hidden:true ,field:'updateBy'">更新者</th>
            <th data-options="width:130,align:'left',hidden:false,field:'updateDate'">更新时间</th>
            <th data-options="width:100,align:'left',hidden:false,field:'remarks'">备注信息</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'delFlag',formatter:pageJsObject.delFlagFormatter">删除标记</th>
            <th data-options="width:80 ,align:'left',hidden:true ,field:'uuid'">UUID</th>
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
<div id="addDialog" style="width: 850px;height: 450px;padding: 5px 10px">
    <form id="addForm" method="post">
        <div class="row">
            <label for="addName">脚本名称</label>
            <input id="addName" name="name" style="width: 700px"/>
        </div>
        <div class="row">
            <label for="addScriptType">脚本类型</label>
            <input id="addScriptType" name="scriptType" style="width: 150px"/>
        </div>
        <div class="row">
            <label for="addDescription">脚本说明</label>
            <input id="addDescription" name="description" style="width: 700px; height: 80px;"/>
        </div>
        <div class="row">
            <label for="addRemarks">备注信息</label>
            <input id="addRemarks" name="remarks" style="width: 700px; height: 50px;"/>
        </div>
        <div class="row" style="margin-top: 15px;">
            <label for="addScript">脚本内容</label>
            <textarea id="addScript" name="script"></textarea>
        </div>
    </form>
</div>
<div id="addDialogButtons">
    <a id="addDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="addDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

<%-- 编辑对话框 --%>
<div id="editDialog" style="width: 850px;height: 450px;padding: 5px 10px">
    <form id="editForm" method="post">
        <input id="editId" name="id" type="hidden"/>
        <input id="editUuid" name="uuid" type="hidden"/>

        <div class="row">
            <label for="editName">脚本名称</label>
            <input id="editName" name="name" style="width: 700px"/>
        </div>
        <div class="row">
            <label for="editScriptType">脚本类型</label>
            <input id="editScriptType" name="scriptType" style="width: 150px"/>

            <label for="editDelFlag" style="margin-left: 30px">删除标记</label>
            <input id="editDelFlag" name="delFlag" style="width: 150px"/>
        </div>

        <div class="row">
            <label for="editDescription">脚本说明</label>
            <input id="editDescription" name="description" style="width: 700px; height: 80px;"/>
        </div>
        <div class="row">
            <label for="editRemarks">备注信息</label>
            <input id="editRemarks" name="remarks" style="width: 700px; height: 50px;"/>
        </div>
        <div class="row" style="margin-top: 15px;">
            <label for="editScript">脚本内容</label>
            <textarea id="editScript" name="script"></textarea>
        </div>
    </form>
</div>
<div id="editDialogButtons">
    <a id="editDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
    <a id="editDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>
</body>
</html>
