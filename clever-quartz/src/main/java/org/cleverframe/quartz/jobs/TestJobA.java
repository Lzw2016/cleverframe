package org.cleverframe.quartz.jobs;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-16 10:47 <br/>
 */
@DisallowConcurrentExecution
public class TestJobA implements Job {
    private final static Logger logger = LoggerFactory.getLogger(TestJobA.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        for (Map.Entry<String, Object> entry : jobDataMap.entrySet()) {
            logger.info("[TestJobA] ================================= Key:" + entry.getKey() + " || value:" + entry.getValue());
        }
    }
}
