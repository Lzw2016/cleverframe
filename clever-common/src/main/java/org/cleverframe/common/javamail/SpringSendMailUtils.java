package org.cleverframe.common.javamail;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.exception.ExceptionUtils;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 发送邮件工具类，使用Spring实现<br/>
 * 根据调用频率重载sendSimpleEmail和sendMimeMessage方法<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-21 17:34 <br/>
 */
public class SpringSendMailUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(SpringSendMailUtils.class);

    /**
     * Spring 发送邮件类
     */
    private static final JavaMailSender JAVA_MAIL_SENDER;

    /**
     * Spring配置的邮件发送帐号
     */
    private static final String FROM_EMAIL_ACCOUNT;

//    // 测试使用代码
//    static {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
//        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.timeout", "20000");
//        javaMailSender.setHost("smtp.163.com");
//        // javaMailSender.setPort(3306);
//        javaMailSender.setDefaultEncoding("UtF-8");
//        javaMailSender.setUsername("love520lzw1000000@163.com");
//        javaMailSender.setPassword("li19930611");
//
//        JAVA_MAIL_SENDER = javaMailSender;
//        FROM_EMAIL_ACCOUNT = javaMailSender.getUsername();
//    }

    // 生产环境使用
    static {
        JAVA_MAIL_SENDER = SpringContextHolder.getBean(SpringBeanNames.JavaMailSender);
        if (JAVA_MAIL_SENDER instanceof JavaMailSenderImpl) {
            JavaMailSenderImpl javaMailSender = ((JavaMailSenderImpl) JAVA_MAIL_SENDER);
            FROM_EMAIL_ACCOUNT = javaMailSender.getUsername();
            // 根据邮箱帐号自动获取 Host 属性
            if (StringUtils.isBlank(javaMailSender.getHost())) {
                String host = EmailServerHostUtils.getEmailSmtpHost(FROM_EMAIL_ACCOUNT);
                if (StringUtils.isNotBlank(host)) {
                    javaMailSender.setHost(host);
                } else {
                    RuntimeException exception = new RuntimeException("JavaMailSenderImpl的Host属性获取失败");
                    logger.error(exception.getMessage(), exception);
                }
            }
        } else {
            FROM_EMAIL_ACCOUNT = "";
            RuntimeException exception = new RuntimeException("Spring发送邮件Bean配置错误，请使用JavaMailSenderImpl实现类");
            logger.error(exception.getMessage(), exception);
        }
    }

    /**
     * 返回Spring发送邮件类<br/>
     * <b>注意：不能修改此对象的属性</b>
     *
     * @return 返回Spring发送邮件类
     */
    public static JavaMailSender getJavaMailSender() {
        return JAVA_MAIL_SENDER;
    }

    /**
     * @return 返回Spring配置的邮件发送帐号
     */
    public static String getFromEmailAccount() {
        return FROM_EMAIL_ACCOUNT;
    }

    /**
     * new一个简单的邮件信息，不支持html，不支持附件和图片<br/>
     *
     * @param to       设置收件人，不能为空
     * @param subject  设置邮件主题，不能为空
     * @param text     设置邮件内容，不能为空
     * @param cc       设置抄送人，可以为空
     * @param bcc      设置密送人，可以为空
     * @param replyTo  设置邮件回复人，可以为空
     * @param sentDate 设置发送时间，可以为空
     * @return 返回一个新的 SimpleMailMessage 对象
     */
    private static SimpleMailMessage newSimpleMailMessage(String[] to, String subject, String text, String[] cc, String[] bcc, String replyTo, Date sentDate) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置发件人
        simpleMailMessage.setFrom(FROM_EMAIL_ACCOUNT);
        // 设置收件人
        simpleMailMessage.setTo(to);
        // 设置邮件主题
        simpleMailMessage.setSubject(subject);
        // 设置邮件内容
        simpleMailMessage.setText(text);

        // 设置抄送人
        if (ArrayUtils.isNotEmpty(cc)) {
            simpleMailMessage.setCc(cc);
        }
        // 设置密送人
        if (ArrayUtils.isNotEmpty(bcc)) {
            simpleMailMessage.setBcc(bcc);
        }
        // 设置邮件回复人
        if (StringUtils.isNotBlank(replyTo)) {
            simpleMailMessage.setReplyTo(replyTo);
        }
        // 设置发送时间
        if (null != sentDate) {
            simpleMailMessage.setSentDate(sentDate);
        }
        return simpleMailMessage;
    }


    /**
     * 发送一个简单的邮件，不支持html，不支持附件和图片<br/>
     *
     * @param to       设置收件人，不能为空
     * @param subject  设置邮件主题，不能为空
     * @param text     设置邮件内容，不能为空
     * @param cc       设置抄送人，可以为空
     * @param bcc      设置密送人，可以为空
     * @param replyTo  设置邮件回复人，可以为空
     * @param sentDate 设置发送时间，可以为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendSimpleEmail(String[] to, String subject, String text, String[] cc, String[] bcc, String replyTo, Date sentDate) {
        SimpleMailMessage simpleMailMessage = newSimpleMailMessage(to, subject, text, cc, bcc, replyTo, sentDate);
        try {
            JAVA_MAIL_SENDER.send(simpleMailMessage);
        } catch (Throwable e) {
            logger.error("sendSimpleEmail-发送邮件失败", e);
            return false;
        }
        return true;
    }

    /**
     * 发送一个简单的邮件，不支持html，不支持附件和图片<br/>
     *
     * @param to      设置收件人，不能为空
     * @param subject 设置邮件主题，不能为空
     * @param text    设置邮件内容，不能为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendSimpleEmail(String[] to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = newSimpleMailMessage(to, subject, text, null, null, null, null);
        try {
            JAVA_MAIL_SENDER.send(simpleMailMessage);
        } catch (Throwable e) {
            logger.error("sendSimpleEmail-发送邮件失败", e);
            return false;
        }
        return true;
    }

    /**
     * 发送一个简单的邮件，不支持html，不支持附件和图片<br/>
     *
     * @param to      设置收件人，不能为空
     * @param subject 设置邮件主题，不能为空
     * @param text    设置邮件内容，不能为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = newSimpleMailMessage(null, subject, text, null, null, null, null);
        simpleMailMessage.setTo(to);
        try {
            JAVA_MAIL_SENDER.send(simpleMailMessage);
        } catch (Throwable e) {
            logger.error("sendSimpleEmail-发送邮件失败", e);
            return false;
        }
        return true;
    }

    /**
     * new一个复杂的邮件信息，支持html，支持附件和图片<br/>
     * 如果邮件中需要显示内联的图片，html格式：&lt;img src="cid:inlineImgKey" width="214" height="46" &frasl;&gt;<br/>
     * 再设置inlineMap参数，inlineMap.put("inlineImgKey", dataSource)<br/>
     *
     * @param to            设置收件人，不能为空
     * @param subject       设置邮件主题，不能为空
     * @param text          设置邮件内容(支持html)，不能为空
     * @param inlineMap     设置内联资源(邮件html中的图片)，格式，，可以为空
     * @param attachmentMap 设置附件，可以为空
     * @param cc            设置抄送人，可以为空
     * @param bcc           设置密送人，可以为空
     * @param replyTo       设置邮件回复人，可以为空
     * @param sentDate      设置发送时间，可以为空
     * @return 返回一个新的 MimeMessage 对象
     */
    private static MimeMessage newMimeMessage(String to,
                                              String subject,
                                              String text,
                                              Map<String, DataSource> inlineMap,
                                              Map<String, DataSource> attachmentMap,
                                              String[] cc,
                                              String[] bcc,
                                              String replyTo,
                                              Date sentDate) {
        MimeMessage mimeMessage = JAVA_MAIL_SENDER.createMimeMessage();
        try {
            //创建MimeMessageHelper对象，处理MimeMessage的辅助类
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 设置发件人
            mimeMessageHelper.setFrom(FROM_EMAIL_ACCOUNT);
            // 设置收件人
            mimeMessageHelper.setTo(to);
            // 设置邮件主题
            mimeMessageHelper.setSubject(subject);
            // 设置邮件内容，支持html
            mimeMessageHelper.setText(text, true);

            // 设置抄送人
            if (ArrayUtils.isNotEmpty(cc)) {
                mimeMessageHelper.setCc(cc);
            }
            // 设置密送人
            if (ArrayUtils.isNotEmpty(bcc)) {
                mimeMessageHelper.setBcc(bcc);
            }
            // 设置邮件回复人
            if (StringUtils.isNotBlank(replyTo)) {
                mimeMessageHelper.setReplyTo(replyTo);
            }
            // 设置发送时间
            if (null != sentDate) {
                mimeMessageHelper.setSentDate(sentDate);
            }

            // 增加内联资源，如：邮件html中的图片
            if (inlineMap != null) {
                Set<String> inlineSetKey = inlineMap.keySet();
                for (String key : inlineSetKey) {
                    mimeMessageHelper.addInline(key, inlineMap.get(key));
                }
            }
            // 增加附件
            if (attachmentMap != null) {
                Set<String> attachmentSetKey = attachmentMap.keySet();
                for (String key : attachmentSetKey) {
                    mimeMessageHelper.addAttachment(key, attachmentMap.get(key));
                }
            }
        } catch (MessagingException e) {
            logger.error("newMimeMessage-创建复杂的邮件信息失败", e);
            throw ExceptionUtils.unchecked(e);
        }
        return mimeMessage;
    }

    /**
     * 发送一个复杂的邮件，支持html，支持附件和图片<br/>
     * 如果邮件中需要显示内联的图片，html格式：&lt;img src="cid:inlineImgKey" width="214" height="46" &frasl;&gt;<br/>
     * 再设置inlineMap参数，inlineMap.put("inlineImgKey", dataSource)<br/>
     *
     * @param to            设置收件人，不能为空
     * @param subject       设置邮件主题，不能为空
     * @param text          设置邮件内容(支持html)，不能为空
     * @param inlineMap     设置内联资源(邮件html中的图片)，格式，，可以为空
     * @param attachmentMap 设置附件，可以为空
     * @param cc            设置抄送人，可以为空
     * @param bcc           设置密送人，可以为空
     * @param replyTo       设置邮件回复人，可以为空
     * @param sentDate      设置发送时间，可以为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendMimeMessage(String to,
                                          String subject,
                                          String text,
                                          Map<String, DataSource> inlineMap,
                                          Map<String, DataSource> attachmentMap,
                                          String[] cc,
                                          String[] bcc,
                                          String replyTo,
                                          Date sentDate) {
        MimeMessage mimeMessage = newMimeMessage(to, subject, text, inlineMap, attachmentMap, cc, bcc, replyTo, sentDate);
        try {
            JAVA_MAIL_SENDER.send(mimeMessage);
        } catch (Throwable e) {
            logger.error("sendMimeMessage-发送邮件失败", e);
            return false;
        }
        return true;
    }

    /**
     * 发送一个复杂的邮件，支持html，支持附件和图片<br/>
     * 如果邮件中需要显示内联的图片，html格式：&lt;img src="cid:inlineImgKey" width="214" height="46" &frasl;&gt;<br/>
     * 再设置inlineMap参数，inlineMap.put("inlineImgKey", dataSource)<br/>
     *
     * @param to            设置收件人，不能为空
     * @param subject       设置邮件主题，不能为空
     * @param text          设置邮件内容(支持html)，不能为空
     * @param inlineMap     设置内联资源(邮件html中的图片)，格式，，可以为空
     * @param attachmentMap 设置附件，可以为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendMimeMessage(String to, String subject, String text, Map<String, DataSource> inlineMap, Map<String, DataSource> attachmentMap) {
        MimeMessage mimeMessage = newMimeMessage(to, subject, text, inlineMap, attachmentMap, null, null, null, null);
        try {
            JAVA_MAIL_SENDER.send(mimeMessage);
        } catch (Throwable e) {
            logger.error("sendMimeMessage-发送邮件失败", e);
            return false;
        }
        return true;
    }

    /**
     * 发送一个复杂的邮件，支持html，支持附件和图片<br/>
     * 如果邮件中需要显示内联的图片，html格式：&lt;img src="cid:inlineImgKey" width="214" height="46" &frasl;&gt;<br/>
     * 再设置inlineMap参数，inlineMap.put("inlineImgKey", dataSource)<br/>
     *
     * @param to            设置收件人，不能为空
     * @param subject       设置邮件主题，不能为空
     * @param text          设置邮件内容(支持html)，不能为空
     * @param attachmentMap 设置附件，可以为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendMimeMessage(String to, String subject, String text, Map<String, DataSource> attachmentMap) {
        MimeMessage mimeMessage = newMimeMessage(to, subject, text, null, attachmentMap, null, null, null, null);
        try {
            JAVA_MAIL_SENDER.send(mimeMessage);
        } catch (Throwable e) {
            logger.error("sendMimeMessage-发送邮件失败", e);
            return false;
        }
        return true;
    }

    /**
     * 发送一个复杂的邮件，支持html，支持附件和图片<br/>
     * 如果邮件中需要显示内联的图片，html格式：&lt;img src="cid:inlineImgKey" width="214" height="46" &frasl;&gt;<br/>
     * 再设置inlineMap参数，inlineMap.put("inlineImgKey", dataSource)<br/>
     *
     * @param to      设置收件人，不能为空
     * @param subject 设置邮件主题，不能为空
     * @param text    设置邮件内容(支持html)，不能为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendMimeMessage(String to, String subject, String text) {
        MimeMessage mimeMessage = newMimeMessage(to, subject, text, null, null, null, null, null, null);
        try {
            JAVA_MAIL_SENDER.send(mimeMessage);
        } catch (Throwable e) {
            logger.error("sendMimeMessage-发送邮件失败", e);
            return false;
        }
        return true;
    }
}
