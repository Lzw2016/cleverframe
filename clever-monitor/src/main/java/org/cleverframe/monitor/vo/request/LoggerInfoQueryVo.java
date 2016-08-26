package org.cleverframe.monitor.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import javax.validation.constraints.Pattern;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-27 0:04 <br/>
 */
public class LoggerInfoQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 日志记录器名称
     */
    private String loggerName;

    /**
     * 日志记录器等级
     */
    @Pattern(regexp = "ALL|TRACE|DEBUG|INFO|WARN|ERROR|FATAL|OFF", message = "日志记录器等级只能是“ALL、TRACE、DEBUG、INFO、WARN、ERROR、FATAL、OFF”")
    private String level;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
