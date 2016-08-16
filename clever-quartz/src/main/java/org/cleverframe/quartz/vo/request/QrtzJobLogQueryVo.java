package org.cleverframe.quartz.vo.request;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-16 15:46 <br/>
 */
public class QrtzJobLogQueryVo implements Serializable {
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
     * 任务分组
     */
    private String jobGroup;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务实现类
     */
    private String jobClassName;

    /**
     * 开始触发时间-起始值
     */
    private Date startTimeByStart;

    /**
     * 开始触发时间-结束值
     */
    private Date startTimeByEnd;

    /**
     * 处理时间-最小值
     */
    private Long processTimeByMin;

    /**
     * 处理时间-最大值
     */
    private Long processTimeByMax;

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

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public Date getStartTimeByStart() {
        return startTimeByStart;
    }

    public void setStartTimeByStart(Date startTimeByStart) {
        this.startTimeByStart = startTimeByStart;
    }

    public Date getStartTimeByEnd() {
        return startTimeByEnd;
    }

    public void setStartTimeByEnd(Date startTimeByEnd) {
        this.startTimeByEnd = startTimeByEnd;
    }

    public Long getProcessTimeByMin() {
        return processTimeByMin;
    }

    public void setProcessTimeByMin(Long processTimeByMin) {
        this.processTimeByMin = processTimeByMin;
    }

    public Long getProcessTimeByMax() {
        return processTimeByMax;
    }

    public void setProcessTimeByMax(Long processTimeByMax) {
        this.processTimeByMax = processTimeByMax;
    }
}
