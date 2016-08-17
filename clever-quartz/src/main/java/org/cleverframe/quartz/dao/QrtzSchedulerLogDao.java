package org.cleverframe.quartz.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.entity.QrtzSchedulerLog;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-3 23:09 <br/>
 */
@Repository(QuartzBeanNames.QrtzSchedulerLogDao)
public class QrtzSchedulerLogDao extends BaseDao<QrtzSchedulerLog> {
    /**
     * 分页查询调度器日志
     *
     * @param page          分页数据
     * @param schedulerName 调度器名称
     * @param instanceName  调度器ID
     * @param methodName    触发事件调用的方法
     * @param logTimeStart  记录时间-起始值
     * @param logTimeEnd    记录时间-结束值
     * @return 触发器日志分页数据
     */
    public Page<QrtzSchedulerLog> findByPage(Page<QrtzSchedulerLog> page, String schedulerName, String instanceName, String methodName, Date logTimeStart, Date logTimeEnd) {
        Parameter param = new Parameter();
        param.put("schedulerName", schedulerName);
        param.put("instanceName", instanceName);
        param.put("methodName", methodName);
        param.put("logTimeStart", logTimeStart);
        param.put("logTimeEnd", logTimeEnd);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.quartz.dao.QrtzSchedulerLogDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }
}
