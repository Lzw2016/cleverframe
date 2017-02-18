/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 获取文档信息
    var docDocumentInfoUrl = globalPath.mvcPath + "/doc/docdocument/getDocDocument.json";
    // Emoji's 图片地址
    var emojiPath = globalPath.staticPath + "/Image/emoji/";
    // Twitter Emoji 图片地址
    var twemojiPath = globalPath.staticPath + "/Image/twemoji/72x72/";
    // katexJsUrl
    var katexJsUrl = globalPath.staticPath + "/KaTeX/katex.min";
    // katexCssUrl
    var katexCssUrl = globalPath.staticPath + "/KaTeX/katex.min";
    // 文档ID
    var docDocumentIdParam = null;
    // 编辑器显示对象
    var editormdView = null;
    // 文档信息
    var docDocumentInfo = null;
    // 文章标题
    var title = $("#title");
    // 作者
    var createBy = $("#createBy");
    // 创建时间
    var createDate = $("#createDate");
    // 更新人
    var updateBy = $("#updateBy");
    // 最后更新时间
    var updateDate = $("#updateDate");
    // 字数
    var numberOfWords = $("#numberOfWords");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        docDocumentIdParam = _this.getUrlParam("docDocumentId");
        if (!docDocumentIdParam) {
            $.messager.alert('提示', '缺少参数[文档ID]！', 'info', function () {
                // window.location.href = "";
            });
        }
        _this.initEditormdView();

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
    this.initEditormdView = function () {
        // You can custom Emoji's graphics files url path
        editormd.emoji = {path: emojiPath, ext: ".png"};

        // Twitter Emoji (Twemoji)  graphics files url path
        editormd.twemoji = {path: twemojiPath, ext: ".png"};

        // KaTeX
        editormd.katexURL = {js: katexJsUrl, css: katexCssUrl};

        // 加载浮层 - 显示
        // var maskTarget = "body";
        // $.mask({target: maskTarget, loadMsg: "正在加载，请稍候..."});
        $.ajax({
            type: "POST", dataType: "JSON", url: docDocumentInfoUrl, async: true, data: {id: docDocumentIdParam},
            success: function (data) {
                // $.unmask({target: maskTarget});
                if (data.success) {
                    docDocumentInfo = data.result;
                    _this.setHeader(docDocumentInfo);
                    var markdownContent = docDocumentInfo.content;
                    if ($.trim(markdownContent) == "") {
                        markdownContent = "空文档!";
                    }
                    editormdView = editormd.markdownToHTML("editormd", {
                        markdown: markdownContent,              //+ "\r\n" + $("#append-test").text(),
                        //htmlDecode      : true,               // 开启 HTML 标签解析(为了安全性)，默认不开启
                        htmlDecode: "style,script,iframe",      // you can filter tags decode
                        //toc             : false,
                        tocm: true,    // Using [TOCM]
                        tocContainer: "#custom-toc-container", // 自定义 ToC 容器层
                        //gfm             : false,
                        //tocDropdown     : true,
                        // markdownSourceCode : true, // 是否保留 Markdown 源码，即是否删除保存源码的 Textarea 标签
                        emoji: true,
                        taskList: true,
                        tex: true,                // 默认不解析
                        flowChart: true,          // 默认不解析
                        sequenceDiagram: true     // 默认不解析
                    });
                    _this.anchorLinkOffset();
                } else {
                    // $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
        //console.log("返回一个 jQuery 实例 =>", testEditormdView);
        // 获取Markdown源码
        //console.log(testEditormdView.getMarkdown());
    };

    // 设置页头信息
    this.setHeader = function (docDocumentInfo) {
        if (docDocumentInfo == null) {
            return;
        }
        title.text(docDocumentInfo.title);
        createBy.text("作者:" + docDocumentInfo.createBy);
        createDate.text("创建时间:" + docDocumentInfo.createDate);
        if (docDocumentInfo.updateBy) {
            updateBy.text("更新人:" + docDocumentInfo.updateBy);
        } else {
            updateBy.text("");
        }
        if (docDocumentInfo.updateDate) {
            updateDate.text("最后更新时间:" + docDocumentInfo.updateDate);
        } else {
            updateDate.text("");
        }
        if (docDocumentInfo.content) {
            numberOfWords.text("字数:" + docDocumentInfo.content.length);
        } else {
            numberOfWords.text("");
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

    // 锚点链接的偏移问题
    this.anchorLinkOffset = function () {
        var handler = function (hash) {
            var target = $(".content a[name='" + hash.slice(1) + "']");
            if (target.length == 1) {
                var top = target.offset().top - 50;
                if (top > 0) {
                    $('html,body').animate({scrollTop: top}, 500);
                }
            }
        };
        $(".menu a[href^='#'][href!='#']").click(function () {
            handler(this.hash);
            history.pushState({}, '', this.href);
            return false;
        });
        if (location.hash) {
            handler(location.hash);
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