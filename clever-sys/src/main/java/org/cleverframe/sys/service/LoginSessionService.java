package org.cleverframe.sys.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.LoginSessionDao;
import org.cleverframe.sys.entity.LoginSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/14 19:41 <br/>
 */
@Service(SysBeanNames.LoginSessionService)
public class LoginSessionService extends BaseService {

    @Autowired
    @Qualifier(SysBeanNames.LoginSessionDao)
    public LoginSessionDao loginSessionDao;

    /**
     * 根据session_id查询Session数据
     *
     * @param sessionId Shiro Session ID
     */
    public LoginSession getBySessionId(String sessionId) {
        return loginSessionDao.getBySessionId(sessionId);
    }

    /**
     * 更新Session
     */
    @Transactional(readOnly = false)
    public boolean update(LoginSession loginSession) {
        loginSessionDao.getHibernateDao().update(loginSession);
        return true;
    }

    /**
     * 根据session_id删除Session数据
     *
     * @param sessionId Shiro Session ID
     */
    @Transactional(readOnly = false)
    public boolean deleteBySessionId(String sessionId) {
        return loginSessionDao.deleteBySessionId(sessionId);
    }

    /**
     * 保存Session
     */
    @Transactional(readOnly = false)
    public boolean save(LoginSession loginSession) {
        loginSessionDao.getHibernateDao().save(loginSession);
        return true;
    }
}
