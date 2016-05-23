package org.cleverframe.common.javamail;

import org.apache.commons.lang3.StringUtils;

/**
 * 常见邮件服务器地址工具类，根据邮件帐号，获取邮件服务器地址<br/>
 * 如：根据 love520lzw1000000@163.com 得到 smtp.163.com<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-22 18:19 <br/>
 */
public class EmailServerHostUtils {

    /**
     * 根据邮件帐号，获取邮件服务器地址(SMTP服务器地址)
     *
     * @param emailAccount 邮件帐号
     * @return 返回获取邮件服务器地址，失败返回null
     */
    public static String getEmailSmtpHost(String emailAccount) {
        String host = null;
        if (StringUtils.isBlank(emailAccount)) {
            return null;
        }
        if (emailAccount.contains("@gmail.com")) {
            host = "smtp.gmail.com";
        }
        if (emailAccount.contains("@21cn.com")) {
            host = "smtp.21cn.com";
        }
        if (emailAccount.contains("@sina.com")) {
            host = "smtp.sina.com.cn";
        }
        if (emailAccount.contains("@tom.com")) {
            host = "smtp.tom.com";
        }
        if (emailAccount.contains("@163.com")) {
            host = "smtp.163.com";
        }
        if (emailAccount.contains("@263.net")) {
            host = "smtp.263.net";
        }
        if (emailAccount.contains("@x263.net")) {
            host = "smtp.x263.net";
        }
        if (emailAccount.contains("@263.net.cn")) {
            host = "smtp.263.net.cn";
        }
        if (emailAccount.contains("@elong.com")) {
            host = "smtp.elong.com";
        }
        if (emailAccount.contains("@china.com")) {
            host = "smtp.china.com";
        }
        if (emailAccount.contains("@sohu.com")) {
            host = "smtp.sohu.com";
        }
        if (emailAccount.contains("@etang.com")) {
            host = "smtp.etang.com";
        }
        if (emailAccount.contains("@yahoo.com")) {
            host = "smtp.mail.yahoo.com";
        }
        if (emailAccount.contains("@yahoo.com.cn")) {
            host = "smtp.mail.yahoo.com.cn";
        }
        return host;
    }

}
