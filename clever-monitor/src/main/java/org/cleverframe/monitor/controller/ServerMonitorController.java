package org.cleverframe.monitor.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.MonitorJspUrlPath;
import org.cleverframe.monitor.service.ServerMonitorService;
import org.cleverframe.monitor.vo.request.RemoveServerAttributeVo;
import org.cleverframe.monitor.vo.response.ServerAttributeVo;
import org.cleverframe.webui.easyui.data.DataGridJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-20 14:31 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/monitor/server")
public class ServerMonitorController extends BaseController {

    @Autowired
    @Qualifier(MonitorBeanNames.ServerMonitorService)
    private ServerMonitorService serverMonitorService;

    @RequestMapping("/ServerMonitor" + VIEW_PAGE_SUFFIX)
    public ModelAndView getServerMonitorJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(MonitorJspUrlPath.ServerMonitor);
    }

    @RequestMapping("/getApplicationAttribute")
    @ResponseBody
    public DataGridJson<ServerAttributeVo> getApplicationAttribute(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String attributeName) {
        DataGridJson<ServerAttributeVo> json = new DataGridJson<>();
        List<ServerAttributeVo> list = serverMonitorService.getApplicationAttribute(request, attributeName);
        json.setRows(list);
        json.setTotal(list.size());
        return json;
    }

    @RequestMapping("/getSessionAttribute")
    @ResponseBody
    public DataGridJson<ServerAttributeVo> getSessionAttribute(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String attributeName) {
        DataGridJson<ServerAttributeVo> json = new DataGridJson<>();
        List<ServerAttributeVo> list = serverMonitorService.getSessionAttribute(request, attributeName);
        json.setRows(list);
        json.setTotal(list.size());
        return json;
    }

    @RequestMapping("/removeSessionAttribute")
    @ResponseBody
    public AjaxMessage<String> removeSessionAttribute(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid RemoveServerAttributeVo removeServerAttributeVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "移除Session中的属性成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            serverMonitorService.removeSessionAttribute(request, removeServerAttributeVo.getAttributeName());
        }
        return ajaxMessage;
    }

    @RequestMapping("/removeApplicationAttribute")
    @ResponseBody
    public AjaxMessage<String> removeApplicationAttribute(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid RemoveServerAttributeVo removeServerAttributeVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "移除Application中的属性成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            serverMonitorService.removeApplicationAttribute(request, removeServerAttributeVo.getAttributeName());
        }
        return ajaxMessage;
    }
}
