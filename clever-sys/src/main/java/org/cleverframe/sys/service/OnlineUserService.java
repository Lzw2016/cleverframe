package org.cleverframe.sys.service;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.LoginSessionDao;
import org.cleverframe.sys.entity.LoginSession;
import org.cleverframe.sys.utils.ShiroSessionUtils;
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

    /**
     * 踢出用户
     *
     * @param sessionId   Shiro SessionID
     * @param ajaxMessage 用于设置失败信息
     * @return 成功返回 true
     */
    public boolean kickOutUser(Serializable sessionId, AjaxMessage ajaxMessage) {
        Session session =  ShiroSessionUtils.getSession(sessionId);
        if (session == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("该用户已经退出系统");
            return false;
        }
        ShiroSessionUtils.setkickOutFlag(session);
        return true;
    }
}
