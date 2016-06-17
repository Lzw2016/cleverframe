package org.cleverframe.common.ehcache;

/**
 * 定义系统所有Ehcahe配置的缓存名称，使用静态字符串<br/>
 * 1.此类的静态字符串的值与Ehcahe的配置文件(ehcache-local.xml)是一致的<br/>
 * 2.若修改ehcache-local.xml里的缓存名称配置，则需要同步此类<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 10:27 <br/>
 */
@SuppressWarnings("unused")
public class EhCacheNames {

    /**
     * 数据库脚本缓存名称
     */
    public static final String QLScriptCache = "QLScriptCache";

    /**
     * 系统配置缓存名称
     */
    public static final String ConfigCache = "ConfigCache";


    // -------------------------------------------------------------------------------------------//
    // Shiro的缓存配置名称
    // -------------------------------------------------------------------------------------------//
    /**
     * Shiro活动的会话缓存名称
     */
    public static final String ShiroActiveSessionCache = "shiro-activeSessionCache";
    /**
     * Shiro的AuthenticationInfo信息缓存名称
     */
    public static final String ShiroAuthenticationCache = "shiroAuthenticationCache";
    /**
     * Shiro的AuthorizationInfo信息缓存名称
     */
    public static final String ShiroAuthorizationCache = "shiroAuthorizationCache";


}
