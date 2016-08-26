package org.cleverframe.monitor.service;

import org.apache.commons.lang3.StringUtils;
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
    public Page<LoggerInfoVo> getAllLoggerInfoVo(Page<LoggerInfoVo> page, String loggerName) {
        List<LoggerInfoVo> loggerInfoVoList = new ArrayList<>();
        List<Logger> loggerList = Log4jManager.getAllLogList();
        if (StringUtils.isBlank(loggerName)) {
            // 分页查询
            int firstResult = page.getFirstResult();
            int lastResult = page.getFirstResult() + page.getPageSize();
            for (int i = firstResult; (i < lastResult && i < loggerList.size()); i++) {
                Logger logger = loggerList.get(i);
                LoggerInfoVo loggerInfoVo = getLoggerInfoVo(logger);
                if (loggerInfoVo != null) {
                    loggerInfoVoList.add(loggerInfoVo);
                }
            }
            page.setCount(loggerList.size());
        } else {
            // 单独查询一个
            for (Logger logger : loggerList) {
                if (logger.getName().equals(loggerName)) {
                    LoggerInfoVo loggerInfoVo = getLoggerInfoVo(logger);
                    if (loggerInfoVo != null) {
                        loggerInfoVoList.add(loggerInfoVo);
                    }
                    break;
                }
            }
            page.setCount(loggerInfoVoList.size());
        }
        page.setList(loggerInfoVoList);
        return page;
    }


}
