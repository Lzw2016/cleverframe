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
    // 文档ID
    var docDocumentIdParam = null;
    // 编辑器显示对象
    var editormdView = null;
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

        // You can custom Emoji's graphics files url path
        editormd.emoji = {path: emojiPath, ext: ".png"};

        // Twitter Emoji (Twemoji)  graphics files url path
        editormd.twemoji = {path: twemojiPath, ext: ".png"};

        // KaTeX
        editormd.katexURL = {js: globalPath.staticPath + "/KaTeX/katex.min", css: globalPath.staticPath + "/KaTeX/katex.min"};


        // 加载浮层 - 显示
        // var maskTarget = "body";
        // $.mask({target: maskTarget, loadMsg: "正在加载，请稍候..."});
        $.ajax({
            type: "POST", dataType: "JSON", url: docDocumentInfoUrl, async: true, data: {id: docDocumentIdParam},
            success: function (data) {
                // $.unmask({target: maskTarget});
                if (data.success) {
                    var docDocumentInfo = data.result;
                    var editormdView = editormd.markdownToHTML("editormd", {
                        markdown: docDocumentInfo.content,      //+ "\r\n" + $("#append-test").text(),
                        //htmlDecode      : true,               // 开启 HTML 标签解析(为了安全性)，默认不开启
                        htmlDecode: "style,script,iframe",      // you can filter tags decode
                        //toc             : false,
                        tocm: true,    // Using [TOCM]
                        //tocContainer    : "#custom-toc-container", // 自定义 ToC 容器层
                        //gfm             : false,
                        //tocDropdown     : true,
                        // markdownSourceCode : true, // 是否保留 Markdown 源码，即是否删除保存源码的 Textarea 标签
                        emoji: true,
                        taskList: true,
                        tex: true,                // 默认不解析
                        flowChart: true,          // 默认不解析
                        sequenceDiagram: true     // 默认不解析
                    });
                } else {
                    // $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });

        //console.log("返回一个 jQuery 实例 =>", testEditormdView);
        // 获取Markdown源码
        //console.log(testEditormdView.getMarkdown());
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