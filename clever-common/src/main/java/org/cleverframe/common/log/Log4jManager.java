package org.cleverframe.common.log;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Log4j日志管理器：<br/>
 * 1.支持获取所有的日志对象<br/>
 * 2.动态的修改某个日志对象的日志打印级别<br/>
 * 3.获取日志的日志信息<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-4-28 18:16 <br/>
 */
public class Log4jManager {
    /**
     * 日志记录器
     */
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(Log4jManager.class);

    /**
     * 日志对象列表
     */
    private static final List<Logger> loggerList = new ArrayList<>();

    /**
     * 设置一个Logger的日志级别是DEBUG
     *
     * @param logName Logger名称，ClassName.class.getName()
     */
    public static void enableDebug(String logName) {
        LogManager.getLogger(logName).setLevel(Level.DEBUG);
    }

    /**
     * 设置一个Logger的日志级别是INFO
     *
     * @param logName Logger名称，ClassName.class.getName()
     */
    public static void enableInfo(String logName) {
        LogManager.getLogger(logName).setLevel(Level.INFO);
    }

    /**
     * 设置一个Logger的日志级别是WARN
     *
     * @param logName Logger名称，ClassName.class.getName()
     */
    public static void enableWarn(String logName) {
        LogManager.getLogger(logName).setLevel(Level.WARN);
    }

    /**
     * 设置一个Logger的日志级别是ERROR
     *
     * @param logName Logger名称，ClassName.class.getName()
     */
    public static void enableError(String logName) {
        LogManager.getLogger(logName).setLevel(Level.ERROR);
    }

    /**
     * 设置一个Logger的日志级别是TRACE
     *
     * @param logName Logger名称，ClassName.class.getName()
     */
    public static void enableTrack(String logName) {
        LogManager.getLogger(logName).setLevel(Level.TRACE);
    }

    /**
     * 设置一个Logger的日志级别是OFF
     *
     * @param logName Logger名称，ClassName.class.getName()
     */
    public static void enableOff(String logName) {
        LogManager.getLogger(logName).setLevel(Level.OFF);
    }

    /**
     * 获取log4j当前注册的所有日志
     *
     * @return 系统
     */
    public static List<Logger> getAllLogList() {
        // 日志列表
        loggerList.clear();
        // 获取根日志
        Logger rootLogger = LogManager.getRootLogger();
        loggerList.add(rootLogger);
        // 获取当前所有的日志对象
        @SuppressWarnings("unchecked")
        Enumeration<Logger> enums = (Enumeration<Logger>) rootLogger.getLoggerRepository().getCurrentLoggers();
        while (enums.hasMoreElements()) {
            Logger logger = enums.nextElement();
            loggerList.add(logger);
        }
        return loggerList;
    }
}
