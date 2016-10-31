<%--
  Created by IntelliJ IDEA.
  User: LiZW
  Date: 2016/10/30
  Time: 15:25
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
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/sys/User.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/sys/User.js"></script>
    <title>系统用户管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<%-- 页面上部 --%>
<div data-options="region:'north',border:true,minWidth:800" style="height:40px;">
    <form id="searchForm" method="post">
        <div class="row">
            <span class="columnLast">
                <label for="searchLoginName">角色名称</label>
                <input id="searchLoginName" name="loginName" class="easyui-textbox" style="width: 300px;">
            </span>
        </div>
    </form>
</div>

<%-- 页面中部 --%>
<div id="centerPanel" data-options="region:'center',border:false">
    <table id="dataTable" class="easyui-datagrid" data-options="border:false">
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
    <div id="dataTableButtons">
        <a id="dataTableButtonsSearch" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
        <a id="dataTableButtonsAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
        <a id="dataTableButtonsEdit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
        <a id="dataTableButtonsDel" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </div>
</div>

<%-- 新增对话框 --%>
<div id="addDialog" style="width: 820px;height: 320px;padding: 5px 10px">
    <form id="addForm" method="post">
        <div class="row">
			<span class="column">
				<label for="addJobNo">工号</label>
				<input id="addJobNo" name="jobNo">
			</span>
            <span class="column">
				<label for="addHomeCompany">归属公司</label>
				<input id="addHomeCompany" name="homeCompany">
			</span>
            <span class="columnLast">
				<label for="addHomeOrg">直属机构</label>
				<input id="addHomeOrg" name="homeOrg">
			</span>
        </div>
        <div class="row">
            <span class="column">
				<label for="addLoginName">登录名</label>
				<input id="addLoginName" name="loginName">
			</span>
            <span class="columnLast">
				<label for="addPassword">密码</label>
				<input id="addPassword" name="password" style="width: 420px;">
			</span>
        </div>

        <div class="row">
			<span class="column">
				<label for="addName">用户姓名</label>
				<input id="addName" name="name">
			</span>
			<span class="column">
				<label for="addSex">性别</label>
				<input id="addSex" name="sex">
			</span>
            <span class="columnLast">
				<label for="addBirthday">生日</label>
				<input id="addBirthday" name="birthday">
			</span>
        </div>

        <div class="row">
			<span class="column">
				<label for="addEmail">邮箱</label>
				<input id="addEmail" name="email">
			</span>
            <span class="column">
				<label for="addPhone">电话</label>
				<input id="addPhone" name="phone">
			</span>
            <span class="columnLast">
				<label for="addMobile">手机</label>
				<input id="addMobile" name="mobile">
			</span>
        </div>
        <div class="row">
             <span class="column">
                <label for="addAccountState">帐号状态</label>
                <input id="addAccountState" name="accountState">
            </span>
			<span class="column">
				<label for="addUserState">用户状态</label>
				<input id="addUserState" name="userState">
			</span>
            <span class="columnLast">
				<label for="addUserType">用户类型</label>
				<input id="addUserType" name="userType">
			</span>
        </div>
        <div class="row">
			<span class="columnLast">
				<label for="addRemarks">备注信息</label>
				<input id="addRemarks" name="remarks" style="width:685px;height:65px">
			</span>
        </div>
    </form>
</div>
<div id="addDialogButtons">
    <a id="addDialogButtonsSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
    <a id="addDialogButtonsCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
</div>

<%-- 编辑对话框 --%>
<div id="editDialog" style="width: 820px;height: 320px;padding: 5px 10px">
    <form id="editForm" method="post">
        <input id="editId" name="id" type="hidden"/>
        <div class="row">
			<span class="column">
				<label for="editJobNo">工号</label>
				<input id="editJobNo" name="jobNo">
			</span>
            <span class="column">
				<label for="editHomeCompany">归属公司</label>
				<input id="editHomeCompany" name="homeCompany">
			</span>
            <span class="columnLast">
				<label for="editHomeOrg">直属机构</label>
				<input id="editHomeOrg" name="homeOrg">
			</span>
        </div>
        <div class="row">
            <span class="column">
				<label for="editLoginName">登录名</label>
				<input id="editLoginName" name="loginName">
			</span>
            <span class="column">
				<label for="editPassword">密码</label>
				<input id="editPassword" name="password">
			</span>
            <span class="columnLast">
				<label for="editDelFlag">删除标记</label>
				<input id="editDelFlag" name="delFlag">
			</span>
        </div>

        <div class="row">
			<span class="column">
				<label for="editName">用户姓名</label>
				<input id="editName" name="name">
			</span>
            <span class="column">
				<label for="editSex">性别</label>
				<input id="editSex" name="sex">
			</span>
            <span class="columnLast">
				<label for="editBirthday">生日</label>
				<input id="editBirthday" name="birthday">
			</span>
        </div>

        <div class="row">
			<span class="column">
				<label for="editEmail">邮箱</label>
				<input id="editEmail" name="email">
			</span>
            <span class="column">
				<label for="editPhone">电话</label>
				<input id="editPhone" name="phone">
			</span>
            <span class="columnLast">
				<label for="editMobile">手机</label>
				<input id="editMobile" name="mobile">
			</span>
        </div>
        <div class="row">
             <span class="column">
                <label for="editAccountState">帐号状态</label>
                <input id="editAccountState" name="accountState">
            </span>
            <span class="column">
				<label for="editUserState">用户状态</label>
				<input id="editUserState" name="userState">
			</span>
            <span class="columnLast">
				<label for="editUserType">用户类型</label>
				<input id="editUserType" name="userType">
			</span>
        </div>
        <div class="row">
			<span class="columnLast">
				<label for="editRemarks">备注信息</label>
				<input id="editRemarks" name="remarks" style="width:685px;height:65px">
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
