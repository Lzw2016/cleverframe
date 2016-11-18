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
        $("#fileupload001").fileupload({
            url : "http://localhost:8080/cleverframe/mvc/filemanager/manager/upload.json",
            type : "POST",
            dataType : "json",
            dropZone: $('#dropzone001'),
            submit : function (e, data) {
                $.messager.alert('提示','submit');
            },
            send : function (e, data) {
                $.messager.alert('提示','send');
            },
            done : function (e, data) {
                $.messager.alert('提示','done');
            },
            fail : function (e, data) {
                $.messager.alert('提示','fail');
            },
            progress : function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progressbar001-1').progressbar('setValue', progress);
            },
            progressall : function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progressbar001-2').progressbar('setValue', progress);
            },
            start : function (e) {
                $.messager.alert('提示','start');
            },
            stop : function (e) {
                $.messager.alert('提示','stop');
            },
            change : function (e, data) {
                $.messager.alert('提示','change');
            }
        });

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