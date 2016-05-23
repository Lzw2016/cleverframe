package utils;

import org.cleverframe.common.utils.IdCardUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-9 23:20 <br/>
 */
public class IdCardUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(IdCardUtilsTest.class);

    private static long time;

    /**
     * 所有测试开始之前运行
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        time = System.currentTimeMillis();
    }

    /**
     * 所有测试结束之后运行
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.info("测试用时：" + (System.currentTimeMillis() - time));
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
    public void test() {
        String idCard = "42900419930611803X";
        logger.info(IdCardUtils.getBirthByIdCard(idCard));
        logger.info(IdCardUtils.getGenderByIdCard(idCard));
        logger.info(IdCardUtils.getProvinceByIdCard(idCard));
        logger.info(IdCardUtils.getAgeByIdCard(idCard) + "");
        logger.info(IdCardUtils.getDateByIdCard(idCard) + "");
        logger.info(IdCardUtils.validateCard(idCard) + "");
    }
}
