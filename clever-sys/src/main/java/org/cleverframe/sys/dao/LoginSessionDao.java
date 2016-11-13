package org.cleverframe.sys.dao;

import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.LoginSession;
import org.springframework.stereotype.Repository;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/13 22:49 <br/>
 */
@Repository(SysBeanNames.LoginSessionDao)
public class LoginSessionDao extends BaseDao<LoginSession> {

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