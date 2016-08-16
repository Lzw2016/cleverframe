package org.cleverframe.quartz.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.QuartzJspUrlPath;
import org.cleverframe.quartz.entity.QrtzJobLog;
import org.cleverframe.quartz.service.QrtzJobLogService;
import org.cleverframe.quartz.vo.request.QrtzJobLogQueryVo;
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
 * 创建时间：2016-8-5 11:08 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/quartz/joblog")
public class QrtzJobLogController extends BaseController {

    @Autowired
    @Qualifier(QuartzBeanNames.QrtzJobLogService)
    private QrtzJobLogService qrtzJobLogService;

    @RequestMapping("/JobDetailLog" + VIEW_PAGE_SUFFIX)
    public ModelAndView getJobDetailLogJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(QuartzJspUrlPath.JobDetailLog);
    }

    @RequestMapping("/findQrtzJobLogByPage")
    @ResponseBody
    public DataGridJson<QrtzJobLog> findQrtzJobLogByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid QrtzJobLogQueryVo qrtzJobLogQueryVo,
            BindingResult bindingResult) {
        DataGridJson<QrtzJobLog> json = new DataGridJson<>();
        Page<QrtzJobLog> qrtzTriggerLogPage = qrtzJobLogService.findByPage(
                new Page<>(request, response),
                qrtzJobLogQueryVo.getSchedulerName(),
                qrtzJobLogQueryVo.getInstanceName(),
                qrtzJobLogQueryVo.getJobGroup(),
                qrtzJobLogQueryVo.getJobName(),
                qrtzJobLogQueryVo.getJobClassName(),
                qrtzJobLogQueryVo.getStartTimeByStart(),
                qrtzJobLogQueryVo.getStartTimeByEnd(),
                qrtzJobLogQueryVo.getProcessTimeByMin(),
                qrtzJobLogQueryVo.getProcessTimeByMax());
        json.setRows(qrtzTriggerLogPage.getList());
        json.setTotal(qrtzTriggerLogPage.getCount());
        return json;
    }
}
