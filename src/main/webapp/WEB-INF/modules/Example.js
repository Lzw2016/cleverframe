/**
 * 页面Js对象定义
 */
var pageJs = function () {
    // 当前pageJs对象
    var _this = this;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        console.log("页面初始化");
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
