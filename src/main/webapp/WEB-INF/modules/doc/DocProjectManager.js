/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 分页查询所有文档项目信息
    var queryDocProjectUrl = globalPath.mvcPath + "/doc/docproject/queryDocProject.json";
    // 获取文档项目信息
    var getDocProjectUrl = globalPath.mvcPath + "/doc/docproject/getDocProject.json";
    // 增加文档项目
    var addDocProjectUrl = globalPath.mvcPath + "/doc/docproject/addDocProject.json";
    // 更新文档项目
    var updateDocProjectUrl = globalPath.mvcPath + "/doc/docproject/updateDocProject.json";
    // 删除文档项目
    var delDocProjectUrl = globalPath.mvcPath + "/doc/docproject/delDocProject.json";
    // 文档编辑页面
    var docProjectEditUrl = globalPath.mvcPath + "/doc/docproject/DocProjectEdit.html?docProjectId=";
    // 文档阅读页面
    var docProjectReadUrl = globalPath.mvcPath + "/doc/docproject/DocProjectRead.html?docProjectId=";
    // 分页插件
    var paginatorUl = $("#paginatorUl");
    // 文档项目信息Div
    var docProjectInfoDiv = $("#docProjectInfoDiv");

    /**
     * 页面初始化方法
     */
    this.init = function () {
        paginatorUl.jqPaginator({
            totalPages: 1,
            visiblePages: 8,
            currentPage: 1,
            first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
            prev: '<li class="prev"><a href="javascript:void(0);"><i class="arrow arrow2"><\/i>上一页<\/a><\/li>',
            next: '<li class="next"><a href="javascript:void(0);">下一页<i class="arrow arrow3"><\/i><\/a><\/li>',
            last: '<li class="last"><a href="javascript:void(0);">末页<\/a><\/li>',
            page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
            onPageChange: function (num, type) {
                _this.setDocProjectInfo(15, num);
                // 设置分页插件页数信息
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

    // this.queryDocProjectUrl

    // 设置文档项目信息
    this.setDocProjectInfo = function (pageSize, pageNo) {
        var loadingIndex = layer.msg(' 加载中......', {icon: 16}); // , shade: [0.3, "#CCC"]
        $.ajax({
            url: queryDocProjectUrl, data: {rows: pageSize, page: pageNo}, dataType: 'json', type: 'post',
            success: function (data) {
                layer.close(loadingIndex);
                if (data.success) {
                    docProjectInfoDiv.html("");
                    var html = [];
                    $(data.result.list).each(function (index, item) {
                        var id = _this.getUUID(32, 16);
                        html.push('<div class="box">');
                        html.push('    <div class="info">');
                        html.push('        <div class="title">' + item.name + '</div>');
                        html.push('        <div class="intro">' + $.trim(item.remarks) + ' | ' + $.trim(item.readme) + '</div>');
                        html.push('    </div>');
                        html.push('    <div class="status">' + item.createDate + '<span class="creator">@' + item.createBy + '</span></div>');
                        // html.push('    <div class="star"><i class="glyphicon glyphicon-star"></i></div>');
                        html.push('    <div class="tools">');
                        html.push('        <i class="glyphicon glyphicon-pencil" title="编辑"></i>');
                        html.push('        <i class="glyphicon glyphicon-export" title="转到编辑页面" onclick="pageJsObject.openDocProjectEdit(' + item.id + ')"></i>');
                        html.push('        <i class="glyphicon glyphicon-eye-open" title="阅读" onclick="pageJsObject.openDocProjectRead(' + item.id + ')"></i>');
                        html.push('        <i class="glyphicon glyphicon-trash" title="删除" onclick="pageJsObject.delDocProject(' + item.id + ')"></i>');
                        html.push('    </div>');
                        html.push('</div>');


                    });
                    html.push('<div class="box-to-add" onclick="pageJsObject.addDocProject()"></div>');
                    docProjectInfoDiv.html(html.join(" "));
                } else {
                    layer.alert(data.failMessage, {icon: 0});
                }
            },
            error: function () {
                layer.alert("操作失败，请重试！", {icon: 0});
            }
        });
    };

    // 删除文档信息
    this.delDocProject = function (docProjectId) {
        layer.confirm('确定删除文档，删除之后无法恢复？', {btn: ['确定', '取消']}, function () {
            $.ajax({
                url: delDocProjectUrl, data: {id: docProjectId}, dataType: 'json', type: 'post',
                success: function (data) {
                    if (data.success) {
                        layer.alert("删除成功", {icon: 1});
                        // TODO 刷新页面
                    } else {
                        layer.alert(data.failMessage, {icon: 0});
                    }
                }
            });
        }, function () {
            // 取消
        });
    };

    // 打开文档编辑页面
    this.openDocProjectEdit = function (docProjectId) {
        if (docProjectId) {
            window.open(docProjectEditUrl + docProjectId);
        }
    };

    // 打开文档项目阅读页面
    this.openDocProjectRead = function (docProjectId) {
        if (docProjectId) {
            window.open(docProjectReadUrl + docProjectId);
        }
    };

    // 新增项目文档
    this.addDocProject = function () {
        var html = [];
        html.push('<div id="docProjectAddDiv">');
        html.push('    <form class="form-horizontal" id="docProjectAddForm">');
        html.push('        <div class="form-group">');
        html.push('            <label for="docProjectName" class="col-sm-3 control-label">');
        html.push('                文档项目名称 <span style="color:#CC0000;">*</span>');
        html.push('            </label>');
        html.push('            <div class="col-sm-9">');
        html.push('                <input id="docProjectName" name="name" type="text" class="form-control" placeholder="文档项目名称">'); // data-rule="required"
        html.push('            </div>');
        html.push('        </div>');
        html.push('        <div class="form-group">');
        html.push('            <label for="docProjectReadme" class="col-sm-3 control-label">文档写项目说明</label>');
        html.push('            <div class="col-sm-9">');
        html.push('                <textarea id="docProjectReadme" name="readme" type="text" class="form-control" placeholder="文档写项目说明"></textarea>');
        html.push('            </div>');
        html.push('        </div>');
        html.push('        <div class="form-group">');
        html.push('            <label for="docProjectRemarks" class="col-sm-3 control-label"> 备 注 </label>');
        html.push('            <div class="col-sm-9">');
        html.push('                <textarea id="docProjectRemarks" name="remarks" type="text" class="form-control" placeholder="备注信息"></textarea>');
        html.push('            </div>');
        html.push('        </div>');
        html.push('    </form>');
        html.push('</div>');
        // 显示表单对话框
        layer.open({
            type: 1, skin: 'layui-layer-rim', area: ['580px', '380px'], content: html.join(" "), btn: ['新增', '取消'],
            yes: function (index, layero) {
                $('#docProjectAddForm').isValid(function (v) {
                    if (v) {
                        // TODO 发送新增文档项目请求
                        layer.close(index);
                    }
                });
            }
        });
        // 表单验证配置
        $('#docProjectAddForm').validator({
            fields: {
                'name': 'required;length(3~50)',
                'readme': 'length(0~600)',
                'remarks': 'length(0~230)'
            },
            msgMaker: function (opt) {
                // 自定义错误提示方式
                layer.tips(opt.msg, "#" + opt.element.id);
                console.log(opt);
            }
        });
    };

    // 获取一个UUID
    this.getUUID = function (len, radix) {
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var uuid = [], i;
        radix = radix || chars.length;
        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
        } else {
            // rfc4122, version 4 form
            var r;
            // rfc4122 requires these characters
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';
            // Fill in random data.  At i==19 set the high bits of clock sequence as
            // per rfc4122, sec. 4.1.5
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }
        return uuid.join('');
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