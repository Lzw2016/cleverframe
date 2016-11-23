<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/11/23
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%--JQuery--%>
    <script type="text/javascript" src="${applicationScope.staticPath}/JQuery/jQuery-1.12.3/jquery-1.12.3.min.js"></script>

    <%--webuploader-0.1.5--%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/FileUpload/webuploader-0.1.5/webuploader.css">
    <script type="text/javascript" src="${applicationScope.staticPath}/FileUpload/webuploader-0.1.5/webuploader.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/filemanager/WebUploader.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/filemanager/WebUploader.js"></script>
    <%-- 页面标题 --%>
    <title>文件上传示例</title>
</head>
<body>

<div id="uploader" class="wu-example">
    <!--用来存放文件信息-->
    <div id="thelist" class="uploader-list"></div>
    <div class="btns">
        <div id="picker">选择文件</div>
        <button id="ctlBtn" style="margin-top: 8px;">开始上传</button>
    </div>
</div>

<hr/>

<div id="uploader-demo" class="wu-example">
    <div id="fileList" class="uploader-list"></div>
    <div id="filePicker">选择图片</div>
</div>

<hr/>

<div id="uploader-demo03" class="wu-example">
    <div id="fileList03" class="uploader-list"></div>
    <div id="filePicker03">选择图片</div>
</div>
<div id="fileMd5"></div>
</body>
</html>
