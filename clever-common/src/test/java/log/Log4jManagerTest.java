package log;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.cleverframe.common.log.Log4jManager;
import org.cleverframe.common.time.DateTimeUtils;
import org.junit.*;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-4-28 16:57 <br/>
 */
public class Log4jManagerTest {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(Log4jManagerTest.class);

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


    @Test
    public void testLog() {
        logger.error("测试Log4j日志输出功能");
    }

    /**
     * 测试获取所有的日志对象
     */
    @Test
    public void testAllGetLogList() {
        // 加载一些类型，为了加载更多的Logger
        DateTimeUtils.getCurrentDate();

        // 输出所有日志级别
        List<Logger> loggerList = Log4jManager.getAllLogList();
        for (Logger logger : loggerList) {
            logger.info("----------------------------------------------------------------------------------------------");
            logger.info("getName - " + logger.getName());
            Enumeration appenderList = logger.getAllAppenders();
            while (appenderList.hasMoreElements()) {
                Appender appender = (Appender) appenderList.nextElement();
                logger.info(appender.getName());
                logger.info(appender.getFilter());
                logger.info(appender.getLayout().getClass().getName());
                if (appender.getLayout() instanceof PatternLayout) {
                    PatternLayout patternLayout = (PatternLayout) appender.getLayout();
                    logger.info(patternLayout.getConversionPattern());
                }
                logger.info(appender.getLayout().getContentType());
                logger.info(appender.getLayout().getHeader());
                logger.info(appender.getLayout().getFooter());
                logger.info(appender.getErrorHandler());
            }
            logger.info("getLevel - " + logger.getLevel());
            logger.info("getEffectiveLevel - " + logger.getEffectiveLevel());
            logger.info("getAdditivity - " + logger.getAdditivity());
        }
    }

    /**
     * 测试获取所有的日志对象的信息 Json格式
     */
    @Test
    public void testAllGetLogListInfo() {
        // 加载一些类型，为了加载更多的Logger
        DateTimeUtils.getCurrentDate();

        // 输出所有日志级别
        List<Logger> loggerList = Log4jManager.getAllLogList();
        for (Logger logger : loggerList) {
            logger.info("\r\n" + JSON.toJSONString(logger, true));
        }
    }

    /**
     * 测试动态的修改日志级别
     */
    @Test
    public void testEnableInfo() {
        String name = Log4jManagerTest.class.getName();
        Log4jManager.enableInfo(name);
        logger.info("testEnableInfo - 001");
        Log4jManager.enableError(name);
        logger.info("testEnableInfo - 002");
        logger.info("testEnableInfo - 003");
        Log4jManager.enableInfo(name);
        logger.info("testEnableInfo - 004");
    }
}
