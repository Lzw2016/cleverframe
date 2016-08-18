package org.cleverframe.core.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.dao.ResourcesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-17 13:55 <br/>
 */
@Service(CoreBeanNames.ResourcesService)
public class ResourcesService extends BaseService {

    @Autowired
    @Qualifier(CoreBeanNames.ResourcesDao)
    private ResourcesDao resourcesDao;
}
