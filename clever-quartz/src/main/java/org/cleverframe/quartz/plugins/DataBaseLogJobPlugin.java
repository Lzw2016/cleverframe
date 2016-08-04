package org.cleverframe.quartz.plugins;

import org.cleverframe.common.exception.ExceptionUtils;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.common.utils.IPAddressUtils;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.entity.QrtzJobLog;
import org.cleverframe.quartz.service.QrtzJobLogService;
import org.quartz.*;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 记录Job执行日志的插件,日志数据存到数据库<br/>
 * 参考{@link org.quartz.plugins.history.LoggingJobHistoryPlugin}
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-7-31 23:09 <br/>
 */
public class DataBaseLogJobPlugin implements SchedulerPlugin, JobListener {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(DataBaseLogJobPlugin.class);

    /**
     * jobDataMap中特殊的Key
     */
    private String jobDataMapKey = "_jobDataMap中特殊的Key";

    /**
     * 监听器名称
     */
    private String name;

    private QrtzJobLogService qrtzJobLogService;

    /**
     * 方法返回一个字符串用以说明 JobListener 的名称
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Scheduler 的创建期间被调用<br/>
     * 当 StdSchedulerFactory 的 getScheduler() 方法被调用后，这个工厂就调用所有注册的插件的 initialize() 方法<br/>
     */
    @Override
    public void initialize(String name, Scheduler scheduler, ClassLoadHelper loadHelper) throws SchedulerException {
        qrtzJobLogService = SpringContextHolder.getBean(QuartzBeanNames.QrtzJobLogService);
        if (qrtzJobLogService != null) {
            logger.debug("### QrtzJobLogService注入成功");
        } else {
            RuntimeException exception = new RuntimeException("Quartz插件DataBaseLogJobPlugin初始化失败，QrtzJobLogService注入失败");
            logger.info(exception.getMessage(), exception);
            throw exception;
        }
        this.name = name;
        this.jobDataMapKey = name + this.jobDataMapKey;
        scheduler.getListenerManager().addJobListener(this);
        logger.info("### Quartz插件DataBaseLogJobPlugin初始化成功");
    }

    /**
     * Scheduler 在 JobDetail 将要被执行时调用这个方法<br/>
     * 增加日志信息
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobDetail jobDetail = context.getJobDetail();
        Job job = context.getJobInstance();
        Trigger trigger = context.getTrigger();
        String schedName = "未知";
        String instanceName = "未知";
        try {
            schedName = context.getScheduler().getSchedulerName();
            instanceName = context.getScheduler().getSchedulerInstanceId();
        } catch (Throwable e) {
            logger.error("获取Scheduler标识失败", e);
        }

        QrtzJobLog qrtzJobLog = new QrtzJobLog();
        qrtzJobLog.setListenerName(name);
        qrtzJobLog.setSchedName(schedName);
        qrtzJobLog.setInstanceName(instanceName);
        qrtzJobLog.setJobName(jobDetail.getKey().getName());
        qrtzJobLog.setJobGroup(jobDetail.getKey().getGroup());
        qrtzJobLog.setJobClassName(job.getClass().getName());
        qrtzJobLog.setTriggerName(trigger.getKey().getName());
        qrtzJobLog.setTriggerGroup(trigger.getKey().getGroup());
        qrtzJobLog.setStartTime(new Date());
        // qrtzJobLog.setEndTime();
        // qrtzJobLog.setProcessTime();
        qrtzJobLog.setPreRunTime(context.getPreviousFireTime());
        qrtzJobLog.setNextRunTime(context.getNextFireTime());
        qrtzJobLog.setRunCount(context.getRefireCount());
        qrtzJobLog.setIpAddress(IPAddressUtils.getInet4AddressStr());
        // qrtzJobLog.setExceptionInfo("");
        // 是否被否决（0：否；1：是）
        qrtzJobLog.setIsVeto('0');
        qrtzJobLog.setBeforeJobData(JacksonMapper.nonEmptyMapper().toJson(jobDetail.getJobDataMap()));
        // qrtzJobLog.setAfterJobData();
        qrtzJobLog = qrtzJobLogService.addQrtzJobLog(qrtzJobLog);
        jobDetail.getJobDataMap().put(jobDataMapKey, qrtzJobLog.getId());
    }

    /**
     * Scheduler 在 JobDetail 即将被执行，但又被 TriggerListener 否决了时调用这个方法<br/>
     * 更新日志信息
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        JobDetail jobDetail = context.getJobDetail();
        Long id = (Long) jobDetail.getJobDataMap().get(jobDataMapKey);
        jobDetail.getJobDataMap().remove(jobDataMapKey);
        if (id == null) {
            logger.warn("### jobDetail.getJobDataMap().get(ListenerName) 返回空值-jobExecutionVetoed");
            return;
        }
        QrtzJobLog qrtzJobLog = new QrtzJobLog();
        qrtzJobLog.setId(id);
        // 是否被否决（0：否；1：是）
        qrtzJobLog.setIsVeto('1');
        qrtzJobLogService.updateQrtzJobLog(qrtzJobLog);

    }

    /**
     * Scheduler 在 JobDetail 被执行之后调用这个方法<br/>
     * 更新日志信息
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        JobDetail jobDetail = context.getJobDetail();
        Long id = (Long) jobDetail.getJobDataMap().get(jobDataMapKey);
        jobDetail.getJobDataMap().remove(jobDataMapKey);
        if (id == null) {
            logger.warn("### jobDetail.getJobDataMap().get(ListenerName) 返回空值-jobWasExecuted");
            return;
        }
        QrtzJobLog qrtzJobLog = new QrtzJobLog();
        qrtzJobLog.setId(id);
        qrtzJobLog.setEndTime(new Date());
        qrtzJobLog.setProcessTime(context.getJobRunTime());
        if (jobException != null) {
            qrtzJobLog.setExceptionInfo(ExceptionUtils.getStackTraceAsString(jobException));
        }
        qrtzJobLog.setAfterJobData(JacksonMapper.nonEmptyMapper().toJson(jobDetail.getJobDataMap()));
        qrtzJobLogService.updateQrtzJobLog(qrtzJobLog);
    }

    /**
     * Scheduler 实例调用 start() 方法让插件知道它可以执行任何需要的启动动作了
     */
    @Override
    public void start() {

    }

    /**
     * shutdown() 方法被调用来通知插件 Scheduler 将要关闭了<br/>
     * 这是给插件的一个机会去清理任何打开的资源<br/>
     */
    @Override
    public void shutdown() {

    }
}
