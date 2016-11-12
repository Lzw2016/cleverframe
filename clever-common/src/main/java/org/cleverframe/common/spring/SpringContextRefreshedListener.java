package org.cleverframe.common.spring;

import org.cleverframe.common.attributes.CommonApplicationAttributes;
import org.cleverframe.common.configuration.CustomPropertyPlaceholderConfigurer;
import org.cleverframe.common.configuration.IConfig;
import org.cleverframe.common.controller.XssExcludeUrlUtils;
import org.cleverframe.common.initialize.IHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.Set;

/**
 * Spring容器初始化完毕事件，需要在Spring中注入该Bean<br/>
 * ContextRefreshedEvent 当ApplicationContext初始化或者刷新时触发该事件<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-9 17:44 <br/>
 */
public class SpringContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(SpringContextRefreshedListener.class);

    /**
     * 系统根路径：/CleverFrame
     */
    private String appPath;

    /**
     * 静态资源基路径：/CleverFrame/static
     */
    private String staticPath;

    /**
     * 系统文档基路径：/CleverFrame/modules/doc
     */
    private String docPath;

    /**
     * 系统HTML、Jsp、Freemarker等系统页面基路径：/CleverFrame/modules
     */
    private String modulesPath;

    /**
     * MVC框架的请求映射基路径：/CleverFrame/mvc
     */
    private String mvcPath;

    /**
     * 把配置信息保存到数据库里<br/>
     *
     * @return 返回新增的配置数量
     */
    public int initPropertiesMap() {
        int count = 0;
        IConfig config = SpringContextHolder.getBean(SpringBeanNames.Config);
        if (config == null) {
            RuntimeException exception = new RuntimeException("未注入Bean:[" + SpringBeanNames.Config + "]");
            logger.error(exception.getMessage(), exception);
            return count;
        }
        // 必须清除缓存数据，防止数据库与缓存中的数据不一样
        config.clearAllCache();
        StringBuilder strTmp = new StringBuilder();
        strTmp.append("\r\n");
        strTmp.append("#=======================================================================================================================#\r\n");
        strTmp.append("# 新增的配置如下:\r\n");
        Set<Map.Entry<String, String>> set = CustomPropertyPlaceholderConfigurer.PropertiesMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            boolean flag = config.updateOrAddConfig(entry.getKey(), entry.getValue());
            if (flag) {
                strTmp.append("#\t ").append(entry.getKey()).append(" = ").append(entry.getValue()).append("\r\n");
                count++;
            }
        }
        strTmp.append("#=======================================================================================================================#");
        if (logger.isInfoEnabled() && count > 0) {
            logger.info(strTmp.toString());
        }
        return count;
    }

    /**
     * 新增数据库里没有的资源
     *
     * @return 返回新增资源的数量
     */
    public int resourcesInitialize(ContextRefreshedEvent event) {
        int addResourcesCount = 0;
        IHandle resourcesIniHandle = SpringContextHolder.getBean(SpringBeanNames.ResourcesIniHandle);
        if (resourcesIniHandle == null) {
            RuntimeException exception = new RuntimeException("未注入Bean:[" + SpringBeanNames.ResourcesIniHandle + "]");
            logger.error(exception.getMessage(), exception);
        } else {
            addResourcesCount = resourcesIniHandle.initialize(event);
        }
        return addResourcesCount;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // TODO Spring初始化完成后处理
        ServletContext servletContext = null;
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        if (null != webApplicationContext) {
            servletContext = webApplicationContext.getServletContext();
        } else if (event.getSource() instanceof GenericWebApplicationContext) {
            // 测试时起作用
            servletContext = ((GenericWebApplicationContext) event.getSource()).getServletContext();
        }
        if (null == servletContext) {
            logger.warn("### Spring MVC Context 容器初始化完成之后的处理-获取ServletContext失败");
            return;
        }
        appPath = servletContext.getContextPath();
        if (!staticPath.contains(appPath)) {
            staticPath = appPath + "/" + staticPath;
        }
        if (!docPath.contains(appPath)) {
            docPath = appPath + "/" + docPath;
        }
        if (!modulesPath.contains(appPath)) {
            modulesPath = appPath + "/" + modulesPath;
        }
        if (!mvcPath.contains(appPath)) {
            mvcPath = appPath + "/" + mvcPath;
        }
        servletContext.setAttribute(CommonApplicationAttributes.APP_PATH, appPath);
        servletContext.setAttribute(CommonApplicationAttributes.STATIC_PATH, staticPath);
        servletContext.setAttribute(CommonApplicationAttributes.DOC_PATH, docPath);
        servletContext.setAttribute(CommonApplicationAttributes.MODULES_PATH, modulesPath);
        servletContext.setAttribute(CommonApplicationAttributes.MVC_PATH, mvcPath);

        if (logger.isInfoEnabled()) {
            String tmp = "\r\n" +
                    "#=======================================================================================================================#\r\n" +
                    "# Spring MVC Context 容器初始化完成之后的处理：\r\n" +
                    "#\t 设置ServletContext属性 " + CommonApplicationAttributes.APP_PATH + " = " + appPath + "\r\n" +
                    "#\t 设置ServletContext属性 " + CommonApplicationAttributes.STATIC_PATH + " = " + staticPath + "\r\n" +
                    "#\t 设置ServletContext属性 " + CommonApplicationAttributes.DOC_PATH + " = " + docPath + "\r\n" +
                    "#\t 设置ServletContext属性 " + CommonApplicationAttributes.MODULES_PATH + " = " + modulesPath + "\r\n" +
                    "#\t 设置ServletContext属性 " + CommonApplicationAttributes.MVC_PATH + " = " + mvcPath + "\r\n" +
                    "#\t 新增配置数据 " + initPropertiesMap() + "条\r\n" +
                    "#\t 加载不需要XXS过滤的URL路径 " + XssExcludeUrlUtils.loadXSSExcludeUrl() + " 个\r\n" +
                    "#\t 新增Url资源信息 " + resourcesInitialize(event) + " 条\r\n" +
                    "#=======================================================================================================================#\r\n";
            logger.info(tmp);
        }
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public String getStaticPath() {
        return staticPath;
    }

    public void setStaticPath(String staticPath) {
        this.staticPath = staticPath;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public String getModulesPath() {
        return modulesPath;
    }

    public void setModulesPath(String modulesPath) {
        this.modulesPath = modulesPath;
    }

    public String getMvcPath() {
        return mvcPath;
    }

    public void setMvcPath(String mvcPath) {
        this.mvcPath = mvcPath;
    }


//    ContextRefreshedEvent   当ApplicationContext初始化或者刷新时触发该事件
//    ContextClosedEvent      当ApplicationContext被关闭时触发该事件。容器被关闭时，其管理的所有单例Bean都被销毁
//    RequestHandleEvent      在Web应用中，当一个http请求（request）结束触发该事件
//    ContestStartedEvent     Spring2.5新增的事件，当容器调用ConfigurableApplicationContext的Start()方法开始/重新开始容器时触发该事件
//    ContestStopedEvent      Spring2.5新增的事件，当容器调用ConfigurableApplicationContext的Stop()方法停止容器时触发该事件
}
