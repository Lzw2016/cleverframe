package org.cleverframe.quartz.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-31 22:25 <br/>
 */
public class TriggerKeyVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

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

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

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
}
