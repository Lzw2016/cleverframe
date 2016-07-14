/**
 * 页面Js对象定义
 */
var pageJs = function () {
    // 当前pageJs对象
    var _this = this;
    // 页面主面板
    var mainPanel = $("#mainPanel");
    // 页面菜单树
    var menuTree = $("#menuTree");
    // 多页签右键菜单
    var menuByTabs = $('#menuByTabs');
    // 多table页签的最外层容器
    var tabsCenter = $('#tabsCenter');

    // 获取菜单信息地址
    var menuUrl = globalPath.mvcPath + "/sys/findMenuByCategory?category=";
    menuUrl += encodeURIComponent("SYS模块菜单");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        // 页面左部，菜单区域
        mainPanel.layout("panel", "west").panel({
            region: "west",
            width: 200,
            title: "功能菜单",
            border: true,
            minWidth: 150,
            maxWidth: 300,
            split: true,
            tools: [{
                iconCls: "icon-reload",
                handler: function () {
                    menuTree.tree("reload");
                }
            }]
        });

        // 构造菜单树
        menuTree.tree({
            url: menuUrl,
            animate: false,
            checkbox: false,
            cascadeCheck: true,
            onlyLeafCheck: false,
            lines: true,
            dnd: false,
            onClick: function (node) {
                if (node.attributes.href.indexOf("/") != 0) {
                    return;
                }
                var url = "${pageScope.appPath}" + node.attributes.href;
                _this.addTab(node.text, url);
            }
        });

        // 菜单设置
        menuByTabs.menu({
            onClick: function (item) {
                var selectIndex = $(this).data("selectIndex");
                var tablist = tabsCenter.tabs('tabs');
                if (item.name == "close") {
                    tabsCenter.tabs('close', selectIndex);
                } else if (item.name == "closeOther") {
                    for (var i = tablist.length - 1; i >= 0; i--) {
                        if (i == selectIndex) {
                            continue;
                        }
                        tabsCenter.tabs('close', i);
                    }
                } else if (item.name == "closeAll") {
                    for (var j = tablist.length - 1; j >= 0; j--) {
                        tabsCenter.tabs('close', j);
                    }
                } else if (item.name == "refresh") {
                    _this.refreshTab();
                } else if ("openInBrowserNewTab" == item.name) {
                    _this.openInBrowserNewTab();
                }
            }
        });

        // 页签集合
        tabsCenter.tabs({
            fit: true,
            border: 'false',
            tools: '#tabsCenter-tools',
            onContextMenu: function (e, title, index) {
                e.preventDefault();
                if (index >= 0) {
                    tabsCenter.tabs("select", index);
                    menuByTabs.menu('show', {
                        left: e.pageX - 3,
                        top: e.pageY - 3
                    }).data("selectIndex", index);
                }
            }
        });
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

    /**
     * 以iframe方式增加页面
     * @param tabName 叶签名称
     * @param tabUrl 叶签url地址
     */
    this.addTab = function (tabName, tabUrl) {
        if (tabsCenter.tabs("exists", tabName)) {
            tabsCenter.tabs("select", tabName);
        } else {
            // 新的table叶签内容
            var content = "";
            if (tabUrl) {
                var id = "id" + Math.random();
                id = id.replace(".", "");
                content = "<iframe id='" + id + "' scrolling='auto' style='width:100%;height:100%;' frameborder='0' src='" + tabUrl + "'></iframe>";
            } else {
                content = "未定义页面路径！";
            }
            tabsCenter.tabs("add", {
                title: tabName,
                closable: true,
                content: content,
                tools: [{
                    iconCls: "icon-mini-refresh",
                    handler: function () {
                        if (id) {
                            document.getElementById(id).contentWindow.location.reload(true);
                        }
                    }
                }]
            });
        }
    };

    /**
     * 刷新页面
     */
    this.refreshTab = function () {
        var tab = tabsCenter.tabs('getSelected');
        if (tab) {
            var content = tab.panel('options')['content'];
            if (content) {
                content = $('<div></div>').html(content);
                var id = content.find("iframe").attr("id");
                document.getElementById(id).contentWindow.location.reload(true);
                content.remove();
            }
        }
    };

    /**
     * 在浏览器新窗口中打开
     */
    this.openInBrowserNewTab = function () {
        var tab = tabsCenter.tabs('getSelected');
        if (tab) {
            var content = tab.panel('options')['content'];
            if (content) {
                content = $('<div></div>').html(content);
                var url = content.find("iframe").attr("src");
                content.remove();
                window.open(url);
            }
        }
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 关闭页面
     */
    this.closeTab = function () {
        var tab = tabsCenter.tabs('getSelected');
        if (tab) {
            var index = tabsCenter.tabs('getTabIndex', tab);
            tabsCenter.tabs('close', index);
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