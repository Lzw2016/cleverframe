package format;

import org.apache.commons.io.FileUtils;
import org.cleverframe.generator.format.XmlFormatUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 17:07 <br/>
 */
public class XmlFormatUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(XmlFormatUtilsTest.class);

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
    public void testXmlFormatByDom4j() throws IOException {
        String xml = FileUtils.readFileToString(new File("E:\\Source\\cleverframe\\src\\main\\resources\\spring\\spring-context-base.xml"));
        xml = XmlFormatUtils.xmlFormatByDom4j(xml, null);
        logger.info(xml);
    }

    @Test
    public void testXmlFormatByXmlApis() throws IOException {
        String xml = FileUtils.readFileToString(new File("E:\\Source\\cleverframe\\src\\main\\resources\\spring\\spring-context-base.xml"));
        xml = XmlFormatUtils.xmlFormatByXmlApis(xml);
        logger.info(xml);
    }
}
