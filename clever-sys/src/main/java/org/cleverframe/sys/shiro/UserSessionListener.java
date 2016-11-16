package org.cleverframe.sys.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.sys.entity.LoginLog;
import org.cleverframe.sys.entity.User;
import org.cleverframe.sys.service.LoginLogService;
import org.cleverframe.sys.utils.KickOutCacheUtils;
import org.cleverframe.sys.utils.ShiroSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Shiro Session监听器
 * 作者：LiZW <br/>
 * 创建时间：2016/11/15 21:50 <br/>
 */
public class UserSessionListener implements SessionListener {

    private LoginLogService loginLogService;

    public UserSessionListener(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(UserSessionListener.class);

    /**
     * 会话创建时触发
     */
    @Override
    public void onStart(Session session) {
        logger.debug("Shiro Session被创建，ID={}", session.getId());
    }

    /**
     * 退出/会话过期时触发
     */
    @Override
    public void onStop(Session session) {
        logger.debug("Shiro Session[退出/过期]，ID={}", session.getId());

        User user = ShiroSessionUtils.getUserBySession(session);
        // 移除ShiroKickOutCache缓存中失效的Session(Session过期或者用户已经登出)
        if (user != null && user.getLoginName() != null) {
            KickOutCacheUtils.removeInvalidSessionId(user.getLoginName(), session.getId());
        }

        // 保存登出日志
        LoginLog loginLog = new LoginLog();
        loginLog.setLoginTime(new Date());
        loginLog.setLoginIp(session.getHost());
        loginLog.setUserAgent("-");
        loginLog.setLoginOperation(LoginLog.LOGOUT);
        if (user != null) {
            loginLog.setLoginName(user.getLoginName());
            loginLog.setUserInfo(JacksonMapper.nonEmptyMapper().toJson(user));

        } else {
            loginLog.setLoginName("未知(可能是被迫下线)");
        }
        loginLogService.save(loginLog);
    }

    /**
     * 会话过期时触发
     */
    @Override
    public void onExpiration(Session session) {
        logger.debug("Shiro Session过期，ID={}", session.getId());
    }
}
