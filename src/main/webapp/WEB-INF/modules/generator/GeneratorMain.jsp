<%--
  作者: LiZW
  时间: 2016-7-14 20:47
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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/generator/GeneratorMain.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/generator/GeneratorMain.js"></script>
    <%-- 页面标题 --%>
    <title>代码生成器后台</title>
</head>
<body id="mainPanel" class="easyui-layout">
<!-- 页面上部 -->
<div data-options="region:'north',border:false">
    <div class="tabs-header" style="overflow: hidden; height: 50px;">
        <div class="logo">CleverFrame-Generator</div>
        <div class="topNav">
            当前用户：<a href="javascript:void(0)"><span id="currentUser"></span></a> |
            <a href="javascript:void(0)">个人主页</a> |
            <a href="javascript:void(0)">系统消息(0)</a> |
            <a href="javascript:void(0)">安全退出</a>
        </div>
    </div>
</div>

<!-- 页面底部 -->
<div data-options="region:'south',border:false" align="center" class="tabs-header" style="font-size: 12px;">
    请使用最新的
    <a href="javascript:void(0)">Chrome</a> /
    <a href="javascript:void(0)">Firefox</a> /
    <a href="javascript:void(0)">Safari</a>
    浏览器&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;2015版权所有&copy;
</div>

<!-- 页面左部 -->
<div data-options="region:'west',title:'数据库',split:true,border:true" style="width:300px;">
    <ul id="dataBaseTree"></ul>
</div>

<!-- 页面右部 -->
<div data-options="region:'east',title:'代码模版',split:true,border:true" style="width:200px;">
    <ul id="codeTemplateTree"></ul>
</div>

<!-- 页面中部 -->
<div data-options="region:'center',border:true,minWidth:800,minHeight:300">
    <div id="tabsCenter" class="easyui-tabs" data-options="fit:true,border:'false',tools:'#tabsCenterTools'">
        <div title="帮助" data-options="border:'false'">
            帮助页面
        </div>
    </div>
    <div id="tabsCenterTools">
        <a id="tabsCenterToolsRefreshTab" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
        <a id="tabsCenterToolsCloseTab" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'ExpandIcon-cross'">关闭</a>
    </div>
</div>

<!-- 多页签右键菜单 -->
<div id="menuByTabs" class="easyui-menu">
    <div data-options="name:'refresh',iconCls:'icon-reload'">刷新</div>
    <div data-options="name:'openInBrowserNewTab'">在浏览器新窗口打开</div>
    <div class="menu-sep"></div>
    <div data-options="name:'close'">关闭</div>
    <div data-options="name:'closeOther'">关闭其他</div>
    <div data-options="name:'closeAll'">关闭全部</div>
</div>
</body>
</html>
