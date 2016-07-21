/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 查询地址
    var findAllUrl = globalPath.mvcPath + "/generator/codetemplate/findAllCodeTemplate.json";
    // 新增保存地址
    var addCategoryUrl = globalPath.mvcPath + "/generator/codetemplate/addCodeTemplateCategory.json";
    // 新增保存地址
    var addCodeUrl = globalPath.mvcPath + "/generator/codetemplate/addCodeTemplateCode.json";
    // 编辑保存地址
    var updateUrl = globalPath.mvcPath + "/generator/codetemplate/updateCodeTemplate.json";
    // 删除地址
    var delUrl = globalPath.mvcPath + "/generator/codetemplate/delCodeTemplate.json";
    // 查询子节点树
    var findChildNodeUrl = globalPath.mvcPath + "/generator/codetemplate/findChildNode.json";
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";
    // 根据模版名称返回模版数据
    var getTemplateByNameUrl = globalPath.mvcPath + "/core/template/getTemplateByName.json";
    // 只更新模版内容地址
    var onlyUpdateCoreTemplateContentUrl = globalPath.mvcPath + "/core/template/updateTemplate.json";
    // 代码格式化地址
    var formatUrl = globalPath.mvcPath + "/generator/codeformat/format.json";

    // 主页面
    var mainPanel = $("#mainPanel");
    // 页面中部多页签
    var tabsCenter = $("#tabsCenter");
    // 页面中部工具栏
    var tabsCenterTools = $("#tabsCenterTools");
    // 页面中部工具栏-关闭
    var tabsCenterToolsCloseTab = $("#tabsCenterToolsCloseTab");
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

    // 编辑代码模版对话框
    var editCodeDialog = $("#editCodeDialog");
    // 编辑代码模版表单
    var editCodeForm = $("#editCodeForm");
    // CodeTemplateId
    var editCodeCodeTemplateId = $("#editCodeCodeTemplateId");
    // TemplateId
    var editCodeTemplateId = $("#editCodeTemplateId");
    // 模版所属分类
    var editCodeParentId = $("#editCodeParentId");
    // 代码模版名称
    var editCodeName = $("#editCodeName");
    // 代码语言
    var editCodeCodeType = $("#editCodeCodeType");
    // 模版语言
    var editCodeLocale = $("#editCodeLocale");
    // 删除标记
    var editCodeDelFlag = $("#editCodeDelFlag");
    // 配置描述
    var editCodeDescription = $("#editCodeDescription");
    // 备注信息
    var editCodeRemarks = $("#editCodeRemarks");
    // 模版内容
    var editCodeContent = null;
    // 编辑代码模版对话框-按钮栏
    var editCodeDialogButtons = $("#editCodeDialogButtons");
    // 编辑代码模版对话框-按钮栏 保存
    var editCodeDialogButtonsSave = $("#editCodeDialogButtonsSave");
    // 编辑代码模版对话框-按钮栏 取消
    var editCodeDialogButtonsCancel = $("#editCodeDialogButtonsCancel");

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

    // 编辑模版分类对话框
    var editCategoryDialog = $("#editCategoryDialog");
    // 编辑模版分类表单
    var editCategoryForm = $("#editCategoryForm");
    // CodeTemplateId
    var editCategoryCodeTemplateId = $("#editCategoryCodeTemplateId");
    // TemplateId
    var editCategoryTemplateId = $("#editCategoryTemplateId");
    // 所属分类
    var editCategoryParentId = $("#editCategoryParentId");
    // 模版分类名称
    var editCategoryName = $("#editCategoryName");
    // 分类说明
    var editCategoryDescription = $("#editCategoryDescription");
    // 备注信息
    var editCategoryRemarks = $("#editCategoryRemarks");
    // 删除标记
    var editCategoryDelFlag = $("#editCategoryDelFlag");
    // 编辑模版分类对话框-按钮栏
    var editCategoryDialogButtons = $("#editCategoryDialogButtons");
    // 编辑模版分类对话框-按钮栏 保存
    var editCategoryDialogButtonsSave = $("#editCategoryDialogButtonsSave");
    // 编辑模版分类对话框-按钮栏 取消
    var editCategoryDialogButtonsCancel = $("#editCategoryDialogButtonsCancel");

    // 代码模版树右键菜单
    var menuByCodeTemplateTree = $("#menuByCodeTemplateTree");
    // 多页签中的代码编辑器
    var tabsCenterEditor = {};

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
            minWidth: 200,
            maxWidth: 350,
            split: true,
            collapsible: false,
            tools: [{
                iconCls: "icon-folderAdd",
                handler: function () {
                    addCategoryDialog.dialog("open");
                    addCategoryForm.form('reset');
                }
            },{
                iconCls: "icon-scriptAdd",
                handler: function () {
                    addCodeDialog.dialog("open");
                    addCodeForm.form('reset');
                    addCodeContent.setValue("");
                }
            }, {
                iconCls: "icon-edit",
                handler: function () {
                    _this.openEditDialog();
                }
            }, {
                iconCls: "icon-remove",
                handler: function () {
                    _this.delData();
                }
            }, {
                iconCls: "icon-reload",
                handler: function () {
                    // 初始化代码模版树的数据
                    _this.reloadCodeTemplateTree(codeTemplateTree);
                }
            }]
        });

        // 初始化代码模版树
        //noinspection JSUnusedLocalSymbols
        codeTemplateTree.tree({
            formatter: function (node) {
                // 节点类型(0:模版分类; 1:代码模版)
                if (node.attributes.nodeType == '1') {
                    return '<a href="javascript:void(0)">' + node.text + '</a>';
                }
                return node.text;
            },
            onClick: function (node) {

            },
            onDblClick: function (node) {
                // 节点类型(0:模版分类; 1:代码模版)
                if (node.attributes.nodeType == '0') {
                    _this.openEditDialog();
                } else if (node.attributes.nodeType == '1') {
                    _this.addTab(node.attributes.name, node.attributes);
                }
            },
            onBeforeExpand: function (node) {

            },
            onBeforeSelect: function (node) {

            },
            onSelect: function (node) {

            },
            onContextMenu: function (e, node) {
                e.preventDefault();
                codeTemplateTree.tree("select", node.target);
                menuByCodeTemplateTree.menu('show', {left: e.pageX - 3, top: e.pageY - 3}).data("selectNode", node);
            }
        });

        // 代码模版树右键菜单
        menuByCodeTemplateTree.menu({
            onClick: function (item) {
                var selectNode = $(this).data("selectNode");

                switch (item.name) {
                    case "refresh":
                        _this.reloadCodeTemplateTree(codeTemplateTree);
                        break;
                    case "addCategory" :
                        addCategoryDialog.dialog("open");
                        addCategoryForm.form('reset');
                        break;
                    case "addCode":
                        addCodeDialog.dialog("open");
                        addCodeForm.form('reset');
                        addCodeContent.setValue("");
                        break;
                    case "edit":
                        _this.openEditDialog();
                        break;
                    case "delete":
                        _this.delData();
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

        // 页面数据初始化
        _this.dataBind();
        // 界面事件绑定方法
        _this.eventBind();
        // 初始化 新增代码模版对话框
        _this.initAddCodeDialog();
        // 初始化 编辑代码模版对话框
        _this.initEditCodeDialog();
        // 初始化 新增模版类别对话框
        _this.initAddCategoryDialog();
        // 初始化 更新模版类别对话框
        _this.initEditCategoryDialog();
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        // 初始化代码模版树的数据
        _this.reloadCodeTemplateTree(codeTemplateTree);
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
            _this.addCodeDialog.dialog("close");
        });

        // 新增模版分类对话框-按钮栏 保存
        addCategoryDialogButtonsSave.click(function () {
            _this.addCategoryData();
        });
        // 新增模版分类对话框-按钮栏 取消
        addCategoryDialogButtonsCancel.click(function () {
            _this.addCategoryDialog.dialog("close");
        });

        // 编辑代码模版对话框-按钮栏 保存
        editCodeDialogButtonsSave.click(function () {
            _this.updateCodeData();
        });
        // 编辑代码模版对话框-按钮栏 取消
        editCodeDialogButtonsCancel.click(function () {
            _this.editCodeDialogButtonsCancel.dialog("close");
        });

        // 更新模版分类对话框-按钮栏 保存
        editCategoryDialogButtonsSave.click(function () {
            _this.updateCategoryData();
        });
        // 更新模版分类对话框-按钮栏 取消
        editCategoryDialogButtonsCancel.click(function () {
            _this.editCategoryDialogButtonsCancel.dialog("close");
        });

        // 关闭中部叶签
        tabsCenterToolsCloseTab.click(function () {
            _this.closeTab();
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    // 初始化代码模版树的数据
    this.reloadCodeTemplateTree = function (codeTemplateTree) {
        var codeTemplateData = [];
        $.ajax({
            type: "POST",
            //dataType: "JSON",
            url: findAllUrl,
            async: false,
            success: function (data) {
                codeTemplateData = data;
            }
        });
        codeTemplateTree.tree("loadData", codeTemplateData);
    };

    // 加载所属分类数
    this.reloadInputCodeTemplateTree = function (parentIdComboTree, fullPath, excludePath) {
        var param = {};
        param.fullPath = fullPath;
        param.excludePath = excludePath;
        $.ajax({
            type: "POST",
            dataType: "JSON",
            url: findChildNodeUrl,
            async: false,
            data: param,
            success: function (data) {
                parentIdComboTree.combotree("clear");
                parentIdComboTree.combotree("loadData", data);
            }
        });
    };

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
                _this.reloadInputCodeTemplateTree(addCodeParentId, "", "excludePath");

                if(addCodeContent != null) {
                    return;
                }
                // Java编辑器-初始化,
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
        addCodeParentId.combotree({
            required : true,
            editable : false,
            animate : false,
            checkbox : false,
            cascadeCheck : true,
            onlyLeafCheck : false,
            lines : true,
            dnd : false,
            valueField: 'id',
            textField: 'text'
        });
        addCodeName.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addCodeCodeType.combobox({
            required: true,
            url: findDictTypeUrl + encodeURIComponent("程序语言"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        addCodeLocale.combobox({
            required: true,
            url: findDictTypeUrl + encodeURIComponent("国际化语言后缀"),
            editable: false,
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

    // 初始化 编辑代码模版对话框
    this.initEditCodeDialog = function (){
        editCodeDialog.dialog({
            title: "更新代码模版数据",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            minWidth: 850,
            minHeight: 330,
            modal: true,
            buttons: "#editCodeDialogButtons",
            onOpen: function() {
                if(editCodeContent != null) {
                    return;
                }
                // Java编辑器-初始化,
                editCodeContent = CodeMirror.fromTextArea(document.getElementById("editCodeContent"), {
                    mode: "text/x-java",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: false
                });
                editCodeContent.setSize("auto", "auto");
                editCodeContent.setOption("theme", "cobalt");
                editCodeContent.setValue('\r\n');
            }
        });
        editCodeParentId.combotree({
            required : true,
            editable : false,
            animate : false,
            checkbox : false,
            cascadeCheck : true,
            onlyLeafCheck : false,
            lines : true,
            dnd : false,
            valueField: 'id',
            textField: 'text'
        });
        editCodeName.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        editCodeCodeType.combobox({
            required: true,
            url: findDictTypeUrl + encodeURIComponent("程序语言"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        editCodeLocale.combobox({
            required: true,
            url: findDictTypeUrl + encodeURIComponent("国际化语言后缀"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        editCodeDelFlag.combobox({
            required: true,
            url: findDictTypeUrl + encodeURIComponent("删除标记"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        editCodeDescription.textbox({
            required: true,
            validType: 'length[0,1000]',
            multiline: true
        });
        editCodeRemarks.textbox({
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
            buttons: "#addCategoryDialogButtons",
            onOpen: function() {
                _this.reloadInputCodeTemplateTree(addCategoryParentId, "", "excludePath");
            }
        });
        addCategoryParentId.combotree({
            required : true,
            editable : false,
            animate : false,
            checkbox : false,
            cascadeCheck : true,
            onlyLeafCheck : false,
            lines : true,
            dnd : false,
            valueField: 'id',
            textField: 'text'
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

    // 初始化 更新模版类别对话框
    this.initEditCategoryDialog = function () {
        editCategoryDialog.dialog({
            title: "新增模版类别数据",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 850,
            minHeight: 300,
            modal: true,
            buttons: "#editCategoryDialogButtons"
        });
        editCategoryParentId.combotree({
            required : true,
            editable : false,
            animate : false,
            checkbox : false,
            cascadeCheck : true,
            onlyLeafCheck : false,
            lines : true,
            dnd : false,
            valueField: 'id',
            textField: 'text'
        });
        editCategoryName.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        editCategoryDescription.textbox({
            required: true,
            validType: 'length[0,1000]',
            multiline: true
        });
        editCategoryRemarks.textbox({
            validType: 'length[0,255]',
            multiline: true
        });
        editCategoryDelFlag.combobox({
            required: true,
            url: findDictTypeUrl + encodeURIComponent("删除标记"),
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
    };

    // 打开编辑对话框
    this.openEditDialog = function () {
        var node = codeTemplateTree.tree("getSelected");
        if(node == null) {
            $.messager.alert("提示", "请选择要编辑的数据！", "info");
            return;
        }
        // 节点类型(0:模版分类; 1:代码模版)
        if(node.attributes.nodeType == "0") {
            _this.reloadInputCodeTemplateTree(editCategoryParentId, "", node.attributes.fullPath);

            editCategoryDialog.dialog('open');
            editCategoryForm.form('load', node.attributes);
            editCategoryCodeTemplateId.val(node.attributes.id);
            editCategoryTemplateId.val("-1");
        }
        if (node.attributes.nodeType == "1") {
            _this.reloadInputCodeTemplateTree(editCodeParentId, "", node.attributes.fullPath);

            var param = {};
            param.name = node.attributes.templateRef;
            $.ajax({
                type: "POST",
                dataType: "JSON",
                url: getTemplateByNameUrl,
                async: false,
                data: param,
                success: function (data) {
                    var template = data.result;
                    editCodeDialog.dialog('open');
                    // form必须先加载template,后加载node.attributes,需要node.attributes覆盖template中相同的属性
                    editCodeForm.form('load', template);
                    editCodeForm.form('load', node.attributes);
                    if (template.content && template.content != null) {
                        editCodeContent.setValue(template.content);
                    } else {
                        editCodeContent.setValue("");
                    }
                    editCodeTemplateId.val(template.id);
                    editCodeCodeTemplateId.val(node.attributes.id);
                }
            });
        }
    };

    /**
     * 保存代码模版数据
     */
    this.addCodeData = function () {
        addCodeForm.form("submit", {
            url: addCodeUrl,
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    addCodeDialog.dialog('close');
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});

                    _this.reloadCodeTemplateTree(codeTemplateTree);
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
            url: addCategoryUrl,
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    addCategoryDialog.dialog('close');
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});

                    _this.reloadCodeTemplateTree(codeTemplateTree);
                } else {
                    // 保存失败
                }
            }
        });
    };

    /**
     * 更新代码模版数据
     */
    this.updateCodeData = function () {
        editCodeForm.form("submit", {
            url: updateUrl,
            onSubmit: function(param){
                // 节点类型(0:模版分类; 1:代码模版)
                param.nodeType = "1";
            },
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    editCodeDialog.dialog('close');
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                    _this.reloadCodeTemplateTree(codeTemplateTree);
                } else {
                    // 保存失败
                }
            }
        });
    };

    /**
     * 更新模版类别数据
     */
    this.updateCategoryData = function () {
        editCategoryForm.form("submit", {
            url: updateUrl,
            onSubmit: function(param){
                // 节点类型(0:模版分类; 1:代码模版)
                param.nodeType = "0";
                param.codeType = "Category";
            },
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    editCategoryDialog.dialog('close');
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                    _this.reloadCodeTemplateTree(codeTemplateTree);
                } else {
                    // 保存失败
                }
            }
        });
    };

    /**
     * 删除模版
     */
    this.delData = function () {
        var node = codeTemplateTree.tree("getSelected");
        if (node == null) {
            $.messager.alert("提示", "请选择要删除的数据！", "info");
            return;
        }

        var message = "确定删除";
        // 节点类型(0:模版分类; 1:代码模版)
        if (node.attributes.nodeType == "0") {
            if (codeTemplateTree.tree("getChildren", node.target).length > 0) {
                $.messager.alert('提示', '不能删除非空的模版类别!', 'info');
                return;
            }
            message = "您确定删除模版分类?<br/>" + node.attributes.name;
        }
        if (node.attributes.nodeType == "1") {
            message = "您确定删除代码模版?<br/>" + node.attributes.name;
        }
        var param = {};
        param.codeTemplateName = node.attributes.name;
        $.messager.confirm("确认删除", message, function (r) {
            if (r) {
                $.post(delUrl, param, function (data) {
                    if (data.success) {
                        // 删除成功
                        $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        _this.reloadCodeTemplateTree(codeTemplateTree);
                    } else {
                        // 删除失败
                    }
                }, "json");
            }
        });
    };

    // 增加代码模版叶签
    this.addTab = function (tabName, codeTemplate) {
        if (tabsCenter.tabs("exists", tabName)) {
            tabsCenter.tabs("select", tabName);
        } else {
            var template = null;
            var param = {};
            param.name = codeTemplate.templateRef;
            $.ajax({
                type: "POST",
                dataType: "JSON",
                url: getTemplateByNameUrl,
                async: false,
                data: param,
                success: function (data) {
                    template = data.result;
                }
            });
            var content = [];
            content.push('<div id="layout_'+codeTemplate.uuid+'" class="easyui-layout" data-options="fit:true,border:false">');
            content.push('    <div data-options="region:\'north\',border:false" style="height:110px;background-color:#E0ECFF;">');
            content.push('        <div class="tabsCenterPageTop">');
            content.push('            <div class="row">');
            content.push('                <span class="column">');
            content.push('                    <label class="label">代码模版名称:</label>');
            content.push('                    <label class="value">' + codeTemplate.name + '</label>');
            content.push('                </span>');
            content.push('                 <span class="column">');
            content.push('                    <label class="label">代码语言:</label>');
            content.push('                    <label class="value">' + codeTemplate.codeType + '</label>');
            content.push('                </span>');
            content.push('                 <span class="columnLast">');
            content.push('                    <label class="label">模版语言:</label>');
            content.push('                    <label class="value">' + template.locale + '</label>');
            content.push('                </span>');
            content.push('                <a id="button_save' + codeTemplate.uuid + '" class="button" href="javascript:void(0)" ></a>');//保存
            content.push('                <a id="button_format' + codeTemplate.uuid + '" class="button" href="javascript:void(0)" ></a>');//格式化
            content.push('                <a id="button_refresh' + codeTemplate.uuid + '" class="button" href="javascript:void(0)" ></a>');// 刷新
            content.push('            </div>');
            content.push('            <div class="row">');
            content.push('                 <span class="columnLast">');
            content.push('                    <label class="label">模版说明:</label>');
            content.push('                    <label class="value">' + codeTemplate.description + '</label>');
            content.push('                </span>');
            content.push('            </div>');
            content.push('            <div class="row">');
            content.push('                 <span class="columnLast">');
            content.push('                    <label class="label">备注信息:</label>');
            content.push('                    <label class="value">' + codeTemplate.remarks + '</label>');
            content.push('                </span>');
            content.push('            </div>');
            content.push('        </div>');
            content.push('    </div>');
            content.push('    <div data-options="region:\'center\',border:false,fit:false">');
            content.push('        <textarea id="codeTemplate_' + codeTemplate.uuid + '"></textarea>');
            content.push('    </div>');
            content.push('</div>');
            var html = content.join("");
            tabsCenter.tabs("add", {
                title: tabName,
                closable: true,
                content: html
            });
            // 设置布局
            var layoutByTab = $("#layout_" + codeTemplate.uuid);
            layoutByTab.layout({
                fit: true,
                border: false
            });
            layoutByTab.layout("panel", "north").panel({
                region: "north",
                border: false
            });
            layoutByTab.layout("panel", "center").panel({
                region: "center",
                border: false,
                fit: false
            });

            // 设置保存按钮
            $("#button_save" + codeTemplate.uuid).linkbutton({
                iconCls: 'icon-save',
                onClick: function(){
                    _this.onlyUpdateCoreTemplateContent(template.id, tabName);
                }
            });
            $("#button_refresh" + codeTemplate.uuid).linkbutton({
                iconCls: 'icon-reload',
                onClick: function(){
                    $.ajax({
                        type: "POST",
                        dataType: "JSON",
                        url: getTemplateByNameUrl,
                        async: false,
                        data: param,
                        success: function (data) {
                            if(data.success){
                                tabsCenterEditor[tabName].setValue(data.result.content);
                                $.messager.show({title: '提示',  msg: "刷新成功", timeout: 5000, showType: 'slide'});
                            }
                        }
                    });
                }
            });
            $("#button_format" + codeTemplate.uuid).linkbutton({
                iconCls: 'icon-format',
                onClick: function(){
                    _this.codeFormat(codeTemplate.codeType, tabName);
                }
            });

            // Java编辑器-初始化,
            var editor = CodeMirror.fromTextArea(document.getElementById("codeTemplate_" + codeTemplate.uuid), {
                lineNumbers: true,
                matchBrackets: true,
                indentUnit: 4,
                readOnly: false
            });
            editor.setOption("mode", _this.getCodeMirrorMode(codeTemplate.codeType));
            editor.setSize("auto", "auto");
            //editor.setSize("height", 800);
            editor.setOption("theme", "cobalt");

            if(!template.content || template.content == null){
                editor.setValue("");
            } else {
                editor.setValue(template.content);
            }
            tabsCenterEditor[tabName] = editor;
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

    // 只更新模版内容
    this.onlyUpdateCoreTemplateContent = function(id, tabName){
        var editor = tabsCenterEditor[tabName];
        var param = {};
        param.id = id;
        param.content = editor.getValue();
        $.ajax({
            type: "POST",
            dataType: "JSON",
            url: onlyUpdateCoreTemplateContentUrl,
            async: false,
            data: param,
            success: function (data) {
                if (data.success) {
                    // 保存成功
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                } else {
                    // 保存失败
                }
            }
        });
    };

    // 格式化代码
    this.codeFormat = function (codeType, tabName) {
        var editor = tabsCenterEditor[tabName];
        var param = {};
        param.codeType = codeType;
        param.code = editor.getValue();
        if (codeType.toLowerCase() == "html" || codeType.toLowerCase() == "java" || codeType.toLowerCase() == "json"
            || codeType.toLowerCase() == "sql" || codeType.toLowerCase() == "xml") {
            $.ajax({
                type: "POST",
                dataType: "JSON",
                url: formatUrl,
                async: false,
                data: param,
                success: function (data) {
                    if (data.success) {
                        editor.setValue(data.result);
                        $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                    } else {
                        $.messager.alert("提示", data.failMessage, "error");
                    }
                }
            });
        } else if (codeType.toLowerCase() == "js" || codeType.toLowerCase() == "javascript") {
            var code = param.code.replace(/^\s+/, '');
            if (code.length > 0) {
                code = js_beautify(code, 4, ' ');
            }
            editor.setValue(code);
            $.messager.show({title: '提示', msg: "JavaScript代码格式化成功", timeout: 5000, showType: 'slide'});
        } else if (codeType.toLowerCase() == "css") {
            var options = {indent: '    '};
            param.code = cssbeautify(param.code, options);
            editor.setValue(param.code);
            $.messager.show({title: '提示', msg: "CSS代码格式化成功", timeout: 5000, showType: 'slide'});
        } else {
            $.messager.alert("提示", "不支持格式化的程序语言:" + codeType, "info");
        }
    };

    // 根据编程语言获取CodeMirror的Mode属性
    this.getCodeMirrorMode = function (codeType) {
        switch (codeType.toLowerCase()) {
            case "java":
                return "text/x-java";
            case "c#":
                return "text/x-csharp";
            case "xml":
                return "application/xml";
            case "html":
                return "text/html";
            case "jsp":
                return "application/x-jsp";
            case "css":
                return "text/css";
            case "sql":
                return "text/x-mysql";
            case "javascript":
                return "text/javascript";
            case "js":
                return "text/javascript";
            case "json":
                return "application/json";
        }
        return "text/x-java";
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