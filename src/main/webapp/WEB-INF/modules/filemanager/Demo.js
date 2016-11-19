/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    var uploadUrl = globalPath.mvcPath + "/filemanager/manager/upload.json";
    var uploadLazyUrl = globalPath.mvcPath + "/filemanager/manager/uploadLazy.json";

    /**
     * 页面初始化方法
     */
    this.init = function () {
        _this.fileupload001();
        _this.progressbar002();
        _this.fileupload003();

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

    this.fileupload001 = function () {
        $("#fileupload001").fileupload({
            url: uploadUrl,
            type: "POST",
            dataType: "json",
            dropZone: $('#dropzone001'),
            submit: function (e, data) {

                $.messager.alert('提示', 'submit');
            },
            send: function (e, data) {
                $.messager.alert('提示', 'send');
            },
            done: function (e, data) {
                $.messager.alert('提示', 'done');
            },
            fail: function (e, data) {
                $.messager.alert('提示', 'fail');
            },
            progress: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progressbar001-1').progressbar('setValue', progress);
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progressbar001-2').progressbar('setValue', progress);
            },
            start: function (e) {
                $.messager.alert('提示', 'start');
            },
            stop: function (e) {
                $.messager.alert('提示', 'stop');
            },
            change: function (e, data) {
                $.messager.alert('提示', 'change');
            }
        });
    };

    this.progressbar002 = function () {
        $("#fileupload002").fileupload({
            url: uploadUrl,
            type: "POST",
            dataType: "json",
            dropZone: $('#dropzone001'),
            add: function (e, data) {
                for (var i = 0; i < data.files.length; i++) {
                    var file = data.files[i];
                    $('<span/>').css("margin-right", "20px").text('文件：' + file.name).appendTo($("#progressDiv"));
                    $('<button/>').css("margin-right", "20px").text('上传').appendTo($("#progressDiv")).click(function () {
                        data.submit();
                    });
                    $('<button/>').css("margin-right", "20px").text('取消').appendTo($("#progressDiv")).click(function () {
                        data.abort();
                    });
                    var progressbarID = ("ID" + Math.random()).replace(".", "");
                    $('<div/>').attr("id", progressbarID).appendTo($("#progressDiv"));
                    $('#' + progressbarID).progressbar({
                        value: 0,
                        height: 22,
                        width: "500px"
                    });
                    data.progressbarID = progressbarID;
                    $('<br/>').appendTo($("#progressDiv"));
                }
            },
            progress: function (e, data) {
                if (data.hasOwnProperty("progressbarID")) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    $('#' + data.progressbarID).progressbar('setValue', progress);
                }
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progressbar002').progressbar('setValue', progress);
            },
            fail: function (e, data) {
                $.messager.alert('提示', 'fail');
            }
        });
    };

    this.fileupload003 = function () {

        if (_this.canReadFile() == false) {
            $.messager.alert('提示', '此浏览器不支持读文件！');
            return;
        }

        document.getElementById("fileupload003").addEventListener("change", function () {
            var file = document.getElementById("fileupload003").files[0];
            //创建md5Hash对象（基于CryptoJS）
            var md5Hash_1 = CryptoJS.algo.MD5.create();
            _this.readFile(
                file,
                function (chunks, currentChunk, e) {
                    var data = _this.arrayBufferToWordArray(e.target.result);
                    md5Hash_1.update(data);
                    var progress = parseInt(currentChunk * 100 / chunks, 10);
                    $('#progressbar003-1').progressbar('setValue', progress);
                    $("#md5003-1").text("计算中...");
                },
                function (md5) {
                    var md5 = md5Hash_1.finalize().toString(CryptoJS.enc.Hex);
                    $("#md5003-1").text(md5);
                }
            );

            //创建md5Hash对象（基于CryptoJS）
            var md5Hash_2 = CryptoJS.algo.MD5.create();
            _this.readFile(
                file,
                function (chunks, currentChunk, e) {
                    var array = new Uint8Array(e.target.result);
                    var data = CryptoJS.lib.WordArray.create(array);
                    md5Hash_2.update(data);
                    var progress = parseInt(currentChunk * 100 / chunks, 10);
                    $('#progressbar003-2').progressbar('setValue', progress);
                    $("#md5003-2").text("计算中...");
                },
                function (md5) {
                    var md5 = md5Hash_2.finalize().toString(CryptoJS.enc.Hex);
                    $("#md5003-2").text(md5);
                }
            );
        });
    };

    this.readFile = function (file, work, callback) {
        // 文件读取对象 HTML5
        var fileReader = new FileReader();
        // 分割文件对象
        var blobSlice = File.prototype.mozSlice || File.prototype.webkitSlice || File.prototype.slice;
        // 文件块大小 20KB
        var chunkSize = 20 * 1024;
        var chunks = Math.ceil(file.size / chunkSize);
        var currentChunk = 0;
        //每块文件读取完毕之后的处理
        fileReader.onload = function (e) {
            currentChunk++;
            var workResult = work(chunks, currentChunk, e);
            if (currentChunk < chunks) {
                // 继续读取文件
                loadNext();
            } else {
                // 文件读取完成
                callback(workResult);
            }
        };
        // 读取下一个文件块
        function loadNext() {
            var start = currentChunk * chunkSize;
            var end = start + chunkSize >= file.size ? file.size : start + chunkSize;
            var blob = file.slice(start, end);
            fileReader.readAsArrayBuffer(blob);
        }

        loadNext();
    };

    this.canReadFile = function () {
        try {
            // Check for FileApi
            if (typeof FileReader == "undefined") {
                return false;
            }
            // Check for Blob and slice api
            if (typeof Blob == "undefined") {
                return false;
            }
            var blob = new Blob();
            if (!blob.slice && !blob.webkitSlice) {
                return false;
            }
            // Check for Drag-and-drop
            if (!('draggable' in document.createElement('span'))) {
                return false;
            }
        } catch (e) {
            return false;
        }
        return true;
    };

    // 数据转换
    this.arrayBufferToWordArray = function (arrayBuffer) {
        var fullWords = Math.floor(arrayBuffer.byteLength / 4);
        var bytesLeft = arrayBuffer.byteLength % 4;
        var u32 = new Uint32Array(arrayBuffer, 0, fullWords);
        var u8 = new Uint8Array(arrayBuffer);
        var cp = [];
        for (var i = 0; i < fullWords; ++i) {
            cp.push(_this.swapendian32(u32[i]));
        }
        if (bytesLeft) {
            var pad = 0;
            for (var i = bytesLeft; i > 0; --i) {
                pad = pad << 8;
                pad += u8[u8.byteLength - i];
            }
            for (var i = 0; i < 4 - bytesLeft; ++i) {
                pad = pad << 8;
            }
            cp.push(pad);
        }
        return CryptoJS.lib.WordArray.create(cp, arrayBuffer.byteLength);
    };

    // 数据转换
    this.swapendian32 = function (val) {
        return (((val & 0xFF) << 24)
            | ((val & 0xFF00) << 8)
            | ((val >> 8) & 0xFF00)
            | ((val >> 24) & 0xFF)) >>> 0;
    };


    this.start = function () {
        $('#uploadLazyToLocal004').text("");
        $('#uploadToLocal004').text("");
        $('#progressbar004-1').progressbar('setValue', 0);
        $('#progressbar004-2').progressbar('setValue', 0);

        var files = $("#fileupload004").prop('files');
        var file = null;
        if (files.length > 0) {
            file = files[0];
        } else {
            $.messager.alert('提示', '未选择文件！');
        }
        var md5Hash = CryptoJS.algo.MD5.create();
        _this.readFile(
            file,
            function (chunks, currentChunk, e) {
                var array = new Uint8Array(e.target.result);
                var data = CryptoJS.lib.WordArray.create(array);
                md5Hash.update(data);
                var progress = parseInt(currentChunk * 100 / chunks, 10);
                $('#progressbar004-1').progressbar('setValue', progress);
            },
            function (md5) {
                var md5 = md5Hash.finalize().toString(CryptoJS.enc.Hex);
                _this.uploadLazyToLocal(file.name, md5, "1");
            }
        );
    };

    this.uploadLazyToLocal = function (fileName, fileDigest, digestType) {
        $.post(uploadLazyUrl,
            {fileName: fileName, fileDigest: fileDigest, digestType: digestType},
            function (data) {
                if (data.success == true) {
                    $('#uploadLazyToLocal004').text("秒传成功");
                } else {
                    $('#uploadLazyToLocal004').text("秒传失败");
                    _this.uploadToLocal();
                }
            }, "json");
    };

    this.uploadToLocal = function () {
        $('#fileupload004').fileupload({
            url: uploadUrl,
            type: "POST",
            dataType: "json",
            autoUpload: false,
            progress: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progressbar004-2').progressbar('setValue', progress);
            },
            done: function (e, data) {
                $('#uploadToLocal004').text("上传成功");
            },
            fail: function (e, data) {
                $.messager.alert('提示', '上传失败');
            },
            always: function (e, data) {
                //设置 utoUpload : false 不需要
                $('#fileupload004').fileupload('destroy');
            }
        });

        var jqXHR = $('#fileupload004').fileupload('send', {
            files: $("#fileupload004").prop('files')
        }).error(function (jqXHR, textStatus, errorThrown) {
            if (errorThrown === 'abort') {
                alert('已取消上传');
            }
        });

        /*
         $('button.cancel').click(function (e) {
         jqXHR.abort();
         });
         */
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