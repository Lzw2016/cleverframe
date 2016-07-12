<%--suppress HtmlUnknownTarget --%>
<%--
  作者: LiZW
  时间: 2016-6-17 11:10
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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/core/Config.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/core/Config.js"></script>
    <%-- 页面标题 --%>
    <title>系统配置管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:110px;">
    <form id="searchForm" method="post">
        <div class="row">
            <span class="columnLast">
                <label for="searchConfigKey">配置键</label>
                <input id="searchConfigKey" name="configKey" class="easyui-textbox" style="width: 645px">
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="searchConfigValue">配置数据值</label>
                <input id="searchConfigValue" name="configValue" class="easyui-textbox" style="width: 645px">
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="searchConfigGroup">配置组名称</label>
                <input id="searchConfigGroup" name="configGroup" class="easyui-textbox">
            </span>
            <span class="column">
                <label for="searchHotSwap">热配置生效</label>
                <input id="searchHotSwap" name="hotSwap" class="easyui-combobox">
            </span>
            <span class="columnLast">
                <label for="searchDelFlag">删除标记</label>
                <input id="searchDelFlag" name="delFlag" class="easyui-combobox">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:false">
    <table id="dataTable" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:150,align:'left',hidden:false,field:'configGroup'">配置组名称</th>
            <th data-options="width:350,align:'left',hidden:false,field:'configKey'">配置键</th>
            <th data-options="width:450,align:'left',hidden:false,field:'configValue'">配置数据值</th>
            <th data-options="width:80,align:'left',hidden:false,field:'hotSwap',formatter:pageJsObject.hotSwapFormatter">热配置生效</th>
            <th data-options="width:160,align:'left',hidden:false,field:'description'">配置描述</th>

            <th data-options="width:30 ,align:'left',hidden:true ,field:'id'">编号</th>
            <th data-options="width:80 ,align:'left',hidden:true ,field:'companyCode'">公司编码</th>
            <th data-options="width:80 ,align:'left',hidden:true ,field:'orgCode'">机构编码</th>
            <th data-options="width:50 ,align:'left',hidden:true ,field:'createBy'">创建者</th>
            <th data-options="width:130,align:'left',hidden:false,field:'createDate'">创建时间</th>
            <th data-options="width:50 ,align:'left',hidden:true ,field:'updateBy'">更新者</th>
            <th data-options="width:130,align:'left',hidden:false,field:'updateDate'">更新时间</th>
            <th data-options="width:100,align:'left',hidden:false,field:'remarks'">备注信息</th>
            <th data-options="width:80 ,align:'left',hidden:false,field:'delFlag',formatter:pageJsObject.delFlagFormatter">删除标记</th>
            <th data-options="width:80 ,align:'left',hidden:true ,field:'uuid'">UUID</th>

            <th data-options="width:50,align:'left',hidden:false,field:'sort'">排序</th>
        </tr>
        </thead>
    </table>
    <div id="dataTableButtons">
        <a id="dataTableButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
        <a id="dataTableButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
        <a id="dataTableButtonsEdit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
        <a id="dataTableButtonsDel" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </div>
</div>

<%-- 新增对话框 --%>
<div id="addDialog" style="width: 850px;height: 330px;padding: 5px 10px">
    <form id="addForm" method="post">
        <div class="row">
            <span class="columnLast">
                <label for="addConfigKey">配置键</label>
                <input id="addConfigKey" name="configKey" style="width: 700px"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="addConfigValue">配置数据值</label>
                <input id="addConfigValue" name="configValue" style="width: 700px"/>
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="addConfigGroup">配置组名称</label>
                <input id="addConfigGroup" name="configGroup"/>
            </span>
            <span class="column">
                <label for="addHotSwap">热配置生效</label>
                <input id="addHotSwap" name="hotSwap"/>
            </span>
            <span class="columnLast">
                <label for="addSort">排序</label>
                <input id="addSort" name="sort"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="addDescription">配置描述</label>
                <input id="addDescription" name="description" style="width: 700px;height: 80px;"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="addRemarks">备注信息</label>
                <input id="addRemarks" name="remarks" style="width: 700px;height: 50px;"/>
            </span>
        </div>
    </form>
</div>
<div id="addDialogButtons">
    <a id="addDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="addDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

<%-- 编辑对话框 --%>
<div id="editDialog" style="width: 850px;height: 360px;padding: 5px 10px">
    <form id="editForm" method="post">
        <input id="editId" name="id" type="hidden"/>

        <div class="row">
            <span class="columnLast">
                <label for="editConfigKey">配置键</label>
                <input id="editConfigKey" name="configKey" style="width: 700px"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="editConfigValue">配置数据值</label>
                <input id="editConfigValue" name="configValue" style="width: 700px"/>
            </span>
        </div>

        <div class="row">
            <span class="column">
                <label for="editConfigGroup">配置组名称</label>
                <input id="editConfigGroup" name="configGroup"/>
            </span>
            <span class="columnLast">
                <label for="editHotSwap">热配置生效</label>
                <input id="editHotSwap" name="hotSwap"/>
            </span>
        </div>

        <div class="row">
            <span class="column">
               <label for="editSort">排序</label>
                <input id="editSort" name="sort"/>
            </span>
            <span class="columnLast">
                <label for="editDelFlag">删除标记</label>
                <input id="editDelFlag" name="delFlag"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="editDescription">配置描述</label>
                <input id="editDescription" name="description" style="width: 700px;height: 80px;"/>
            </span>
        </div>

        <div class="row">
            <span class="columnLast">
                <label for="editRemarks">备注信息</label>
                <input id="editRemarks" name="remarks" style="width: 700px;height: 50px;"/>
            </span>
        </div>
    </form>
</div>
<div id="editDialogButtons">
    <a id="editDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
    <a id="editDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

</body>
</html>