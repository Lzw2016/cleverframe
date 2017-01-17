<%--suppress HtmlFormInputWithoutLabel --%>
<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2017/1/12
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%--<!DOCTYPE html>--%>
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
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/extend/jquery.easyui.mask.extend.js"></script>

    <%--Editor.md--%>
    <link rel="stylesheet" href="${applicationScope.staticPath}/editor.md/css/editormd.min.css"/>
    <%--<script type="text/javascript" src="${applicationScope.staticPath}/JQuery/jQuery-1.12.3/jquery-1.12.3.min.js"></script>--%>
    <script type="text/javascript" src="${applicationScope.staticPath}/editor.md/editormd.min.js"></script>

    <%--Moment--%>
    <script src="${applicationScope.staticPath}/Moment/moment-2.17.1/moment.min.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/doc/DocumentEdit.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/doc/DocumentEdit.js"></script>
    <%-- 页面标题 --%>
    <title>文档编辑</title>
</head>
<body>
<div id="editormd" style="width: 100%;height: 100%">
    <textarea style="display:none;"></textarea>
</div>

<%-- 文档信息对话框 --%>
<div id="documentInfoDialog" style="width: 450px;padding: 5px 10px">
    <div class="documentInfoDiv">
        <table>
            <tbody>
            <tr class="row">
                <td class="label">文档标题</td>
                <td class="value"><label id="infoTitle"></label></td>
            </tr>
            </tr>
            <tr class="row">
                <td class="label">创建者</td>
                <td class="value"><label id="infoCreateBy"></label></td>
            </tr>
            <tr class="row">
                <td class="label">创建时间</td>
                <td class="value"><label id="infoCreateDate"></label></td>
            </tr>
            <tr class="row">
                <td class="label">更新者</td>
                <td class="value"><label id="infoUpdateBy"></label></td>
            </tr>
            <tr class="row">
                <td class="label">更新时间</td>
                <td class="value"><label id="infoUpdateDate"></label></td>
            </tr>
            <tr class="row">
                <td class="label">备注信息</td>
                <td class="value"><label id="infoRemarks"></label></td>
            </tr>
            </tbody>
        </table>
        <div style="width: 100%;text-align: center;margin-top: 10px;">
            <a id="documentInfoDialogClose" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a>
        </div>
    </div>
</div>

<%--文档历史版本对话框--%>
<div id="docHistoryDialog" style="width: 850px;height:600px;">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'west',split:false,border:false" style="width:225px;background: #FAFAFA;border-right: 1px solid #95B8E7;">
            <%--文档历史版本树--%>
            <ul id="docHistoryTree"></ul>
        </div>
        <div data-options="region:'center',border:false">
            <textarea id="docContentHistoryView"></textarea>
        </div>
        <div data-options="region:'south',split:false,border:false" style="height:32px;">
            <div id="docHistoryPagination" class="easyui-pagination" style="background:#efefef;border-top:1px solid #95B8E7;"></div>
        </div>
    </div>
</div>
</body>
</html>
