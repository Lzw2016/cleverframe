<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/12/4
  Time: 18:35
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
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/xml/xml.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/htmlembedded/htmlembedded.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/css/css.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/sql/sql.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/javascript/javascript.js"></script>

    <%--代码格式化JS--%>
    <script src="${applicationScope.staticPath}/CodeFormat/jsbeautify.js"></script>

    <%--Moment--%>
    <script src="${applicationScope.staticPath}/Moment/moment-2.17.1/moment.min.js"></script>

    <%--FancyBox--%>
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/lib/jquery.mousewheel-3.0.6.pack.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/jquery.fancybox.pack.js?v=2.1.5"></script>
    <link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/jquery.fancybox.css?v=2.1.5" media="screen"/>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/activiti/Model.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/activiti/Model.js"></script>
    <%-- 页面标题 --%>
    <title>流程模型管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:75px;">
    <form id="searchForm">
        <%--sort	否	'id' (默认), 'category', 'createTime', 'key', 'lastUpdateTime', 'name'，'version'或'tenantId'	排序的字段，和'order'一起使用。--%>
        <input type="hidden" name="sort" value="name">
        <input type="hidden" name="order" value="asc">
        <div class="row">
            <span class="column">
                <label for="searchNameLike">流程名称</label>
                <input id="searchNameLike" name="nameLike">
            </span>
            <span class="column">
                <label for="searchCategoryLike">流程类别</label>
                <input id="searchCategoryLike" name="categoryLike">
            </span>
            <span class="columnLast">
                <label for="searchKey">模型Key</label>
                <input id="searchKey" name="key">
            </span>
        </div>
        <div class="row">
            <span class="column">
                <label for="searchTenantIdLike">租户ID</label>
                <input id="searchTenantIdLike" name="tenantIdLike">
            </span>
            <span class="column">
                <label for="searchDeployed">是否部署</label>
                <input id="searchDeployed" name="deployed">
            </span>
            <span class="columnLast">
                <label for="searchLatestVersion">最后的版本</label>
                <input id="searchLatestVersion" name="latestVersion">
            </span>
        </div>
        <%--deploymentId--%>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:true">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:80 ,align:'left',hidden:false,field:'id'">模型ID</th>
            <th data-options="width:150,align:'left',hidden:false,field:'name'">模型名称</th>
            <th data-options="width:150,align:'left',hidden:false,field:'key'">模型Key</th>
            <th data-options="width:150,align:'left',hidden:false,field:'category'">模型类型</th>
            <th data-options="width:130,align:'left',hidden:false,field:'createTime',formatter:pageJsObject.timeFormatter">创建时间</th>
            <th data-options="width:130,align:'left',hidden:false,field:'lastUpdateTime',formatter:pageJsObject.timeFormatter">更新时间</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'version'">模型版本</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'metaInfo',formatter:pageJsObject.metaInfoFormatter">MetaInfo</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'url',formatter:pageJsObject.urlFormatter">模型Url</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'deploymentId'">流程部署ID</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'deploymentUrl'">流程部署Url</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'sourceUrl',formatter:pageJsObject.sourceUrlFormatter">SourceUrl</th>
            <th data-options="width:100,align:'left',hidden:false,field:'sourceExtraUrl',formatter:pageJsObject.sourceExtraUrlFormatter">SourceExtraUrl</th>
            <th data-options="width:120,align:'left',hidden:false,field:'tenantId'">租户ID</th>
            <th data-options="width:120,align:'left',hidden:false,field:'operate',formatter:pageJsObject.operateFormatter">操作</th>
        </tr>
        </thead>
    </table>
    <div id="dataTableButtons">
        <a id="dataTableButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
        <a id="dataTableButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
    </div>
</div>

<%-- 新增对话框 --%>
<div id="addDialog" style="width: 700px;height: 280px;padding: 5px 10px">
    <form id="addForm" method="post">
        <div class="row">
            <span class="column">
                <label for="addName">模型名称</label>
                <input id="addName" name="name"/>
            </span>
            <span class="columnLast">
                <label for="addKey">模型Key</label>
                <input id="addKey" name="key"/>
            </span>
        </div>
        <div class="row">
            <span class="column">
                <label for="addCategory">模型类别</label>
                <input id="addCategory" name="category"/>
            </span>
            <span class="columnLast">
                <label for="addTenantId">租户ID</label>
                <input id="addTenantId" name="tenantId"/>
            </span>
        </div>
        <div class="row">
            <label for="addDescription">模型说明</label>
            <input id="addDescription" name="description" style="width: 580px; height: 120px;"/>
        </div>
    </form>
</div>
<div id="addDialogButtons">
    <a id="addDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="addDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

<%-- 查看流程数据对话框(文本) --%>
<div id="viewCodeTemplateDialog" style="width: 700px;height: 450px;">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <textarea id="viewCodeTemplateEdit"></textarea>
</div>
</body>
</html>
