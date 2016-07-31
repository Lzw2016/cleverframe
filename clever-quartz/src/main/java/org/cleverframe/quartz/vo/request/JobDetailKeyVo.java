package org.cleverframe.quartz.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-31 12:20 <br/>
 */
public class JobDetailKeyVo extends BaseRequestVo {
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
}
