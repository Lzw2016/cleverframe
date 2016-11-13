package org.cleverframe.sys.service;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.sys.SysBeanNames;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Shiro Session管理，用于管理在线用户
 * 作者：LiZW <br/>
 * 创建时间：2016/11/13 16:37 <br/>
 */
@Service(SysBeanNames.OnlineUserService)
public class OnlineUserService extends BaseService {

    public DefaultWebSessionManager sessionManager = SpringContextHolder.getBean(SpringBeanNames.DefaultWebSessionManager);

    public List<Session> get() {
        List<Session> sessionList = new ArrayList<>();
        SessionDAO sessionDAO = sessionManager.getSessionDAO();
        Collection<Session> sessionCollection = sessionDAO.getActiveSessions();
//        for (Session session : sessionCollection) {
//            session.
//        }
        sessionList.addAll(sessionCollection);
        return sessionList;
    }
}
