/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    // 获取数据库表详细信息
    var getTableSchemaURL = globalPath.mvcPath + "/generator/matedata/getTableSchema.json";
    // 代码模版树
    var findAllCodeTemplateUrl = globalPath.mvcPath + "/generator/codetemplate/findAllCodeTemplate.json";
    // 根据模版名称返回模版数据
    var getTemplateByNameUrl = globalPath.mvcPath + "/core/template/getTemplateByName.json";

    // 数据库名
    var labelSchemaName = $("#labelSchemaName");
    // 表名称
    var labelTableName = $("#labelTableName");
    // 表描述
    var labelDescription = $("#labelDescription");

    // 数据库表-数据显示表格
    var dataTable = $("#dataTable");
    // 代码模版-数据显示表格
    var codeTemplateDataTable = $("#codeTemplateDataTable");
    // 代码模版-添加
    var codeTemplateDataTableButtonsAdd = $("#codeTemplateDataTableButtonsAdd");
    // 代码模版-移除
    var codeTemplateDataTableButtonsDel = $("#codeTemplateDataTableButtonsDel");

    // 选择模版代码对话框
    var selectCodeTemplateDialog = $("#selectCodeTemplateDialog");
    // 代码模版选择树
    var codeTemplateID = $("#codeTemplateID");
    // 不能选择模版类型错误提示
    var selectCodeTemplateMsg = $("#selectCodeTemplateMsg");
    // 选择模版代码对话框-选择
    var selectCodeTemplateDialogButtonsOk = $("#selectCodeTemplateDialogButtonsOk");
    // 选择模版代码对话框-取消
    var selectCodeTemplateDialogButtonsCancel = $("#selectCodeTemplateDialogButtonsCancel");
    // 选择模版代码对话框-生成代码
    var codeTemplateDataTableButtonsRun = $("#codeTemplateDataTableButtonsRun");

    // 查看模版代码对话框
    var viewCodeTemplateDialog = $("#viewCodeTemplateDialog");
    // 查看代码对话框-编辑器
    var viewCodeTemplateEdit = null;
    // 查看模版代码对话框-关闭
    var viewCodeTemplateDialogButtonsCancel = $("#viewCodeTemplateDialogButtonsCancel");

    var paramSchemaName = null;
    var paramTableName = null;
    // 数据库表结构数据
    var tableSchema = null;
    // 当前选择的模版数据
    var selectCodeTemplateData = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {

        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
            idField: 'columnName',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: false,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            //toolbar: "#",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150]
        });

        codeTemplateDataTable.datagrid({
            idField: 'name',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#codeTemplateDataTableButtons",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150]
        });

        // 初始化选择模版代码对话框
        _this.initSelectCodeTemplateDialog();
        // 初始化查看模版代码对话框
        _this.initViewCodeTemplateDialog();
        // 页面数据初始化
        _this.dataBind();
        // 界面事件绑定方法
        _this.eventBind();
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        paramSchemaName = _this.getUrlParam("schemaName");
        paramTableName = _this.getUrlParam("tableName");
        if (paramSchemaName == null || paramTableName == null) {
            $.messager.alert("提示", "请求参数“schemaName”、“tableName”不存在！", "error");
            return;
        }
        _this.getTableSchema(paramSchemaName, paramTableName, dataTable);

        if(tableSchema != null){
            labelSchemaName.text(tableSchema.schemaName);
            labelTableName.text(tableSchema.tableName);
            labelDescription.text(tableSchema.description);
        }
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        // 代码模版-添加
        codeTemplateDataTableButtonsAdd.click(function () {
            selectCodeTemplateDialog.dialog("open");
        });

        // 代码模版-移除
        codeTemplateDataTableButtonsDel.click(function () {

        });

        // 选择模版代码对话框-生成代码
        codeTemplateDataTableButtonsRun.click(function () {

        });

        // 选择模版代码对话框-选择
        selectCodeTemplateDialogButtonsOk.click(function () {
            if(selectCodeTemplateData == null) {
                return;
            }
            var rows = codeTemplateDataTable.datagrid("getRows");
            $(rows).each(function (index, row) {
                if(row.name == selectCodeTemplateData.name) {
                    selectCodeTemplateMsg.text("已经选择了模版[" + row.name + "]");
                    return false;
                }
            });
            if (selectCodeTemplateMsg.text() == "") {
                codeTemplateDataTable.datagrid("appendRow", selectCodeTemplateData);
                selectCodeTemplateData = null;
                selectCodeTemplateDialog.dialog("close");
            }
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    // 初始化选择模版代码对话框
    this.initSelectCodeTemplateDialog = function () {
        selectCodeTemplateDialog.dialog({
            title: "选择模版代码",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 300,
            minHeight: 130,
            modal: true,
            buttons: "#selectCodeTemplateDialogButtons"
        });

        codeTemplateID.combotree({
            required: true,
            editable: false,
            animate: false,
            checkbox: false,
            cascadeCheck: true,
            onlyLeafCheck: false,
            lines: true,
            dnd: false,
            valueField: 'text',
            textField: 'text',
            onSelect: function (node) {
                // 节点类型(0:模版分类; 1:代码模版)
                if(node.attributes.nodeType != "1") {
                    //noinspection BadExpressionStatementJS,JSUnusedAssignment
                    selectCodeTemplateData = null;
                    selectCodeTemplateMsg.text("不能选择模版分类");
                    return;
                }
                selectCodeTemplateMsg.text("");
                selectCodeTemplateData = node.attributes;
            }
        });

        var param = {isClose: "true"};
        $.ajax({
            type: "POST",
            dataType: "JSON",
            url: findAllCodeTemplateUrl,
            data: param,
            async: true,
            success: function (data) {
                codeTemplateID.combotree("loadData", data);
            }
        });
    };

    // 初始化查看模版代码对话框
    this.initViewCodeTemplateDialog = function () {
        viewCodeTemplateDialog.dialog({
            title: "查看模版代码",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            minWidth: 850,
            minHeight: 300,
            modal: true,
            buttons: "#viewCodeTemplateDialogButtons",
            onOpen: function () {
                if (viewCodeTemplateEdit != null) {
                    return;
                }
                // Java编辑器-初始化,
                viewCodeTemplateEdit = CodeMirror.fromTextArea(document.getElementById("viewCodeTemplateEdit"), {
                    mode: "text/x-java",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: true
                });
                viewCodeTemplateEdit.setSize("auto", "auto");
                viewCodeTemplateEdit.setOption("theme", "cobalt");
                viewCodeTemplateEdit.setValue('');
            }
        });
    };

    // 打开查看模版代码对话框
    this.openViewCodeTemplateDialog = function (name) {
        var codeTemplate = null;
        var rows = codeTemplateDataTable.datagrid("getRows");
        $(rows).each(function (index, row) {
            if(row.name == name) {
                codeTemplate = row;
                return false;
            }
        });
        if(codeTemplate == null) {
            return;
        }

        viewCodeTemplateDialog.dialog("open");
        viewCodeTemplateEdit.setValue('');
        var param = {name: name};
        $.ajax({
            type: "POST",
            dataType: "JSON",
            url: getTemplateByNameUrl,
            data: param,
            async: true,
            success: function (data) {
                data = data.result;
                viewCodeTemplateEdit.setOption("mode", _this.getCodeMirrorMode(codeTemplate.codeType));
                if(data.content && data.content != null){
                    viewCodeTemplateEdit.setValue(data.content);
                } else {
                    viewCodeTemplateEdit.setValue('');
                }
            }
        });
    };

    // 根据编程语言获取CodeMirror的Mode属性
    this.getCodeMirrorMode = function (codeType) {
        switch (codeType.toLowerCase()) {
            case "java":
                return "text/x-java";
            case "c#":
                return "text/x-csharp";
            case "xml":
                return "application/xml";
            case "html":
                return "text/html";
            case "jsp":
                return "application/x-jsp";
            case "css":
                return "text/css";
            case "sql":
                return "text/x-mysql";
            case "javascript":
                return "text/javascript";
            case "js":
                return "text/javascript";
            case "json":
                return "application/json";
        }
        return "text/x-java";
    };

    // 获取 数据库表详细信息
    this.getTableSchema = function (schemaName, tableName, dataTable) {
        var param = {};
        param.schemaName = schemaName;
        param.tableName = tableName;
        $.ajax({
            type: "POST",
            //dataType: "JSON",
            url: getTableSchemaURL,
            data: param,
            async: false,
            success: function (data) {
                if (data.success == true) {
                    tableSchema = data.result;
                } else {
                    $.messager.alert("提示", data.failMessage, "error");
                }
            }
        });
        dataTable.datagrid("loadData", tableSchema.columnList);
    };

    // 获取URL参数
    this.getUrlParam = function (name) {
        //构造一个含有目标参数的正则表达式对象
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        //匹配目标参数
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return decodeURIComponent(r[2]);
        }
        return null;
    };

    // 格式化
    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
    this.ordinalPositionFormatter = function (value, rowData, rowIndex) {
        //<span class="tree-icon tree-folder icon-table"></span>
        var image = _this.getColumnImage(rowData.dataType);
        var result = '<span class="' + image + '" style="display: inline-block;width: 16px;height: 16px;"></span>';
        return result + " " + value;
    };

    // 根据数据库列类型返回 对应的图标
    this.getColumnImage = function (dataType){
        if(!dataType || dataType == null || dataType == ""){
            return "icon-unknown";
        }
        dataType = dataType.toLowerCase();
        if (dataType.indexOf("char") >= 0) {
            return "icon-string";
        }
        if (dataType.indexOf("int") >= 0
            || dataType.indexOf("number") >= 0
            || dataType.indexOf("float") >= 0
            || dataType.indexOf("double") >= 0
            || dataType.indexOf("decimal") >= 0) {
            return "icon-number";
        }
        if (dataType.indexOf("date") >= 0
            || dataType.indexOf("time") >= 0
            || dataType.indexOf("year") >= 0) {
            return "icon-datetime";
        }
        if (dataType.indexOf("bool") >= 0) {
            return "icon-boolean";
        }
        if (dataType.indexOf("clob") >= 0) {
            return "icon-binary";
        }
        if (dataType.indexOf("lob") >= 0) {
            return "icon-lob";
        }
        if (dataType.indexOf("text") >= 0) {
            //return "icon-text";
            return "icon-lob";
        }
        return "icon-unknown";
    };

    // 格式化
    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.booleanFormatter = function (value, rowData, rowIndex) {
        if (value == true) {
            return '<input type="checkbox" onclick="return false;" checked="checked"/>';
        }
        if (value == false) {
            return "";
        }
        return value;
    };

    // 格式化
    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.codeTemplateNameFormatter = function (value, rowData, rowIndex) {
        if ($.trim(value) == "") {
            return value;
        }
        return '<a href="javascript:void(0)" onclick="pageJsObject.openViewCodeTemplateDialog(\'' + value + '\');">' + value + '</a>';
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