/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 获取文档项目的所有文档
    var docDocumentTreeUrl = globalPath.mvcPath + "/doc/docdocument/findDocDocumentByProjectId.json";
    // 获取文档项目信息
    var docProjectInfoUrl = globalPath.mvcPath + "/doc/docproject/getDocProject.json";
    // 文档项目名称
    var docProjectIdParam = null;
    var docProjectInfo = null;

    // 主面板
    var mainPanel = $("#mainPanel");
    // 项目文档树
    var docDocumentTree = $("#docDocumentTree");
    // 项目文档树 - 加载中
    var docDocumentTreeLoading = $("#docDocumentTreeLoading");
    // 项目文档树 - 右键菜单
    var menuByDocDocumentTree = $("#menuByDocDocumentTree");
    // 中央Tab面板
    var tabsCenter = $("#tabsCenter");
    // 中央Tab面板 - 关闭
    var tabsCenterToolsCloseTab = $("#tabsCenterToolsCloseTab");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        docProjectIdParam = _this.getUrlParam("docProjectId");
        if (!docProjectIdParam) {
            $.messager.alert('提示', '缺少参数[文档项目ID]！', 'info', function () {
                // window.location.href = "";
            });
        }

        _this.initMain();
        _this.getDocProjectInfo(docProjectIdParam);

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
    this.initMain = function () {
        // 页面左部
        mainPanel.layout("panel", "west").panel({
            region: "west",
            width: 240,
            title: "文档目录",
            border: true,
            minWidth: 200,
            maxWidth: 350,
            split: true,
            collapsible: false,
            bodyCls: "westBodyCls",
            tools: [{
                iconCls: "icon-expandAll",
                handler: function () {
                }
            }, {
                iconCls: "icon-collapseAll",
                handler: function () {
                }
            }, {
                iconCls: "icon-addDocument",
                handler: function () {
                }
            }, {
                iconCls: "icon-edit",
                handler: function () {
                }
            }, {
                iconCls: "icon-remove",
                handler: function () {
                }
            }, {
                iconCls: "icon-reload",
                handler: function () {
                    docDocumentTree.tree("reload");
                }
            }]
        });

        // 初始化项目文档树
        //noinspection JSUnusedLocalSymbols
        docDocumentTree.tree({
            url: docDocumentTreeUrl,
            method: "POST",
            lines: true,
            loadFilter: function (data, parent) {
                if (data.success) {
                    return data.result;
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            },
            onBeforeLoad: function (node, param) {
                // 展开节点不加载数据
                if (param.id) {
                    return false;
                }
                param.isClose = "false";
                param.projectId = docProjectIdParam;
                docDocumentTreeLoading.css("display", "block");
            },
            onLoadSuccess: function (node, param) {
                docDocumentTreeLoading.css("display", "none");
            },
            onLoadError: function (arguments) {
                docDocumentTreeLoading.css("display", "none");
            },
            formatter: function (node) {
                var html = [];
                html.push('<a href="javascript:void(0)" class="documentTitle">');
                html.push('    ' + node.text);
                if (node.children && node.children.length) {
                    html.push('<span style="color:blue">(' + node.children.length + ')</span>');
                }
                html.push('</a>');
                return html.join('');
            },
            onClick: function (node) {
            },
            onDblClick: function (node) {
            },
            onBeforeExpand: function (node) {
            },
            onBeforeSelect: function (node) {
            },
            onSelect: function (node) {
            },
            onContextMenu: function (e, node) {
                e.preventDefault();
                docDocumentTree.tree("select", node.target);
                menuByDocDocumentTree.menu('show', {left: e.pageX - 3, top: e.pageY - 3}).data("selectNode", node);
            }
        });

        // 项目文档树右键菜单
        menuByDocDocumentTree.menu({
            onClick: function (item) {
                var selectNode = $(this).data("selectNode");
                switch (item.name) {
                    case "refresh":
                        docDocumentTree.tree("reload");
                        break;
                    case "addDocument" :
                        break;
                    case "edit":
                        break;
                    case "delete":
                        break;
                    case "properties":
                        break;
                    case "expand":
                        break;
                    case "collapse":
                        break;
                    case "expandAll":
                        break;
                    case "collapseAll":
                        break;
                }
            }
        });

        // 页面中部多页签
        tabsCenter.tabs({
            fit: true,
            border: 'false',
            tools: '#tabsCenterTools',
            onContextMenu: function (e, title, index) {
            }
        });
    };

    // 获取文档和项目名称
    this.getDocProjectInfo = function (docProjectId) {
        // 加载浮层 - 显示
        var maskTarget = "body";
        $.mask({target: maskTarget, loadMsg: "正在加载，请稍候..."});
        $.ajax({
            type: "POST", dataType: "JSON", url: docProjectInfoUrl, async: true, data: {id: docProjectId},
            success: function (data) {
                $.unmask({target: maskTarget});
                if (data.success) {
                    docProjectInfo = data.result;
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
    };

    // 获取URL参数
    this.getUrlParam = function (name) {
        //构造一个含有目标参数的正则表达式对象
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        //匹配目标参数
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return decodeURIComponent(r[2]);
        }
        return null;
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