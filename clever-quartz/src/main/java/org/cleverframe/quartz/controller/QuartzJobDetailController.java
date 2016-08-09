package org.cleverframe.quartz.controller;

import com.fasterxml.jackson.databind.JavaType;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.service.JobDetailService;
import org.cleverframe.quartz.vo.model.QrtzJobDetails;
import org.cleverframe.quartz.vo.request.JobDetailKeyVo;
import org.cleverframe.quartz.vo.request.SaveJobDetailVo;
import org.quartz.JobKey;
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
 * 创建时间：2016-7-29 16:35 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping(value = "/${mvcPath}/quartz/jobdetail")
public class QuartzJobDetailController extends BaseController {

    @Autowired
    @Qualifier(QuartzBeanNames.JobDetailService)
    private JobDetailService jobDetailService;

    @RequestMapping("/getAllJobKey")
    @ResponseBody
    public AjaxMessage<List<JobKey>> getAllJobKey(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<List<JobKey>> ajaxMessage = new AjaxMessage<>(true, "获取所有的JobKey成功", null);
        List<JobKey> jobKeyList = jobDetailService.getAllJobKey(ajaxMessage);
        ajaxMessage.setResult(jobKeyList);
        return ajaxMessage;
    }

    @RequestMapping("/getAllJobDetail")
    @ResponseBody
    public AjaxMessage<List<QrtzJobDetails>> getAllJobDetail(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<List<QrtzJobDetails>> ajaxMessage = new AjaxMessage<>(true, "获取所有的JobDetail成功", null);
        List<QrtzJobDetails> jobDetailList = jobDetailService.getAllJobDetail();
        ajaxMessage.setResult(jobDetailList);
        return ajaxMessage;
    }

    @RequestMapping("/getAllJobClassName")
    @ResponseBody
    public AjaxMessage<List<String>> getAllJobClassName(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<List<String>> ajaxMessage = new AjaxMessage<>(true, "获取所有的Job类名成功", null);
        List<String> jobClassNameList = jobDetailService.getAllJobClassName();
        ajaxMessage.setResult(jobClassNameList);
        return ajaxMessage;
    }

    @RequestMapping("/getJobGroupNames")
    @ResponseBody
    public AjaxMessage<List<String>> getJobGroupNames(HttpServletRequest request, HttpServletResponse response) {
        AjaxMessage<List<String>> ajaxMessage = new AjaxMessage<>(true, "获取所有的JobGroupName成功", null);
        List<String> jobGroupNames = jobDetailService.getJobGroupNames(ajaxMessage);
        ajaxMessage.setResult(jobGroupNames);
        return ajaxMessage;
    }

    /**
     * 保存一个JobDetail,只是保存一个JobDetail并不设置其 Trigger
     */
    @RequestMapping("/saveJobDetail")
    @ResponseBody
    public AjaxMessage<String> saveJobDetail(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid SaveJobDetailVo saveJobDetailVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "保存JobDetail成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            Map<String, String> jobData = null;
            if (StringUtils.isNotBlank(saveJobDetailVo.getJobData())) {
                JavaType javaType = JacksonMapper.nonEmptyMapper().contructMapType(Map.class, String.class, String.class);
                jobData = JacksonMapper.nonEmptyMapper().fromJson(saveJobDetailVo.getJobData(), javaType);
            }
            jobDetailService.saveJobDetail(
                    saveJobDetailVo.getJobName(),
                    saveJobDetailVo.getJobGroup(),
                    saveJobDetailVo.getDescription(),
                    saveJobDetailVo.getJobClassName(),
                    "1".equals(saveJobDetailVo.getRequestsRecovery()),
                    jobData,
                    ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 停止并且删除一个JobDetail
     */
    @RequestMapping("/deleteJobDetail")
    @ResponseBody
    public AjaxMessage<String> deleteJobDetail(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid JobDetailKeyVo jobDetailKeyVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "删除JobDetail成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            jobDetailService.deleteJobDetail(jobDetailKeyVo.getJobName(), jobDetailKeyVo.getJobGroup(), ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 暂停一个JobDetail
     */
    @RequestMapping("/pauseJob")
    @ResponseBody
    public AjaxMessage<String> pauseJob(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid JobDetailKeyVo jobDetailKeyVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "暂停JobDetail成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            jobDetailService.pauseJob(jobDetailKeyVo.getJobName(), jobDetailKeyVo.getJobGroup(), ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 取消暂停一个JobDetail
     */
    @RequestMapping("/resumeJob")
    @ResponseBody
    public AjaxMessage<String> resumeJob(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid JobDetailKeyVo jobDetailKeyVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "取消暂停JobDetail成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            jobDetailService.resumeJob(jobDetailKeyVo.getJobName(), jobDetailKeyVo.getJobGroup(), ajaxMessage);
        }
        return ajaxMessage;
    }

    /**
     * 立即运行JobDetail
     */
    @RequestMapping("/triggerJob")
    @ResponseBody
    public AjaxMessage<String> triggerJob(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid JobDetailKeyVo jobDetailKeyVo,
            BindingResult bindingResult) {
        AjaxMessage<String> ajaxMessage = new AjaxMessage<>(true, "立即运行JobDetail成功", null);
        if (beanValidator(bindingResult, ajaxMessage)) {
            jobDetailService.triggerJob(jobDetailKeyVo.getJobName(), jobDetailKeyVo.getJobGroup(), ajaxMessage);
        }
        return ajaxMessage;
    }

}
