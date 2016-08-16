package org.cleverframe.quartz.utils;

import org.cleverframe.common.exception.ExceptionUtils;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Quartz管理类
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-7-29 11:39 <br/>
 */
public class QuartzManager {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(QuartzManager.class);

    private final static Scheduler SCHEDULER;

    static {
        try {
            SCHEDULER = SpringContextHolder.getBean(SpringBeanNames.SchedulerFactoryBean);
            if (SCHEDULER != null) {
                logger.info("### SchedulerFactoryBean注入成功");
            } else {
                RuntimeException exception = new RuntimeException("### SchedulerFactoryBean注入失败");
                logger.error("### SchedulerFactoryBean注入失败", exception);
                throw exception;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 返回basePackage包下面所有的Job子类
     *
     * @param basePackage 扫描的基础包
     * @return 所有的Job子类集合
     */
    @SuppressWarnings("deprecation")
    public static List<String> getAllJobClassName(String basePackage) {
        List<String> JobClassNameList = new ArrayList<>();
        Reflections reflections = new Reflections(basePackage);
        Set<Class<? extends Job>> jobClassSet = reflections.getSubTypesOf(Job.class);
        JobClassNameList.addAll(jobClassSet.stream().map(Class::getName).collect(Collectors.toList()));

        Set<Class<? extends InterruptableJob>> interruptableJobClassSet = reflections.getSubTypesOf(InterruptableJob.class);
        JobClassNameList.addAll(interruptableJobClassSet.stream().map(Class::getName).collect(Collectors.toList()));

        Set<Class<? extends QuartzJobBean>> quartzJobBeanClassSet = reflections.getSubTypesOf(QuartzJobBean.class);
        JobClassNameList.addAll(quartzJobBeanClassSet.stream().map(Class::getName).collect(Collectors.toList()));

        Set<Class<? extends StatefulJob>> statefulJobClassSet = reflections.getSubTypesOf(StatefulJob.class);
        JobClassNameList.addAll(statefulJobClassSet.stream().map(Class::getName).collect(Collectors.toList()));
        return JobClassNameList;
    }

    public static Scheduler getScheduler() {
        if (SCHEDULER == null) {
            RuntimeException exception = new RuntimeException("### SchedulerFactoryBean注入失败");
            logger.error("### SchedulerFactoryBean注入失败", exception);
            throw exception;
        }
        return SCHEDULER;
    }

    /**
     * 反射获取Job类型
     *
     * @param jobClassName 类全名
     * @return 失败返回null
     */
    public static Class getJobClass(String jobClassName) {
        try {
            Class aClass = Class.forName(jobClassName);
            if (Job.class.isAssignableFrom(aClass)) {
                return aClass;
            } else {
                logger.warn("### 类型[" + jobClassName + "]不是org.quartz.Job的子类");
            }
        } catch (Throwable e) {
            logger.error("### 获取Job类型失败[" + jobClassName + "]", e);
        }
        return null;
    }

    /**
     * 启动 Scheduler
     *
     * @return 操作成功返回true
     */
    public static boolean start() {
        try {
            if (!SCHEDULER.isStarted() || SCHEDULER.isInStandbyMode()) {
                SCHEDULER.start();
            }
        } catch (Throwable e) {
            logger.error("### Scheduler启动失败", e);
            return false;
        }
        logger.info("### 成功启动 Scheduler");
        return true;
    }

    /**
     * 关闭 Scheduler
     *
     * @return 操作成功返回true
     */
    public static boolean shutdown() {
        try {
            if (!SCHEDULER.isShutdown()) {
                SCHEDULER.shutdown();
            }
        } catch (Throwable e) {
            logger.error("### Scheduler关闭失败", e);
            return false;
        }
        logger.info("### 成功关闭 Scheduler");
        return true;
    }

    /**
     * 暂停 Scheduler<br/>
     * 调用 {@link #start()} 继续运行调度器(Scheduler)
     *
     * @return 操作成功返回true
     * @see #start()
     */
    public static boolean standby() {
        try {
            if (!SCHEDULER.isInStandbyMode()) {
                SCHEDULER.standby();
            }
        } catch (Throwable e) {
            logger.error("### Scheduler暂停失败", e);
            return false;
        }
        logger.info("### 成功暂停 Scheduler");
        return true;
    }

    /**
     * 获取所有的JobGroupName
     *
     * @return 失败返回null
     */
    public static List<String> getJobGroupNames() {
        List<String> jobGroupNames = null;
        try {
            jobGroupNames = SCHEDULER.getJobGroupNames();
        } catch (Throwable e) {
            logger.error("### 获取所有的JobGroupName失败", e);
        }
        return jobGroupNames;
    }

    /**
     * 获取所有的TriggerGroupName
     *
     * @return 失败返回null
     */
    public static List<String> getTriggerGroupNames() {
        List<String> triggerGroupNames = null;
        try {
            triggerGroupNames = SCHEDULER.getTriggerGroupNames();
        } catch (Throwable e) {
            logger.error("### 获取所有的TriggerGroupName失败", e);
        }
        return triggerGroupNames;
    }

    /**
     * 获取所有的CalendarName
     *
     * @return 失败返回null
     */
    public static List<String> getCalendarNames() {
        List<String> calendarName = null;
        try {
            calendarName = SCHEDULER.getCalendarNames();
        } catch (Throwable e) {
            logger.error("### 获取所有的CalendarName失败", e);
        }
        return calendarName;
    }

    /**
     * 获取所有的JobKey
     *
     * @return 失败返回null
     */
    public static List<JobKey> getAllJobKey() {
        List<JobKey> jobKeyList = new ArrayList<>();
        try {
            List<String> jobGroupNames = SCHEDULER.getJobGroupNames();
            for (String group : jobGroupNames) {
                Set<JobKey> jobKeySet = SCHEDULER.getJobKeys(GroupMatcher.<JobKey>groupEquals(group));
                jobKeyList.addAll(jobKeySet);
            }
        } catch (Throwable e) {
            logger.error("### 获取所有的JobKey失败", e);
            return null;
        }
        return jobKeyList;
    }

    /**
     * 获取所有的 JobDetail
     *
     * @return JobDetail集合
     */
    public static List<JobDetail> getAllJobDetail() {
        List<JobDetail> jobDetailList = new ArrayList<>();
        List<JobKey> jobKeyList = getAllJobKey();
        if (jobKeyList == null) {
            return null;
        }
        for (JobKey jobKey : jobKeyList) {
            JobDetail jobDetail = null;
            try {
                jobDetail = SCHEDULER.getJobDetail(jobKey);
            } catch (Throwable e) {
                logger.error("### 获取JobDetail失败[jobName=" + jobKey.getName() + ",jobGroup=" + jobKey.getGroup() + "]", e);
            }
            if (jobDetail != null) {
                jobDetailList.add(jobDetail);
            }
        }
        return jobDetailList;
    }

    /**
     * 获取一个Job的所有 Trigger
     *
     * @param jobName  job名称
     * @param jobGroup job组名称
     * @return 失败返回null
     */
    public static List<? extends Trigger> getTriggerByJob(String jobName, String jobGroup) {
        List<? extends Trigger> jobTriggers = null;
        try {
            jobTriggers = SCHEDULER.getTriggersOfJob(JobKey.jobKey(jobName, jobGroup));
        } catch (Throwable e) {
            logger.error("### 获取一个Job的所有Trigger失败 [jobName=" + jobName + ", jobGroup=" + jobGroup + "]", e);
        }
        return jobTriggers;
    }

    /**
     * 验证cron表达式,失败返回null
     *
     * @param cron cron表达式
     * @param num  获取cron表达式表示时间数量
     * @return 失败返回null
     */
    public static List<Date> validatorCron(String cron, int num) {
        List<Date> dateList = new ArrayList<>();
        CronExpression exp;
        try {
            exp = new CronExpression(cron);
        } catch (Throwable e) {
            logger.error("cron表达式错误[" + cron + "]", e);
            return null;
        }
        Date date = new Date();
        // 循环得到接下来n此的触发时间点，供验证
        for (int i = 0; i < num; i++) {
            date = exp.getNextValidTimeAfter(date);
            dateList.add(date);
        }
        return dateList;
    }
}
