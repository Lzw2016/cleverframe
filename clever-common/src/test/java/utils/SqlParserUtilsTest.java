package utils;

import org.cleverframe.common.utils.SqlParserUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 21:18 <br/>
 */
public class SqlParserUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(SqlParserUtilsTest.class);

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
        String sql = "SELECT ID, MERCHANT_CODE, MEMO, CREATED_AT FROM t_merchant_relation a where a.MERCHANT_CODE = '121'";
        String countSql = SqlParserUtils.getSmartCountSql(sql);
        logger.info(countSql);

        countSql = SqlParserUtils.getSimpleCountSql(sql);
        logger.info(countSql);

        logger.info(SqlParserUtils.getCountSqlCache());
    }
}
