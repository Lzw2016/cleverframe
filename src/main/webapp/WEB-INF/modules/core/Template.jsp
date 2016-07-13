<%--
  作者: LiZW
  时间: 2016-6-18 17:22
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

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/core/Template.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/core/Template.js"></script>
    <%-- 页面标题 --%>
    <title>FreeMarker模版管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:80px;">
    <form id="searchForm" method="post">
        <div class="row">
            <span class="columnLast">
                <label for="searchName">模版名称</label>
                <input id="searchName" name="name" class="easyui-textbox" style="width: 645px">
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="searchLocale">模版语言</label>
                <input id="searchLocale" name="locale" >
            </span>
            <span class="column">
                <label for="searchDelFlag">删除标记</label>
                <input id="searchDelFlag" name="delFlag" >
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:false">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:200,align:'left',hidden:false,field:'name'">模版名称</th>
            <th data-options="width:150 ,align:'left',hidden:false,field:'locale',formatter:pageJsObject.localeFormatter">模版语言</th>
            <th data-options="width:450,align:'left',hidden:false,field:'description'">模版说明</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'content'">模版内容</th>

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
            <span class="column">
                <label for="addName">模版名称</label>
                <input id="addName" name="name"/>
            </span>
            <span class="columnLast">
                <label for="addLocale">模版语言</label>
                <input id="addLocale" name="locale"/>
            </span>
        </div>
        <div class="row">
            <label for="addDescription">模版说明</label>
            <input id="addDescription" name="description" style="width: 700px; height: 80px;"/>
        </div>
        <div class="row">
            <label for="addRemarks">备注信息</label>
            <input id="addRemarks" name="remarks" style="width: 700px; height: 50px;"/>
        </div>
        <div class="row" style="margin-top: 15px;width: 100%;height: 100%;">
            <label for="addContent">模版内容</label>
            <textarea id="addContent" name="content"></textarea>
        </div>
    </form>
</div>
<div id="addDialogButtons">
    <a id="addDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="addDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

<%-- 编辑对话框 --%>
<div id="editDialog" style="width: 850px;height: 360px;padding: 5px 10px">
    <form id="editForm" method="post">
        <input id="editId" name="id" type="hidden"/>

        <div class="row">
            <span class="column">
                <label for="editName">模版名称</label>
                <input id="editName" name="name"/>
            </span>
            <span class="column">
                <label for="editLocale">模版语言</label>
                <input id="editLocale" name="locale"/>
            </span>
            <span class="columnLast">
                <label for="editDelFlag">删除标记</label>
                <input id="editDelFlag" name="delFlag"/>
            </span>
        </div>
        <div class="row">
            <label for="editDescription">模版说明</label>
            <input id="editDescription" name="description" style="width: 700px; height: 80px;"/>
        </div>
        <div class="row">
            <label for="editRemarks">备注信息</label>
            <input id="editRemarks" name="remarks" style="width: 700px; height: 50px;"/>
        </div>
        <div class="row" style="margin-top: 15px;width: 100%;height: 100%;">
            <label for="editContent">模版内容</label>
            <textarea id="editContent" name="content"></textarea>
        </div>
    </form>
</div>
<div id="editDialogButtons">
    <a id="editDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
    <a id="editDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>


</body>
</html>
