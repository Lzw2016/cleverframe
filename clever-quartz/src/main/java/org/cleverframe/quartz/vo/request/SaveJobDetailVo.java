package org.cleverframe.quartz.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-30 14:54 <br/>
 */
public class SaveJobDetailVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * Job key
     */
    @NotBlank(message = "JobName不能为空")
    @Length(max = 200, message = "JobName长度不能超过200")
    private String jobName;

    /**
     * Job group 名称
     */
    @NotBlank(message = "JobGroup不能为空")
    @Length(max = 200, message = "JobGroup长度不能超过200")
    private String jobGroup;

    /**
     * Job描述， .withDescription()方法传入的string
     */
    @Length(max = 250, message = "Job描述长度不能超过250")
    private String description;

    /**
     * 实现Job的类名，trigger触发时调度此类的execute方法
     */
    @NotBlank(message = "JobClassName不能为空")
    @Length(max = 250, message = "JobClassName长度不能超过250")
    private String jobClassName;

    /**
     * Scheduler实例发生故障时，故障恢复节点会检测故障的Scheduler正在调度的任务是否需要recovery(复苏)，如果需要会添加一个只执行一次的simple trigger重新触发
     */
    @NotBlank(message = "requestsRecovery不能为空")
    @Pattern(regexp = "0|1", message = "requestsRecovery只能是：0-否,1-是")
    private String requestsRecovery;

    /**
     * 存储JobDataMap等,Map<String, String> Json字符串
     */
    private String jobData;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

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
     * 获取 Scheduler实例发生故障时，故障恢复节点会检测故障的Scheduler正在调度的任务是否需要recovery(复苏)，如果需要会添加一个只执行一次的simple trigger重新触发
     */
    public String getRequestsRecovery() {
        return requestsRecovery;
    }

    /**
     * 设置 Scheduler实例发生故障时，故障恢复节点会检测故障的Scheduler正在调度的任务是否需要recovery(复苏)，如果需要会添加一个只执行一次的simple trigger重新触发
     */
    public void setRequestsRecovery(String requestsRecovery) {
        this.requestsRecovery = requestsRecovery;
    }

    /**
     * 获取 存储JobDataMap等
     */
    public String getJobData() {
        return jobData;
    }

    /**
     * 设置 存储JobDataMap等
     */
    public void setJobData(String jobData) {
        this.jobData = jobData;
    }
}
