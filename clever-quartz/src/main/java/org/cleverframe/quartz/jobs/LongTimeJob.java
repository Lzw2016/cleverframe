package org.cleverframe.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-14 16:50 <br/>
 */
public class LongTimeJob implements Job {
    private final static Logger logger = LoggerFactory.getLogger(PrintPropsJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        for (int i = 0; i < 10000; i++) {
            try {
                Thread.sleep(1000 * 2);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            logger.info("[LongTimeJob] ================================= 耗时的Job");
        }
    }
}
