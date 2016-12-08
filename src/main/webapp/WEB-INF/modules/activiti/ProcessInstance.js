/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    // 获得流程实例 GET runtime/process-instances/{processInstanceId}

    // 删除流程实例 DELETE runtime/process-instances/{processInstanceId}

    // 激活或挂起流程实例 PUT runtime/process-instances/{processInstanceId}

    // 启动流程实例 POST runtime/process-instances

    // 显示流程实例列表 GET runtime/process-instances

    // 查询流程实例 POST query/process-instances

    // 获得流程实例的流程图 GET runtime/process-instances/{processInstanceId}

    // 获得流程实例的参与者 GET runtime/process-instances/{processInstanceId}/identitylinks

    // 为流程实例添加一个参与者 POST runtime/process-instances/{processInstanceId}/identitylinks

    // 删除一个流程实例的参与者 DELETE runtime/process-instances/{processInstanceId}/identitylinks/users/{userId}/{type}

    // 列出流程实例的变量 GET runtime/process-instances/{processInstanceId}/variables

    // 获得流程实例的一个变量 GET runtime/process-instances/{processInstanceId}/variables/{variableName}

    // 创建（或更新）流程实例变量
    // POST runtime/process-instances/{processInstanceId}/variables
    // PUT runtime/process-instances/{processInstanceId}/variables
    // 使用POST时，会创建所有传递的变量。如果流程实例中已经存在了其中一个变量，就会返回一个错误（409 - CONFLICT）。
    // 使用PUT时， 流程实例中不存在的变量会被创建，已存在的变量会被更新，不会有任何错误。

    // 更新一个流程实例变量 PUT runtime/process-instances/{processInstanceId}/variables/{variableName}

    // 创建一个新的二进制流程变量 POST runtime/process-instances/{processInstanceId}/variables

    // 更新一个二进制的流程实例变量 PUT runtime/process-instances/{processInstanceId}/variables


    /**
     * 页面初始化方法
     */
    this.init = function () {
        console.log(globalPath);
        console.log("页面初始化");

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