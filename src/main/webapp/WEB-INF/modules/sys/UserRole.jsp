<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/10/31
  Time: 22:55
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

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/sys/UserRole.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/sys/UserRole.js"></script>
    <title>用户角色管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">

<%-- 页面上部 --%>
<div data-options="region:'north',border:true,split:false,collapsible:true" style="height:40px;">
    <form id="searchForm" method="post">
        <input id="editDelFlag" name="delFlag" type="hidden" value="1"/>
        <div class="row">
            <span class="columnLast">
                <label for="searchLoginName">角色名称</label>
                <input id="searchLoginName" name="loginName" class="easyui-textbox" style="width: 300px;">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div data-options="region:'center',border:true,title:'系统用户'" style="height: 65%;">
    <table id="dataTable_1" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:100,align:'left',hidden:true ,field:'id'">编号</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'companyCode'">数据所属公司的机构编码</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'orgCode'">数据直属机构的编码</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'createBy'">创建者</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'createDate'">创建时间</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'updateBy'">更新者</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'updateDate'">更新时间</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'uuid'">数据全局标识UUID</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'homeCompany'">归属公司</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'homeOrg'">直属机构</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'loginName'">登录名</th>
            <th data-options="width:100,align:'left',hidden:true ,field:'password'">密码</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'jobNo'">工号</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'name'">姓名</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'sex'">性别</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'birthday'">生日</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'email'">邮箱</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'phone'">电话</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'mobile'">手机</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'userType'">用户类型</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'loginIp'">最后登陆IP</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'loginDate'">最后登陆时间</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'accountState'">用户帐号状态</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'userState'">用户状态</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'remarks'">备注信息</th>
            <th data-options="width:100,align:'left',hidden:false ,field:'delFlag'">删除标记</th>
        </tr>
        </thead>
    </table>
    <div id="dataTableButtons_1">
        <a id="dataTableButtonsSearch_1" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
    </div>
</div>

<%-- 页面下部 --%>
<div data-options="region:'south',border:true,title:'用户拥有的角色',split:true,collapsible:false,minHeight:210" style="height:35%;">
    <table id="dataTable_2" data-options="border:false">
        <thead>
        <tr>
            <th data-options="width:100,align:'left',hidden:true  ,field:'id'">编号</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'companyCode'">数据所属公司的机构编码</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'orgCode'">数据直属机构的编码</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'createBy'">创建者</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'createDate'">创建时间</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'updateBy'">更新者</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'updateDate'">更新时间</th>
            <th data-options="width:100,align:'left',hidden:true  ,field:'uuid'">数据全局标识UUID</th>
            <th data-options="width:150,align:'left',hidden:false ,field:'name'">角色名称</th>
            <th data-options="width:550,align:'left',hidden:false ,field:'description'">角色说明</th>
            <th data-options="width:150,align:'left',hidden:false ,field:'remarks'">备注信息</th>
            <th data-options="width:80 ,align:'left',hidden:false ,field:'delFlag',formatter:pageJsObject.delFlagFormatter">删除标记</th>
        </tr>
        </thead>
    </table>
    <div id="dataTableButtons_2">
        <a id="dataTableButtonsSearch_2" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
        <a id="dataTableButtonsAdd_2" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加角色</a>
        <a id="dataTableButtonsDel_2" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">移除角色</a>
        <span style="margin-left: 50px;margin-top: 5px;">
                <label style="font-weight: bold;">选择的用户:</label>
                <label id="selectUserText" style="color: red;"></label>
        </span>
    </div>
</div>

<%--选择角色对话框--%>
<div id="selectRoleDialog" style="width: 800px;height: 450px;">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'north',border:true,split:false,collapsible:true,maxHeight:40,minHeight:40">
            <form id="selectForm" method="post">
                <div class="row">
                    <span class="columnLast">
                        <label for="selectName">角色名称</label>
                        <input id="selectName" name="name" class="easyui-textbox" style="width: 300px;">
                    </span>
                </div>
            </form>
        </div>
        <div data-options="region:'center',border:true">
            <table id="dataTable_3" data-options="border:true">
                <thead>
                <tr>
                    <th data-options="field:'check',checkbox:true">选择</th>
                    <th data-options="width:100,align:'left',hidden:true  ,field:'id'">编号</th>
                    <th data-options="width:100,align:'left',hidden:true  ,field:'companyCode'">数据所属公司的机构编码</th>
                    <th data-options="width:100,align:'left',hidden:true  ,field:'orgCode'">数据直属机构的编码</th>
                    <th data-options="width:100,align:'left',hidden:true  ,field:'createBy'">创建者</th>
                    <th data-options="width:100,align:'left',hidden:true  ,field:'createDate'">创建时间</th>
                    <th data-options="width:100,align:'left',hidden:true  ,field:'updateBy'">更新者</th>
                    <th data-options="width:100,align:'left',hidden:true  ,field:'updateDate'">更新时间</th>
                    <th data-options="width:100,align:'left',hidden:true  ,field:'uuid'">数据全局标识UUID</th>
                    <th data-options="width:150,align:'left',hidden:false ,field:'name'">角色名称</th>
                    <th data-options="width:350,align:'left',hidden:false ,field:'description'">角色说明</th>
                    <th data-options="width:150,align:'left',hidden:false ,field:'remarks'">备注信息</th>
                    <th data-options="width:80 ,align:'left',hidden:false ,field:'delFlag',formatter:pageJsObject.delFlagFormatter">删除标记</th>
                </tr>
                </thead>
            </table>
            <div id="dataTableButtons_3">
                <a id="dataTableButtonsSearch_3" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
            </div>
        </div>
    </div>
</div>
<div id="selectRoleDialogButtons">
    <a id="selectRoleDialogButtonsOK" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a>
    <a id="selectRoleDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
</div>

</body>
</html>
