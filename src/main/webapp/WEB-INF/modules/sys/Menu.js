/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";
    // 新增资源信息
    var findAllMenuTypeUrl = globalPath.mvcPath + "/sys/menu/findAllMenuType.json";
    // 查询菜单树
    var findMenuTreeByTypeUrl = globalPath.mvcPath + "/sys/menu/findMenuTreeByType.json";

    // 菜单类型
    var menuTypeList = $("#menuTypeList");
    // 菜单树
    var treeGrid = $("#treeGrid");
    // 菜单树 - 刷新
    var treeGridButtonsSearch = $("#treeGridButtonsSearch");
    // 菜单树 - 新增
    var treeGridButtonsAdd = $("#treeGridButtonsAdd");
    // 菜单树 - 编辑
    var treeGridButtonsEdit = $("#treeGridButtonsEdit");
    // 菜单树 - 删除
    var treeGridButtonsDel = $("#treeGridButtonsDel");

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

        menuTypeList.datalist({
            url: findAllMenuTypeUrl,
            checkbox: false,
            lines: false,
            pagination: false
        });

        treeGrid.treegrid({
            url: findMenuTreeByTypeUrl,
            idField: 'id',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#treeGridButtons",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {
                _this.openEditDialog();
            },
            onBeforeLoad: function (param) {
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