package org.cleverframe.quartz.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-16 11:19 <br/>
 */
public class TestJobB extends QuartzJobBean {
    private final static Logger logger = LoggerFactory.getLogger(TestJobB.class);

    private String name = null;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 怎样注入name?
        logger.info("[TestJobB] ================================= name:" + name);
    }
}
