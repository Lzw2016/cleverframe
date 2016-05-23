package org.cleverframe.common.configuration;

/**
 * 系统配置的所有配置的默认值<br/>
 * cleverframe.properties 文件里的配置在此类都需要定义相应的字符串常量<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 0:10 <br/>
 *
 * @see BaseConfigNames
 */
@SuppressWarnings("unused")
public class BaseConfigValues {
    // -------------------------------------------------------------------------------------------//
    // 数据库配置
    // -------------------------------------------------------------------------------------------//
    /**
     * 数据库类型值，默认值：mysql
     */
    public final static String JDBC_TYPE = "mysql";

    /**
     * 数据库驱动值，默认值： com.mysql.jdbc.Driver
     */
    public final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 数据库地址值，默认值：jdbc:mysql://127.0.0.1:3306/mysql?useUnicode=true&characterEncoding=utf-8
     */
    public final static String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/mysql?useUnicode=true&characterEncoding=utf-8";

    /**
     * 数据库用户名值，默认值： root
     */
    public final static String JDBC_USERNAME = "root";

    /**
     * 数据库密码值，默认值：root
     */
    public final static String JDBC_PASSWORD = "root";

    /**
     * 数据库连接池初始数，默认值：5
     */
    public final static String JDBC_POOL_INIT = "5";

    /**
     * 数据库连接池最小大小值，默认值：3
     */
    public final static String JDBC_POOL_MINIDLE = "3";

    /**
     * 数据库连接池最小大小值，默认值：20
     */
    public final static String JJDBC_POOL_MAXACTIVE = "20";

    /**
     * 数据库测试使用的SQL，默认值：SELECT 'x' FROM DUAL
     */
    public final static String JDBC_TESTSQL = "SELECT 'x' FROM DUAL";


    // -------------------------------------------------------------------------------------------//
    // 框架配置
    // -------------------------------------------------------------------------------------------//
    /**
     * Hibernate是否显示sql语句值，默认值：true
     */
    public final static String HIBERNATE_SHOW_SQL = "true";

    /**
     * 最大文件上传限制，单位字节。默认值： 10M=10*1024*1024(B)=10485760
     */
    public final static String WEB_MAX_UPLOAD_SIZE = "10485760";

    /**
     * 储路上传文件的存径，默认值
     */
    public final static String WEB_FILE_STORAGE_PATH = "F:\\fileStoragePath";

    /**
     * 上传文件到FTP的存储路径，默认值
     */
    public final static String WEB_FILE_STORAGE_PATH_BY_FTP = "web.fileStoragePathByFTP";

    /**
     * 存储上传文件的FTP服务器地址，默认值
     */
    public final static String FTP_HOST = "";

    /**
     * 存储上传文件的FTP服务器端口号，默认值
     */
    public final static String FTP_PORT = "";

    /**
     * 存储上传文件的FTP服务器用户名，默认值
     */
    public final static String FTP_USER_NAME = "";

    /**
     * 存储上传文件的FTP服务器用户密码，默认值
     */
    public final static String FTP_PASSWORD = "";

    /**
     * FastDFS 配置文件地址，默认值：fdfs_client.conf
     */
    public final static String FASTDFS_CONF_FILE_NAME = "fdfs_client.conf";


    // -------------------------------------------------------------------------------------------//
    // 系统配置
    // -------------------------------------------------------------------------------------------//
    /**
     * 系统名称，默认值：CleverFrame 快速开发平台
     */
    public final static String PRODUCTNAME = "CleverFrame 快速开发平台";

    /**
     * 版权年份，默认值：2014
     */
    public final static String COPYRIGHTYEAR = "2014";

    /**
     * 系统版本号，默认值：V0.1
     */
    public final static String VERSION = "V0.1";

    /**
     * 静态资源基路径，默认值：static
     */
    public final static String STATIC_PATH = "static";

    /**
     * 系统HTML、Jsp、Freemarker等系统页面基路径，默认值：modules
     */
    public final static String VIEWS_PATH = "modules";

    /**
     * 系统文档基路径，默认值：modules/doc
     */
    public final static String DOC_PATH = "modules/doc";

    /**
     * MVC框架的请求映射基路径，默认值：mvc
     */
    public final static String MVC_PATH = "mvc";

    /**
     * JSP视图文件存放路径，默认值：/WEB-INF/modules/
     */
    public final static String JSP_VIEW_PREFIX = "/WEB-INF/modules/";

    /**
     * JSP视图路径后缀名值，默认值：.jsp
     */
    public final static String JSP_VIEW_SUFFIX = ".jsp";

    /**
     * 分页查询的每页的数据条数，默认值：10
     */
    public final static String PAGE_PAGESIZE = "10";


    /**
     * 系统发送邮件的帐号
     */
    public final static String JAVA_MAIL_SENDER_USERNAME = "";

    /**
     * 系统发送邮件帐号的密码
     */
    public final static String JAVA_MAIL_SENDER_PASSWORD = "";

    /**
     * 系统发送邮件帐号的邮件服务器地址，可以为空
     */
    public final static String JAVA_MAIL_SENDER_HOST = "";
}
