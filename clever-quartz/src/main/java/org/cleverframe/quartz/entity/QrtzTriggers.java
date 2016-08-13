package org.cleverframe.quartz.entity;

import org.cleverframe.quartz.entity.key.QrtzTriggersKey;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 实体类，对应表qrtz_triggers(存储已配置的Trigger的信息)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-13 12:48:03 <br/>
 */
@Entity
@Table(name = "qrtz_triggers")
@DynamicInsert
@DynamicUpdate
@IdClass(QrtzTriggersKey.class)
public class QrtzTriggers implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Scheduler名称
     */
    @Id
    private String schedName;

    /**
     * Trigger key
     */
    @Id
    private String triggerName;

    /**
     * Trigger group名称
     */
    @Id
    private String triggerGroup;

    /**
     * Job key
     */
    private String jobName;

    /**
     * Job group 名称
     */
    private String jobGroup;

    /**
     * Trigger 描述， .withDescription()方法传入的string
     */
    private String description;

    /**
     * 下一次触发时间
     */
    private Long nextFireTime;

    /**
     * 上一次触发时间，默认-1
     */
    private Long prevFireTime;

    /**
     * Trigger 优先级，默认5
     */
    private Integer priority;

    /**
     * Trigger状态，PAUSED_BLOCKED:停止_阻塞; PAUSED:停止; WAITING:等待执行; ACQUIRED:已获得; EXECUTING:执行中; COMPLETE:完成; BLOCKED:阻塞; ERROR:错误; DELETED:已删除
     */
    private String triggerState;

    /**
     * Cron 或 Simple
     */
    private String triggerType;

    /**
     * Trigger开始时间
     */
    private Long startTime;

    /**
     * Trigger结束时间
     */
    private Long endTime;

    /**
     * Trigger关联的Calendar name
     */
    private String calendarName;

    /**
     * misfire规则id
     */
    private Short misfireInstr;

    /**
     * 存储Trigger的JobDataMap等
     */
    @Lob
    @Column(columnDefinition = "BLOB")
    private Byte[] jobData;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

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
     * 获取 Trigger 描述， .withDescription()方法传入的string
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 Trigger 描述， .withDescription()方法传入的string
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 下一次触发时间
     */
    public Long getNextFireTime() {
        return nextFireTime;
    }

    /**
     * 设置 下一次触发时间
     */
    public void setNextFireTime(Long nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    /**
     * 获取 上一次触发时间，默认-1
     */
    public Long getPrevFireTime() {
        return prevFireTime;
    }

    /**
     * 设置 上一次触发时间，默认-1
     */
    public void setPrevFireTime(Long prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    /**
     * 获取 Trigger 优先级，默认5
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 设置 Trigger 优先级，默认5
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 获取 Trigger状态，PAUSED_BLOCKED:停止_阻塞; PAUSED:停止; WAITING:等待执行; ACQUIRED:已获得; EXECUTING:执行中; COMPLETE:完成; BLOCKED:阻塞; ERROR:错误; DELETED:已删除
     */
    public String getTriggerState() {
        return triggerState;
    }

    /**
     * 设置 Trigger状态，PAUSED_BLOCKED:停止_阻塞; PAUSED:停止; WAITING:等待执行; ACQUIRED:已获得; EXECUTING:执行中; COMPLETE:完成; BLOCKED:阻塞; ERROR:错误; DELETED:已删除
     */
    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    /**
     * 获取 Cron 或 Simple
     */
    public String getTriggerType() {
        return triggerType;
    }

    /**
     * 设置 Cron 或 Simple
     */
    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    /**
     * 获取 Trigger开始时间
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * 设置 Trigger开始时间
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取 Trigger结束时间
     */
    public Long getEndTime() {
        return endTime;
    }

    /**
     * 设置 Trigger结束时间
     */
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 Trigger关联的Calendar name
     */
    public String getCalendarName() {
        return calendarName;
    }

    /**
     * 设置 Trigger关联的Calendar name
     */
    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    /**
     * 获取 misfire规则id
     */
    public Short getMisfireInstr() {
        return misfireInstr;
    }

    /**
     * 设置 misfire规则id
     */
    public void setMisfireInstr(Short misfireInstr) {
        this.misfireInstr = misfireInstr;
    }

    /**
     * 获取 存储Trigger的JobDataMap等
     */
    public Byte[] getJobData() {
        return jobData;
    }

    /**
     * 设置 存储Trigger的JobDataMap等
     */
    public void setJobData(Byte[] jobData) {
        this.jobData = jobData;
    }
}
