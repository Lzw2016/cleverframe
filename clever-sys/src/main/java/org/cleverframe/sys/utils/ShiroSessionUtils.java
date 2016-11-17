package org.cleverframe.sys.utils;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.sys.attributes.SysSessionAttributes;
import org.cleverframe.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Shiro Session操作工具类
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/15 10:18 <br/>
 */
public class ShiroSessionUtils {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(ShiroSessionUtils.class);

    /**
     * Shiro Session属性键，值为true表示用户已经被提出
     */
    public static final String USER_IS_KICK_OUT = "KickOutSessionFilter-USER_IS_KICK_OUT";

    /**
     * 请使用 {@link #getSessionDAO()}
     */
    private static SessionDAO SESSION_DAO;

    /**
     * 获取 Shiro Session Dao
     */
    public static SessionDAO getSessionDAO() {
        if (SESSION_DAO == null) {
            SESSION_DAO = SpringContextHolder.getBean(SpringBeanNames.ShiroSessionDAO);
        }
        if (SESSION_DAO == null) {
            RuntimeException exception = new RuntimeException("获取Bean失败，BeanName=" + SpringBeanNames.ShiroSessionDAO);
            logger.error(exception.getMessage(), exception);
            throw exception;
        }
        return SESSION_DAO;
    }

    /**
     * 从Shiro Session中获取user对象
     *
     * @param session Shiro Session
     * @return 不存在返回null
     */
    public static User getUserBySession(Session session) {
        if (session == null || session.getAttribute(SysSessionAttributes.LOGIN_USER) == null) {
            return null;
        }
        Object object = session.getAttribute(SysSessionAttributes.LOGIN_USER);
        if (!(object instanceof User)) {
            logger.error("Shiro Session中的属性值不能转换成User对象，属性[{}]", SysSessionAttributes.LOGIN_USER);
            return null;
        }
        return (User) object;
    }

    /**
     * 从Shiro Session中获取 用户是否登录通过
     *
     * @param session Shiro Session
     * @return {@link org.cleverframe.core.persistence.entity.BaseEntity#YES} 或者 {@link org.cleverframe.core.persistence.entity.BaseEntity#NO}
     */
    public static Character getIsOnLineBySession(Session session) {
        Character result = User.NO;
        if (session == null) {
            return result;
        }
        if (session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY) != null) {
            if ("true".equalsIgnoreCase(session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY).toString())) {
                result = User.YES;
            } else if ("false".equalsIgnoreCase(session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY).toString())) {
                result = User.NO;
            } else {
                logger.error("Shiro Session用户是否登录通过属性值未知，{}={}",
                        DefaultSubjectContext.AUTHENTICATED_SESSION_KEY,
                        session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY));
            }
        }
        return result;
    }

    /**
     * 根据sessionId获取 Shiro Session
     *
     * @return 不存在返回null
     */
    public static Session getSession(Serializable sessionId) {
        Session session = null;
        SessionDAO sessionDAO = getSessionDAO();
        try {
            session = sessionDAO.readSession(sessionId);
        } catch (Exception e) {
            logger.warn("获取Shiro Session失败,SessionId=" + sessionId, e);
        }
        return session;
    }

    /**
     * 在Session中设置踢出用户属性
     *
     * @param session Shiro Session
     */
    public static void setkickOutFlag(Session session) {
        session.setAttribute(USER_IS_KICK_OUT, "true");
    }

    /**
     * @param session Shiro Session
     * @return Session中存在踢出用户属性返回true
     */
    public static boolean isKickOut(Session session) {
        Object isKickOut = session.getAttribute(USER_IS_KICK_OUT);
        return isKickOut != null && "true".equalsIgnoreCase(isKickOut.toString());
    }

    /**
     * 序列化Shiro Session
     */
    public static byte[] serialize(Session session) {
        if (session == null) {
            return null;
        }
        if (!(session instanceof Serializable)) {
            throw new RuntimeException("Session没有实现Serializable接口，Session Class=" + session.getClass().getName());
        }
        return SerializationUtils.serialize((Serializable) session);
    }

    /**
     * 反序列化Shiro Session
     */
    public static Session deserialize(byte[] data) {
        if (data == null) {
            return null;
        }
        return SerializationUtils.deserialize(data);
    }
}
