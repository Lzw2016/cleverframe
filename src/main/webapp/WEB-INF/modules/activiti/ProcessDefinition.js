/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    // 流程定义列表 GET repository/process-definitions
    var getProcessDefinitionsUrl = globalPath.appPath + "/repository/process-definitions";
    // 获得一个流程定义 GET repository/process-definitions/{processDefinitionId}

    // 获得一个流程定义的资源内容 GET repository/process-definitions/{processDefinitionId}/resourcedata

    // 获得流程定义的BPMN模型 GET repository/process-definitions/{processDefinitionId}/model

    // 暂停流程定义 PUT repository/process-definitions/{processDefinitionId}
    var suspendProcessDefinitionUrl = globalPath.appPath + "/repository/process-definitions/{processDefinitionId}.json";
    // 激活流程定义 PUT repository/process-definitions/{processDefinitionId}
    var activateProcessDefinitionUrl = globalPath.appPath + "/repository/process-definitions/{processDefinitionId}.json";
    // 获得流程定义的所有候选启动者 GET repository/process-definitions/{processDefinitionId}/identitylinks

    // 为流程定义添加一个候选启动者 POST repository/process-definitions/{processDefinitionId}/identitylinks

    // 删除流程定义的候选启动者 DELETE repository/process-definitions/{processDefinitionId}/identitylinks/{family}/{identityId}

    // 获得流程定义的一个候选启动者 GET repository/process-definitions/{processDefinitionId}/identitylinks/{family}/{identityId}

    // 获取部署流程信息
    var getProcessDefinitionUrl = globalPath.mvcPath + "/activiti/repository/getProcessDefinition/{processDefinitionId}.json";
    // 获取部署资源数据
    var getDeploymentResourceDataUrl = globalPath.mvcPath + "/activiti/repository/getDeploymentResourceData";
    // 启动流程实例 POST runtime/process-instances
    var startProcessUrl = globalPath.appPath + "/runtime/process-instances";

    // 查询表单
    var searchForm = $("#searchForm");
    // 查询表单 - 流程名称
    var searchNameLike = $("#searchNameLike");
    // 查询表单 - 流程类别
    var searchCategoryLike = $("#searchCategoryLike");
    // 查询表单 - 模型Key
    var searchKeyLike = $("#searchKeyLike");
    // 查询表单 - 源数据名称
    var searchResourceNameLike = $("#searchResourceNameLike");
    // 查询表单- 启动的用户
    var searchStartableByUser = $("#searchStartableByUser");
    // 查询表单 - 是否挂起
    var searchSuspended = $("#searchSuspended");

    // 流程定义数据表格
    var dataTable = $("#dataTable");
    var dataTableButtonsSearch = $("#dataTableButtonsSearch");
    var dataTableButtonsStart = $("#dataTableButtonsStart");

    // 查看模版代码对话框
    var viewCodeTemplateDialog = $("#viewCodeTemplateDialog");
    // 查看代码对话框-编辑器
    var viewCodeTemplateEdit = null;

    // 新增流程实例对话框
    var addProcessInstanceDialog = $("#addProcessInstanceDialog");
    // 新增流程实例对话框 - 保存
    var addProcessInstanceDialogSave = $("#addProcessInstanceDialogSave");
    // 新增流程实例对话框 - 取消
    var addProcessInstanceDialogCancel = $("#addProcessInstanceDialogCancel");
    // 新增流程实例对话框 - 保存
    var variablesTableButtonsSave = $("#variablesTableButtonsSave");
    // 流程变量表格
    var variablesTable = $("#variablesTable");
    // 流程变量表格 - 新增
    var variablesTableButtonsAdd = $("#variablesTableButtonsAdd");
    // 流程变量表格 - 移除
    var variablesTableButtonsRemove = $("#variablesTableButtonsRemove");
    // 流程变量表格 - 编辑行
    var addVariablesEditIndex = undefined;
    // 新增流程实例 表单
    var addProcessInstanceForm = $("#addProcessInstanceForm");
    var addStartType = $("#addStartType");
    var addProcessDefinitionId = $("#addProcessDefinitionId");
    var addBusinessKey = $("#addBusinessKey");
    var addTenantId = $("#addTenantId");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        _this.initSearchForm();
        _this.initDataTable();
        _this.initViewCodeTemplateDialog();
        _this.initAddProcessInstanceDialog();

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
        dataTableButtonsSearch.click(function () {
            dataTable.datagrid("load");
        });
        dataTableButtonsStart.click(function () {
            var row = dataTable.datagrid('getSelected');
            if (row == null) {
                $.messager.alert('提示', '请先选择一个流程定义记录', 'info');
                return;
            }
            addProcessInstanceForm.form('reset');
            variablesTable.datagrid('loadData', []);
            addProcessInstanceDialog.dialog('open');
            addProcessDefinitionId.textbox("setValue", row.id);
        });
        addProcessInstanceDialogCancel.click(function () {
            addProcessInstanceDialog.dialog('close');
        });
        addProcessInstanceDialogSave.click(function () {
            _this.startProcess();
        });
    };

    // ---------------------------------------------------------------------------------------------------------
    this.initSearchForm = function () {
        searchNameLike.textbox({required: false, validType: 'length[0,50]'});
        searchCategoryLike.textbox({required: false, validType: 'length[0,50]'});
        searchKeyLike.textbox({required: false, validType: 'length[0,50]'});
        searchResourceNameLike.textbox({required: false, validType: 'length[0,50]'});
        searchStartableByUser.textbox({required: false, validType: 'length[0,50]'});
        searchSuspended.textbox({required: false, validType: 'length[0,50]'});
    };

    this.initDataTable = function () {
        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
            url: getProcessDefinitionsUrl,
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
                if ($.trim(searchNameLike.textbox("getValue")) != "") {
                    param.nameLike = "%" + searchNameLike.textbox("getValue") + "%";
                }
                if ($.trim(searchCategoryLike.textbox("getValue")) != "") {
                    param.categoryLike = "%" + searchCategoryLike.textbox("getValue") + "%";
                }
                if ($.trim(searchKeyLike.textbox("getValue")) != "") {
                    param.keyLike = "%" + searchKeyLike.textbox("getValue") + "%";
                }
                if ($.trim(searchResourceNameLike.textbox("getValue")) != "") {
                    param.resourceNameLike = "%" + searchResourceNameLike.textbox("getValue") + "%";
                }
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

    // 初始化启动流程对话框
    this.initAddProcessInstanceDialog = function () {
        addProcessInstanceDialog.dialog({
            title: "启动任务",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 680,
            minHeight: 450,
            modal: true,
            buttons: "#addProcessInstanceDialogButtons"
        });

        //noinspection JSUnusedLocalSymbols
        addStartType.combobox({
            required: true, editable: false, panelHeight: 70, validType: 'length[1,250]',
            onChange: function (newValue, oldValue) {
                addProcessDefinitionId.textbox("setValue", "");
                addProcessDefinitionId.textbox("readonly", false);
                var row = dataTable.datagrid('getSelected');
                if (row == null) {
                    return;
                }
                switch (newValue) {
                    case "ID":
                        addProcessDefinitionId.textbox("setValue", row.id);
                        addProcessDefinitionId.textbox("readonly", true);
                        break;
                    case "KEY":
                        addProcessDefinitionId.textbox("setValue", row.key);
                        addProcessDefinitionId.textbox("readonly", true);
                        break;
                    case "MESSAGE":
                        break;
                    default:
                }
            }
        });
        addProcessDefinitionId.textbox({required: true, validType: 'length[1,250]', readonly: true});
        addBusinessKey.textbox({required: true, validType: 'length[1,250]'});
        addTenantId.textbox({required: false, validType: 'length[1,250]'});

        var endEditing = function () {
            if (addVariablesEditIndex == undefined) {
                return true
            }
            if (variablesTable.datagrid('validateRow', addVariablesEditIndex)) {
                variablesTable.datagrid('endEdit', addVariablesEditIndex);
                addVariablesEditIndex = undefined;
                return true;
            } else {
                return false;
            }
        };
        variablesTable.datagrid({
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            toolbar: "#variablesTableButtons",
            pagination: false,
            onEndEdit: function (index, row) {

            },
            onClickCell: function (index, field) {
                if (addVariablesEditIndex != index) {
                    if (endEditing()) {
                        variablesTable.datagrid('selectRow', index).datagrid('beginEdit', index);
                        var ed = variablesTable.datagrid('getEditor', {index: index, field: field});
                        if (ed) {
                            ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                        }
                        addVariablesEditIndex = index;
                    } else {
                        setTimeout(function () {
                            variablesTable.datagrid('selectRow', addVariablesEditIndex);
                        }, 0);
                    }
                }
            }
        });
        // 变量数据表格 - 增加
        variablesTableButtonsAdd.click(function () {
            if (endEditing()) {
                variablesTable.datagrid('appendRow', {});
                addVariablesEditIndex = variablesTable.datagrid('getRows').length - 1;
                variablesTable.datagrid('selectRow', addVariablesEditIndex).datagrid('beginEdit', addVariablesEditIndex);
            }
        });
        // 变量数据表格 - 移除
        variablesTableButtonsRemove.click(function () {
            if (addVariablesEditIndex == undefined) {
                return
            }
            variablesTable.datagrid('cancelEdit', addVariablesEditIndex).datagrid('deleteRow', addVariablesEditIndex);
            addVariablesEditIndex = undefined;
        });
        // 变量数据表格 - 保存
        variablesTableButtonsSave.click(function () {
            if (endEditing()) {
                variablesTable.datagrid('acceptChanges');
            }
        });
    };

    // 启动流程
    this.startProcess = function () {
        // 校验表单
        if (!addProcessInstanceForm.form("validate")) {
            return;
        }
        var param = {};
        param.businessKey = addBusinessKey.textbox("getValue");
        param.tenantId = addTenantId.textbox("getValue");
        var startType = addStartType.combobox('getValue');
        switch (startType) {
            case "ID":
                param.processDefinitionId = addProcessDefinitionId.textbox("getValue");
                break;
            case "KEY":
                param.processDefinitionKey = addProcessDefinitionId.textbox("getValue");
                break;
            case "MESSAGE":
                param.message = addProcessDefinitionId.textbox("getValue");
                break;
            default:
                return;
        }
        var rows = variablesTable.datagrid('getData');
        if (rows != null && rows.rows != null && rows.rows.length >= 0) {
            param.variables = [];
            $(rows.rows).each(function (index, element) {
                param.variables.push({name: element.key, value: element.value});
            });
        }
        $.ajax({
            type: "POST", dataType: "text", url: startProcessUrl, data: JSON.stringify(param), async: true,
            contentType: "application/json; charset=utf-8", success: function (data) {
                addProcessInstanceDialog.dialog('close');
                if (!data || data == null) {
                    data = "";
                } else {
                    data = js_beautify(data, 4, ' ')
                }
                viewCodeTemplateDialog.dialog("open");
                viewCodeTemplateEdit.setValue("");
                viewCodeTemplateEdit.setOption("mode", _this.getCodeMirrorMode("json"));
                viewCodeTemplateEdit.setValue(data);
            }
        });
    };

    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
    this.deploymentUrlFormatter = function (value, rowData, rowIndex) {
        if ($.trim(value) == "") {
            return value;
        } else {
            var id = _this.getUUID(32, 16);
            var a = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.openDeploymentUrlView(\"" + id + "\")'>查看</a>");
            a.attr("deploymentUrl", value);
            return $("<div/>").append(a).html();
        }
    };

    this.openDeploymentUrlView = function (id) {
        var a = $("#" + id);
        var deploymentUrl = a.attr("deploymentUrl");
        $.ajax({
            type: "GET", dataType: "text", url: deploymentUrl, data: {}, async: true,
            success: function (data) {
                if (!data || data == null) {
                    data = "";
                } else {
                    data = js_beautify(data, 4, ' ')
                }
                viewCodeTemplateDialog.dialog("open");
                viewCodeTemplateEdit.setValue("");
                viewCodeTemplateEdit.setOption("mode", _this.getCodeMirrorMode("json"));
                viewCodeTemplateEdit.setValue(data);
            }
        });
    };

    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
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
                    data = js_beautify(data, 4, ' ')
                }
                viewCodeTemplateDialog.dialog("open");
                viewCodeTemplateEdit.setValue("");
                viewCodeTemplateEdit.setOption("mode", _this.getCodeMirrorMode("json"));
                viewCodeTemplateEdit.setValue(data);
            }
        });
    };

    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
    this.resourceFormatter = function (value, rowData, rowIndex) {
        if ($.trim(value) == "") {
            return value;
        } else {
            var id = _this.getUUID(32, 16);
            var a = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.openResourceView(\"" + id + "\")'>查看</a>");
            a.attr("diagramResource", value);
            a.attr("processDefinitionId", rowData.id);
            a.attr("deploymentId", rowData.deploymentId);
            return $("<div/>").append(a).html();
        }
    };

    this.openResourceView = function (id) {
        var a = $("#" + id);
        var diagramResource = a.attr("diagramResource");
        var processDefinitionId = a.attr("processDefinitionId");
        var deploymentId = a.attr("deploymentId");
        var urlTmp = getProcessDefinitionUrl.replace("{processDefinitionId}", encodeURIComponent(processDefinitionId));
        $.ajax({
            type: "GET", dataType: "json", url: urlTmp, data: {}, async: true,
            success: function (data) {
                if (data.success) {
                    var paramData = {deploymentId: deploymentId, resourceId: data.result.resourceName};
                    var url = _this.getParamUrl(getDeploymentResourceDataUrl, "?", paramData);
                    $.ajax({
                        type: "GET", dataType: "text", url: url, data: {}, async: true,
                        success: function (data) {
                            if ($.trim(data) == "") {
                                data = "";
                            }
                            viewCodeTemplateDialog.dialog("open");
                            viewCodeTemplateEdit.setValue("");
                            viewCodeTemplateEdit.setOption("mode", _this.getCodeMirrorMode("text/xml"));
                            viewCodeTemplateEdit.setValue(data);
                        }
                    });
                }
            }
        });
    };

    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
    this.diagramResourceFormatter = function (value, rowData, rowIndex) {
        if ($.trim(value) == "") {
            return value;
        } else {
            var id = _this.getUUID(32, 16);
            var a = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.openDiagramResourceView(\"" + id + "\")'>查看</a>");
            a.attr("diagramResource", value);
            a.attr("processDefinitionId", rowData.id);
            a.attr("deploymentId", rowData.deploymentId);
            return $("<div/>").append(a).html();
        }
    };

    this.openDiagramResourceView = function (id) {
        var a = $("#" + id);
        var diagramResource = a.attr("diagramResource");
        var processDefinitionId = a.attr("processDefinitionId");
        var deploymentId = a.attr("deploymentId");
        var urlTmp = getProcessDefinitionUrl.replace("{processDefinitionId}", encodeURIComponent(processDefinitionId));
        $.ajax({
            type: "GET", dataType: "json", url: urlTmp, data: {}, async: true,
            success: function (data) {
                if (data.success) {
                    var paramData = {deploymentId: deploymentId, resourceId: data.result.diagramResourceName};
                    var url = _this.getParamUrl(getDeploymentResourceDataUrl, "?", paramData);
                    $.fancybox.open({href: url, title: '图片资源查看'});
                }
            }
        });
    };

    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
    this.operateFormatter = function (value, rowData, rowIndex) {
        var div = $("<div/>");
        if (rowData.id) {
            var id1 = _this.getUUID(32, 16);
            var suspend = $("<a id='" + id1 + "' href='javascript:void(0)' onclick='pageJsObject.suspendProcessDefinition(\"" + id1 + "\")'>暂停</a>");
            suspend.attr("processDefinitionId", rowData.id);
            div.append(suspend);

            div.append("&nbsp;");

            var id2 = _this.getUUID(32, 16);
            var activate = $("<a id='" + id2 + "' href='javascript:void(0)' onclick='pageJsObject.activateProcessDefinition(\"" + id2 + "\")'>激活</a>");
            activate.attr("processDefinitionId", rowData.id);
            div.append(activate);

            div.append("&nbsp;");

            div.append("编辑候选启动者");
        }
        return div.html();
    };

    this.suspendProcessDefinition = function (id) {
        var a = $("#" + id);
        var processDefinitionId = a.attr("processDefinitionId");
        var url = suspendProcessDefinitionUrl.replace("{processDefinitionId}", encodeURIComponent(processDefinitionId));
        var param = {action: "suspend", includeProcessInstances: "false", date: moment(new Date()).format()};
        $.ajax({
            type: "PUT", dataType: "json", url: url, data: JSON.stringify(param), async: true, contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.suspended) {
                    // 成功
                    $.messager.show({title: '提示', msg: "流程暂停成功", timeout: 5000, showType: 'slide'});
                    dataTable.datagrid("reload");
                } else {
                    // 失败
                    $.messager.alert("提示", "流程暂停失败", "warn");
                }
            }
        });
    };

    this.activateProcessDefinition = function (id) {
        var a = $("#" + id);
        var processDefinitionId = a.attr("processDefinitionId");
        var url = activateProcessDefinitionUrl.replace("{processDefinitionId}", encodeURIComponent(processDefinitionId));
        var param = {action: "activate", includeProcessInstances: "true", date: moment(new Date()).format()};
        $.ajax({
            type: "PUT", dataType: "json", url: url, data: JSON.stringify(param), async: true, contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.suspended) {
                    // 失败
                    $.messager.show({title: '提示', msg: "流程激活成功", timeout: 5000, showType: 'slide'});
                } else {
                    // 成功
                    $.messager.alert("提示", "流程激活失败", "warn");
                }
            }
        });
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

    this.getParamUrl = function (url, connectStr, object) {
        var param = null;
        $.each(object, function (property, value) {
            if (param == null) {
                param = property + "=" + encodeURIComponent(value);
            } else {
                param = param + "&" + property + "=" + encodeURIComponent(value);
            }
        });
        return url + connectStr + param;
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