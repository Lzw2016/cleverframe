/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 根据字典类别查询字典地址
    var findDictTypeUrl = globalPath.mvcPath + "/core/dict/findDictByType.json?dictType=";
    // 新增资源信息
    var findAllMenuTypeUrl = globalPath.mvcPath + "/sys/menu/findAllMenuType.json";
    // 查询菜单树
    var findMenuTreeByTypeUrl = globalPath.mvcPath + "/sys/menu/findMenuTreeByType.json";
    // 增加菜单信息
    var addMenuUrl = globalPath.mvcPath + "/sys/menu/addMenu.json";
    // 删除菜单
    var deleteMenuUrl = globalPath.mvcPath + "/sys/menu/deleteMenu.json";

    // 新增菜单类型
    var menuTypeToolsAdd = $("#menuTypeToolsAdd");
    // 刷新菜单类型
    var menuTypeToolsReload = $("#menuTypeToolsReload");
    // 菜单类型
    var menuTypeList = $("#menuTypeList");
    // 菜单树
    var treeGrid = $("#treeGrid");
    // 菜单树 - 刷新
    var treeGridButtonsSearch = $("#treeGridButtonsSearch");
    // 菜单树 - 新增
    var treeGridButtonsAdd = $("#treeGridButtonsAdd");
    // 菜单树 - 编辑
    var treeGridButtonsEdit = $("#treeGridButtonsEdit");
    // 菜单树 - 删除
    var treeGridButtonsDel = $("#treeGridButtonsDel");

    // 新增类型对话框
    var addTypeDialog = $("#addTypeDialog");
    // 新增类型对话框 - 保存
    var addTypeDialogButtonsSave = $("#addTypeDialogButtonsSave");
    // 新增类型对话框 - 取消
    var addTypeDialogButtonsCancel = $("#addTypeDialogButtonsCancel");
    // 新增类型表单
    var addTypeForm = $("#addTypeForm");
    // 新增类型表单 - 上级菜单
    var addTypeParentId = $("#addTypeParentId");
    // 新增类型表单 - 菜单类别
    var addTypeMenuType = $("#addTypeMenuType");
    // 新增类型表单 - 菜单名称
    var addTypeName = $("#addTypeName");
    // 新增类型表单 - 菜单图标
    var addTypeIcon = $("#addTypeIcon");
    // 新增类型表单 - 打开方式
    var addTypeOpenMode = $("#addTypeOpenMode");
    // 新增类型表单 - 排序
    var addTypeSort = $("#addTypeSort");
    // 新增类型表单 - 菜单地址
    var addTypeResourcesId = $("#addTypeResourcesId");
    // 新增类型表单 - 备注
    var addTypeRemarks = $("#addTypeRemarks");

    // 新增对话框
    var addDialog = $("#addDialog");
    // 新增对话框 - 保存
    var addDialogButtonsSave = $("#addDialogButtonsSave");
    // 新增对话框 - 取消
    var addDialogButtonsCancel = $("#addDialogButtonsCancel");
    // 新增表单
    var addForm = $("#addForm");
    // 新增表单 - 上级菜单
    var addParentId = $("#addParentId");
    // 新增表单 - 菜单类别
    var addMenuType = $("#addMenuType");
    // 新增表单 - 菜单名称
    var addName = $("#addName");
    // 新增表单 - 菜单图标
    var addIcon = $("#addIcon");
    // 新增表单 - 打开方式
    var addOpenMode = $("#addOpenMode");
    // 新增表单 - 排序
    var addSort = $("#addSort");
    // 新增表单 - 菜单地址
    var addResourcesId = $("#addResourcesId");
    // 新增表单 - 备注
    var addRemarks = $("#addRemarks");

    // 当前选择菜单类别 - 显示文本
    var selectMenuTypeText = $("#selectMenuTypeText");
    // 当前选择菜单类别
    var selectMenuType = null;


    /**
     * 页面初始化方法
     */
    this.init = function () {
        _this.addTypeDialogInit();
        _this.addDialogInit();
        _this.dataBind();
        _this.eventBind();
    };

    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        //noinspection JSUnusedLocalSymbols
        menuTypeList.datalist({
            url: findAllMenuTypeUrl,
            loadMsg: "正在加载，请稍候...",
            checkbox: false,
            lines: false,
            pagination: false,
            textFormatter: function (value, row, index) {
                return row.menu_type;
            },
            onSelect: function (index, row) {
                selectMenuType = row.menu_type;
                selectMenuTypeText.text(selectMenuType);
                treeGrid.treegrid("reload");
            }
        });

        //noinspection JSUnusedLocalSymbols
        treeGrid.treegrid({
            url: findMenuTreeByTypeUrl,
            idField: 'id',
            treeField: 'name',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#treeGridButtons",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {
                // _this.openEditDialog();
            },
            onBeforeLoad: function (row, param) {
                if (selectMenuType == null) {
                    return false;
                }
                param["menuType"] = selectMenuType;
            }
        });
    };

    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        menuTypeToolsAdd.click(function () {
            addTypeDialog.dialog("open");
            addTypeForm.form('reset');
            addTypeParentId.textbox("setValue", "-1");
            addTypeParentId.textbox("setText", "无");
        });

        menuTypeToolsReload.click(function () {
            menuTypeList.datalist("reload");
            selectMenuType = null;
            selectMenuTypeText.text("");
        });

        addTypeDialogButtonsSave.click(function () {
            _this.addMenuByType();
        });

        addTypeDialogButtonsCancel.click(function () {
            addTypeDialog.dialog("close");
        });

        treeGridButtonsSearch.click(function () {
            treeGrid.treegrid("reload");
        });

        treeGridButtonsAdd.click(function () {
            if (selectMenuType == null) {
                $.messager.alert("提示", "请选择菜单类别！", "info");
                return;
            }
            addDialog.dialog("open");
            addForm.form('reset');
            addMenuType.textbox("setValue", selectMenuType);
        });

        treeGridButtonsEdit.click(function () {
            if (selectMenuType == null) {
                $.messager.alert("提示", "请选择菜单类别！", "info");
                return;
            }
        });

        treeGridButtonsDel.click(function () {
            _this.deleteMenu();
        });

        addDialogButtonsSave.click(function () {
            _this.addMenu();
        });

        addDialogButtonsCancel.click(function () {
            addDialog.dialog("close");
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    // 初始化新增类型对话框
    this.addTypeDialogInit = function () {
        addTypeDialog.dialog({
            title: "新增菜单类型",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 830,
            minHeight: 310,
            modal: true,
            buttons: "#addTypeDialogButtons"
        });

        addTypeParentId.textbox({
            required: true,
            readonly: true,
            value: "-1"
        });
        addTypeMenuType.textbox({
            required: true,
            validType: 'length[0,255]'
        });
        addTypeName.textbox({
            required: true,
            validType: 'length[0,50]'
        });
        addTypeIcon.textbox({
            required: true,
            validType: 'length[0,50]'
        });
        addTypeOpenMode.combobox({
            required: true,
            url: findDictTypeUrl + encodeURIComponent("菜单打开方式") + "&hasSelectAll=false",
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        addTypeSort.numberspinner({
            required: true,
            value: 0,
            editable: true
        });
        addTypeResourcesId.textbox({
            required: true,
            validType: 'length[0,50]'
        });
        addTypeRemarks.textbox({
            required: true,
            validType: 'length[0,255]',
            multiline: true
        });
    };

    // 增加菜单类型
    this.addMenuByType = function () {
        addTypeForm.form("submit", {
            url: addMenuUrl,
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    addTypeDialog.dialog('close');
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                    menuTypeList.datalist('reload');
                    treeGrid.treegrid('reload');
                } else {
                    // 保存失败
                }
            }
        });
    };

    // 初始化新增对话框
    this.addDialogInit = function () {
        addDialog.dialog({
            title: "新增菜单",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            minWidth: 830,
            minHeight: 330,
            modal: true,
            buttons: "#addDialogButtons"
        });

        addParentId.textbox({
            required: true
        });
        addMenuType.textbox({
            readonly: true,
            required: true,
            value: "-",
            validType: 'length[0,255]'
        });
        addName.textbox({
            required: true,
            validType: 'length[0,50]'
        });
        addIcon.textbox({
            required: true,
            validType: 'length[0,50]'
        });
        addOpenMode.combobox({
            required: true,
            url: findDictTypeUrl + encodeURIComponent("菜单打开方式") + "&hasSelectAll=false",
            editable: false,
            valueField: 'value',
            textField: 'text',
            panelHeight: 80
        });
        addSort.numberspinner({
            required: true,
            value: 0,
            editable: true
        });
        addResourcesId.textbox({
            required: true,
            validType: 'length[0,50]'
        });
        addRemarks.textbox({
            required: true,
            validType: 'length[0,255]',
            multiline: true
        });
    };

    // 增加菜单类型
    this.addMenu = function () {
        addForm.form("submit", {
            url: addMenuUrl,
            success: function (data) {
                data = $.parseJSON(data);
                if (data.success) {
                    // 保存成功
                    addDialog.dialog('close');
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                    treeGrid.treegrid('reload');
                } else {
                    // 保存失败
                }
            }
        });
    };

    // 删除菜单
    this.deleteMenu = function () {
        var row = treeGrid.treegrid('getSelected');
        if (row == null) {
            $.messager.alert("提示", "请选择要删除的数据！", "info");
            return;
        }
        $.messager.confirm("确认删除", "您确定删除菜单?<br/>" + row.name, function (r) {
            if (r) {
                $.post(deleteMenuUrl, {"id": row.id}, function (data) {
                    if (data.success) {
                        // 删除成功
                        $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                        treeGrid.treegrid('reload');
                    } else {
                        // 删除失败
                    }
                }, "json");
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