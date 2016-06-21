package format;

import com.alibaba.druid.util.JdbcUtils;
import org.cleverframe.generator.format.SqlFormatUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 16:49 <br/>
 */
public class SqlFormatUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(SqlFormatUtilsTest.class);

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
    public void testSqlFormatByHibernate() {
        String sql = "select\n" +
                     "\t*\n" +
                     "from \n" +
                     "\tcore_mdict\n" +
                     "where (del_flag=:delFlag or :delFlag='' or :delFlag is null)\n" +
                     "\tand (id=:id or :id='' or :id is null)\n" +
                     "\tand (uuid=:uuid or :uuid='' or :uuid is null)\n" +
                     "\tand (mdict_key=:mdictKey or :mdictKey='' or :mdictKey is null)\n" +
                     "\tand (mdict_type=:mdictType or :mdictType='' or :mdictType is null)";
        sql = SqlFormatUtils.sqlFormatByHibernate(sql);
        logger.info(sql);
    }

    @Test
    public void testSqlFormatByDruid() {
        String sql = "select\n" +
                "\t*\n" +
                "from \n" +
                "\tcore_mdict\n" +
                "where (del_flag=:delFlag or :delFlag='' or :delFlag is null)\n" +
                "\tand (id=:id or :id='' or :id is null)\n" +
                "\tand (uuid=:uuid or :uuid='' or :uuid is null)\n" +
                "\tand (mdict_key=:mdictKey or :mdictKey='' or :mdictKey is null)\n" +
                "\tand (mdict_type=:mdictType or :mdictType='' or :mdictType is null)";
        sql = SqlFormatUtils.sqlFormatByDruid(sql, JdbcUtils.MYSQL);
        logger.info(sql);
    }
}
