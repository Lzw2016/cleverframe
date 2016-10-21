/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";
    // 分页查询系统资源
    var findByPageUrl = globalPath.mvcPath + "/sys/resources/findByPage.json";
    // 新增资源信息
    var addUrl = globalPath.mvcPath + "/sys/resources/addResources.json";
    // 更新资源信息
    var updateUrl = globalPath.mvcPath + "/sys/resources/updateResources.json";
    // 更新资源信息
    var deleteUrl = globalPath.mvcPath + "/sys/resources/deleteResources.json";

    // 查询表单
    var searchForm = $("#searchForm");
    // 资源类型
    var searchResourcesType = $("#searchResourcesType");
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
    // 新增表单
    var addForm = $("#addForm");
    // 资源标题
    var addTitle = $("#addTitle");
    // 资源URL
    var addResourcesUrl = $("#addResourcesUrl");
    // 权限标识
    var addPermission = $("#addPermission");
    // 资源类型
    var addResourcesType = $("#addResourcesType");
    // 资源说明
    var addDescription = $("#addDescription");

    // 编辑对话框
    var editDialog = $("#editDialog");
    // 编辑对话框 - 保存
    var editDialogButtonsSave = $("#editDialogButtonsSave");
    // 编辑对话框 - 取消
    var editDialogButtonsCancel = $("#editDialogButtonsCancel");
    // 编辑表单
    var editForm = $("#editForm");
    // 资源标题
    var editTitle = $("#editTitle");
    // 资源URL
    var editResourcesUrl = $("#editResourcesUrl");
    // 权限标识
    var editPermission = $("#editPermission");
    // 资源类型
    var editResourcesType = $("#editResourcesType");
    // 资源说明
    var editDescription = $("#editDescription");

    // 资源类型 枚举
    var resourcesTypeArray = null;

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
        // 设置资源类型下拉框
        searchResourcesType.combobox({
            required: false,
            url: findDictTypeUrl + encodeURIComponent("资源类型") + "&hasSelectAll=true&defaultSelectKey=" + encodeURIComponent("全部"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 100
        });

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
        // 数据显示表格 查询
        dataTableButtonsSearch.click(function () {
            dataTable.datagrid('load');
        });

        // 新增
        dataTableButtonsAdd.click(function () {
            addDialog.dialog('open');
            addForm.form('reset');
        });

        // 新增 -- 保存
        addDialogButtonsSave.click(function () {
            _this.addData();
        });

        // 新增 -- 取消
        addDialogButtonsCancel.click(function () {
            addDialog.dialog('close');
        });

        // 编辑
        dataTableButtonsEdit.click(function () {
            _this.openEditDialog();
        });

        // 编辑 -- 保存
        editDialogButtonsSave.click(function () {
            _this.updateData();
        });

        // 编辑 -- 取消
        editDialogButtonsCancel.click(function () {
            editDialog.dialog('close');
        });

        // 删除
        dataTableButtonsDel.click(function () {
            _this.delData();
        });
    };

    // ---------------------------------------------------------------------------------------------------------
    // 初始化新增对话框
    this.addDialogInit = function () {
        addDialog.dialog({
            title: "新增系统资源",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 830,
            minHeight: 330,
            modal: true,
            buttons: "#addDialogButtons"
        });

        addTitle.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addResourcesUrl.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addPermission.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addResourcesType.combobox({
            required: true,
            url: findDictTypeUrl + encodeURIComponent("资源类型") + "&hasSelectAll=false",
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        addDescription.textbox({
            required: true,
            validType: 'length[0,10000]',
            multiline: true
        });
    };

    // 新增资源信息
    this.addData = function () {
        addForm.form("submit", {
            url: addUrl,
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

    // 初始化编辑对话框
    this.editDialogInit = function () {
        editDialog.dialog({
            title: "新增系统资源",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 830,
            minHeight: 330,
            modal: true,
            buttons: "#editDialogButtons"
        });

        editTitle.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        editResourcesUrl.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        editPermission.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        editResourcesType.combobox({
            required: true,
            url: findDictTypeUrl + encodeURIComponent("资源类型") + "&hasSelectAll=false",
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        editDescription.textbox({
            required: true,
            validType: 'length[0,10000]',
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

    // 更新数据
    this.updateData = function () {
        editForm.form("submit", {
            url: updateUrl,
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

    // 删除数据
    this.delData = function () {
        var row = dataTable.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提示", "请选择要删除的数据！", "info");
            return;
        }
        $.messager.confirm("确认删除", "您确定删除资源信息?<br/>" + row.title, function (r) {
            if (r) {
                $.post(deleteUrl, row, function (data) {
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

    // 格式化
    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.resourcesTypeFormatter = function (value, rowData, rowIndex) {
        if (resourcesTypeArray == null) {
            resourcesTypeArray = searchResourcesType.combobox("getData");
        }
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