package org.cleverframe.quartz.dao;

import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.entity.QrtzTriggers;
import org.springframework.stereotype.Repository;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-13 12:54 <br/>
 */
@Repository(QuartzBeanNames.QrtzTriggersDao)
public class QrtzTriggersDao extends BaseDao<QrtzTriggers> {

    /**
     * 查询触发器数据
     *
     * @param schedName    Scheduler名称
     * @param triggerName  Trigger key
     * @param triggerGroup Trigger group名称
     * @return 不存在返回null
     */
    public QrtzTriggers getQrtzTriggers(String schedName, String triggerGroup, String triggerName) {
        Parameter param = new Parameter();
        param.put("schedName", schedName);
        param.put("triggerGroup", triggerGroup);
        param.put("triggerName", triggerName);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.quartz.dao.QrtzTriggersDao.getQrtzTriggers");
        return hibernateDao.getBySql(sql, param);
    }
}
