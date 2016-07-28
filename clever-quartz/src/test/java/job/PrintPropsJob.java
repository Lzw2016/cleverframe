package job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-26 19:44 <br/>
 */
public class PrintPropsJob implements Job {

    public PrintPropsJob() {
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap data = jobExecutionContext.getMergedJobDataMap();
        System.out.println("someProp = " + data.getString("someProp"));
    }
}