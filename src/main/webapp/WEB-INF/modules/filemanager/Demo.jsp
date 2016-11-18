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
    <title>文件上传示例</title>
</head>
<body>
文件上传示例

<body>
<form action="http://localhost:8080/cleverframe/mvc/filemanager/manager/upload.json" method="post" enctype="multipart/form-data">
    选择要上传的图片：<input type="file" name="fileUp"/>
    <input type="submit" value="上传"/>
</form>
</body>
</html>
