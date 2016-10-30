/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";
    // 分页查询用户信息
    var findByPageUrl = globalPath.mvcPath + "/sys/user/findByPage.json";
    // 增加用户
    var addUserUrl = globalPath.mvcPath + "/sys/user/addUser.json";
    // 更新用户
    var updateUserUrl = globalPath.mvcPath + "/sys/user/updateUser.json";
    // 删除用户(软删除)
    var deleteUserUrl = globalPath.mvcPath + "/sys/user/deleteUser.json";

    // 查询表单
    var searchForm = $("#searchForm");
    // 数据表格
    var dataTable = $("#dataTable");
    // 数据表格 - 新增
    var dataTableButtonsAdd = $("#dataTableButtonsAdd");
    // 数据表格 - 编辑
    var dataTableButtonsEdit = $("#dataTableButtonsEdit");
    // 数据表格 - 删除
    var dataTableButtonsDel = $("#dataTableButtonsDel");

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
        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
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
    };

    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
    };

    // ---------------------------------------------------------------------------------------------------------

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