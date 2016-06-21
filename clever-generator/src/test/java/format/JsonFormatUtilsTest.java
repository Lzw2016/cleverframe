package format;

import org.cleverframe.common.net.HttpUtils;
import org.cleverframe.generator.format.JsonFormatUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 16:32 <br/>
 */
public class JsonFormatUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(JsonFormatUtilsTest.class);

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
    public void testJsonFormatByGson() {
        String json = HttpUtils.httpGetResultStr("http://dc.3.cn/category/get", null, "GBK");
        json = JsonFormatUtils.jsonFormatByGson(json);
        logger.info(json);
    }

    @Test
    public void testJsonFormatByFastJson() {
        String json = HttpUtils.httpGetResultStr("http://dc.3.cn/category/get", null, "GBK");
        json = JsonFormatUtils.jsonFormatByFastJson(json);
        logger.info(json);
    }
}
