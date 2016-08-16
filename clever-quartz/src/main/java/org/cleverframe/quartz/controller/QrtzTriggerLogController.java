package org.cleverframe.quartz.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.QuartzJspUrlPath;
import org.cleverframe.quartz.entity.QrtzTriggerLog;
import org.cleverframe.quartz.service.QrtzTriggerLogService;
import org.cleverframe.quartz.vo.request.QrtzTriggerLogQueryVo;
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
@RequestMapping(value = "/${mvcPath}/quartz/triggerlog")
public class QrtzTriggerLogController extends BaseController {

    @Autowired
    @Qualifier(QuartzBeanNames.QrtzTriggerLogService)
    private QrtzTriggerLogService qrtzTriggerLogService;

    @RequestMapping("/TriggerLog" + VIEW_PAGE_SUFFIX)
    public ModelAndView getTriggerLogJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(QuartzJspUrlPath.TriggerLog);
    }

    @RequestMapping("/findTriggerLogByPage")
    @ResponseBody
    public DataGridJson<QrtzTriggerLog> findTriggerLogByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid QrtzTriggerLogQueryVo qrtzTriggerLogQueryVo,
            BindingResult bindingResult) {
        DataGridJson<QrtzTriggerLog> json = new DataGridJson<>();
        Page<QrtzTriggerLog> qrtzTriggerLogPage = qrtzTriggerLogService.findByPage(
                new Page<>(request, response),
                qrtzTriggerLogQueryVo.getSchedulerName(),
                qrtzTriggerLogQueryVo.getInstanceName(),
                qrtzTriggerLogQueryVo.getTriggerGroup(),
                qrtzTriggerLogQueryVo.getTriggerName(),
                qrtzTriggerLogQueryVo.getJobGroup(),
                qrtzTriggerLogQueryVo.getJobName(),
                qrtzTriggerLogQueryVo.getJobClassName(),
                qrtzTriggerLogQueryVo.getStartTimeByStart(),
                qrtzTriggerLogQueryVo.getStartTimeByEnd(),
                qrtzTriggerLogQueryVo.getProcessTimeByMin(),
                qrtzTriggerLogQueryVo.getProcessTimeByMax());
        json.setRows(qrtzTriggerLogPage.getList());
        json.setTotal(qrtzTriggerLogPage.getCount());
        return json;
    }

}
