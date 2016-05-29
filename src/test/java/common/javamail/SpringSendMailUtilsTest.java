package common.javamail;

import basetest.ServiceTestBase;
import org.apache.commons.io.FileUtils;
import org.cleverframe.common.javamail.SpringSendMailUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-26 0:25 <br/>
 */
public class SpringSendMailUtilsTest extends ServiceTestBase {

    @SuppressWarnings("UnusedAssignment")
    @Test
    public void testSendSimpleEmail() throws IOException {
        boolean flag = false;

        flag = SpringSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com"}, "测试", "内容1", null, null, null, null);
        logger.info(flag + "");

//        flag = SpringSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com"}, "测试", "内容2", new String[]{"lzw1000000@163.com"}, null, null, null);
//        logger.info(flag + "");
//
//        flag = SpringSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com"}, "测试", "内容3", null, new String[]{"lzw1000000@163.com"}, null, null);
//        logger.info(flag + "");
//
//        flag = SpringSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com", "lzw1000000@163.com"}, "测试", "内容4", null, null, "lzw1000000@163.com", null);
//        logger.info(flag + "");
//
//        flag = SpringSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com"}, "测试", "内容5", null, null, null, DateTimeUtils.parseDate("2015-02-02 11:11:12"));
//        logger.info(flag + "");
//
//        String html = FileUtils.readFileToString(new File("E:\\Source\\HBuilderProject\\My97DatePicker\\index.html"));
//        flag = SpringSendMailUtils.sendSimpleEmail(new String[]{"1183409807@qq.com"}, "测试", html, null, null, null, null);
//        logger.info(flag + "");
    }

    @SuppressWarnings("UnusedAssignment")
    @Test
    public void testSendMimeMessage() throws IOException {
        boolean flag = false;
        String html = FileUtils.readFileToString(new File("E:\\Source\\HBuilderProject\\My97DatePicker\\index.html"));

        flag = SpringSendMailUtils.sendMimeMessage("1183409807@qq.com", "HTML邮件", html);
        logger.info(flag + "");
//
//
//        URLDataSource img = new URLDataSource(new URL("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=145605301,663197096&fm=58"));
//        Map<String, DataSource> inlineMap = new HashMap<>();
//        inlineMap.put("img", img);
//        Map<String, DataSource> attachmentMap = new HashMap<>();
//        attachmentMap.put("附件.html", new FileDataSource("E:\\Source\\HBuilderProject\\My97DatePicker\\index.html"));
//        flag = SpringSendMailUtils.sendMimeMessage("1183409807@qq.com", "HTML邮件", html, inlineMap, attachmentMap);
//        logger.info(flag + "");
    }
}
