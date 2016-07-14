/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    var ID = 0;
    // 数据库结构节点属性 type - 节点类型
    var Type_DataBase = "DataBase";
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


    // 数据库概要信息，包括数据库名，数据库包含的所有表名和所有视图名称
    var dataBaseSummary = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {

        dataBaseTree.tree({
            onClick: function (node) {

            },
            onDblClick: function (node) {

            },
            onBeforeExpand: function (node) {
                if(node.attributes.isLoad == IsLoad_True) {
                    return true;
                }
                // 展开节点之前的操作
                if(node.attributes.type == Type_Table || node.attributes.type == Type_View) {
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
                node.children.push(n);
            });
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
                node.children.push(n);
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