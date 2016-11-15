package org.cleverframe.quartz.plugins;

import org.cleverframe.common.exception.ExceptionUtils;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.common.utils.IPAddressUtils;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.entity.QrtzSchedulerLog;
import org.cleverframe.quartz.service.QrtzSchedulerLogService;
import org.quartz.*;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录Scheduler日志的插件,日志数据存到数据库<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-8-2 0:19 <br/>
 */
public class DataBaseLogSchedulerPlugin implements SchedulerPlugin, SchedulerListener {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(DataBaseLogSchedulerPlugin.class);

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

    private QrtzSchedulerLogService qrtzSchedulerLogService;

    /**
     * 保存触发日志数据
     *
     * @param methodName 触发事件调用的方法
     * @param logData    触发事件记录的日志数据
     */
    private void saveLog(String methodName, Map<String, Object> logData) {
        String schedName = "未知";
        String instanceName = "未知";
        try {
            schedName = this.scheduler.getSchedulerName();
            instanceName = this.scheduler.getSchedulerInstanceId();
        } catch (Throwable e) {
            logger.error("获取Scheduler标识失败", e);
        }
        QrtzSchedulerLog qrtzSchedulerLog = new QrtzSchedulerLog();
        qrtzSchedulerLog.setListenerName(name);
        qrtzSchedulerLog.setSchedName(schedName);
        qrtzSchedulerLog.setInstanceName(instanceName);
        qrtzSchedulerLog.setMethodName(methodName);
        qrtzSchedulerLog.setLogData(JacksonMapper.nonEmptyMapper().toJson(logData));
        qrtzSchedulerLog.setIpAddress(IPAddressUtils.getInet4AddressStr());
        qrtzSchedulerLog.setLogTime(new Date());
        qrtzSchedulerLogService.saveQrtzSchedulerLog(qrtzSchedulerLog);
    }

    /**
     * 部署保存一个Trigger
     */
    @Override
    public void jobScheduled(Trigger trigger) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "json");
        logData.put("data", trigger);
        saveLog("jobScheduled", logData);
    }

    /**
     *
     */
    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "json");
        logData.put("data", triggerKey);
        saveLog("jobUnscheduled", logData);
    }

    /**
     *
     */
    @Override
    public void triggerFinalized(Trigger trigger) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "json");
        logData.put("data", trigger);
        saveLog("triggerFinalized", logData);
    }

    /**
     *
     */
    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "json");
        logData.put("data", triggerKey);
        saveLog("triggerPaused", logData);
    }

    /**
     *
     */
    @Override
    public void triggersPaused(String triggerGroup) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "string");
        logData.put("data", triggerGroup);
        saveLog("triggersPaused", logData);
    }

    /**
     *
     */
    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "json");
        logData.put("data", triggerKey);
        saveLog("triggerResumed", logData);
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "string");
        logData.put("data", triggerGroup);
        saveLog("triggersResumed", logData);
    }

    /**
     *
     */
    @Override
    public void jobAdded(JobDetail jobDetail) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "json");
        logData.put("data", jobDetail);
        saveLog("jobAdded", logData);
    }

    /**
     *
     */
    @Override
    public void jobDeleted(JobKey jobKey) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "json");
        logData.put("data", jobKey);
        saveLog("jobDeleted", logData);
    }

    /**
     *
     */
    @Override
    public void jobPaused(JobKey jobKey) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "json");
        logData.put("data", jobKey);
        saveLog("jobPaused", logData);
    }

    /**
     *
     */
    @Override
    public void jobsPaused(String jobGroup) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "string");
        logData.put("data", jobGroup);
        saveLog("jobsPaused", logData);
    }

    /**
     *
     */
    @Override
    public void jobResumed(JobKey jobKey) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "json");
        logData.put("data", jobKey);
        saveLog("jobResumed", logData);
    }

    /**
     *
     */
    @Override
    public void jobsResumed(String jobGroup) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "string");
        logData.put("data", jobGroup);
        saveLog("jobsResumed", logData);
    }

    /**
     *
     */
    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "java");
        logData.put("data", msg + "\r\n" + ExceptionUtils.getStackTraceAsString(cause));
        saveLog("schedulerError", logData);
    }

    /**
     *
     */
    @Override
    public void schedulerInStandbyMode() {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "null");
        logData.put("data", null);
        saveLog("schedulerInStandbyMode", logData);
    }

    /**
     *
     */
    @Override
    public void schedulerStarted() {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "null");
        logData.put("data", null);
        saveLog("schedulerStarted", logData);
    }

    /**
     *
     */
    @Override
    public void schedulerStarting() {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "null");
        logData.put("data", null);
        saveLog("schedulerStarting", logData);
    }

    /**
     *
     */
    @Override
    public void schedulerShutdown() {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "null");
        logData.put("data", null);
        saveLog("schedulerShutdown", logData);
    }

    /**
     *
     */
    @Override
    public void schedulerShuttingdown() {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "null");
        logData.put("data", null);
        saveLog("schedulerShuttingdown", logData);
    }

    /**
     *
     */
    @Override
    public void schedulingDataCleared() {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "null");
        logData.put("data", null);
        saveLog("schedulingDataCleared", logData);
    }

    /* ======================================================================================================================================== */

    /**
     * Scheduler 的创建期间被调用<br/>
     * 当 StdSchedulerFactory 的 getScheduler() 方法被调用后，这个工厂就调用所有注册的插件的 initialize() 方法<br/>
     */
    @Override
    public void initialize(String name, Scheduler scheduler, ClassLoadHelper loadHelper) throws SchedulerException {
        qrtzSchedulerLogService = SpringContextHolder.getBean(QuartzBeanNames.QrtzSchedulerLogService);
        if (qrtzSchedulerLogService != null) {
            logger.info("### QrtzSchedulerLogService注入成功");
        } else {
            RuntimeException exception = new RuntimeException("Quartz插件DataBaseLogSchedulerPlugin初始化失败，QrtzSchedulerLogService注入失败");
            logger.info(exception.getMessage(), exception);
            throw exception;
        }
        this.name = name;
        this.jobDataMapKey = name + this.jobDataMapKey;
        this.scheduler = scheduler;
        scheduler.getListenerManager().addSchedulerListener(this);
        logger.info("### Quartz插件DataBaseLogSchedulerPlugin初始化成功");
    }

    /**
     * Scheduler 实例调用 start() 方法让插件知道它可以执行任何需要的启动动作了
     */
    @Override
    public void start() {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "null");
        logData.put("data", null);
        saveLog("start", logData);
    }

    /**
     * shutdown() 方法被调用来通知插件 Scheduler 将要关闭了<br/>
     * 这是给插件的一个机会去清理任何打开的资源<br/>
     */
    @Override
    public void shutdown() {
        Map<String, Object> logData = new HashMap<>();
        logData.put("dataType", "null");
        logData.put("data", null);
        saveLog("shutdown", logData);
    }
}
