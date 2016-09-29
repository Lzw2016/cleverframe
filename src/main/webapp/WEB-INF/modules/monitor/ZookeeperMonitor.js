/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 获取子节点目录
    var getChildrenUrl = globalPath.mvcPath + "/monitor/zookeeper/getChildren.json";
    // 获取节点信息
    var getZNodeInfoUrl = globalPath.mvcPath + "/monitor/zookeeper/getZNodeInfo.json";

    // 主页面
    var mainPanel = $("#mainPanel");
    // Zookeeper树结构
    var zookeeperNodeTree = $("#zookeeperNodeTree");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        // 页面左部
        mainPanel.layout("panel", "west").panel({
            region: "west",
            title:'Zookeeper数据节点',
            split:true,
            border:true,
            collapsible:false,
            width: 750,
            tools: [{
                iconCls: "icon-reload",
                handler: function () {
                    // 刷新 Zookeeper树结构
                    zookeeperNodeTree.tree("reload");
                }
            }]
        });

        // Zookeeper树结构
        zookeeperNodeTree.tree({
            // url: getChildrenUrl,
            lines: true,
            loader: function (param, success, error) {
                if (!param.id) {
                    param.path = "/";
                } else {
                    param.path = param.id;
                }
                $.ajax({
                    type: "POST",
                    dataType: "JSON",
                    url: getChildrenUrl,
                    data: param,
                    async: true,
                    success: function (data) {
                        success(data);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        error();
                    }
                });
            },
            loadFilter: function (data, parent) {
                var pData = zookeeperNodeTree.tree("getData", parent);
                var parentPath = "/";
                if (pData && pData.id) {
                    parentPath = pData.id;
                }
                if (data.success == true) {
                    var nodeArray = [];
                    $(data.result).each(function (index, nodeName) {
                        var path;
                        if (parentPath == "/") {
                            path = "/" + nodeName;
                        } else {
                            path = parentPath + "/" + nodeName;
                        }
                        nodeArray.push({"id": path, "text": nodeName, "state": "closed"});
                    });
                    return nodeArray;
                }
            },
            onSelect: function (node) {
                $.ajax({
                    type: "POST",
                    dataType: "JSON",
                    url: getZNodeInfoUrl,
                    data: {"path": node.id},
                    async: true,
                    success: function (data) {
                        console.log(data);
                    }
                });
            }
        });

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