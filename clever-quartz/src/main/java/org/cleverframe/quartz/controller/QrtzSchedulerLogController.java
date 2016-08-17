package org.cleverframe.quartz.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.QuartzJspUrlPath;
import org.cleverframe.quartz.entity.QrtzSchedulerLog;
import org.cleverframe.quartz.service.QrtzSchedulerLogService;
import org.cleverframe.quartz.vo.request.QrtzSchedulerLogQuery;
import org.cleverframe.webui.easyui.data.DataGridJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-5 11:09 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/quartz/schedulerlog")
public class QrtzSchedulerLogController extends BaseController {

    @Autowired
    @Qualifier(QuartzBeanNames.QrtzSchedulerLogService)
    private QrtzSchedulerLogService qrtzSchedulerLogService;

    @RequestMapping("/SchedulerLog" + VIEW_PAGE_SUFFIX)
    public ModelAndView getSchedulerLogJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(QuartzJspUrlPath.SchedulerLog);
    }

    @RequestMapping("/findSchedulerLogByPage")
    @ResponseBody
    public DataGridJson<QrtzSchedulerLog> findSchedulerLogByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid QrtzSchedulerLogQuery qrtzSchedulerLogQuery,
            BindingResult bindingResult) {
        DataGridJson<QrtzSchedulerLog> json = new DataGridJson<>();
        Page<QrtzSchedulerLog> qrtzTriggerLogPage = qrtzSchedulerLogService.findByPage(
                new Page<>(request, response),
                qrtzSchedulerLogQuery.getSchedulerName(),
                qrtzSchedulerLogQuery.getInstanceName(),
                qrtzSchedulerLogQuery.getMethodName(),
                qrtzSchedulerLogQuery.getLogTimeStart(),
                qrtzSchedulerLogQuery.getLogTimeEnd());
        json.setRows(qrtzTriggerLogPage.getList());
        json.setTotal(qrtzTriggerLogPage.getCount());
        return json;
    }
}
