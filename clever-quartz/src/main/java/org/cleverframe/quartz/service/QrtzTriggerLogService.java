package org.cleverframe.quartz.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.dao.QrtzTriggerLogDao;
import org.cleverframe.quartz.entity.QrtzTriggerLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-3 23:35 <br/>
 */
@Service(QuartzBeanNames.QrtzTriggerLogService)
public class QrtzTriggerLogService extends BaseService {

    @Autowired
    @Qualifier(QuartzBeanNames.QrtzTriggerLogDao)
    private QrtzTriggerLogDao qrtzTriggerLogDao;

    @Transactional(readOnly = false)
    public QrtzTriggerLog addQrtzTriggerLog(QrtzTriggerLog qrtzTriggerLog) {
        qrtzTriggerLogDao.getHibernateDao().save(qrtzTriggerLog);
        return qrtzTriggerLog;
    }

    @Transactional(readOnly = false)
    public QrtzTriggerLog updateQrtzTriggerLog(QrtzTriggerLog qrtzTriggerLog) {
        qrtzTriggerLog = (QrtzTriggerLog) qrtzTriggerLogDao.getHibernateDao().update(QrtzTriggerLog.class, qrtzTriggerLog.getId(), qrtzTriggerLog);
        return qrtzTriggerLog;
    }

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
        return qrtzTriggerLogDao.findByPage(
                page,
                schedulerName,
                instanceName,
                triggerGroup,
                triggerName,
                jobGroup,
                jobName,
                jobClassName,
                startTimeByStart,
                startTimeByEnd,
                processTimeByMin,
                processTimeByMax);
    }
}
