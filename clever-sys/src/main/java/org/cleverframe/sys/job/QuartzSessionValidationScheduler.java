package org.cleverframe.sys.job;

import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 验证Shiro会话(Session)的定时任务<br/>
 * 由于shiro-quartz不支持最新版本的quartz所以自己实现，参考 org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler
 * <p>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/10 23:35 <br/>
 */
public class QuartzSessionValidationScheduler implements SessionValidationScheduler {

    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/
    /**
     * 默认会话验证的时间间隔，参考 {@link #setSessionValidationInterval(int)}
     */
    public static final int DEFAULT_SESSION_VALIDATION_INTERVAL = 1000 * 60 * 60;

    /**
     * 定时任务名称
     */
    private static final String JOB_NAME = "SessionValidationJob";

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    private static final Logger log = LoggerFactory.getLogger(QuartzSessionValidationScheduler.class);

    /**
     * 调度器对象 {@link StdSchedulerFactory#getDefaultScheduler()}
     */
    private Scheduler scheduler;

    /**
     * 是否顺便启动和停止调度器
     */
    private boolean schedulerImplicitlyCreated = false;

    /**
     * 定时任务是否启动
     */
    private boolean enabled = false;

    /**
     * Shiro的SessionManager
     */
    private ValidatingSessionManager sessionManager;

    /**
     * 会话验证的时间间隔
     */
    private int sessionValidationInterval = DEFAULT_SESSION_VALIDATION_INTERVAL;

    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /**
     * 默认的构造
     */
    public QuartzSessionValidationScheduler() {
    }

    /**
     * 构造 设置 Shiro的SessionManager
     *
     * @param sessionManager Shiro的SessionManager
     */
    public QuartzSessionValidationScheduler(ValidatingSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

    /**
     * 获取Spring注入的Scheduler(调度器)
     */
    protected Scheduler getScheduler() throws SchedulerException {
        if (scheduler == null) {
            scheduler = SpringContextHolder.getBean(SpringBeanNames.SchedulerFactoryBean);
        }
        if (scheduler == null) {
            RuntimeException exception = new RuntimeException("### SchedulerFactoryBean注入失败");
            log.error("### SchedulerFactoryBean注入失败", exception);
            throw exception;
        }
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setSessionManager(ValidatingSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * 设置会话验证的时间间隔 (定时任务运行的时间间隔)
     * {@link org.apache.shiro.session.mgt.ValidatingSessionManager#validateSessions() ValidatingSessionManager#validateSessions()}
     * <p>
     * <p>Unless this method is called, the default value is {@link #DEFAULT_SESSION_VALIDATION_INTERVAL}.
     *
     * @param sessionValidationInterval 会话验证的时间间隔
     */
    public void setSessionValidationInterval(int sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    /**
     * 开始启用定时任务，定时验证会话是否有效 (只需要运行一次)
     * 参考 {@link org.cleverframe.sys.job.QuartzSessionValidationJob}
     */
    public void enableSessionValidation() {
        if (log.isDebugEnabled()) {
            log.debug("Shiro会话验证调度器开始, 验证时间间隔:[{}]ms", sessionValidationInterval);
        }
        if (sessionManager == null) {
            throw new RuntimeException("必须注入sessionManager属性");
        }
        QuartzSessionValidationJob.setSessionManager(sessionManager);
        try {
            Scheduler scheduler = getScheduler();
            JobKey jobKey = new JobKey(JOB_NAME, Scheduler.DEFAULT_GROUP);
            JobDetail detail = scheduler.getJobDetail(jobKey);
            if (detail == null) {
                SimpleTrigger trigger = newTrigger()
                        .withIdentity(getClass().getName(), Scheduler.DEFAULT_GROUP)
                        .withSchedule(simpleSchedule()
                                .withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY)
                                .withIntervalInMilliseconds(sessionValidationInterval))
                        .build();
                detail = newJob(QuartzSessionValidationJob.class)
                        .withIdentity(JOB_NAME, Scheduler.DEFAULT_GROUP)
                        .storeDurably(false)
                        .build();
                scheduler.scheduleJob(detail, trigger);
            } else {
                log.warn("定时验证会话是否有效的定时任务已经存在[{}]", jobKey.toString());
            }
            if (schedulerImplicitlyCreated && !scheduler.isStarted()) {
                scheduler.start();
                log.warn("已启动调度器,执行: scheduler.start()");
            }
            this.enabled = true;
            log.debug("Shiro会话验证调度器启用成功，等待执行");

        } catch (SchedulerException e) {
            log.error("Shiro会话验证调度器启用失败", e);
        }
    }

    /**
     * 开始禁用定时任务 (只需要运行一次)
     */
    public void disableSessionValidation() {
        log.debug("开始停止Shiro会话验证调度器...");
        try {
            Scheduler scheduler = getScheduler();
            TriggerKey triggerKey = new TriggerKey(getClass().getName(), Scheduler.DEFAULT_GROUP);
            scheduler.unscheduleJob(triggerKey);
            JobKey jobKey = new JobKey(JOB_NAME, Scheduler.DEFAULT_GROUP);
            scheduler.deleteJob(jobKey);
            log.debug("停止Shiro会话验证调度器成功");
        } catch (Exception e) {
            log.debug("停止Shiro会话验证调度器失败", e);
            return;
        }
        this.enabled = false;
        if (schedulerImplicitlyCreated) {
            try {
                scheduler.shutdown();
                log.warn("已停止调度器,执行: scheduler.shutdown()");
            } catch (SchedulerException e) {
                log.warn("停止调度器失败,执行: scheduler.shutdown()", e);
            } finally {
                setScheduler(null);
                schedulerImplicitlyCreated = false;
            }
        }
    }
}