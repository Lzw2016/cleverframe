package org.cleverframe.common.configuration;

/**
 * 系统基本配置<br/>
 * cleverframe-base.properties 配置文件里的配置键名称在此类都需要定义相应的字符串常量<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 0:07 <br/>
 *
 * @see BaseConfigValues
 */
public class BaseConfigNames {
    // -------------------------------------------------------------------------------------------//
    // 系统基本配置
    // -------------------------------------------------------------------------------------------//
    /**
     * 静态资源基路径
     */
    public final static String STATIC_PATH = "base.staticPath";

    /**
     * MVC框架的请求映射基路径
     */
    public final static String MVC_PATH = "base.mvcPath";


    /**
     * 系统视图文件(jsp、html、ftl等)对应的css、js静态文件基路径
     */
    public final static String MODULES_PATH = "base.modulesPath";

    /**
     * 系统文档基路径
     */
    public final static String DOC_PATH = "base.docPath";

    /**
     * JSP视图文件存放路径，以及Spring JSP视图的配置
     */
    public final static String JSP_VIEW_PREFIX = "base.jsp.view.prefix";

    /**
     * JSP文件后缀配置
     */
    public final static String JSP_VIEW_SUFFIX = "base.web.view.suffix";

    /**
     * 分页查询的每页的数据条数，“-1”表示不分页
     */
    public final static String PAGE_PAGESIZE = "base.page.pageSize";

    /**
     * 系统发送邮件的帐号
     */
    public final static String JAVA_MAIL_SENDER_USERNAME = "base.JavaMailSender.username";

    /**
     * 系统发送邮件帐号的密码
     */
    public final static String JAVA_MAIL_SENDER_PASSWORD = "base.JavaMailSender.password";

    /**
     * 系统发送邮件帐号的邮件服务器地址，可以为空
     */
    public final static String JAVA_MAIL_SENDER_HOST = "base.JavaMailSender.host";
}
