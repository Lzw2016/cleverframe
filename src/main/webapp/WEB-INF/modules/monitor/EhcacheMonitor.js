/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 获取所有的缓存名称
    var getAllEhCacheNamesUrl = globalPath.mvcPath + "/monitor/ehcache/getAllEhCacheNames.json";
    // 获取缓存管理器信息
    var getCacheManagerInfoUrl = globalPath.mvcPath + "/monitor/ehcache/getCacheManagerInfo.json";
    // 获取缓存信息
    var getCacheInfoUrl = globalPath.mvcPath + "/monitor/ehcache/getCacheInfo.json";
    // 移除缓存元素
    var removeElementUrl = globalPath.mvcPath + "/monitor/ehcache/removeElement.json";
    // 清除缓存数据
    var clearCacheUrl = globalPath.mvcPath + "/mvc/monitor/ehcache/clearCache.json";

    // 名称
    var cacheManagerName = $("#cacheManagerName");
    // 集群UUID
    var cacheManagerClusterUUID = $("#cacheManagerClusterUUID");
    // 状态
    var cacheManagerStatus = $("#cacheManagerStatus");
    // 显式命名的缓存管理(named)
    var cacheManagerNamed = $("#cacheManagerNamed");
    // 缓存磁盘存储路径
    var cacheManagerDiskStoreConfiguration = $("#cacheManagerDiskStoreConfiguration");
    // 缓存管理器配置xml
    var cacheManagerConfigurationXml = null;
    // 缓存管理器配置xml
    var cacheManagerConfigurationJson = null;

    // 缓存统计Div
    var cacheStatisticsDiv = $("#cacheStatisticsDiv");

    /**
     * 页面初始化方法
     */
    this.init = function () {

        _this.initCacheManager();
        _this.initCacheStatistics();
        _this.initCacheContents();
        _this.dataBind();
        _this.eventBind();
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
    // 初始化缓存管理器
    this.initCacheManager = function () {
        if (cacheManagerConfigurationXml == null) {
            // 编辑器-初始化
            cacheManagerConfigurationXml = CodeMirror.fromTextArea(document.getElementById("cacheManagerConfigurationXml"), {
                mode: 'application/xml',
                lineNumbers: true,
                matchBrackets: true,
                indentUnit: 4,
                readOnly: true
            });
            cacheManagerConfigurationXml.setSize("auto", "98%");
            //cacheManagerConfigurationXml.setOption("theme", "cobalt");
        }
        if (cacheManagerConfigurationJson == null) {
            // 编辑器-初始化
            cacheManagerConfigurationJson = CodeMirror.fromTextArea(document.getElementById("cacheManagerConfigurationJson"), {
                mode: 'application/json',
                lineNumbers: true,
                matchBrackets: true,
                indentUnit: 4,
                readOnly: true
            });
            cacheManagerConfigurationJson.setSize("auto", "98%");
            //cacheManagerConfigurationJson.setOption("theme", "cobalt");
        }

        $.ajax({
            type: "POST", dataType: "JSON", url: getCacheManagerInfoUrl, async: true,
            success: function (data) {
                if (data.success) {
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 1000, showType: 'slide'});
                    cacheManagerName.text(data.result.name);
                    cacheManagerClusterUUID.text(data.result.clusterUUID);
                    cacheManagerStatus.text(data.result.status);
                    cacheManagerNamed.text(data.result.named);
                    var configurationJson = JSON.parse(data.result.configurationJson);
                    cacheManagerDiskStoreConfiguration.text(configurationJson.diskStoreConfiguration.path + "  原始路径(" + configurationJson.diskStoreConfiguration.originalPath + ")");

                    cacheManagerConfigurationXml.setValue("");
                    cacheManagerConfigurationJson.setValue("");
                    cacheManagerConfigurationXml.setValue(data.result.configurationXml);
                    cacheManagerConfigurationJson.setValue(js_beautify(data.result.configurationJson, 4, ' '));
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
    };

    // 初始化缓存监控
    this.initCacheStatistics = function () {
        var ehCacheNames = null;
        $.ajax({
            type: "POST", dataType: "JSON", url: getAllEhCacheNamesUrl, async: false,
            success: function (data) {
                if (data.success) {
                    ehCacheNames = data.result;
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
        if (ehCacheNames == null) {
            return;
        }

        $(ehCacheNames).each(function (index, cacheName) {
            var param = {cacheName: cacheName};
            $.ajax({
                type: "POST", dataType: "JSON", url: getCacheInfoUrl, data: param, async: true,
                success: function (data) {
                    if (data.success) {
                        var div = $('<div style="width: 500px;height: 400px;float: left;margin-top: 10px;margin-left: 10px; border: 1px solid #DDDDDD;"></div>');
                        var p = $("<p></p>");
                        p.html(JSON.stringify(data.result));
                        div.append(p);
                        cacheStatisticsDiv.append(div);
                    } else {
                        $.messager.alert("提示", data.failMessage, "warning");
                    }
                }
            });
        });
    };

    // 初始化缓存内容搜索
    this.initCacheContents = function () {

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