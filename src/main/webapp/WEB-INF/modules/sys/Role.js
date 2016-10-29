/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";
    // 分页查询角色信息
    var findByPageUrl = globalPath.mvcPath + "/sys/role/findByPage.json";
    // 新增角色
    var addRoleUrl = globalPath.mvcPath + "/sys/role/addRole.json";
    // 更新角色
    var updateRoleUrl = globalPath.mvcPath + "/sys/role/updateRole.json";
    // 删除角色
    var deleteRoleUrl = globalPath.mvcPath + "/sys/role/deleteRole.json";

    // 查询表单
    var searchForm = $("#searchForm");
    // 查询表单 - 角色名称
    var searchName = $("#searchName");

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

    // 新增角色对话框
    var addDialog = $("#addDialog");
    // 新增角色对话框 - 保存
    var addDialogButtonsSave = $("#addDialogButtonsSave");
    // 新增角色对话框 - 取消
    var addDialogButtonsCancel = $("#addDialogButtonsCancel");

    // 新增角色表单
    var addForm = $("#addForm");
    // 新增角色表单 - 角色名称
    var addName = $("#addName");
    // 新增角色表单 - 角色说明
    var addDescription = $("#addDescription");
    // 新增角色表单 - 备注信息
    var addRemarks = $("#addRemarks");

    // 编辑对话框
    var editDialog = $("#editDialog");
    // 编辑对话框 - 保存
    var editDialogButtonsSave = $("#editDialogButtonsSave");
    // 编辑对话框 - 编辑
    var editDialogButtonsCancel = $("#editDialogButtonsCancel");

    // 编辑角色表单
    var editForm = $("#editForm");
    // 编辑角色表单 - 角色名称
    var editName = $("#editName");
    // 编辑角色表单 - 角色说明
    var editDescription = $("#editDescription");
    // 编辑角色表单 - 备注信息
    var editRemarks = $("#editRemarks");

    // 删除标记数据
    var delFlagArray = null;

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

        $.ajax({
            type: "POST", dataType: "JSON", url: findDictTypeUrl + encodeURIComponent("删除标记"), async: false,
            success: function (data) {
                delFlagArray = data;
            }
        });
    };

    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        dataTableButtonsSearch.click(function () {
            dataTable.datagrid('load');
        });

        dataTableButtonsAdd.click(function () {
            addDialog.dialog("open");
            addForm.form('reset');
        });

        dataTableButtonsEdit.click(function () {
            _this.openEditDialog();
        });

        dataTableButtonsDel.click(function () {
            _this.delRole();
        });

        addDialogButtonsSave.click(function () {
            _this.addRole();
        });

        addDialogButtonsCancel.click(function () {
            addDialog.dialog("close");
        });

        editDialogButtonsSave.click(function () {
            _this.updateRole();
        });

        editDialogButtonsCancel.click(function () {
            editDialog.dialog("close");
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    // 初始化新增对话框
    this.addDialogInit = function () {
        addDialog.dialog({
            title: "新增角色",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 630,
            minHeight: 300,
            modal: true,
            buttons: "#addDialogButtons"
        });
        addName.textbox({
            required: true,
            validType: 'length[0,50]'
        });
        addRemarks.textbox({
            required: true,
            validType: 'length[0,255]',
            multiline: true
        });
        addDescription.textbox({
            required: true,
            validType: 'length[0,2000]',
            multiline: true
        });
    };

    // 保存角色
    this.addRole = function () {
        addForm.form("submit", {
            url: addRoleUrl,
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

    // 编辑对话框
    this.editDialogInit = function () {
        editDialog.dialog({
            title: "编辑角色",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 630,
            minHeight: 300,
            modal: true,
            buttons: "#editDialogButtons"
        });
        editName.textbox({
            required: true,
            validType: 'length[0,50]'
        });
        editDescription.textbox({
            required: true,
            validType: 'length[0,255]',
            multiline: true
        });
        editRemarks.textbox({
            required: true,
            validType: 'length[0,2000]',
            multiline: true
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

    // 更新角色
    this.updateRole = function () {
        editForm.form("submit", {
            url: updateRoleUrl,
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

    // 删除角色
    this.delRole = function () {
        var row = dataTable.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提示", "请选择要删除的数据！", "info");
            return;
        }
        $.messager.confirm("确认删除", "您确定删除资源信息?<br/>" + row.name, function (r) {
            if (r) {
                $.post(deleteRoleUrl, row, function (data) {
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

    // 删除标记格式化
    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.delFlagFormatter = function (value, rowData, rowIndex) {
        var result = "";
        $(delFlagArray).each(function (index, data) {
            if (data.value == value) {
                result = data.text;
                return false;
            }
        });
        return result == "" ? value : result;
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