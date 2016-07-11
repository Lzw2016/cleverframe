/**
 * jquery中各个事件执行顺序如下：
 * 1.ajaxStart(全局事件)
 * 2.beforeSend(局部事件)
 * 3.ajaxSend(全局事件)
 * 4.success(局部事件)
 * 5.ajaxSuccess(全局事件)
 * 6.error(局部事件)
 * 7.ajaxError (全局事件)
 * 8.complete(局部事件)
 * 9.ajaxComplete(全局事件)
 * 10.ajaxStop(全局事件)
 *
 * Created by LiZW on 2016-7-11.
 */

$(document).ajaxStart(function () {
    //console.log("ajaxStart");
});

$(document).ajaxSend(function (evt, request, settings) {
    //console.log("ajaxSend");
});

$(document).ajaxSuccess(function (evt, request, settings) {
    //console.log("ajaxSuccess");

    //console.log(request);
});

$(document).ajaxError(function (evt, request, settings) {
    //console.log("ajaxError");
    //if(request.status == 200){
    //    var url = "/CodeGenerator/mvc/sys/loginJsp"; //转向网页的地址
    //    var name = "登录"; //网页名称
    //    var iWidth = 600; //弹出窗口的宽度
    //    var iHeight = 400; //弹出窗口的高度
    //    var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
    //    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
    //    window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
    //}
});

$(document).ajaxComplete(function () {
    //console.log("ajaxComplete");
});

$(document).ajaxStop(function () {
    //console.log("ajaxStop");
});