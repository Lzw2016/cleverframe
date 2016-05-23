package org.cleverframe.common.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Spring容器初始化完毕事件，需要在Spring中注入该Bean<br/>
 * ContextRefreshedEvent 当ApplicationContext初始化或者刷新时触发该事件<br/>
 * <p/>
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
    private String viewsPath;

    /**
     * MVC框架的请求映射基路径：/CleverFrame/mvc
     */
    private String mvcPath;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // TODO Spring初始化完成后处理
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        appPath = servletContext.getContextPath();
        servletContext.setAttribute("appPath", appPath);
        servletContext.setAttribute("staticPath", staticPath);
        servletContext.setAttribute("docPath", docPath);
        servletContext.setAttribute("viewsPath", viewsPath);
        servletContext.setAttribute("mvcPath", mvcPath);

        if (logger.isInfoEnabled()) {
            String tmp = "\r\n" +
                    "#=======================================================================================================================#\r\n" +
                    "# Spring MVC Context 容器初始化完成之后的处理：\r\n" +
                    "#\t 设置ServletContext属性 appPath = " + appPath + "\r\n" +
                    "#\t 设置ServletContext属性 staticPath = " + staticPath + "\r\n" +
                    "#\t 设置ServletContext属性 docPath = " + docPath + "\r\n" +
                    "#\t 设置ServletContext属性 viewsPath = " + viewsPath + "\r\n" +
                    "#\t 设置ServletContext属性 mvcPath = " + mvcPath + "\r\n" +
                    "#=======================================================================================================================#\r\n";
            logger.info(tmp);
        }
    }

    public static Logger getLogger() {
        return logger;
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

    public String getViewsPath() {
        return viewsPath;
    }

    public void setViewsPath(String viewsPath) {
        this.viewsPath = viewsPath;
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
