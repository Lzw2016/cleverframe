package org.cleverframe.quartz.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.QuartzJspUrlPath;
import org.cleverframe.quartz.service.SchedulerService;
import org.cleverframe.quartz.vo.model.QuartzJobDetails;
import org.cleverframe.quartz.vo.request.JobDetailKeyVo;
import org.quartz.JobKey;
import org.quartz.SchedulerMetaData;
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
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-30 12:38 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/quartz/scheduler")
public class QuartzSchedulerController extends BaseController {

    @Autowired
    @Qualifier(QuartzBeanNames.SchedulerService)
    private SchedulerService schedulerService;

    @RequestMapping("/Scheduler" + VIEW_PAGE_SUFFIX)
    public ModelAndView getSchedulerJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(QuartzJspUrlPath.Scheduler);
    }

    @RequestMapping("/standby")
    @ResponseBody
    public AjaxMessage<String> standby(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "暂停调度器成功", null);
        schedulerService.standby(ajaxMessage);
        return ajaxMessage;
    }

    @RequestMapping("/start")
    @ResponseBody
    public AjaxMessage<String> start(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "继续运行调度器成功", null);
        schedulerService.start(ajaxMessage);
        return ajaxMessage;
    }

    @RequestMapping("/pauseAll")
    @ResponseBody
    public AjaxMessage<String> pauseAll(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "暂停所有的触发器成功", null);
        schedulerService.pauseAll(ajaxMessage);
        return ajaxMessage;
    }

    @RequestMapping("/resumeAll")
    @ResponseBody
    public AjaxMessage<String> resumeAll(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "取消暂停所有的触发器成功", null);
        schedulerService.resumeAll(ajaxMessage);
        return ajaxMessage;
    }

    @RequestMapping("/getRunningJobs")
    @ResponseBody
    public AjaxMessage<List<QuartzJobDetails>> getRunningJobs(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<List<QuartzJobDetails>> ajaxMessage = new AjaxMessage<>(true, "获取正在运行的Job成功", null);
        ajaxMessage.setResult(schedulerService.getRunningJobs(ajaxMessage));
        return ajaxMessage;
    }

    @RequestMapping("/interrupt")
    @ResponseBody
    public AjaxMessage<String> interrupt(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid JobDetailKeyVo jobDetailKeyVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "中断Job成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            schedulerService.interrupt(JobKey.jobKey(jobDetailKeyVo.getJobName(), jobDetailKeyVo.getJobGroup()), ajaxMessage);
        }
        return ajaxMessage;
    }

    @RequestMapping("/getMetaData")
    @ResponseBody
    public AjaxMessage<SchedulerMetaData> getMetaData(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<SchedulerMetaData> ajaxMessage = new AjaxMessage<>(true, "获取Scheduler信息成功", null);
        ajaxMessage.setResult(schedulerService.getMetaData(ajaxMessage));
        return ajaxMessage;
    }

    @RequestMapping("/getContext")
    @ResponseBody
    public AjaxMessage<Map<String, Object>> getContext(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<Map<String, Object>> ajaxMessage = new AjaxMessage<>(true, "获取SchedulerContext成功", null);
        ajaxMessage.setResult(schedulerService.getContext(ajaxMessage));
        return ajaxMessage;
    }
}
