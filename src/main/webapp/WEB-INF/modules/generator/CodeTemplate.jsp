<%--
  作者: LiZW
  时间: 2016-7-15 20:17
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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/generator/CodeTemplate.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/generator/CodeTemplate.js"></script>
    <%-- 页面标题 --%>
    <title>代码模版管理</title>
</head>
<body id="mainPanel" class="easyui-layout" data-options="fit:true,border:false">

<!-- 页面左部 -->
<div data-options="region:'west',title:'代码模版',split:true,border:true,collapsible:false" style="width:240px;">
    <ul id="codeTemplateTree"></ul>
</div>

<!-- 页面中部 -->
<div data-options="region:'center',border:true,minWidth:800,minHeight:300">
<%--

    <div id="tabsCenter" class="easyui-tabs" data-options="fit:true,border:'false',tools:'#tabsCenterTools'">
    </div>
    <div id="tabsCenterTools">
        <a id="tabsCenterToolsRefreshTab" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
        <a id="tabsCenterToolsCloseTab" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'ExpandIcon-cross'">关闭</a>
    </div>
--%>

</div>

<%-- 新增代码模版对话框 --%>
<div id="addCodeDialog" style="width: 850px;height: 450px;padding: 5px 10px">
    <form id="addCodeForm" method="post" style="margin-top: 10px;">
        <%--模版节点类型 节点类型(0:模版分类; 1:代码模版) --%>
        <input name="nodeType" value="1" type="hidden"/>

        <div class="row">
            <span class="column">
                <label for="addCodeParentId">模版所属分类</label>
                <input id="addCodeParentId" name="parentId"/>
            </span>
            <span class="columnLast">
                <label for="addCodeName">代码模版名称</label>
                <input id="addCodeName" name="name"/>
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="addCodeCodeType">代码语言</label>
                <input id="addCodeCodeType" name="codeType"/>
            </span>
            <span class="columnLast">
                <label for="addCodeLocale">模版语言</label>
                <input id="addCodeLocale" name="locale"/>
            </span>
        </div>

        <div class="row">
            <label for="addCodeDescription">模版说明</label>
            <input id="addCodeDescription" name="description" style="width: 700px; height: 80px;"/>
        </div>
        <div class="row">
            <label for="addCodeRemarks">备注信息</label>
            <input id="addCodeRemarks" name="remarks" style="width: 700px; height: 50px;"/>
        </div>
        <div class="row" style="margin-top: 15px;width: 100%;height: 100%;">
            <label for="addCodeContent" style="text-align: left;">模版内容</label>
            <textarea id="addCodeContent" name="content"></textarea>
        </div>
    </form>
</div>
<div id="addCodeDialogButtons">
    <a id="addCodeDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="addCodeDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

<%-- 新增模版类别对话框 --%>
<div id="addCategoryDialog" style="width: 850px;height: 300px;padding: 5px 10px">
    <form id="addCategoryForm" method="post" style="margin-top: 10px;">
        <%--模版节点类型 节点类型(0:模版分类; 1:代码模版) --%>
        <input name="nodeType" value="0" type="hidden"/>
        <%--模版代码语言，如：java、html、jsp、sql。若是模版分类取值“Category” --%>
        <input name="codeType" value="Category" type="hidden"/>

        <div class="row">
            <span class="column">
                <label for="addCategoryParentId">所属分类</label>
                <input id="addCategoryParentId" name="parentId"/>
            </span>
            <span class="columnLast">
                <label for="addCategoryName">模版分类名称</label>
                <input id="addCategoryName" name="name"/>
            </span>
        </div>

        <div class="row">
            <label for="addCategoryDescription">分类说明</label>
            <input id="addCategoryDescription" name="description" style="width: 700px; height: 100px;"/>
        </div>
        <div class="row">
            <label for="addCategoryRemarks">备注信息</label>
            <input id="addCategoryRemarks" name="remarks" style="width: 700px; height: 50px;"/>
        </div>
    </form>
</div>
<div id="addCategoryDialogButtons">
    <a id="addCategoryDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="addCategoryDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

</body>
</html>
