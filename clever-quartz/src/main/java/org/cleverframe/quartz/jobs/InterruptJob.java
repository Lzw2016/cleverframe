package org.cleverframe.quartz.jobs;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-8 18:59 <br/>
 */
public class InterruptJob implements InterruptableJob {
    private final static Logger logger = LoggerFactory.getLogger(InterruptJob.class);

    private boolean flag = true;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        flag = false;
        logger.info("################### [InterruptJob] =========== 被中断");
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        for (int i = 0; i < 100000; i++) {
            if (!flag) {
                logger.info("[InterruptJob] ================================= 中断测试任务 | 任务中断退出");
                return;
            }
            try {
                Thread.sleep(1000 * 2);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            logger.info("[InterruptJob] ================================= 中断测试任务 | " + i);
        }
    }
}
