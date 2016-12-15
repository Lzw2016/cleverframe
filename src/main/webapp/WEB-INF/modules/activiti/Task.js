/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    // 任务列表 GET runtime/tasks
    var getTasksUrl = globalPath.appPath + "/runtime/tasks.json";
    // 获得流程实例的流程图 GET runtime/process-instances/{processInstanceId}/diagram
    var getDiagramUrl = globalPath.appPath + "/runtime/process-instances/{processInstanceId}/diagram.png";

    // 操作任务 POST runtime/tasks/{taskId} org.activiti.rest.service.api.runtime.task.TaskResource

    //

    // 查询表单
    var searchForm = $("#searchForm");

    // 任务数据列表
    var dataTable = $("#dataTable");
    // 任务数据列表 - 查询按钮
    var dataTableButtonsSearch = $("#dataTableButtonsSearch");

    // 查看模版代码对话框
    var viewCodeTemplateDialog = $("#viewCodeTemplateDialog");
    // 查看代码对话框-编辑器
    var viewCodeTemplateEdit = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        _this.initSearchForm();
        _this.initDataTable();
        _this.initViewCodeTemplateDialog();
        _this.dataBind();
        _this.eventBind();
    };

    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
    };

    /**
     * 界面事件绑定方法 searchForm
     */
    this.eventBind = function () {
    };

    // ---------------------------------------------------------------------------------------------------------
    this.initSearchForm = function () {

    };

    this.initDataTable = function () {
        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
            url: getTasksUrl,
            method: 'GET',
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
            onClickRow: function (rowIndex, rowData) {
                // _this.loadeDataTable_2(rowData);
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
                // 移除
                var rmProperty = [];
                $.each(param, function (property, value) {
                    if ($.trim(value) == "") {
                        rmProperty.push(property);
                    }
                });
                $.each(rmProperty, function (i, property) {
                    delete param[property];
                });
                param.size = $.isNumeric(param.rows) ? param.rows : 15;
                param.start = $.isNumeric(param.page) ? (param.page - 1) * param.rows : 0;
                delete param.rows;
                delete param.page;
                // 模糊查询
                // if ($.trim(searchTenantIdLike.textbox("getValue")) != "") {
                //     param.tenantIdLike = "%" + searchTenantIdLike.textbox("getValue") + "%";
                // }
            },
            loadFilter: function (data) {
                var resultData = {};
                resultData.rows = data.data;
                resultData.total = data.total;
                return resultData;
            }
        });
    };

    // 初始化查看模版代码对话框
    this.initViewCodeTemplateDialog = function () {
        viewCodeTemplateDialog.dialog({
            title: "查看流程资源",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: true,
            minWidth: 700,
            minHeight: 450,
            modal: true,
            //buttons: "#viewCodeTemplateDialogButtons",
            onOpen: function () {
                if (viewCodeTemplateEdit != null) {
                    return;
                }
                // Java编辑器-初始化,
                viewCodeTemplateEdit = CodeMirror.fromTextArea(document.getElementById("viewCodeTemplateEdit"), {
                    mode: "application/xml",
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

    //noinspection JSUnusedLocalSymbols
    this.urlFormatter = function (value, rowData, rowIndex) {
        if ($.trim(value) == "") {
            return value;
        } else {
            var id = _this.getUUID(32, 16);
            var a = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.openUrlView(\"" + id + "\")'>查看</a>");
            a.attr("url", value);
            return $("<div/>").append(a).html();
        }
    };

    this.openUrlView = function (id) {
        var a = $("#" + id);
        var url = a.attr("url");
        $.ajax({
            type: "GET", dataType: "text", url: url, data: {}, async: true,
            success: function (data) {
                if (!data || data == null) {
                    data = "";
                } else {
                    data = js_beautify(data, 4, ' ');
                }
                viewCodeTemplateDialog.dialog("open");
                viewCodeTemplateEdit.setValue("");
                viewCodeTemplateEdit.setOption("mode", _this.getCodeMirrorMode("json"));
                viewCodeTemplateEdit.setValue(data);
            }
        });
    };

    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
    this.variablesFormatter = function (value, rowData, rowIndex) {
        if (value != "" && value != null && (value.constructor == Object || $.isArray(value))) {
            var jsonValue = JSON.stringify(value);
            var id = _this.getUUID(32, 16);
            var a = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.openVariablesView(\"" + id + "\")'>查看</a>");
            a.attr("jsonValue", jsonValue);
            return $("<div/>").append(a).html();
        } else {
            return value;
        }
    };

    this.openVariablesView = function (id) {
        var a = $("#" + id);
        var jsonValue = a.attr("jsonValue");
        if (jsonValue == null || $.trim(jsonValue) == "") {
            jsonValue = "";
        } else {
            jsonValue = js_beautify(jsonValue, 4, ' ');
        }
        viewCodeTemplateDialog.dialog("open");
        viewCodeTemplateEdit.setValue("");
        viewCodeTemplateEdit.setOption("mode", _this.getCodeMirrorMode("json"));
        viewCodeTemplateEdit.setValue(jsonValue);
    };

    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
    this.diagramFormatter = function (value, rowData, rowIndex) {
        if ($.trim(rowData.processInstanceId) == "") {
            return "";
        } else {
            var id = _this.getUUID(32, 16);
            var a = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.openDiagramView(\"" + id + "\")'>查看</a>");
            a.attr("processInstanceId", rowData.processInstanceId);
            return $("<div/>").append(a).html();
        }
    };

    this.openDiagramView = function (id) {
        var a = $("#" + id);
        var processInstanceId = a.attr("processInstanceId");
        if ($.trim(processInstanceId) != "") {
            var diagramUrl = getDiagramUrl.replace("{processInstanceId}", processInstanceId);
            $.fancybox.open({href: diagramUrl, title: '图片资源查看'});
        }
    };

    //noinspection JSUnusedLocalSymbols
    this.timeFormatter = function (value, rowData, rowIndex) {
        var time = moment(value);
        if (time.isValid()) {
            return time.format("YYYY-MM-DD HH:mm:ss");
        } else {
            return value
        }
    };

    // 根据编程语言获取CodeMirror的Mode属性
    this.getCodeMirrorMode = function (mediaType) {
        switch (mediaType.toLowerCase()) {
            case "java":
                return "text/x-java";
            case "c#":
                return "text/x-csharp";
            case "text/xml":
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
        return "application/xml";
    };

    // 获取一个UUID
    this.getUUID = function (len, radix) {
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var uuid = [], i;
        radix = radix || chars.length;
        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
        } else {
            // rfc4122, version 4 form
            var r;
            // rfc4122 requires these characters
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';
            // Fill in random data.  At i==19 set the high bits of clock sequence as
            // per rfc4122, sec. 4.1.5
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }
        return uuid.join('');
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