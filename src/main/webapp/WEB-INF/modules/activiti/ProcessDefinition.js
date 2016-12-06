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


    // 查询表单
    var searchForm = $("#searchForm");
    var searchNameLike = $("#searchNameLike");
    var searchCategoryLike = $("#searchCategoryLike");
    var searchKeyLike = $("#searchKeyLike");
    var searchResourceNameLike = $("#searchResourceNameLike");
    var searchStartableByUser = $("#searchStartableByUser");
    var searchSuspended = $("#searchSuspended");


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