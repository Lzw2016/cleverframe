<%--
  作者: LiZW
  时间: 2016-7-15 20:17
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
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/extend/jquery.easyui.mask.extend.js"></script>

    <%-- CodeMirror --%>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.js"></script>
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.css">
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/theme/cobalt.css">
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/keymap/sublime.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/clike/clike.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/xml/xml.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/htmlembedded/htmlembedded.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/css/css.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/sql/sql.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/javascript/javascript.js"></script>

    <%--代码格式化JS--%>
    <script src="${applicationScope.staticPath}/CodeFormat/cssbeautify.js"></script>
    <script src="${applicationScope.staticPath}/CodeFormat/jsbeautify.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/generator/TemplateEdit.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/generator/TemplateEdit.js"></script>
    <title>代码模版编辑器</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <!-- 页面上部 -->
    <div data-options="region:'north',border:true,split:false" style="height:28px;">
        <a id="saveTemplate" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">保存</a>
        <a id="reloadTemplate" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">重新加载</a>
        <a id="infoTemplate" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-info'">模版信息</a>
        <a id="codeStyle" href="javascript:void(0)" class="easyui-menubutton" data-options="menu:'#mm2',iconCls:'icon-code'">代码样式</a>
        <a id="formatTemplate" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-format'">格式化</a>
        <div id="mm2" style="width:80px;">
            <div data-options="iconCls:'icon-select'">Java</div>
            <div>C#</div>
            <div>XML</div>
            <div>HTML</div>
            <div>JSP</div>
            <div>CSS</div>
            <div>SQL</div>
            <div>JavaScript</div>
            <div>Json</div>
        </div>
    </div>

    <!-- 页面中部 -->
    <div data-options="region:'center',border:true,fit:false,minWidth:800,minHeight:300">
        <textarea id="codeTemplateContent"></textarea>
    </div>
</div>

<%-- 代码模版信息页面 --%>
<div id="templateInfoDialog" style="width: 550px;padding: 5px 10px">
    <div class="templateInfoDiv">
        <table>
            <tbody>
            <tr class="row">
                <td class="label">模版名称</td>
                <td class="value"><label id="infoName"></label></td>
            </tr>
            <tr class="row">
                <td class="label">脚本模版引用</td>
                <td class="value"><label id="infoTemplateRef"></label></td>
            </tr>
            <tr class="row">
                <td class="label">模版语言</td>
                <td class="value"><label id="infoLocale"></label></td>
            </tr>
            <tr class="row">
                <td class="label">代码语言</td>
                <td class="value"><label id="infoCodeType"></label></td>
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
                <td class="label">模版说明</td>
                <td class="value"><label id="infoDescription"></label></td>
            </tr>
            <tr class="row">
                <td class="label">备注信息</td>
                <td class="value"><label id="infoRemarks"></label></td>
            </tr>
            </tbody>
        </table>
        <div style="width: 100%;text-align: center;margin-top: 10px;">
            <a id="templateInfoDialogClose" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a>
        </div>
    </div>
</div>
</body>
</html>
