package org.cleverframe.monitor.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.cleverframe.common.log.Log4jManager;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.vo.response.LoggerInfoVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-26 10:35 <br/>
 */
@Component(MonitorBeanNames.Log4jMonitorService)
public class Log4jMonitorService extends BaseService {

    /**
     * 获取 LoggerInfoVo
     */
    private LoggerInfoVo getLoggerInfoVo(Logger logger) {
        if (logger == null) {
            return null;
        }
        LoggerInfoVo loggerInfoVo = new LoggerInfoVo();
        loggerInfoVo.setName(logger.getName());
        loggerInfoVo.setAdditivity(logger.getAdditivity());
        loggerInfoVo.setLevel(logger.getLevel() == null ? "" : logger.getLevel().toString());
        loggerInfoVo.setTraceEnabled(logger.isTraceEnabled());
        loggerInfoVo.setDebugEnabled(logger.isDebugEnabled());
        loggerInfoVo.setInfoEnabled(logger.isInfoEnabled());
        loggerInfoVo.setEffectiveLevel(logger.getEffectiveLevel().toString());
        return loggerInfoVo;

    }

    /**
     * 分页查询 Logger 信息
     *
     * @param page       分页数据
     * @param loggerName 日志名称
     * @return 分页数据
     */
    public Page<LoggerInfoVo> getAllLoggerInfoVo(Page<LoggerInfoVo> page, String loggerName, String level) {
        List<LoggerInfoVo> loggerInfoVoList = new ArrayList<>();
        List<Logger> loggerList = Log4jManager.getAllLogList();
        List<Logger> tmp = new ArrayList<>();
        for (Logger logger : loggerList) {
            if (StringUtils.isNotBlank(loggerName) && !logger.getName().equals(loggerName)) {
                continue;
            }
            if (StringUtils.isNotBlank(level) && !logger.getEffectiveLevel().toString().toUpperCase().equals(level.toUpperCase())) {
                continue;
            }
            tmp.add(logger);
        }
        int firstResult = page.getFirstResult();
        int lastResult = page.getFirstResult() + page.getPageSize();
        for (int i = firstResult; (i < lastResult && i < tmp.size()); i++) {
            Logger logger = tmp.get(i);
            LoggerInfoVo loggerInfoVo = getLoggerInfoVo(logger);
            if (loggerInfoVo != null) {
                loggerInfoVoList.add(loggerInfoVo);
            }
        }
        page.setCount(tmp.size());
        page.setList(loggerInfoVoList);
        return page;
    }

    /**
     * 设置日志记录器日志级别
     *
     * @param loggerName 日志记录器名称
     * @param level      日志级别
     * @return 成功返回true, 失败返回false
     */
    public boolean setLoggerLevel(String loggerName, String level) {
        if (StringUtils.isBlank(loggerName) || StringUtils.isBlank(level)) {
            return false;
        }
        // ALL|TRACE|DEBUG|INFO|WARN|ERROR|FATAL|OFF
        switch (level.toUpperCase()) {
            case "ALL":
                LogManager.getLogger(loggerName).setLevel(Level.ALL);
                break;
            case "TRACE":
                Log4jManager.enableTrack(loggerName);
                break;
            case "DEBUG":
                Log4jManager.enableDebug(loggerName);
                break;
            case "INFO":
                Log4jManager.enableInfo(loggerName);
                break;
            case "WARN":
                Log4jManager.enableWarn(loggerName);
                break;
            case "ERROR":
                Log4jManager.enableError(loggerName);
                break;
            case "FATAL":
                LogManager.getLogger(loggerName).setLevel(Level.FATAL);
                break;
            case "OFF":
                Log4jManager.enableOff(loggerName);
                break;
            default:
                return false;
        }
        return true;
    }
}
