package format;

import org.apache.commons.io.FileUtils;
import org.cleverframe.generator.format.JavaFormatUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 10:57 <br/>
 */
public class JavaFormatUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(JavaFormatUtilsTest.class);

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
    public void testJavaFormatByJalopy() throws IOException {
        String javaCode = FileUtils.readFileToString(new File("E:\\Source\\cleverframe\\clever-generator\\src\\main\\java\\org\\cleverframe\\generator\\format\\JavaFormatUtils.java"));
        javaCode = JavaFormatUtils.javaFormatByJalopy(javaCode);
        logger.info(javaCode);
    }

    @Test
    public void testJavaFormatByJDT() throws IOException {
        String javaCode = FileUtils.readFileToString(new File("E:\\Source\\cleverframe\\clever-generator\\src\\main\\java\\org\\cleverframe\\generator\\format\\JavaFormatUtils.java"));
        javaCode = JavaFormatUtils.javaFormatByJDT(javaCode);
        logger.info(javaCode);
    }
}
