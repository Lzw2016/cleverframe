<%--
  作者: LiZW
  时间: 2016-6-19 22:29
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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/core/AccessLog.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/core/AccessLog.js"></script>
    <%-- 页面标题 --%>
    <title>系统访问日志管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:140px;">
    <form id="searchForm" method="post">
        <div class="row">
            <span class="column">
                <label for="searchMethod">操作方式</label>
                <input id="searchMethod" name="method" class="easyui-combobox">
            </span>

            <span class="columnLast">
                <label for="searchRequestUri">请求URI</label>
                <input id="searchRequestUri" name="requestUri" class="easyui-textbox" style="width: 445px">
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="searchLoginName">登录名</label>
                <input id="searchLoginName" name="loginName" class="easyui-textbox">
            </span>

            <span class="columnLast">
                <label for="searchRequestStartTime">请求时间</label>
                <input id="searchRequestStartTime" name="requestStartTime" class="easyui-datetimebox">
                <label for="searchRequestEndTime" style="text-align: center"> 至 </label>
                <input id="searchRequestEndTime" name="requestEndTime" class="easyui-datetimebox">
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="searchRemoteAddr">请求IP</label>
                <input id="searchRemoteAddr" name="remoteAddr" class="easyui-textbox">
            </span>

            <span class="columnLast">
                <label for="searchProcessMinTime">处理时间</label>
                <input id="searchProcessMinTime" name="processMinTime" class="easyui-numberspinner">
                <label for="searchProcessMaxTime" style="text-align: center">至</label>
                <input id="searchProcessMaxTime" name="processMaxTime" class="easyui-numberspinner">
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="searchUserAgent">用户代理</label>
                <input id="searchUserAgent" name="userAgent" class="easyui-textbox">
            </span>

            <span class="columnLast">
                <label for="searchHasException">是否有异常</label>
                <input id="searchHasException" name="hasException" class="easyui-combobox">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:false">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:30 ,align:'left',hidden:true ,field:'id'">编号</th>
            <th data-options="width:80,align:'left',hidden:false,field:'loginName'">登录名</th>
            <th data-options="width:130,align:'left',hidden:false,field:'requestTime'">请求时间</th>
            <th data-options="width:850,align:'left',hidden:false,field:'requestUri'">请求URI</th>
            <th data-options="width:80,align:'left',hidden:false,field:'method'">操作方式</th>
            <th data-options="width:260,align:'left',hidden:false,field:'params'">操作提交的数据</th>
            <th data-options="width:80,align:'left',hidden:false,field:'processTime',formatter:pageJsObject.processTimeFormatter">请求处理时间</th>
            <th data-options="width:140,align:'left',hidden:false,field:'remoteAddr'">请求IP</th>
            <th data-options="width:130,align:'left',hidden:false,field:'userAgent'">用户代理</th>
            <th data-options="width:80,align:'left',hidden:false,field:'hasException',formatter:pageJsObject.hasExceptionFormatter">是否有异常</th>
            <th data-options="width:150,align:'left',hidden:true,field:'exceptionInfo'">异常信息</th>
        </tr>
        </thead>
    </table>
    <div id="dataTableButtons">
        <a id="dataTableButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
    </div>
</div>

<%-- 查看对话框 --%>
<div id="viewDialog" style="width: 850px;height: 330px;padding: 5px 10px">
    <div class="row" style="height: 18%;">
        <label for="viewParams">请求参数</label>
        <input id="viewParams" name="params" style="width: 700px;height: 100%;"/>
    </div>
    <div class="row" style="margin-top: 10px;height: 75%;">
        <label for="viewExceptionInfo">异常信息</label>
        <textarea id="viewExceptionInfo" name="exceptionInfo"></textarea>
    </div>
</div>
<div id="viewDialogButtons">
    <a id="viewDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
</div>

</body>
</html>
