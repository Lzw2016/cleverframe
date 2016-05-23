package imgvalidate;

import org.apache.commons.io.FileUtils;
import org.cleverframe.common.imgvalidate.ImageValidatePatchcaUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-5 23:01 <br/>
 */
public class ImageValidatePatchcaUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(ImageValidatePatchcaUtilsTest.class);

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
    public void testCreateImageStream() throws Exception {
        for (int i = 1; i < 10; i++) {
            OutputStream outputStream = new FileOutputStream("F://" + i + ".png");
            String code = ImageValidatePatchcaUtils.createImageStream(outputStream);
            outputStream.close();
            logger.info(i + " " + code);
        }
    }

    @Test
    public void testCreateImage() throws Exception {
        for (int i = 1; i < 10; i++) {
            byte[] data = ImageValidatePatchcaUtils.createImage();
            FileUtils.writeByteArrayToFile(new File("F://" + i + ".png"), data);
        }
    }
}
