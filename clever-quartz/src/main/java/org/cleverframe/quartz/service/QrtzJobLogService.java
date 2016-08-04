package org.cleverframe.quartz.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.dao.QrtzJobLogDao;
import org.cleverframe.quartz.entity.QrtzJobLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
