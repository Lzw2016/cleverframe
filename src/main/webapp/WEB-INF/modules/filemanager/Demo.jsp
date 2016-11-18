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
单个文件进度: <div id="progressbar001-1" class="easyui-progressbar" style="width:500px;"></div><br/>
所有文件进度: <div id="progressbar001-2" class="easyui-progressbar" style="width:500px;"></div><br/>
<hr/>

<input id="fileupload002" type="file" multiple="multiple" name="files[]" > <br/>
<div id="progressDiv" >
    上传文件总进度：<div id="progressbar002" class="easyui-progressbar" style="width:500px;"></div><br/>
</div>
<hr/>

<input id="fileupload003" type="file" name="files[]" > <br/>
MD5:(依赖函数 arrayBufferToWordArray(arrayBuffer)、swapendian32(val) ) <br/>
<span id="md5003-1">MD5值</span>
<div id="progressbar003-1" class="easyui-progressbar" data-options="text:'计算进度：{value}%'" style="width:500px;"></div><br/>

MD5:(依赖文件 lib-typedarrays-min.js ) <br/>
<span id="md5003-2">MD5值</span>
<div id="progressbar003-2" class="easyui-progressbar" data-options="text:'计算进度：{value}%'"  style="width:500px;"></div><br/>
<hr/>

<input id="fileupload004" type="file" name="files[]" style="margin-right: 20px">
<input type="button" value="开始上传" onclick="pageJsObject.start();" ><br/>
秒传进度: <span id="uploadLazyToLocal004"></span><div id="progressbar004-1" class="easyui-progressbar" style="width:500px;"></div><br/>
上传进度: <span id="uploadToLocal004"></span><div id="progressbar004-2" class="easyui-progressbar" style="width:500px;"></div><br/>

</body>
</html>
