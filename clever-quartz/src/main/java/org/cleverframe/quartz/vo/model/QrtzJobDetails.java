package org.cleverframe.quartz.vo.model;

import java.io.Serializable;
import java.util.Map;

/**
 *
 *
 * 作者：LiZW <br/>
 * 创建时间：2016-7-29 14:34 <br/>
 */
public class QrtzJobDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Scheduler名称 */
    private String schedName;

    /** Job key */
    private String jobName;

    /** Job group 名称 */
    private String jobGroup;

    /** Job描述， .withDescription()方法传入的string */
    private String description;

    /** 实现Job的类名，trigger触发时调度此类的execute方法 */
    private String jobClassName;

    /** 为true时，Job相关的trigger完成以后，Job数据继续保留 */
    private boolean isDurable;

    /** 是否不允许并发，为true时，如果下一次的触发事件到了，而上一次的job执行还未结束，则后续的触发会放入队列等待 */
    private boolean isNonconcurrent;

    /** 是否在多次调度之间更新JobDataMap */
    private boolean isUpdateData;

    /** Scheduler实例发生故障时，故障恢复节点会检测故障的Scheduler正在调度的任务是否需要recovery(复苏)，如果需要会添加一个只执行一次的simple trigger重新触发 */
    private boolean requestsRecovery;

    /** 存储JobDataMap等 */
    private Map<String, Object> jobData;

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
     * 获取 Job描述， .withDescription()方法传入的string
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 Job描述， .withDescription()方法传入的string
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 实现Job的类名，trigger触发时调度此类的execute方法
     */
    public String getJobClassName() {
        return jobClassName;
    }

    /**
     * 设置 实现Job的类名，trigger触发时调度此类的execute方法
     */
    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    /**
     * 获取 为true时，Job相关的trigger完成以后，Job数据继续保留
     */
    public boolean getIsDurable() {
        return isDurable;
    }

    /**
     * 设置 为true时，Job相关的trigger完成以后，Job数据继续保留
     */
    public void setIsDurable(boolean isDurable) {
        this.isDurable = isDurable;
    }

    /**
     * 获取 是否不允许并发，为true时，如果下一次的触发事件到了，而上一次的job执行还未结束，则后续的触发会放入队列等待
     */
    public boolean getIsNonconcurrent() {
        return isNonconcurrent;
    }

    /**
     * 设置 是否不允许并发，为true时，如果下一次的触发事件到了，而上一次的job执行还未结束，则后续的触发会放入队列等待
     */
    public void setIsNonconcurrent(boolean isNonconcurrent) {
        this.isNonconcurrent = isNonconcurrent;
    }

    /**
     * 获取 是否在多次调度之间更新JobDataMap
     */
    public boolean getIsUpdateData() {
        return isUpdateData;
    }

    /**
     * 设置 是否在多次调度之间更新JobDataMap
     */
    public void setIsUpdateData(boolean isUpdateData) {
        this.isUpdateData = isUpdateData;
    }

    /**
     * 获取 Scheduler实例发生故障时，故障恢复节点会检测故障的Scheduler正在调度的任务是否需要recovery(复苏)，如果需要会添加一个只执行一次的simple trigger重新触发
     */
    public boolean getRequestsRecovery() {
        return requestsRecovery;
    }

    /**
     * 设置 Scheduler实例发生故障时，故障恢复节点会检测故障的Scheduler正在调度的任务是否需要recovery(复苏)，如果需要会添加一个只执行一次的simple trigger重新触发
     */
    public void setRequestsRecovery(boolean requestsRecovery) {
        this.requestsRecovery = requestsRecovery;
    }

    /**
     * 获取 存储JobDataMap等
     */
    public Map<String, Object> getJobData() {
        return jobData;
    }

    /**
     * 设置 存储JobDataMap等
     */
    public void setJobData(Map<String, Object> jobData) {
        this.jobData = jobData;
    }
}
