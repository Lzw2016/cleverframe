package org.cleverframe.quartz.entity.key;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-13 13:25 <br/>
 */
@Embeddable
public class QrtzTriggersKey implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Scheduler名称
     */
    private String schedName;

    /**
     * Trigger key
     */
    private String triggerName;

    /**
     * Trigger group名称
     */
    private String triggerGroup;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QrtzTriggersKey)) {
            return false;
        }
        QrtzTriggersKey qrtzTriggersKey = (QrtzTriggersKey) obj;
        return StringUtils.equals(this.schedName, qrtzTriggersKey.schedName)
                && StringUtils.equals(this.triggerName, qrtzTriggersKey.triggerName)
                && StringUtils.equals(this.triggerGroup, qrtzTriggersKey.triggerGroup);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (schedName == null ? 0 : schedName.hashCode());
        result = 31 * result + (triggerName == null ? 0 : triggerName.hashCode());
        result = 31 * result + (triggerGroup == null ? 0 : triggerGroup.hashCode());
        return result;
    }

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
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
}
