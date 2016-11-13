/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;
    // 获取所有的缓存名称
    var getAllEhCacheNamesUrl = globalPath.mvcPath + "/monitor/ehcache/getAllEhCacheNames.json";
    // 获取缓存管理器信息
    var getCacheManagerInfoUrl = globalPath.mvcPath + "/monitor/ehcache/getCacheManagerInfo.json";
    // 分页查询缓存数据
    var getCacheDataUrl = globalPath.mvcPath + "/monitor/ehcache/getCacheData.json";
    // 获取缓存信息
    var getCacheInfoUrl = globalPath.mvcPath + "/monitor/ehcache/getCacheInfo.json";
    // 移除缓存元素
    var removeElementUrl = globalPath.mvcPath + "/monitor/ehcache/removeElement.json";
    // 清除缓存数据
    var clearCacheUrl = globalPath.mvcPath + "/monitor/ehcache/clearCache.json";

    // 名称
    var cacheManagerName = $("#cacheManagerName");
    // 集群UUID
    var cacheManagerClusterUUID = $("#cacheManagerClusterUUID");
    // 状态
    var cacheManagerStatus = $("#cacheManagerStatus");
    // 显式命名的缓存管理(named)
    var cacheManagerNamed = $("#cacheManagerNamed");
    // 缓存磁盘存储路径
    var cacheManagerDiskStoreConfiguration = $("#cacheManagerDiskStoreConfiguration");
    // 缓存管理器配置xml
    var cacheManagerConfigurationXml = null;
    // 缓存管理器配置xml
    var cacheManagerConfigurationJson = null;

    // 缓存统计Div
    var cacheStatisticsDiv = $("#cacheStatisticsDiv");

    // 查询表单
    var searchForm = $("#searchForm");
    // 缓存名称
    var searchCacheName = $("#searchCacheName");
    // 缓存键
    var searchKey = $("#searchKey");
    // 所有缓存的名称
    var cacheNamesData = null;

    // 缓存数据表格
    var cacheDataTable = $("#cacheDataTable");
    // 查询所有
    var cacheDataTableSearchAll = $("#cacheDataTableSearchAll");
    // 删除元素
    var cacheDataTableDelete = $("#cacheDataTableDelete");
    // 清除所有
    var cacheDataTableClearAll = $("#cacheDataTableClearAll");
    // 第一次获取 分页查询缓存数据
    var firstGetCacheData = true;

    // json数据对话框
    var jsonViewDialog = $("#jsonViewDialog");
    // 数据查看高亮控件
    var jsonViewEdit = null;
    // 当前查询的缓存名称
    var queryCacheName = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {
        _this.initCacheManager();
        _this.initCacheStatistics();
        _this.initCacheContents();
        _this.initJsonViewDialog();
        _this.dataBind();
        _this.eventBind();
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        // 查询所有
        cacheDataTableSearchAll.click(function () {
            if (!searchForm.form("validate")) {
                return;
            }
            cacheDataTable.datagrid("load");
        });

        // 删除元素
        cacheDataTableDelete.click(function () {
            if (!searchForm.form("validate")) {
                return;
            }
            var row = cacheDataTable.datagrid("getSelected");
            if(row == null){
                $.messager.alert("提示", "请先选择删除的元素", "info");
                return;
            }
            if (queryCacheName == null || $.trim(queryCacheName) == "") {
                $.messager.alert("提示", "请先查询缓存数据", "info");
                return;
            }
            _this.removeCacheElement(queryCacheName, row.key);
        });

        // 清除所有
        cacheDataTableClearAll.click(function () {
            if (!searchForm.form("validate")) {
                return;
            }
            if (queryCacheName == null || $.trim(queryCacheName) == "") {
                $.messager.alert("提示", "请先查询缓存数据", "info");
                return;
            }
            _this.clearCacheAll(queryCacheName);
        });
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
    };

    // ---------------------------------------------------------------------------------------------------------
    // 初始化缓存管理器
    this.initCacheManager = function () {
        if (cacheManagerConfigurationXml == null) {
            // 编辑器-初始化
            cacheManagerConfigurationXml = CodeMirror.fromTextArea(document.getElementById("cacheManagerConfigurationXml"), {
                mode: 'application/xml',
                lineNumbers: true,
                matchBrackets: true,
                indentUnit: 4,
                readOnly: true
            });
            cacheManagerConfigurationXml.setSize("auto", "98%");
            //cacheManagerConfigurationXml.setOption("theme", "cobalt");
        }
        if (cacheManagerConfigurationJson == null) {
            // 编辑器-初始化
            cacheManagerConfigurationJson = CodeMirror.fromTextArea(document.getElementById("cacheManagerConfigurationJson"), {
                mode: 'application/json',
                lineNumbers: true,
                matchBrackets: true,
                indentUnit: 4,
                readOnly: true
            });
            cacheManagerConfigurationJson.setSize("auto", "98%");
            //cacheManagerConfigurationJson.setOption("theme", "cobalt");
        }

        $.ajax({
            type: "POST", dataType: "JSON", url: getCacheManagerInfoUrl, async: true,
            success: function (data) {
                if (data.success) {
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 1000, showType: 'slide'});
                    cacheManagerName.text(data.result.name);
                    cacheManagerClusterUUID.text(data.result.clusterUUID);
                    cacheManagerStatus.text(data.result.status);
                    cacheManagerNamed.text(data.result.named);
                    var configurationJson = JSON.parse(data.result.configurationJson);
                    cacheManagerDiskStoreConfiguration.text(configurationJson.diskStoreConfiguration.path + "  原始路径(" + configurationJson.diskStoreConfiguration.originalPath + ")");

                    cacheManagerConfigurationXml.setValue("");
                    cacheManagerConfigurationJson.setValue("");
                    cacheManagerConfigurationXml.setValue(data.result.configurationXml);
                    cacheManagerConfigurationJson.setValue(js_beautify(data.result.configurationJson, 4, ' '));
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
    };

    // 初始化缓存监控
    this.initCacheStatistics = function () {
        var ehCacheNames = null;
        $.ajax({
            type: "POST", dataType: "JSON", url: getAllEhCacheNamesUrl, async: false,
            success: function (data) {
                if (data.success) {
                    ehCacheNames = data.result;
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
        if (ehCacheNames == null) {
            return;
        }
        cacheNamesData = ehCacheNames;
        $(ehCacheNames).each(function (index, cacheName) {
            var param = {cacheName: cacheName};
            $.ajax({
                type: "POST", dataType: "JSON", url: getCacheInfoUrl, data: param, async: false,
                success: function (data) {
                    if (data.success) {
                        _this.addCacheStatisticsDivItem(data.result);
                    } else {
                        $.messager.alert("提示", data.failMessage, "warning");
                    }
                }
            });
        });
    };

    // 增加缓存监控 Div
    this.addCacheStatisticsDivItem = function (cacheInfo) {
        var uuid = _this.getUUID(32, 16);
        var html = [];
        html.push('<div class="cacheStatisticsDivItem" style="width: 600px;height: 375px;float: left;margin-top: 10px;margin-left: 15px;margin-bottom: 10px;">');
        html.push('    <div id="' + uuid + '">');
        html.push('        <div title="缓存概要信息">');
        html.push('            <table>');
        html.push('                <tbody>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">缓存名称:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsName' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">缓存状态:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsStatus' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">高速缓存唯一ID:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsGuid' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">是否支持搜索:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsSearchable' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">缓存元素个数:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsSize' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">缓存元素数量:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsObjectCount' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">集群支持批量加载:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsClusterBulkLoadEnabled' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">当前节点支持批量加载:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsNodeBulkLoadEnabled' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">是否禁用缓存:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsDisabled' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">是否是Terracotta集群:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsTerracottaClustered' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">缓存命中率(总):</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsCacheHits' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">内存缓存命中率:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsInMemoryHits' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">非堆存储缓存命中率:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsOffHeapHits' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">磁盘存储缓存命中率:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsOnDiskHits' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">缓存占用空间大小:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsSizeInBytes' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                <tr class="row">');
        html.push('                    <td class="label">准备写入缓的元素数量:</td>');
        html.push('                    <td class="value">');
        html.push('                        <label id="cacheStatisticsWriterQueueLength' + uuid + '"></label>');
        html.push('                    </td>');
        html.push('                </tr>');
        html.push('                </tbody>');
        html.push('            </table>');
        html.push('        </div>');
        html.push('        <div title="缓存配置信息">');
        html.push('            <textarea id="cacheStatisticsConfigurationXml' + uuid + '"></textarea>');
        html.push('        </div>');
        html.push('        <div title="缓存属性">');
        html.push('            <textarea id="cacheStatisticsConfigurationJson' + uuid + '"></textarea>');
        html.push('        </div>');
        html.push('        <div title="缓存命中率">');
        html.push('            <canvas id="cacheStatisticsChart' + uuid + '"></canvas>');
        html.push('        </div>');
        html.push('    </div>');
        html.push('    <div id="tabTools' + uuid + '">');
        html.push('        <a id="tabToolsReload' + uuid + '" href="javascript:void(0)"></a>');
        html.push('        <a id="tabToolClear' + uuid + '" href="javascript:void(0)"></a>');
        html.push('    </div>');
        html.push('</div>');
        html = html.join("");
        cacheStatisticsDiv.append(html);

        var cacheStatisticsConfigurationXml = null;
        var cacheStatisticsConfigurationJson = null;
        var cacheStatisticsChart = null;
        var cacheStatisticsChartData = null;
        var reloadData = function (cacheInfo) {
            var tmp;
            $("#cacheStatisticsName" + uuid).text(cacheInfo.name);
            $("#cacheStatisticsStatus" + uuid).text(cacheInfo.status);
            $("#cacheStatisticsGuid" + uuid).text(cacheInfo.guid);
            $("#cacheStatisticsSearchable" + uuid).text(cacheInfo.searchable);
            $("#cacheStatisticsSize" + uuid).text(cacheInfo.size);
            tmp = "总数量:" + cacheInfo.objectCount
                + " | 内存存储数量:" + cacheInfo.memoryStoreObjectCount
                + " | 非堆存储数量:" + cacheInfo.offHeapStoreObjectCount
                + " | 磁盘存储数量:" + cacheInfo.diskStoreObjectCount;
            $("#cacheStatisticsObjectCount" + uuid).text(tmp);
            $("#cacheStatisticsClusterBulkLoadEnabled" + uuid).text(cacheInfo.clusterBulkLoadEnabled);
            $("#cacheStatisticsNodeBulkLoadEnabled" + uuid).text(cacheInfo.nodeBulkLoadEnabled);
            $("#cacheStatisticsDisabled" + uuid).text(cacheInfo.disabled);
            $("#cacheStatisticsTerracottaClustered" + uuid).text(cacheInfo.terracottaClustered);
            tmp = (cacheInfo.cacheHitPercentage * 100).toFixed(2) + "% (未命中率:" + (cacheInfo.cacheMissPercentage * 100).toFixed(2) + "%)"
                + " | 命中次数:" + cacheInfo.cacheHits
                + " | 未命中次数:" + cacheInfo.cacheMisses;
            $("#cacheStatisticsCacheHits" + uuid).text(tmp);
            tmp = (cacheInfo.inMemoryHitPercentage * 100).toFixed(2)
                + "% | 命中次数:" + cacheInfo.inMemoryHits
                + " | 未命中次数:" + cacheInfo.inMemoryMisses;
            $("#cacheStatisticsInMemoryHits" + uuid).text(tmp);
            tmp = (cacheInfo.offHeapHitPercentage * 100).toFixed(2)
                + "% | 命中次数:" + cacheInfo.offHeapHits
                + " | 未命中次数:" + cacheInfo.offHeapMisses;
            $("#cacheStatisticsOffHeapHits" + uuid).text(tmp);
            tmp = (cacheInfo.onDiskHitPercentage * 100).toFixed(2)
                + "% | 命中次数:" + cacheInfo.onDiskHits
                + " | 未命中次数:" + cacheInfo.onDiskMisses;
            $("#cacheStatisticsOnDiskHits" + uuid).text(tmp);
            tmp = "占用内存:" + _this.getEasyStoreSize(cacheInfo.heapSizeInBytes)
                + " | 占用非堆存储:" + _this.getEasyStoreSize(cacheInfo.offHeapSizeInBytes)
                + " | 占用磁盘存储:" + _this.getEasyStoreSize(cacheInfo.diskSizeInBytes);
            $("#cacheStatisticsSizeInBytes" + uuid).text(tmp);
            $("#cacheStatisticsWriterQueueLength" + uuid).text(cacheInfo.writerQueueLength + " (最大限制值:" + cacheInfo.writerMaxQueueSize + ")");

            if (cacheStatisticsConfigurationXml != null) {
                cacheStatisticsConfigurationXml.setValue("");
                cacheStatisticsConfigurationXml.setValue(cacheInfo.configurationXml);
            }
            if (cacheStatisticsConfigurationJson != null) {
                cacheStatisticsConfigurationJson.setValue("");
                cacheStatisticsConfigurationJson.setValue(js_beautify(cacheInfo.configurationJson, 4, ' '));
            }
            if (cacheStatisticsChart != null && cacheStatisticsChartData != null) {
                cacheStatisticsChartData.datasets = [{
                    data: [cacheInfo.cacheHitPercentage, 1 - cacheInfo.cacheHitPercentage],
                    backgroundColor: ["#36A2EB", "#FF6384"],
                    hoverBackgroundColor: ["#36A2EB", "#FF6384"]
                }];
                cacheStatisticsChart.update();
            }
        };

        $("#tabToolsReload" + uuid).linkbutton({
            plain: true, iconCls: 'icon-reload', onClick: function () {
                // 刷新数据
                var param = {cacheName: cacheInfo.name};
                $.ajax({
                    type: "POST", dataType: "JSON", url: getCacheInfoUrl, data: param, async: false,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 1000, showType: 'slide'});
                            cacheInfo = data.result;
                            reloadData(cacheInfo);
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                    }
                });
            }
        });
        $("#tabToolClear" + uuid).linkbutton({
            plain: true, iconCls: 'icon-clearCache', onClick: function () {
                _this.clearCacheAll(cacheInfo.name);
            }
        });
        $("#" + uuid).tabs({
            fit: true, tools: '#tabTools' + uuid, border: true, pill: false, tabPosition: 'bottom', onSelect: function (title, index) {
                if (index == 1 && cacheStatisticsConfigurationXml == null) {
                    cacheStatisticsConfigurationXml = CodeMirror.fromTextArea(document.getElementById("cacheStatisticsConfigurationXml" + uuid), {
                        mode: 'application/xml',
                        lineNumbers: true,
                        matchBrackets: true,
                        indentUnit: 4,
                        readOnly: true
                    });
                    cacheStatisticsConfigurationXml.setSize("auto", "auto");
                    cacheStatisticsConfigurationXml.setValue(cacheInfo.configurationXml);
                }
                if (index == 2 && cacheStatisticsConfigurationJson == null) {
                    cacheStatisticsConfigurationJson = CodeMirror.fromTextArea(document.getElementById("cacheStatisticsConfigurationJson" + uuid), {
                        mode: 'application/json',
                        lineNumbers: true,
                        matchBrackets: true,
                        indentUnit: 4,
                        readOnly: true
                    });
                    cacheStatisticsConfigurationJson.setSize("auto", "auto");
                    cacheStatisticsConfigurationJson.setValue(js_beautify(cacheInfo.configurationJson, 4, ' '));
                }
                if (index == 3 && cacheStatisticsChart == null) {
                    Chart.defaults.global.animation.duration = 800;
                    cacheStatisticsChartData = {
                        labels: ["命中", "未命中"],
                        datasets: [{
                            data: [cacheInfo.cacheHitPercentage, 1 - cacheInfo.cacheHitPercentage],
                            backgroundColor: ["#36A2EB", "#FF6384"],
                            hoverBackgroundColor: ["#36A2EB", "#FF6384"]
                        }]
                    };
                    cacheStatisticsChart = new Chart(document.getElementById("cacheStatisticsChart" + uuid).getContext("2d"), {
                        type: 'pie', data: cacheStatisticsChartData, options: {
                            responsive: true, title: {display: true, text: "缓存命中率分析[" + cacheInfo.name + "]"}, tooltips: {
                                //mode:'label',
                                callbacks: {
                                    label: function (tooltipItem, data) {
                                        var result = "未知";

                                        if (tooltipItem.index == 0) {
                                            // 命中提示信息
                                            result = "命中率:" + (data.datasets[0].data[tooltipItem.index] * 100).toFixed(2) + "%";
                                        }
                                        if (tooltipItem.index == 1) {
                                            // 未命中提示信息
                                            result = "未命中率:" + (data.datasets[0].data[tooltipItem.index] * 100).toFixed(2) + "%";
                                        }
                                        return result;
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
        reloadData(cacheInfo);
    };

    // 初始化缓存内容搜索
    this.initCacheContents = function () {
        searchCacheName.combobox({
            required: true,
            panelHeight: 150,
            valueField: 'key',
            textField: 'value',
            value: (cacheNamesData != null && cacheNamesData.length >= 1) ? cacheNamesData[0] : null,
            editable: false
        });
        var data = [];
        $(cacheNamesData).each(function (index, name) {
            data.push({key: name, value: name});
        });
        searchCacheName.combobox("loadData", data);

        //noinspection JSUnusedLocalSymbols
        searchKey.textbox({
            icons: [{
                iconCls: 'icon-search',
                handler: function (e) {
                    // 查询数据
                    if (!searchForm.form("validate")) {
                        return;
                    }
                    cacheDataTable.datagrid("load");
                }
            }]
        });

        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        cacheDataTable.datagrid({
            url: getCacheDataUrl,
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: true,
            loadMsg: "正在加载，请稍候...",
            toolbar: "#cacheDataTableButtons",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150],
            onDblClickRow: function (rowIndex, rowData) {
            },
            onBeforeLoad: function (param) {
                if (firstGetCacheData == true) {
                    firstGetCacheData = false;
                    return false;
                }
                // 增加查询参数
                var paramArray = searchForm.serializeArray();
                $(paramArray).each(function () {
                    if (param[this.name]) {
                        if ($.isArray(param[this.name])) {
                            param[this.name].push(this.value);
                        } else {
                            param[this.name] = [param[this.name], this.value];
                        }
                    } else {
                        param[this.name] = this.value;
                    }
                });
                queryCacheName = param.cacheName;
            }
        });
    };

    this.initJsonViewDialog = function () {
        jsonViewDialog.dialog({
            title: "查看数据",
            closed: true,
            minimizable: false,
            maximizable: true,
            resizable: false,
            minWidth: 850,
            minHeight: 450,
            modal: true,
            //buttons: "#",
            onOpen: function () {
                if (jsonViewEdit != null) {
                    return;
                }
                // 编辑器-初始化,
                jsonViewEdit = CodeMirror.fromTextArea(document.getElementById("jsonViewEdit"), {
                    mode: "application/json",
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    readOnly: true
                });
                jsonViewEdit.setSize("auto", "auto");
                //jsonViewEdit.setOption("theme", "cobalt");
                jsonViewEdit.setValue("");
            }
        });
    };

    // 清除缓存所有数据
    this.clearCacheAll = function (cacheName) {
        $.messager.confirm("确认清除缓存", "您确认清除[" + cacheName + "]的所有缓存?", function (r) {
            if (r) {
                // 清除缓存
                var param = {cacheName: cacheName};
                $.ajax({
                    type: "POST", dataType: "JSON", url: clearCacheUrl, data: param, async: false,
                    success: function (data) {
                        if (data.success) {
                            $.messager.show({title: '提示', msg: data.successMessage, timeout: 1000, showType: 'slide'});
                            cacheDataTable.datagrid("reload");
                        } else {
                            $.messager.alert("提示", data.failMessage, "warning");
                        }
                    }
                });
            }
        });
    };

    // 清除缓存一个元素
    this.removeCacheElement = function (cacheName, key) {
        var param = {cacheName: cacheName, key: key};
        $.ajax({
            type: "POST", dataType: "JSON", url: removeElementUrl, data: param, async: false,
            success: function (data) {
                if (data.success) {
                    $.messager.show({title: '提示', msg: data.successMessage, timeout: 1000, showType: 'slide'});
                    cacheDataTable.datagrid("reload");
                } else {
                    $.messager.alert("提示", data.failMessage, "warning");
                }
            }
        });
    };

    // 打开查看对话框
    this.openJsonViewDialog = function (jobData) {
        jsonViewDialog.dialog("open");
        jsonViewEdit.setValue(jobData);
    };

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.jobDataFormatter = function (value, rowData, rowIndex) {
        if (value != "" && value != null && value.constructor == Object) {
            var jobData = JSON.stringify(value);
            jobData = js_beautify(jobData, 4, ' ');
            var uuid = _this.getUUID(32, 16);
            var aButton = $("<a id='" + uuid + "' href='javascript:void(0)'>查看</a>");
            $("body").on("click", "#" + uuid, function () {
                _this.openJsonViewDialog(jobData);
            });
            return $("<div></div>").append(aButton).html();
        } else {
            return value;
        }
    };

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.serializedSizeFormatter = function (value, rowData, rowIndex) {
        if (!value && value != 0) {
            return "未知";
        }
        return _this.getEasyStoreSize(value);
    };

    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.timeFormatter = function (value, rowData, rowIndex) {
        if (!value && value != 0) {
            return "未知";
        }
        return _this.getEasyTime(value);
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

    // 获取容易看的存储大小
    this.getEasyStoreSize = function (byte) {
        var result = byte;
        var tmp = byte / 8;
        if (tmp < 1) {
            return result.toFixed(2) + "byte";
        }
        result = tmp; // bit

        tmp = tmp / 1024;
        if (tmp < 1) {
            return result.toFixed(2) + "bit";
        }
        result = tmp; // KB

        tmp = tmp / 1024;
        if (tmp < 1) {
            return result.toFixed(2) + "KB";
        }
        result = tmp; // MB

        tmp = tmp / 1024;
        if (tmp < 1) {
            return result.toFixed(2) + "MB";
        }
        result = tmp; // GB

        tmp = tmp / 1024;
        if (tmp < 1) {
            return result.toFixed(2) + "GB";
        }
        result = tmp; // TB
        return result.toFixed(2) + "TB";
    };

    // 毫秒转可读的时间
    this.getEasyTime = function (timeMillisecond) {
        var millisecond = timeMillisecond % 1000;
        var second = Math.floor(timeMillisecond / 1000) % 60;
        var minute = Math.floor(timeMillisecond / 1000 / 60) % 60;
        var hour = Math.floor(timeMillisecond / 1000 / 60 / 60) % 24;
        var day = Math.floor(timeMillisecond / 1000 / 60 / 60 / 24) % 30;
        var month = Math.floor(timeMillisecond / 1000 / 60 / 60 / 24 / 30) % 12;
        var year = Math.floor(timeMillisecond / 1000 / 60 / 60 / 24 / 30 / 12);
        var result = "";
        if (year >= 1) {
            result = year + "年";
        }
        if (month >= 1) {
            result = result + month + "月";
        }
        if (day >= 1) {
            result = result + day + "天";
        }
        if (hour >= 1) {
            result = result + hour + "小时";
        }
        if (minute >= 1) {
            result = result + minute + "分钟";
        }
        if (second >= 1) {
            result = result + second + "秒";
        }
        if (millisecond >= 1) {
            result = result + millisecond + "毫秒";
        }
        if (result == "") {
            result = "0毫秒";
        }
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