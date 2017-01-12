package org.cleverframe.doc.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.doc.DocBeanNames;
import org.cleverframe.doc.dao.DocProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 作者：LiZW <br/>
 * 创建时间：2017/1/12 22:17 <br/>
 */
@Service(DocBeanNames.DocProjectService)
public class DocProjectService extends BaseService {

    @Autowired
    @Qualifier(DocBeanNames.DocProjectDao)
    private DocProjectDao docProjectDao;
}
