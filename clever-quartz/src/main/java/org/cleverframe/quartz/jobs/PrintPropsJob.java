package org.cleverframe.quartz.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-30 14:49 <br/>
 */
@DisallowConcurrentExecution
public class PrintPropsJob implements Job {
    private final static Logger logger = LoggerFactory.getLogger(PrintPropsJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        // @DisallowConcurrentExecution 对应 isNonconcurrent 不能并发执行
        // @PersistJobDataAfterExecution 对应 isUpdateData

        // JobDataMap data = context.getMergedJobDataMap();
        try {
            Thread.sleep(1000 * 2);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        logger.info("[PrintPropsJob] ================================= 测试任务");
    }
}
