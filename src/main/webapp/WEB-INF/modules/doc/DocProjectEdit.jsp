<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2017/1/15
  Time: 12:12
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
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/extend/jquery.easyui.mask.extend.js"></script>

    <%--Editor.md--%>
    <link rel="stylesheet" href="${applicationScope.staticPath}/editor.md/css/editormd.min.css"/>
    <%--<script type="text/javascript" src="${applicationScope.staticPath}/JQuery/jQuery-1.12.3/jquery-1.12.3.min.js"></script>--%>
    <script type="text/javascript" src="${applicationScope.staticPath}/editor.md/editormd.min.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/doc/DocProjectEdit.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/doc/DocProjectEdit.js"></script>
    <%-- 页面标题 --%>
    <title>文档项目编辑</title>
</head>
<body>
<div id="mainPanel" class="easyui-layout" data-options="fit:true,border:false">
    <!-- 页面左部 -->
    <div data-options="region:'west',title:'文档目录',split:true,border:true,collapsible:false" style="width:240px;">
        <div id="docDocumentTreeLoading" class="panel-loading" style="display: none;">刷新中，请稍待...</div>
        <%-- 项目文档树 --%>
        <ul id="docDocumentTree"></ul>

        <!-- 项目文档树右键菜单 -->
        <div id="menuByDocDocumentTree" class="easyui-menu">
            <div data-options="name:'refresh',iconCls:'icon-reload'">刷新</div>
            <div data-options="name:'addDocument',iconCls:'icon-addDocument'">新增文档</div>
            <div data-options="name:'edit',iconCls:'icon-edit'">编辑</div>
            <div data-options="name:'delete',iconCls:'icon-remove'">删除</div>
            <div data-options="name:'history',iconCls:'icon-history'">历史版本</div>
            <div data-options="name:'properties',iconCls:'icon-properties'">文档属性</div>
            <div class="menu-sep"></div>
            <%--TODO 展开或折叠只需要两个 - 自动判断 --%>
            <div data-options="name:'expand',iconCls:'icon-expand'">展开当前文档</div>
            <div data-options="name:'expandAll',iconCls:'icon-expandAll'">展开所有文档</div>
            <div data-options="name:'collapse',iconCls:'icon-collapse'">折叠当前文档</div>
            <div data-options="name:'collapseAll',iconCls:'icon-collapseAll'">折叠所有文档</div>
        </div>
    </div>

    <!-- 页面中部 -->
    <div data-options="region:'center',border:true,fit:false,minWidth:800,minHeight:300">
        <div id="tabsCenter" class="easyui-tabs" data-options="fit:true,border:'false',tools:'#tabsCenterTools'"></div>
        <div id="tabsCenterTools">
            <a id="tabsCenterToolsCloseTab" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-windowClose'">关闭</a>
        </div>
    </div>
</div>


</body>
</html>
