package org.cleverframe.sys.job;

import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证Shiro会话(Session)的定时任务<br/>
 * 由于shiro-quartz不支持最新版本的quartz所以自己实现，参考 org.apache.shiro.session.mgt.quartz.QuartzSessionValidationJob
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/10 23:27 <br/>
 */
public class QuartzSessionValidationJob implements Job {
   /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    private static final Logger log = LoggerFactory.getLogger(QuartzSessionValidationJob.class);

    /**
     * Shiro的SessionManager
     */
    private static ValidatingSessionManager sessionManager;

    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    /**
     * Called when the job is executed by quartz.  This method delegates to the
     * <tt>validateSessions()</tt> method on the associated session manager.
     *
     * @param context the Quartz job execution context for this execution.
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.debug("开始验证会话是否有效...");
        sessionManager.validateSessions();
        log.debug("验证会话是否有效执行完成");
    }

    public static ValidatingSessionManager getSessionManager() {
        return sessionManager;
    }

    public static void setSessionManager(ValidatingSessionManager sessionManager) {
        QuartzSessionValidationJob.sessionManager = sessionManager;
    }
}
