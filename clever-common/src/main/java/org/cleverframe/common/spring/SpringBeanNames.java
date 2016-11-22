package org.cleverframe.common.spring;

import org.cleverframe.common.IBeanNames;

/**
 * 定义Spring的配置文件中配置的Bean名称，使用静态字符串<br/>
 * TODO 若修改Spring的配置文件，则需要同步此类<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-9 17:54 <br/>
 */
@SuppressWarnings("unused")
public class SpringBeanNames implements IBeanNames {
    /**
     * Spring的Bean获取工具
     */
    public static final String SpringContextHolder = "common_SpringContextHolder";
    /**
     * 系统用户信息获取工具
     */
    public static final String UserUtils = "core_UserUtils";
    /**
     * 系统配置服务Bean，实现org.cleverframe.common.configuration.IConfig接口
     */
    public static final String Config = "core_ConfigService";
    /**
     * 资源初始化服务类(新增)
     */
    public static final String ResourcesIniHandle = "sys_ResourcesIniHandle";

    // -------------------------------------------------------------------------------------------//
    // spring-context-base.xml 配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * 数据库连接池Bean名称
     */
    public static final String DataSource = "dataSource";
    /**
     * Hibernate Session工厂Bean名称
     */
    public static final String SessionFactory = "sessionFactory";
    /**
     * HibernateTemplate Bean名称
     */
    public static final String HibernateTemplate = "hibernateTemplate";
    /**
     * LocalValidatorFactoryBean Bean名称
     */
    public static final String Validator = "validator";
    /**
     * EhCacheManagerFactoryBean Bean名称
     */
    public static final String CacheManager = "cacheManager";

    // -------------------------------------------------------------------------------------------//
    // spring-context-freemarker.xml 配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * Spring整合FreeMarker的Bean名称-用于系统使用的模板，除去了JSP的功能
     */
    public static final String CustomFreeMarkerConfigurer = "customFreeMarkerConfigurer";
    /**
     * Spring整合FreeMarker的Bean名称-用于前台页面输出(替代JSP)
     */
    public static final String FreemarkerConfig = "freemarkerConfig";

    // -------------------------------------------------------------------------------------------//
    // spring-context-javamail.xml 配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * Spring与Dozer整合
     */
    public static final String JavaMailSender = "javaMailSender";


    // -------------------------------------------------------------------------------------------//
    // spring-context-dozer.xml 配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * Spring发送邮件Bean，org.springframework.mail.javamail.JavaMailSenderImpl类实例
     */
    public static final String DozerBeanMapper = "dozerBeanMapper";

    // -------------------------------------------------------------------------------------------//
    // spring-context-quartz.xml配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * Quartz与Spring整合时的SchedulerFactoryBean
     */
    public static final String SchedulerFactoryBean = "schedulerFactoryBean";


    // -------------------------------------------------------------------------------------------//
    // spring-context-shiro.xml配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * Shiro权限控制过滤器入口
     */
    public static final String ShiroFilterFactoryBean = "shiroFilter";

    /**
     * 安全管理器,定义Shiro主要业务对象
     */
    public static final String DefaultWebSecurityManager = "securityManager";

    /**
     * 安全管理器，用于查询用户信息和用户角色、权限等信息 (Shiro Dao)
     */
    public static final String UserAuthorizingRealm = "userAuthorizingRealm";

    /**
     * 缓存管理器,缓存用户授权信息Cache, 采用EhCache
     */
    public static final String ShiroCacheManager = "shiroCacheManager";

    /**
     * shiro会话管理器
     */
    public static final String DefaultWebSessionManager = "shiroSessionManager";

    /**
     * RememberMe Cookie管理器
     */
    public static final String CookieRememberMeManager = "rememberMeManager";

    /**
     * 用户登录验证过滤器
     */
    public static final String LoginFormAuthenticationFilter = "loginFormAuthenticationFilter";

    /**
     * 用户访问资源授权过滤器，检验权限
     */
    public static final String UserPermissionsAuthorizationFilter = "userPermissionsAuthorizationFilter";

    /**
     * 用户访问资源授权过滤器，检验权限
     */
    public static final String KickOutSessionFilter = "kickOutSessionFilter";

    /**
     * 自定义Shiro权限解析器，把权限字符串解析成Permission对象
     */
    public static final String UserPermissionResolver = "permissionResolver";

    /**
     * 根据角色字符串解析出角色所有的权限
     */
    public static final String UserRolePermissionResolver = "rolePermissionResolver";

    /**
     * Realm的凭证匹配器，用于指定用户名、密码的解密加密算法
     */
    public static final String HashedCredentialsMatcher = "credentialsMatcher";

    /**
     * 会话DAO，使用Ehcache进行会话存储
     */
    public static final String ShiroSessionDAO = "sessionDAO";

    /**
     * 会话Cookie模板
     */
    public static final String SimpleCookie = "sessionIdCookie";

    /**
     * Shiro生命周期处理器,保证实现了Shiro内部lifecycle函数的bean执行
     */
    public static final String LifecycleBeanPostProcessor = "lifecycleBeanPostProcessor";

    /**
     * 会话验证调度器
     */
    public static final String QuartzSessionValidationScheduler = "sessionValidationScheduler";


    // -------------------------------------------------------------------------------------------//
    // spring-context-jedis.xml配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * Redis连接池Bean名称
     */
    public static final String JedisPoolConfig = "jedisPoolConfig";

    /**
     * Redis与Spring整合时的 RedisTemplate
     */
    public static final String RedisTemplate = "redisTemplate";

    /**
     * Redis与Spring整合时的 JedisConnectionFactory
     */
    public static final String JedisConnectionFactory = "jedisConnectionFactory";

    // -------------------------------------------------------------------------------------------//
    // spring-context-memcached.xml配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * Memcached客户端工厂Bean名称，通过此Bean名称直接得到Memcached客户端对象
     */
    public static final String MemcachedClient = "memcachedClient";

    // -------------------------------------------------------------------------------------------//
    // spring-context-zookeeper.xml配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * zookeeper与Spring整合时的 CuratorFramework
     */
    public static final String CuratorFramework = "curatorFramework";

    /**
     * zookeeper与Spring整合时的 RetryPolicy
     */
    public static final String RetryPolicy = "retryPolicy";

    // -------------------------------------------------------------------------------------------//
    // spring-context-filemanager.xml配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * FastDFS连接池配置
     */
    public static final String FastDfsConnectionPool = "fastDfsConnectionPool";
    /**
     * FastDfs命令执行器
     */
    public static final String FastDfsCommandExecutor = "fastDfsCommandExecutor";
    /**
     * FastDFS Tracker Client
     */
    public static final String FastDfsTrackerClient = "fastDfsTrackerClient";
    /**
     * FastDFS Storage Client
     */
    public static final String FastDfsStorageClient = "fastDfsStorageClient";

    // -------------------------------------------------------------------------------------------//
    // spring-mvc-filemanager.xml配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * 文件上传解析器
     */
    public static final String MultipartResolver = "multipartResolver";
}
