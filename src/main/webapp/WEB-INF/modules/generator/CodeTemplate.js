/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 查询地址
    var findUrl = globalPath.mvcPath + "";
    // 新增保存地址
    var addUrl = globalPath.mvcPath + "/generator/codetemplate/addCodeTemplate.json";
    // 编辑保存地址
    var updateUrl = globalPath.mvcPath + "/generator/codetemplate/updateCodeTemplate.json";
    // 删除地址
    var delUrl = globalPath.mvcPath + "/generator/codetemplate/.json";
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";

    // 主页面
    var mainPanel = $("#mainPanel");
    // 代码模版树
    var codeTemplateTree = $("#codeTemplateTree");

    // 新增代码模版对话框
    var addCodeDialog = $("#addCodeDialog");
    // 新增代码模版表单
    var addCodeForm = $("#addCodeForm");
    // 模版所属分类
    var addCodeParentId = $("#addCodeParentId");
    // 代码模版名称
    var addCodeName = $("#addCodeName");
    // 代码语言
    var addCodeCodeType = $("#addCodeCodeType");
    // 模版语言
    var addCodeLocale = $("#addCodeLocale");
    // 配置描述
    var addCodeDescription = $("#addCodeDescription");
    // 备注信息
    var addCodeRemarks = $("#addCodeRemarks");
    // 模版内容
    var addCodeContent = null;
    // 新增代码模版对话框-按钮栏
    var addCodeDialogButtons = $("#addCodeDialogButtons");
    // 新增代码模版对话框-按钮栏 保存
    var addCodeDialogButtonsSave = $("#addCodeDialogButtonsSave");
    // 新增代码模版对话框-按钮栏 取消
    var addCodeDialogButtonsCancel = $("#addCodeDialogButtonsCancel");

    // 新增模版分类对话框
    var addCategoryDialog = $("#addCategoryDialog");
    // 新增模版分类表单
    var addCategoryForm = $("#addCategoryForm");
    // 所属分类
    var addCategoryParentId = $("#addCategoryParentId");
    // 模版分类名称
    var addCategoryName = $("#addCategoryName");
    // 分类说明
    var addCategoryDescription = $("#addCategoryDescription");
    // 备注信息
    var addCategoryRemarks = $("#addCategoryRemarks");
    // 新增模版分类对话框-按钮栏
    var addCategoryDialogButtons = $("#addCategoryDialogButtons");
    // 新增模版分类对话框-按钮栏 保存
    var addCategoryDialogButtonsSave = $("#addCategoryDialogButtonsSave");
    // 新增模版分类对话框-按钮栏 取消
    var addCategoryDialogButtonsCancel = $("#addCategoryDialogButtonsCancel");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        // 页面左部
        mainPanel.layout("panel", "west").panel({
            region: "west",
            width: 240,
            title: "代码模版",
            border: true,
            minWidth: 150,
            maxWidth: 300,
            split: true,
            collapsible: false,
            tools: [{
                iconCls: "icon-folderAdd",
                handler: function () {
                    addCategoryDialog.dialog("open");
                }
            },{
                iconCls: "icon-scriptAdd",
                handler: function () {
                    addCodeDialog.dialog("open");
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

                }
            }]
        });

        // 页面数据初始化
        _this.dataBind();
        // 界面事件绑定方法
        _this.eventBind();
        // 初始化 新增代码模版对话框
        _this.initAddCodeDialog();
        // 初始化 新增模版类别对话框
        _this.initAddCategoryDialog();
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
        // 新增代码模版对话框-按钮栏 保存
        addCodeDialogButtonsSave.click(function () {
            _this.addCodeData();
        });
        // 新增代码模版对话框-按钮栏 取消
        addCodeDialogButtonsCancel.click(function () {

        });
        // 新增模版分类对话框-按钮栏 保存
        addCategoryDialogButtonsSave.click(function () {
            _this.addCategoryData();
        });
        // 新增模版分类对话框-按钮栏 取消
        addCategoryDialogButtonsCancel.click(function () {

        });
    };

    // ---------------------------------------------------------------------------------------------------------

    // 初始化 新增代码模版对话框
    this.initAddCodeDialog = function (){
        addCodeDialog.dialog({
            title: "新增代码模版数据",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            minWidth: 850,
            minHeight: 330,
            modal: true,
            buttons: "#addCodeDialogButtons",
            onOpen: function() {
                if(addCodeContent != null) {
                    return;
                }
                // SQL编辑器-初始化,
                addCodeContent = CodeMirror.fromTextArea(document.getElementById("addCodeContent"), {
                    mode: "text/x-java",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: false
                });
                addCodeContent.setSize("auto", "auto");
                addCodeContent.setOption("theme", "cobalt");
                addCodeContent.setValue('\r\n');
            }
        });
        addCodeParentId.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addCodeName.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addCodeCodeType.combobox({
            required: false,
            url: findDictTypeUrl + encodeURIComponent("程序语言"),
            editable: true,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        addCodeLocale.combobox({
            required: false,
            url: findDictTypeUrl + encodeURIComponent("国际化语言后缀"),
            editable: true,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        addCodeDescription.textbox({
            required: true,
            validType: 'length[0,1000]',
            multiline: true
        });
        addCodeRemarks.textbox({
            validType: 'length[0,255]',
            multiline: true
        });
    };

    // 初始化 新增模版类别对话框
    this.initAddCategoryDialog = function () {
        addCategoryDialog.dialog({
            title: "新增模版类别数据",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 850,
            minHeight: 300,
            modal: true,
            buttons: "#addCategoryDialogButtons"
        });
        addCategoryParentId.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addCategoryName.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addCategoryDescription.textbox({
            required: true,
            validType: 'length[0,1000]',
            multiline: true
        });
        addCategoryRemarks.textbox({
            validType: 'length[0,255]',
            multiline: true
        });
    };

    /**
     * 保存代码模版数据
     */
    this.addCodeData = function () {
        addCodeForm.form("submit", {
            url: addUrl,
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    addCodeDialog.dialog('close');
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                } else {
                    // 保存失败
                }
            }
        });
    };

    /**
     * 保存模版分类数据
     */
    this.addCategoryData = function () {
        addCategoryForm.form("submit", {
            url: addUrl,
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    addCategoryDialog.dialog('close');
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                } else {
                    // 保存失败
                }
            }
        });
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