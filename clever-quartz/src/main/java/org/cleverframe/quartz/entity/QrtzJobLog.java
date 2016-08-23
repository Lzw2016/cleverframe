package org.cleverframe.quartz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.cleverframe.core.persistence.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

/**
 * 实体类，对应表qrtz_job_log(Job执行日志表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-8-3 17:46 <br/>
 */
@Entity
@Table(name = "qrtz_job_log")
@DynamicInsert
@DynamicUpdate
public class QrtzJobLog extends IdEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 监听器名称
     */
    private String listenerName;

    /**
     * Scheduler名称
     */
    private String schedName;

    /**
     * Scheduler实例的唯一标识，配置文件中的Instance Id
     */
    private String instanceName;

    /**
     * Job key
     */
    private String jobName;

    /**
     * Job group 名称
     */
    private String jobGroup;

    /**
     * Job类名称
     */
    private String jobClassName;

    /**
     * Trigger key
     */
    private String triggerName;

    /**
     * Trigger group名称
     */
    private String triggerGroup;

    /**
     * 开始执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date startTime;

    /**
     * 执行结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date endTime;

    /**
     * 执行用时(ms)
     */
    private Long processTime;

    /**
     * 上一次执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date preRunTime;

    /**
     * 下一次执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date nextRunTime;

    /**
     * 执行次数
     */
    private Integer runCount;

    /**
     * 执行节点IP,可能有多个(‘;’分隔)
     */
    private String ipAddress;

    /**
     * 异常信息
     */
    @Lob
    @Column(columnDefinition = "MediumText")
    private String exceptionInfo;

    /**
     * 是否被否决（0：否；1：是）
     */
    private Character isVeto;

    /**
     * 执行前的JobDataMap数据
     */
    @Lob
    @Column(columnDefinition = "MediumText")
    private String beforeJobData;

    /**
     * 执行后的JobDataMap数据
     */
    @Lob
    @Column(columnDefinition = "MediumText")
    private String afterJobData;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 监听器名称
     */
    public String getListenerName() {
        return listenerName;
    }

    /**
     * 设置 监听器名称
     */
    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }

    /**
     * 获取 Scheduler名称
     */
    public String getSchedName() {
        return schedName;
    }

    /**
     * 设置 Scheduler名称
     */
    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    /**
     * 获取 Scheduler实例的唯一标识，配置文件中的Instance Id
     */
    public String getInstanceName() {
        return instanceName;
    }

    /**
     * 设置 Scheduler实例的唯一标识，配置文件中的Instance Id
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    /**
     * 获取 Job key
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置 Job key
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 获取 Job group 名称
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * 设置 Job group 名称
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * 获取 Job类名称
     */
    public String getJobClassName() {
        return jobClassName;
    }

    /**
     * 设置 Job类名称
     */
    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    /**
     * 获取 Trigger key
     */
    public String getTriggerName() {
        return triggerName;
    }

    /**
     * 设置 Trigger key
     */
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    /**
     * 获取 Trigger group名称
     */
    public String getTriggerGroup() {
        return triggerGroup;
    }

    /**
     * 设置 Trigger group名称
     */
    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    /**
     * 获取 开始执行时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置 开始执行时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取 执行结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置 执行结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 执行用时(ms)
     */
    public Long getProcessTime() {
        return processTime;
    }

    /**
     * 设置 执行用时(ms)
     */
    public void setProcessTime(Long processTime) {
        this.processTime = processTime;
    }

    /**
     * 获取 上一次执行时间
     */
    public Date getPreRunTime() {
        return preRunTime;
    }

    /**
     * 设置 上一次执行时间
     */
    public void setPreRunTime(Date preRunTime) {
        this.preRunTime = preRunTime;
    }

    /**
     * 获取 下一次执行时间
     */
    public Date getNextRunTime() {
        return nextRunTime;
    }

    /**
     * 设置 下一次执行时间
     */
    public void setNextRunTime(Date nextRunTime) {
        this.nextRunTime = nextRunTime;
    }

    /**
     * 获取 执行次数
     */
    public Integer getRunCount() {
        return runCount;
    }

    /**
     * 设置 执行次数
     */
    public void setRunCount(Integer runCount) {
        this.runCount = runCount;
    }

    /**
     * 获取 执行节点IP,可能有多个(‘;’分隔)
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 设置 执行节点IP,可能有多个(‘;’分隔)
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 获取 异常信息
     */
    public String getExceptionInfo() {
        return exceptionInfo;
    }

    /**
     * 设置 异常信息
     */
    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    /**
     * 获取 是否被否决（0：否；1：是）
     */
    public Character getIsVeto() {
        return isVeto;
    }

    /**
     * 设置 是否被否决（0：否；1：是）
     */
    public void setIsVeto(Character isVeto) {
        this.isVeto = isVeto;
    }

    /**
     * 获取 执行前的JobDataMap数据
     */
    public String getBeforeJobData() {
        return beforeJobData;
    }

    /**
     * 设置 执行前的JobDataMap数据
     */
    public void setBeforeJobData(String beforeJobData) {
        this.beforeJobData = beforeJobData;
    }

    /**
     * 获取 执行后的JobDataMap数据
     */
    public String getAfterJobData() {
        return afterJobData;
    }

    /**
     * 设置 执行后的JobDataMap数据
     */
    public void setAfterJobData(String afterJobData) {
        this.afterJobData = afterJobData;
    }
}
