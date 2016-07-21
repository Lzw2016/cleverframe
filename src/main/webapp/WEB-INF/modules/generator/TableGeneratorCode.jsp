<%--
  作者: LiZW
  时间: 2016-7-15 10:50
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

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/generator/TableGeneratorCode.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/generator/TableGeneratorCode.js"></script>
    <%-- 页面标题 --%>
    <title>根据数据表选择模版生成代码</title>
</head>
<body>

<div id="tabsCenter" class="easyui-tabs" data-options="fit:true,border:'false',tabPosition:'top',pill:'true'">
    <div title="表结构">
        <div class="easyui-layout" data-options="fit:true,border:false">
            <%-- 页面上部 --%>
            <div data-options="region:'north',border:false,minWidth:800" style="height:40px;background-color:#E0ECFF;">
                <div id="dataBaseTableInfoPanel">
                    <div class="row">
            <span class="column">
                <label class="label">数据库名:</label>
                <label class="value" id="labelSchemaName"></label>
            </span>
             <span class="column">
                <label class="label">表名称:</label>
                <label class="value" id="labelTableName"></label>
            </span>
             <span class="columnLast">
                <label class="label">表描述:</label>
                <label class="value" id="labelDescription"></label>
            </span>
                    </div>
                </div>
            </div>

            <%-- 页面中部 --%>
            <div data-options="region:'center',border:false">
                <table id="dataTable" data-options="border:false">
                    <thead>
                    <tr>
                        <th data-options="field:'select',checkbox:true"></th>
                        <th data-options="width:60,align:'left',hidden:false,field:'ordinalPosition',formatter:pageJsObject.ordinalPositionFormatter">序号位置</th>
                        <th data-options="width:100,align:'left',hidden:true,field:'schemaName'">数据库名</th>
                        <th data-options="width:100,align:'left',hidden:true,field:'tableName'">表名</th>
                        <th data-options="width:160,align:'left',hidden:false,field:'columnName'">列名</th>
                        <th data-options="width:100,align:'left',hidden:false,field:'dataType'">类型</th>
                        <th data-options="width:80,align:'left',hidden:false,field:'size'">字段长度</th>
                        <th data-options="width:80,align:'left',hidden:false,field:'width'">精度</th>
                        <th data-options="width:60,align:'left',hidden:false,field:'decimalDigits'">小数位数</th>
                        <th data-options="width:60,align:'center',hidden:false,field:'notNull',formatter:pageJsObject.booleanFormatter">不能为空</th>
                        <th data-options="width:60,align:'center',hidden:false,field:'autoIncrement',formatter:pageJsObject.booleanFormatter">自增长</th>
                        <th data-options="width:60,align:'center',hidden:false,field:'partOfPrimaryKey',formatter:pageJsObject.booleanFormatter">是主键</th>
                        <th data-options="width:60,align:'center',hidden:false,field:'partOfForeignKey',formatter:pageJsObject.booleanFormatter">是外键</th>
                        <th data-options="width:60,align:'center',hidden:false,field:'partOfUniqueIndex',formatter:pageJsObject.booleanFormatter">唯一约束</th>
                        <th data-options="width:60,align:'center',hidden:false,field:'partOfIndex',formatter:pageJsObject.booleanFormatter">建了索引</th>
                        <th data-options="width:100,align:'left',hidden:false,field:'defaultValue'">默认值</th>
                        <th data-options="width:100,align:'left',hidden:false,field:'extra'">extra</th>
                        <th data-options="width:260,align:'left',hidden:false,field:'comment'">描述信息</th>
                        <th data-options="width:100,align:'left',hidden:true,field:'generated'">generated</th>
                        <th data-options="width:100,align:'left',hidden:true,field:'hidden'">hidden</th>
                        <th data-options="width:100,align:'left',hidden:true,field:'charset'">charset</th>
                        <th data-options="width:100,align:'left',hidden:true,field:'collation'">collation</th>
                        <th data-options="width:100,align:'left',hidden:true,field:'attributes'">attributes</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>

    <div title="模版选择" style="background-color:#E0ECFF;">
        <table id="codeTemplateDataTable" data-options="border:false,fit:true">
            <thead>
            <tr>
                <th data-options="width:210,align:'left',hidden:false,field:'name'">代码模版名称</th>
                <th data-options="width:100,align:'left',hidden:false,field:'nodeType'">节点类型</th>
                <th data-options="width:100,align:'left',hidden:true,field:'templateRef'">脚本模版引用</th>
                <th data-options="width:100,align:'left',hidden:false,field:'codeType'">模版代码语言</th>
                <th data-options="width:650,align:'left',hidden:false,field:'description'">模版说明</th>
                <th data-options="width:350,align:'left',hidden:false,field:'remarks'">备注信息</th>
            </tr>
            </thead>
        </table>
        <div id="codeTemplateDataTableButtons">
            <a id="codeTemplateDataTableButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
            <a id="codeTemplateDataTableButtonsDel" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">移除</a>
        </div>
    </div>
</div>


<%-- 选择模版代码对话框 --%>
<div id="selectCodeTemplateDialog" style="width: 300px;height: 130px;padding: 15px 20px">
    <span>
        <label for="codeTemplateID">所属分类</label>
        <input id="codeTemplateID"/>
    </span>
</div>
<div id="selectCodeTemplateDialogButtons">
    <a id="selectCodeTemplateDialogButtonsOk" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">选择</a>
    <a id="selectCodeTemplateDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

<%-- 查看模版代码对话框 --%>
<div id="viewCodeTemplateDialog" style="width: 850px;height: 300px;padding: 5px 10px">

</div>
<div id="viewCodeTemplateDialogButtons">
    <a id="viewCodeTemplateDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
</div>

</body>
</html>
