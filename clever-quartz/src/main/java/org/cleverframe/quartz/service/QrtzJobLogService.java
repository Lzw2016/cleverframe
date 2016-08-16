package org.cleverframe.quartz.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.dao.QrtzJobLogDao;
import org.cleverframe.quartz.entity.QrtzJobLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-3 23:34 <br/>
 */
@Service(QuartzBeanNames.QrtzJobLogService)
public class QrtzJobLogService extends BaseService {

    @Autowired
    @Qualifier(QuartzBeanNames.QrtzJobLogDao)
    private QrtzJobLogDao qrtzJobLogDao;

    @Transactional(readOnly = false)
    public QrtzJobLog addQrtzJobLog(QrtzJobLog qrtzJobLog) {
        qrtzJobLogDao.getHibernateDao().save(qrtzJobLog);
        return qrtzJobLog;
    }

    @Transactional(readOnly = false)
    public QrtzJobLog updateQrtzJobLog(QrtzJobLog qrtzJobLog) {
        qrtzJobLog = (QrtzJobLog) qrtzJobLogDao.getHibernateDao().update(QrtzJobLog.class, qrtzJobLog.getId(), qrtzJobLog);
        return qrtzJobLog;
    }

    /**
     * 分页查询所有的触发器日志
     *
     * @param page             分页数据
     * @param schedulerName    调度器名称
     * @param instanceName     调度器ID
     * @param jobGroup         任务分组
     * @param jobName          任务名称
     * @param jobClassName     任务实现类
     * @param startTimeByStart 开始触发时间-起始值
     * @param startTimeByEnd   开始触发时间-结束值
     * @param processTimeByMin 处理时间-最小值
     * @param processTimeByMax 处理时间-最大值
     * @return 触发器日志分页数据
     */
    public Page<QrtzJobLog> findByPage(
            Page<QrtzJobLog> page,
            String schedulerName,
            String instanceName,
            String jobGroup,
            String jobName,
            String jobClassName,
            Date startTimeByStart,
            Date startTimeByEnd,
            Long processTimeByMin,
            Long processTimeByMax) {
        return qrtzJobLogDao.findByPage(page, schedulerName, instanceName, jobGroup, jobName, jobClassName, startTimeByStart, startTimeByEnd, processTimeByMin, processTimeByMax);
    }
}
