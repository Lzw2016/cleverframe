/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";
    // 分页查询用户信息
    var findUserByPageUrl = globalPath.mvcPath + "/sys/user/findByPage.json";
    // 分页查询角色信息
    var findRoleByPageUrl = globalPath.mvcPath + "/sys/role/findByPage.json";
    // 查询用户的所有数据 (不分页)
    var findRoleByUserUrl = globalPath.mvcPath + "/sys/user/findRoleByUser.json";
    // 为用户增加一个角色
    var addUserRoleUrl = globalPath.mvcPath + "/sys/user/addUserRole.json";
    // 移除用户角色
    var deleteUserRoleUrl = globalPath.mvcPath + "/sys/user/deleteUserRole.json";

    // 查询表单
    var searchForm = $("#searchForm");
    // 查询表单 - 登录名称
    var searchLoginName = $("#searchLoginName");

    // 用户数据表
    var dataTable_1 = $("#dataTable_1");
    // 用户数据表 - 查询
    var dataTableButtonsSearch_1 = $("#dataTableButtonsSearch_1");

    // 角色数据表
    var dataTable_2 = $("#dataTable_2");
    // 角色数据表 - 查询
    var dataTableButtonsSearch_2 = $("#dataTableButtonsSearch_2");
    // 角色数据表 - 增加
    var dataTableButtonsAdd_2 = $("#dataTableButtonsAdd_2");
    // 角色数据表 - 移除
    var dataTableButtonsDel_2 = $("#dataTableButtonsDel_2");
    // 显示选择的用户Label
    var selectUserText = $("#selectUserText");

    // 选择角色对话框
    var selectRoleDialog = $("#selectRoleDialog");
    // 选择角色对话框 - 确定
    var selectRoleDialogButtonsOK = $("#selectRoleDialogButtonsOK");
    // 选择角色对话框 - 取消
    var selectRoleDialogButtonsCancel = $("#selectRoleDialogButtonsCancel");
    // 选择角色对话框 - 查询表单
    var selectForm = $("#selectForm");
    // 选择角色对话框 - 查询表单 - 角色名称
    var selectName = $("#selectName");
    // 选择角色对话框 - 数据表格
    var dataTable_3 = $("#dataTable_3");
    // 选择角色对话框 - 数据表格 - 查询按钮
    var dataTableButtonsSearch_3 = $("#dataTableButtonsSearch_3");
    // 控制 dataTable_3 初始化时不加载数据
    var dataTable_3_Load = false;

    // 当前选中的用户
    var selectUser = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        _this.selectRoleDialogInit();
        _this.dataBind();
        _this.eventBind();
    };

    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable_1.datagrid({
            url: findUserByPageUrl,
            idField: 'id',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: true,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#dataTableButtons_1",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onSelect: function (rowIndex, rowData) {
                _this.setSelectRole(rowData);
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

        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable_2.datagrid({
            idField: 'id',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#dataTableButtons_2",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150]
        });
    };

    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        dataTableButtonsSearch_1.click(function () {
            dataTable_1.datagrid("load");
        });

        dataTableButtonsSearch_2.click(function () {
            _this.setSelectRole(selectUser);
        });

        dataTableButtonsAdd_2.click(function () {
            if (selectUser == null) {
                $.messager.alert("提示", "请先选择用户！", "info");
                return;
            }
            selectRoleDialog.dialog("open");
            dataTable_3.datagrid("load");
        });

        dataTableButtonsDel_2.click(function () {
            _this.deleteUserRole();
        });

        dataTableButtonsSearch_3.click(function () {
            dataTable_3.datagrid("load");
        });

        selectRoleDialogButtonsOK.click(function () {
            _this.addUserRole();
        });

        selectRoleDialogButtonsCancel.click(function () {
            selectRoleDialog.dialog("close");
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    // 选择依赖资源对话框 初始化
    this.selectRoleDialogInit = function () {
        selectRoleDialog.dialog({
            title: "为用户添加角色",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            // minWidth: 830,
            // minHeight: 330,
            modal: true,
            buttons: "#selectRoleDialogButtons"
        });

        //noinspection JSUnusedLocalSymbols
        dataTable_3.datagrid({
            url: findRoleByPageUrl,
            idField: 'id',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: true,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#dataTableButtons_3",
            pageSize: 20,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (index, row) {
                _this.addUserRole();
            },
            onBeforeLoad: function (param) {
                // 初始化时不加载数据
                if (dataTable_3_Load == false) {
                    dataTable_3_Load = true;
                    return false;
                }
                // 增加查询参数
                var paramArray = selectForm.serializeArray();
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

    // 根据用户名查询角色
    this.setSelectRole = function (user) {
        selectUser = user;
        selectUserText.text(selectUser.loginName);
        dataTable_2.datagrid("loading");
        //noinspection JSUnusedLocalSymbols
        $.ajax({
            type: "POST", dataType: "JSON", data: {"id": selectUser.id}, async: true, url: findRoleByUserUrl,
            success: function (data) {
                if (data.success) {
                    dataTable_2.datagrid("loadData", data.result);
                }
            },
            complete: function (xhr, ts) {
                dataTable_2.datagrid("loaded");
            }
        });
    };

    // 角色移除资源
    this.deleteUserRole = function () {
        var row = dataTable_2.datagrid('getSelected');
        if (selectUser == null || row == null) {
            $.messager.alert("提示", "请选择用户和角色信息！", "info");
            return;
        }
        $.messager.confirm("确认移除", "您确定移除角色?<br/>用户:" + selectUser.loginName + "<br/>角色:" + row.name, function (r) {
            if (r) {
                $.post(deleteUserRoleUrl, {"roleId": row.id, "userId": selectUser.id}, function (data) {
                    if (data.success) {
                        // 删除成功
                        $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        _this.setSelectRole(selectUser);
                    } else {
                        // 删除失败
                    }
                }, "json");
            }
        });
    };

    // 增加角色资源
    this.addUserRole = function () {
        var row = dataTable_3.datagrid('getSelected');
        if (selectUser == null || row == null) {
            $.messager.alert("提示", "请选择用户和角色信息！", "info");
            return;
        }
        $.post(addUserRoleUrl, {"roleId": row.id, "userId": selectUser.id}, function (data) {
            if (data.success) {
                selectRoleDialog.dialog("close");
                $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                _this.setSelectRole(selectUser);
            }
        }, "json");
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