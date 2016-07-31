package org.cleverframe.quartz.plugins;

import org.quartz.*;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;

/**
 * 记录Trigger触发的日志的插件,日志数据存到数据库<br/>
 * 参考{@link org.quartz.plugins.history.LoggingTriggerHistoryPlugin}
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-7-31 23:12 <br/>
 */
public class DataBaseLogTriggerPlugin implements SchedulerPlugin, TriggerListener {
    @Override
    public void initialize(String name, Scheduler scheduler, ClassLoadHelper loadHelper) throws SchedulerException {

    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {

    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }
}
