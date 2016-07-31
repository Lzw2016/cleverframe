package org.cleverframe.quartz.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.time.DateTimeUtils;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.utils.QuartzManager;
import org.cleverframe.quartz.vo.model.QrtzTriggers;
import org.quartz.*;
import org.quartz.impl.triggers.CalendarIntervalTriggerImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.DailyTimeIntervalTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-29 16:43 <br/>
 */
@Service(QuartzBeanNames.TriggerService)
public class TriggerService extends BaseService {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(TriggerService.class);

    /**
     * 验证cron表达式,失败返回null
     *
     * @param cron cron表达式
     * @param num  获取cron表达式表示时间数量
     * @return 失败返回null
     */
    public List<String> validatorCron(String cron, int num, AjaxMessage ajaxMessage) {
        List<Date> dateList = QuartzManager.validatorCron(cron, num);
        List<String> daStrList = new ArrayList<>();
        if (dateList == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("cron表达式验证失败");
        } else {
            for (Date date : dateList) {
                daStrList.add(DateTimeUtils.formatToString(date, "yyyy-MM-dd HH:mm:ss"));
            }
        }
        return daStrList;
    }

    /**
     * 使用Trigger的基础属性创建一个TriggerBuilder
     *
     * @param jobName      job名称,必填
     * @param jobGroup     job组名称,必填
     * @param triggerName  trigger组名称,必填
     * @param triggerGroup trigger组名称,必填
     * @param description  trigger描述,可为null
     * @param startTime    开始触发时间,必填
     * @param endTime      结束触发时间,可为null
     * @param priority     优先级,可为null
     * @param jobDataMap   Trigger数据,可为null
     * @return 返回一个 TriggerBuilder
     */
    private TriggerBuilder<Trigger> newTriggerBuilder(
            String jobName,
            String jobGroup,
            String triggerName,
            String triggerGroup,
            String description,
            Date startTime,
            Date endTime,
            Integer priority,
            Map<String, String> jobDataMap) {
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger().forJob(jobName, jobGroup);
        triggerBuilder.withIdentity(triggerName, triggerGroup);
        triggerBuilder.withDescription(description);
        triggerBuilder.startAt(startTime);
        if (endTime != null) {
            triggerBuilder.endAt(endTime);
        }
        if (priority != null) {
            triggerBuilder.withPriority(priority);
        }
        if (jobDataMap != null && jobDataMap.size() > 0) {
            for (Map.Entry<String, String> entry : jobDataMap.entrySet()) {
                triggerBuilder.usingJobData(entry.getKey(), entry.getValue());
            }
        }
        return triggerBuilder;
    }

    /**
     * 创建一个SimpleScheduleBuilder
     *
     * @param interval           触发的时间间隔(毫秒),必填
     * @param repeatCount        触发的次数,可为null,次数不限取值:-1(SimpleTrigger.REPEAT_INDEFINITELY)
     * @param misfireInstruction Quartz的Misfire处理规则(SimpleTrigger),可为null
     * @return 返回一个 SimpleScheduleBuilder
     */
    private SimpleScheduleBuilder newSimpleScheduleBuilder(long interval, Integer repeatCount, Integer misfireInstruction) {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        simpleScheduleBuilder.withIntervalInMilliseconds(interval);
        if (repeatCount != null && repeatCount >= 1) {
            simpleScheduleBuilder.withRepeatCount(repeatCount);
        }
        if (misfireInstruction != null) {
            switch (misfireInstruction) {
                case SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW:
                    simpleScheduleBuilder.withMisfireHandlingInstructionFireNow();
                    break;
                case Trigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                    simpleScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
                    break;
                case SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT:
                    simpleScheduleBuilder.withMisfireHandlingInstructionNextWithExistingCount();
                    break;
                case SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT:
                    simpleScheduleBuilder.withMisfireHandlingInstructionNextWithRemainingCount();
                    break;
                case SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT:
                    simpleScheduleBuilder.withMisfireHandlingInstructionNowWithExistingCount();
                    break;
                case SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT:
                    simpleScheduleBuilder.withMisfireHandlingInstructionNowWithRemainingCount();
                    break;
            }
        }
        return simpleScheduleBuilder;
    }

    /**
     * 创建一个CronScheduleBuilder
     *
     * @param cron               cron表达式,必填
     * @param misfireInstruction Quartz的Misfire处理规则(CronTrigger),可为null
     * @return 返回一个 CronScheduleBuilder
     */
    private CronScheduleBuilder newCronScheduleBuilder(String cron, Integer misfireInstruction) {
        CronScheduleBuilder cronScheduleBuilder;
        try {
            cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        } catch (Throwable e) {
            logger.error("创建CronScheduleBuilder异常", e);
            return null;
        }
        if (misfireInstruction != null) {
            switch (misfireInstruction) {
                case Trigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY:
                    cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
                    break;
                case CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING:
                    cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
                    break;
                case CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW:
                    cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
                    break;
            }
        }
        return cronScheduleBuilder;
    }

