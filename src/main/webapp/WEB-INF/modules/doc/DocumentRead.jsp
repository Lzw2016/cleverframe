<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2017/1/15
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%-- EasyUI --%>
    <%--<link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/themes/default/easyui.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/themes/icon.css">--%>
    <%--<script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/jquery.min.js"></script>--%>
    <%--<script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>--%>
    <%--<script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>--%>
    <%--<script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/extend/jquery.easyui.customize.js"></script>--%>

    <%--Editor.md 显示文档需要 --%>
    <%--<link rel="stylesheet" href="${applicationScope.staticPath}/editor.md/css/editormd.preview.min.css"/>--%>
    <link rel="stylesheet" href="${applicationScope.staticPath}/editor.md/css/editormd.min.css"/>
    <script type="text/javascript" src="${applicationScope.staticPath}/JQuery/jQuery-1.12.3/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/editor.md/editormd.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/editor.md/lib/marked.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/editor.md/lib/prettify.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/editor.md/lib/raphael.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/editor.md/lib/underscore.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/editor.md/lib/sequence-diagram.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/editor.md/lib/flowchart.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/editor.md/lib/jquery.flowchart.min.js"></script>

    <%-- Bootstrap --%>
    <link rel="stylesheet" href="${applicationScope.staticPath}/Bootstrap/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${applicationScope.staticPath}/Bootstrap/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css" />
    <%--<script src="${applicationScope.staticPath}/Bootstrap/js/jquery-1.12.3.min.js"></script>--%>
    <script src="${applicationScope.staticPath}/Bootstrap/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/doc/DocumentRead.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/doc/DocumentRead.js"></script>
    <%-- 页面标题 --%>
    <title>阅读文档</title>
</head>
<body>
<div>
    <%--页面头部--%>
</div>

<div class="container">
    <%--文档内容--%>
    <div id="editormd">
        <textarea style="display:none;"></textarea>
    </div>
</div>

<div>
    <%--页面底部--%>
</div>
</body>
</html>
