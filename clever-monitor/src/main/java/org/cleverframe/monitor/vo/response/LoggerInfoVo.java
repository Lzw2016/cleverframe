package org.cleverframe.monitor.vo.response;

import org.cleverframe.common.vo.response.BaseResponseVo;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-26 11:00 <br/>
 */
public class LoggerInfoVo extends BaseResponseVo {
    private static final long serialVersionUID = 1L;

    private String name;
    private String level;
    private String effectiveLevel;
    private boolean additivity;
    private boolean traceEnabled;
    private boolean debugEnabled;
    private boolean infoEnabled;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isAdditivity() {
        return additivity;
    }

    public void setAdditivity(boolean additivity) {
        this.additivity = additivity;
    }

    public String getEffectiveLevel() {
        return effectiveLevel;
    }

    public void setEffectiveLevel(String effectiveLevel) {
        this.effectiveLevel = effectiveLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTraceEnabled() {
        return traceEnabled;
    }

    public void setTraceEnabled(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public boolean isInfoEnabled() {
        return infoEnabled;
    }

    public void setInfoEnabled(boolean infoEnabled) {
        this.infoEnabled = infoEnabled;
    }
}
