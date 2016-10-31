/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";
    // 分页查询用户信息
    var findByPageUrl = globalPath.mvcPath + "/sys/user/findByPage.json";
    // 增加用户
    var addUserUrl = globalPath.mvcPath + "/sys/user/addUser.json";
    // 更新用户
    var updateUserUrl = globalPath.mvcPath + "/sys/user/updateUser.json";
    // 删除用户(软删除)
    var deleteUserUrl = globalPath.mvcPath + "/sys/user/deleteUser.json";

    // 查询表单
    var searchForm = $("#searchForm");
    // 数据表格
    var dataTable = $("#dataTable");
    // 数据表格 - 查询
    var dataTableButtonsSearch = $("#dataTableButtonsSearch");
    // 数据表格 - 新增
    var dataTableButtonsAdd = $("#dataTableButtonsAdd");
    // 数据表格 - 编辑
    var dataTableButtonsEdit = $("#dataTableButtonsEdit");
    // 数据表格 - 删除
    var dataTableButtonsDel = $("#dataTableButtonsDel");

    // 新增对话框
    var addDialog = $("#addDialog");
    // 新增对话框 - 保存
    var addDialogButtonsSave = $("#addDialogButtonsSave");
    // 新增对话框 - 取消
    var addDialogButtonsCancel = $("#addDialogButtonsCancel");

    // 新增用户表单
    var addForm = $("#addForm");
    // 新增用户表单 - 工号
    var addJobNo = $("#addJobNo");
    // 新增用户表单 - 归属公司
    var addHomeCompany = $("#addHomeCompany");
    // 新增用户表单 - 直属机构
    var addHomeOrg = $("#addHomeOrg");
    // 新增用户表单 - 登录名
    var addLoginName = $("#addLoginName");
    // 新增用户表单 - 密码
    var addPassword = $("#addPassword");
    // 新增用户表单 - 用户姓名
    var addName = $("#addName");
    // 新增用户表单 - 性别
    var addSex = $("#addSex");
    // 新增用户表单 - 生日
    var addBirthday = $("#addBirthday");
    // 新增用户表单 - 邮箱
    var addEmail = $("#addEmail");
    // 新增用户表单 - 电话
    var addPhone = $("#addPhone");
    // 新增用户表单 - 手机
    var addMobile = $("#addMobile");
    // 新增用户表单 - 帐号状态
    var addAccountState = $("#addAccountState");
    // 新增用户表单 - 用户状态
    var addUserState = $("#addUserState");
    // 新增用户表单 - 用户类型
    var addUserType = $("#addUserType");
    // 新增用户表单 - 备注信息
    var addRemarks = $("#addRemarks");

    // 编辑对话框
    var editDialog = $("#editDialog");
    // 编辑对话框 - 保存
    var editDialogButtonsSave = $("#editDialogButtonsSave");
    // 编辑对话框 - 取消
    var editDialogButtonsCancel = $("#editDialogButtonsCancel");

    // 编辑用户表单
    var editForm = $("#editForm");
    // 编辑用户表单 - 工号
    var editJobNo = $("#editJobNo");
    // 编辑用户表单 - 归属公司
    var editHomeCompany = $("#editHomeCompany");
    // 编辑用户表单 - 直属机构
    var editHomeOrg = $("#editHomeOrg");
    // 编辑用户表单 - 登录名
    var editLoginName = $("#editLoginName");
    // 编辑用户表单 - 密码
    var editPassword = $("#editPassword");
    // 编辑用户表单 - 删除标记
    var editDelFlag = $("#editDelFlag");
    // 编辑用户表单 - 用户姓名
    var editName = $("#editName");
    // 编辑用户表单 - 性别
    var editSex = $("#editSex");
    // 编辑用户表单 - 生日
    var editBirthday = $("#editBirthday");
    // 编辑用户表单 - 邮箱
    var editEmail = $("#editEmail");
    // 编辑用户表单 - 电话
    var editPhone = $("#editPhone");
    // 编辑用户表单 - 手机
    var editMobile = $("#editMobile");
    // 编辑用户表单 - 帐号状态
    var editAccountState = $("#editAccountState");
    // 编辑用户表单 - 用户状态
    var editUserState = $("#editUserState");
    // 编辑用户表单 - 用户类型
    var editUserType = $("#editUserType");
    // 编辑用户表单 - 备注信息
    var editRemarks = $("#editRemarks");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        _this.addDialogInit();
        _this.editDialogInit();
        _this.dataBind();
        _this.eventBind();
    };

    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
            url: findByPageUrl,
            idField: 'id',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: true,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#dataTableButtons",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {
                _this.openEditDialog();
            },
            onBeforeLoad: function (param) {
                // 增加查询参数
                var paramArray = searchForm.serializeArray();
                $(paramArray).each(function () {
                    if (param[this.name]) {
                        if ($.isArray(param[this.name])) {
                            param[this.name].push(this.value);
                        } else {
                            param[this.name] = [param[this.name], this.value];
                        }
                    } else {
                        param[this.name] = this.value;
                    }
                });
            }
        });
    };

    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        dataTableButtonsSearch.click(function () {
            dataTable.datagrid("load");
        });

        dataTableButtonsAdd.click(function () {
            addDialog.dialog("open");
            addForm.form('reset');
        });

        dataTableButtonsEdit.click(function () {
            _this.openEditDialog();
        });

        dataTableButtonsDel.click(function () {
            _this.deleteUser();
        });

        addDialogButtonsSave.click(function () {
            _this.addUser();
        });

        addDialogButtonsCancel.click(function () {
            addDialog.dialog("close");
        });

        editDialogButtonsSave.click(function () {
            _this.updateUser();
        });

        editDialogButtonsCancel.click(function () {
            editDialog.dialog("close");
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    // 初始化增加用户对话框
    this.addDialogInit = function () {
        addDialog.dialog({
            title: "新增用户",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 820,
            minHeight: 320,
            modal: true,
            buttons: "#addDialogButtons"
        });

        addJobNo.textbox({
            required: true,
            validType: 'length[0,30]'
        });
        addHomeCompany.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addHomeOrg.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addLoginName.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addPassword.textbox({
            required: true,
            validType: 'length[0,32]',
            type: 'password'
        });
        editDelFlag.textbox({
            required: true,
            validType: 'length[0,1]'
        });
        addName.textbox({
            required: true,
            validType: 'length[0,100]'
        });
        addSex.textbox({
            required: true,
            validType: 'length[0,1]'
        });
        addBirthday.datebox({
            required: true
        });
        addEmail.textbox({
            required: false,
            validType: 'email'
        });
        addPhone.textbox({
            required: false,
            validType: 'length[0,100]'
        });
        addMobile.textbox({
            required: false,
            validType: 'length[0,100]'
        });
        addAccountState.textbox({
            required: true,
            validType: 'length[0,1]'
        });
        addUserState.textbox({
            required: true,
            validType: 'length[0,1]'
        });
        addUserType.textbox({
            required: true,
            validType: 'length[0,1]'
        });
        addRemarks.textbox({
            required: true,
            validType: 'length[0,255]',
            multiline: true
        });
    };

    // 保存用户数据
    this.addUser = function () {
        addForm.form("submit", {
            url: addUserUrl,
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    addDialog.dialog('close');
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                    dataTable.datagrid('reload');
                } else {
                    // 保存失败
                }
            }
        });
    };

    // 初始化编辑用户对话框
    this.editDialogInit = function () {
        editDialog.dialog({
            title: "编辑用户",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 820,
            minHeight: 320,
            modal: true,
            buttons: "#editDialogButtons"
        });

        editJobNo.textbox({
            required: true,
            validType: 'length[0,30]'
        });
        editHomeCompany.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        editHomeOrg.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        editLoginName.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        editPassword.textbox({
            required: true,
            validType: 'length[0,32]',
            type: 'password'
        });
        editName.textbox({
            required: true,
            validType: 'length[0,100]'
        });
        editSex.textbox({
            required: true,
            validType: 'length[0,1]'
        });
        editBirthday.datebox({
            required: true
        });
        editEmail.textbox({
            required: false,
            validType: 'email'
        });
        editPhone.textbox({
            required: false,
            validType: 'length[0,100]'
        });
        editMobile.textbox({
            required: false,
            validType: 'length[0,100]'
        });
        editAccountState.textbox({
            required: true,
            validType: 'length[0,1]'
        });
        editUserState.textbox({
            required: true,
            validType: 'length[0,1]'
        });
        editUserType.textbox({
            required: true,
            validType: 'length[0,1]'
        });
        editRemarks.textbox({
            required: true,
            validType: 'length[0,255]',
            multiline: true
        });
    };

    // 更新用户数据
    this.updateUser = function () {
        editForm.form("submit", {
            url: updateUserUrl,
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    editDialog.dialog('close');
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                    dataTable.datagrid('reload');
                } else {
                    // 保存失败
                }
            }
        });
    };

    // 打开编辑对话框
    this.openEditDialog = function () {
        var row = dataTable.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提示", "请选择要编辑的数据！", "info");
            return;
        }
        if (row) {
            editDialog.dialog('open');
            editForm.form('load', row);
        }
    };

    // 删除用户
    this.deleteUser = function () {
        var row = dataTable.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提示", "请选择要删除的数据！", "info");
            return;
        }
        $.messager.confirm("确认删除", "您确定删除用户信息?<br/>" + row.loginName, function (r) {
            if (r) {
                $.post(deleteUserUrl, row, function (data) {
                    if (data.success) {
                        // 删除成功
                        $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        dataTable.datagrid('reload');
                    } else {
                        // 删除失败
                    }
                }, "json");
            }
        });
    };
};

/**
 * 页面Js对象
 */
var pageJsObject = null;
/**
 * 页面Js执行入口
 */
$(document).ready(function () {
    if (typeof(globalPath) == "undefined") {
        alert("系统全局路径对象未定义(globalPath)");
    } else {
        pageJsObject = new pageJs(globalPath);
        pageJsObject.init();
    }
});