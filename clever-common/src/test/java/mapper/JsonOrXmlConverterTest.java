package mapper;

import org.cleverframe.common.mapper.JsonOrXmlConverter;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-1 18:32 <br/>
 */
public class JsonOrXmlConverterTest {
    private final static Logger logger = LoggerFactory.getLogger(JsonOrXmlConverterTest.class);

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

    /**
     * Json字符串转换成XML字符串
     */
    @Test
    public void testJsonToXml() {
        String json = "[ {\"model.TestModel\":{\"birthday\":\"1993-04-29 15:41:16.76 UTC\",\"sex\":false,\"name\":\"李志伟11111111\"}}, " +
                "{\"model.TestModel\":{\"birthday\":\"1993-04-29 15:41:16.76 UTC\",\"sex\":false,\"name\":\"李志伟11111111\"}} ]";
        String xml = JsonOrXmlConverter.jsonToXml(json);
        logger.info(xml);
    }

    /**
     * XML字符串转换成Json字符串
     */
    @Test
    public void testXmlToJson() {
        String xml =
                "<model.TestModel>\n" +
                        "  <name>李志伟11111111</name>\n" +
                        "  <sex>false</sex>\n" +
                        "  <birthday>1993-04-29 15:41:16.76 UTC</birthday>\n" +
                        "</model.TestModel>";
        String json = JsonOrXmlConverter.xmlToJson(xml);
        logger.info(json);
    }
}
