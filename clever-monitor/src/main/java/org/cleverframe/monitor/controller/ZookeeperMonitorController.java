package org.cleverframe.monitor.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.service.ZookeeperMonitorService;
import org.cleverframe.monitor.vo.response.ZNodeInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-27 16:05 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/monitor/zookeeper")
public class ZookeeperMonitorController extends BaseController {

    @Autowired
    @Qualifier(MonitorBeanNames.ZookeeperMonitorService)
    private ZookeeperMonitorService zookeeperMonitorService;

    @RequestMapping("/getChildren")
    @ResponseBody
    public AjaxMessage<List<String>> getChildren(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false, defaultValue = "/") String path) {
        addXSSExcludeUrl(request);
        AjaxMessage<List<String>> ajaxMessage = new AjaxMessage<>(true, "获取Zookeeper子节点成功", null);
        List<String> childrenList = zookeeperMonitorService.getChildren(path);
        if (childrenList == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("获取Zookeeper子节点失败");
        }
        ajaxMessage.setResult(childrenList);
        return ajaxMessage;
    }

    @RequestMapping("/getZNodeInfo")
    @ResponseBody
    public AjaxMessage<ZNodeInfoVo> getZNodeInfo(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = true) String path) {
        AjaxMessage<ZNodeInfoVo> ajaxMessage = new AjaxMessage<>(true, "获取Zookeeper节点数据成功", null);
        ZNodeInfoVo zNodeInfoVo = zookeeperMonitorService.getZNodeInfo(path);
        if (zNodeInfoVo == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("获取Zookeeper节点数据失败");
        }
        ajaxMessage.setResult(zNodeInfoVo);
        return ajaxMessage;
    }
}
