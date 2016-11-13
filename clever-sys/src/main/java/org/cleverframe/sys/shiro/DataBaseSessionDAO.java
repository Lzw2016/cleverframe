package org.cleverframe.sys.shiro;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.LoginSessionDao;
import org.cleverframe.sys.entity.LoginSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;

/**
 * 使用数据库存储Shiro用户登录Session信息,方便计算和查询(分页)在线人数等信息<br/>
 * 参考 EnterpriseCacheSessionDAO
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/13 22:54 <br/>
 *
 * @see org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO
 */
@Transactional(readOnly = true)
public class DataBaseSessionDAO extends CachingSessionDAO {

    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(DataBaseSessionDAO.class);

    private LoginSessionDao loginSessionDao;

    public LoginSessionDao getLoginSessionDao() {
        if (loginSessionDao == null) {
            loginSessionDao = SpringContextHolder.getBean(SysBeanNames.LoginSessionDao);
        }
        return loginSessionDao;
    }

    @Transactional(readOnly = false)
    @Override
    protected void doUpdate(Session session) {
        this.getLoginSessionDao();
        String sessionId = (String) session.getId();
        if (StringUtils.isBlank(sessionId)) {
            RuntimeException exception = new RuntimeException("Shiro Session ID 不能为空");
            logger.error(exception.getMessage(), exception);
            return;
        }
        LoginSession loginSession = loginSessionDao.getBySessionId(sessionId);
        if (loginSession == null) {
            doCreate(session);
            return;
        }
        if (!(session instanceof Serializable)) {
            RuntimeException exception = new RuntimeException("Shiro Session没有实现Serializable接口不能序列化存储");
            logger.error(exception.getMessage(), exception);
            return;
        }
        Serializable serializable = (Serializable) session;
        loginSession.setUpdateDate(new Date());
        loginSession.setSessionObject(SerializationUtils.serialize(serializable));
        loginSessionDao.getHibernateDao().update(loginSession);
        logger.debug("Session更新成功, SessionId=[{}]", sessionId);
    }

    @Transactional(readOnly = false)
    @Override
    protected void doDelete(Session session) {
        this.getLoginSessionDao();
        String sessionId = (String) session.getId();
        if (StringUtils.isBlank(sessionId)) {
            RuntimeException exception = new RuntimeException("Shiro Session ID 不能为空");
            logger.error(exception.getMessage(), exception);
            return;
        }
        boolean flag = loginSessionDao.deleteBySessionId(sessionId);
        if (!flag) {
            RuntimeException exception = new RuntimeException("Shiro Session 删除失败");
            logger.error(exception.getMessage(), exception);
        } else {
            logger.debug("Session删除成功, SessionId=[{}]", sessionId);
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        this.getLoginSessionDao();
        this.generateSessionId(session);
        String sessionId = (String) session.getId();
        if (StringUtils.isBlank(sessionId)) {
            throw new RuntimeException("Shiro Session ID 不能为空");
        }
        if (!(session instanceof Serializable)) {
            throw new RuntimeException("Shiro Session没有实现Serializable接口不能序列化存储");
        }
        Serializable serializable = (Serializable) session;
        LoginSession loginSession = new LoginSession();
        loginSession.setCreateDate(new Date());
        loginSession.setSessionId(sessionId);
        loginSession.setLoginName(session.getHost());
        loginSession.setSessionObject(SerializationUtils.serialize(serializable));
        loginSessionDao.getHibernateDao().save(loginSession);
        logger.debug("Session新增成功, SessionId=[{}]", sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        this.getLoginSessionDao();
        String strId = (String) sessionId;
        if (StringUtils.isBlank(strId)) {
            throw new RuntimeException("Shiro Session ID 不能为空");
        }
        LoginSession loginSession = loginSessionDao.getBySessionId(strId);
        Session session = null;
        if (loginSession != null && loginSession.getSessionObject() != null) {
            session = SerializationUtils.deserialize(loginSession.getSessionObject());
            logger.debug("Session读取成功, SessionId=[{}]", strId);
        }
        return session;
    }
}
