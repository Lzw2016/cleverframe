package org.cleverframe.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-16 17:29 <br/>
 */
public class TestJobC implements Job {
    private final static Logger logger = LoggerFactory.getLogger(TestJobC.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("[TestJobC] ================================= 抛出异常测试");
        throw new RuntimeException("Job测试异常");
    }
}
