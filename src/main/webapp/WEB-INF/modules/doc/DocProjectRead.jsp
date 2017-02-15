<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2017/1/15
  Time: 12:13
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

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/doc/DocProjectRead.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/doc/DocProjectRead.js"></script>
    <%-- 页面标题 --%>
    <title>文档项目阅读</title>
</head>
<body>
<div id="mainPanel" class="easyui-layout" data-options="fit:true,border:false">
    <!-- 页面上部 -->
    <div data-options="region:'north',border:false">
        <div class="tabs-header" style="overflow: hidden; height: 40px;">
            <div class="logo">
                阅读文档
                <span id="docProjectName"></span>
            </div>
            <div class="topNav">
                当前用户：<a href="javascript:void(0)"><span id="currentUser"></span></a> |
                <a href="javascript:void(0)">个人主页</a> |
                <a href="javascript:void(0)">系统消息(0)</a> |
                <a href="javascript:void(0)">安全退出</a>
            </div>
        </div>
    </div>

    <!-- 页面左部 -->
    <div data-options="region:'west',title:'文档目录',split:true,border:true,collapsible:false" style="width:215px;">
        <div id="docDocumentTreeLoading" class="panel-loading" style="display: none;">刷新中，请稍待...</div>
        <%-- 项目文档树 --%>
        <ul id="docDocumentTree"></ul>

        <!-- 项目文档树右键菜单 -->
        <div id="menuByDocDocumentTree" class="easyui-menu">
            <div data-options="name:'refresh',iconCls:'icon-reload'">刷新</div>
            <div data-options="name:'expandAll',iconCls:'icon-expandAll'">展开所有文档</div>
            <div data-options="name:'collapseAll',iconCls:'icon-collapseAll'">折叠所有文档</div>
        </div>
    </div>

    <!-- 页面中部 -->
    <div data-options="region:'center',border:true,fit:false,minWidth:800,minHeight:300" style="background-color: #F9F9F5;">
        <div id="tabsCenter" class="easyui-tabs" data-options="fit:true,border:'false',tools:'#tabsCenterTools'"></div>
        <div id="tabsCenterTools">
            <a id="tabsCenterToolsCloseTab" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-windowClose'">关闭</a>
        </div>
    </div>
</div>
</body>
</html>
