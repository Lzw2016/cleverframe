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

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/doc/DocumentRead.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/doc/DocumentRead.js"></script>
    <%-- 页面标题 --%>
    <title>阅读文档</title>
</head>
<body>
<div class="page">
    <%--页面头部--%>
    <div class="header">
        <span class="headerDivFirst">
            <span id="title"><%--文章标题--%></span>
        </span>
        <span class="headerDivLast">
            <span id="createBy"><%--作者--%></span>
            <span id="createDate"><%--创建时间--%></span>
            <span id="updateBy"><%--更新人--%></span>
            <span id="updateDate"><%--最后更新时间--%></span>
            <span id="numberOfWords"><%--字数--%></span>
        </span>
    </div>

    <%--文档内容--%>
    <div id="editormd" class="content">
        <textarea style="display:none;"></textarea>
    </div>

    <%--文档菜单--%>
    <div id="custom-toc-container" class="menu"></div>
</div>
<div>
    <%--页面底部--%>
</div>
</body>
</html>
