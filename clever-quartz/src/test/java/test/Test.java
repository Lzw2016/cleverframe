package test;

import job.PrintPropsJob;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-26 17:07 <br/>
 */
public class Test {
    private final static Logger logger = LoggerFactory.getLogger(Test.class);

    /**
     * 所有测试开始之前运行
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    /**
     * 所有测试结束之后运行
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    /**
     * 每一个测试方法之前运行
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * 每一个测试方法之后运行
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void test01() throws Exception {
        StdSchedulerFactory sf = new StdSchedulerFactory();
        sf.initialize("E:\\Source\\cleverframe\\clever-quartz\\src\\test\\resources\\quartz.properties");
        Scheduler scheduler = sf.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(PrintPropsJob.class)
                .withIdentity("job1", "group1")
                .usingJobData("someProp", "someValue")
                .build();
        jobDetail.getJobDataMap().put("someProp","XXXXXX");

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .endAt(new Date(System.currentTimeMillis() + (1000 * 1000)))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();

        for (String group : scheduler.getTriggerGroupNames()) {
            System.out.println("=========================-" + group);
            // enumerate each trigger in group
            for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.<TriggerKey>groupEquals(group))) {
                System.out.println("Found trigger identified by: " + triggerKey);
            }
        }


        System.out.println("启动----------------");

        for (int i = 1; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println(i);
        }

        scheduler.shutdown();
        System.out.println("关闭----------------");
    }
}
