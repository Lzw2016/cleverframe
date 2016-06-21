package format;

import org.cleverframe.common.net.HttpUtils;
import org.cleverframe.generator.format.HtmlFormatUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 10:38 <br/>
 */
public class HtmlFormatUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(HtmlFormatUtilsTest.class);

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
    public void testHtmlFormatByJsoup() {
        String html = HttpUtils.httpGetResultStr("http://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.4");
        html = HtmlFormatUtils.htmlFormatByJsoup(html);
        logger.info(html);
    }
}
