/**
 * 页面Js对象定义
 * @param globalPath 系统全局路径对象
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 分页查询地址
    var findByPageUrl = globalPath.mvcPath + "/core/qlscript/findQLScriptByPage.json";
    // 新增保存地址
    var addUrl = globalPath.mvcPath + "/core/qlscript/addQLScript.json";
    // 编辑保存地址
    var updateUrl = globalPath.mvcPath + "/core/qlscript/updateQLScript.json";
    // 删除地址
    var delUrl = globalPath.mvcPath + "/core/qlscript/deleteQLScript.json";
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";

    // 数据查询表单
    var searchForm = $("#searchForm");
    // 数据显示表格
    var dataTable = $("#dataTable");
    // 数据显示表格 按钮栏
    var dataTableButtons = $("#dataTableButtons");
    // 数据显示表格 查询按钮
    var dataTableButtonsSearch = $("#dataTableButtonsSearch");
    // 数据显示表格 增加按钮
    var dataTableButtonsAdd = $("#dataTableButtonsAdd");
    // 数据显示表格 编辑按钮
    var dataTableButtonsEdit = $("#dataTableButtonsEdit");
    // 数据显示表格 删除按钮
    var dataTableButtonsDel = $("#dataTableButtonsDel");

    var searchName = $("#searchName");
    var searchType = $("#searchType");
    var searchDelFlag = $("#searchDelFlag");
    var searchId = $("#searchId");
    var searchUuid = $("#searchUuid");

    // 数据新增对话框
    var addDialog = $("#addDialog");
    // 数据新增表单
    var addForm = $("#addForm");
    // 数据新增对话框 按钮栏
    var addDialogButtons = $("#addDialogButtons");
    // 数据新增对话框 新增按钮
    var addDialogButtonsSave = $("#addDialogButtonsSave");
    // 数据新增对话框 取消按钮
    var addDialogButtonsCancel = $("#addDialogButtonsCancel");

    var addName = $("#addName");
    var addScriptType = $("#addScriptType");
    var addDescription = $("#addDescription");
    var addRemarks = $("#addRemarks");


    // 数据编辑对话框
    var editDialog = $("#editDialog");
    // 数据编辑表单
    var editForm = $("#editForm");
    // 数据编辑表单 按钮栏
    var editDialogButtons = $("#editDialogButtons");
    // 数据编辑表单 保存按钮
    var editDialogButtonsSave = $("#editDialogButtonsSave");
    // 数据编辑表单 取消按钮
    var editDialogButtonsCancel = $("#editDialogButtonsCancel");

    var editName = $("#editName");
    var editScriptType = $("#editScriptType");
    var editDescription = $("#editDescription");
    var editRemarks = $("#editRemarks");
    var editUuid = $("#editUuid");
    var editId = $("#editId");
    var editDelFlag = $("#editDelFlag");

    // SQL编辑器
    var addScript = null;
    var editScript = null;

    // 字典数据
    //var scriptTypeDate;
    //var delFlag;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        // 设置脚本类型下拉框
        searchType.combobox({
            required: false,
            url: findDictTypeUrl + encodeURIComponent("数据库脚本类型")+ "&hasSelectAll=true",
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        // 设置删除标记下拉框
        searchDelFlag.combobox({
            required: false,
            url: findDictTypeUrl + encodeURIComponent("删除标记") + "&hasSelectAll=true&defaultSelectKey=" + encodeURIComponent("正常"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 100
        });

        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
            url: findByPageUrl,
            idField: 'name',
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

        // 初始化新增对话框
        _this.initAddDialog();
        // 初始化编辑对话框
        _this.initEditDialog();
        // 页面数据初始化
        _this.dataBind();
        // 事件绑定初始化
        _this.eventBind();
    };

    /**
     * 页面数据初始化
     */
    this.dataBind = function () {

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
            addScript.setValue('\r\n');
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

    /**
     * 初始化新增对话框
     */
    this.initAddDialog = function () {
        addDialog.dialog({
            title: "新增数据库脚本信息",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 850,
            minHeight: 450,
            modal: true,
            buttons: "#addDialogButtons",
            onOpen: function() {
                if(addScript != null) {
                    return;
                }
                // SQL编辑器-初始化,
                addScript = CodeMirror.fromTextArea(document.getElementById("addScript"), {
                    mode: "text/x-mysql",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: false
                });
                addScript.setSize("auto", "auto");
                addScript.setOption("theme", "cobalt");
                addScript.setValue('\r\n');
            }
        });
        addName.textbox({
            required: true,
            validType: 'length[5,100]'
        });
        addScriptType.combobox({
            required: true,
            validType: 'length[1,10]',
            url: findDictTypeUrl + encodeURIComponent("数据库脚本类型"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 50
        });
        addDescription.textbox({
            required: true,
            validType: 'length[2,1000]',
            multiline: true
        });
        addRemarks.textbox({
            validType: 'length[0,255]',
            multiline: true
        });
    };

    /**
     * 初始化编辑对话框
     */
    this.initEditDialog = function () {
        editDialog.dialog({
            title: "编辑数据库脚本信息",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 850,
            minHeight: 450,
            modal: true,
            buttons: "#editDialogButtons",
            onOpen: function() {
                if(editScript != null) {
                    return;
                }
                // SQL编辑器-初始化,
                editScript = CodeMirror.fromTextArea(document.getElementById("editScript"), {
                    mode: "text/x-mysql",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: false
                });
                editScript.setSize("auto", "auto");
                editScript.setOption("theme", "cobalt");
                editScript.setValue('\r\n');
            }
        });

        editName.textbox({
            required: true,
            validType: 'length[5,100]'
        });
        editScriptType.combobox({
            required: true,
            validType: 'length[1,10]',
            url: findDictTypeUrl + encodeURIComponent("数据库脚本类型"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 50
        });
        editDescription.textbox({
            required: true,
            validType: 'length[2,1000]',
            multiline: true
        });
        editRemarks.textbox({
            validType: 'length[0,255]',
            multiline: true
        });
        editDelFlag.combobox({
            required: true,
            url: findDictTypeUrl + encodeURIComponent("删除标记"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
    };

    /**
     * 打开编辑对话框
     */
    this.openEditDialog = function () {
        var row = dataTable.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提示", "请选择要编辑的数据！", "info");
            return;
        }
        if (row) {
            editDialog.dialog('open');
            editForm.form('load', row);
            editScript.setValue(row.script);
        }
    };

    /**
     * 保存数据
     */
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

    /**
     * 更新数据
     */
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

    /**
     * 删除数据
     */
    this.delData = function () {
        var row = dataTable.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提示", "请选择要删除的数据！", "info");
            return;
        }
        $.messager.confirm("确认删除", "您确定删除数据库脚本?<br/>" + row.description, function (r) {
            if (r) {
                $.post(delUrl, row, function (data) {
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

    // 脚本类型列格式化
    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.scriptTypeFormatter = function (value, rowData, rowIndex){
        var result = "";
        $(searchType.combobox('getData')).each(function(index, data) {
            if(data.value == value) {
                result = data.text;
                return false;
            }
        });
        return result;
    };

    // 删除标记格式化
    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.delFlagFormatter = function (value, rowData, rowIndex){
        var result = "";
        $(searchDelFlag.combobox('getData')).each(function(index, data) {
            if(data.value == value) {
                result = data.text;
                return false;
            }
        });
        return result;
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
