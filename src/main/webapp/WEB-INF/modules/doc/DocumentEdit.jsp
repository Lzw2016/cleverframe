<%--suppress HtmlFormInputWithoutLabel --%>
<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2017/1/12
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <%--Editor.md--%>
    <link rel="stylesheet" href="${applicationScope.staticPath}/editor.md/css/editormd.min.css"/>
    <script type="text/javascript" src="${applicationScope.staticPath}/JQuery/jQuery-1.12.3/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/editor.md/editormd.min.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/doc/DocumentEdit.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/doc/DocumentEdit.js"></script>
    <%-- 页面标题 --%>
    <title>文档编辑</title>
</head>
<body>

<div id="editormd">
    <textarea style="display:none;">### Hello Editor.md !</textarea>
</div>

</body>
</html>
