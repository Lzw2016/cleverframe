package org.cleverframe.common.attributes;

/**
 * 定义Application范围的属性值的名称常量字符串<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-17 11:27 <br/>
 */
public class CommonApplicationAttributes implements IApplicationAttributes {
    /**
     * 系统根路径：/CleverFrame
     */
    public static final String APP_PATH = "appPath";

    /**
     * 静态资源基路径：/CleverFrame/static
     */
    public static final String STATIC_PATH = "staticPath";

    /**
     * 系统文档基路径：/CleverFrame/modules/doc
     */
    public static final String DOC_PATH = "docPath";

    /**
     * 系统HTML、Jsp、Freemarker等系统页面基路径：/CleverFrame/modules
     */
    public static final String MODULES_PATH = "modulesPath";

    /**
     * MVC框架的请求映射基路径：/CleverFrame/mvc
     */
    public static final String MVC_PATH = "mvcPath";

    /**
     * 服务器本次启动后处理的请求总数,类型:long
     */
    public static final String REQUEST_COUNT_BY_START = "Request_Count_By_Start";

    /**
     * 服务器当天处理请求总数(00:00:00--23:59:59),类型:long
     */
    public static final String REQUEST_COUNT_BY_DAY = "Request_Count_By_Day";

    /**
     * 统计服务器当前小时处理请求总数(n:00:00-n:59:59),类型:long
     */
    public static final String REQUEST_COUNT_BY_HOUR = "Request_Count_By_Hour";

    /**
     * 最后一次请求的时间,类型:long
     */
    public static final String LAST_REQUEST_TIME = "Last_Request_Time";

    /**
     * 同时在线人数,类型:int
     */
    public static final String LOGIN_USER_COUNT = "Login_User_Count";
}
