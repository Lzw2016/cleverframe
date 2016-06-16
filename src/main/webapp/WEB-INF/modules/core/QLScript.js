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
    // 数据保存或更新地址,根据情况取值:addUrl、updateUrl
    var saveUrl = "";
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/sys/findDictByType.json?dict-type=";
    findDictTypeUrl += encodeURIComponent("数据库脚本类型");

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

    //var editDelFlag = $("#editDelFlag");
    //var editCompanyCode = $("#editCompanyCode");
    //var editOrgCode = $("#editOrgCode");
    //var editCreateBy = $("#editCreateBy");
    //var editCreateDate = $("#editCreateDate");
    //var editUpdateBy = $("#editUpdateBy");
    //var editUpdateDate = $("#editUpdateDate");

    // SQL编辑器
    var editScript = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        // 设置脚本类型下拉框
        searchType.combobox({
            required: false,
            url: findDictTypeUrl,
            editable: false,
            valueField: 'value',
            textField: 'value',
            panelHeight: 50
        });
        // 设置删除标记下拉框
        searchDelFlag.combobox({
            required: false,
            url: findDictTypeUrl,
            editable: false,
            valueField: 'value',
            textField: 'value',
            panelHeight: 50
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
            pageSize: 50,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {
                _this.edit();
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

        _this.initEditDialog();
        _this.dataBind();
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

        // 数据显示表格 增加
        dataTableButtonsAdd.click(function () {
            _this.add();
        });

        // 数据显示表格 编辑
        dataTableButtonsEdit.click(function () {
            _this.edit();
        });

        // 数据显示表格 删除
        dataTableButtonsDel.click(function () {
            _this.del();
        });

        // 数据编辑表单 保存
        editDialogButtonsSave.click(function () {
            _this.saveOrUpdate();
        });

        // 数据编辑表单 取消
        editDialogButtonsCancel.click(function () {
            editDialog.dialog('close');
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    /**
     * 初始化编辑对话框
     */
    this.initEditDialog = function () {
        editDialog.dialog({
            title: "数据库脚本信息",
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
            url: findDictTypeUrl,
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
        editUuid.textbox({
            readonly: true
        });
        editId.textbox({
            readonly: true
        });

        //editDelFlag.textbox({
        //    readonly: true
        //});
        //editCompanyCode.textbox({
        //    readonly: true
        //});
        //editOrgCode.textbox({
        //    readonly: true
        //});
        //editCreateBy.textbox({
        //    readonly: true
        //});
        //editCreateDate.textbox({
        //    readonly: true
        //});
        //editUpdateBy.textbox({
        //    readonly: true
        //});
        //editUpdateDate.textbox({
        //    readonly: true
        //});
    };

    /**
     * 新增数据
     */
    this.add = function () {
        saveUrl = addUrl;
        editDialog.dialog('open').dialog('setTitle', '新增数据库脚本');
        editForm.form('reset');
    };

    /**
     * 编辑数据
     */
    this.edit = function () {
        saveUrl = updateUrl;
        var row = dataTable.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提示", "请选择要编辑的数据！", "info");
            return;
        }
        if (row) {
            editDialog.dialog('open').dialog('setTitle', '编辑数据库脚本');
            editForm.form('load', row);
        }
    };

    /**
     * 删除数据
     */
    this.del = function () {
        var row = dataTable.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提示", "请选择要删除的数据！", "info");
            return;
        }
        $.messager.confirm("确认删除", "您确定删除数据库脚本?<br/>" + row.name, function (r) {
            if (r) {
                $.post(delUrl, row, function (data) {
                    if (data.success) {
                        // 删除成功
                        $.messager.show({title: '提示', msg: data.message, timeout: 5000, showType: 'slide'});
                        dataTable.datagrid('reload');
                    } else {
                        // 删除失败
                    }
                }, "json");
            }
        });
    };

    /**
     * 保存或更新数据
     */
    this.saveOrUpdate = function () {
        editForm.form("submit", {
            url: saveUrl,
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    editDialog.dialog('close');
                    $.messager.show({title: '提示', msg: data.message, timeout: 5000, showType: 'slide'});
                    dataTable.datagrid('reload');
                } else {
                    // 保存失败
                }
            }
        });
    }
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
