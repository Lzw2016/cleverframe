package org.cleverframe.common.configuration;

/**
 * 系统配置的所有配置名称<br/>
 * cleverframe.properties 配置文件里的配置键名称在此类都需要定义相应的字符串常量<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 0:07 <br/>
 *
 * @see BaseConfigValues
 */
@SuppressWarnings("unused")
public class BaseConfigNames {
    // -------------------------------------------------------------------------------------------//
    // 数据库配置
    // -------------------------------------------------------------------------------------------//
    /**
     * 数据库类型，如：mysql、Oracle、h2...
     */
    public final static String JDBC_TYPE = "jdbc.type";

    /**
     * 数据库驱动
     */
    public final static String JDBC_DRIVER = "jdbc.driver";

    /**
     * 数据库地址
     */
    public final static String JDBC_URL = "jdbc.url";

    /**
     * 数据库用户名
     */
    public final static String JDBC_USERNAME = "jdbc.username";

    /**
     * 数据库密码
     */
    public final static String JDBC_PASSWORD = "jdbc.password";

    /**
     * 数据库连接池初始数
     */
    public final static String JDBC_POOL_INIT = "jdbc.pool.init";

    /**
     * 数据库连接池最小大小
     */
    public final static String JDBC_POOL_MINIDLE = "jdbc.pool.minIdle";

    /**
     * 数据库连接池最大大小
     */
    public final static String JDBC_POOL_MAXACTIVE = "jdbc.pool.maxActive";

    /**
     * 数据库测试使用的SQL
     */
    public final static String JDBC_TESTSQL = "jdbc.testSql";


    // -------------------------------------------------------------------------------------------//
    // 框架配置
    // -------------------------------------------------------------------------------------------//
    /**
     * Hibernate是否显示sql语句
     */
    public final static String HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    /**
     * 最大文件上传限制
     */
    public final static String WEB_MAX_UPLOAD_SIZE = "web.maxUploadSize";

    /**
     * 上传文件的存储路径，当storedType=1时使用，（1：当前服务器硬盘；2：FTP服务器；3：；FastDFS服务器）
     */
    public final static String WEB_FILE_STORAGE_PATH = "web.fileStoragePath";

    /**
     * 上传文件到FTP的存储路径
     */
    public final static String WEB_FILE_STORAGE_PATH_BY_FTP = "web.fileStoragePathByFTP";

    /**
     * 存储上传文件的FTP服务器地址
     */
    public final static String FTP_HOST = "FTP.host";

    /**
     * 存储上传文件的FTP服务器端口号
     */
    public final static String FTP_PORT = "FTP.port";

    /**
     * 存储上传文件的FTP服务器用户名
     */
    public final static String FTP_USER_NAME = "FTP.username";

    /**
     * 存储上传文件的FTP服务器用户密码
     */
    public final static String FTP_PASSWORD = "FTP.password";

    /**
     * FastDFS 配置文件地址
     */
    public final static String FASTDFS_CONF_FILE_NAME = "FastDFS.conf.filename";


    // -------------------------------------------------------------------------------------------//
    // 系统配置
    // -------------------------------------------------------------------------------------------//
    /**
     * 系统名称
     */
    public final static String PRODUCTNAME = "productName";

    /**
     * 版权年份
     */
    public final static String COPYRIGHTYEAR = "copyrightYear";

    /**
     * 系统版本号
     */
    public final static String VERSION = "version";

    /**
     * 静态资源基路径
     */
    public final static String STATIC_PATH = "staticPath";

    /**
     * 系统HTML、Jsp、Freemarker等系统页面基路径
     */
    public final static String VIEWS_PATH = "viewsPath";

    /**
     * 系统文档基路径
     */
    public final static String DOC_PATH = "docPath";

    /**
     * MVC框架的请求映射基路径
     */
    public final static String MVC_PATH = "mvcPath";

    /**
     * JSP视图文件存放路径
     */
    public final static String JSP_VIEW_PREFIX = "web.view.prefix";

    /**
     * JSP视图路径后缀名
     */
    public final static String JSP_VIEW_SUFFIX = "web.view.suffix";

    /**
     * 分页查询的每页的数据条数，“-1”表示不分页
     */
    public final static String PAGE_PAGESIZE = "page.pageSize";

    /**
     * 系统发送邮件的帐号
     */
    public final static String JAVA_MAIL_SENDER_USERNAME = "JavaMailSender.username";

    /**
     * 系统发送邮件帐号的密码
     */
    public final static String JAVA_MAIL_SENDER_PASSWORD = "JavaMailSender.password";

    /**
     * 系统发送邮件帐号的邮件服务器地址，可以为空
     */
    public final static String JAVA_MAIL_SENDER_HOST = "JavaMailSender.host";

}
