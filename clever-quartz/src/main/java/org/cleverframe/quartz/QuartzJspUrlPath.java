package org.cleverframe.quartz;

import org.cleverframe.common.IJspUrlPath;

/**
 * 当前Quartz模块对应的JSP文件URL路径<br/>
 * <b>注意：此类只保存JSP文件的URL</b><br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-20 15:30 <br/>
 */
public class QuartzJspUrlPath implements IJspUrlPath {

    /**
     * Quartz管理主页
     */
    public static final String QuartzMain = "quartz/QuartzMain";

    /**
     * Scheduler 管理页面
     */
    public static final String Scheduler = "quartz/Scheduler";

    /**
     * JobDetail 管理页面
     */
    public static final String JobDetail = "quartz/JobDetail";

    /**
     * Trigger 管理页面
     */
    public static final String Trigger = "quartz/Trigger";

    /**
     * Scheduler 日志管理页面
     */
    public static final String SchedulerLog = "quartz/SchedulerLog";

    /**
     * JobDetail 日志管理页面
     */
    public static final String JobDetailLog = "quartz/JobDetailLog";

    /**
     * Trigger 日志管理页面
     */
    public static final String TriggerLog = "quartz/TriggerLog";

}
