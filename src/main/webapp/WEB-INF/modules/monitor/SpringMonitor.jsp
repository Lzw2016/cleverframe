<%--
  作者: LiZW
  时间: 2016-8-17 18:10
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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/monitor/SpringMonitor.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/monitor/SpringMonitor.js"></script>
    <%-- 页面标题 --%>
    <title>Spring监控</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">

<div data-options="region:'north',border:false" style="height:50px;">
    <form id="searchForm" method="post">
        <div class="row">
            <span class="columnLast">
                <label for="searchBeanName">Bean名称</label>
                <input id="searchBeanName" name="beanName" style="width: 560px">
            </span>
        </div>
    </form>
</div>

<div data-options="region:'center',border:true">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:350 ,align:'left',hidden:false ,field:'name'">Bean名称</th>
            <th data-options="width:600 ,align:'left',hidden:false ,field:'clazz'">Bean类型</th>
            <th data-options="width:500 ,align:'left',hidden:false ,field:'jsonValue'">值</th>
        </tr>
        </thead>
        <div id="dataTableButtons">
            <a id="dataTableSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
        </div>
    </table>
</div>
</body>
</html>
