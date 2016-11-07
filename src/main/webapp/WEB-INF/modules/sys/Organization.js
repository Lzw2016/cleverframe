/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        console.log(globalPath);
        console.log("页面初始化");

        _this.dataBind();
        _this.eventBind();
    };

    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        var websocket = new WebSocket("ws://localhost:8080/cleverframe/myHandler");
        websocket.onopen = function (evt) {
            console.log("连接成功");

            doSend("消息 李志伟");
        };
        websocket.onclose = function (evt) {
            console.log("连接断开");
        };
        websocket.onmessage = function (evt) {
            console.log("返回消息:" + evt.data);
        };
        websocket.onerror = function (evt) {
            console.log("发生错误:" + evt.data);
        };

        function doSend(message) {
            console.log("发送测试消息:" + message);
            websocket.send(message);
        }

        var i = 0;
        $("#test001").click(function () {
            doSend("李志伟" + i);
            i++;
        });
        $("#test002").click(function () {
            websocket.close();
        });
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