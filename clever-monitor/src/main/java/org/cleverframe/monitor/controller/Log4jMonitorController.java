package org.cleverframe.monitor.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.service.Log4jMonitorService;
import org.cleverframe.monitor.vo.response.LoggerInfoVo;
import org.cleverframe.webui.easyui.data.DataGridJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public DataGridJson<LoggerInfoVo> getAllLoggerInfo(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String loggerName) {
        DataGridJson<LoggerInfoVo> json = new DataGridJson<>();
        Page<LoggerInfoVo> page = log4jMonitorService.getAllLoggerInfoVo(new Page<>(request, response), loggerName);
        json.setRows(page.getList());
        json.setTotal(page.getCount());
        return json;
    }


}
