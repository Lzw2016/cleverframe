package org.cleverframe.monitor.vo.request;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-27 0:31 <br/>
 */
public class SetLoggerLevelVo {
    /**
     * 日志记录器名称
     */
    @NotBlank(message = "日志记录器名称不能为空")
    private String loggerName;

    /**
     * 日志记录器等级
     */
    @NotBlank(message = "日志记录器等级不能为空")
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
