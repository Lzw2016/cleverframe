/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    // 查询所有日志信息
    var getAllLoggerInfoUrl = globalPath.mvcPath + "/monitor/log4j/getAllLoggerInfo.json";
    // 设置日志等级
    var setLoggerLevelUrl = globalPath.mvcPath + "/monitor/log4j/setLoggerLevel.json";

    // 查询表单
    var searchForm = $("#searchForm");
    // Logger Name
    var searchLoggerName = $("#searchLoggerName");
    // 数据表格
    var dataTable = $("#dataTable");
    // 查询
    var dataTableSearch = $("#dataTableSearch");
    // 编辑
    var dataTableEdit = $("#dataTableEdit");

    // 编辑对话框
    var editDialog = $("#editDialog");
    // 编辑对话框 - 保存
    var editDialogButtonsSave = $("#editDialogButtonsSave");
    // 编辑对话框 - 取消
    var editDialogButtonsCancel = $("#editDialogButtonsCancel");
    // 编辑表单
    var editForm = $("#editForm");
    // 编辑对话框 - 日志名称
    var editLoggerName = $("#editLoggerName");
    // 编辑对话框 - 日志等级
    var editLevel = $("#editLevel");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        //noinspection JSUnusedLocalSymbols
        searchLoggerName.textbox({
            icons: [{
                iconCls: 'icon-search',
                handler: function (e) {
                    // 查询数据
                    if (!searchForm.form("validate")) {
                        return;
                    }
                    dataTable.datagrid("load");
                }
            }]
        });

        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
            url: getAllLoggerInfoUrl,
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

        editDialog.dialog({
            title: "修改日志等级",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 750,
            minHeight: 165,
            modal: true,
            buttons: "#editDialogButtons"
        });

        editLoggerName.textbox({
            width: 600,
            readonly: true
        });

        editLevel.combobox({
            required: true,
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 100
        });
        var dataTmp = [
            {"value":"ALL", "text":"ALL-所有"},
            {"value":"TRACE", "text":"TRACE-跟踪"},
            {"value":"DEBUG", "text":"DEBUG-调试"},
            {"value":"INFO", "text":"INFO-信息"},
            {"value":"WARN", "text":"WARN-警告"},
            {"value":"ERROR", "text":"ERROR-错误"},
            {"value":"FATAL", "text":"FATAL-致命"},
            {"value":"OFF", "text":"OFF-关闭"}];
        editLevel.combobox("loadData", dataTmp);

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
        dataTableSearch.click(function () {
            if (!searchForm.form("validate")) {
                return;
            }
            dataTable.datagrid("load");
        });

        dataTableEdit.click(function () {
            _this.openEditDialog();
        });

        editDialogButtonsSave.click(function () {
            _this.saveLoggerLevel();
        });

        editDialogButtonsCancel.click(function () {
            editDialog.dialog("close");
        });
    };

    // ---------------------------------------------------------------------------------------------------------
    // 打开编辑对话框
    this.openEditDialog = function () {
        var row = dataTable.datagrid("getSelected");
        if(row) {
            editDialog.dialog("open");
            editLoggerName.textbox("setValue", row.name);
            editLevel.combobox("setValue", row.level);
        } else {
            $.messager.alert('提示','请先选择需要修改的日志信息','info');
        }
    };

    // 保存日志等级
    this.saveLoggerLevel = function () {
        editForm.form("submit", {
            url: setLoggerLevelUrl,
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