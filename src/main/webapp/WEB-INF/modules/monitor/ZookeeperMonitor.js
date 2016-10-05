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
    // 节点信息DIV
    var nodeInfoDiv = $("#nodeInfoDiv");
    // 节点路径
    var zkNodePath = $("#zkNodePath");
    // 节点创建时的zxid
    var nodeInfoCzxid = $("#nodeInfoCzxid");
    // 节点最新一次更新发生时的zxid
    var nodeInfoMzxid = $("#nodeInfoMzxid");
    // 节点创建时的时间戳
    var nodeInfoCtime = $("#nodeInfoCtime");
    // 节点最新一次更新发生时的时间戳
    var nodeInfoMtime = $("#nodeInfoMtime");
    // 节点数据的更新次数
    var nodeInfoVersion = $("#nodeInfoVersion");
    // 其子节点的更新次数
    var nodeInfoCversion = $("#nodeInfoCversion");
    // 节点ACL(授权信息)的更新次数(aclVersion)
    var nodeInfoAversion = $("#nodeInfoAversion");
    // ephemeralOwner
    var nodeInfoEphemeralOwner = $("#nodeInfoEphemeralOwner");
    // 节点数据的字节数
    var nodeInfoDataLength = $("#nodeInfoDataLength");
    // 子节点个数
    var nodeInfoNumChildren = $("#nodeInfoNumChildren");
    // pzxid
    var nodeInfoPzxid = $("#nodeInfoPzxid");
    // 节点数据
    var nodeInfoData = $("#nodeInfoData");
    // 节点数据(字符串格式)
    var nodeInfoDataStr = $("#nodeInfoDataStr");
    
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
                    // 设置节点信息
                    _this.setNodeInfo(false);
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
                //noinspection JSUnusedLocalSymbols
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
                        if(data.success == true && data.result) {
                            //noinspection JSPrimitiveTypeWrapperUsage
                            data.result.nodePath = node.id;
                        } else {
                            data.result = false;
                        }
                        // 设置节点信息
                        _this.setNodeInfo(data.result);
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
        // 设置节点信息
        _this.setNodeInfo(false);
    };

    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
    };

    // ---------------------------------------------------------------------------------------------------------
    // 设置节点信息
    this.setNodeInfo = function (nodeInfo) {
        if(!nodeInfo) {
            nodeInfoDiv.css("display", "none");
        } else {
            nodeInfoDiv.css("display", "block");
            zkNodePath.text(nodeInfo.nodePath);
            nodeInfoCzxid.text(nodeInfo.czxid);
            nodeInfoMzxid.text(nodeInfo.mzxid);
            nodeInfoCtime.text(nodeInfo.ctime);
            nodeInfoMtime.text(nodeInfo.mtime);
            nodeInfoVersion.text(nodeInfo.version);
            nodeInfoCversion.text(nodeInfo.cversion);
            nodeInfoAversion.text(nodeInfo.aclVersion);
            nodeInfoEphemeralOwner.text(nodeInfo.ephemeralOwner);
            nodeInfoDataLength.text(nodeInfo.dataLength);
            nodeInfoNumChildren.text(nodeInfo.numChildren);
            nodeInfoPzxid.text(nodeInfo.pzxid);
            nodeInfoData.text(nodeInfo.data);
            nodeInfoDataStr.text(nodeInfo.dataStr);
        }
    }
    
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