/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 获取文档信息
    var docDocumentInfoUrl = globalPath.mvcPath + "/doc/docdocument/getDocDocument.json";
    // 文档保存
    var docDocumentSaveUrl = globalPath.mvcPath + "/doc/docdocument/updateDocDocument.json";
    // 获取文档历史版本
    var findDocHistoryUrl = globalPath.mvcPath + "/doc/dochistory/findByPage.json";
    // 还原文档到之前的一个版本
    var revertDocDocumentUrl = globalPath.mvcPath + "/doc/docdocument/revertDocDocument.json";

    // Editor.md编辑器 lib 路径
    var editorPath = globalPath.staticPath + "/editor.md/lib/";
    // Emoji's 图片地址
    var emojiPath = globalPath.staticPath + "/Image/emoji/";
    // Twitter Emoji 图片地址
    var twemojiPath = globalPath.staticPath + "/Image/twemoji/72x72/";
    // katexJsUrl
    var katexJsUrl = globalPath.staticPath + "/KaTeX/katex.min";
    // katexCssUrl
    var katexCssUrl = globalPath.staticPath + "/KaTeX/katex.min";
    // 编辑器实例
    var editor = null;
    // 编辑器实例 - 初始化成功
    var editorInitialized = false;
    // 文档ID
    var docDocumentIdParam = null;
    // 文档信息
    var docDocumentInfo = null;
    // 最后一次保存时间
    var lastSaveTime = 0;

    // 文档信息页面
    var documentInfoDialog = $("#documentInfoDialog");
    var infoTitle = $("#infoTitle");
    var infoRemarks = $("#infoRemarks");
    var infoCreateBy = $("#infoCreateBy");
    var infoCreateDate = $("#infoCreateDate");
    var infoUpdateBy = $("#infoUpdateBy");
    var infoUpdateDate = $("#infoUpdateDate");
    var documentInfoDialogClose = $("#documentInfoDialogClose");

    // 文档历史版本对话框
    var docHistoryDialog = $("#docHistoryDialog");
    // 文档历史版本树
    var docHistoryTree = $("#docHistoryTree");
    // 文档历史版本树 - 分页控件
    var docHistoryPagination = $("#docHistoryPagination");
    // 文档历史版本 - 编辑器
    var docContentHistoryView = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        docDocumentIdParam = _this.getUrlParam("docDocumentId");
        if (!docDocumentIdParam) {
            $.messager.alert('提示', '缺少参数[文档ID]！', 'info', function () {
                // window.location.href = "";
            });
        } else {
            _this.getDocumentInfo(docDocumentIdParam);
        }
        _this.initMain();
        _this.initDocumentInfoDialog();
        _this.initDocHistoryDialog();

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
        documentInfoDialogClose.linkbutton({
            onClick: function () {
                documentInfoDialog.dialog("close");
            }
        });
    };

    // ---------------------------------------------------------------------------------------------------------
    this.initMain = function () {
        // You can custom Emoji's graphics files url path
        editormd.emoji = {path: emojiPath, ext: ".png"};

        // Twitter Emoji (Twemoji)  graphics files url path
        editormd.twemoji = {path: twemojiPath, ext: ".png"};

        // KaTeX
        editormd.katexURL = {js: katexJsUrl, css: katexCssUrl};

        // noinspection JSUnusedLocalSymbols 编辑器初始化配置
        editor = editormd("editormd", {
            width: "100%",
            height: "100%",
            // autoHeight: true,
            path: editorPath,
            // theme: "dark",
            // previewTheme: "dark",
            // editorTheme: "pastel-on-dark",
            // markdown: "markdown",
            codeFold: true,
            //syncScrolling : false,
            saveHTMLToTextarea: true,       // 保存 HTML 到 Textarea
            searchReplace: true,
            //watch : false,                // 关闭实时预览
            htmlDecode: true,
            // htmlDecode: "style,script,iframe|on*", // 开启 HTML 标签解析(为了安全性)，默认不开启
            //toolbar  : false,             //关闭工具栏
            //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
            emoji: true,
            taskList: true,
            tocm: true,                     // Using [TOCM]
            tex: true,                      // 开启科学公式TeX语言支持，默认关闭
            flowChart: true,                // 开启流程图支持，默认关闭
            sequenceDiagram: true,          // 开启时序/序列图支持，默认关闭,
            //dialogLockScreen : false,     // 设置弹出层对话框不锁屏，全局通用，默认为true
            //dialogShowMask : false,       // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
            //dialogDraggable : false,      // 设置弹出层对话框不可拖动，全局通用，默认为true
            dialogMaskOpacity: 0.4,         // 设置透明遮罩层的透明度，全局通用，默认值为0.1
            //dialogMaskBgColor : "#000",   // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
            // 文件上传配置
            imageUpload: true,
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL: globalPath.mvcPath + "/filemanager/fileupload/doc/upload.json",
            // 自定义工具栏
            toolbarIcons: function () {
                return [
                    "save", "refresh", "|",
                    "undo", "redo", "|",
                    "bold", "del", "italic", "quote", "ucwords", "uppercase", "lowercase", "|",
                    "h1", "h2", "h3", "h4", "h5", "h6", "|",
                    "list-ul", "list-ol", "hr", "|",
                    "link", "reference-link", "image", "code", "preformatted-text", "code-block", "table", "datetime", "emoji", "html-entities", "pagebreak", "|",
                    "goto-line", "watch", "preview", "clear", "search", "|",
                    "help", "docInfo", "||", "history", "share"
                ];
            },
            // 工具栏图标 - 指定一个FontAawsome的图标类
            toolbarIconsClass: {
                docInfo: "fa-info-circle",
                save: "fa-save",
                refresh: "fa-refresh",
                history: "fa-history",
                share: "fa-share-alt"
            },
            lang: {
                // 自定义按钮的提示文本，即title属性
                toolbar: {
                    docInfo: "文档信息",
                    save: "保存(Ctrl+S)",
                    refresh: "重新加载(F5)",
                    history: "文档历史版本",
                    share: "分享",
                }
            },
            // 自定义工具栏按钮的事件处理
            toolbarHandlers: {
                /**
                 * @param {Object}      cm         CodeMirror对象
                 * @param {Object}      icon       图标按钮jQuery元素对象
                 * @param {Object}      cursor     CodeMirror的光标对象，可获取光标所在行和位置
                 * @param {String}      selection  编辑器选中的文本
                 */
                docInfo: function (cm, icon, cursor, selection) {
                    documentInfoDialog.dialog("open");
                },
                save: function (cm, icon, cursor, selection) {
                    _this.saveDocument(docDocumentIdParam);
                },
                refresh: function (cm, icon, cursor, selection) {
                    _this.getDocumentInfo(docDocumentIdParam);
                },
                history: function (cm, icon, cursor, selection) {
                    docHistoryDialog.dialog("open");
                },
                share: function (cm, icon, cursor, selection) {
                    alert("未实现该功能");
                }
            },
            disabledKeyMaps: ["F11", "F10"],
            onload: function () {
                // noinspection JSUnusedLocalSymbols 自定义快捷键
                var keyMap = {
                    "Ctrl-S": function (cm) {
                        _this.saveDocument(docDocumentIdParam);
                    },
                    "F5": function (cm) {
                        _this.getDocumentInfo(docDocumentIdParam);
                    }
                };
                this.addKeyMap(keyMap);

                // 设置初始值
                if (docDocumentInfo && docDocumentInfo.content && $.trim(editor.getValue()) == "") {
                    editor.setValue(docDocumentInfo.content);
                }
                editorInitialized = true;
            }
        });
        // 工具栏自动固定定位的开启与禁用
        // editor.setToolbarAutoFixed(false);
    };

    // 初始化文档信息对话框
    this.initDocumentInfoDialog = function () {
        documentInfoDialog.dialog({
            title: "文档信息",
            closed: true,
            minimizable: false,
            maximizable: false,
            resizable: false,
            // minWidth: 850,
            // minHeight: 300,
            modal: true
        });
    };

    // 初始化文档历史版本对话框
    this.initDocHistoryDialog = function () {
        docHistoryDialog.dialog({
            title: "文档保存历史 - 双击还原到指定历史文档",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            // minWidth: 850,
            // minHeight: 300,
            modal: true,
            onOpen: function () {
                if (docContentHistoryView == null) {
                    docContentHistoryView = CodeMirror.fromTextArea(document.getElementById("docContentHistoryView"), {
                        lineNumbers: true,
                        matchBrackets: true,
                        styleActiveLine: true,
                        indentUnit: 4,
                        smartIndent: false,
                        readOnly: true
                    });
                    docContentHistoryView.setSize("auto", "auto");
                }
                // 初始化数据
                var page = docHistoryPagination.pagination("options").pageNumber;
                var rows = docHistoryPagination.pagination("options").pageSize;
                _this.getDocHistory(docDocumentIdParam, page, rows);
            }
        });

        docHistoryTree.tree({
            lines: true,
            formatter: function (node) {
                var diffTime = _this.getDiffTime(node.attributes.createDate);
                var title = "";
                if (diffTime.years >= 1 || diffTime.months >= 1 || diffTime.days >= 1 || diffTime.hours >= 8) {
                    title = node.attributes.createDate;
                } else {
                    if (diffTime.days >= 1) {
                        title = title + diffTime.days + "天";
                    }
                    if (diffTime.hours >= 1) {
                        title = title + diffTime.hours + "小时";
                    }
                    if (diffTime.minutes >= 1) {
                        title = title + diffTime.minutes + "分";
                    }
                    if (diffTime.seconds >= 1) {
                        title = title + diffTime.seconds + "秒";
                    }
                    title = title + "之前";
                }
                var html = [];
                html.push('<div>');
                html.push('<span style="display:inline-block;width: 25px;color:blue;">' + node.attributes.sequence + ')</span>');
                html.push(title);
                // html.push('<span class="icon-revert" style="display:inline-block;width: 22px;height: 22px;"></span>');
                html.push('</div>');
                return html.join('');
            },
            onClick: function (node) {
            },
            onSelect: function (node) {
                if (node.attributes && node.attributes.content) {
                    docContentHistoryView.setValue(node.attributes.content);
                } else {
                    docContentHistoryView.setValue("");
                }
            },
            onDblClick: function (node) {
                $.messager.confirm("确认还原", "您确认还原文档?<br/>", function (r) {
                    if (r) {
                        docHistoryDialog.dialog("close");
                        _this.revertDocDocument(docDocumentIdParam, node.attributes.id);
                    }
                });
            }
        });

        docHistoryPagination.pagination({
            pageList: [10, 25, 40],
            pageSize: 25,
            layout: ['list', 'sep', 'first', 'prev', 'links', 'next', 'last', 'sep', 'refresh'],
            onSelectPage: function (pageNumber, pageSize) {
                _this.getDocHistory(docDocumentIdParam, pageNumber, pageSize);
            },
            onBeforeRefresh: function (pageNumber, pageSize) {
            },
            onRefresh: function (pageNumber, pageSize) {
            },
            onChangePageSize: function (pageSize) {
            }
        })
    };

    // 获取文档信息
    this.getDocumentInfo = function (docDocumentId) {
        // 加载浮层 - 显示
        var maskTarget = "body";
        $.mask({target: maskTarget, loadMsg: "正在加载，请稍候..."});
        $.ajax({
            type: "POST", dataType: "JSON", url: docDocumentInfoUrl, async: true, data: {id: docDocumentId},
            success: function (data) {
                $.unmask({target: maskTarget});
                if (data.success) {
                    docDocumentInfo = data.result;
                    // 重新设置界面数据
                    if (editorInitialized) {
                        editor.setValue(docDocumentInfo.content);
                    }
                    infoTitle.text(docDocumentInfo.title);
                    infoCreateBy.text(docDocumentInfo.createBy);
                    infoCreateDate.text(docDocumentInfo.createDate);
                    infoUpdateBy.text(docDocumentInfo.updateBy);
                    infoUpdateDate.text(docDocumentInfo.updateDate);
                    infoRemarks.html(docDocumentInfo.remarks);
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
    };

    // 保存文档信息
    this.saveDocument = function (docDocumentId) {
        var time = new Date().getTime();
        if (time - lastSaveTime > (1000)) {
            lastSaveTime = time;
            // 加载浮层 - 显示
            var maskTarget = "body";
            $.mask({target: maskTarget, loadMsg: "正在保存，请稍候..."});
            $.ajax({
                type: "POST", dataType: "JSON", url: docDocumentSaveUrl, async: true, data: {id: docDocumentId, content: editor.getValue()},
                success: function (data) {
                    $.unmask({target: maskTarget});
                    if (!data.success) {
                        $.messager.alert("提示", data.failMessage, "warning");
                    }
                }
            });
        }
    };

    // 获取文档历史版本
    this.getDocHistory = function (docDocumentId, page, rows, callback) {
        // 加载浮层 - 显示
        docHistoryPagination.pagination("loading");
        var maskTarget = docHistoryDialog.panel('body');
        $.mask({target: maskTarget, loadMsg: "正在加载，请稍候..."});
        $.ajax({
            type: "POST", dataType: "JSON", url: findDocHistoryUrl, async: true, data: {documentId: docDocumentId, page: page, rows: rows},
            success: function (data) {
                if ($.isFunction(callback)) {
                    callback(data);
                }
                $.unmask({target: maskTarget});
                docHistoryPagination.pagination("loaded");
                if (data.success) {
                    var result = data.result;
                    docHistoryPagination.pagination("refresh", {total: result.count, pageNumber: result.pageNo});
                    var list = [];
                    $(result.list).each(function (index, item) {
                        item.sequence = result.firstResult + index + 1;
                        var node = {
                            id: item.id,
                            text: item.createDate,
                            iconCls: "icon-history",
                            state: "open",
                            attributes: item
                        };
                        list.push(node);
                    });
                    docHistoryTree.tree("loadData", list);
                    docContentHistoryView.setValue("");
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
    };

    // 还原文档
    this.revertDocDocument = function (docDocumentId, historyId, callback) {
        // 加载浮层 - 显示
        var maskTarget = "body";
        $.mask({target: maskTarget, loadMsg: "正在处理，请稍候..."});
        $.ajax({
            type: "POST", dataType: "JSON", url: revertDocDocumentUrl, async: true, data: {documentId: docDocumentId, historyId: historyId},
            success: function (data) {
                if ($.isFunction(callback)) {
                    callback(data);
                }
                $.unmask({target: maskTarget});
                if (data.success) {
                    _this.getDocumentInfo(docDocumentId);
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

    // 获取时间差 dateSrt -> 2016-01-10 15:14:12
    this.getDiffTime = function (dateSrt) {
        var result = {};
        var diffTime = moment().diff(moment(dateSrt, "YYYY-MM-DD HH:mm:ss"));
        var duration = moment.duration(diffTime);
        result.years = Math.floor(duration.asYears());
        result.months = Math.floor(duration.asMonths()) % 12;
        result.days = Math.floor(duration.asDays()) % 30;
        result.hours = Math.floor(duration.asHours()) % 24;
        result.minutes = Math.floor(duration.asMinutes()) % 60;
        result.seconds = Math.floor(duration.asSeconds()) % 60;
        return result;
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