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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/generator/TableSchema.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/generator/TableSchema.js"></script>
    <%-- 页面标题 --%>
    <title>数据库表结构</title>
</head>
<body id="mainPanel" class="easyui-layout">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:40px;background-color:#E0ECFF;">
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
<div id="centerPanel" data-options="region:'center',border:false">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
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

</body>
</html>
