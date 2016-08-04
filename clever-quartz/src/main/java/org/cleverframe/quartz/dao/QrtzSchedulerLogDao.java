package org.cleverframe.quartz.dao;

import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.quartz.QuartzBeanNames;
import org.cleverframe.quartz.entity.QrtzSchedulerLog;
import org.springframework.stereotype.Repository;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-3 23:09 <br/>
 */
@Repository(QuartzBeanNames.QrtzSchedulerLogDao)
public class QrtzSchedulerLogDao extends BaseDao<QrtzSchedulerLog> {

}
