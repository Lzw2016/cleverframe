<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/12/4
  Time: 18:49
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

    <%-- CodeMirror --%>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.js"></script>
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.css">
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/theme/cobalt.css">
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/clike/clike.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/xml/xml.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/htmlembedded/htmlembedded.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/css/css.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/sql/sql.js"></script>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/javascript/javascript.js"></script>

    <%--代码格式化JS--%>
    <script src="${applicationScope.staticPath}/CodeFormat/jsbeautify.js"></script>

    <%--Moment--%>
    <script src="${applicationScope.staticPath}/Moment/moment-2.17.1/moment.min.js"></script>

    <%--FancyBox--%>
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/lib/jquery.mousewheel-3.0.6.pack.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/jquery.fancybox.pack.js?v=2.1.5"></script>
    <link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/FancyBox/FancyBox-v2.1.5-0/source/jquery.fancybox.css?v=2.1.5" media="screen"/>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/activiti/ProcessDefinition.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/activiti/ProcessDefinition.js"></script>
    <%-- 页面标题 --%>
    <title>流程定义</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:75px;">
    <form id="searchForm">
        <%--'name'（默认），'id'，'key'，'category'，'deploymentId'和'version'--%>
        <input type="hidden" name="sort" value="name">
        <input type="hidden" name="order" value="asc">
        <div class="row">
            <span class="column">
                <label for="searchNameLike">流程名称</label>
                <input id="searchNameLike" name="nameLike">
            </span>
            <span class="column">
                <label for="searchCategoryLike">流程类别</label>
                <input id="searchCategoryLike" name="categoryLike">
            </span>
            <span class="columnLast">
                <label for="searchKeyLike">模型Key</label>
                <input id="searchKeyLike" name="keyLike">
            </span>
        </div>
        <div class="row">
            <span class="column">
                <label for="searchResourceNameLike">源数据名称</label>
                <input id="searchResourceNameLike" name="resourceNameLike">
            </span>
            <span class="column">
                <label for="searchStartableByUser">启动的用户</label>
                <input id="searchStartableByUser" name="startableByUser">
            </span>
            <span class="columnLast">
                <label for="searchSuspended">是否挂起</label>
                <input id="searchSuspended" name="suspended">
            </span>
        </div>
        <%--deploymentId--%>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:true">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:150,align:'left',hidden:false,field:'id'">流程ID</th>
            <th data-options="width:150,align:'left',hidden:false,field:'name'">流程名称</th>
            <th data-options="width:150,align:'left',hidden:false,field:'key'">流程Key</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'version'">流程版本</th>
            <th data-options="width:230,align:'left',hidden:false,field:'category'">流程类别</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'suspended'">是否挂起</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'graphicalNotationDefined'">是否图形化</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'startFormDefined'">是否有表单</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'deploymentId'">部署ID</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'deploymentUrl',formatter:pageJsObject.deploymentUrlFormatter">部署Url</th>
            <th data-options="width:60 ,align:'left',hidden:false,field:'url',formatter:pageJsObject.urlFormatter">流程Url</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'resource',formatter:pageJsObject.resourceFormatter">流程源数据</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'diagramResource',formatter:pageJsObject.diagramResourceFormatter">流程图形源数据</th>
            <th data-options="width:180,align:'left',hidden:false,field:'description'">流程说明</th>
            <th data-options="width:170,align:'left',hidden:false,field:'operate',formatter:pageJsObject.operateFormatter">操作</th>
        </tr>
        </thead>
    </table>
    <div id="dataTableButtons">
        <a id="dataTableButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
        <a id="dataTableButtonsStart" class="easyui-linkbutton" data-options="iconCls:'icon-start',plain:true">启动流程</a>
    </div>
</div>

<%-- 查看流程数据对话框(文本) --%>
<div id="viewCodeTemplateDialog" style="width: 700px;height: 450px;">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <textarea id="viewCodeTemplateEdit"></textarea>
</div>

<div id="addProcessInstanceDialog" style="width: 680px;height: 450px;">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'north',border:false" style="height:75px;">
            <form id="addProcessInstanceForm" method="post">
                <%--processDefinitionId--%>
                <%--processDefinitionKey--%>
                <%--message--%>
                <div class="row">
                    <span class="column">
                        <label for="addStartType">启动类型</label>
                        <select id="addStartType" name="startType">
                            <option value="ID" selected="selected">流程ID</option>
                            <option value="KEY">流程Key</option>
                            <option value="MESSAGE">流程Message</option>
                        </select>
                    </span>
                    <span class="columnLast">
                        <label for="addProcessDefinitionId">启动数据</label>
                        <input id="addProcessDefinitionId" name="processDefinitionId"/>
                    </span>
                </div>
                <div class="row">
                    <span class="column">
                        <label for="addBusinessKey">业务数据Key</label>
                        <input id="addBusinessKey" name="businessKey"/>
                    </span>
                    <span class="columnLast">
                        <label for="addTenantId">租户ID</label>
                        <input id="addTenantId" name="tenantId"/>
                    </span>
                </div>
            </form>
        </div>
        <div data-options="region:'center',border:false">
            <table id="variablesTable" data-options="border:true">
                <thead>
                <tr>
                    <th data-options="width:180,align:'left',hidden:false,field:'key',editor:{type:'textbox',options:{required: true}}">变量名称</th>
                    <th data-options="width:430,align:'left',hidden:false,field:'value',editor:{type:'textbox',options:{required: true}}">变量值</th>
                </tr>
                </thead>
            </table>
            <div id="variablesTableButtons">
                <a id="variablesTableButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" href="javascript:void(0)"></a>
                <a id="variablesTableButtonsRemove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" href="javascript:void(0)"></a>
                <a id="variablesTableButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" href="javascript:void(0)"></a>
                <span style="float: right;margin-right: 45px;margin-top: 5px;color: #738014;font-size: 14px;font-weight: bold;">流程变量数据设置</span>
            </div>
        </div>
    </div>
</div>
<div id="addProcessInstanceDialogButtons">
    <a id="addProcessInstanceDialogSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)">启动流程</a>
    <a id="addProcessInstanceDialogCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)">取消</a>
</div>

</body>
</html>
