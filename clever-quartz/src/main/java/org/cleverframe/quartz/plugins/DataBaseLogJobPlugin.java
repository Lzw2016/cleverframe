package org.cleverframe.quartz.plugins;

import org.quartz.*;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;

/**
 * 记录Job执行日志的插件,日志数据存到数据库<br/>
 * 参考{@link org.quartz.plugins.history.LoggingJobHistoryPlugin}
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-7-31 23:09 <br/>
 */
public class DataBaseLogJobPlugin implements SchedulerPlugin, JobListener {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

    }

    @Override
    public void initialize(String name, Scheduler scheduler, ClassLoadHelper loadHelper) throws SchedulerException {

    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }
}
