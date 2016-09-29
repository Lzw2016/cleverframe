<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/9/29
  Time: 21:20
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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/monitor/ZookeeperMonitor.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/monitor/ZookeeperMonitor.js"></script>
    <%-- 页面标题 --%>
    <title>Zookeeper监控</title>
</head>
<body id="mainPanel" class="easyui-layout" data-options="fit:true,border:false">

<!-- 页面左部 -->
<div data-options="region:'west',title:'Zookeeper数据节点',split:true,border:true,collapsible:false" style="width:750px;">
    <%--Zookeeper树结构数据--%>
    <ul id="zookeeperNodeTree"></ul>
</div>

<!-- 页面中部 -->
<div data-options="region:'center',border:true,minWidth:800,minHeight:300">

</div>
</body>
</html>
