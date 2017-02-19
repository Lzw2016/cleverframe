<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2017/1/15
  Time: 12:11
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

    <%-- Bootstrap --%>
    <link rel="stylesheet" href="${applicationScope.staticPath}/Bootstrap/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${applicationScope.staticPath}/Bootstrap/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"/>
    <script type="text/javascript" src="${applicationScope.staticPath}/Bootstrap/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

    <%--Layer--%>
    <link rel="stylesheet" href="${applicationScope.staticPath}/Layer/skin/default/layer.css"/>
    <script type="text/javascript" src="${applicationScope.staticPath}/Layer/layer.js"></script>

    <%--jqPaginator 分页插件--%>
    <script type="text/javascript" src="${applicationScope.staticPath}/JQuery/Plugins/jqPaginator/jqPaginator.min.js"></script>
    <%--nice-validator 表单验证插件--%>
    <script type="text/javascript" src="${applicationScope.staticPath}/JQuery/Plugins/nice-validator/jquery.validator.min.js?local=zh-CN"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/doc/DocProjectManager.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/doc/DocProjectManager.js"></script>
    <%-- 页面标题 --%>
    <title>文档项目管理</title>
</head>
<body>
<div class="groups-con container">
    <h2>文档项目管理</h2>
    <div class="groups">
        <%--所有文档 分组--%>
        <div class="group">
            <h3>
                <span class="group-name">
                    <i class="glyphicon glyphicon-user"></i> 所有文档
                </span>
            </h3>

            <div class="box-con clearfix" id="docProjectInfoDiv">
                <%--
                &lt;%&ndash;已经存在的文档项目&ndash;%&gt;
                <div class="box">
                    <div class="info">
                        <div class="title">文档标题</div>
                        <div class="intro">文档介绍</div>
                    </div>
                    <div class="status">文档创建时间<span class="creator">@作者</span></div>
                    <div class="star"><i class="glyphicon glyphicon-star"></i></div>
                    <div class="tools">
                        <i class="glyphicon glyphicon-pencil" title="编辑"></i>
                        <i class="glyphicon glyphicon-export" title="转到编辑页面"></i>
                        <i class="glyphicon glyphicon-eye-open" title="阅读"></i>
                        <i class="glyphicon glyphicon-trash" title="删除"></i>
                    </div>
                </div>
                &lt;%&ndash;新增文档项目&ndash;%&gt;
                <div class="box-to-add"></div>
                --%>
            </div>

            <%--分页插件--%>
            <div id="paginatorDiv">
                <ul id="paginatorUl" class="pagination"></ul>
            </div>
        </div>
        <%--我参与的文档 分组--%>
        <%--
        <div class="group">
            <h3>
                <span class="group-name">
                    <i class="glyphicon glyphicon-tag"></i>我参与的文档
                </span>
            </h3>
            <div class="box-con clearfix">
                &lt;%&ndash;新增文档项目&ndash;%&gt;
                <div class="box-to-add"></div>
            </div>
        </div>
        --%>
    </div>
</div>

</body>
</html>
