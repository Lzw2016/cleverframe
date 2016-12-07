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

    // 激活流程定义 PUT repository/process-definitions/{processDefinitionId}

    // 获得流程定义的所有候选启动者 GET repository/process-definitions/{processDefinitionId}/identitylinks

    // 为流程定义添加一个候选启动者 POST repository/process-definitions/{processDefinitionId}/identitylinks

    // 删除流程定义的候选启动者 DELETE repository/process-definitions/{processDefinitionId}/identitylinks/{family}/{identityId}

    // 获得流程定义的一个候选启动者 GET repository/process-definitions/{processDefinitionId}/identitylinks/{family}/{identityId}

    // 获取部署流程信息
    var getProcessDefinitionUrl = globalPath.mvcPath + "/activiti/repository/getProcessDefinition/{processDefinitionId}.json";
    // 获取部署资源数据
    var getDeploymentResourceDataUrl = globalPath.mvcPath + "/activiti/repository/getDeploymentResourceData";

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
    var dataTableButtonsAdd = $("#dataTableButtonsAdd");

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
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        dataTableButtonsSearch.click(function () {
            dataTable.datagrid("reload");
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
        return "暂停 激活 编辑候选启动者";
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