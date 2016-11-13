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

    <%--Chart.js--%>
    <script src="${applicationScope.staticPath}/Chart.js/Chart.js-2.2.1/Chart.min.js"></script>

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
        </div>
    </div>

    <div title="缓存数据查询">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north'" style="height:50px;">
                <form id="searchForm" method="post">
                    <div class="row">
                        <span class="column">
                            <label for="searchCacheName">缓存名称</label>
                            <input id="searchCacheName" name="cacheName">
                        </span>

                        <span class="columnLast">
                            <label for="searchKey">缓存键</label>
                            <input id="searchKey" name="key" style="width: 560px">
                        </span>
                    </div>
                </form>
            </div>
            <div data-options="region:'center'">
                <table id="cacheDataTable" data-options="border:false">
                    <thead>
                    <tr>
                        <th data-options="width:450 ,align:'left',hidden:false ,field:'key',formatter:pageJsObject.jobDataFormatter">Key</th>
                        <th data-options="width:300 ,align:'left',hidden:true ,field:'keyClass'">Key Class</th>
                        <th data-options="width:50 ,align:'left',hidden:false ,field:'value',formatter:pageJsObject.jobDataFormatter">value</th>
                        <th data-options="width:300 ,align:'left',hidden:false ,field:'valueClass'">Value Class</th>
                        <th data-options="width:130 ,align:'left',hidden:false ,field:'createTime'">创建时间</th>
                        <th data-options="width:160 ,align:'left',hidden:false ,field:'expirationTime'">过期时间</th>
                        <th data-options="width:60 ,align:'left',hidden:false ,field:'hitCount'">命中次数</th>
                        <th data-options="width:130 ,align:'left',hidden:false ,field:'lastAccessTime'">最后访问时间</th>
                        <th data-options="width:130 ,align:'left',hidden:false ,field:'lastUpdateTime'">最后更新时间</th>
                        <th data-options="width:70 ,align:'left',hidden:false ,field:'serializedSize',formatter:pageJsObject.serializedSizeFormatter">元素大小</th>
                        <th data-options="width:60 ,align:'left',hidden:false ,field:'timeToIdle',formatter:pageJsObject.timeFormatter">空闲时间</th>
                        <th data-options="width:60 ,align:'left',hidden:false ,field:'timeToLive',formatter:pageJsObject.timeFormatter">存储时间</th>
                    </tr>
                    </thead>
                </table>
                <div id="cacheDataTableButtons">
                    <a id="cacheDataTableSearchAll" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询所有</a>
                    <a id="cacheDataTableDelete" class="easyui-linkbutton" data-options="iconCls:'icon-delete',plain:true">删除元素</a>
                    <a id="cacheDataTableClearAll" class="easyui-linkbutton" data-options="iconCls:'icon-clearCache',plain:true">清除所有</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- 查看对话框 --%>
<div id="jsonViewDialog" style="width: 850px;height: 330px;">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <textarea id="jsonViewEdit"></textarea>
</div>
</body>
</html>
