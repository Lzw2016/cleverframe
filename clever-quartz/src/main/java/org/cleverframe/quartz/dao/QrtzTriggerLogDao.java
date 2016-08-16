package org.cleverframe.quartz.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.entity.QrtzTriggerLog;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-3 23:08 <br/>
 */
@Repository(QuartzBeanNames.QrtzTriggerLogDao)
public class QrtzTriggerLogDao extends BaseDao<QrtzTriggerLog> {

    /**
     * 分页查询所有的触发器日志
     *
     * @param page             分页数据
     * @param schedulerName    调度器名称
     * @param instanceName     调度器ID
     * @param triggerGroup     触发器分组
     * @param triggerName      触发器名称
     * @param jobGroup         任务分组
     * @param jobName          任务名称
     * @param jobClassName     任务实现类
     * @param startTimeByStart 开始触发时间-起始值
     * @param startTimeByEnd   开始触发时间-结束值
     * @param processTimeByMin 处理时间-最小值
     * @param processTimeByMax 处理时间-最大值
     * @return 触发器日志分页数据
     */
    public Page<QrtzTriggerLog> findByPage(
            Page<QrtzTriggerLog> page,
            String schedulerName,
            String instanceName,
            String triggerGroup,
            String triggerName,
            String jobGroup,
            String jobName,
            String jobClassName,
            Date startTimeByStart,
            Date startTimeByEnd,
            Long processTimeByMin,
            Long processTimeByMax) {
        Parameter param = new Parameter();
        param.put("schedulerName", schedulerName);
        param.put("instanceName", instanceName);
        param.put("triggerGroup", triggerGroup);
        param.put("triggerName", triggerName);
        param.put("jobGroup", jobGroup);
        param.put("jobName", jobName);
        param.put("jobClassName", jobClassName);
        param.put("startTimeByStart", startTimeByStart);
        param.put("startTimeByEnd", startTimeByEnd);
        param.put("processTimeByMin", processTimeByMin);
        param.put("processTimeByMax", processTimeByMax);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.quartz.dao.QrtzTriggerLogDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }
}
