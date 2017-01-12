/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 代码格式化地址
    var formatUrl = globalPath.mvcPath + "/generator/codeformat/format.json";
    // 根据模版名称返回模版数据
    var getTemplateByNameUrl = globalPath.mvcPath + "/core/template/getTemplateByName.json";
    // 更新模版内容地址
    var updateTemplateUrl = globalPath.mvcPath + "/core/template/updateTemplate.json";
    // 获取代码模版信息
    var getCodeTemplateByNameUrl = globalPath.mvcPath + "/generator/codetemplate/getCodeTemplateByName.json";

    // 模版名称
    var paramTemplateName = null;

    // 保存
    var saveTemplate = $("#saveTemplate");
    // 重新加载
    var reloadTemplate = $("#reloadTemplate");
    // 格式化
    var formatTemplate = $("#formatTemplate");
    // 模版信息
    var infoTemplate = $("#infoTemplate");
    // 代码样式
    var codeStyle = $("#codeStyle");
    var codeStyleMenu = $("#codeStyleMenu");
    // 编辑器样式
    var editTheme = $("#editTheme");
    var editThemeMenu = $("#editThemeMenu");

    // 代码编辑器
    var codeTemplateContent = null;
    // 当前辑器代码语言
    var currentCodeType = null;
    // 模版数据
    var templateData = null;
    // 代码模版数据
    var codeTemplateData = null;

    // 代码模版信息页面
    var templateInfoDialog = $("#templateInfoDialog");
    var infoName = $("#infoName");
    var infoTemplateRef = $("#infoTemplateRef");
    var infoLocale = $("#infoLocale");
    var infoCodeType = $("#infoCodeType");
    var infoDescription = $("#infoDescription");
    var infoRemarks = $("#infoRemarks");
    var infoCreateBy = $("#infoCreateBy");
    var infoCreateDate = $("#infoCreateDate");
    var infoUpdateBy = $("#infoUpdateBy");
    var infoUpdateDate = $("#infoUpdateDate");
    var templateInfoDialogClose = $("#templateInfoDialogClose");
    // 最后一次保存时间
    var lastSaveTime = 0;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        paramTemplateName = _this.getUrlParam("templateName");
        _this.initMain();

        _this.dataBind();
        _this.eventBind();
    };

    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        if (!paramTemplateName) {
            $.messager.alert('提示', '缺少参数[模版名称]！', 'info', function () {
                // window.location.href = "";
            });
        }
        _this.loadInfoDataForUI();
    };

    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
        saveTemplate.linkbutton({
            onClick: function () {
                _this.saveTemplateUrl();
            }
        });
        reloadTemplate.linkbutton({
            onClick: function () {
                _this.loadInfoDataForUI();
            }
        });
        formatTemplate.linkbutton({
            onClick: function () {
                _this.codeFormat(codeTemplateData.codeType);
            }
        });
        infoTemplate.linkbutton({
            onClick: function () {
                templateInfoDialog.dialog("open");
            }
        });
        templateInfoDialogClose.linkbutton({
            onClick: function () {
                templateInfoDialog.dialog("close");
            }
        });
        $(document).keydown(function (e) {
            if (e.ctrlKey == true && e.keyCode == 83) {
                var time = new Date().getTime();
                if (time - lastSaveTime > (1000)) {
                    lastSaveTime = time;
                    _this.saveTemplateUrl();
                }
                return false;
            }
        });
    };

    // ---------------------------------------------------------------------------------------------------------

    this.initMain = function () {
        codeTemplateContent = CodeMirror.fromTextArea(document.getElementById("codeTemplateContent"), {
            lineNumbers: true,
            matchBrackets: true,
            styleActiveLine: true,
            indentUnit: 4,
            smartIndent: false,
            readOnly: false,
            keyMap: "sublime"
        });
        codeTemplateContent.setSize("auto", "auto");
        //codeTemplateContent.setSize("height", 800);
        // codeTemplateContent.setOption("theme", "cobalt");
        _this.setCodeEditTheme("cobalt");
        //noinspection JSUnusedLocalSymbols
        codeTemplateContent.setOption("extraKeys", {
            Tab: function (cm) {
                var spaces = new Array(cm.getOption("indentUnit") + 1).join(" ");
                cm.replaceSelection(spaces);
            }
        });

        templateInfoDialog.dialog({
            title: "模版信息",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            // minWidth: 850,
            // minHeight: 300,
            modal: true
        });

        codeStyleMenu.menu({
            hideOnUnhover: false,
            inline: true,
            onClick: function (item) {
                _this.setCodeEditType(item.text);
            }
        });
        editThemeMenu.menu({
            hideOnUnhover: false,
            inline: true,
            onClick: function (item) {
                _this.setCodeEditTheme(item.text);
            }
        });
    };

    // 重新加载代码模版数据
    this.loadInfoDataForUI = function () {
        _this.getInfoData(function (template, codeTemplate) {
            codeTemplateContent.setValue("");
            _this.setCodeEditType(codeTemplate.codeType);
            // 更新代码 内容
            if (template.content) {
                codeTemplateContent.setValue(template.content);
            }

            // 更新代码 信息
            infoName.text(codeTemplate.name + " (" + template.name + ")");
            infoTemplateRef.text(codeTemplate.templateRef);
            infoLocale.text(template.locale);
            infoCodeType.text(codeTemplate.codeType);
            infoCreateBy.text(template.createBy);
            infoCreateDate.text(template.createDate);
            infoUpdateBy.text(template.updateBy);
            infoUpdateDate.text(template.updateDate);
            infoDescription.html(codeTemplate.description);
            infoRemarks.html(codeTemplate.remarks);
        });
    };

    // 获取模版数据 callback(template, codeTemplate)
    this.getInfoData = function (callback) {
        var maskTarget = "body";
        $.mask({target: maskTarget, loadMsg: "正在加载，请稍候..."});
        var callbackTmp = function (codeTemplate) {
            $.ajax({
                type: "POST", dataType: "JSON", url: getTemplateByNameUrl, async: true, data: {name: codeTemplate.templateRef},
                success: function (data) {
                    $.unmask({target: maskTarget});
                    if (data.success) {
                        templateData = data.result;
                        if ($.isFunction(callback)) {
                            callback(templateData, codeTemplateData);
                        }
                    } else {
                        $.messager.alert("提示", data.failMessage, "warning");
                    }
                }
            });
        };
        $.ajax({
            type: "POST", dataType: "JSON", url: getCodeTemplateByNameUrl, async: true, data: {name: paramTemplateName},
            success: function (data) {
                if (data.success) {
                    codeTemplateData = data.result;
                    callbackTmp(data.result);
                } else {
                    $.unmask({target: maskTarget});
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
    };

    // 只更新模版内容
    this.saveTemplateUrl = function () {
        var maskTarget = "body";
        $.mask({target: maskTarget, loadMsg: "正在保存，请稍候..."});
        $.ajax({
            type: "POST", dataType: "JSON", url: updateTemplateUrl, async: true, data: {id: templateData.id, content: codeTemplateContent.getValue()},
            success: function (data) {
                if (data.success) {
                    // 保存成功
                    $.unmask({target: maskTarget});
                    // $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
                } else {
                    // 保存失败
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
    };

    // 格式化代码
    this.codeFormat = function (codeType) {
        var param = {codeType: codeType, code: codeTemplateContent.getValue()};
        if (codeType.toLowerCase() == "html" || codeType.toLowerCase() == "java" || codeType.toLowerCase() == "json"
            || codeType.toLowerCase() == "sql" || codeType.toLowerCase() == "xml") {
            var maskTarget = "body";
            $.mask({target: maskTarget, loadMsg: "正在保存，请稍候..."});
            $.ajax({
                type: "POST", dataType: "JSON", url: formatUrl, async: true, data: param,
                success: function (data) {
                    $.unmask({target: maskTarget});
                    if (data.success) {
                        codeTemplateContent.setValue(data.result);
                        // $.messager.show({title: '提示', msg: data.successMessage, timeout: 5000, showType: 'slide'});
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
            codeTemplateContent.setValue(code);
            $.messager.show({title: '提示', msg: "JavaScript代码格式化成功", timeout: 5000, showType: 'slide'});
        } else if (codeType.toLowerCase() == "css") {
            var options = {indent: '    '};
            param.code = cssbeautify(param.code, options);
            codeTemplateContent.setValue(param.code);
            $.messager.show({title: '提示', msg: "CSS代码格式化成功", timeout: 5000, showType: 'slide'});
        } else {
            $.messager.alert("提示", "不支持格式化的程序语言:" + codeType, "info");
        }
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

    // 设置编辑器主题
    this.setCodeEditTheme = function (theme) {
        var item = editThemeMenu.menu("findItem", theme);
        if (codeTemplateContent && item) {
            var oldTheme = codeTemplateContent.getOption("theme");
            var oldItem = editThemeMenu.menu("findItem", oldTheme);
            if (theme == oldTheme) {
                return;
            }
            if (oldItem) {
                editThemeMenu.menu('setIcon', {target: oldItem.target, iconCls: null});
            }
            editThemeMenu.menu('setIcon', {target: item.target, iconCls: 'icon-select'});
            codeTemplateContent.setOption("theme", theme);
        }
    };

    // 设置编辑器代码语言 codeType -> java c#
    this.setCodeEditType = function (codeType) {
        var item = codeStyleMenu.menu("findItem", codeType);
        if (codeTemplateContent && item) {
            var oldItem = codeStyleMenu.menu("findItem", currentCodeType);
            if (codeType == currentCodeType) {
                return;
            }
            if (oldItem) {
                codeStyleMenu.menu('setIcon', {target: oldItem.target, iconCls: null});
            }
            codeStyleMenu.menu('setIcon', {target: item.target, iconCls: 'icon-select'});
            currentCodeType = codeType;
            codeTemplateContent.setOption("mode", _this.getCodeMirrorMode(codeType));
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