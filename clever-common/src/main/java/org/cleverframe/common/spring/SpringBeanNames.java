package org.cleverframe.common.spring;

import org.cleverframe.common.IBeanNames;

/**
 * 定义Spring的配置文件中配置的Bean名称，使用静态字符串<br/>
 * TODO 若修改Spring的配置文件，则需要同步此类<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-9 17:54 <br/>
 */
@SuppressWarnings("unused")
public class SpringBeanNames implements IBeanNames {
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
    /**
     * 系统配置服务Bean，实现org.cleverframe.common.configuration.IConfig接口
     */
    public static final String Config = "configService";

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
    // spring-context-shiro.xml配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//


    // -------------------------------------------------------------------------------------------//
    // spring-context-jedis.xml配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * Redis连接池Bean名称
     */
    public static final String JedisPoolConfig = "jedisPoolConfig";
    /**
     * Redis连接工厂Bean名称
     */
    public static final String JedisConnectionFactory = "jedisConnectionFactory";
    /**
     * Spring提供的Redis模版Bean名称
     */
    public static final String JedisTemplate = "jedisTemplate";

    // -------------------------------------------------------------------------------------------//
    // spring-context-memcached.xml配置文件的Bean名称
    // -------------------------------------------------------------------------------------------//
    /**
     * Memcached客户端工厂Bean名称，通过此Bean名称直接得到Memcached客户端对象
     */
    public static final String MemcachedClient = "memcachedClient";
}
