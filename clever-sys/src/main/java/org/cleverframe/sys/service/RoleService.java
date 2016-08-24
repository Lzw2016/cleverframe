package org.cleverframe.sys.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service，对应表sys_role(角色表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:36:05 <br/>
 */
@Service(SysBeanNames.RoleService)
public class RoleService extends BaseService {

    @Autowired
    @Qualifier(SysBeanNames.RoleDao)
    private RoleDao roleDao;
}
