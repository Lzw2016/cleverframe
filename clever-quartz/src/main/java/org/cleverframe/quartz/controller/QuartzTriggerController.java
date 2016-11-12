package org.cleverframe.quartz.controller;

import com.fasterxml.jackson.databind.JavaType;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.service.TriggerService;
import org.cleverframe.quartz.vo.model.QuartzTriggers;
import org.cleverframe.quartz.vo.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-29 16:42 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${base.mvcPath}/quartz/trigger")
public class QuartzTriggerController extends BaseController {

    @Autowired
    @Qualifier(QuartzBeanNames.TriggerService)
    private TriggerService triggerService;

    @RequestMapping("/validatorCron")
    @ResponseBody
    public AjaxMessage<List<String>> validatorCron(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid ValidatorCronVo validatorCronVo,
            BindingResult bindingResult) {
        AjaxMessage<List<String>> ajaxMessage = new AjaxMessage<>(true, "cron表达式验证成功", null);
        List<String> dateList = null;
        if (beanValidator(bindingResult, ajaxMessage)) {
            dateList = triggerService.validatorCron(validatorCronVo.getCron(), validatorCronVo.getNum(), ajaxMessage);
        }
        ajaxMessage.setResult(dateList);
        return ajaxMessage;
    }

    @RequestMapping("/addSimpleTriggerForJob")
    @ResponseBody
    public AjaxMessage<String> addSimpleTriggerForJob(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid AddSimpleTriggerForJobVo addSimpleTriggerForJobVo,
            BindingResult bindingResult) {
        addXSSExcludeUrl(request);
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "给JobDetail增加一个SimpleTrigger成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            Map<String, String> jobData = null;
            if (StringUtils.isNotBlank(addSimpleTriggerForJobVo.getJobData())) {
                JavaType javaType = JacksonMapper.nonEmptyMapper().contructMapType(Map.class, String.class, String.class);
                jobData = JacksonMapper.nonEmptyMapper().fromJson(addSimpleTriggerForJobVo.getJobData(), javaType);
            }
            triggerService.addSimpleTriggerForJob(
                    addSimpleTriggerForJobVo.getJobName(),
                    addSimpleTriggerForJobVo.getJobGroup(),
                    addSimpleTriggerForJobVo.getTriggerName(),
                    addSimpleTriggerForJobVo.getTriggerGroup(),
                    addSimpleTriggerForJobVo.getDescription(),
                    addSimpleTriggerForJobVo.getStartTime(),
                    addSimpleTriggerForJobVo.getEndTime(),
                    addSimpleTriggerForJobVo.getPriority(),
                    jobData,
                    addSimpleTriggerForJobVo.getInterval(),
                    addSimpleTriggerForJobVo.getRepeatCount(),
                    addSimpleTriggerForJobVo.getMisfireInstruction(),
                    ajaxMessage);
        }
        return ajaxMessage;
    }

    @RequestMapping("/addCronTriggerForJob")
    @ResponseBody
    public AjaxMessage<String> addCronTriggerForJob(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid AddCronTriggerForJobVo addCronTriggerForJobVo,
            BindingResult bindingResult) {
        addXSSExcludeUrl(request);
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "给JobDetail增加一个CronTrigger成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            Map<String, String> jobData = null;
            if (StringUtils.isNotBlank(addCronTriggerForJobVo.getJobData())) {
                JavaType javaType = JacksonMapper.nonEmptyMapper().contructMapType(Map.class, String.class, String.class);
                jobData = JacksonMapper.nonEmptyMapper().fromJson(addCronTriggerForJobVo.getJobData(), javaType);
            }
            triggerService.addCronTriggerForJob(
                    addCronTriggerForJobVo.getJobName(),
                    addCronTriggerForJobVo.getJobGroup(),
                    addCronTriggerForJobVo.getTriggerName(),
                    addCronTriggerForJobVo.getTriggerGroup(),
                    addCronTriggerForJobVo.getDescription(),
                    addCronTriggerForJobVo.getStartTime(),
                    addCronTriggerForJobVo.getEndTime(),
                    addCronTriggerForJobVo.getPriority(),
                    jobData,
                    addCronTriggerForJobVo.getCron(),
                    addCronTriggerForJobVo.getMisfireInstruction(),
                    ajaxMessage);
        }
        return ajaxMessage;
    }

    @RequestMapping("/getTriggerByJob")
    @ResponseBody
    public AjaxMessage<List<QuartzTriggers>> getTriggerByJob(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid JobDetailKeyVo jobDetailKeyVo,
            BindingResult bindingResult) {
        AjaxMessage<List<QuartzTriggers>> ajaxMessage = new AjaxMessage<>(true, "获取JobDetail的所有Trigger成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            List<QuartzTriggers> qrtzTriggersList = triggerService.getTriggerByJob(jobDetailKeyVo.getJobName(), jobDetailKeyVo.getJobGroup(), ajaxMessage);
            ajaxMessage.setResult(qrtzTriggersList);
        }
        return ajaxMessage;
    }

    @RequestMapping("/getTriggerGroupNames")
    @ResponseBody
    AjaxMessage<List<String>> getTriggerGroupNames(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<List<String>> ajaxMessage = new AjaxMessage<>(true, "获取所有的TriggerGroupName成功", null);
        ajaxMessage.setResult(triggerService.getTriggerGroupNames(ajaxMessage));
        return ajaxMessage;
    }

    @RequestMapping("/deleteTriggerByJob")
    @ResponseBody
    public AjaxMessage<String> deleteTriggerByJob(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid JobDetailKeyVo jobDetailKeyVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "删除一个JobDetail的所有Trigger成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            triggerService.deleteTriggerByJob(jobDetailKeyVo.getJobName(), jobDetailKeyVo.getJobGroup(), ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 暂停而且删除Trigger
     */
    @RequestMapping("/deleteTrigger")
    @ResponseBody
    public AjaxMessage<String> deleteTrigger(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid TriggerKeyVo triggerKeyVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "删除Trigger成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            triggerService.deleteTrigger(triggerKeyVo.getTriggerName(), triggerKeyVo.getTriggerGroup(), ajaxMessage);
        }
        return ajaxMessage;
    }

    @RequestMapping("/pauseTrigger")
    @ResponseBody
    public AjaxMessage<String> pauseTrigger(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid TriggerKeyVo triggerKeyVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "暂停Trigger成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            triggerService.pauseTrigger(triggerKeyVo.getTriggerName(), triggerKeyVo.getTriggerGroup(), ajaxMessage);
        }
        return ajaxMessage;
    }

    @RequestMapping("/resumeTrigger")
    @ResponseBody
    public AjaxMessage<String> resumeTrigger(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid TriggerKeyVo triggerKeyVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "取消暂停Trigger成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            triggerService.resumeTrigger(triggerKeyVo.getTriggerName(), triggerKeyVo.getTriggerGroup(), ajaxMessage);
        }
        return ajaxMessage;
    }
}
