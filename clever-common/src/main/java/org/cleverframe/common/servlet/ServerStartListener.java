package org.cleverframe.common.servlet;

import org.cleverframe.common.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统启动或关闭的监听器<br/>
 * 需要在web.xml中配置监听器<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-9 17:59 <br/>
 */
public class ServerStartListener implements ServletContextListener {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(ServerStartListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // TODO 系统启动时的任务
        // 必须调用一下：ApplicationAttributes.SERVER_START_TIME，初始化服务器启动时间
        if (logger.isInfoEnabled()) {
            String tmp = "\r\n" +
                    "#=======================================================================================================================#\r\n" +
                    "# 服务器启动时的任务：\r\n" +
                    "#\t 服务器启动时间：" + DateTimeUtils.getCurrentDateTime() + "\r\n" +
                    "#=======================================================================================================================#\r\n";
            logger.info(tmp);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO 系统关闭时的任务
        if (logger.isInfoEnabled()) {
            String tmp = "\r\n" +
                    "#=======================================================================================================================#\r\n" +
                    "# 服务器关闭时的任务：\r\n" +
                    "#\t 服务器关闭时间：" + DateTimeUtils.getCurrentDateTime() + "\r\n" +
                    "#=======================================================================================================================#\r\n";
            logger.info(tmp);
        }
    }
}
