package org.cleverframe.quartz.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.dao.QrtzTriggerLogDao;
import org.cleverframe.quartz.entity.QrtzTriggerLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
