package org.cleverframe.quartz.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.dao.QrtzSchedulerLogDao;
import org.cleverframe.quartz.entity.QrtzSchedulerLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
