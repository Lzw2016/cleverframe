package org.cleverframe.quartz.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.utils.QuartzManager;
import org.cleverframe.quartz.vo.model.QuartzJobDetails;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-8 16:37 <br/>
 */
@Service(QuartzBeanNames.SchedulerService)
public class SchedulerService extends BaseService {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    /**
     * 暂停调度器
     *
     * @return 失败返回false
     */
    @Transactional(readOnly = false)
    public boolean standby(AjaxMessage ajaxMessage) {
        boolean result = QuartzManager.standby();
        if (!result) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("暂停调度器失败");
        }
        return result;
    }

    /**
     * 继续运行调度器
     *
     * @return 失败返回false
     */
    @Transactional(readOnly = false)
    public boolean start(AjaxMessage ajaxMessage) {
        boolean result = QuartzManager.start();
        if (!result) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("继续运行调度器失败");
        }
        return result;
    }

    /**
     * 暂停所有的触发器
     *
     * @return 失败返回false
     */
    @Transactional(readOnly = false)
    public boolean pauseAll(AjaxMessage ajaxMessage) {
        Scheduler scheduler = QuartzManager.getScheduler();
        try {
            scheduler.pauseAll();
        } catch (Throwable e) {
            logger.error("暂停所有的触发器异常", e);
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("暂停所有的触发器异常");
            return false;
        }
        return true;
    }

    /**
     * 取消暂停所有的触发器
     *
     * @return 失败返回false
     */
    @Transactional(readOnly = false)
    public boolean resumeAll(AjaxMessage ajaxMessage) {
        Scheduler scheduler = QuartzManager.getScheduler();
        try {
            scheduler.resumeAll();
        } catch (Throwable e) {
            logger.error("取消暂停所有的触发器异常", e);
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("取消暂停所有的触发器异常");
            return false;
        }
        return true;
    }

    /**
     * 返回正在运行的Job<br/>
     * <b>注意:此方法不支持集群</b>
     *
     * @return 失败返回null
     */
    public List<QuartzJobDetails> getRunningJobs(AjaxMessage ajaxMessage) {
        List<QuartzJobDetails> qrtzJobDetailsList;
        Scheduler scheduler = QuartzManager.getScheduler();
        List<JobExecutionContext> list;
        String schedName;
        try {
            schedName = QuartzManager.getScheduler().getSchedulerName();
            list = scheduler.getCurrentlyExecutingJobs();
        } catch (Throwable e) {
            logger.error("获取当前所有执行的JobExecutionContext异常", e);
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("获取正在运行的Job异常");
            return null;
        }
        qrtzJobDetailsList = new ArrayList<>();
        for (JobExecutionContext jobExecutionContext : list) {
            JobDetail jobDetail = jobExecutionContext.getJobDetail();
            QuartzJobDetails qrtzJobDetails = new QuartzJobDetails();
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
     * 中断Job<br/>
     * <b>注意:被中断的Job,必须实现接口 InterruptableJob</b>
     *
     * @return 失败返回false
     */
    @Transactional(readOnly = false)
    public boolean interrupt(JobKey jobKey, AjaxMessage ajaxMessage) {
        Scheduler scheduler = QuartzManager.getScheduler();
        try {
            scheduler.interrupt(jobKey);
        } catch (Throwable e) {
            logger.error("中断Job异常", e);
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("中断Job异常");
            return false;
        }
        return true;
    }

    /**
     * 获取Scheduler信息
     *
     * @return 失败返回null
     */
    public SchedulerMetaData getMetaData(AjaxMessage ajaxMessage) {
        Scheduler scheduler = QuartzManager.getScheduler();
        SchedulerMetaData schedulerMetaData = null;
        try {
            schedulerMetaData = scheduler.getMetaData();
        } catch (Throwable e) {
            logger.error("获取Scheduler信息异常", e);
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("获取Scheduler信息异常");
        }
        return schedulerMetaData;
    }

    /**
     * 获取SchedulerContext
     *
     * @return 失败返回null
     */
    public Map<String, Object> getContext(AjaxMessage ajaxMessage) {
        Scheduler scheduler = QuartzManager.getScheduler();
        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = scheduler.getContext();
        } catch (Throwable e) {
            logger.error("获取SchedulerContext异常", e);
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("获取SchedulerContext异常");
        }
        Map<String, Object> result = null;
        if (schedulerContext != null) {
            result = new HashMap<>();
            for (Map.Entry<String, Object> entry : schedulerContext.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Serializable) {
                    result.put(entry.getKey(), value);
                } else {
                    result.put(entry.getKey(), value.getClass().getName());
                }
            }
        }
        return result;
    }
}
