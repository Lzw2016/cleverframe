package javamail;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.EmailAttachment;
import org.cleverframe.common.javamail.ApacheSendMailUtils;
import org.cleverframe.common.time.DateTimeUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-22 16:54 <br/>
 */
public class ApacheSendMailUtilsTest {
    private final static Logger logger = LoggerFactory.getLogger(ApacheSendMailUtilsTest.class);

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

    @SuppressWarnings("UnusedAssignment")
    @Test
    public void testSendSimpleEmail() throws IOException {
        boolean flag = false;

        flag = ApacheSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com"}, "测试", "内容1", null, null, null, null);
        logger.info(flag + "");

        flag = ApacheSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com"}, "测试", "内容2", new String[]{"lzw1000000@163.com"}, null, null, null);
        logger.info(flag + "");

        flag = ApacheSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com"}, "测试", "内容3", null, new String[]{"lzw1000000@163.com"}, null, null);
        logger.info(flag + "");

        flag = ApacheSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com", "lzw1000000@163.com"}, "测试", "内容4", null, null, "lzw1000000@163.com", null);
        logger.info(flag + "");

        flag = ApacheSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com"}, "测试", "内容5", null, null, null, DateTimeUtils.parseDate("2015-02-02 11:11:12"));
        logger.info(flag + "");

        String string = FileUtils.readFileToString(new File("E:\\Source\\HBuilderProject\\My97DatePicker\\index.html"));
        flag = ApacheSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com"}, "测试", string, null, null, null, null);
        logger.info(flag + "");
    }

    @SuppressWarnings("UnusedAssignment")
    @Test
    public void testSendMultiPartEmail() throws MalformedURLException {
        List<EmailAttachment> emailAttachmentList = new ArrayList<>();

        EmailAttachment emailAttachment = new EmailAttachment();
        // 设置附件资源
        emailAttachment.setURL(new URL("http://central.maven.org/maven2/com/alibaba/fastjson/1.2.11/fastjson-1.2.11.jar"));
        // 设置是附件，还是内嵌资源(inline)
        emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
        // 附件的说明
        emailAttachment.setDescription("URL 附件");
        // 增加附件
        emailAttachmentList.add(emailAttachment);

        emailAttachment = new EmailAttachment();
        // 设置附件资源
        emailAttachment.setPath("E:\\助学贷款结清凭证1.doc");
        // 设置是附件，还是内嵌资源(inline)
        emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
        // 附件的说明
        emailAttachment.setDescription("本地 附件");
        // 增加附件
        emailAttachmentList.add(emailAttachment);

        boolean flag = false;
        flag = ApacheSendMailUtils.sendMultiPartEmail(new String[]{"1183409807@qq.com"}, "测试", "内容1", emailAttachmentList, null, null, null, null);
        logger.info(flag + "");
    }

    @SuppressWarnings("UnusedAssignment")
    @Test
    public void testSendHtmlEmail() throws IOException {
        List<EmailAttachment> emailAttachmentList = new ArrayList<>();

        EmailAttachment emailAttachment = new EmailAttachment();
        // 设置附件资源
        emailAttachment.setURL(new URL("http://central.maven.org/maven2/com/alibaba/fastjson/1.2.11/fastjson-1.2.11.jar"));
        // 设置是附件，还是内嵌资源(inline)
        emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
        // 附件的说明
        emailAttachment.setDescription("URL 附件");
        // 增加附件
        emailAttachmentList.add(emailAttachment);

        emailAttachment = new EmailAttachment();
        // 设置附件资源
        emailAttachment.setPath("E:\\助学贷款结清凭证1.doc");
        // 设置是附件，还是内嵌资源(inline)
        emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
        // 附件的说明
        emailAttachment.setDescription("本地 附件");
        // 增加附件
        emailAttachmentList.add(emailAttachment);

        boolean flag = false;

        String html = FileUtils.readFileToString(new File("E:\\Source\\HBuilderProject\\My97DatePicker\\index.html"));
        flag = ApacheSendMailUtils.sendHtmlEmail(new String[]{"1183409807@qq.com"}, "测试", html, emailAttachmentList, null, null, null, null);
        logger.info(flag + "");
    }
}
