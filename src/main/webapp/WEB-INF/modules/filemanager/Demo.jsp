<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/11/17
  Time: 21:32
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

    <script type="text/javascript" src="${applicationScope.staticPath}/FileUpload/jQuery-File-Upload-9.14.0/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/FileUpload/jQuery-File-Upload-9.14.0/js/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/FileUpload/jQuery-File-Upload-9.14.0/js/jquery.fileupload.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/CryptoJS/crypto-js-3.1.6/core.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/CryptoJS/crypto-js-3.1.6/md5.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/CryptoJS/crypto-js-3.1.6/lib-typedarrays.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/filemanager/Demo.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/filemanager/Demo.js"></script>
    <%-- 页面标题 --%>
    <title>文件上传示例</title>
</head>
<body>
<input id="fileupload001" type="file" name="files[]" > <br/>
<div id="dropzone001" style="background: #cccccc;width: 500px;height: 70px;text-align: center;">拖放文件到此处上传</div>
progressbar: <div id="progressbar001-1" class="easyui-progressbar" style="width:500px;"></div><br/>
progressallbar: <div id="progressbar001-2" class="easyui-progressbar" style="width:500px;"></div><br/>
从服务器端获取进度:<div id="progressbar001-3" class="easyui-progressbar" style="width:500px;"></div><br/>


</body>
</html>
