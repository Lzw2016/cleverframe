package org.cleverframe.sys.dao;

import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.LoginLog;
import org.springframework.stereotype.Repository;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-24 11:28 <br/>
 */
@Repository(SysBeanNames.LoginLogDao)
public class LoginLogDao extends BaseDao<LoginLog> {

}
