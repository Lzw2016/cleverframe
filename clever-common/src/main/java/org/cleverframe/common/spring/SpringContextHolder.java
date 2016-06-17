package org.cleverframe.common.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 获取Spring ApplicationContext容器的类<br/>
 * 1.以静态变量保存Spring ApplicationContext<br/>
 * 2.可在任何代码任何地方任何时候取出ApplicaitonContext<br/>
 * 3.提供获取Spring容器中的Bean的方法<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-9 14:25 <br/>
 */
@Component(SpringBeanNames.SpringContextHolder)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    /**
     * Spring ApplicationContext容器
     */
    private static ApplicationContext applicationContext = null;

    /**
     * Spring WebApplicationContext容器，不能直接获取该属性，必须调用getter方法
     */
    private static WebApplicationContext webApplicationContext = null;

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContextHolder.applicationContext != null) {
            logger.info("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextHolder.applicationContext);
        }
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() throws Exception {
        applicationContext = null;
        webApplicationContext = null;
    }

    /**
     * 获取Spring容器applicationContext对象
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取Spring容器webApplicationContext对象
     */
    @Deprecated
    public static WebApplicationContext getWebApplicationContext() {
        if (webApplicationContext == null) {
            webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        }
        return webApplicationContext;
    }

    /**
     * 获取系统根目录
     */
    public static String getRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = applicationContext.getResource("").getFile().getAbsolutePath();
        } catch (Throwable e) {
            logger.warn("获取系统根目录失败", e);
        }
        return rootRealPath;
    }

    /**
     * 获取资源根目录
     */
    public static String getResourceRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = new DefaultResourceLoader().getResource("").getFile().getAbsolutePath();
        } catch (Throwable e) {
            logger.warn("获取资源根目录失败", e);
        }
        return rootRealPath;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @return 返回Bean对象，失败返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        try {
            return (T) applicationContext.getBean(name);
        } catch (Throwable e) {
            logger.error("获取Bean失败", e);
            return null;
        }
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @return 返回Bean对象，失败返回null
     */
    public static <T> T getBean(Class<T> requiredType) {
        try {
            return applicationContext.getBean(requiredType);
        } catch (Throwable e) {
            logger.error("获取Bean失败", e);
            return null;
        }
    }

    /**
     * 从静态变量webApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @return 返回Bean对象，失败返回null
     */
    @Deprecated
    @SuppressWarnings({"unchecked", "deprecation"})
    public static <T> T getWebBean(String name) {
        try {
            return (T) getWebApplicationContext().getBean(name);
        } catch (Throwable e) {
            logger.error("获取Bean失败", e);
            return null;
        }
    }

    /**
     * 从静态变量webApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @return 返回Bean对象，失败返回null
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public static <T> T getWebBean(Class<T> requiredType) {
        try {
            return getWebApplicationContext().getBean(requiredType);
        } catch (Throwable e) {
            logger.error("获取Bean失败", e);
            return null;
        }
    }
}
