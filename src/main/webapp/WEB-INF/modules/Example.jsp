<%--suppress HtmlUnknownTarget --%>
<%--
  作者: LiZW
  时间: 2016-6-4 23:58
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
    <%--<script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/ajxGlobalEventByEasyUI.js"--%>

    <%-- Bootstrap --%>
    <link rel="stylesheet" href="${applicationScope.staticPath}/Bootstrap/bootstrap-3.3.6-dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${applicationScope.staticPath}/Bootstrap/bootstrap-3.3.6-dist/css/bootstrap-theme.min.css" />
    <script src="${applicationScope.staticPath}/Bootstrap/js/jquery-1.12.3.min.js"></script>
    <script src="${applicationScope.staticPath}/Bootstrap/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>

    <%-- CodeMirror --%>
    <%--<script src="//cdn.bootcss.com/jquery/1.12.1/jquery.js"></script>--%>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.js"></script>
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.css">
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/theme/cobalt.css">
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/clike/clike.js"></script>

    <%--Chart.js--%>
    <script src="${applicationScope.staticPath}/Chart.js/Chart.js-2.2.1/Chart.min.js"></script>

    <%--Moment--%>
    <script src="${applicationScope.staticPath}/Moment/moment-2.17.1/moment.min.js"></script>

    <%--FancyBox--%>
    <!-- Add jQuery library -->
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/lib/jquery-1.10.1.min.js"></script>
    <!-- Add mousewheel plugin (this is optional) -->
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/lib/jquery.mousewheel-3.0.6.pack.js"></script>
    <!-- Add fancyBox main JS and CSS files -->
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/jquery.fancybox.pack.js?v=2.1.5"></script>
    <link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/jquery.fancybox.css?v=2.1.5" media="screen" />
    <!-- Add Button helper (this is optional) -->
    <link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
    <!-- Add Thumbnail helper (this is optional) -->
    <link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
    <!-- Add Media helper (this is optional) -->
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/helpers/jquery.fancybox-media.js?v=1.0.6"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/Example.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/Example.js"></script>
    <%-- 页面标题 --%>
    <title>页面示例</title>
</head>
<body>
<%--　路径变量　--%>
${applicationScope.appPath}<br/>
${applicationScope.staticPath}<br/>
${applicationScope.docPath}<br/>
${applicationScope.modulesPath}<br/>
${applicationScope.mvcPath}<br/>


</body>
</html>
