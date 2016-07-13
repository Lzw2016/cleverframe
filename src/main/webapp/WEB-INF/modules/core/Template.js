/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 分页查询地址
    var findByPageUrl = globalPath.mvcPath + "/core/template/findTemplateByPage.json";
    // 新增保存地址
    var addUrl = globalPath.mvcPath + "/core/template/addTemplate.json";
    // 编辑保存地址
    var updateUrl = globalPath.mvcPath + "/core/template/updateTemplate.json";
    // 删除地址
    var delUrl = globalPath.mvcPath + "/core/template/deleteTemplate.json";
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";

    // 数据查询表单
    var searchForm = $("#searchForm");
    var searchName = $("#searchName");
    var searchLocale = $("#searchLocale");
    var searchDelFlag = $("#searchDelFlag");

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

    // 新增对话框
    var addDialog = $("#addDialog");
    // 新增表单
    var addForm = $("#addForm");
    // 模版名称
    var addName = $("#addName");
    // 模版语言
    var addLocale = $("#addLocale");
    // 配置描述
    var addDescription = $("#addDescription");
    // 备注信息
    var addRemarks = $("#addRemarks");
    // 模版内容
    var addContent = null;

    // 新增对话框 - 按钮栏
    var addDialogButtons = $("#addDialogButtons");
    // 新增对话框 - 新增
    var addDialogButtonsSave = $("#addDialogButtonsSave");
    // 新增对话框 - 取消
    var addDialogButtonsCancel = $("#addDialogButtonsCancel");

    // 编辑对话框
    var editDialog = $("#editDialog");
    // 编辑表单
    var editForm = $("#editForm");
    // 模版名称
    var editName = $("#editName");
    // 模版语言
    var editLocale = $("#editLocale");
    // 删除标记
    var editDelFlag = $("#editDelFlag");
    // 配置描述
    var editDescription = $("#editDescription");
    // 备注信息
    var editRemarks = $("#editRemarks");
    // 模版内容
    var editContent = null;

    // 编辑对话框 - 按钮栏
    var editDialogButtons = $("#editDialogButtons");
    // 编辑对话框 - 编辑
    var editDialogButtonsSave = $("#editDialogButtonsSave");
    // 编辑对话框 - 取消
    var editDialogButtonsCancel = $("#editDialogButtonsCancel");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        $("#searchLocale").combobox({
            required: false,
            url: findDictTypeUrl + encodeURIComponent("国际化语言后缀")+ "&hasSelectAll=true",
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

    //noinspection JSUnusedGlobalSymbols
    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
    };

    //noinspection JSUnusedGlobalSymbols
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
            addContent.setValue('\r\n');
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
    this.initAddDialog = function () {
        addDialog.dialog({
            title: "新增模版数据",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            minWidth: 850,
            minHeight: 330,
            modal: true,
            buttons: "#addDialogButtons",
            onOpen: function() {
                if(addContent != null) {
                    return;
                }
                // SQL编辑器-初始化,
                addContent = CodeMirror.fromTextArea(document.getElementById("addContent"), {
                    mode: "text/x-java",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: false
                });
                addContent.setSize("auto", "auto");
                addContent.setOption("theme", "cobalt");
                addContent.setValue('\r\n');
            }
        });
        addName.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addLocale.combobox({
            required: false,
            url: findDictTypeUrl + encodeURIComponent("国际化语言后缀"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        addDescription.textbox({
            required: true,
            validType: 'length[0,500]',
            multiline: true
        });
        addRemarks.textbox({
            validType: 'length[0,255]',
            multiline: true
        });
    };

    // 初始化编辑对话框
    this.initEditDialog = function () {
        editDialog.dialog({
            title: "更新模版数据",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            minWidth: 850,
            minHeight: 330,
            modal: true,
            buttons: "#editDialogButtons",
            onOpen: function() {
                if(editContent != null) {
                    return;
                }
                // SQL编辑器-初始化,
                editContent = CodeMirror.fromTextArea(document.getElementById("editContent"), {
                    mode: "text/x-java",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: false
                });
                editContent.setSize("auto", "auto");
                editContent.setOption("theme", "cobalt");
                editContent.setValue('\r\n');
            }
        });
        editName.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        editLocale.combobox({
            required: false,
            url: findDictTypeUrl + encodeURIComponent("国际化语言后缀"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        editDescription.textbox({
            required: true,
            validType: 'length[0,500]',
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
            if (!row.content || row.content == null) {
                editContent.setValue("");
            } else {
                editContent.setValue(row.content);
            }
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
        $.messager.confirm("确认删除", "您确定删除模版信息?<br/>" + row.name, function (r) {
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

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.localeFormatter = function (value, rowData, rowIndex){
        var result = "";
        $(searchLocale.combobox('getData')).each(function(index, data) {
            if(data.value == value) {
                result = data.text;
                return false;
            }
        });
        return result == "" ? value : result;
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