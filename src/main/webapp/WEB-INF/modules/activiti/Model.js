/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    // 获得模型列表 GET repository/models
    var getModelPage = globalPath.appPath + "/repository/models";
    // 获得一个模型 GET repository/models/{modelId}

    // 获得一个模型 GET repository/models/{modelId}

    // 更新模型 PUT repository/models/{modelId}

    // 新建模型 POST repository/models
    // var createModelUrl = globalPath.appPath + "/repository/models";
    // 删除模型 DELETE repository/models/{modelId}

    // 获得模型的可编译源码 GET repository/models/{modelId}/source

    // 设置模型的可编辑源码 PUT repository/models/{modelId}/source

    // 获得模型的附加可编辑源码 GET repository/models/{modelId}/source-extra

    // 设置模型的附加可编辑源码 PUT repository/models/{modelId}/source-extr


    // 新增一个模型 用于在线流程设计
    var createModelUrl = globalPath.mvcPath + "/activiti/model/createModel.json";
    // 根据数据库中的Model部署流程
    var deployModelUrl = globalPath.mvcPath + "/activiti/model/deployModel.json?modelId=";
    // 模型编辑地址
    var modelerEditUrl = globalPath.staticPath + "/Activiti/modeler.html?modelId=";

    // 查询表单
    var searchForm = $("#searchForm");
    // 查询表单 - 流程名称
    var searchNameLike = $("#searchNameLike");
    // 查询表单 - 流程类别
    var searchCategoryLike = $("#searchCategoryLike");
    // 查询表单 - 流程Key
    var searchKey = $("#searchKey");
    // 查询表单 - 流程租户ID
    var searchTenantIdLike = $("#searchTenantIdLike");
    // 查询表单 - 是否部署
    var searchDeployed = $("#searchDeployed");
    // 查询表单 - 最后的版本
    var searchLatestVersion = $("#searchLatestVersion");

    // 部署流程数据表
    var dataTable = $("#dataTable");
    // 数据显示表格 查询
    var dataTableButtonsSearch = $("#dataTableButtonsSearch");
    // 数据显示表格 新增
    var dataTableButtonsAdd = $("#dataTableButtonsAdd");

    // 新增模型
    var addDialog = $("#addDialog");
    var addForm = $("#addForm");
    var addName = $("#addName");
    var addKey = $("#addKey");
    var addCategory = $("#addCategory");
    var addTenantId = $("#addTenantId");
    var addDescription = $("#addDescription");
    var addDialogButtonsSave = $("#addDialogButtonsSave");
    var addDialogButtonsCancel = $("#addDialogButtonsCancel");

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
        _this.initAddDialog();

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
            dataTable.datagrid('load');
        });

        dataTableButtonsAdd.click(function () {
            addDialog.dialog('open');
            addForm.form('reset');
        });

        addDialogButtonsSave.click(function () {
            _this.createModel();
        });

        addDialogButtonsCancel.click(function () {
            addDialog.dialog('close');
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    this.initSearchForm = function () {
        searchNameLike.textbox({required: false, validType: 'length[0,50]'});
        searchCategoryLike.textbox({required: false, validType: 'length[0,50]'});
        searchKey.textbox({required: false, validType: 'length[0,50]'});
        searchTenantIdLike.textbox({required: false, validType: 'length[0,50]'});
        searchDeployed.textbox({required: false, validType: 'length[0,50]'});
        searchLatestVersion.textbox({required: false, validType: 'length[0,50]'});
    };

    this.initDataTable = function () {
        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
            url: getModelPage,
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
                if ($.trim(searchTenantIdLike.textbox("getValue")) != "") {
                    param.tenantIdLike = "%" + searchTenantIdLike.textbox("getValue") + "%";
                }
                if ($.trim(searchCategoryLike.textbox("getValue")) != "") {
                    param.categoryLike = "%" + searchCategoryLike.textbox("getValue") + "%";
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

    // 初始化新增模型对话框
    this.initAddDialog = function () {
        addDialog.dialog({
            title: "新增模型对话框",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            minWidth: 700,
            minHeight: 280,
            modal: true,
            buttons: "#addDialogButtons"
        });
        addName.textbox({required: true, validType: 'length[0,255]'});
        addKey.textbox({required: true, validType: 'length[0,255]'});
        addCategory.textbox({required: false, validType: 'length[0,255]'});
        addTenantId.textbox({required: false, validType: 'length[0,255]'});
        addDescription.textbox({required: false, validType: 'length[0,255]', multiline: true});
    };

    this.createModel = function () {
        addForm.form("submit", {
            url: createModelUrl,
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    addDialog.dialog('close');
                    dataTable.datagrid('reload');
                    var editUrl = modelerEditUrl + data.result.id;
                    window.open(editUrl);
                    // $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                } else {
                    // 保存失败
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
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

    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
    this.metaInfoFormatter = function (value, rowData, rowIndex) {
        if ($.trim(value) == "") {
            return value;
        } else {
            var id = _this.getUUID(32, 16);
            var a = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.openMetaInfoView(\"" + id + "\")'>查看</a>");
            a.attr("data", value);
            return $("<div/>").append(a).html();
        }
    };

    this.openMetaInfoView = function (id) {
        var a = $("#" + id);
        var data = a.attr("data");
        if (!data || data == null) {
            data = "";
        } else {
            data = js_beautify(data, 4, ' ')
        }
        viewCodeTemplateDialog.dialog("open");
        viewCodeTemplateEdit.setValue("");
        viewCodeTemplateEdit.setOption("mode", _this.getCodeMirrorMode("json"));
        viewCodeTemplateEdit.setValue(data);
    };

    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
    this.urlFormatter = function (value, rowData, rowIndex) {
        if ($.trim(value) == "") {
            return value;
        } else {
            var id = _this.getUUID(32, 16);
            var a = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.openModelUrlView(\"" + id + "\")'>查看</a>");
            a.attr("modelUrl", value);
            return $("<div/>").append(a).html();
        }
    };

    this.openModelUrlView = function (id) {
        var a = $("#" + id);
        var modelUrl = a.attr("modelUrl");
        $.ajax({
            type: "GET", dataType: "text", url: modelUrl, data: {}, async: true,
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
    this.sourceUrlFormatter = function (value, rowData, rowIndex) {
        if ($.trim(value) == "") {
            return value;
        } else {
            var id = _this.getUUID(32, 16);
            var a = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.openSourceUrlView(\"" + id + "\")'>查看</a>");
            a.attr("sourceUrl", value);
            return $("<div/>").append(a).html();
        }
    };

    this.openSourceUrlView = function (id) {
        var a = $("#" + id);
        var sourceUrl = a.attr("sourceUrl");
        $.ajax({
            type: "GET", dataType: "text", url: sourceUrl, data: {}, async: true,
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
    this.sourceExtraUrlFormatter = function (value, rowData, rowIndex) {
        if ($.trim(value) == "") {
            return value;
        } else {
            var id = _this.getUUID(32, 16);
            var a = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.openSourceExtraUrlView(\"" + id + "\")'>查看</a>");
            a.attr("sourceExtraUrl", value);
            return $("<div/>").append(a).html();
        }
    };

    this.openSourceExtraUrlView = function (id) {
        var a = $("#" + id);
        var sourceExtraUrl = a.attr("sourceExtraUrl") + ".png";
        $.fancybox.open({href: sourceExtraUrl, title: '图片资源查看'});
    };

    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
    this.operateFormatter = function (value, rowData, rowIndex) {
        var div = $("<div/>");
        if (rowData.id) {
            var editUrl = modelerEditUrl + rowData.id;
            var edit = $("<a target='_blank' href='" + editUrl + "'>编辑</a>");
            div.append(edit);
            div.append("&nbsp;");
            var deployUrl = deployModelUrl + rowData.id;
            var id = _this.getUUID(32, 16);
            var deploy = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.deployModel(\"" + id + "\")'>部署</a>");
            deploy.attr("deployUrl", deployUrl);
            div.append(deploy);
        }
        return div.html();
    };

    this.deployModel = function (id) {
        var a = $("#" + id);
        var deployUrl = a.attr("deployUrl");
        $.ajax({
            type: "GET", dataType: "json", url: deployUrl, data: {}, async: true,
            success: function (data) {
                if (data.success) {
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 1000, showType: 'slide'});
                } else {
                    var failMessageStr = "/* [" + data.failMessage + "] */ \r\n \r\n" + data.exceptionStack;
                    viewCodeTemplateDialog.dialog("open");
                    viewCodeTemplateEdit.setValue("");
                    viewCodeTemplateEdit.setOption("mode", _this.getCodeMirrorMode("java"));
                    viewCodeTemplateEdit.setValue(failMessageStr);
                    // $.messager.alert("提示", data.failMessage, "warning");
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