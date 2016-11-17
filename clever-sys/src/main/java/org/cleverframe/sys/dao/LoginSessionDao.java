package org.cleverframe.sys.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.LoginSession;
import org.cleverframe.sys.vo.request.LoginSessionQueryVo;
import org.springframework.stereotype.Repository;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/13 22:49 <br/>
 */
@Repository(SysBeanNames.LoginSessionDao)
public class LoginSessionDao extends BaseDao<LoginSession> {

    /**
     * 查询所有的 Shiro Session(使用分页)
     */
    public Page<LoginSession> findAllByPage(Page<LoginSession> page) {
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.LoginSessionDao.findAllByPage");
        return hibernateDao.findBySql(page, sql);
    }

    /**
     * 分页查询在线用户(Session)信息
     */
    public Page<LoginSession> findByPage(Page<LoginSession> page, LoginSessionQueryVo loginSessionQueryVo) {
        Parameter param = new Parameter();
        param.put("createDateStart", loginSessionQueryVo.getCreateDateStart());
        param.put("createDateEnd", loginSessionQueryVo.getCreateDateEnd());
        param.put("updateDateStart", loginSessionQueryVo.getUpdateDateStart());
        param.put("updateDateEnd", loginSessionQueryVo.getUpdateDateEnd());
        param.put("loginName", loginSessionQueryVo.getLoginName());
        param.put("onLine", loginSessionQueryVo.getOnLine());
        param.put("hostIp", loginSessionQueryVo.getHostIp());
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.LoginSessionDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }

    /**
     * 根据session_id查询Session数据
     *
     * @param sessionId Shiro Session ID
     */
    public LoginSession getBySessionId(String sessionId) {
        Parameter param = new Parameter();
        param.put("sessionId", sessionId);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.LoginSessionDao.getBySessionId");
        return hibernateDao.getBySql(sql, param);
    }

    /**
     * 根据session_id删除Session数据
     *
     * @param sessionId Shiro Session ID
     */
    public boolean deleteBySessionId(String sessionId) {
        Parameter param = new Parameter();
        param.put("sessionId", sessionId);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.LoginSessionDao.deleteBySessionId");
        int count = hibernateDao.updateBySql(sql, param);
        return count == 1;
    }
}