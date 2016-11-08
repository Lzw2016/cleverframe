package org.cleverframe.sys.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.LoginLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service，对应表sys_login_log(用户登录日志表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:51:31 <br/>
 */
@Service(SysBeanNames.LoginLogService)
public class LoginLogService extends BaseService {

    @Autowired
    @Qualifier(SysBeanNames.LoginLogDao)
    private LoginLogDao loginLogDao;
}