    /**
     * 给JobDetail增加一个SimpleTrigger
     * <pre>
     * Quartz的Misfire处理规则(SimpleTrigger)
     * 1.withMisfireHandlingInstructionFireNow
     *      -以当前时间为触发频率立即触发执行
     *      -执行至FinalTime的剩余周期次数
     *      -以调度或恢复调度的时刻为基准的周期频率，FinalTime根据剩余次数和当前时间计算得到
     *      -调整后的FinalTime会略大于根据startTime计算的到的FinalTime值
     * 2.withMisfireHandlingInstructionIgnoreMisfires
     *      -以错过的第一个频率时间立刻开始执行
     *      -重做错过的所有频率周期
     *      -当下一次触发频率发生时间大于当前时间以后，按照Interval的依次执行剩下的频率
     *      -共执行RepeatCount+1次
     * 3.withMisfireHandlingInstructionNextWithExistingCount
     *      -不触发立即执行
     *      -等待下次触发频率周期时刻，执行至FinalTime的剩余周期次数
     *      -以startTime为基准计算周期频率，并得到FinalTime
     *      -即使中间出现pause，resume以后保持FinalTime时间不变
     * 4.withMisfireHandlingInstructionNextWithRemainingCount
     *      -不触发立即执行
     *      -等待下次触发频率周期时刻，执行至FinalTime的剩余周期次数
     *      -以startTime为基准计算周期频率，并得到FinalTime
     *      -即使中间出现pause，resume以后保持FinalTime时间不变
     * 5.withMisfireHandlingInstructionNowWithRemainingCount
     *      -以当前时间为触发频率立即触发执行
     *      -执行至FinalTIme的剩余周期次数
     *      -以调度或恢复调度的时刻为基准的周期频率，FinalTime根据剩余次数和当前时间计算得到
     *      -调整后的FinalTime会略大于根据startTime计算的到的FinalTime值
     * 6.misfireInstructionRescheduleNowWithRemainingRepeatCount
     *      -此指令导致trigger忘记原始设置的startTime和repeat-count
     *      -触发器的repeat-count将被设置为剩余的次数
     *      -这样会导致后面无法获得原始设定的startTime和repeat-count值
     * </pre>
     *
     * @param jobName            job名称,必填
     * @param jobGroup           job组名称,必填
     * @param triggerName        trigger组名称,必填
     * @param triggerGroup       trigger组名称,必填
     * @param description        trigger描述,可为null
     * @param startTime          开始触发时间,必填
     * @param endTime            结束触发时间,可为null
     * @param priority           优先级,可为null
     * @param jobDataMap         Trigger数据,可为null
     * @param interval           触发的时间间隔(毫秒),必填
     * @param repeatCount        触发的次数,可为null,次数不限取值:-1(SimpleTrigger.REPEAT_INDEFINITELY)
     * @param misfireInstruction Quartz的Misfire处理规则(SimpleTrigger),可为null
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean addSimpleTriggerForJob(
            String jobName,
            String jobGroup,
            String triggerName,
            String triggerGroup,
            String description,
            Date startTime,
            Date endTime,
            Integer priority,
            Map<String, String> jobDataMap,
            long interval,
            Integer repeatCount,
            Integer misfireInstruction,
            AjaxMessage ajaxMessage) {
        TriggerBuilder<Trigger> triggerBuilder = newTriggerBuilder(jobName, jobGroup, triggerName, triggerGroup, description, startTime, endTime, priority, jobDataMap);
        SimpleScheduleBuilder simpleScheduleBuilder = newSimpleScheduleBuilder(interval, repeatCount, misfireInstruction);
        triggerBuilder.withSchedule(simpleScheduleBuilder);
        Trigger trigger = triggerBuilder.build();
        Scheduler scheduler = QuartzManager.getScheduler();
        try {
            scheduler.scheduleJob(trigger);
        } catch (Throwable e) {
            logger.error("给JobDetail增加一个SimpleTrigger异常", e);
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("给JobDetail增加一个SimpleTrigger失败");
            return false;
        }
        return true;
    }

    /**
     * 给JobDetail增加一个SimpleTrigger
     * <pre>
     * 1.withMisfireHandlingInstructionDoNothing
     *      -不触发立即执行
     *      -等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
     * 2.withMisfireHandlingInstructionIgnoreMisfires
     *      -以错过的第一个频率时间立刻开始执行
     *      -重做错过的所有频率周期后
     *      -当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
     * 3.withMisfireHandlingInstructionFireAndProceed
     *      -以当前时间为触发频率立刻触发一次执行
     *      -然后按照Cron频率依次执行
     * </pre>
     *
     * @param jobName            job名称,必填
     * @param jobGroup           job组名称,必填
     * @param triggerName        trigger组名称,必填
     * @param triggerGroup       trigger组名称,必填
     * @param description        trigger描述,可为null
     * @param startTime          开始触发时间,必填
     * @param endTime            结束触发时间,可为null
     * @param priority           优先级,可为null
     * @param jobDataMap         Trigger数据,可为null
     * @param cron               cron表达式,必填
     * @param misfireInstruction Quartz的Misfire处理规则(CronTrigger),可为null
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean addCronTriggerForJob(
            String jobName,
            String jobGroup,
            String triggerName,
            String triggerGroup,
            String description,
            Date startTime,
            Date endTime,
            Integer priority,
            Map<String, String> jobDataMap,
            String cron,
            Integer misfireInstruction,
            AjaxMessage ajaxMessage) {
        TriggerBuilder<Trigger> triggerBuilder = newTriggerBuilder(jobName, jobGroup, triggerName, triggerGroup, description, startTime, endTime, priority, jobDataMap);
        CronScheduleBuilder cronScheduleBuilder = newCronScheduleBuilder(cron, misfireInstruction);
        triggerBuilder.withSchedule(cronScheduleBuilder);
        Trigger trigger = triggerBuilder.build();
        Scheduler scheduler = QuartzManager.getScheduler();
        try {
            scheduler.scheduleJob(trigger);
        } catch (Throwable e) {
            logger.error("给JobDetail增加一个CronTrigger异常", e);
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("给JobDetail增加一个CronTrigger失败");
            return false;
        }
        return true;
    }

    /**
     * 获取一个Job的所有 Trigger
     *
     * @param jobName  job名称
     * @param jobGroup job组名称
     * @return 失败返回null
     */
    public List<QrtzTriggers> getTriggerByJob(String jobName, String jobGroup, AjaxMessage ajaxMessage) {
        List<QrtzTriggers> qrtzTriggersList = new ArrayList<>();
        List<? extends Trigger> triggerList = QuartzManager.getTriggerByJob(jobName, jobGroup);
        if (triggerList == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("获取JobDetail的所有Trigger失败");
            return null;
        }
        Scheduler scheduler = QuartzManager.getScheduler();
        String schedName = null;
        try {
            schedName = scheduler.getSchedulerName();
        } catch (Throwable e) {
            logger.error("获取SchedulerName失败", e);
        }
        for (Trigger trigger : triggerList) {
            QrtzTriggers qrtzTriggers = new QrtzTriggers();
            qrtzTriggers.setSchedName(schedName);
            qrtzTriggers.setTriggerName(trigger.getKey().getName());
            qrtzTriggers.setTriggerGroup(trigger.getKey().getGroup());
            qrtzTriggers.setJobName(trigger.getJobKey().getName());
            qrtzTriggers.setJobGroup(trigger.getJobKey().getGroup());
            qrtzTriggers.setDescription(trigger.getDescription());
            qrtzTriggers.setNextFireTime(trigger.getNextFireTime());
            qrtzTriggers.setPrevFireTime(trigger.getPreviousFireTime());
            qrtzTriggers.setPriority(trigger.getPriority());
            try {
                qrtzTriggers.setTriggerState(scheduler.getTriggerState(trigger.getKey()).name());
            } catch (Throwable e) {
                logger.error("获取Trigger状态失败");
            }
            qrtzTriggers.setTriggerType(trigger.getClass().getName());
            qrtzTriggers.setStartTime(trigger.getStartTime());
            qrtzTriggers.setEndTime(trigger.getEndTime());
            qrtzTriggers.setCalendarName(trigger.getCalendarName());
            qrtzTriggers.setMisfireInstr(trigger.getMisfireInstruction());
            qrtzTriggers.setJobData(trigger.getJobDataMap());
            if (trigger instanceof SimpleTriggerImpl) {
                SimpleTriggerImpl simpleTrigger = (SimpleTriggerImpl) trigger;
                qrtzTriggers.setRepeatCount(simpleTrigger.getRepeatCount());
                qrtzTriggers.setRepeatInterval(simpleTrigger.getRepeatInterval());
                qrtzTriggers.setTimesTriggered(simpleTrigger.getTimesTriggered());
            }
            if (trigger instanceof CronTriggerImpl) {
                CronTriggerImpl cronTrigger = (CronTriggerImpl) trigger;
                qrtzTriggers.setCronEx(cronTrigger.getCronExpression());
                qrtzTriggers.setTimeZoneId(cronTrigger.getTimeZone() == null ? null : cronTrigger.getTimeZone().getID());
            }
            //noinspection StatementWithEmptyBody
            if (trigger instanceof CalendarIntervalTriggerImpl) {
                // TODO CalendarIntervalTriggerImpl

            }
            //noinspection StatementWithEmptyBody
            if (trigger instanceof DailyTimeIntervalTriggerImpl) {
                // TODO CalendarIntervalTriggerImpl
            }
            qrtzTriggersList.add(qrtzTriggers);
        }
        return qrtzTriggersList;
    }
}
