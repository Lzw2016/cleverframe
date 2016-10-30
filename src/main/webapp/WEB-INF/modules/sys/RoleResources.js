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
    // 分页查询系统资源
    var findByResourcesPageUrl = globalPath.mvcPath + "/sys/resources/findByPage.json";
    // 查询角色资源数据 (不分页)
    var findResourcesByRoleUrl = globalPath.mvcPath + "/sys/role/findResourcesByRole.json";
    // 角色添加资源
    var addRoleResourcesUrl = globalPath.mvcPath + "/sys/role/addRoleResources.json";
    // 角色移除资源
    var deleteRoleResourcesUrl = globalPath.mvcPath + "/sys/role/deleteRoleResources.json";

    // 查询表单
    var searchForm = $("#searchForm");
    // 查询表单 - 角色名称
    var searchName = $("#searchName");

    // 系统角色信息表格
    var dataTable_1 = $("#dataTable_1");
    // 系统角色信息表格 - 查询
    var dataTableButtonsSearch_1 = $("#dataTableButtonsSearch_1");

    // 角色拥有的资源信息表格
    var dataTable_2 = $("#dataTable_2");
    // 角色拥有的资源信息表格 - 刷新
    var dataTableButtonsReload_2 = $("#dataTableButtonsReload_2");
    // 角色拥有的资源信息表格 - 添加资源
    var dataTableButtonsAdd_2 = $("#dataTableButtonsAdd_2");
    // 角色拥有的资源信息表格 - 移除资源
    var dataTableButtonsDel_2 = $("#dataTableButtonsDel_2");
    // 选择的角色文本
    var selectRoleText = $("#selectRoleText");

    // 选择依赖资源对话框
    var selectResourcesDialog = $("#selectResourcesDialog");
    // 选择依赖资源对话框 - 确定
    var selectResourcesDialogButtonsOK = $("#selectResourcesDialogButtonsOK");
    // 选择依赖资源对话框 - 取消
    var selectResourcesDialogButtonsCancel = $("#selectResourcesDialogButtonsCancel");
    // 选择查询表单
    var selectForm = $("#selectForm");
    // 选择查询表单 - 资源类型
    var selectResourcesType = $("#selectResourcesType");
    // 选择查询表单 - 资源类型
    var selectTitle = $("#selectTitle");
    // 选择查询表单 - 资源类型
    var selectResourcesUrl = $("#selectResourcesUrl");
    // 选择查询表单 - 资源类型
    var selectPermission = $("#selectPermission");
    // 资源选择表格
    var dataTable_3 = $("#dataTable_3");
    // 资源选择表格 - 查询
    var dataTableButtonsSearch_3 = $("#dataTableButtonsSearch_3");
    // 控制 dataTable_3 初始化时不加载数据
    var dataTable_3_Load = false;

    // 当前选择的角色
    var selectRole = null;
    // 资源类型 枚举
    var resourcesTypeArray = null;
    // 删除标记数据
    var delFlagArray = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        _this.selectResourcesDialogInit();
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

        $.ajax({
            type: "POST", dataType: "JSON", url: findDictTypeUrl + encodeURIComponent("删除标记"), async: false,
            success: function (data) {
                delFlagArray = data;
            }
        });

        $.ajax({
            type: "POST", dataType: "JSON", data: {}, async: false, url: findDictTypeUrl + encodeURIComponent("资源类型"),
            success: function (data) {
                resourcesTypeArray = data;
            }
        });
    };

    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        dataTableButtonsSearch_1.click(function () {
            dataTable_1.datagrid("load");
        });

        dataTableButtonsReload_2.click(function () {
            if (selectRole != null) {
                _this.setSelectRole(selectRole);
            }
        });

        dataTableButtonsAdd_2.click(function () {
            if (selectRole == null) {
                $.messager.alert("提示", "请先选择角色！", "info");
                return;
            }
            selectResourcesDialog.dialog("open");
            dataTable_3.datagrid("load");
        });

        dataTableButtonsDel_2.click(function () {
            _this.deleteRoleResources();
        });

        selectResourcesDialogButtonsOK.click(function () {
            _this.addRoleResources();
        });

        selectResourcesDialogButtonsCancel.click(function () {
            selectResourcesDialog.dialog("close");
        });

        dataTableButtonsSearch_3.click(function () {
            dataTable_3.datagrid("load");
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    // 选择依赖资源对话框 初始化
    this.selectResourcesDialogInit = function () {
        selectResourcesDialog.dialog({
            title: "为角色添加资源",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            // minWidth: 830,
            // minHeight: 330,
            modal: true,
            buttons: "#selectResourcesDialogButtons"
        });

        dataTable_3.datagrid({
            url: findByResourcesPageUrl,
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
                _this.addRoleResources();
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

    // 设置当前选择的角色
    this.setSelectRole = function (role) {
        selectRole = role;
        selectRoleText.text(selectRole.name);
        dataTable_2.datagrid("loading");
        //noinspection JSUnusedLocalSymbols
        $.ajax({
            type: "POST", dataType: "JSON", data: {"id": selectRole.id}, async: true, url: findResourcesByRoleUrl,
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

    // 增加角色资源
    this.addRoleResources = function () {
        var row = dataTable_3.datagrid('getSelected');
        if (selectRole == null || row == null) {
            $.messager.alert("提示", "请选择角色和资源信息！", "info");
            return;
        }
        $.post(addRoleResourcesUrl, {"resourcesId": row.id, "roleId": selectRole.id}, function (data) {
            if (data.success) {
                selectResourcesDialog.dialog("close");
                $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                _this.setSelectRole(selectRole);
            }
        }, "json");
    };

    // 角色移除资源
    this.deleteRoleResources = function () {
        var row = dataTable_2.datagrid('getSelected');
        if (selectRole == null || row == null) {
            $.messager.alert("提示", "请选择角色和资源信息！", "info");
            return;
        }
        $.messager.confirm("确认移除", "您确定移除资源?<br/>角色:" + selectRole.name + "<br/>资源:" + row.title, function (r) {
            if (r) {
                $.post(deleteRoleResourcesUrl, {"resourcesId": row.id, "roleId": selectRole.id}, function (data) {
                    if (data.success) {
                        // 删除成功
                        $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        _this.setSelectRole(selectRole);
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

    // 格式化
    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.resourcesTypeFormatter = function (value, rowData, rowIndex) {
        var result = "";
        $(resourcesTypeArray).each(function (index, data) {
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