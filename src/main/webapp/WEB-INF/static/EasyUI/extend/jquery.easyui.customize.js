/**
 * EasyUI 自定义的扩展功能，包括Bug修改
 * Created by LiZW on 2016-7-10.
 */


/**
 * 针对panel window dialog三个组件拖动时会超出父级元素的修正
 * 如果父级元素的overflow属性为hidden，则修复上下左右个方向
 * 如果父级元素的overflow属性为非hidden，则只修复上左两个方向
 * @param left
 * @param top
 */
var easyuiPanelOnMove = function (left, top) {
    var parentObj = $(this).panel('panel').parent();
    if (left < 0) {
        $(this).window('move', {left: 1});
    }
    if (top < 0) {
        $(this).window('move', {top: 1});
    }
    var width = $(this).panel('options').width;
    var height = $(this).panel('options').height;
    //var right = left + width;
    //var buttom = top + height;
    var parentWidth = parentObj.width();
    var parentHeight = parentObj.height();
    if (parentObj.css("overflow") == "hidden") {
        if (left > parentWidth - width) {
            $(this).window('move', { "left": parentWidth - width });
        }
        if (top > parentHeight - height) {
            $(this).window('move', { "top": parentHeight - height });
        }
    }
};
$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;


/** ============================================================================================================================= */
