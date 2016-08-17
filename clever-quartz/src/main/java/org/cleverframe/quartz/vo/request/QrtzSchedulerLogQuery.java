package org.cleverframe.quartz.vo.request;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-16 18:20 <br/>
 */
public class QrtzSchedulerLogQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 调度器名称
     */
    private String schedulerName;

    /**
     * 调度器ID
     */
    private String instanceName;

    /**
     * 触发事件调用的方法
     */
    private String methodName;

    /**
     * 记录时间-起始值
     */
    private Date logTimeStart;

    /**
     * 记录时间-结束值
     */
    private Date logTimeEnd;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getSchedulerName() {
        return schedulerName;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Date getLogTimeStart() {
        return logTimeStart;
    }

    public void setLogTimeStart(Date logTimeStart) {
        this.logTimeStart = logTimeStart;
    }

    public Date getLogTimeEnd() {
        return logTimeEnd;
    }

    public void setLogTimeEnd(Date logTimeEnd) {
        this.logTimeEnd = logTimeEnd;
    }
}
