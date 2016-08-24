package org.cleverframe.sys.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service，对应表sys_organization(组织机构表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:42:15 <br/>
 */
@Service(SysBeanNames.OrganizationService)
public class OrganizationService extends BaseService {

    @Autowired
    @Qualifier(SysBeanNames.OrganizationDao)
    private OrganizationDao organizationDao;
}
