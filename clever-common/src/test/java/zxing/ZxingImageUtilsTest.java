package zxing;

import com.google.zxing.BarcodeFormat;
import org.apache.commons.io.FileUtils;
import org.cleverframe.common.zxing.ZxingCreateImageUtils;
import org.cleverframe.common.zxing.ZxingReaderImageUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-6 23:42 <br/>
 */
public class ZxingImageUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(ZxingImageUtilsTest.class);

    /**
     * 所有测试开始之前运行
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    /**
     * 所有测试结束之后运行
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    /**
     * 每一个测试方法之前运行
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * 每一个测试方法之后运行
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateImageStream() throws Exception {
        String contents = "http://www.jd.com/?cu=true&utm_source=vip.baidu.com&utm_medium=tuiguang&utm_campaign=t_298046589_&utm_term=90ceeea6fe864ab383cc223d57f33b80";
        OutputStream outputStream = new FileOutputStream("F://1.png");
        ZxingCreateImageUtils.createImageStream(contents, BarcodeFormat.QR_CODE, outputStream);
        outputStream.close();

        outputStream = new FileOutputStream("F://2.png");
        contents = "测试二维码测试二维码测试二维码测试二维码" + contents;
        ZxingCreateImageUtils.createImageStream(contents, BarcodeFormat.QR_CODE, 500, 500, outputStream);
        outputStream.close();
    }

    @Test
    public void testCreateImage() throws Exception {
        byte[] data = ZxingCreateImageUtils.createImage("李志伟", BarcodeFormat.PDF_417);
        FileUtils.writeByteArrayToFile(new File("F://3.png"), data);

        String contents = "http://www.jd.com/?cu=true&utm_source=vip.baidu.com&utm_medium=tuiguang&utm_campaign=t_298046589_&utm_term=90ceeea6fe864ab383cc223d57f33b80";
        contents = contents + contents + contents + contents + contents + contents + contents + contents + contents;
        data = ZxingCreateImageUtils.createImage(contents, BarcodeFormat.PDF_417, 800, 300);
        FileUtils.writeByteArrayToFile(new File("F://4.png"), data);
    }

    @Test
    public void testReaderImage() {
        String contents = ZxingReaderImageUtils.readerImage("F://downLocal.jpg");
        logger.info(contents);

        contents = ZxingReaderImageUtils.readerImage("F://2.png");
        logger.info(contents);

        contents = ZxingReaderImageUtils.readerImage("F://3.png");
        logger.info(contents);

        contents = ZxingReaderImageUtils.readerImage("F://4.png");
        logger.info(contents);

        contents = ZxingReaderImageUtils.readerImageByUri("http://img3.douban.com/view/note/large/public/p28476002.jpg");
        logger.info(contents);

        contents = ZxingReaderImageUtils.readerImageByUri("http://www.fjboxin.com/upload/images/20887.jpg");
        logger.info(contents);

        contents = ZxingReaderImageUtils.readerImageByUri("http://b.hiphotos.baidu.com/zhidao/pic/item/b90e7bec54e736d17e0189919b504fc2d562691c.jpg");
        logger.info(contents);
    }
}
