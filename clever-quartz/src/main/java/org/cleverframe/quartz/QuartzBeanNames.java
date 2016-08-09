package org.cleverframe.quartz;

import org.cleverframe.common.IBeanNames;

/**
 * 定义当前quartz模块定义的Spring Bean名称<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-7-26 17:02 <br/>
 */
public class QuartzBeanNames implements IBeanNames {
    // -------------------------------------------------------------------------------------------//
    // Dao
    // -------------------------------------------------------------------------------------------//
    public static final String  QrtzSchedulerLogDao = "quartz_QrtzSchedulerLogDao";
    public static final String  QrtzTriggerLogDao = "quartz_QrtzTriggerLogDao";
    public static final String  QrtzJobLogDao = "quartz_QrtzJobLogDao";

    // -------------------------------------------------------------------------------------------//
    // Service
    // -------------------------------------------------------------------------------------------//
    public static final String  SchedulerService = "quartz_SchedulerService";
    public static final String  TriggerService = "quartz_TriggerService";
    public static final String  JobDetailService = "quartz_JobDetailService";

    public static final String  QrtzJobLogService = "quartz_QrtzJobLogService";
    public static final String  QrtzSchedulerLogService = "quartz_QrtzSchedulerLogService";
    public static final String  QrtzTriggerLogService = "quartz_QrtzTriggerLogService";


    // -------------------------------------------------------------------------------------------//
    // Other
    // -------------------------------------------------------------------------------------------//


}
