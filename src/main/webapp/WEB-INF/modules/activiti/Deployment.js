/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    // 部署列表 GET repository/deployments
    var getDeploymentPageUrl = globalPath.appPath + "/repository/deployments";

    // 获得一个部署 GET repository/deployments/{deploymentId}

    // 创建新部署 POST repository/deployments

    // 删除部署 DELETE repository/deployments/{deploymentId}

    // 列出部署内的资源 GET repository/deployments/{deploymentId}/resources
    var getAllResourcesUrl = globalPath.appPath + "/repository/deployments/{deploymentId}/resources";
    // 获取部署资源 GET repository/deployments/{deploymentId}/resources/{resourceId}

    // 获取部署资源的内容 GET repository/deployments/{deploymentId}/resourcedata/{resourceId}
    // var getResourceDataUrl = globalPath.appPath + "/repository/deployments/{deploymentId}/resourcedata/{resourceId}";

    // --------------------------------------------------------------------------------------------------------------------
    // 获取部署资源
    var getDeploymentResourceUrl = globalPath.mvcPath + "/activiti/repository/getDeploymentResource";

    // 获取部署资源数据
    var getDeploymentResourceDataUrl = globalPath.mvcPath + "/activiti/repository/getDeploymentResourceData";

    // 查询表单
    var searchForm = $("#searchForm");
    // 查询表单 - 流程名称
    var searchNameLike = $("#searchNameLike");
    // 查询表单 - 流程类别
    var searchCategory = $("#searchCategory");
    // 查询表单 - 流程租户ID
    var searchTenantIdLike = $("#searchTenantIdLike");

    // 部署流程数据表
    var dataTable = $("#dataTable");
    // 数据显示表格 查询
    var dataTableButtonsSearch = $("#dataTableButtonsSearch");
    // 数据显示表格 删除
    var dataTableButtonsDelete = $("#dataTableButtonsDelete");

    // 部署流程资源数据
    var dataTable_2 = $("#dataTable_2");
    // 部署流程资源数据 - 刷新
    var dataTableButtonsSearch_2 = $("#dataTableButtonsSearch_2");
    // 用于显示 选择的流程
    var selectDeployment = $("#selectDeployment");
    // 当前选择的部署流程ID
    var selectDeploymentId = null;

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
        _this.initDataTable_2();
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
        // 数据显示表格 查询
        dataTableButtonsSearch.click(function () {
            dataTable.datagrid('load');
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    this.initSearchForm = function () {
        searchNameLike.textbox({required: false, validType: 'length[0,50]'});
        searchCategory.textbox({required: false, validType: 'length[0,50]'});
        searchTenantIdLike.textbox({required: false, validType: 'length[0,50]'});
    };

    this.initDataTable = function () {
        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
            url: getDeploymentPageUrl,
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
            pageSize: 20,
            pageList: [10, 20, 30, 50, 100, 150],
            onClickRow: function (rowIndex, rowData) {
                _this.loadeDataTable_2(rowData);
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
            },
            loadFilter: function (data) {
                var resultData = {};
                resultData.rows = data.data;
                resultData.total = data.total;
                return resultData;
            }
        });
    };

    this.initDataTable_2 = function () {
        dataTable_2.datagrid({
            // url: getDeploymentPageUrl,
            method: 'GET',
            idField: 'id',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#dataTableButtons_2",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150]
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

    this.loadeDataTable_2 = function (deploymentData) {
        if ($.trim(deploymentData.id) == "") {
            return;
        }
        selectDeploymentId = deploymentData.id;
        selectDeployment.val("部署流程ID: " + deploymentData.id + ", 流程名称: " + deploymentData.name);
        var url = getAllResourcesUrl.replace("{deploymentId}", encodeURIComponent(deploymentData.id));
        $.ajax({
            type: "GET", dataType: "JSON", url: url, data: {}, async: false,
            success: function (data) {
                if (data) {
                    dataTable_2.datagrid("loadData", data);
                    $.messager.show({title: '提示', msg: "获取资源信息成功", timeout: 1000, showType: 'slide'});
                } else {
                    dataTable_2.datagrid("loadData", []);
                    $.messager.alert("提示", "获取资源信息失败", "warning");
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
    this.operateFormatter = function (value, rowData, rowIndex) {
        var id = _this.getUUID(32, 16);
        var a = $("<a id='" + id + "' href='javascript:void(0)' onclick='pageJsObject.openResourceView(\"" + id + "\")'>查看</a>");
        a.attr("deploymentId", selectDeploymentId);
        a.attr("resourceId", rowData.id);
        a.attr("mediaType", rowData.mediaType);
        return $("<div/>").append(a).html();
    };

    this.openResourceView = function (id) {
        var a = $("#" + id);
        var deploymentId = a.attr("deploymentId");
        var resourceId = a.attr("resourceId");
        var mediaType = a.attr("mediaType");

        switch (mediaType) {
            case "text/xml":
                _this.openViewCodeTemplateDialog(deploymentId, resourceId, "xml");
                break;
            case "image/png":
                var paramData = {deploymentId: deploymentId, resourceId: resourceId};
                var url = _this.getParamUrl(getDeploymentResourceDataUrl, "?", paramData);
                $.fancybox.open({href: url, title: '图片资源查看'});
                break;
            default:
                _this.openViewCodeTemplateDialog(deploymentId, resourceId, "json");
        }
    };

    // 打开查看模版代码对话框
    this.openViewCodeTemplateDialog = function (deploymentId, resourceId, mediaType) {
        var paramData = {deploymentId: deploymentId, resourceId: resourceId};
        $.ajax({
            type: "GET", dataType: "text", url: getDeploymentResourceDataUrl, data: paramData, async: true,
            success: function (data) {
                if (!data || data == null) {
                    data = "";
                } else if (mediaType == "json") {
                    data = js_beautify(data, 4, ' ')
                }
                viewCodeTemplateDialog.dialog("open");
                viewCodeTemplateEdit.setValue("");
                viewCodeTemplateEdit.setOption("mode", _this.getCodeMirrorMode(mediaType));
                viewCodeTemplateEdit.setValue(data);
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