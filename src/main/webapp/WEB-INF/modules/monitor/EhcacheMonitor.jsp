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

    <%-- CodeMirror --%>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.js"></script>
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.css">
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/theme/cobalt.css">
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/xml/xml.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/javascript/javascript.js"></script>

    <%--代码格式化JS--%>
    <script src="${applicationScope.staticPath}/CodeFormat/jsbeautify.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/monitor/EhcacheMonitor.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/monitor/EhcacheMonitor.js"></script>
    <%-- 页面标题 --%>
    <title>Ehcache缓存监控</title>
</head>
<body>
<div id="tabsCenter" class="easyui-tabs" data-options="fit:true,border:false,pill:true">
    <div title="缓存管理器" data-options="border:false">
        <div id="tabsCenterCacheManager" class="easyui-layout" data-options="fit:true,border:false">
            <%--缓存管理器概要信息--%>
            <div data-options="region:'north',border:false" style="height:190px;">
                <div style="margin-top: 20px;margin-right: 10px;margin-left: 10px;">
                    <table id="cacheManagerDataTable">
                        <tbody>
                        <tr class="row">
                            <td class="label">名称:</td>
                            <td class="value">
                                <label id="cacheManagerName"></label>
                            </td>
                        </tr>
                        <tr class="row">
                            <td class="label">集群UUID:</td>
                            <td class="value">
                                <label id="cacheManagerClusterUUID"></label>
                            </td>
                        </tr>
                        <tr class="row">
                            <td class="label">状态:</td>
                            <td class="value">
                                <label id="cacheManagerStatus"></label>
                            </td>
                        </tr>
                        <tr class="row">
                            <td class="label">显式命名的缓存管理(named):</td>
                            <td class="value">
                                <label id="cacheManagerNamed"></label>
                            </td>
                        </tr>
                        <tr class="row">
                            <td class="label">缓存磁盘存储路径:</td>
                            <td class="value">
                                <label id="cacheManagerDiskStoreConfiguration"></label>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <%--缓存管理器配置xml--%>
            <div data-options="region:'west',border:false,width:'50%'">
                <%--suppress HtmlFormInputWithoutLabel --%>
                <textarea id="cacheManagerConfigurationXml"></textarea>
            </div>
            <%--缓存管理器配置Json--%>
            <div data-options="region:'east',border:false,width:'50%'">
                <%--suppress HtmlFormInputWithoutLabel --%>
                <textarea id="cacheManagerConfigurationJson"></textarea>
            </div>
        </div>
    </div>

    <div title="缓存监控">
        <div id="cacheStatisticsDiv" style="margin-top: 5px;margin-right: 5px;margin-left: 5px;">
            <%--<div style="width: 500px;height: 400px;float: left;margin-top: 10px;margin-left: 10px; border: 1px solid #DDDDDD;"></div>--%>
        </div>
    </div>

    <div title="缓存内容">

    </div>
</div>
</body>
</html>
