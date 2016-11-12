package org.cleverframe.common.configuration;

/**
 * 系统基本配置<br/>
 * cleverframe-base.properties  文件里的配置在此类都需要定义相应的字符串常量<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 0:10 <br/>
 *
 * @see BaseConfigNames
 */
public class BaseConfigValues {
    // -------------------------------------------------------------------------------------------//
    // 系统基本配置
    // -------------------------------------------------------------------------------------------//
    /**
     * 静态资源基路径
     */
    public final static String STATIC_PATH = "static";

    /**
     * MVC框架的请求映射基路径
     */
    public final static String MVC_PATH = "mvc";


    /**
     * 系统视图文件(jsp、html、ftl等)对应的css、js静态文件基路径
     */
    public final static String MODULES_PATH = "modules";

    /**
     * 系统文档基路径
     */
    public final static String DOC_PATH = "modules/doc";

    /**
     * JSP视图文件存放路径，以及Spring JSP视图的配置
     */
    public final static String JSP_VIEW_PREFIX = "/WEB-INF/modules/";

    /**
     * JSP文件后缀配置
     */
    public final static String JSP_VIEW_SUFFIX = ".jsp";

    /**
     * 分页查询的每页的数据条数，“-1”表示不分页
     */
    public final static String PAGE_PAGESIZE = "10";

    /**
     * 系统发送邮件的帐号
     */
    public final static String JAVA_MAIL_SENDER_USERNAME = "love520lzw1000000@163.com";

    /**
     * 系统发送邮件帐号的密码
     */
    public final static String JAVA_MAIL_SENDER_PASSWORD = "li19930611";

    /**
     * 系统发送邮件帐号的邮件服务器地址，可以为空
     */
    public final static String JAVA_MAIL_SENDER_HOST = "smtp.163.com";
}
