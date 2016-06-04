/**
 * 页面Js对象定义
 */
var pageJs = function () {
    // 当前pageJs对象
    var _this = this;
    // 获取分页QLScript数据地址
    var qlScriptDataUrl = "${pageScope.mvcPath}/common/findQLScriptByPage";
    // 新增保存QLScript地址
    var newQLScriptUrl = "${pageScope.mvcPath}/common/addQLScript";
    // 编辑保存QLScript地址
    var editQLScriptUrl = "${pageScope.mvcPath}/common/updateQLScript";
    // 删除QLScript地址
    var delQLScriptUrl = "${pageScope.mvcPath}/common/deleteQLScript";
    // 数据库脚本编辑表单--提交地址
    var qlScriptSaveUrl = "";
    // 根据字典类别查询字典地址
    var findDictTypeUrl	= mvcPath + "/sys/findDictByType?dict-type=";
    findDictTypeUrl += encodeURIComponent("数据库脚本类型");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        console.log(findDictTypeUrl);
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
    };

    //noinspection JSUnusedGlobalSymbols
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
    pageJsObject = new pageJs();
    pageJsObject.init();
});
