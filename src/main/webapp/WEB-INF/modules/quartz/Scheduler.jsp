<%--
  作者: LiZW
  时间: 2016-8-9 17:34
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%-- EasyUI --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${applicationScope.staticPath}/EasyUI/extend/jquery.easyui.customize.js"></script>

    <%-- CodeMirror --%>
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.js"></script>
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/lib/codemirror.css">
    <link rel="stylesheet" href="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/theme/cobalt.css">
    <script src="${applicationScope.staticPath}/CodeMirror/codemirror-5.15.2/mode/javascript/javascript.js"></script>

    <%--代码格式化JS--%>
    <script src="${applicationScope.staticPath}/CodeFormat/jsbeautify.js"></script>

    <%-- 加载自定义的全局JS文件 --%>
    <script type="text/javascript" src="${applicationScope.mvcPath}/core/globaljs/globalPath.js"></script>
    <%-- 当前页面的CSS、JS脚本 --%>
    <link rel="stylesheet" type="text/css" href="${applicationScope.modulesPath}/quartz/Scheduler.css">
    <script type="text/javascript" src="${applicationScope.modulesPath}/quartz/Scheduler.js"></script>
    <title>调度器(Scheduler)管理</title>
</head>
<body>

<div id="tabsCenter" class="easyui-tabs" data-options="fit:true,border:'false',pill:true">
    <div title="调度器信息">
        <div style="margin-right: 25px;margin-left: 25px;margin-top: 30px;">
            <table id="schedulerMetaDataTable">
                <tbody>
                <tr class="row">
                    <td class="label">
                        调度器总览:
                        <a id="schedulerReload" href="javascript:void(0)" class="easyui-linkbutton"></a>
                    </td>
                    <td class="value">
                        <pre id="summary"></pre>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">调度器版本:</td>
                    <td class="value">
                        <label id="version"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">调度器名称:</td>
                    <td class="value">
                        <label id="schedulerName"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">调度器ID:</td>
                    <td class="value">
                        <label id="schedulerInstanceId"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">调度器实现类:</td>
                    <td class="value">
                        <label id="schedulerClass"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">
                        调度器状态:
                        <a id="schedulerStatus" href="javascript:void(0)" class="easyui-linkbutton"></a>
                    </td>
                    <td class="value">
                        <label for="started" style="margin-left: 10px">是否启动:</label>
                        <label id="started"></label>

                        <label for="shutdown" style="margin-left: 20px">是否关闭:</label>
                        <label id="shutdown"></label>

                        <label for="inStandbyMode" style="margin-left: 20px">是否暂停:</label>
                        <label id="inStandbyMode"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">调度器启动时间:</td>
                    <td class="value">
                        <label id="runningSince"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">是否使用集群:</td>
                    <td class="value">
                        <label id="jobStoreClustered"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">是否远程提供服务:</td>
                    <td class="value">
                        <label id="schedulerRemote"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">本次启动之后一共执行的任务数:</td>
                    <td class="value">
                        <label id="numberOfJobsExecuted"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">调度数据是否支持持久化:</td>
                    <td class="value">
                        <label id="jobStoreSupportsPersistence"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">调度数据存储实现类:</td>
                    <td class="value">
                        <label id="jobStoreClass"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">调度任务线程池实现类:</td>
                    <td class="value">
                        <label id="threadPoolClass"></label>
                    </td>
                </tr>
                <tr class="row">
                    <td class="label">调度任务线程池实大小:</td>
                    <td class="value">
                        <label id="threadPoolSize"></label>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div title="调度器Context">
        <table id="contextDataTable" data-options="border:false">
            <thead>
            <tr>
                <th data-options="width:600,align:'left',hidden:false,field:'key'">属性名</th>
                <th data-options="width:100,align:'left',hidden:false,field:'value',formatter:pageJsObject.jobDataFormatter">属性内容(Json)</th>
            </tr>
            </thead>
        </table>
        <div id="contextDataTableButtons">
            <a id="contextDataTableButtonsReload" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
        </div>
    </div>

    <div title="正在运行的任务">
        <table id="runningJobsDataTable" data-options="border:false">
            <thead>
            <tr>
                <th data-options="width:200,align:'left',hidden:false,field:'schedName'">调度器名称</th>
                <th data-options="width:100,align:'left',hidden:false,field:'jobGroup'">任务分组</th>
                <th data-options="width:100,align:'left',hidden:false,field:'jobName'">任务名称</th>
                <th data-options="width:400,align:'left',hidden:false,field:'jobClassName'">任务类</th>
                <th data-options="width:80,align:'left',hidden:false,field:'isDurable'">存储</th>
                <th data-options="width:80,align:'left',hidden:false,field:'isNonconcurrent'">不支持并发</th>
                <th data-options="width:80,align:'left',hidden:false,field:'isUpdateData'">更新JobData</th>
                <th data-options="width:80,align:'left',hidden:false,field:'requestsRecovery'">支持故障恢复</th>
                <th data-options="width:100,align:'left',hidden:false,field:'jobData',formatter:pageJsObject.jobDataFormatter">JobData</th>
                <th data-options="width:500,align:'left',hidden:false,field:'description'">任务描述</th>
            </tr>
            </thead>
        </table>
        <div id="runningJobsDataTableButtons">
            <a id="runningJobsDataTableButtonsReload" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">刷新</a>
            <a id="runningJobsDataTableButtonsInterrupt" class="easyui-linkbutton" data-options="iconCls:'icon-interrupt',plain:true">中断任务</a>
        </div>
    </div>
</div>

<%-- 查看对话框 --%>
<div id="jsonViewDialog" style="width: 850px;height: 330px;padding: 5px 10px">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <textarea id="jsonViewEdit"></textarea>
</div>

</body>
</html>
