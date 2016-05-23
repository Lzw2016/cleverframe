package org.cleverframe.common.javamail;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.*;
import org.cleverframe.common.configuration.BaseConfigNames;
import org.cleverframe.common.configuration.IConfig;
import org.cleverframe.common.exception.ExceptionUtils;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 发送邮件工具类，使用Apache commons-email实现<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-21 17:48 <br/>
 */
public class ApacheSendMailUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(ApacheSendMailUtils.class);
    /**
     * 邮件编码格式
     */
    private static final String defaultEncoding = "UTF-8";

    /**
     * 发送邮件默认的账户
     */
    private static final String DEFAULT_ACCOUNT;

    /**
     * 发送邮件默认账户的密码
     */
    private static final String DEFAULT_ACCOUNT_PASSWORD;

    /**
     * 发送邮件默认账户的邮箱服务器地址
     */
    private static final String DEFAULT_ACCOUNT_HOST;

    static {
        // 测试使用
//        DEFAULT_ACCOUNT = "love520lzw1000000@163.com";
//        DEFAULT_ACCOUNT_PASSWORD = "li19930611";
//        DEFAULT_ACCOUNT_HOST = "smtp.163.com";

        // 生产环境，读取配置
        IConfig config = SpringContextHolder.getBean(SpringBeanNames.Config);
        assert config != null;
        DEFAULT_ACCOUNT = config.getConfig(BaseConfigNames.JAVA_MAIL_SENDER_USERNAME);
        DEFAULT_ACCOUNT_PASSWORD = config.getConfig(BaseConfigNames.JAVA_MAIL_SENDER_PASSWORD);
        String host = config.getConfig(BaseConfigNames.JAVA_MAIL_SENDER_HOST);
        // 根据邮箱帐号自动获取 Host 属性
        if (StringUtils.isBlank(host)) {
            host = EmailServerHostUtils.getEmailSmtpHost(DEFAULT_ACCOUNT);
            if (StringUtils.isBlank(host)) {
                RuntimeException exception = new RuntimeException("DEFAULT_ACCOUNT_HOST 属性获取失败");
                logger.error(exception.getMessage(), exception);
            }
        }
        DEFAULT_ACCOUNT_HOST = host;
    }

    /**
     * 为邮件信息设置基本的属性值<br/>
     *
     * @param email       邮件信息对象
     * @param fromAccount 发送人的邮箱帐号，不能为空
     * @param fromName    发送人的名称，可以为空
     * @param password    发送人的邮箱密码，不能为空
     * @param to          设置收件人，不能为空
     * @param subject     设置邮件主题，不能为空
     * @param text        设置邮件内容，可以为空
     * @param cc          设置抄送人，可以为空
     * @param bcc         设置密送人，可以为空
     * @param replyTo     设置邮件回复人，可以为空
     * @param sentDate    设置发送时间，可以为空
     * @return 返回传入的邮件信息对象
     */
    private static Email emailValueBind(Email email,
                                        String fromAccount,
                                        String fromName,
                                        String password,
                                        String[] to,
                                        String subject,
                                        String text,
                                        String[] cc,
                                        String[] bcc,
                                        String replyTo,
                                        Date sentDate) {
        try {
            // 设置邮件编码
            email.setCharset(defaultEncoding);
            // 设置发件人邮箱密码
            email.setAuthentication(fromAccount, password);
            // 设置发件人邮箱服务器地址
            email.setHostName(DEFAULT_ACCOUNT_HOST);
            // 设置发件人
            if (StringUtils.isBlank(fromName)) {
                fromName = fromAccount;
            }
            email.setFrom(fromAccount, fromName, defaultEncoding);
            // 设置收件人
            email.addTo(to);
            // 设置邮件主题
            email.setSubject(subject);
            // 设置邮件内容
            if (null != text) {
                email.setMsg(text);
            }
            // 设置抄送人
            if (ArrayUtils.isNotEmpty(cc)) {
                email.addCc(cc);
            }
            // 设置密送人
            if (ArrayUtils.isNotEmpty(bcc)) {
                email.addBcc(bcc);
            }
            // 设置邮件回复人
            if (StringUtils.isNotBlank(replyTo)) {
                email.addReplyTo(replyTo);
            }
            // 设置发送时间
            if (null != sentDate) {
                email.setSentDate(sentDate);
            }
        } catch (Throwable e) {
            logger.error("emailValueBind-为邮件信息赋值失败", e);
            throw ExceptionUtils.unchecked(e);
        }
        return email;
    }

    /**
     * 发送简单类型的邮件，基本文本格式邮件<br/>
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
        SimpleEmail simpleEmail = new SimpleEmail();
        emailValueBind(simpleEmail, DEFAULT_ACCOUNT, DEFAULT_ACCOUNT, DEFAULT_ACCOUNT_PASSWORD, to, subject, text, cc, bcc, replyTo, sentDate);
        String result;
        try {
            result = simpleEmail.send();
        } catch (Throwable e) {
            logger.error("sendSimpleEmail-邮件发送失败", e);
            return false;
        }
        logger.debug("sendSimpleEmail-邮件发送成功，返回值：[{}]", result);
        return true;
    }

    /**
     * 发送简单类型的邮件，基本文本格式邮件<br/>
     *
     * @param to      设置收件人，不能为空
     * @param subject 设置邮件主题，不能为空
     * @param text    设置邮件内容，不能为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendSimpleEmail(String[] to, String subject, String text) {
        return sendSimpleEmail(to, subject, text, null, null, null, null);
    }

    /**
     * 发送简单类型的邮件，基本文本格式邮件<br/>
     *
     * @param to      设置收件人，不能为空
     * @param subject 设置邮件主题，不能为空
     * @param text    设置邮件内容，不能为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendSimpleEmail(String to, String subject, String text) {
        SimpleEmail simpleEmail = new SimpleEmail();
        emailValueBind(simpleEmail, DEFAULT_ACCOUNT, DEFAULT_ACCOUNT, DEFAULT_ACCOUNT_PASSWORD, null, subject, text, null, null, null, null);
        String result;
        try {
            // 单独设置收件人
            simpleEmail.addTo(to);
            result = simpleEmail.send();
        } catch (Throwable e) {
            logger.error("sendSimpleEmail-邮件发送失败", e);
            return false;
        }
        logger.debug("sendSimpleEmail-邮件发送成功，返回值：[{}]", result);
        return true;
    }


    /**
     * 发送文本格式，带附件邮件<br/>
     *
     * @param to             设置收件人，不能为空
     * @param subject        设置邮件主题，不能为空
     * @param text           设置邮件内容，不能为空
     * @param attachmentList 附件集合，可以为空
     * @param cc             设置抄送人，可以为空
     * @param bcc            设置密送人，可以为空
     * @param replyTo        设置邮件回复人，可以为空
     * @param sentDate       设置发送时间，可以为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendMultiPartEmail(String[] to, String subject, String text, List<EmailAttachment> attachmentList, String[] cc, String[] bcc, String replyTo, Date sentDate) {
        MultiPartEmail multiPartEmail = new MultiPartEmail();
        emailValueBind(multiPartEmail, DEFAULT_ACCOUNT, DEFAULT_ACCOUNT, DEFAULT_ACCOUNT_PASSWORD, to, subject, text, cc, bcc, replyTo, sentDate);
        String result;
        try {
            // 增加附件
            if (attachmentList != null && attachmentList.size() > 0) {
                for (EmailAttachment emailAttachment : attachmentList) {
                    multiPartEmail.attach(emailAttachment);
                }
            }
            result = multiPartEmail.send();
        } catch (Throwable e) {
            logger.error("sendMultiPartEmail-邮件发送失败", e);
            return false;
        }
        logger.debug("sendMultiPartEmail-邮件发送成功，返回值：[{}]", result);
        return true;
    }

    /**
     * 发送文本格式，带附件邮件<br/>
     *
     * @param to             设置收件人，不能为空
     * @param subject        设置邮件主题，不能为空
     * @param text           设置邮件内容，不能为空
     * @param attachmentList 附件集合，可以为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendMultiPartEmail(String[] to, String subject, String text, List<EmailAttachment> attachmentList) {
        return sendMultiPartEmail(to, subject, text, attachmentList, null, null, null, null);
    }

    /**
     * 发送文本格式，带附件邮件<br/>
     *
     * @param to          设置收件人，不能为空
     * @param subject     设置邮件主题，不能为空
     * @param text        设置邮件内容，不能为空
     * @param attachments 附件集合，可以为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendMultiPartEmail(String[] to, String subject, String text, EmailAttachment... attachments) {
        return sendMultiPartEmail(to, subject, text, Arrays.asList(attachments), null, null, null, null);
    }

    /**
     * 发送HTML格式带附件邮件<br/>
     *
     * @param to             设置收件人，不能为空
     * @param subject        设置邮件主题，不能为空
     * @param htmlText       设置邮件内容，不能为空
     * @param attachmentList 附件集合，可以为空
     * @param cc             设置抄送人，可以为空
     * @param bcc            设置密送人，可以为空
     * @param replyTo        设置邮件回复人，可以为空
     * @param sentDate       设置发送时间，可以为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendHtmlEmail(String[] to, String subject, String htmlText, List<EmailAttachment> attachmentList, String[] cc, String[] bcc, String replyTo, Date sentDate) {
        HtmlEmail htmlEmail = new HtmlEmail();
        emailValueBind(htmlEmail, DEFAULT_ACCOUNT, DEFAULT_ACCOUNT, DEFAULT_ACCOUNT_PASSWORD, to, subject, null, cc, bcc, replyTo, sentDate);
        String result;
        try {
            // 设置html内容
            htmlEmail.setHtmlMsg(htmlText);
            // 增加附件
            if (attachmentList != null && attachmentList.size() > 0) {
                for (EmailAttachment emailAttachment : attachmentList) {
                    htmlEmail.attach(emailAttachment);
                }
            }
            result = htmlEmail.send();
        } catch (Throwable e) {
            logger.error("sendHtmlEmail-邮件发送失败", e);
            return false;
        }
        logger.debug("sendHtmlEmail-邮件发送成功，返回值：[{}]", result);
        return true;
    }

    /**
     * 发送HTML格式带附件邮件<br/>
     *
     * @param to             设置收件人，不能为空
     * @param subject        设置邮件主题，不能为空
     * @param htmlText       设置邮件内容，不能为空
     * @param attachmentList 附件集合，可以为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendHtmlEmail(String[] to, String subject, String htmlText, List<EmailAttachment> attachmentList) {
        return sendHtmlEmail(to, subject, htmlText, attachmentList, null, null, null, null);
    }


    /**
     * 发送HTML格式带附件邮件<br/>
     *
     * @param to          设置收件人，不能为空
     * @param subject     设置邮件主题，不能为空
     * @param htmlText    设置邮件内容，不能为空
     * @param attachments 附件集合，可以为空
     * @return 发送成功返回true, 失败返回false
     */
    public static boolean sendHtmlEmail(String[] to, String subject, String htmlText, EmailAttachment... attachments) {
        return sendHtmlEmail(to, subject, htmlText, Arrays.asList(attachments), null, null, null, null);
    }
}
