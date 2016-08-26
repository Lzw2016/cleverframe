package org.cleverframe.monitor.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.service.Log4jMonitorService;
import org.cleverframe.monitor.vo.request.LoggerInfoQueryVo;
import org.cleverframe.monitor.vo.request.SetLoggerLevelVo;
import org.cleverframe.monitor.vo.response.LoggerInfoVo;
import org.cleverframe.webui.easyui.data.DataGridJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-26 10:34 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/monitor/log4j")
public class Log4jMonitorController extends BaseController {

    @Autowired
    @Qualifier(MonitorBeanNames.Log4jMonitorService)
    private Log4jMonitorService log4jMonitorService;

    @RequestMapping("/getAllLoggerInfo")
    @ResponseBody
    public Object getAllLoggerInfo(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid LoggerInfoQueryVo loggerInfoQueryVo,
            BindingResult bindingResult) {
        AjaxMessage ajaxMessage = new AjaxMessage();
        if (!beanValidator(bindingResult, ajaxMessage)) {
            return ajaxMessage;
        }
        DataGridJson<LoggerInfoVo> json = new DataGridJson<>();
        Page<LoggerInfoVo> page = log4jMonitorService.getAllLoggerInfoVo(new Page<>(request, response), loggerInfoQueryVo.getLoggerName(), loggerInfoQueryVo.getLevel());
        json.setRows(page.getList());
        json.setTotal(page.getCount());
        return json;
    }

    @RequestMapping("/setLoggerLevel")
    @ResponseBody
    public AjaxMessage<String> setLoggerLevel(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid SetLoggerLevelVo setLoggerLevelVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "设置日志记录器日志级别成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            boolean flag = log4jMonitorService.setLoggerLevel(setLoggerLevelVo.getLoggerName(), setLoggerLevelVo.getLevel());
            if (!flag) {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("设置日志记录器日志级别失败");
            }
        }
        return ajaxMessage;
    }
}
