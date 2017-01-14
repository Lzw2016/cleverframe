/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // Editor.md编辑器 lib 路径
    var editorPath = globalPath.staticPath + "/editor.md/lib/";
    // Emoji's 图片地址
    var emojiPath = globalPath.staticPath + "/Image/emoji/";
    // Twitter Emoji 图片地址
    var twemojiPath = globalPath.staticPath + "/Image/twemoji/72x72/";

    /**
     * 页面初始化方法
     */
    this.init = function () {
        // You can custom Emoji's graphics files url path
        editormd.emoji = {
            path: emojiPath,
            ext: ".png"
        };

        // Twitter Emoji (Twemoji)  graphics files url path
        editormd.twemoji = {
            path: twemojiPath,
            ext: ".png"
        };

        var editor = editormd("editormd", {
            width: "100%",
            height: "100%",
            // autoHeight: true,
            path: editorPath,
            emoji: true,
            theme: "dark",
            previewTheme: "dark",
            // editorTheme: "cobalt",
            editorTheme: "pastel-on-dark",
            dialogMaskOpacity: 0.5,       // 设置透明遮罩层的透明度，全局通用，默认值为0.1
            disabledKeyMaps: [
                "Ctrl-B", "F11", "F10"
            ],
            onload: function () {
                // setting signle key
                var keyMap = {
                    "Ctrl-T": function (cm) {
                        alert("Ctrl+T");
                    }
                };
                this.addKeyMap(keyMap);
            },
            taskList: true

        });
        // 工具栏自动固定定位的开启与禁用
        // editor.setToolbarAutoFixed(false);


        // console.log(globalPath);
        // console.log("页面初始化");

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