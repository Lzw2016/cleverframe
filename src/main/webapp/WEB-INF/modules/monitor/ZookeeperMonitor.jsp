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
    <div id="nodeInfoDiv">
        <table align="center">
            <tbody>
            <tr class="row">
                <td class="label" colspan="2" style="text-align: center">
                    节点路径:[&nbsp;<label id="zkNodePath" style="color: #2A00FF"></label>&nbsp;]
                </td>
            </tr>
            <tr class="row">
                <td class="label">节点创建时的zxid(czxid):</td>
                <td class="value"><label id="nodeInfoCzxid"></label></td>
            </tr>
            <tr class="row">
                <td class="label">节点最新一次更新发生时的zxid(mzxid):</td>
                <td class="value"><label id="nodeInfoMzxid"></label></td>
            </tr>
            <tr class="row">
                <td class="label">节点创建时的时间戳(ctime):</td>
                <td class="value"><label id="nodeInfoCtime"></label>
                </td>
            </tr>
            <tr class="row">
                <td class="label">节点最新一次更新发生时的时间戳(mtime):</td>
                <td class="value"><label id="nodeInfoMtime"></label></td>
            </tr>
            <tr class="row">
                <td class="label">节点数据的更新次数(version):</td>
                <td class="value"><label id="nodeInfoVersion"></label></td>
            </tr>
            <tr class="row">
                <td class="label">其子节点的更新次数(cversion):</td>
                <td class="value"><label id="nodeInfoCversion"></label></td>
            </tr>
            <tr class="row">
                <td class="label">节点ACL(授权信息)的更新次数(aclVersion):</td>
                <td class="value"><label id="nodeInfoAversion"></label></td>
            </tr>
            <tr class="row">
                <td class="label">ephemeralOwner:</td>
                <td class="value"><label id="nodeInfoEphemeralOwner"></label></td>
            </tr>
            <tr class="row">
                <td class="label">节点数据的字节数(dataLength):</td>
                <td class="value"><label id="nodeInfoDataLength"></label></td>
            </tr>
            <tr class="row">
                <td class="label">子节点个数(numChildren):</td>
                <td class="value"><label id="nodeInfoNumChildren"></label></td>
            </tr>
            <tr class="row">
                <td class="label">pzxid:</td>
                <td class="value"><label id="nodeInfoPzxid"></label></td>
            </tr>
            <tr class="row">
                <td class="label">节点数据(data):</td>
                <td class="value"><label id="nodeInfoData"></label></td>
            </tr>
            <tr class="row">
                <td class="label">节点数据(字符串格式UTF-8):</td>
                <td class="value"><label id="nodeInfoDataStr"></label></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
