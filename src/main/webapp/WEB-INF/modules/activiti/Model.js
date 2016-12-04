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
    var createModelUrl = globalPath.appPath + "/repository/models";
    // 删除模型 DELETE repository/models/{modelId}

    // 获得模型的可编译源码 GET repository/models/{modelId}/source

    // 设置模型的可编辑源码 PUT repository/models/{modelId}/source

    // 获得模型的附加可编辑源码 GET repository/models/{modelId}/source-extra

    // 设置模型的附加可编辑源码 PUT repository/models/{modelId}/source-extr


    // 新增一个模型 用于在线流程设计
    var createModelUrl = globalPath.mvcPath + "/activiti/model/createModel.json";

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
    // 数据显示表格 编辑
    var dataTableButtonsEdit = $("#dataTableButtonsEdit");
    // 数据显示表格 删除
    var dataTableButtonsDelete = $("#dataTableButtonsDelete");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        _this.initSearchForm();
        _this.initDataTable();

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

    //noinspection JSUnusedLocalSymbols
    this.timeFormatter = function (value, rowData, rowIndex) {
        var time = moment(value);
        if (time.isValid()) {
            return time.format("YYYY-MM-DD HH:mm:ss");
        } else {
            return value
        }
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