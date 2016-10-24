/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";
    // 分页查询系统资源
    var findByPageUrl = globalPath.mvcPath + "/sys/resources/findByPage.json";
    // 查询一个页面资源的所有依赖资源(不需要分页)
    var findDependenceResourcesUrl = globalPath.mvcPath + "/sys/resources/findDependenceResources.json";
    // 为页面资源增加一个依赖资源
    var addDependenceResourcesUrl = globalPath.mvcPath + "/sys/resources/addDependenceResources.json";
    // 为页面资源删除一个依赖资源
    var deleteDependenceResourcesUrl = globalPath.mvcPath + "/sys/resources/deleteDependenceResources.json";
    // 查询资源依赖树(查询系统所有资源:只分两级，页面资源和后台资源)
    var findResourcesTreeUrl = globalPath.mvcPath + "/sys/resources/findResourcesTree.json";

    // 资源类型 枚举
    var resourcesTypeArray = null;
    // 查询表单
    var searchForm = $("#searchForm");
    // 查询表单 - 资源类型
    var searchResourcesType = $("#searchResourcesType");
    // 资源标题 - 资源类型
    var searchTitle = $("#searchTitle");
    // 资源标题 - 资源类型
    var searchResourcesUrl = $("#searchResourcesUrl");
    // 权限标识 - 资源类型
    var searchPermission = $("#searchPermission");

    // 页面资源表格
    var dataTable_1 = $("#dataTable_1");
    // 页面资源表格 - 查询
    var dataTableButtonsSearch_1 = $("#dataTableButtonsSearch_1");

    // 依赖资源表格
    var dataTable_2 = $("#dataTable_2");
    // 依赖资源表格 - 新增依赖
    var dataTableButtonsAdd_2 = $("#dataTableButtonsAdd_2");
    // 依赖资源表格 - 删除依赖
    var dataTableButtonsDel_2 = $("#dataTableButtonsDel_2");
    // 选中页面资源
    var selectPageResourcesText = $("#selectPageResourcesText");
    // 选中页面资源
    var selectPageResources = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        _this.dataBind();
        _this.eventBind();
    };

    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        //noinspection JSUnusedLocalSymbols
        dataTable_1.datagrid({
            url: findByPageUrl,
            idField: 'id',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: true,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#dataTableButtons_1",
            pageSize: 20,
            pageList: [10, 20, 30, 50, 100, 150],
            onSelect: function (index, row) {
                _this.setDependenceResources(row);
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

        //noinspection JSUnusedLocalSymbols
        dataTable_2.datagrid({
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

        $.ajax({
            type: "POST", dataType: "JSON", data: {}, async: false,
            url: findDictTypeUrl + encodeURIComponent("资源类型") + "&hasSelectAll=false",
            success: function (data) {
                resourcesTypeArray = data;
            }
        });

    };

    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        // 数据显示表格 查询
        dataTableButtonsSearch_1.click(function () {
            dataTable_1.datagrid('load');
        });
    };

    // ---------------------------------------------------------------------------------------------------------
    // 查询依赖资源
    this.setDependenceResources = function (resources) {
        selectPageResources = resources;
        selectPageResourcesText.text(resources.title + "[" + resources.resourcesUrl + "]");
        $.ajax({
            type: "POST", dataType: "JSON", data: {"id": resources.id}, async: true, url: findDependenceResourcesUrl,
            success: function (data) {
                if (data.success) {
                    dataTable_2.datagrid("loadData", data.result);
                }
            }
        });
    };

    // 格式化
    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.resourcesTypeFormatter = function (value, rowData, rowIndex) {
        var result = "";
        $(resourcesTypeArray).each(function (index, data) {
            if (data.value == value) {
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