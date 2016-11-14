package org.cleverframe.sys.service;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.LoginSessionDao;
import org.cleverframe.sys.entity.LoginSession;
import org.cleverframe.sys.vo.request.LoginSessionQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/14 22:07 <br/>
 */
@Service(SysBeanNames.OnlineUserService)
public class OnlineUserService extends BaseService {

    @Autowired
    @Qualifier(SysBeanNames.LoginSessionDao)
    private LoginSessionDao loginSessionDao;

    @Autowired
    @Qualifier(SpringBeanNames.ShiroSessionDAO)
    private SessionDAO sessionDAO;

    /**
     * 分页查询在线用户(Session)信息
     */
    public Page<LoginSession> findByPage(Page<LoginSession> page, LoginSessionQueryVo loginSessionQueryVo) {
        return loginSessionDao.findByPage(page, loginSessionQueryVo);
    }

    /**
     * 根据sessionId调用SessionDAO.readSession读取Session对象
     */
    public Session getSession(Serializable sessionId) {
        return sessionDAO.readSession(sessionId);
    }
}
