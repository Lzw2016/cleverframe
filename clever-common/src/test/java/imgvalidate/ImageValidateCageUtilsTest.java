package imgvalidate;

import org.apache.commons.io.FileUtils;
import org.cleverframe.common.imgvalidate.ImageValidateCageUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-6 0:40 <br/>
 */
public class ImageValidateCageUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(ImageValidateCageUtilsTest.class);

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
        OutputStream outputStream = new FileOutputStream("F://1.png");
        ImageValidateCageUtils.createImageStream("asyh", outputStream);
        outputStream.close();

        outputStream = new FileOutputStream("F://2.png");
        String code = ImageValidateCageUtils.createImageStream(outputStream);
        outputStream.close();
        logger.info(code);

        outputStream = new FileOutputStream("F://3.png");
        ImageValidateCageUtils.createImageStream("李志伟", outputStream);
        outputStream.close();

        outputStream = new FileOutputStream("F://4.png");
        ImageValidateCageUtils.createImageStream("lizhiwei", outputStream);
        outputStream.close();
    }

    @Test
    public void testCreateImage() throws Exception {
        byte[] data = ImageValidateCageUtils.createImage();
        FileUtils.writeByteArrayToFile(new File("F://5.png"), data);

        data = ImageValidateCageUtils.createImage("lizjhwbjknkdnfjk");
        FileUtils.writeByteArrayToFile(new File("F://6.png"), data);
    }
}
