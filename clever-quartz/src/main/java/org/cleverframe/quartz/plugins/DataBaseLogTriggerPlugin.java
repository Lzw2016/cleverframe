package org.cleverframe.quartz.plugins;

import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.common.utils.IPAddressUtils;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.entity.QrtzTriggerLog;
import org.cleverframe.quartz.service.QrtzTriggerLogService;
import org.quartz.*;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 记录Trigger触发的日志的插件,日志数据存到数据库<br/>
 * 参考{@link org.quartz.plugins.history.LoggingTriggerHistoryPlugin}
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-7-31 23:12 <br/>
 */
public class DataBaseLogTriggerPlugin implements SchedulerPlugin, TriggerListener {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(DataBaseLogTriggerPlugin.class);

    /**
     * jobDataMap中特殊的Key
     */
    private String jobDataMapKey = "_jobDataMap中特殊的Key";

    /**
     * 监听器名称
     */
    private String name;

    /**
     * 调度器全局单例
     */
    private Scheduler scheduler;

    private QrtzTriggerLogService qrtzTriggerLogService;

    /**
     * 返回一个字符串用以说明监听器的名称
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
        qrtzTriggerLogService = SpringContextHolder.getBean(QuartzBeanNames.QrtzTriggerLogService);
        if (qrtzTriggerLogService != null) {
            logger.info("### QrtzTriggerLogService注入成功");
        } else {
            RuntimeException exception = new RuntimeException("Quartz插件DataBaseLogTriggerPlugin初始化失败，QrtzTriggerLogService注入失败");
            logger.info(exception.getMessage(), exception);
            throw exception;
        }
        this.name = name;
        this.jobDataMapKey = name + this.jobDataMapKey;
        this.scheduler = scheduler;
        scheduler.getListenerManager().addTriggerListener(this);
        logger.info("### Quartz插件DataBaseLogTriggerPlugin初始化成功");
    }

    /**
     * 当与监听器相关联的 Trigger 被触发，Job 上的 execute() 方法将要被执行时，Scheduler 就调用这个方法<br/>
     * 在全局 TriggerListener 情况下，这个方法为所有 Trigger 被调用<br/>
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        JobDetail jobDetail = context.getJobDetail();
        Job job = context.getJobInstance();
        String schedName = "未知";
        String instanceName = "未知";
        try {
            schedName = context.getScheduler().getSchedulerName();
            instanceName = context.getScheduler().getSchedulerInstanceId();
        } catch (Throwable e) {
            logger.error("获取Scheduler标识失败", e);
        }
        QrtzTriggerLog qrtzTriggerLog = new QrtzTriggerLog();
        qrtzTriggerLog.setListenerName(name);
        qrtzTriggerLog.setSchedName(schedName);
        qrtzTriggerLog.setInstanceName(instanceName);
        qrtzTriggerLog.setJobName(jobDetail.getKey().getName());
        qrtzTriggerLog.setJobGroup(jobDetail.getKey().getGroup());
        qrtzTriggerLog.setJobClassName(job.getClass().getName());
        qrtzTriggerLog.setTriggerName(trigger.getKey().getName());
        qrtzTriggerLog.setTriggerGroup(trigger.getKey().getGroup());
        qrtzTriggerLog.setStartTime(new Date());
        // qrtzTriggerLog.setEndTime();
        // qrtzTriggerLog.setProcessTime();
        qrtzTriggerLog.setPreRunTime(context.getPreviousFireTime());
        qrtzTriggerLog.setNextRunTime(context.getNextFireTime());
        qrtzTriggerLog.setRunCount(context.getRefireCount());
        qrtzTriggerLog.setIpAddress(IPAddressUtils.getInet4AddressStr());
        // 是否错过了触发（0：否；1：是）
        qrtzTriggerLog.setMisFired('0');
        qrtzTriggerLog.setBeforeJobData(JacksonMapper.nonEmptyMapper().toJson(trigger.getJobDataMap()));
        // qrtzTriggerLog.setAfterJobData();
        // qrtzTriggerLog.setTriggerInstructionCode();
        // qrtzTriggerLog.setInstrCode();
        qrtzTriggerLog = qrtzTriggerLogService.addQrtzTriggerLog(qrtzTriggerLog);
        trigger.getJobDataMap().put(jobDataMapKey, qrtzTriggerLog.getId());
    }

    /**
     * 在 Trigger 触发后，Job 将要被执行时由 Scheduler 调用这个方法<br/>
     * TriggerListener 给了一个选择去否决 Job 的执行<br/>
     * 假如这个方法返回 true，这个 Job 将不会为此次 Trigger 触发而得到执行<br/>
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    /**
     * Scheduler 调用这个方法是在 Trigger 错过触发时<br/>
     * 你应该关注此方法中持续时间长的逻辑<br/>
     * 在出现许多错过触发的 Trigger 时，长逻辑会导致骨牌效应。你应当保持这上方法尽量的小<br/>
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
        JobDetail jobDetail;
        try {
            jobDetail = this.scheduler.getJobDetail(trigger.getJobKey());
        } catch (Throwable e) {
            logger.error("获取JobDetail失败-triggerMisfired", e);
            return;
        }
        String schedName = "未知";
        String instanceName = "未知";
        try {
            schedName = this.scheduler.getSchedulerName();
            instanceName = this.scheduler.getSchedulerInstanceId();
        } catch (Throwable e) {
            logger.error("获取Scheduler标识失败", e);
        }
        QrtzTriggerLog qrtzTriggerLog = new QrtzTriggerLog();
        qrtzTriggerLog.setListenerName(name);
        qrtzTriggerLog.setSchedName(schedName);
        qrtzTriggerLog.setInstanceName(instanceName);
        qrtzTriggerLog.setJobName(jobDetail.getKey().getName());
        qrtzTriggerLog.setJobGroup(jobDetail.getKey().getGroup());
        qrtzTriggerLog.setJobClassName(jobDetail.getJobClass().getName());
        qrtzTriggerLog.setTriggerName(trigger.getKey().getName());
        qrtzTriggerLog.setTriggerGroup(trigger.getKey().getGroup());
        qrtzTriggerLog.setStartTime(new Date());
        // qrtzTriggerLog.setEndTime();
        // qrtzTriggerLog.setProcessTime();
        qrtzTriggerLog.setPreRunTime(trigger.getPreviousFireTime());
        qrtzTriggerLog.setNextRunTime(trigger.getNextFireTime());
        qrtzTriggerLog.setRunCount(-1);
        qrtzTriggerLog.setIpAddress(IPAddressUtils.getInet4AddressStr());
        // 是否错过了触发（0：否；1：是）
        qrtzTriggerLog.setMisFired('1');
        qrtzTriggerLog.setBeforeJobData(JacksonMapper.nonEmptyMapper().toJson(trigger.getJobDataMap()));
        // qrtzTriggerLog.setAfterJobData();
        // qrtzTriggerLog.setTriggerInstructionCode();
        // qrtzTriggerLog.setInstrCode();
        qrtzTriggerLogService.addQrtzTriggerLog(qrtzTriggerLog);
    }

    /**
     * Trigger 被触发并且完成了 Job 的执行时，Scheduler 调用这个方法<br/>
     * 这不是说这个 Trigger 将不再触发了，而仅仅是当前 Trigger 的触发(并且紧接着的 Job 执行) 结束时<br/>
     * 这个 Trigger 也许还要在将来触发多次的<br/>
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        Long id = (Long) trigger.getJobDataMap().get(jobDataMapKey);
        trigger.getJobDataMap().remove(jobDataMapKey);
        if (id == null) {
            logger.warn("### trigger.getJobDataMap().get(jobDataMapKey) 返回空值-triggerComplete");
            return;
        }
        QrtzTriggerLog qrtzTriggerLog = new QrtzTriggerLog();
        qrtzTriggerLog.setId(id);
        qrtzTriggerLog.setEndTime(new Date());
        qrtzTriggerLog.setProcessTime(context.getJobRunTime());
        qrtzTriggerLog.setAfterJobData(JacksonMapper.nonEmptyMapper().toJson(trigger.getJobDataMap()));
        qrtzTriggerLogService.updateQrtzTriggerLog(qrtzTriggerLog);

        String instrCode = "UNKNOWN";
        if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.DELETE_TRIGGER) {
            instrCode = "DELETE TRIGGER";
        } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.NOOP) {
            instrCode = "DO NOTHING";
        } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.RE_EXECUTE_JOB) {
            instrCode = "RE-EXECUTE JOB";
        } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_COMPLETE) {
            instrCode = "SET ALL OF JOB'S TRIGGERS COMPLETE";
        } else if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.SET_TRIGGER_COMPLETE) {
            instrCode = "SET THIS TRIGGER COMPLETE";
        }
        qrtzTriggerLog.setTriggerInstructionCode(triggerInstructionCode.name());
        qrtzTriggerLog.setInstrCode(instrCode);
        qrtzTriggerLogService.updateQrtzTriggerLog(qrtzTriggerLog);
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
