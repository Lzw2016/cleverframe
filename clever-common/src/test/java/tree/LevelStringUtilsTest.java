package tree;

import org.cleverframe.common.tree.LevelStringUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 15:41 <br/>
 */
public class LevelStringUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(LevelStringUtilsTest.class);

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
    public void test() {
        logger.info(LevelStringUtils.isLevelString("02356121654a") + "");
        logger.info(LevelStringUtils.isLevelString("02356121654A") + "");

        logger.info(LevelStringUtils.rootLevelString(1));
        logger.info(LevelStringUtils.rootLevelString(2));
        logger.info(LevelStringUtils.rootLevelString(3));
        logger.info(LevelStringUtils.rootLevelString(4));
        logger.info(LevelStringUtils.rootLevelString(5));

        logger.info(LevelStringUtils.nextLevelString(3, "002001"));
        logger.info(LevelStringUtils.nextLevelString(3, "00200A"));

        logger.info(LevelStringUtils.nextLevelString(2, "0001030F"));
    }

}
