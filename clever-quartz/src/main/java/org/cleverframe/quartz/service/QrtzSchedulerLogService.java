package org.cleverframe.quartz.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.dao.QrtzSchedulerLogDao;
import org.cleverframe.quartz.entity.QrtzSchedulerLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-3 23:34 <br/>
 */
@Service(QuartzBeanNames.QrtzSchedulerLogService)
public class QrtzSchedulerLogService extends BaseService {

    @Autowired
    @Qualifier(QuartzBeanNames.QrtzSchedulerLogDao)
    private QrtzSchedulerLogDao qrtzSchedulerLogDao;

    @Transactional(readOnly = false)
    public QrtzSchedulerLog saveQrtzSchedulerLog(QrtzSchedulerLog qrtzSchedulerLog) {
        qrtzSchedulerLogDao.getHibernateDao().save(qrtzSchedulerLog);
        return qrtzSchedulerLog;
    }

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
        return qrtzSchedulerLogDao.findByPage(page, schedulerName, instanceName, methodName, logTimeStart, logTimeEnd);
    }
}
