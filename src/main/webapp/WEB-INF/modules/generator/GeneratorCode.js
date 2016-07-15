/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    var ID = 0;
    // 数据库结构节点属性 type - 节点类型
    var Type_DataBase = "DataBase";
    var Type_FolderTable = "FolderTable";
    var Type_FolderView = "FolderView";
    var Type_Table = "Table";
    var Type_View = "View";
    var Type_Column = "Column";

    // 数据库结构节点属性 isLoad - 是否已经加载子节点
    var IsLoad_True = true;
    var IsLoad_False = false;

    // 当前pageJs对象
    var _this = this;

    // 数据库元信息-数据库概要
    var findAllDataBaseSummaryURL = globalPath.mvcPath + "/generator/matedata/findAllDataBaseSummary.json";
    // 获取数据库表详细信息
    var getTableSchemaURL = globalPath.mvcPath + "/generator/matedata/getTableSchema.json";
    // 数据库表结构页面
    var tableSchemaHtmlURL = globalPath.mvcPath + "/generator/matedata/TableSchema.html";

    // 主页面
    var mainPanel = $("#mainPanel");
    // 数据库结构树
    var dataBaseTree = $("#dataBaseTree");

    // 代码模版树
    var codeTemplateTree = $("#codeTemplateTree");

    // 页面中部多页签
    var tabsCenter = $("#tabsCenter");
    // 页面中部工具栏
    var tabsCenterTools = $("#tabsCenterTools");
    // 页面中部工具栏-刷新
    var tabsCenterToolsRefreshTab = $("#tabsCenterToolsRefreshTab");
    // 页面中部工具栏-关闭
    var tabsCenterToolsCloseTab = $("#tabsCenterToolsCloseTab");
    // 页面中部工具栏-右键菜单
    var menuByTabs = $("#menuByTabs");


    // 数据库概要信息，包括数据库名，数据库包含的所有表名和所有视图名称
    var dataBaseSummary = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        // 页面左部
        mainPanel.layout("panel", "west").panel({
            region: "west",
            width: 240,
            title: "数据库",
            border: true,
            minWidth: 150,
            maxWidth: 300,
            split: true,
            tools: [{
                iconCls: "icon-reload",
                handler: function () {
                    // 获取 数据库概要信息
                    _this.getDataBaseSummary(dataBaseTree);
                }
            }]
        });

        // 页面右部
        mainPanel.layout("panel", "east").panel({
            region: "east",
            width: 200,
            title: "代码模版",
            border: true,
            minWidth: 150,
            maxWidth: 300,
            split: true,
            tools: [{
                iconCls: "icon-reload",
                handler: function () {

                }
            }]
        });

        // 数据库表结构树
        dataBaseTree.tree({
            onClick: function (node) {

            },
            onDblClick: function (node) {
                if (node.attributes.type == Type_Table || node.attributes.type == Type_View) {
                    // 查看表结构页面
                    var tabName, tabUrl;
                    tabName = node.attributes.tableName + "(" + node.attributes.schemaName + ")";
                    tabUrl = tableSchemaHtmlURL + "?schemaName=" + encodeURIComponent(node.attributes.schemaName) + "&tableName=" + encodeURIComponent(node.attributes.tableName);
                    _this.addTab(tabName, tabUrl);
                }
            },
            onBeforeExpand: function (node) {
                if (node.attributes.isLoad == IsLoad_True) {
                    return true;
                }
                // 展开节点之前的操作
                if (node.attributes.type == Type_Table || node.attributes.type == Type_View) {
                    // 加载表结构 或 视图结构
                    _this.getTableSchema(node.attributes.schemaName, node.attributes.tableName, dataBaseTree, node);
                    node.attributes.isLoad = IsLoad_True
                }
            },
            onBeforeSelect: function (node) {

            },
            onSelect: function (node) {

            },
            onContextMenu: function (e, node) {

            }
        });

        // 页面中部多页签
        tabsCenter.tabs({
            fit: true,
            border: 'false',
            tools: '#tabsCenterTools',
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

        // 菜单设置
        menuByTabs.menu({
            onClick: function (item) {
                var selectIndex = $(this).data("selectIndex");
                var tabList = tabsCenter.tabs('tabs');
                var j = 0;
                switch (item.name) {
                    case "close":
                        tabsCenter.tabs('close', selectIndex);
                        break;
                    case "closeOther" :
                        for (j = tabList.length - 1; j >= 0; j--) {
                            if (j == selectIndex) {
                                continue;
                            }
                            tabsCenter.tabs('close', j);
                        }
                        break;
                    case "closeAll":
                        for (j = tabList.length - 1; j >= 0; j--) {
                            tabsCenter.tabs('close', j);
                        }
                        break;
                    case "refresh":
                        _this.refreshTab();
                        break;
                    case "openInBrowserNewTab":
                        _this.openInBrowserNewTab();
                        break;
                }
            }
        });

        // 页面数据初始化
        _this.dataBind();
        // 界面事件绑定方法
        _this.eventBind();
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        // 获取 数据库概要信息
        _this.getDataBaseSummary(dataBaseTree);
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        // 刷新叶签
        tabsCenterToolsRefreshTab.click(function () {
            _this.refreshTab();
        });
        // 关闭叶签
        tabsCenterToolsCloseTab.click(function () {
            _this.closeTab();
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    // 获取 数据库概要信息
    this.getDataBaseSummary = function (dataBaseTree) {
        dataBaseSummary = null;
        $.ajax({
            type: "POST",
            //dataType: "JSON",
            url: findAllDataBaseSummaryURL,
            async: false,
            success: function (data) {
                if (data.success == true) {
                    dataBaseSummary = data.result;
                } else {
                    $.messager.alert("提示", data.failMessage, "error");
                }
            }
        });

        //id：绑定节点的标识值。
        //text：显示的节点文本。
        //iconCls：显示的节点图标CSS类ID。
        //state：节点状态，'open' 或 'closed'。
        //attributes：绑定该节点的自定义属性。
        var nodeArray = [];
        $(dataBaseSummary).each(function (index, schemaName) {
            var node = {};
            node.id = (ID ++);
            node.text = schemaName.schemaName;
            node.state = "closed";//open
            node.iconCls = "icon-database";
            node.children = [];
            node.attributes = {};
            node.attributes.type = Type_DataBase;
            node.attributes.isLoad = IsLoad_True;
            node.attributes.schemaName = schemaName.schemaName;

            var nodeTable = {};
            nodeTable.id = (ID ++);
            nodeTable.text = "表";
            nodeTable.state = "closed";//open
            nodeTable.iconCls = "icon-folderTable";
            nodeTable.children = [];
            nodeTable.attributes = {};
            nodeTable.attributes.type = Type_FolderTable;
            nodeTable.attributes.isLoad = IsLoad_True;
            node.children.push(nodeTable);
            $(schemaName.tableNameList).each(function (i, tableName) {
                var n = {};
                n.id = (ID ++);
                n.text = tableName;
                n.state = "closed";
                n.iconCls = "icon-table";
                n.attributes = {};
                n.attributes.type = Type_Table;
                n.attributes.isLoad = IsLoad_False;
                n.attributes.schemaName = schemaName.schemaName;
                n.attributes.tableName = tableName;
                nodeTable.children.push(n);
            });

            var nodeView = {};
            nodeView.id = (ID ++);
            nodeView.text = "视图";
            nodeView.state = "closed";//open
            nodeView.iconCls = "icon-folderView";
            nodeView.children = [];
            nodeView.attributes = {};
            nodeView.attributes.type = Type_FolderView;
            nodeView.attributes.isLoad = IsLoad_True;
            node.children.push(nodeView);
            $(schemaName.viewNames).each(function (i, viewName) {
                var n = {};
                n.id = (ID ++);
                n.text = viewName;
                n.state = "closed";
                n.attributes = {};
                n.attributes.type = Type_View;
                n.attributes.isLoad = IsLoad_False;
                n.attributes.schemaName = schemaName.schemaName;
                n.attributes.tableName = viewName;
                nodeView.children.push(n);
            });
            nodeArray.push(node);
        });
        dataBaseTree.tree("loadData", nodeArray);
    };

    // 获取 数据库表详细信息
    this.getTableSchema = function (schemaName, tableName, dataBaseTree, node) {
        var tableSchema = null;
        var param = {};
        param.schemaName = schemaName;
        param.tableName = tableName;
        $.ajax({
            type: "POST",
            //dataType: "JSON",
            url: getTableSchemaURL,
            data: param,
            async: false,
            success: function (data) {
                if (data.success == true) {
                    tableSchema = data.result;
                } else {
                    $.messager.alert("提示", data.failMessage, "error");
                }
            }
        });
        $(node.children).each(function (index, n) {
            dataBaseTree.tree("remove", n.target);
        });
        node.attributes.schemaName = tableSchema.schemaName;
        node.attributes.tableName = tableSchema.tableName;
        node.attributes.engine = tableSchema.engine;
        node.attributes.autoIncrement = tableSchema.autoIncrement;
        node.attributes.charset = tableSchema.charset;
        node.attributes.collation = tableSchema.collation;
        node.attributes.description = tableSchema.description;
        node.attributes.rowCount = tableSchema.rowCount;
        node.attributes.avgRowLength = tableSchema.avgRowLength;
        node.attributes.dataLength = tableSchema.dataLength;
        node.attributes.createTime = tableSchema.createTime;
        node.text = tableSchema.tableName + "(" + tableSchema.description + ")";
        var nodeArray = [];
        $(tableSchema.columnList).each(function (index, column) {
            var n = {};
            n.id = (ID++);
            n.text = column.columnName;
            n.state = "open";
            n.iconCls = _this.getColumnImage(column.dataType);
            n.attributes = {};
            n.attributes.type = Type_Column;
            n.attributes.isLoad = IsLoad_True;
            n.attributes.schemaName = column.schemaName;
            n.attributes.tableName = column.tableName;
            n.attributes.columnName = column.columnName;
            n.attributes.ordinalPosition = column.ordinalPosition;
            n.attributes.size = column.size;
            n.attributes.width = column.width;
            n.attributes.decimalDigits = column.decimalDigits;
            n.attributes.generated = column.generated;
            n.attributes.hidden = column.hidden;
            n.attributes.partOfForeignKey = column.partOfForeignKey;
            n.attributes.partOfIndex = column.partOfIndex;
            n.attributes.partOfPrimaryKey = column.partOfPrimaryKey;
            n.attributes.partOfUniqueIndex = column.partOfUniqueIndex;
            n.attributes.dataType = column.dataType;
            n.attributes.notNull = column.notNull;
            n.attributes.autoIncrement = column.autoIncrement;
            n.attributes.defaultValue = column.defaultValue;
            n.attributes.extra = column.extra;
            n.attributes.charset = column.charset;
            n.attributes.comment = column.comment;
            n.attributes.attributes = column.attributes;
            nodeArray.push(n);
        });
        param = {};
        param.parent = node.target;
        param.data = nodeArray;
        dataBaseTree.tree("append", param);
    };

    // 根据数据库列类型返回 对应的图标
    this.getColumnImage = function (dataType){
        if(!dataType || dataType == null || dataType == ""){
            return "icon-unknown";
        }
        dataType = dataType.toLowerCase();
        if (dataType.indexOf("char") >= 0) {
            return "icon-string";
        }
        if (dataType.indexOf("int") >= 0
            || dataType.indexOf("number") >= 0
            || dataType.indexOf("float") >= 0
            || dataType.indexOf("double") >= 0
            || dataType.indexOf("decimal") >= 0) {
            return "icon-number";
        }
        if (dataType.indexOf("date") >= 0
            || dataType.indexOf("time") >= 0
            || dataType.indexOf("year") >= 0) {
            return "icon-datetime";
        }
        if (dataType.indexOf("bool") >= 0) {
            return "icon-boolean";
        }
        if (dataType.indexOf("clob") >= 0) {
            return "icon-binary";
        }
        if (dataType.indexOf("lob") >= 0) {
            return "icon-lob";
        }
        if (dataType.indexOf("text") >= 0) {
            //return "icon-text";
            return "icon-lob";
        }
        return "icon-unknown";
    };

    //以iframe方式增加页面
    this.addTab = function (tabName, tabUrl) {
        if (tabsCenter.tabs("exists", tabName)) {
            tabsCenter.tabs("select", tabName);
        } else {
            var content = null;
            if (tabUrl && $.trim(tabUrl) != "") {
                var id = _this.getUUID(32, 16);
                content = "<iframe id='" + id + "' scrolling='auto' style='width:100%;height:100%;' frameborder='0' src='" + tabUrl + "'></iframe>";
            } else {
                content = "未定义页面路径！";
            }
            $("#tabsCenter").tabs("add", {
                title : tabName,
                closable : true,
                content : content,
                tools : [{
                    iconCls : "icon-mini-refresh",
                    handler : function(){
                        if(id){
                            document.getElementById(id).contentWindow.location.reload(true);
                        }
                        //window.open(tabUrl); // 在新窗口中打开
                    }
                }]
            });
        }
    };

    // 刷新页面
    this.refreshTab = function () {
        var tab = tabsCenter.tabs('getSelected');
        if (tab) {
            var content = tab.panel('options')['content'];
            if(content){
                content = $('<div></div>').html(content);
                var id = content.find("iframe").attr("id");
                document.getElementById(id).contentWindow.location.reload(true);
                content.remove();
            }
        }
    };

    // 关闭页面
    this.closeTab = function () {
        var tab = tabsCenter.tabs('getSelected');
        if (tab) {
            var index = tabsCenter.tabs('getTabIndex', tab);
            tabsCenter.tabs('close', index);
        }
    };

    // 获取一个UUID
    this.getUUID = function (len, radix) {
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var uuid = [], i;
        radix = radix || chars.length;
        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
        } else {
            // rfc4122, version 4 form
            var r;
            // rfc4122 requires these characters
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';
            // Fill in random data.  At i==19 set the high bits of clock sequence as
            // per rfc4122, sec. 4.1.5
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }
        return uuid.join('');
    };

    // 在浏览器新窗口中打开
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