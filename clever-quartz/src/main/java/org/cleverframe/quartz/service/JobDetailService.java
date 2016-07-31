package org.cleverframe.quartz.service;

import org.cleverframe.common.exception.ExceptionUtils;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.utils.QuartzManager;
import org.cleverframe.quartz.vo.model.QrtzJobDetails;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-29 16:44 <br/>
 */
@Service(QuartzBeanNames.JobDetailService)
public class JobDetailService extends BaseService {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(JobDetailService.class);

    /**
     * 获取所有的JobKey
     */
    public List<JobKey> getAllJobKey(AjaxMessage<List<JobKey>> ajaxMessage) {
        List<JobKey> jobKeyList = QuartzManager.getAllJobKey();
        if (jobKeyList == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("获取所有的JobKey失败");
        }
        return jobKeyList;
    }

    /**
     * 获取所有的 JobDetail
     */
    public List<QrtzJobDetails> getAllJobDetail() {
        List<QrtzJobDetails> qrtzJobDetailsList = new ArrayList<>();
        List<JobDetail> jobDetailList = QuartzManager.getAllJobDetail();
        assert jobDetailList != null;
        String schedName = null;
        try {
            schedName = QuartzManager.getScheduler().getSchedulerName();
        } catch (Throwable e) {
            logger.error("获取SchedulerName失败", e);
        }
        for (JobDetail jobDetail : jobDetailList) {
            QrtzJobDetails qrtzJobDetails = new QrtzJobDetails();
            qrtzJobDetails.setSchedName(schedName);
            qrtzJobDetails.setJobGroup(jobDetail.getKey().getGroup());
            qrtzJobDetails.setJobName(jobDetail.getKey().getName());
            qrtzJobDetails.setIsDurable(jobDetail.isDurable());
            qrtzJobDetails.setDescription(jobDetail.getDescription());
            qrtzJobDetails.setJobClassName(jobDetail.getJobClass().getName());
            qrtzJobDetails.setJobData(jobDetail.getJobDataMap());
            qrtzJobDetails.setRequestsRecovery(jobDetail.requestsRecovery());
            // @DisallowConcurrentExecution 对应 isNonconcurrent
            // @PersistJobDataAfterExecution 对应 isUpdateData
            qrtzJobDetails.setIsNonconcurrent(jobDetail.isConcurrentExectionDisallowed());
            qrtzJobDetails.setIsUpdateData(jobDetail.isPersistJobDataAfterExecution());
            qrtzJobDetailsList.add(qrtzJobDetails);
        }
        return qrtzJobDetailsList;
    }

    /**
     * 返回basePackage包下面所有的Job子类
     */
    public List<String> getAllJobClassName() {
        List<String> jobClassNameList = new ArrayList<>();
        jobClassNameList.addAll(QuartzManager.getAllJobClassName("org.cleverframe"));
        jobClassNameList.addAll(QuartzManager.getAllJobClassName("org.quartz"));
        return jobClassNameList;
    }

    /**
     * 获取所有的JobGroupName
     */
    public List<String> getJobGroupNames(AjaxMessage<List<String>> ajaxMessage) {
        List<String> jobGroupNames = QuartzManager.getJobGroupNames();
        if (jobGroupNames == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("获取所有的JobGroupName失败");
        }
        return jobGroupNames;
    }

    /**
     * 保存一个JobDetail,只是保存一个JobDetail并不设置其 Trigger
     *
     * @param jobName          jobName
     * @param jobGroup         jobGroup
     * @param description      Job描述
     * @param jobClassName     实现Job的类名
     * @param requestsRecovery Scheduler实例发生故障时
     * @param jobData          JobDataMap
     * @return 成功返回true
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = false)
    public boolean saveJobDetail(
            String jobName,
            String jobGroup,
            String description,
            String jobClassName,
            boolean requestsRecovery,
            Map<String, String> jobData,
            AjaxMessage ajaxMessage) {
        Scheduler scheduler = QuartzManager.getScheduler();
        Class aClass = QuartzManager.getJobClass(jobClassName);
        if (aClass == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("JobClassName错误[" + jobClassName + "]");
            return false;
        }
        JobBuilder jobBuilder = JobBuilder.newJob(aClass);
        jobBuilder.withIdentity(jobName, jobGroup);
        // 需要存储的job必须调用此方法
        jobBuilder.storeDurably();
        jobBuilder.withDescription(description);
        jobBuilder.requestRecovery(requestsRecovery);
        if (jobData != null) {
            for (Map.Entry<String, String> entry : jobData.entrySet()) {
                jobBuilder.usingJobData(entry.getKey(), entry.getValue());
            }
        }
        // @DisallowConcurrentExecution 对应 isNonconcurrent
        // @PersistJobDataAfterExecution 对应 isUpdateData
        JobDetail jobDetail = jobBuilder.build();
        try {
            scheduler.addJob(jobDetail, false);
        } catch (Throwable e) {
            logger.error("保存JobDetail失败", e);
            throw ExceptionUtils.unchecked(e);
        }
        return true;
    }

    /**
     * 停止并且删除一个JobDetail
     *
     * @param jobName  job名称
     * @param jobGroup job组名称
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean deleteJobDetail(String jobName, String jobGroup, AjaxMessage ajaxMessage) {
        Scheduler scheduler = QuartzManager.getScheduler();
        List<? extends Trigger> jobTriggers = QuartzManager.getTriggerByJob(jobName, jobGroup);
        if (jobTriggers == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("删除JobDetail失败-获取JobDetail的所有Trigger失败");
            return false;
        }
        try {
            for (Trigger trigger : jobTriggers) {
                // 暂停触发器
                scheduler.pauseTrigger(trigger.getKey());
                // 移除触发器
                scheduler.unscheduleJob(trigger.getKey());
            }
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        } catch (Throwable e) {
            logger.error("删除JobDetail发生异常", e);
            throw ExceptionUtils.unchecked(e);
        }
        return true;
    }

    /**
     * 暂停一个JobDetail
     *
     * @param jobName  job名称
     * @param jobGroup job组名称
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean pauseJob(String jobName, String jobGroup, AjaxMessage ajaxMessage) {
        Scheduler scheduler = QuartzManager.getScheduler();
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName, jobGroup));
        } catch (Throwable e) {
            logger.error("暂停JobDetail异常", e);
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("暂停JobDetail失败");
            return false;
        }
        return true;
    }

    /**
     * 取消暂停一个JobDetail
     *
     * @param jobName  job名称
     * @param jobGroup job组名称
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean resumeJob(String jobName, String jobGroup, AjaxMessage ajaxMessage) {
        Scheduler scheduler = QuartzManager.getScheduler();
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName, jobGroup));
        } catch (Throwable e) {
            logger.error("取消暂停JobDetail异常", e);
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("取消暂停JobDetail失败");
            return false;
        }
        return true;
    }
}
