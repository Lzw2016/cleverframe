package utils;

import org.cleverframe.common.utils.RMBUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 11:40 <br/>
 */
public class RMBUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(PinyinUtilsTest.class);

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
    public void testNumToRMBStr() {
        logger.info(RMBUtils.numToRMBStr(0.00));
        logger.info(RMBUtils.numToRMBStr(0.01));
        logger.info(RMBUtils.numToRMBStr(0.22));
        logger.info(RMBUtils.numToRMBStr(0.30));
        logger.info(RMBUtils.numToRMBStr(4.00));
        logger.info(RMBUtils.numToRMBStr(55.21));
        logger.info(RMBUtils.numToRMBStr(654.01));

        logger.info(RMBUtils.numToRMBStr(11234567890123.11));

        logger.info(RMBUtils.numToRMBStr(1234567890123.11));

        logger.info(RMBUtils.numToRMBStr(2147483647.99));

    }
}
