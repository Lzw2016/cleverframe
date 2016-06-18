package org.cleverframe.core.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.dao.MDictDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-18 23:07 <br/>
 */
@Service(CoreBeanNames.MDictService)
public class MDictService extends BaseService {

    @Autowired
    @Qualifier(CoreBeanNames.MDictDao)
    private MDictDao mDictDao;
}
