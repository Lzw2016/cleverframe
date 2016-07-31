package org.cleverframe.quartz.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-31 17:13 <br/>
 */
public class AddCronTriggerForJobVo extends BaseRequestVo {
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
     * Trigger key
     */
    @NotBlank(message = "TriggerName不能为空")
    @Length(max = 200, message = "TriggerName长度不能超过200")
    private String triggerName;

    /**
     * Trigger group名称
     */
    @NotBlank(message = "TriggerGroup不能为空")
    @Length(max = 200, message = "TriggerGroup长度不能超过200")
    private String triggerGroup;

    /**
     * Trigger 描述， .withDescription()方法传入的string
     */
    @Length(max = 250, message = "Job描述长度不能超过250")
    private String description;

    /**
     * 开始触发时间
     */
    @NotNull(message = "开始触发时间不能为空")
    private Date startTime;

    /**
     * 结束触发时间
     */
    private Date endTime;

    /**
     * 优先级
     */
    @Range(min = 1, max = 10, message = "Trigger优先级取值范围:1~10")
    private Integer priority;

    /**
     * 存储JobDataMap等,Map<String, String> Json字符串
     */
    private String jobData;

    /**
     * cron表达式
     */
    @NotBlank(message = "触发的时间间隔不能为空")
    private String cron;

    /**
     * Quartz的Misfire处理规则取值
     */
    private Integer misfireInstruction;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getJobData() {
        return jobData;
    }

    public void setJobData(String jobData) {
        this.jobData = jobData;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getMisfireInstruction() {
        return misfireInstruction;
    }

    public void setMisfireInstruction(Integer misfireInstruction) {
        this.misfireInstruction = misfireInstruction;
    }
}
